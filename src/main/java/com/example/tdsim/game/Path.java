package com.example.tdsim.game;

import javafx.geometry.Point2D;

import java.util.List;

public class Path {
    private final List<Point2D> waypoints;

    public Path(List<Point2D> waypoints) {
        this.waypoints = waypoints;
    }

    public List<Point2D> getWaypoints() {
        return waypoints;
    }

    public Point2D getWaypoint(int index){
        return this.waypoints.get(index);
    }

    public int size(){
        return this.waypoints.size();
    }
}
