package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private Game game;
    private Cell[][] board;
    private Player player;

    @Before
    public void setUp() {
        game = new Game(5, 5);
        board = game.getBoard();
        player = new Player();
    }

    // Cannot check a lot of tests (like correct thrown exception) since this requires game calls + checks
    @Test
    public void testPlaceWorker() {
        player.placeWorker(0, 0, 2, 2, board);
        assertTrue(board[0][0].occupancy());
        assertTrue(board[2][2].occupancy());

        // Test other random spaces are not occupied by default
        assertFalse(board[2][3].occupancy());
        assertFalse(board[3][2].occupancy());
        assertFalse(board[4][4].occupancy());
    }

    @Test
    public void testPlaceWorkerDifferentHeights() {
        board[0][0].setLevel(1);
        board[0][0].setLevel(3);
        player.placeWorker(0, 0, 2, 2, board);
        assertTrue(board[0][0].occupancy());
        assertTrue(board[2][2].occupancy());
    }

    @Test
    public void testPlaceTower() {
        player.placeTower(0, 0, board);
        assertEquals(1, board[0][0].getLevels());

        player.placeTower(0, 0, board);
        assertEquals(2, board[0][0].getLevels());

        player.placeTower(0, 0, board);
        assertEquals(3, board[0][0].getLevels());

        player.placeTower(0, 0, board);
        assertEquals(4, board[0][0].getLevels());

        // Doesn't add more to a tower with a dome
        player.placeTower(0, 0, board);
        assertEquals(4, board[0][0].getLevels());
    }

}