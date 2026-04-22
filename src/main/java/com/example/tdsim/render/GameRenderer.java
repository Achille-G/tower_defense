package com.example.tdsim.render;

import com.example.tdsim.game.Enemy;
import com.example.tdsim.game.GameState;
import com.example.tdsim.logic.MovementUtils;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameRenderer {

    public void render(GraphicsContext gc, GameState gameState, double visibleTime) {
        drawBackground(gc);
        drawPath(gc, gameState);
        drawBase(gc, gameState);
        drawEnemies(gc, gameState, visibleTime);
    }

    private void drawBackground(GraphicsContext gc) {
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(0, 0, 800, 600);
    }

    private void drawPath(GraphicsContext gc, GameState gameState) {
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(20);

        for (int i = 0; i < gameState.getPath().size() - 1; i++) {
            Point2D p1 = gameState.getPath().getWaypoint(i);
            Point2D p2 = gameState.getPath().getWaypoint(i + 1);

            gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }
        Point2D lastWaypoint = gameState.getPath().getWaypoint(gameState.getPath().size() - 1);
        Point2D basePos = gameState.getBase().getPosition();

        gc.strokeLine(
                lastWaypoint.getX(),
                lastWaypoint.getY(),
                basePos.getX(),
                basePos.getY()
        );
    }

    private void drawBase(GraphicsContext gc, GameState gameState) {
        Point2D pos = gameState.getBase().getPosition();

        gc.setFill(Color.BROWN);
        gc.fillRect(pos.getX() - 15, pos.getY() - 15, 30, 30);
    }

    private void drawEnemies(GraphicsContext gc, GameState gameState, double visibleTime) {
        for (Enemy enemy : gameState.getEnemies().values()) {

            if (!enemy.isAlive()) {
                continue;
            }

            Point2D pos = MovementUtils.getEnemyPosition(enemy, visibleTime);

            if (pos == null) {
                continue;
            }

            // Dessin du cercle (ennemi)
            gc.setFill(Color.RED);
            gc.fillOval(pos.getX() - 10, pos.getY() - 10, 20, 20);

            // Barre de vie
            double hpRatio = enemy.getHp() / enemy.getMaxHp();

            gc.setFill(Color.BLACK);
            gc.fillRect(pos.getX() - 10, pos.getY() - 20, 20, 4);

            gc.setFill(Color.LIMEGREEN);
            gc.fillRect(pos.getX() - 10, pos.getY() - 20, 20 * hpRatio, 4);
        }
    }
}