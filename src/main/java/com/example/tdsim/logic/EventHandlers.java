package com.example.tdsim.logic;

import com.example.tdsim.engine.SimulationEngine;
import com.example.tdsim.engine.events.*;
import com.example.tdsim.game.*;
import javafx.geometry.Point2D;

public class EventHandlers {

    private static final double ENEMY_SPEED = 40.0; // pixels par seconde

    public static void handleEnemySpawn(EnemySpawnEvent event, GameState gameState, SimulationEngine engine) {
        final String enemyId = event.getEnemyId();
        final Enemy enemy = new Enemy(enemyId, 10.0);
        gameState.getEnemies().put(enemyId, enemy);

        Point2D waypoint0 = gameState.getPath().getWaypoint(0);
        Point2D waypoint1 = gameState.getPath().getWaypoint(1);
        double duration = waypoint0.distance(waypoint1) / ENEMY_SPEED;
        MovementState movement = new MovementState(waypoint0, waypoint1, engine.getCurrentTime(), engine.getCurrentTime() + duration);
        enemy.setCurrentWaypointIndex(1);
        enemy.setMovementState(movement);

        engine.schedule(new EnemyReachWaypointEvent(
                engine.getCurrentTime() + duration,
                engine.nextSequenceNumber(),
                enemyId,
                enemy.getCurrentWaypointIndex()
        ));
    }

    public static void handleEnemyReachWaypoint(EnemyReachWaypointEvent event, GameState gameState, SimulationEngine engine) {
        final String enemyId = event.getEnemyId();
        final int waypointIndex = event.getWaypointIndex();

        final Enemy enemy = gameState.getEnemies().get(enemyId);
        final Point2D waypoint = gameState.getPath().getWaypoint(waypointIndex);
        final int dernierWaypoint = gameState.getPath().size() - 1;

        //revalidation
        if (enemy == null || !enemy.isAlive()) {
            return;
        }

        if (waypointIndex == dernierWaypoint) {
            Point2D basePos = gameState.getBase().getPosition();
            double duration = waypoint.distance(basePos) / ENEMY_SPEED;
            MovementState movement = new MovementState(waypoint, basePos, engine.getCurrentTime(), engine.getCurrentTime() + duration);
            enemy.setMovementState(movement);
            engine.schedule(new EnemyReachBaseEvent(
                    engine.getCurrentTime() + duration,
                    engine.nextSequenceNumber(),
                    enemyId
            ));
        } else {
            Point2D nextWaypoint = gameState.getPath().getWaypoint(waypointIndex + 1);
            double duration = waypoint.distance(nextWaypoint) / ENEMY_SPEED;
            MovementState movement = new MovementState(waypoint, nextWaypoint, engine.getCurrentTime(), engine.getCurrentTime() + duration);
            enemy.setCurrentWaypointIndex(waypointIndex + 1);
            enemy.setMovementState(movement);
            engine.schedule(new EnemyReachWaypointEvent(
                    engine.getCurrentTime() + duration,
                    engine.nextSequenceNumber(),
                    enemyId,
                    enemy.getCurrentWaypointIndex()
            ));
        }
    }

    public static void handleEnemyReachBase(EnemyReachBaseEvent event, GameState gameState, SimulationEngine engine) {
        final String enemyId = event.getEnemyId();
        final Enemy enemy = gameState.getEnemies().get(enemyId);
        if (enemy == null || !enemy.isAlive()) {
            return;
        }
        enemy.setAlive(false);
        Base base = gameState.getBase();
        base.setHp(base.getHp() - 1);
        System.out.printf("Base hit! HP %.2f%n", base.getHp());
    }

    public static void handlePlayerPlaceTower(PlayerPlaceTowerEvent event, GameState gameState, SimulationEngine engine){
        final String towerId = event.getTowerId();
        final Point2D position = event.getPosition();
        final double range = 120.0;
        final double damage = 5.0;
        final double cooldown = 2.0;
        final Tower tower = new Tower(towerId,position,range,damage,cooldown);
        gameState.getTowers().put(towerId,tower);

        engine.schedule(new TowerFireEvent(engine.getCurrentTime(), engine.nextSequenceNumber(),towerId));

    }

    public static void handleTowerFire(TowerFireEvent event, GameState gameState, SimulationEngine engine){
        final String towerId = event.getTowerId();
        final Tower tower = gameState.getTowers().get(towerId);
        if(tower == null || !tower.isActive()){
            return;
        }
        Enemy enemy = TargetingUtils.chooseTarget(tower,gameState,engine.getCurrentTime());
        if(enemy != null){
            Point2D enemyPos = MovementUtils.getEnemyPosition(enemy, engine.getCurrentTime());
            tower.recordFire(engine.getCurrentTime(), enemyPos);

            enemy.setHp(enemy.getHp() - tower.getDamage());
            System.out.printf("Enemy lose HP, current hp %.2f%n",enemy.getHp());
            if(enemy.getHp() <= 0.0){
                enemy.setDying(true);
                engine.schedule(new EnemyDieEvent(engine.getCurrentTime(),engine.nextSequenceNumber(),enemy.getId()));
            }
        }
        engine.schedule(new TowerFireEvent(engine.getCurrentTime() + tower.getCooldown(), engine.nextSequenceNumber(),towerId));

    }

    public static void handleEnemyDie(EnemyDieEvent event, GameState gameState, SimulationEngine engine){
        final String enemyId = event.getEnemyId();
        final Enemy enemy = gameState.getEnemies().get(enemyId);
        //revalidation
        if (enemy == null || !enemy.isAlive()) {
            return;
        }
        enemy.setAlive(false);
        System.out.printf("Enemy %s died %n",enemyId);
    }

    public static void handlePlayerSellTower(PlayerSellTowerEvent event, GameState gameState, SimulationEngine engine){
        final String towerId = event.getTowerId();
        final Tower tower = gameState.getTowers().get(towerId);

        //revalidation
        if (tower == null || !tower.isActive()){
            return;
        }
        tower.setActive(false);
        System.out.printf("Tower %s sold%n", tower.getId() );
    }
}
