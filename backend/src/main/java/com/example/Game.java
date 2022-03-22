package com.example;

public class Game {
    // Strategy easier to implement (but code duplication)
    // Template harder to implement (but code reusability)

    // Demeter skip option, how does that work because rn I have it so that it asks for more than one position
    // initiatePlayer?

    // move or build states, builtAlready state for Demeter
    // can have parameters that are not used by the base game (can use default values, obj has getter methods)

    private final int NUM_ROWS = 5;
    private final int NUM_COLS = 5;
    private Cell[][] board;
    private Player player1;
    private Player player2;
    private GodCard player1GC;
    private GodCard player2GC;
    private Player currentPlayer;
    private Worker currentWorker;
    private boolean gameOver;
    private Player winner;

    public Game(Player p1, GodCard gc1, Player p2, GodCard gc2) {
        this.currentPlayer = p1;
        this.player1 = p1;
        this.player2 = p2;
        this.player1GC = gc1;
        this.player2GC = gc2;

        this.board = new Cell[NUM_ROWS][NUM_COLS];
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                this.board[row][col] = new Cell(0, false);
            }
        }
    }

    public int getNumRows() { return NUM_ROWS; }

    public int getNumCols() { return NUM_COLS; }

    public Player getPlayer1() { return this.player1; }

    public Player getPlayer2() { return this.player2; }

    public void setPlayer1(Player player) { this.player1 = player; }

    public void setPlayer2(Player player) { this.player2 = player; }

    public Player getCurrentPlayer() { return this.currentPlayer; }

    public void setCurrentPlayer() {
        if (this.currentPlayer == this.player1) { this.currentPlayer = this.player2; }
        else { this.currentPlayer = this.player1; }
    }

    public Cell[][] getBoard() { return this.board; }

    public Player getWinner() { return this.winner; }

    public boolean getGameOver() { return this.gameOver; }

    public void initiateCardMove(Cell[] positions, Worker worker, Player player) throws InvalidTurnException, GameOverException, InvalidMoveException {
        if (this.gameOver) { throw new GameOverException("Game is over!"); }
        if (this.currentPlayer != player) { throw new InvalidTurnException("It's not your turn!"); }

        GodCard card = (player == this.player1 ? this.player1GC : this.player2GC);
        card.initiateMove(positions, worker, player, this.board);
        this.currentWorker = worker;
    }

    public void initiateCardTower(Cell[] positions, Worker worker, Player player) throws InvalidMoveException, GameOverException {
        if (this.gameOver) { throw new GameOverException("Game is over!"); }
        if (this.currentWorker != worker) { throw new InvalidMoveException("Build has to be adjacent to recently moved worker!"); }

        GodCard card = (player == this.player1 ? this.player1GC : this.player2GC);
        card.initiateTower(positions, worker, this.board);
    }

    public void gameOverCard(Player player) {
        if (this.currentPlayer != player) { return; }

        GodCard card = (player == this.player1 ? this.player1GC : this.player2GC);
        if (card.gameOver(player)) {
            this.winner = player;
            this.gameOver = true;
        }

    }

    public void initiatePlayer() {}

}
