package com.example.tdsim.engine;

import java.util.Map;
import java.util.Objects;

public class Event implements Comparable<Event> {
    private final double time;
    private final EventType type;
    private final Map<String, Object> payload;
    private final long sequenceNumber;

    public Event(double time, EventType type, Map<String, Object> payload, long sequenceNumber) {
        this.time = time;
        this.type = type;
        this.payload = payload;
        this.sequenceNumber = sequenceNumber;
    }

    public double getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public int compareTo(Event o) {
        int timeComparison = Double.compare(this.time,o.getTime());
        if (timeComparison != 0){
            return timeComparison;
        }
        return Long.compare(this.sequenceNumber,o.getSequenceNumber());
    }

    @Override
    public String toString() {
        return "Event{" +
                "time=" + time +
                ", type=" + type +
                ", payload=" + payload +
                ", sequenceNumber=" + sequenceNumber +
                '}';
    }
}
