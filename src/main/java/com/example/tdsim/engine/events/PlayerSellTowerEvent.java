package com.example.tdsim.engine.events;

import com.example.tdsim.engine.AbstractEvent;
import com.example.tdsim.engine.SimulationEngine;
import com.example.tdsim.game.GameState;
import com.example.tdsim.logic.EventHandlers;

public class PlayerSellTowerEvent extends AbstractEvent {
    private final String towerId;

    public PlayerSellTowerEvent(double time, long sequenceNumber, String towerId) {
        super(time, sequenceNumber);
        this.towerId = towerId;
    }

    public String getTowerId() {
        return towerId;
    }

    @Override
    public void execute(GameState gameState, SimulationEngine engine) {
        EventHandlers.handlePlayerSellTower(this,gameState,engine);
    }

    @Override
    public String toString() {
        return "PlayerSellTowerEvent{time=" + getTime()
                + ", towerId='" + towerId + "'" + "}";
    }
}
