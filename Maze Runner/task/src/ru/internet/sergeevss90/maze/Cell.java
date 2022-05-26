package ru.internet.sergeevss90.maze;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final int x;
    private final int y;
    private boolean visited;
    private final List<Cell> neighbors;
    private final boolean wall;
    private boolean inPath;

    public Cell(int x, int y, boolean wall) {
        this.x = x;
        this.y = y;
        this.wall = wall;
        this.visited = false;
        this.neighbors = new ArrayList<>();
        this.inPath = false;
    }

    public void addNeighbor(Cell otherCell) {
        if (!neighbors.contains(otherCell)) {
            neighbors.add(otherCell);
        }
        if (!otherCell.neighbors.contains(this)) {
            otherCell.neighbors.add(this);
        }
    }

    public void addNeighbor(List<Cell> neighbours) {
        neighbours.forEach(this::addNeighbor);
    }

    public boolean hasRigthCellNeighbor() {
        return neighbors.stream()
                .anyMatch(c -> c.getX() == this.x && c.getY() == this.y + 1);
    }

    public boolean hasLowerCellNeighbor() {
        return neighbors.stream()
                .anyMatch(c -> c.getX() == this.x + 1 && c.getY() == this.y);
    }

    public boolean hasUpperCellNeighbor() {
        return neighbors.stream()
                .anyMatch(c -> c.getX() == this.x - 1 && c.getY() == this.y);
    }

    public boolean hasLeftCellNeighbor() {
        return neighbors.stream()
                .anyMatch(c -> c.getX() == this.x && c.getY() == this.y - 1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isWall() {
        return wall;
    }

    public boolean isInPath() {
        return !inPath;
    }

    public void setInPath(boolean inPath) {
        this.inPath = inPath;
    }

    @Override
    public String toString() {
        return "Cell [x=" + x + ", y=" + y + ", visited=" + visited + ", wall=" + wall + ", inPath=" + inPath + "]";
    }
}