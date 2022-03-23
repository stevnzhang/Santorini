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
    private Cell[] positions0022;
    private GodCard godCard;

    @Before
    public void setUp() {
        game = new Game(new Player(), new NoCard(), new Player(), new NoCard());
        board = game.getBoard();
        positions0022 = new Cell[2];
        positions0022[0] = new Cell(0, 0);
        positions0022[1] = new Cell(2, 2);
        godCard = new NoCard();
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
        godCard.initiatePlayer(positions0022, game.getPlayer1(), board);
        assertTrue(board[0][0].occupancy());
        assertTrue(board[2][2].occupancy());
    }

    @Test
    public void testInitiateOutOfBoundsNeg() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            Cell[] positions = new Cell[2];
            positions[0] = new Cell(-1, -1);
            positions[1] = new Cell(0, 0);
            godCard.initiatePlayer(positions, game.getPlayer1(), board);
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateOutOfBoundsPos() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            Cell[] positions = new Cell[2];
            positions[0] = new Cell(0, 0);
            positions[1] = new Cell(5, 5);
            godCard.initiatePlayer(positions, game.getPlayer1(), board);
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateSameSpot() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            Cell[] positions = new Cell[2];
            positions[0] = new Cell(0, 0);
            positions[1] = new Cell(0, 0);
            godCard.initiatePlayer(positions, game.getPlayer1(), board);
        });

        String expectedMessage = "Cannot initialize both workers in the same location!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateMove() throws InvalidMoveException, GameOverException, InvalidTurnException {
        godCard.initiatePlayer(positions0022, game.getPlayer1(), board);
        Worker worker1 = game.getPlayer1().getWorker1();
        Worker worker2 = game.getPlayer1().getWorker2();
        game.initiateCardMove(new Cell(1, 1), worker1, game.getPlayer1());
        assertTrue(board[1][1].occupancy());
        assertFalse(board[0][0].occupancy());
        game.initiateCardMove(new Cell(2, 1), worker2, game.getPlayer1());
        assertTrue(board[2][1].occupancy());
        assertFalse(board[2][2].occupancy());
        game.initiateCardMove(new Cell(2, 2), worker1, game.getPlayer1());
        assertTrue(board[2][2].occupancy());
        assertFalse(board[1][1].occupancy());
    }

    @Test
    public void testInitiateMoveOutOfBoundsNeg() throws GameOverException, InvalidMoveException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            godCard.initiatePlayer(positions0022, game.getPlayer1(), board);
            Worker worker1 = game.getPlayer1().getWorker1();
            game.initiateCardMove(new Cell(-1, -1), worker1, game.getPlayer1());
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateMoveOutOfBoundsPos() throws GameOverException, InvalidMoveException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            Cell[] positions = new Cell[2];
            positions[0] = new Cell(0, 0);
            positions[1] = new Cell(4, 4);
            godCard.initiatePlayer(positions, game.getPlayer1(), board);
            Worker worker2 = game.getPlayer1().getWorker2();
            game.initiateCardMove(new Cell(5, 5), worker2, game.getPlayer1());
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateMoveOnOccupied() throws InvalidMoveException, GameOverException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            godCard.initiatePlayer(positions0022, game.getPlayer1(), board);
            Worker worker1 = game.getPlayer1().getWorker1();
            Worker worker2 = game.getPlayer1().getWorker2();
            game.initiateCardMove(new Cell(1, 1), worker1, game.getPlayer1());
            game.initiateCardMove(new Cell(1, 1), worker2, game.getPlayer1());
        });

        String expectedMessage = "Location has a worker on it!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInitiateMoveOnDome() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            godCard.initiatePlayer(positions0022, game.getPlayer1(), board);
            Worker worker1 = game.getPlayer1().getWorker1();
            board[1][1].setLevel(4);
            game.initiateCardMove(new Cell(1, 1), worker1, game.getPlayer1());
        });

        String expectedMessage = "Location has a dome on it!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testinitiateCardTower() throws GameOverException, InvalidMoveException, InvalidTurnException {
        godCard.initiatePlayer(positions0022, game.getPlayer1(), board);
        Worker worker1 = game.getPlayer1().getWorker1();
        game.initiateCardMove(new Cell(0, 1), worker1, game.getPlayer1());
        game.initiateCardTower(new Cell(1, 1), worker1, game.getPlayer1());
        assertEquals(1, board[1][1].getLevels());
    }

    @Test
    public void testinitiateCardTowerOutOfBoundsNeg() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            godCard.initiatePlayer(positions0022, game.getPlayer1(), board);
            Worker worker1 = game.getPlayer1().getWorker1();
            game.initiateCardMove(new Cell(0, 1), worker1, game.getPlayer1());
            game.initiateCardTower(new Cell(-1, 0), worker1, game.getPlayer1());
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testinitiateCardTowerOutOfBoundsPos() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            Cell[] positions = new Cell[2];
            positions[0] = new Cell(0, 0);
            positions[1] = new Cell(4, 3);
            godCard.initiatePlayer(positions, game.getPlayer1(), board);
            Worker worker2 = game.getPlayer1().getWorker2();
            game.initiateCardMove(new Cell(3, 4), worker2, game.getPlayer1());
            game.initiateCardTower(new Cell(4, 5), worker2, game.getPlayer1());
        });

        String expectedMessage = "Out of bounds!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testinitiateCardTowerCreatesDome() throws InvalidMoveException, InvalidTurnException, GameOverException {
        godCard.initiatePlayer(positions0022, game.getPlayer1(), board);
        Worker worker1 = game.getPlayer1().getWorker1();
        board[1][1].setLevel(3);
        game.initiateCardMove(new Cell(0, 1), worker1, game.getPlayer1());
        game.initiateCardTower(new Cell(1, 1), worker1, game.getPlayer1());
        assertEquals(4, board[1][1].getLevels());
        assertTrue(board[1][1].hasDome());
    }

    @Test
    public void testinitiateCardTowerOnDome() throws InvalidMoveException, InvalidTurnException, GameOverException {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            godCard.initiatePlayer(positions0022, game.getPlayer1(), board);
            Worker worker1 = game.getPlayer1().getWorker1();
            board[1][1].setLevel(4);
            game.initiateCardMove(new Cell(0, 1), worker1, game.getPlayer1());
            game.initiateCardTower(new Cell(1, 1), worker1, game.getPlayer1());
        });

        String expectedMessage = "Location has a dome on it!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testinitiateCardTowerAndChangeTurns() throws InvalidMoveException, InvalidTurnException, GameOverException {
        Cell[] positions = new Cell[2];
        positions[0] = new Cell(1, 2);
        positions[1] = new Cell(1, 0);
        godCard.initiatePlayer(positions0022, game.getPlayer1(), board);
        godCard.initiatePlayer(positions, game.getPlayer2(), board);
        Worker worker1 = game.getPlayer1().getWorker1();
        Worker worker2 = game.getPlayer1().getWorker2();
        Worker worker3 = game.getPlayer2().getWorker1();
        Worker worker4 = game.getPlayer2().getWorker2();
        game.initiateCardMove(new Cell(0, 1), worker1, game.getPlayer1());
        game.initiateCardTower(new Cell(1, 1), worker1, game.getPlayer1());
        game.setCurrentPlayer();
        assertEquals(1, board[1][1].getLevels());
        game.initiateCardMove(new Cell(2, 1), worker3, game.getPlayer2());
        game.initiateCardTower(new Cell(1, 1), worker3, game.getPlayer2());
        game.setCurrentPlayer();
        assertEquals(2, board[1][1].getLevels());
        game.initiateCardMove(new Cell(1, 2), worker2, game.getPlayer1());
        game.initiateCardTower(new Cell(1, 1), worker2, game.getPlayer1());
        game.setCurrentPlayer();
        assertEquals(3, board[1][1].getLevels());
        game.initiateCardMove(new Cell(2, 0), worker4, game.getPlayer2());
        game.initiateCardTower(new Cell(1, 1), worker4, game.getPlayer2());
        game.setCurrentPlayer();
        assertEquals(4, board[1][1].getLevels());
        assertTrue(board[1][1].hasDome());
    }

    @Test
    public void testAdjacentMoveWorker() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            godCard.initiatePlayer(positions0022, game.getPlayer1(), board);
            Worker worker1 = game.getPlayer1().getWorker1();
            game.initiateCardMove(new Cell(4, 4), worker1, game.getPlayer1());
        });

        String expectedMessage = "Move has to be adjacent from current worker!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAdjacentPlaceTower() {
        Exception e = assertThrows(InvalidMoveException.class, () -> {
            godCard.initiatePlayer(positions0022, game.getPlayer1(), board);
            Worker worker1 = game.getPlayer1().getWorker1();
            game.initiateCardMove(new Cell(1, 1), worker1, game.getPlayer1());
            game.initiateCardTower(new Cell(4, 4), worker1, game.getPlayer1());
        });

        String expectedMessage = "Build has to be adjacent from current worker!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}