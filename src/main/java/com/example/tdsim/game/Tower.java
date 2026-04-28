package com.example.tdsim.game;

import javafx.geometry.Point2D;

public class Tower {
    private final String id;
    private final Point2D position;
    private final double range;
    private final double damage;
    private final double cooldown;
    private boolean active;
    private double lastFireTime = Double.NEGATIVE_INFINITY;
    private Point2D lastFireTarget;

    public Tower(String id, Point2D position, double range, double damage, double cooldown) {
        this.id = id;
        this.position = position;
        this.range = range;
        this.damage = damage;
        this.cooldown = cooldown;
        this.active = true;
    }

    public double getLastFireTime() {
        return lastFireTime;
    }

    public Point2D getLastFireTarget() {
        return lastFireTarget;
    }

    public void recordFire(double time, Point2D target) {
        this.lastFireTime = time;
        this.lastFireTarget = target;
    }

    public String getId() {
        return id;
    }

    public Point2D getPosition() {
        return position;
    }

    public double getRange() {
        return range;
    }

    public double getDamage() {
        return damage;
    }

    public double getCooldown() {
        return cooldown;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
