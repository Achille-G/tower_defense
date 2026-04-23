package com.example.tdsim.engine.events;

import com.example.tdsim.engine.AbstractEvent;
import com.example.tdsim.engine.SimulationEngine;
import com.example.tdsim.game.GameState;
import com.example.tdsim.logic.EventHandlers;

public class EnemyReachWaypointEvent extends AbstractEvent {
    private final String enemyId;
    private final int waypointIndex;

    public EnemyReachWaypointEvent(double time, long sequenceNumber, String enemyId, int waypointIndex) {
        super(time, sequenceNumber);
        this.enemyId = enemyId;
        this.waypointIndex = waypointIndex;
    }

    public String getEnemyId() {
        return enemyId;
    }

    public int getWaypointIndex() {
        return waypointIndex;
    }

    @Override
    public void execute(GameState gameState, SimulationEngine engine) {
        EventHandlers.handleEnemyReachWaypoint(this, gameState, engine);
    }

    @Override
    public String toString() {
        return "EnemyReachWaypointEvent{time=" + getTime()
                + ", enemyId='" + enemyId + "'"
                + ", waypointIndex=" + waypointIndex + "}";
    }
}
