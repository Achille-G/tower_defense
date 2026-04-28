package com.example.tdsim.game;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameState {
    private final Map<String,Enemy> enemies;
    private final Path path;
    private final Base base;
    private final Map<String,Tower> towers;
    private final BuildGrid buildGrid;

    public GameState(Path path, Base base, BuildGrid buildGrid) {
        this.path = path;
        this.base = base;
        this.buildGrid = buildGrid;
        this.enemies = new LinkedHashMap<>();
        this.towers = new LinkedHashMap<>();
    }

    public BuildGrid getBuildGrid() {
        return buildGrid;
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

    public Map<String, Tower> getTowers() {
        return towers;
    }

}
