package com.example;

import java.util.Arrays;

public class GameState {

    // Don't change variables -- No sets, only gets and legal checks
    // Override the links

    private final GameCell[] gameCells;
    private final Player winner;
    private final Player turn;

    private GameState(GameCell[] gameCells, Player winner, Player turn) {
        this.gameCells = gameCells;
        this.turn = turn;
        this.winner = winner;
    }

    public static GameState forGame(Game game) {
        GameCell[] gameCells = getGameCells(game);
        Player winner = getWinner(game);
        Player turn = getTurn(game);
        return new GameState(gameCells, winner, turn);
    }

    @Override
    public String toString() {
        if (this.winner == null) {
            return "{ \"cells\": " + Arrays.toString(this.gameCells) + "," +
                     "\"turn\": " + String.format("\"%s\"", this.turn.getID()) + "}";
        }
        // if there is a winner:
        return "{ \"cells\": " + Arrays.toString(this.gameCells) + "," +
                 "\"turn\": " + String.format("\"%s\"", this.turn.getID()) + "," +
                 "\"winner\": " + String.format("\"%s\"", this.turn.getID()) + "}";
    }

    private static GameCell[] getGameCells(Game game) {
        GameCell gameCells[] = new GameCell[25];
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        Cell[][] board = game.getBoard();
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                String text = "";
                String link = "/play?x=" + x + "&y=" + y;
                String clazz = "playable";
                Worker worker = board[x][y].getWorker();
                Worker selectedWorker = game.getSelectedWorker();
                if (game.allWorkersPlaced()) {
                    link = "/pickworker?x=" + x + "&y=" + y;
                }
                if (selectedWorker != null && !game.getJustMoved()) {
                    link = "/moveworker?x=" + x + "&y=" + y;
                    if (x == selectedWorker.getRow() && y == selectedWorker.getCol()) clazz = "picked";
                } if (selectedWorker != null && game.getJustMoved()) {
                    link = "/placetower?x=" + x + "&y=" + y;
                    if (x == selectedWorker.getRow() && y == selectedWorker.getCol()) clazz = "picked";
                }
                if (worker != null) { // Drawing the workers
                    if (worker == player1.getWorker1() || worker == player1.getWorker2()) text = "X";
                    else if (worker == player2.getWorker1() || worker == player2.getWorker2()) text = "O";
                }
                if (board[x][y].getLevels() >= 4) { // Drawing Dome
                    text = "D";
                    clazz = "dome";
                } if (0 < board[x][y].getLevels() && board[x][y].getLevels() < 4) { // Drawing Towers
                    String left = "";
                    String right = "";
                    for (int i=0; i < board[x][y].getLevels(); i++) {
                        left += "[";
                        right += "]";
                    }
                    text = left + text + right;
                }
                gameCells[5 * x + y] = new GameCell(text, clazz, link);
            }
        }
        return gameCells;
    }

    private static Player getWinner(Game game) {
        return game.getWinner();
    }

    private static Player getTurn(Game game) {
        return game.getCurrentPlayer() == game.getPlayer1() ? game.getPlayer1() : game.getPlayer2();
    }
}

class GameCell {
    private final String text;
    private final String clazz;
    private final String link;

    GameCell(String text, String clazz, String link) {
        this.text = text;
        this.clazz = clazz;
        this.link = link;
    }

    public String getText() { return this.text; }

    public String getClazz() { return this.clazz; }

    public String getLink() { return this.link; }

    @Override
    public String toString() {
        return "{ \"text\": \"" + this.text + "\"," +
               " \"clazz\": \"" + this.clazz + "\"," +
               " \"link\": \"" + this.link + "\"}";
    }
}
