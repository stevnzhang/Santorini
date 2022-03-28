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

    public App() throws IOException {
        super(8080);

        Player player1 = new Player("1");
        Player player2 = new Player("2");
        this.game = new Game(player1, player2);
        this.game.setGodCard(new NoCard(), game.getPlayer1());
        this.game.setGodCard(new NoCard(), game.getPlayer2());

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        Player currentPlayer = this.game.getCurrentPlayer();
        if (uri.equals("/newgame")) {
            Player player1 = new Player("1");
            Player player2 = new Player("2");
            this.game = new Game(player1, player2);
            this.game.setGodCard(new NoCard(), this.game.getPlayer1());
            this.game.setGodCard(new NoCard(), this.game.getPlayer2());
        } else if (uri.equals("/play")) {
            Cell position = new Cell(Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
            try {
                this.game.initiatePlayer(position, this.game.getCurrentPlayer());
                if (currentPlayer.getWorker1() != null && currentPlayer.getWorker2() != null && !game.allWorkersPlaced()) {
                    this.game.setCurrentPlayer();
                }
                if (game.allWorkersPlaced()) this.game.setCurrentPlayer();
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        } else if (uri.equals("/pickworker")) {
                try {
                    Cell workerPosition = game.getBoard()[Integer.parseInt(params.get("x"))][Integer.parseInt(params.get("y"))];
                    Worker worker = workerPosition.getWorker();
                    if (worker == null) { throw new InvalidMoveException("Have to select a worker first"); }
                    if (worker != currentPlayer.getWorker1() && worker != currentPlayer.getWorker2()) {
                        throw new InvalidMoveException("Have to select your own worker");
                    }
                    game.setSelectedWorker(worker);
                } catch (InvalidMoveException e) {
                    e.printStackTrace();
                }
        } else if (uri.equals("/moveworker")) {
            try {
                Cell position = new Cell(Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
                this.game.initiateCardMove(position, this.game.getSelectedWorker(), game.getCurrentPlayer());
                this.game.gameOverCard(currentPlayer);
                this.game.setJustMoved(true);
            } catch (InvalidTurnException | GameOverException | InvalidMoveException e) {
                e.printStackTrace();
            }
        }
        // Extract the view-specific data from the game and apply it to the template.
        GameState gameplay = GameState.forGame(this.game);
        return newFixedLengthResponse(gameplay.toString());
    }

    public static class Test{
        public String getText() {
            return "Hello World!";
        }
    }
}