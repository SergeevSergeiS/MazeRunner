package ru.internet.sergeevss90.maze;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Solver {
    private final Maze maze;
    private final int height;
    private final int width;
    private final String[][] solvedGrid;
    private final Cell[][] createdCells;
    private static final String PATH = "//";

    public Solver(Maze maze) {
        this.maze = maze;
        height = maze.getHeight();
        width = maze.getWidth();
        solvedGrid = Arrays.stream(maze.getMazeGrid())
                .map(String[]::clone)
                .toArray(String[][]::new);
        createdCells = new Cell[height][width];
        initCells();
        solveMaze();
    }

    public void draw() {
        for (String[] line : solvedGrid) {
            for (String symbol : line) {
                System.out.print(symbol);
            }
            System.out.println();
        }
    }

    private void solveMaze() {
        int exit = findExitPoint();
        Deque<Cell> pathFound = new ArrayDeque<>();
        pathFound.offerLast(createdCells[findStartPoint()][0]);
        while (true) {
            Cell selected = pathFound.peekLast();
            if (selected != null && selected.getX() == exit && selected.getY() == width - 1) {
                break;
            }
            Cell next = null;
            if (selected != null) {
                next = getValidNeighbour(selected);
            }
            if (next != null) {
                next.setInPath(true);
                next.setVisited(true);
                pathFound.offerLast(next);
            } else {
                if (selected != null) {
                    selected.setInPath(false);
                }
                pathFound.pollLast();
            }
            if (pathFound.isEmpty()) {
                System.out.println("There is no way out of this maze");
                return;
            }
        }
        drawPath(pathFound);
    }

    private void drawPath(Deque<Cell> pathFound) {
        while (!pathFound.isEmpty()) {
            Cell pathCell = pathFound.pollLast();
            solvedGrid[pathCell.getX()][pathCell.getY()] = PATH;
        }
    }

    private int findStartPoint() {
        for (int i = 0; i < height; i++) {
            if (solvedGrid[i][0].equals("  ")) {
                return i;
            }
        }
        return 0;
    }

    private int findExitPoint() {
        for (int i = 0; i < width; i++) {
            if (solvedGrid[i][width - 1].equals("  ")) {
                return i;
            }
        }
        return 0;
    }


    private void initCells() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (solvedGrid[i][j].equals("  ")) {
                    createdCells[i][j] = new Cell(i, j, false);
                } else {
                    createdCells[i][j] = new Cell(i, j, true);
                }
            }
        }
        populateCellsNeighbour();
    }

    private void populateCellsNeighbour() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = createdCells[i][j];
                if (cell.isWall()) {
                    continue;
                }
                List<Cell> neighbours = new ArrayList<>(Arrays.asList(
                        getCell(cell.getX() + 1, cell.getY()),
                        getCell(cell.getX() - 1, cell.getY()),
                        getCell(cell.getX(), cell.getY() + 1),
                        getCell(cell.getX(), cell.getY() - 1)
                ));
                neighbours.removeIf(n -> (n == null || n.isVisited() || n.isWall()));

                if (!neighbours.isEmpty()) {
                    cell.addNeighbor(neighbours);
                }
            }
        }
    }

    private Cell getCell(int x, int y) {
        try {
            return createdCells[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private Cell getValidNeighbour(Cell selected) {
        if (selected.hasRigthCellNeighbor()) {
            Cell next = getCell(selected.getX(), selected.getY() + 1);
            if (next != null && !next.isVisited() && next.isInPath()) {
                return next;
            }
        }

        if (selected.hasLowerCellNeighbor()) {
            Cell next = getCell(selected.getX() + 1, selected.getY());
            if (next != null && !next.isVisited() && next.isInPath()) {
                return next;
            }
        }
        if (selected.hasUpperCellNeighbor()) {
            Cell next = getCell(selected.getX() - 1, selected.getY());

            if (next != null && !next.isVisited() && next.isInPath()) {
                return next;
            }
        }
        if (selected.hasLeftCellNeighbor()) {
            Cell next = getCell(selected.getX(), selected.getY() - 1);
            assert next != null;
            if (!next.isVisited() && next.isInPath()) {
                return next;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "MazeSolver [maze=" + maze + ", height=" + height + ", widht=" + width + "]";
    }
}