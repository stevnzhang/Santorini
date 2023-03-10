package com.example;

public class Player {
    private Worker worker1;
    private Worker worker2;
    private String id;

    public Player(String id) { this.id = id; }

    public Worker getWorker1() { return this.worker1; }

    public Worker getWorker2() { return this.worker2; }

    public void setWorker1(Worker worker) { this.worker1 = worker; }

    public void setWorker2(Worker worker) { this.worker2 = worker; }

    public String getID() { return this.id; }

}
