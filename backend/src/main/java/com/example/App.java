package com.example;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;
    private GodCard gc1;
    private GodCard gc2;

    public App() throws IOException {
        super(8080);

        Player player1 = new Player("1");
        Player player2 = new Player("2");
        this.game = new Game(player1, player2);
        this.gc1 = new Minotaur();
        this.gc2 = new NoCard();
        this.game.setGodCard(gc1, game.getPlayer1());
        this.game.setGodCard(gc2, game.getPlayer2());

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        Player currentPlayer = this.game.getCurrentPlayer();
        String exception = "";
        if (uri.equals("/newgame")) {
            Player player1 = new Player("1");
            Player player2 = new Player("2");
            this.game = new Game(player1, player2);
            this.game.setGodCard(gc1, this.game.getPlayer1());
            this.game.setGodCard(gc2, this.game.getPlayer2());
        } else if (uri.equals("/play")) {
            try {
                Cell position = new Cell(Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
                this.game.initiatePlayer(position, this.game.getCurrentPlayer());
                if (currentPlayer.getWorker1() != null && currentPlayer.getWorker2() != null && !game.allWorkersPlaced()) {
                    this.game.setCurrentPlayer();
                }
                if (game.allWorkersPlaced()) this.game.setCurrentPlayer();
            } catch (InvalidMoveException e) {
                exception = "Player " + currentPlayer.getID() + "'s Turn: " + e.getMessage();
            }
        } else if (uri.equals("/pickworker")) {
                try {
                    Cell workerPosition = game.getBoard()[Integer.parseInt(params.get("x"))][Integer.parseInt(params.get("y"))];
                    Worker worker = workerPosition.getWorker();
                    game.checkWorker(worker);
                    game.setSelectedWorker(worker);
                } catch (InvalidMoveException e) {
                    exception = "Player " + currentPlayer.getID() + "'s Turn: " + e.getMessage();
                }
        } else if (uri.equals("/moveworker")) {
            try {
                Cell position = new Cell(Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
                this.game.initiateCardMove(position, this.game.getSelectedWorker(), game.getCurrentPlayer());
                this.game.gameOverCard(currentPlayer);
                this.game.setJustMoved(true);
            } catch (InvalidTurnException | GameOverException | InvalidMoveException e) {
                exception = "Player " + currentPlayer.getID() + "'s Turn: " + e.getMessage();
            }
        } else if (uri.equals("/placetower")) {
            try {
                Cell position = new Cell(Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
                this.game.initiateCardTower(position, this.game.getSelectedWorker(), game.getCurrentPlayer());
                this.game.setSelectedWorker(null);
                this.game.setJustMoved(false);
                this.game.setCurrentPlayer();
            } catch (GameOverException | InvalidMoveException e) {
                exception = "Player " + currentPlayer.getID() + "'s Turn: " + e.getMessage();
            }
        } else if (uri.equals("/skip")) {
            this.game.setState("build");
            this.game.setCurrentPlayer();
        }
        // Extract the view-specific data from the game and apply it to the template.
        GameState gameplay = GameState.forGame(this.game, exception);
        return newFixedLengthResponse(gameplay.toString());
    }

    public static class Test{
        public String getText() {
            return "Hello World!";
        }
    }
}