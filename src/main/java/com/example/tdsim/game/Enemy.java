package com.example.tdsim.game;

public class Enemy {
    private final String id;
    private final double maxHp;
    private double hp;
    private boolean alive;
    private int currentWaypointIndex;
    private MovementState movementState;

    public Enemy(String id, double maxHp) {
        this.id = id;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.alive = true;
        this.currentWaypointIndex = 0;
        movementState = null;
    }

    public String getId() {
        return id;
    }

    public double getMaxHp() {
        return maxHp;
    }

    public double getHp() {
        return hp;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getCurrentWaypointIndex() {
        return currentWaypointIndex;
    }

    public MovementState getMovementState() {
        return movementState;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setCurrentWaypointIndex(int currentWaypointIndex) {
        this.currentWaypointIndex = currentWaypointIndex;
    }

    public void setMovementState(MovementState movementState) {
        this.movementState = movementState;
    }
}
