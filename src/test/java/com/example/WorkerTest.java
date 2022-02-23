package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class WorkerTest {

    private Game game;
    private Cell[][] board;
    private Player player;

    @Before
    public void setUp() {
        game = new Game(5, 5);
        board = game.getBoard();
        player = new Player();
    }

    @Test
    public void testMoveWorker() {
        player.placeWorker(0, 0, 2, 2, board);
        Worker worker1 = player.getWorker1();
        assertTrue(board[0][0].occupancy());
        assertFalse(board[1][1].occupancy());
        worker1.moveWorker(1, 1, board);
        assertFalse(board[0][0].occupancy());
        assertTrue(board[1][1].occupancy());
    }

    @Test
    public void testMoveBothWorkers() {
        player.placeWorker(0, 0, 2, 2, board);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        assertTrue(board[0][0].occupancy());
        assertFalse(board[4][4].occupancy());
        worker1.moveWorker(4, 4, board);
        assertFalse(board[0][0].occupancy());
        assertTrue(board[4][4].occupancy());

        assertTrue(board[2][2].occupancy());
        assertFalse(board[3][4].occupancy());
        worker2.moveWorker(3, 4, board);
        assertFalse(board[2][2].occupancy());
        assertTrue(board[3][4].occupancy());
    }
}