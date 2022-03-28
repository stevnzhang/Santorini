package com.example;

public class Game {
    private final int numRows = 5;
    private final int numCols = 5;
    private Cell[][] board;
    private Player player1;
    private Player player2;
    private GodCard player1GC;
    private GodCard player2GC;
    private Player currentPlayer;
    private Worker currentWorker;
    private boolean gameOver;
    private Player winner;
    private String state;
    private Worker selectedWorker;
    private boolean justMoved;

    public Game(Player p1, Player p2) {
        this.currentPlayer = p1;
        this.player1 = p1;
        this.player2 = p2;

        this.board = new Cell[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                this.board[row][col] = new Cell(row, col);
            }
        }
    }

    public Player getPlayer1() { return this.player1; }

    public Player getPlayer2() { return this.player2; }

    public Player getCurrentPlayer() { return this.currentPlayer; }

    public void setCurrentPlayer() {
        if (this.currentPlayer == this.player1) { this.currentPlayer = this.player2; }
        else { this.currentPlayer = this.player1; }
    }

    public Cell[][] getBoard() { return this.board; }

    public Player getWinner() { return this.winner; }

    public boolean getGameOver() { return this.gameOver; }

    public String getState() { return this.state; }

    public void setState(String state) { this.state = state; }

    public void setGodCard(GodCard gc, Player player) {
        if (player == this.player1) {
            this.player1GC = gc;
        } else if (player == this.player2) {
            this.player2GC = gc;
        }
    }

    public Worker getSelectedWorker() { return this.selectedWorker; }

    public void setSelectedWorker(Worker worker) { this.selectedWorker = worker; }

    public boolean getJustMoved() { return this.justMoved; }

    public void setJustMoved(boolean val) { this.justMoved = val; }

    public void checkWorker(Worker worker) throws InvalidMoveException {
        if (worker == null) { throw new InvalidMoveException("Have to select a worker first"); }
        if (worker != this.currentPlayer.getWorker1() && worker != this.currentPlayer.getWorker2()) {
            throw new InvalidMoveException("Have to select your own worker");
        }
    }

    /**
     * Places a tower at the specified location on the board
     *
     * @param row worker's row
     * @param col worker's col
     */
    public void placeWorker(int row, int col, Player player) {
        Worker worker1 = player.getWorker1();
        if (worker1 == null) {
            player.setWorker1(new Worker(row, col, 0));
            this.board[row][col].setWorker(player.getWorker1());
        } else { // player placed their first worker
            player.setWorker2(new Worker(row, col, 0));
            this.board[row][col].setWorker(player.getWorker2());
        }
        this.board[row][col].setOccupied();
    }

    /**
     * Creates the initial workers for the player.
     *
     * @param position the cell we want to place our worker on.
     * @param player the player we want to initiate.
     */
    public void initiatePlayer(Cell position, Player player) throws InvalidMoveException {
        int row = position.getRow();
        int col = position.getCol();
        Worker worker1 = player.getWorker1();
        if (worker1 != null && worker1.getRow() == row && worker1.getCol() == col) {
            throw new InvalidMoveException("Location has a worker on it!");
        }
        GodCard card = (player == this.player1 ? this.player1GC : this.player2GC);

        card.basicLegalChecks(row, col, this.board);
        card.playerCheck(row, col, this.board);

        placeWorker(row, col, player);
    }

    public boolean allWorkersPlaced() {
        if (this.player1.getWorker1() != null && this.player1.getWorker2() != null &&
        this.player2.getWorker1() != null && this.player2.getWorker2() != null) {
            return true;
        } return false;
    }

    public void initiateCardMove(Cell position, Worker worker, Player player) throws InvalidTurnException, GameOverException, InvalidMoveException {
        if (this.gameOver) { throw new GameOverException("Game is over!"); }
        if (this.currentPlayer != player) { throw new InvalidTurnException("It's not your turn!"); }

        int originalRow = worker.getRow();
        int originalCol = worker.getCol();
        GodCard card = (player == this.player1 ? this.player1GC : this.player2GC);
        card.initiateMove(position, worker, player, this.board);
        this.board[originalRow][originalCol].setWorker(null);
        this.currentWorker = worker;
        setState("build");
    }

    public void initiateCardTower(Cell position, Worker worker, Player player) throws InvalidMoveException, GameOverException {
        if (this.gameOver) { throw new GameOverException("Game is over!"); }
        if (this.currentWorker != worker) { throw new InvalidMoveException("Build has to be adjacent to recently moved worker!"); }

        GodCard card = (player == this.player1 ? this.player1GC : this.player2GC);
//        if (this.state == "build") card.initiateTower(position, worker, this.board, this.state);
//        else if (this.state == "second build") card.initiateTower(position, worker, this.board, this.state);
        // TODO: DEMETER AND SELECT GOD CARDS
        if (this.state != "skip") {
            card.initiateTower(position, worker, this.board, this.state);
            if (this.state == "second build") setState(null);
        }
        else if (this.state == "skip") setState(null);
    }

    public void gameOverCard(Player player) {
        if (this.currentPlayer != player) { return; }

        GodCard card = (player == this.player1 ? this.player1GC : this.player2GC);
        if (card.gameOver(player)) {
            this.winner = player;
            this.gameOver = true;
        }
    }

}
