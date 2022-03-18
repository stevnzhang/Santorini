package com.example;

public class Pan extends Game {

    private static final int WIN_HEIGHT = 3;

    public Pan(int numRows, int numCols) {
        super(numRows, numCols);
    }

    /**
     * Win if worker moves down 2 or more level
     */
    @Override
    public void gameOver() {
        Player currPlayer = getCurrentPlayer() == 0 ? getPlayer1() : getPlayer2();
        if (currPlayer.getWorker1().getHeight() == WIN_HEIGHT ||
            currPlayer.getWorker2().getHeight() == WIN_HEIGHT ||
            getWorker_dy() >= 2) { // The currPlayer has a worker at level 3, or they recently moved their worker 2 or more levels down
            winner = currPlayer;
            gameOver = true;
        }

    }

}
