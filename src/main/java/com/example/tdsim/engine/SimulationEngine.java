package com.example.tdsim.engine;

import com.example.tdsim.game.GameState;
import com.example.tdsim.logic.EventHandlers;

import java.util.Map;
import java.util.PriorityQueue;

public class SimulationEngine {
    private double currentTime;
    private long nextSequenceNumber;
    private final PriorityQueue<Event> eventQueue;

    public SimulationEngine() {
        this.currentTime =0.0;
        this.nextSequenceNumber = 0;
        this.eventQueue = new PriorityQueue<>();
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void schedule(double time, EventType type, Map<String, Object> payload){
        Event newEvent = new Event(time,type,payload,this.nextSequenceNumber);
        this.nextSequenceNumber++;
        System.out.printf("[t=%.2f] schedule %s %s%n",time,type,payload);
        this.eventQueue.add(newEvent);
    }

    public void runUntil(double visibleTime, GameState gameState){
        while(!eventQueue.isEmpty() && eventQueue.peek().getTime() <= visibleTime){
            Event event  = eventQueue.poll();
            currentTime = event.getTime();

            System.out.printf("[t=%.2f] execute %s %s%n",currentTime,event.getType(),event.getPayload());

            EventHandlers.handleEvent(event,gameState,this);

        }
    }
}
