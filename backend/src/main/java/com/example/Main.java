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

        Player player1 = new Player("1");
        Player player2 = new Player("2");
        GodCard minotaur = new Minotaur();
        GodCard demeter = new Demeter();
        GodCard pan = new Pan();
        Game game = new Game(player1, player2);
    }
}
