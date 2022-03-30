package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class PanTest {

    private Game game;
    private Cell[][] board;

    @Before
    public void setUp() throws InvalidMoveException {
        game = new Game(new Player("1"), new Player("2"));
        game.setGodCard(new Pan(), this.game.getPlayer1());
        game.setGodCard(new NoCard(), this.game.getPlayer2());
        board = game.getBoard();

        game.initiatePlayer(new Cell(0, 0), game.getPlayer1());
        game.initiatePlayer(new Cell(1, 1), game.getPlayer1());
        game.initiatePlayer(new Cell(0, 1), game.getPlayer2());
        game.initiatePlayer(new Cell(1, 0), game.getPlayer2());
    }

    @Test
    public void testWinByDefault() throws InvalidTurnException, GameOverException, InvalidMoveException {
        Worker worker2 = game.getPlayer1().getWorker2();
        board[2][0].setLevel(1);
        board[2][1].setLevel(2);
        board[2][2].setLevel(3);
        game.initiateCardMove(new Cell(2, 0), worker2, game.getPlayer1());
        game.initiateCardMove(new Cell(2, 1), worker2, game.getPlayer1());
        game.initiateCardMove(new Cell(2, 2), worker2, game.getPlayer1());
        game.gameOverCard(game.getPlayer1());
        assertTrue(game.getGameOver());
    }

    @Test
    public void testWinByPower() throws InvalidTurnException, GameOverException, InvalidMoveException {
        Worker worker2 = game.getPlayer1().getWorker2();
        board[2][0].setLevel(1);
        board[2][1].setLevel(2);
        game.initiateCardMove(new Cell(2, 0), worker2, game.getPlayer1());
        game.initiateCardMove(new Cell(2, 1), worker2, game.getPlayer1());
        game.initiateCardMove(new Cell(2, 2), worker2, game.getPlayer1());
        game.gameOverCard(game.getPlayer1());
        assertTrue(game.getGameOver());
    }

    @Test
    public void testDoesntWinByDroppingOneLevel() throws InvalidTurnException, GameOverException, InvalidMoveException {
        Worker worker2 = game.getPlayer1().getWorker2();
        board[2][0].setLevel(1);
        game.initiateCardMove(new Cell(2, 0), worker2, game.getPlayer1());
        game.initiateCardMove(new Cell(2, 1), worker2, game.getPlayer1());
        game.gameOverCard(game.getPlayer1());
        assertFalse(game.getGameOver());
    }

}
