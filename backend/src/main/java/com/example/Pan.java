package com.example;

public class Pan implements GodCard {

    private static final int WIN_HEIGHT = 3;

    /**
     * Wins if worker moves down 2 or more level
     *
     * @param player the current player.
     */
    @Override
    public boolean gameOver(Player player) {
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        int worker_dy1 = worker1.getPrevHeight() - worker1.getHeight();
        int worker_dy2 = worker2.getPrevHeight() - worker1.getHeight();

        // The currPlayer has a worker at level 3, or they recently moved their worker 2 or more levels down
        if ((worker1.getHeight() == WIN_HEIGHT && !worker1.isForced()) ||
            (worker2.getHeight() == WIN_HEIGHT && !worker2.isForced()) ||
            (worker_dy1 >= 2 && !worker1.isForced()) ||
            (worker_dy2 >= 2 && !worker2.isForced()) ) {
             return true;
        }
        return false;
    }

}
