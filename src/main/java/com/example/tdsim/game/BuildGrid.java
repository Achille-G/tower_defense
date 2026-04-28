package com.example.tdsim.game;

import javafx.geometry.Point2D;

import java.util.HashSet;
import java.util.Set;

public class BuildGrid {

    private static final double CELL_SIZE = 40.0;
    private static final double PATH_HALF_WIDTH = 10.0;
    private static final double MIN_DISTANCE_FROM_PATH = PATH_HALF_WIDTH + 5.0;
    private static final double MAX_DISTANCE_FROM_PATH = PATH_HALF_WIDTH + CELL_SIZE + 5.0;
    private static final double MIN_DISTANCE_FROM_BASE = CELL_SIZE;

    private final int cols;
    private final int rows;
    private final Set<Long> buildableCells;

    public BuildGrid(double width, double height, Path path, Base base) {
        this.cols = (int) Math.floor(width / CELL_SIZE);
        this.rows = (int) Math.floor(height / CELL_SIZE);
        this.buildableCells = computeBuildableCells(path, base);
    }

    public double getCellSize() {
        return CELL_SIZE;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public boolean isBuildable(int col, int row) {
        return buildableCells.contains(key(col, row));
    }

    public boolean isBuildableAt(Point2D point) {
        return isBuildable(colOf(point.getX()), rowOf(point.getY()));
    }

    public Point2D snapToCenter(Point2D point) {
        int col = colOf(point.getX());
        int row = rowOf(point.getY());
        return centerOf(col, row);
    }

    public Point2D centerOf(int col, int row) {
        return new Point2D(col * CELL_SIZE + CELL_SIZE / 2.0, row * CELL_SIZE + CELL_SIZE / 2.0);
    }

    private int colOf(double x) {
        return (int) Math.floor(x / CELL_SIZE);
    }

    private int rowOf(double y) {
        return (int) Math.floor(y / CELL_SIZE);
    }

    private Set<Long> computeBuildableCells(Path path, Base base) {
        Set<Long> cells = new HashSet<>();
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                Point2D center = centerOf(col, row);
                double pathDist = distanceToPath(center, path, base);
                double baseDist = center.distance(base.getPosition());
                if (pathDist >= MIN_DISTANCE_FROM_PATH
                        && pathDist <= MAX_DISTANCE_FROM_PATH
                        && baseDist >= MIN_DISTANCE_FROM_BASE) {
                    cells.add(key(col, row));
                }
            }
        }
        return cells;
    }

    private static double distanceToPath(Point2D point, Path path, Base base) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < path.size() - 1; i++) {
            min = Math.min(min, pointToSegmentDistance(point, path.getWaypoint(i), path.getWaypoint(i + 1)));
        }
        min = Math.min(min, pointToSegmentDistance(point, path.getWaypoint(path.size() - 1), base.getPosition()));
        return min;
    }

    private static double pointToSegmentDistance(Point2D p, Point2D a, Point2D b) {
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();
        double lengthSquared = dx * dx + dy * dy;
        if (lengthSquared == 0.0) {
            return p.distance(a);
        }
        double t = ((p.getX() - a.getX()) * dx + (p.getY() - a.getY()) * dy) / lengthSquared;
        t = Math.max(0.0, Math.min(1.0, t));
        return p.distance(new Point2D(a.getX() + t * dx, a.getY() + t * dy));
    }

    private static long key(int col, int row) {
        return (((long) col) << 32) | (row & 0xffffffffL);
    }
}
