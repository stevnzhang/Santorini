package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

public class MinotaurTest {

    private Game game;
    private Cell[][] board;

    @Before
    public void setUp() throws InvalidMoveException {
        game = new Game(new Player("1"), new Player("2"));
        game.setGodCard(new Minotaur(), this.game.getPlayer1());
        game.setGodCard(new NoCard(), this.game.getPlayer2());
        board = game.getBoard();

        game.initiatePlayer(new Cell(0, 0), game.getPlayer1());
        game.initiatePlayer(new Cell(1, 1), game.getPlayer1());
        game.initiatePlayer(new Cell(0, 1), game.getPlayer2());
        game.initiatePlayer(new Cell(1, 0), game.getPlayer2());
    }

    @Test
    public void testCannotPushOntoDome() throws InvalidMoveException, InvalidTurnException, GameOverException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            Worker worker1 = game.getPlayer1().getWorker1();
            board[0][2].setLevel(3);
            board[0][2].addLevel();
            game.initiateCardMove(new Cell(0, 1), worker1, game.getPlayer1());
        });

        String expectedMessage = "Location has a dome on it!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCannotPushOutOfBounds() throws InvalidMoveException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            Worker worker2 = game.getPlayer1().getWorker2();
            game.initiateCardMove(new Cell(0, 1), worker2, game.getPlayer1());
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCannotPushOntoAnotherPlayer() throws InvalidMoveException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            Worker worker1 = game.getPlayer1().getWorker1(); // (0, 0)
            Worker worker2 = game.getPlayer1().getWorker2(); // (1, 1)
            game.initiateCardMove(new Cell(1, 1), worker1, game.getPlayer1());
        });

        String expectedMessage = "Cannot push your own worker!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCannotPushOwnPlayer() throws InvalidMoveException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            Worker worker1 = game.getPlayer1().getWorker1();
            Worker worker2 = game.getPlayer1().getWorker2();
            Worker worker3 = game.getPlayer2().getWorker1(); // (0, 1)
            Worker worker4 = game.getPlayer2().getWorker2(); // (1, 0)
            game.initiateCardMove(new Cell(0, 2), worker2, game.getPlayer1());
            game.initiateCardTower(new Cell(0, 3), worker2, game.getPlayer1());
            game.setCurrentPlayer(); // Change turns

            game.initiateCardMove(new Cell(2, 0), worker4, game.getPlayer2());
            game.initiateCardTower(new Cell(3, 0), worker4, game.getPlayer2());
            game.setCurrentPlayer();

            game.initiateCardMove(new Cell(0, 1), worker1, game.getPlayer1());
        });

        String expectedMessage = "Location has a worker on it!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCanPushOntoHigherTower() throws InvalidMoveException, InvalidTurnException, GameOverException {
        Worker worker1 = game.getPlayer1().getWorker1();
        Worker worker4 = game.getPlayer2().getWorker2(); // (1, 0)
        board[2][0].setLevel(2);
        game.initiateCardMove(new Cell(1, 0), worker1, game.getPlayer1());

        // Worker is moved
        assertEquals(1, worker1.getRow());
        assertEquals(0, worker1.getCol());
        assertEquals(0, worker1.getHeight());

        // Opponent is moved
        assertEquals(2, worker4.getRow());
        assertEquals(0, worker4.getCol());
        assertEquals(2, worker4.getHeight());
    }

    @Test
    public void testForcedOpponentDoesNotWin() throws InvalidMoveException, InvalidTurnException, GameOverException {
        Worker worker1 = game.getPlayer1().getWorker1();
        Worker worker4 = game.getPlayer2().getWorker2(); // (1, 0)
        board[2][0].setLevel(3);
        game.initiateCardMove(new Cell(1, 0), worker1, game.getPlayer1());

        // Worker is moved
        assertEquals(1, worker1.getRow());
        assertEquals(0, worker1.getCol());
        assertEquals(0, worker1.getHeight());

        // Opponent is moved
        assertEquals(2, worker4.getRow());
        assertEquals(0, worker4.getCol());
        assertEquals(3, worker4.getHeight());

        game.gameOverCard(game.getPlayer2());
        assertFalse(game.getGameOver());
    }

}