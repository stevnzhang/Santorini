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

    private void moveWorkerHelper(int row, int col, Worker worker, Cell[][] board) {
        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        // Make Move
        board[originalRow][originalCol].setWorker(null);
        board[originalRow][originalCol].setUnoccupied();
        board[row][col].setWorker(worker);
        board[row][col].setOccupied();
        //Update Worker
        worker.setRow(row);
        worker.setCol(col);
        worker.setHeight(board[row][col].getLevels());
    }

    @Test
    public void testMoveWorker() {
        game.placeWorker(0, 0, player);
        game.placeWorker(2, 2, player);
        Worker worker1 = player.getWorker1();
        assertTrue(board[0][0].occupancy());
        assertFalse(board[1][1].occupancy());
        moveWorkerHelper(1, 1, worker1, board);
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
        moveWorkerHelper(4, 4, worker1, board);
        assertFalse(board[0][0].occupancy());
        assertTrue(board[4][4].occupancy());

        assertTrue(board[2][2].occupancy());
        assertFalse(board[3][4].occupancy());
        moveWorkerHelper(3, 4, worker2, board);
        assertFalse(board[2][2].occupancy());
        assertTrue(board[3][4].occupancy());
    }

}