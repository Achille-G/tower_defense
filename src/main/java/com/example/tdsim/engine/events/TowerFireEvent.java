package com.example.tdsim.engine.events;

import com.example.tdsim.engine.AbstractEvent;
import com.example.tdsim.engine.SimulationEngine;
import com.example.tdsim.game.GameState;
import com.example.tdsim.logic.EventHandlers;

public class TowerFireEvent extends AbstractEvent {
    final private String towerId;

    public TowerFireEvent(double time, long sequenceNumber, String towerId) {
        super(time, sequenceNumber);
        this.towerId = towerId;
    }

    public String getTowerId() {
        return towerId;
    }

    @Override
    public void execute(GameState gameState, SimulationEngine engine) {
        EventHandlers.handleTowerFire(this,gameState,engine);
    }

    @Override
    public String toString() {
        return "TowerFireEvent{time=" + getTime()
                + ", towerId=" + towerId + "}";
    }
}
