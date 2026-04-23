package com.example.tdsim.logic;

import com.example.tdsim.engine.SimulationEngine;
import com.example.tdsim.engine.events.EnemyReachBaseEvent;
import com.example.tdsim.engine.events.EnemyReachWaypointEvent;
import com.example.tdsim.engine.events.EnemySpawnEvent;
import com.example.tdsim.game.Base;
import com.example.tdsim.game.Enemy;
import com.example.tdsim.game.GameState;
import com.example.tdsim.game.MovementState;
import javafx.geometry.Point2D;

public class EventHandlers {

    private static final double SEGMENT_DURATION = 2.0;

    public static void handleEnemySpawn(EnemySpawnEvent event, GameState gameState, SimulationEngine engine) {
        final String enemyId = event.getEnemyId();
        final Enemy enemy = new Enemy(enemyId, 10.0);
        gameState.getEnemies().put(enemyId, enemy);

        Point2D waypoint0 = gameState.getPath().getWaypoint(0);
        Point2D waypoint1 = gameState.getPath().getWaypoint(1);
        MovementState movement = new MovementState(waypoint0, waypoint1, engine.getCurrentTime(), engine.getCurrentTime() + SEGMENT_DURATION);
        enemy.setCurrentWaypointIndex(1);
        enemy.setMovementState(movement);

        engine.schedule(new EnemyReachWaypointEvent(
                engine.getCurrentTime() + SEGMENT_DURATION,
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
            MovementState movement = new MovementState(waypoint, gameState.getBase().getPosition(), engine.getCurrentTime(), engine.getCurrentTime() + SEGMENT_DURATION);
            enemy.setMovementState(movement);
            engine.schedule(new EnemyReachBaseEvent(
                    engine.getCurrentTime() + SEGMENT_DURATION,
                    engine.nextSequenceNumber(),
                    enemyId
            ));
        } else {
            MovementState movement = new MovementState(waypoint, gameState.getPath().getWaypoint(waypointIndex + 1), engine.getCurrentTime(), engine.getCurrentTime() + SEGMENT_DURATION);
            enemy.setCurrentWaypointIndex(waypointIndex + 1);
            enemy.setMovementState(movement);
            engine.schedule(new EnemyReachWaypointEvent(
                    engine.getCurrentTime() + SEGMENT_DURATION,
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
}
