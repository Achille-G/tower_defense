package com.example.tdsim.game;

import javafx.geometry.Point2D;

public class MovementState {
    private Point2D fromPoint;
    private Point2D toPoint;
    private double startTime;
    private double endTime;

    public MovementState(Point2D fromPoint, Point2D toPoint, double startTime, double endTime) {
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Point2D getFromPoint() {
        return fromPoint;
    }

    public void setFromPoint(Point2D fromPoint) {
        this.fromPoint = fromPoint;
    }

    public Point2D getToPoint() {
        return toPoint;
    }

    public void setToPoint(Point2D toPoint) {
        this.toPoint = toPoint;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

}
