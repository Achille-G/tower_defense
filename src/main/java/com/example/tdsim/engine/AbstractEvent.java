package com.example.tdsim.engine;

public abstract class AbstractEvent implements Event {
    private final double time;
    private final long sequenceNumber;

    protected AbstractEvent(double time, long sequenceNumber) {
        this.time = time;
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public double getTime() {
        return time;
    }

    @Override
    public long getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public int compareTo(Event o) {
        int timeComparison = Double.compare(this.time, o.getTime());
        if (timeComparison != 0) {
            return timeComparison;
        }
        return Long.compare(this.sequenceNumber, o.getSequenceNumber());
    }
}
