package com.example.tdsim;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Tower Defense Simulator");

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, 800, 600);

                gc.setFill(Color.GREEN);
                gc.fillRect(0, 0, 800, 600);

                gc.setFill(Color.RED);
                gc.fillOval(100, 100, 20, 20);
            }
        }.start();
    }

    public static void main(String[] args) {
        launch();
    }
}