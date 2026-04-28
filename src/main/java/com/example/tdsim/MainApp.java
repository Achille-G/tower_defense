package com.example.tdsim;

import com.example.tdsim.engine.SimulationEngine;
import com.example.tdsim.engine.events.EnemySpawnEvent;
import com.example.tdsim.engine.events.PlayerPlaceTowerEvent;
import com.example.tdsim.game.Base;
import com.example.tdsim.game.BuildGrid;
import com.example.tdsim.game.GameState;
import com.example.tdsim.game.Path;
import com.example.tdsim.render.GameRenderer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

        engine.schedule(new EnemySpawnEvent(1.0, engine.nextSequenceNumber(), "E1"));
        scene.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                return;
            }

            Point2D click = new Point2D(mouseEvent.getX(), mouseEvent.getY());
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

    public static void main(String[] args) {
        launch();
    }
}