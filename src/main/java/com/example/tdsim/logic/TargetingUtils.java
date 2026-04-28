package com.example.tdsim.logic;

import com.example.tdsim.game.Enemy;
import com.example.tdsim.game.GameState;
import com.example.tdsim.game.Tower;
import javafx.geometry.Point2D;

public class TargetingUtils {
    public static Enemy chooseTarget(Tower tower, GameState gameState, double time){
        for (Enemy enemy : gameState.getEnemies().values()){
            if (enemy.isAlive() || !enemy.isDying()){
                Point2D enemyPosition = MovementUtils.getEnemyPosition(enemy,time);
                if(enemyPosition != null){
                    double distance = tower.getPosition().distance(enemyPosition);
                    if(distance <= tower.getRange()){
                        return enemy;
                    }
                }
            }

        }
        return null;
    }
}
