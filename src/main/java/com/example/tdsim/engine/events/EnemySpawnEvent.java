package com.example.tdsim.engine.events;

import com.example.tdsim.engine.AbstractEvent;
import com.example.tdsim.engine.SimulationEngine;
import com.example.tdsim.game.GameState;
import com.example.tdsim.logic.EventHandlers;

public class EnemySpawnEvent extends AbstractEvent {
    private final String enemyId;

    public EnemySpawnEvent(double time, long sequenceNumber, String enemyId) {
        super(time, sequenceNumber);
        this.enemyId = enemyId;
    }

    public String getEnemyId() {
        return enemyId;
    }

    @Override
    public void execute(GameState gameState, SimulationEngine engine) {
        EventHandlers.handleEnemySpawn(this, gameState, engine);
    }

    @Override
    public String toString() {
        return "EnemySpawnEvent{time=" + getTime() + ", enemyId='" + enemyId + "'}";
    }
}
