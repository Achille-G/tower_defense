package com.example.tdsim.game;

import javafx.geometry.Point2D;

public class Base {
    private final Point2D position;
    private double hp;

    public Base(Point2D position, double hp) {
        this.position = position;
        this.hp = hp;
    }

    public Point2D getPosition() {
        return position;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }
}
