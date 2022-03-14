package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class CellTest {

    private Game game;
    private Cell[][] board;

    @Before
    public void setUp() {
        game = new Game(5, 5);
        board = game.getBoard();
    }

    @Test
    public void testAddLevel() {
        assertEquals(0, board[2][2].getLevels());
        board[2][2].addLevel();
        assertEquals(1, board[2][2].getLevels());
        board[2][2].addLevel();
        assertEquals(2, board[2][2].getLevels());
        board[2][2].addLevel();
        assertEquals(3, board[2][2].getLevels());
        board[2][2].addLevel();
        assertEquals(4, board[2][2].getLevels());
        board[2][2].addLevel();
        // Doesn't add more to a tower with a dome
        assertEquals(4, board[2][2].getLevels());
    }

    @Test
    public void testOccupancy() {
        board[2][2].setOccupied();
        assert(board[2][2].occupancy());
        board[2][2].setUnoccupied();
        assert(!board[2][2].occupancy());
    }

    @Test
    public void testOccupancyEdgeValues() {
        board[0][0].setOccupied();
        board[4][4].setOccupied();
        assertTrue(board[0][0].occupancy());
        assertTrue(board[4][4].occupancy());
        board[0][0].setUnoccupied();
        board[4][4].setUnoccupied();
        assertFalse(board[0][0].occupancy());
        assertFalse(board[4][4].occupancy());
    }

    @Test
    public void testHasDome() {
        assertFalse(board[2][2].hasDome()); // Initially no dome
        board[2][2].setLevel(3);
        assertFalse(board[2][2].hasDome()); // No dome on winning condition height
        board[2][2].addLevel();
        assertTrue(board[2][2].hasDome()); // Dome on height of 4
    }
}