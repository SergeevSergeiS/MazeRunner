package ru.internet.sergeevss90.maze;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static Maze maze;
    public static boolean exit = false;

    public static void main(String[] args) throws IOException {
        while (!exit) {
            menu();
            int option = scanner.nextInt();
            if (option == 1) {
                maze = generate();
                if (maze != null) {
                    maze.draw();
                }

            } else if (option == 2) {
                maze = load();
            } else if (option == 3 && maze != null) {
                save();
            } else if (option == 4 && maze != null) {
                maze.draw();
            } else if (option == 5 && maze != null) {
                solve();
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Incorrect option. Please try again");
            }
            System.out.println();
        }
        scanner.close();
        System.out.println("Bye!");
    }

    public static void solve() {
        Solver solvedMaze = new Solver(maze);
        solvedMaze.draw();
    }

    private static void save() throws IOException {
        scanner.nextLine();
        System.out.println("Enter file name: ");
        maze.saveMaze(scanner.nextLine());
    }

    private static Maze load() {
        scanner.nextLine();
        System.out.println("Please, Enter file name");
        String filename = scanner.nextLine();
        try (FileReader fileReader = new FileReader(filename)) {
            maze = new Maze(fileReader);
        } catch (IOException e) {
            System.out.println("Cannot load the maze!!!");
        }
        return maze;
    }

    public static Maze generate() {
        System.out.println("Enter the size of a new maze");
        int size = scanner.nextInt();
        if (size <= 4) {
            System.out.println("ERROR: The maze most have a minimum size of 5.");
            return null;
        }
        return new Maze(size);
    }

    public static void menu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Generate a new maze");
        System.out.println("2. Load a maze");
        if (maze != null) {
            System.out.println("3. Save the maze");
            System.out.println("4. Display the maze");
            System.out.println("5. Find the escape");
        }
        System.out.println("0. Exit");
    }
}