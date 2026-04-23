package com.example.tdsim.engine;

import com.example.tdsim.game.GameState;

public interface Event extends Comparable<Event> {
    double getTime();

    long getSequenceNumber();

    void execute(GameState gameState, SimulationEngine engine);
}
