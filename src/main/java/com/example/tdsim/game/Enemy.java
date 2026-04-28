package com.example.tdsim.game;

public class Enemy {
    private final String id;
    private final double maxHp;
    private double hp;
    private boolean alive;
    private boolean dying;
    private int currentWaypointIndex;
    private MovementState movementState;

    public Enemy(String id, double maxHp) {
        this.id = id;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.alive = true;
        this.dying = false;
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

    public boolean isDying() {
        return dying;
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

    public void setDying(boolean dying) {
        this.dying = dying;
    }
}
