package com.example;

public class Pan implements GodCard {

    private static final int WIN_HEIGHT = 3;

    /**
     * Wins if worker moves down 2 or more level
     *
     * @param player the current player.
     */
    @Override
    public void gameOver(Player player) {
        if (player.getWorker1().getHeight() == WIN_HEIGHT ||
            player.getWorker2().getHeight() == WIN_HEIGHT ||
            getWorker_dy() >= 2) { // The currPlayer has a worker at level 3, or they recently moved their worker 2 or more levels down
            winner = player;
            gameOver = true;
        }

    }

}
