package com.example.tdsim;

import com.example.tdsim.engine.SimulationEngine;
import com.example.tdsim.engine.events.EnemySpawnEvent;
import com.example.tdsim.engine.events.PlayerPlaceTowerEvent;
import com.example.tdsim.engine.events.PlayerSellTowerEvent;
import com.example.tdsim.game.Base;
import com.example.tdsim.game.BuildGrid;
import com.example.tdsim.game.GameState;
import com.example.tdsim.game.Path;
import com.example.tdsim.game.Tower;
import com.example.tdsim.render.GameRenderer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class MainApp extends Application {

    private double visibleTime = 0.0;
    private long lastTime = 0L;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Tower Defense Simulator");

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        Path path = new Path(List.of(
                new Point2D(100, 100),
                new Point2D(300, 100),
                new Point2D(300, 300),
                new Point2D(600, 300)
        ));

        Base base = new Base(new Point2D(700, 300), 5.0);
        BuildGrid buildGrid = new BuildGrid(canvas.getWidth(), canvas.getHeight(), path, base);
        GameState gameState = new GameState(path, base, buildGrid);

        SimulationEngine engine = new SimulationEngine();
        GameRenderer renderer = new GameRenderer();

        final int waveSize = 8;
        final double firstSpawnTime = 1.0;
        final double spawnInterval = 2.0;

        for (int i = 0; i < waveSize; i++) {
            engine.schedule(new EnemySpawnEvent(
                    firstSpawnTime + i * spawnInterval,
                    engine.nextSequenceNumber(),
                    "E" + (i + 1)
            ));
        }

        ContextMenu towerMenu = new ContextMenu();
        MenuItem sellItem = new MenuItem("Vendre");
        towerMenu.getItems().add(sellItem);

        final Tower[] selectedTower = new Tower[1];

        sellItem.setOnAction(actionEvent -> {
            Tower tower = selectedTower[0];

            if (tower == null || !tower.isActive()) {
                return;
            }

            engine.schedule(new PlayerSellTowerEvent(
                    visibleTime,
                    engine.nextSequenceNumber(),
                    tower.getId()
            ));

            System.out.printf("[t=%.2f] player requested sell tower %s%n",
                    visibleTime,
                    tower.getId());

            selectedTower[0] = null;
        });

        scene.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                return;
            }

            Point2D click = new Point2D(mouseEvent.getX(), mouseEvent.getY());

            Tower clickedTower = findTowerAt(gameState, click);
            if (clickedTower != null) {
                selectedTower[0] = clickedTower;

                towerMenu.show(
                        stage,
                        mouseEvent.getScreenX(),
                        mouseEvent.getScreenY()
                );

                return;
            }

            towerMenu.hide();
            selectedTower[0] = null;

            if (!gameState.getBuildGrid().isBuildableAt(click)) {
                System.out.printf("[t=%.2f] click rejected (not buildable) at (%.1f, %.1f)%n",
                        visibleTime, click.getX(), click.getY());
                return;
            }

            String towerId = "T" + (gameState.getTowers().size() + 1);
            Point2D position = gameState.getBuildGrid().snapToCenter(click);

            engine.schedule(new PlayerPlaceTowerEvent(
                    visibleTime,
                    engine.nextSequenceNumber(),
                    towerId,
                    position
            ));

            System.out.printf("[t=%.2f] player requested tower %s at (%.1f, %.1f)%n",
                    visibleTime,
                    towerId,
                    position.getX(),
                    position.getY());
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0L) {
                    lastTime = now;
                    return;
                }

                double deltaSeconds = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                visibleTime += deltaSeconds;

                engine.runUntil(visibleTime, gameState);
                renderer.render(gc, gameState, visibleTime);
            }
        }.start();
    }

    private Tower findTowerAt(GameState gameState, Point2D clickPosition) {
        for (Tower tower : gameState.getTowers().values()) {
            if (!tower.isActive()) {
                continue;
            }

            if (tower.getPosition().distance(clickPosition) <= 20.0) {
                return tower;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        launch();
    }
}