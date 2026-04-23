package com.example.tdsim.engine;

import com.example.tdsim.game.GameState;

import java.util.PriorityQueue;

public class SimulationEngine {
    private double currentTime;
    private long nextSequenceNumber;
    private final PriorityQueue<Event> eventQueue;

    public SimulationEngine() {
        this.currentTime = 0.0;
        this.nextSequenceNumber = 0;
        this.eventQueue = new PriorityQueue<>();
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public long nextSequenceNumber() {
        return nextSequenceNumber++;
    }

    public void schedule(Event event) {
        System.out.printf("[t=%.2f] schedule %s%n", event.getTime(), event);
        this.eventQueue.add(event);
    }

    public void runUntil(double visibleTime, GameState gameState) {
        while (!eventQueue.isEmpty() && eventQueue.peek().getTime() <= visibleTime) {
            Event event = eventQueue.poll();
            currentTime = event.getTime();

            System.out.printf("[t=%.2f] execute %s%n", currentTime, event);

            event.execute(gameState, this);
        }
    }
}
