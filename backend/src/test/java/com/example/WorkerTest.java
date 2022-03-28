package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class WorkerTest {

    private Game game;
    private Cell[][] board;
    private Player player;

    @Before
    public void setUp() {
        game = new Game(new Player("1"), new Player("2"));
        game.setGodCard(new NoCard(), this.game.getPlayer1());
        game.setGodCard(new NoCard(), this.game.getPlayer2());
        board = game.getBoard();
        player = game.getPlayer1();
    }

    @Test
    public void testMoveWorker() {
        game.placeWorker(0, 0, player);
        game.placeWorker(2, 2, player);
        Worker worker1 = player.getWorker1();
        assertTrue(board[0][0].occupancy());
        assertFalse(board[1][1].occupancy());
        worker1.moveWorker(1, 1, board);
        assertFalse(board[0][0].occupancy());
        assertTrue(board[1][1].occupancy());
    }

    @Test
    public void testMoveBothWorkers() {
        game.placeWorker(0, 0, player);
        game.placeWorker(2, 2, player);
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

    @Test
    public void testPlaceTower() {
        game.placeWorker(0, 0, player);
        game.placeWorker(2, 2, player);
        Worker worker1 = player.getWorker1();

        worker1.placeTower(0, 0, board);
        assertEquals(1, board[0][0].getLevels());

        worker1.placeTower(0, 0, board);
        assertEquals(2, board[0][0].getLevels());

        worker1.placeTower(0, 0, board);
        assertEquals(3, board[0][0].getLevels());

        worker1.placeTower(0, 0, board);
        assertEquals(4, board[0][0].getLevels());

        // Doesn't add more to a tower with a dome
        worker1.placeTower(0, 0, board);
        assertEquals(4, board[0][0].getLevels());
    }
}