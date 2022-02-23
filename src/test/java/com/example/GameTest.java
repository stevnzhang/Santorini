package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

public class GameTest {

    private Game game;
    private Cell[][] board;

    @Before
    public void setUp() {
        game = new Game(5, 5);
        board = game.getBoard();
        game.setPlayer1(new Player());
        game.setPlayer2(new Player());
    }

    @Test
    public void testInitialHeightsZero() {
        for (int row = 0; row < game.getNumRows(); row++) {
            for (int col = 0; col < game.getNumCols(); col++) {
                assertEquals(0, board[row][col].getLevels());
                assertFalse(board[row][col].occupancy());
                assertFalse(board[row][col].hasDome());
            }
        }
    }

    @Test
    public void testInitiatePlayer() throws InvalidMoveException {
        game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
        assertTrue(board[0][0].occupancy());
        assertTrue(board[2][2].occupancy());
    }

    @Test
    public void testInitiateOutOfBoundsNeg() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            game.initiatePlayer(-1, -1, 0, 0, game.getPlayer1());
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateOutOfBoundsPos() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            game.initiatePlayer(0, 0, 5, 5, game.getPlayer1());
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateSameSpot() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            game.initiatePlayer(0, 0, 0, 0, game.getPlayer1());
        });

        String expectedMessage = "Cannot initialize both workers in the same location!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateMove() throws InvalidMoveException, GameOverException, InvalidTurnException {
        game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
        Worker worker1 = game.getPlayer1().getWorker1();
        Worker worker2 = game.getPlayer1().getWorker2();
        game.initiateMove(1, 1, worker1, game.getPlayer1());
        assertTrue(board[1][1].occupancy());
        assertFalse(board[0][0].occupancy());
        game.initiateMove(2, 1, worker2, game.getPlayer1());
        assertTrue(board[2][1].occupancy());
        assertFalse(board[2][2].occupancy());
        game.initiateMove(2, 2, worker1, game.getPlayer1());
        assertTrue(board[2][2].occupancy());
        assertFalse(board[1][1].occupancy());
    }

    @Test
    public void testInitiateMoveOutOfBoundsNeg() throws GameOverException, InvalidMoveException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
            Worker worker1 = game.getPlayer1().getWorker1();
            game.initiateMove(-1, -1, worker1, game.getPlayer1());
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateMoveOutOfBoundsPos() throws GameOverException, InvalidMoveException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            game.initiatePlayer(0, 0, 4, 4, game.getPlayer1());
            Worker worker2 = game.getPlayer1().getWorker2();
            game.initiateMove(5, 5, worker2, game.getPlayer1());
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateMoveOnOccupied() throws InvalidMoveException, GameOverException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
            Worker worker1 = game.getPlayer1().getWorker1();
            Worker worker2 = game.getPlayer1().getWorker2();
            game.initiateMove(1, 1, worker1, game.getPlayer1());
            game.initiateMove(1, 1, worker2, game.getPlayer1());
        });

        String expectedMessage = "Location has a worker on it!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateMoveOnDome() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
            Worker worker1 = game.getPlayer1().getWorker1();
            board[1][1].setLevel(4);
            game.initiateMove(1, 1, worker1, game.getPlayer1());
        });

        String expectedMessage = "Location has a dome on it!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateTower() throws GameOverException, InvalidMoveException, InvalidTurnException {
        game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
        Worker worker1 = game.getPlayer1().getWorker1();
        game.initiateMove(0, 1, worker1, game.getPlayer1());
        game.initiateTower(1, 1, worker1);
        assertEquals(1, board[1][1].getLevels());
    }

    @Test
    public void testInitiateTowerOutOfBoundsNeg() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
            Worker worker1 = game.getPlayer1().getWorker1();
            game.initiateMove(0, 1, worker1, game.getPlayer1());
            game.initiateTower(-1, 0, worker1);
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testInitiateTowerOutOfBoundsPos() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            game.initiatePlayer(0, 0, 4, 3, game.getPlayer1());
            Worker worker2 = game.getPlayer1().getWorker2();
            game.initiateMove(3, 4, worker2, game.getPlayer1());
            game.initiateTower(4, 5, worker2);
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateTowerCreatesDome() throws InvalidMoveException, InvalidTurnException, GameOverException {
        game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
        Worker worker1 = game.getPlayer1().getWorker1();
        board[1][1].setLevel(3);
        game.initiateMove(0, 1, worker1, game.getPlayer1());
        game.initiateTower(1, 1, worker1);
        assertEquals(4, board[1][1].getLevels());
        assertTrue(board[1][1].hasDome());
    }

    @Test
    public void testInitiateTowerOnDome() throws InvalidMoveException, InvalidTurnException, GameOverException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
            Worker worker1 = game.getPlayer1().getWorker1();
            board[1][1].setLevel(4);
            game.initiateMove(0, 1, worker1, game.getPlayer1());
            game.initiateTower(1, 1, worker1);
        });

        String expectedMessage = "Location has a dome on it!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateTowerAndChangeTurns() throws InvalidMoveException, InvalidTurnException, GameOverException {
        game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
        game.initiatePlayer(1, 2, 1, 0, game.getPlayer2());
        Worker worker1 = game.getPlayer1().getWorker1();
        Worker worker2 = game.getPlayer1().getWorker2();
        Worker worker3 = game.getPlayer2().getWorker1();
        Worker worker4 = game.getPlayer2().getWorker2();
        game.initiateMove(0, 1, worker1, game.getPlayer1());
        game.initiateTower(1, 1, worker1);
        assertEquals(1, board[1][1].getLevels());
        game.initiateMove(2, 1, worker3, game.getPlayer2());
        game.initiateTower(1, 1, worker3);
        assertEquals(2, board[1][1].getLevels());
        game.initiateMove(1, 2, worker2, game.getPlayer1());
        game.initiateTower(1, 1, worker2);
        assertEquals(3, board[1][1].getLevels());
        game.initiateMove(2, 0, worker4, game.getPlayer2());
        game.initiateTower(1, 1, worker4);
        assertEquals(4, board[1][1].getLevels());
        assertTrue(board[1][1].hasDome());
    }

    @Test
    public void testAdjacentMoveWorker() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
            Worker worker1 = game.getPlayer1().getWorker1();
            game.initiateMove(4, 4, worker1, game.getPlayer1());
        });

        String expectedMessage = "Move has to be adjacent from current worker!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAdjacentPlaceTower() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            game.initiatePlayer(0, 0, 2, 2, game.getPlayer1());
            Worker worker1 = game.getPlayer1().getWorker1();
            game.initiateMove(1, 1, worker1, game.getPlayer1());
            game.initiateTower(4, 4, worker1);
        });

        String expectedMessage = "Build has to be adjacent from current worker!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

//    @Test
//    public void testGameOver() {
//        assertEquals();
//    }

}