package com.example;

import java.io.IOException;

public final class Main {

    private Main() {
        // Disable instantiating this class.
        throw new UnsupportedOperationException();
    }

    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 5;

    public static void main(String[] args) throws IOException { // Should have CLI here
        System.out.println("Start");
//        Cell[][] board = new Cell[NUM_ROWS][NUM_COLS];
//        Worker[] workers1 = new Worker[2];
//        Worker[] workers2 = new Worker[2];
//        workers1[0] = new Worker(0, 0, 0);
//        workers1[1] = new Worker(1, 0, 0);
//        workers2[0] = new Worker(0, 1, 0);
//        workers2[1] = new Worker(1, 1, 0);
//        Player player1 = new Player(workers1);
//        Player player2 = new Player(workers2);
        GodCard game = new Game(Player player1, Player player2);
    }
}
