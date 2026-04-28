package com.example.tdsim.game;

import javafx.geometry.Point2D;

public class Base {
    private final Point2D position;
    private final double maxHp;
    private double hp;

    public Base(Point2D position, double hp) {
        this.position = position;
        this.maxHp = hp;
        this.hp = hp;
    }

    public Point2D getPosition() {
        return position;
    }

    public double getHp() {
        return hp;
    }

    public double getMaxHp() {
        return maxHp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }
}
