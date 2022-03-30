package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

public class DemeterTest {

    private Game game;
    private Cell[][] board;

    @Before
    public void setUp() throws InvalidMoveException {
        game = new Game(new Player("1"), new Player("2"));
        game.setGodCard(new Demeter(), this.game.getPlayer1());
        game.setGodCard(new NoCard(), this.game.getPlayer2());
        board = game.getBoard();

        game.initiatePlayer(new Cell(0, 0), game.getPlayer1());
        game.initiatePlayer(new Cell(1, 1), game.getPlayer1());
        game.initiatePlayer(new Cell(0, 1), game.getPlayer2());
        game.initiatePlayer(new Cell(1, 0), game.getPlayer2());
    }

    @Test
    public void testDemeterWorks() throws InvalidMoveException, InvalidTurnException, GameOverException {
        Worker worker2 = game.getPlayer1().getWorker2(); // (1, 1)

        game.initiateCardMove(new Cell(0, 2), worker2, game.getPlayer1());
        game.initiateCardTower(new Cell(0, 3), worker2, game.getPlayer1());
        assertEquals("build", game.getState());
        game.setState("second build");
        game.initiateCardTower(new Cell(1, 3), worker2, game.getPlayer1());
        game.setCurrentPlayer();
        assertEquals("build", game.getState());
        assertEquals(game.getPlayer2(), game.getCurrentPlayer());
        assertEquals(1, board[0][3].getLevels());
        assertEquals(1, board[1][3].getLevels());
    }

    @Test
    public void testCannotPlaceOnJustPlacedTower() throws InvalidMoveException, InvalidTurnException, GameOverException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            Worker worker2 = game.getPlayer1().getWorker2(); // (1, 1)

            game.initiateCardMove(new Cell(0, 2), worker2, game.getPlayer1());
            game.initiateCardTower(new Cell(0, 3), worker2, game.getPlayer1());
            game.setState("second build");
            game.initiateCardTower(new Cell(0, 3), worker2, game.getPlayer1());
        });

        String expectedMessage = "Cannot place tower on first placed tower!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}