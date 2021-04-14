package solution.tournee;

import java.util.LinkedList;

import solution.Request;

public abstract class Round {

    private LinkedList<Request> requests;
    private int totalCost;
    private int date;

    public Round() {

        this.requests = new LinkedList<>();
        this.totalCost = 0;
        this.date = -1;
    }
}
