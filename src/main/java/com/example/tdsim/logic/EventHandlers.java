package com.example.tdsim.logic;

import com.example.tdsim.engine.Event;
import com.example.tdsim.engine.EventType;
import com.example.tdsim.engine.SimulationEngine;
import com.example.tdsim.game.Base;
import com.example.tdsim.game.Enemy;
import com.example.tdsim.game.GameState;
import com.example.tdsim.game.MovementState;
import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.Map;

public class EventHandlers {

    private static final double SEGMENT_DURATION = 2.0;

    public static void handleEvent(Event event, GameState gameState, SimulationEngine engine){
        switch (event.getType()){
            case ENEMY_SPAWN -> handleEnemySpawn(event,gameState,engine);
            case ENEMY_REACH_WAYPOINT -> handleEnemyReachWaypoint(event,gameState,engine);
            case ENEMY_REACH_BASE -> handleEnemyReachBase(event,gameState,engine);
        }
    }

    private static void handleEnemySpawn(Event event, GameState gameState, SimulationEngine engine) {
        final String enemyId = (String) event.getPayload().get("enemyId");
        final Enemy enemy = new Enemy(enemyId,10.0);
        gameState.getEnemies().put(enemyId,enemy);

        Point2D waypoint0 = gameState.getPath().getWaypoint(0);
        Point2D waypoint1 = gameState.getPath().getWaypoint(1);
        MovementState movement = new MovementState(waypoint0,waypoint1, engine.getCurrentTime(), engine.getCurrentTime() + SEGMENT_DURATION );
        enemy.setCurrentWaypointIndex(1);
        enemy.setMovementState(movement);
        Map<String,Object> payload = new HashMap<>();
        payload.put("enemyId",enemyId);
        payload.put("waypointIndex",enemy.getCurrentWaypointIndex());
        engine.schedule(engine.getCurrentTime()+SEGMENT_DURATION, EventType.ENEMY_REACH_WAYPOINT,payload);
    }

    private static void handleEnemyReachWaypoint(Event event, GameState gameState, SimulationEngine engine) {
        final String enemyId = (String) event.getPayload().get("enemyId");
        final int waypointIndex = (int) event.getPayload().get("waypointIndex");

        final Enemy enemy = gameState.getEnemies().get(enemyId);
        final Point2D waypoint = gameState.getPath().getWaypoint(waypointIndex);
        final int dernierWaypoint = gameState.getPath().size() - 1;

        //revalidation
        if(enemy == null || !enemy.isAlive()){
            return;
        }

        if(waypointIndex == dernierWaypoint){
            MovementState movement = new MovementState(waypoint,gameState.getBase().getPosition(), engine.getCurrentTime(), engine.getCurrentTime()+ SEGMENT_DURATION);
            enemy.setMovementState(movement);
            Map<String,Object> payload = new HashMap<>();
            payload.put("enemyId",enemyId);
            engine.schedule(engine.getCurrentTime()+SEGMENT_DURATION, EventType.ENEMY_REACH_BASE,payload);
        }
        else{
            MovementState movement = new MovementState(waypoint,gameState.getPath().getWaypoint(waypointIndex+1), engine.getCurrentTime(), engine.getCurrentTime()+ SEGMENT_DURATION);
            enemy.setCurrentWaypointIndex(waypointIndex+1);
            enemy.setMovementState(movement);
            Map<String,Object> payload = new HashMap<>();
            payload.put("enemyId",enemyId);
            payload.put("waypointIndex",enemy.getCurrentWaypointIndex());
            engine.schedule(engine.getCurrentTime()+SEGMENT_DURATION, EventType.ENEMY_REACH_WAYPOINT,payload);
        }

    }

    private static void handleEnemyReachBase(Event event, GameState gameState, SimulationEngine engine) {
        final String enemyId = (String) event.getPayload().get("enemyId");
        final Enemy enemy = gameState.getEnemies().get(enemyId);
        if(enemy ==null || !enemy.isAlive()){
            return;
        }
        enemy.setAlive(false);
        Base base = gameState.getBase();
        base.setHp(base.getHp() -1);
        System.out.printf("Base hit! HP %.2f%n",base.getHp());
    }

}
