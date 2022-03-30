package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class GodCardTest {

    private Game game;
    private Cell[][] board;

    @Before
    public void setUp() throws InvalidMoveException {
        game = new Game(new Player("1"), new Player("2"));
        game.setGodCard(new Minotaur(), this.game.getPlayer1());
        game.setGodCard(new Pan(), this.game.getPlayer2());
        board = game.getBoard();

        game.initiatePlayer(new Cell(0, 0), game.getPlayer1());
        game.initiatePlayer(new Cell(1, 1), game.getPlayer1());
        game.initiatePlayer(new Cell(0, 1), game.getPlayer2());
        game.initiatePlayer(new Cell(1, 0), game.getPlayer2());
    }

    @Test
    public void testPanAndMinotaurIntegration() throws InvalidMoveException, InvalidTurnException, GameOverException {
        Worker worker1 = game.getPlayer1().getWorker1();
        Worker worker2 = game.getPlayer1().getWorker2();
        Worker worker3 = game.getPlayer2().getWorker1();
        Worker worker4 = game.getPlayer2().getWorker2();
        game.initiateCardMove(new Cell(1, 2), worker2, game.getPlayer1());
        game.initiateCardTower(new Cell(1, 1), worker2, game.getPlayer1());
        game.setCurrentPlayer();

        game.initiateCardMove(new Cell(2, 0), worker4, game.getPlayer2());
        game.initiateCardTower(new Cell(1, 1), worker4, game.getPlayer2());
        game.setCurrentPlayer();

        game.initiateCardMove(new Cell(1, 3), worker2, game.getPlayer1());
        game.initiateCardTower(new Cell(1, 2), worker2, game.getPlayer1());
        game.setCurrentPlayer();

        game.initiateCardMove(new Cell(1, 2), worker3, game.getPlayer2());
        game.initiateCardTower(new Cell(1, 1), worker3, game.getPlayer2());
        game.setCurrentPlayer();

        // Pan player does not win by being forced onto a level three tower
        game.initiateCardMove(new Cell(1, 2), worker2, game.getPlayer1());
        game.initiateCardTower(new Cell(1, 3), worker2, game.getPlayer1());
        game.setCurrentPlayer();
        game.gameOverCard(game.getPlayer2());

        game.initiateCardMove(new Cell(3, 0), worker4, game.getPlayer2());
        game.initiateCardTower(new Cell(4, 0), worker4, game.getPlayer2());
        game.setCurrentPlayer();

        game.gameOverCard(game.getPlayer2());
        assertFalse(game.getGameOver());

        // Pan player does not win by being forced >= 2 levels down
        game.initiateCardMove(new Cell(1,3), worker2, game.getPlayer1());
        game.initiateCardTower(new Cell(1, 2), worker2, game.getPlayer1());
        game.setCurrentPlayer();

        game.initiateCardMove(new Cell(4, 0), worker4, game.getPlayer2());
        game.initiateCardTower(new Cell(3, 0), worker4, game.getPlayer2());
        game.setCurrentPlayer();

        game.initiateCardMove(new Cell(1, 2), worker2, game.getPlayer1());
        game.initiateCardTower(new Cell(0, 2), worker2, game.getPlayer1());
        game.setCurrentPlayer();

        game.initiateCardMove(new Cell(3, 0), worker4, game.getPlayer2());
        game.initiateCardTower(new Cell(4, 0), worker4, game.getPlayer2());
        game.setCurrentPlayer();

        game.initiateCardMove(new Cell(1, 1), worker2, game.getPlayer1());

        // Minotaur can win from forcing opponent out of a level 3 tower
        game.gameOverCard(game.getPlayer1());
        assertTrue(game.getGameOver());
    }

}