package com.example.tdsim.engine.events;

import com.example.tdsim.engine.AbstractEvent;
import com.example.tdsim.engine.SimulationEngine;
import com.example.tdsim.game.GameState;
import com.example.tdsim.logic.EventHandlers;

public class EnemyDieEvent extends AbstractEvent {

    private final String enemyId;

    public EnemyDieEvent(double time, long sequenceNumber, String enemyId) {
        super(time, sequenceNumber);
        this.enemyId = enemyId;
    }

    public String getEnemyId() {
        return enemyId;
    }

    @Override
    public void execute(GameState gameState, SimulationEngine engine) {
        EventHandlers.handleEnemyDie(this,gameState,engine);
    }

    @Override
    public String toString() {
        return "EnemyDieEvent{time=" + getTime()
                + ", enemyId=" + enemyId + "}";
    }
}
