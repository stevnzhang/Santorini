package com.example;

public class Cell {
    private int row;
    private int col;
    private int numLevels;
    private boolean hasDome;
    private boolean occupied;
    private Worker worker;
    private boolean justPlaced;

    private static final int DOME_LEVEL = 4;

    public Cell(int num, boolean occupied) {
        this.numLevels = num;
        this.occupied = occupied;
    }

    public Cell(int row, int col) { // For testing
        this.row = row;
        this.col = col;
        this.numLevels = 0;
        this.occupied = false;
    }

    public int getRow() { return this.row; }

    public int getCol() { return this.col; }

    public int getLevels() { return this.numLevels; }

    public void setUnoccupied() { this.occupied = false; }

    public void setOccupied() { this.occupied = true; }

    public Worker getWorker() { return this.worker; }

    public void setWorker(Worker worker) { this.worker = worker; }

    public boolean getJustPlaced() { return this.justPlaced; }

    public void setJustPlaced(boolean val) { this.justPlaced = val; }

    /**
     * Changes the level of the cell (ONLY FOR TESTING PURPOSES).
     *
     * @param num the numLevels to change the cell to.
     */
    // I only included this function for testing so that I can separate my addLevel from my occupancy tests
    public void setLevel(int num) {
        if (0 <= num && num <= DOME_LEVEL) { this.numLevels = num; }
        if (num == DOME_LEVEL) { this.hasDome = true; }
    }

    /**
     * Adds a block on to the cell
     */
    public void addLevel() {
        if (!this.hasDome) { this.numLevels += 1; }
        if (this.numLevels == DOME_LEVEL) { this.hasDome = true; }
    }

    /**
     * Checks if the cell already has a worker in it
     *
     * @return {@code true} if the cell is occupied
     */
    public boolean occupancy() { return this.occupied; }

    /**
     * Checks if the cell has a dome on it
     *
     * @return {@code true} if the cell has a dome
     */
    public boolean hasDome() { return this.hasDome; }
}
