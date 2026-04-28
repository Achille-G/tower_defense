package com.example.tdsim.render;

import com.example.tdsim.game.Base;
import com.example.tdsim.game.BuildGrid;
import com.example.tdsim.game.Enemy;
import com.example.tdsim.game.GameState;
import com.example.tdsim.game.Tower;
import com.example.tdsim.logic.MovementUtils;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameRenderer {

    private static final double FIRE_FLASH_DURATION = 0.1;

    public void render(GraphicsContext gc, GameState gameState, double visibleTime) {
        drawBackground(gc);
        drawPath(gc, gameState);
        drawBuildableCells(gc, gameState);
        drawBase(gc, gameState);
        drawTowers(gc, gameState);
        drawEnemies(gc, gameState, visibleTime);
        drawFireFlashes(gc, gameState, visibleTime);
    }

    private void drawBuildableCells(GraphicsContext gc, GameState gameState) {
        BuildGrid grid = gameState.getBuildGrid();
        double cell = grid.getCellSize();
        gc.setFill(Color.color(0.4, 1.0, 0.4, 0.25));
        gc.setStroke(Color.color(0.4, 1.0, 0.4, 0.5));
        gc.setLineWidth(1);
        for (int col = 0; col < grid.getCols(); col++) {
            for (int row = 0; row < grid.getRows(); row++) {
                if (!grid.isBuildable(col, row)) {
                    continue;
                }
                double x = col * cell;
                double y = row * cell;
                gc.fillRect(x, y, cell, cell);
                gc.strokeRect(x, y, cell, cell);
            }
        }
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
        Base base = gameState.getBase();
        Point2D pos = base.getPosition();

        gc.setFill(Color.BROWN);
        gc.fillRect(pos.getX() - 15, pos.getY() - 15, 30, 30);

        double hpRatio = Math.max(0.0, base.getHp() / base.getMaxHp());

        gc.setFill(Color.BLACK);
        gc.fillRect(pos.getX() - 15, pos.getY() - 25, 30, 5);

        gc.setFill(Color.RED);
        gc.fillRect(pos.getX() - 15, pos.getY() - 25, 30 * hpRatio, 5);
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

            gc.setFill(Color.RED);
            gc.fillRect(pos.getX() - 10, pos.getY() - 20, 20 * hpRatio, 4);
        }
    }

    private void drawFireFlashes(GraphicsContext gc, GameState gameState, double visibleTime) {
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(2);
        for (Tower tower : gameState.getTowers().values()) {
            Point2D target = tower.getLastFireTarget();
            if (target == null) {
                continue;
            }
            if (visibleTime - tower.getLastFireTime() > FIRE_FLASH_DURATION) {
                continue;
            }
            Point2D from = tower.getPosition();
            gc.strokeLine(from.getX(), from.getY(), target.getX(), target.getY());
        }
    }

    private void drawTowers(GraphicsContext gc, GameState gameState) {
        for (Tower tower : gameState.getTowers().values()) {
            if (!tower.isActive()) {
                continue;
            }

            Point2D pos = tower.getPosition();

            // Range preview
            gc.setStroke(Color.LIGHTBLUE);
            gc.setLineWidth(1);
            gc.strokeOval(
                    pos.getX() - tower.getRange(),
                    pos.getY() - tower.getRange(),
                    tower.getRange() * 2,
                    tower.getRange() * 2
            );

            // Tower body
            gc.setFill(Color.BLUE);
            gc.fillRect(pos.getX() - 12, pos.getY() - 12, 24, 24);
        }
    }
}