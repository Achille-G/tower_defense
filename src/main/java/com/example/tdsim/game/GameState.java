package com.example.tdsim.game;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameState {
    private final Map<String,Enemy> enemies;
    private final Path path;
    private final Base base;

    public GameState(Path path, Base base) {
        this.path = path;
        this.base = base;
        this.enemies = new LinkedHashMap<>();
    }

    public Map<String, Enemy> getEnemies() {
        return enemies;
    }

    public Path getPath() {
        return path;
    }

    public Base getBase() {
        return base;
    }
}
