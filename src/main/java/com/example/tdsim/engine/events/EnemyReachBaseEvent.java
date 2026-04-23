package com.example.tdsim.engine.events;

import com.example.tdsim.engine.AbstractEvent;
import com.example.tdsim.engine.SimulationEngine;
import com.example.tdsim.game.GameState;
import com.example.tdsim.logic.EventHandlers;

public class EnemyReachBaseEvent extends AbstractEvent {
    private final String enemyId;

    public EnemyReachBaseEvent(double time, long sequenceNumber, String enemyId) {
        super(time, sequenceNumber);
        this.enemyId = enemyId;
    }

    public String getEnemyId() {
        return enemyId;
    }

    @Override
    public void execute(GameState gameState, SimulationEngine engine) {
        EventHandlers.handleEnemyReachBase(this, gameState, engine);
    }

    @Override
    public String toString() {
        return "EnemyReachBaseEvent{time=" + getTime() + ", enemyId='" + enemyId + "'}";
    }
}
