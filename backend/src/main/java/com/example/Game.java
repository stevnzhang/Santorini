package com.example;

public class Game {
    // Strategy easier to implement (but code duplication)
    // Template harder to implement (but code reusability)
    private final int numRows = 5;
    private final int numCols = 5;
    private Cell[][] board;
    private Player player1;
    private Player player2;
    private GodCard player1GC;
    private GodCard player2GC;
    private Player currentPlayer;
    private Worker currentWorker;
    private int worker_dy;
    private boolean gameOver;
    private Player winner;

    public Game(Player p1, Player p2, GodCard gc1, GodCard gc2) {
        this.currentPlayer = p1;
        this.player1 = p1;
        this.player2 = p2;
        this.player1GC = gc1;
        this.player2GC = gc2;

        this.board = new Cell[numRows][numCols];
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                this.board[row][col] = new Cell(0, false);
            }
        }
    }

    public int getNumRows() { return this.numRows; }

    public int getNumCols() { return this.numCols; }

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

    public int getWorker_dy() { return this.worker_dy; }

    public void initiateCardMove(Cell[] positions, Worker worker, Player player, Cell[][] board) throws InvalidTurnException, GameOverException, InvalidMoveException {
        if (this.gameOver) { throw new GameOverException("Game is over!"); }
        if (this.currentPlayer != player) { throw new InvalidTurnException("It's not your turn!"); }

        GodCard card = (player == this.player1 ? this.player1GC : this.player2GC);
        card.initiateMove(positions, worker, player, board);

        int height = positions[0].getLevels();
        this.worker_dy = worker.getHeight() - height;
        card.gameOver(player); // Check for game over after a move has been made
    }

    public void initiateCardTower(Cell[] positions, Worker worker, Player player, Cell[][] board) throws InvalidMoveException, GameOverException {
        if (this.gameOver) { throw new GameOverException("Game is over!"); }
        if (this.currentWorker != worker) { throw new InvalidMoveException("Build has to be adjacent to recently moved worker!"); }

        GodCard card = (player == this.player1 ? this.player1GC : this.player2GC);
        card.initiateTower(positions, worker, board);
        this.currentWorker = worker;
    }

    public void gameOverCard(Player player) {
        if (this.currentPlayer != player) { return; }

        GodCard card = (player == this.player1 ? this.player1GC : this.player2GC);
        card.gameOver(player);
    }

}
