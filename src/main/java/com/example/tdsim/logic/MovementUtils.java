package com.example.tdsim.logic;

import com.example.tdsim.game.Enemy;
import com.example.tdsim.game.MovementState;
import javafx.geometry.Point2D;

public class MovementUtils {
    public static Point2D getEnemyPosition(Enemy enemy,double time){
        MovementState movement = enemy.getMovementState();
        if(movement==null){
            return null;
        }
        if(time <= movement.getStartTime()){
            return movement.getFromPoint();
        }
        if(time >= movement.getEndTime()){
            return movement.getToPoint();
        }
        double ratio = (time-movement.getStartTime())/(movement.getEndTime()- movement.getStartTime());
        double x = movement.getFromPoint().getX() + ratio * (movement.getToPoint().getX() - movement.getFromPoint().getX());
        double y = movement.getFromPoint().getY() + ratio * (movement.getToPoint().getY() - movement.getFromPoint().getY());
        return new Point2D(x,y);
    }
}
