//package com.example;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class IntegrationTest {
//
//    private Game game;
//    private Cell[][] board;
//    private GodCard godCard;
//
//    @Before
//    public void setUp() {
//        game = new Game(new Player(), new NoCard(), new Player(), new NoCard());
//        board = game.getBoard();
//        godCard = new NoCard();
//    }
//
//    @Test
//    public void integrationTest() throws InvalidMoveException, InvalidTurnException, GameOverException {
//        Cell[] positions1 = new Cell[2];
//        positions1[0] = new Cell(0, 0);
//        positions1[1] = new Cell(2, 2);
//        Cell[] positions2 = new Cell[2];
//        positions2[0] = new Cell(1, 2);
//        positions2[1] = new Cell(1, 0);
//        godCard.initiatePlayer(positions1, game.getPlayer1(), board);
//        godCard.initiatePlayer(positions2, game.getPlayer2(), board);
//        Worker worker1 = game.getPlayer1().getWorker1();
//        Worker worker2 = game.getPlayer1().getWorker2();
//        Worker worker3 = game.getPlayer2().getWorker1();
//        Worker worker4 = game.getPlayer2().getWorker2();
//
//        // Building the 3 level tower
//        game.initiateCardMove(new Cell(0, 1), worker1, game.getPlayer1());
//        game.initiateCardTower(new Cell(1, 1), worker1, game.getPlayer1());
//        assertEquals(1, board[1][1].getLevels());
//
//        game.initiateCardMove(new Cell(2, 1), worker3, game.getPlayer2());
//        game.initiateCardTower(new Cell(1, 1), worker3, game.getPlayer2());
//        assertEquals(2, board[1][1].getLevels());
//
//        game.initiateCardMove(new Cell(1, 2), worker2, game.getPlayer1());
//        game.initiateCardTower(new Cell(1, 1), worker2, game.getPlayer1());
//        assertEquals(3, board[1][1].getLevels());
//
//        // Building the 2 level tower
//        game.initiateCardMove(new Cell(2, 0), worker4, game.getPlayer2());
//        game.initiateCardTower(new Cell(1, 0), worker4, game.getPlayer2());
//        assertEquals(1, board[1][0].getLevels());
//
//        game.initiateCardMove(new Cell(0, 0), worker1, game.getPlayer1());
//        game.initiateCardTower(new Cell(1, 0), worker1, game.getPlayer1());
//        assertEquals(2, board[1][0].getLevels());
//
//        // Filler turn
//        game.initiateCardMove(new Cell(2, 2), worker3, game.getPlayer2());
//        game.initiateCardTower(new Cell(3, 3), worker3, game.getPlayer2());
//
//        // Building the 1 level tower
//        game.initiateCardMove(new Cell(0, 2), worker2, game.getPlayer1());
//        game.initiateCardTower(new Cell(0, 1), worker2, game.getPlayer1());
//
//        // Moving worker1 to winning condition (row, col = 1, 1)
//        game.initiateCardMove(new Cell(3, 1), worker4, game.getPlayer2());
//        game.initiateCardTower(new Cell(4, 1), worker4, game.getPlayer2());
//
//        game.initiateCardMove(new Cell(0, 1), worker1, game.getPlayer1());
//        game.initiateCardTower(new Cell(0, 0), worker1, game.getPlayer1());
//
//        game.initiateCardMove(new Cell(1, 2), worker3, game.getPlayer2());
//        game.initiateCardTower(new Cell(1, 3), worker3, game.getPlayer2());
//
//        game.initiateCardMove(new Cell(1, 0), worker1, game.getPlayer1());
//        game.initiateCardTower(new Cell(0, 0), worker1, game.getPlayer1());
//
//        game.initiateCardMove(new Cell(0, 1), worker3, game.getPlayer2());
//        game.initiateCardTower(new Cell(0, 0), worker3, game.getPlayer2());
//
//        game.initiateCardMove(new Cell(1, 1), worker1, game.getPlayer1());
//
////        assertTrue(board[0][0].hasDome());
//        assertTrue(game.getGameOver());
//        assertEquals(game.getPlayer1(), game.getWinner());
//    }
//
//}
