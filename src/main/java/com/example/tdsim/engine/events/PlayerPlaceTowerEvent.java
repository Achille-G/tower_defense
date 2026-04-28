package com.example.tdsim.engine.events;

import com.example.tdsim.engine.AbstractEvent;
import com.example.tdsim.engine.SimulationEngine;
import com.example.tdsim.game.GameState;
import com.example.tdsim.logic.EventHandlers;
import javafx.geometry.Point2D;

public class PlayerPlaceTowerEvent extends AbstractEvent {
    private final String towerId;
    private final Point2D position;

    public PlayerPlaceTowerEvent(double time, long sequenceNumber, String towerId, Point2D position) {
        super(time, sequenceNumber);
        this.towerId = towerId;
        this.position = position;
    }

    public String getTowerId() {
        return towerId;
    }

    public Point2D getPosition() {
        return position;
    }

    @Override
    public void execute(GameState gameState, SimulationEngine engine) {
        EventHandlers.handlePlayerPlaceTower(this,gameState,engine);
    }

    @Override
    public String toString() {
        return "PlayerPlaceTowerEvent{time=" + getTime()
                + ", towerId='" + towerId + "'"
                + ", Position=" + position + "}";
    }
}
