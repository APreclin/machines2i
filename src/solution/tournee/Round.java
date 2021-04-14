package solution.tournee;

import java.util.LinkedList;

import instance.reseau.Request;

public abstract class Round {

    private LinkedList<Request> requests;
    private int totalCost;
    private int date;

    public Round() {
        this.requests = new LinkedList<Request>();
        this.totalCost = 0;
        this.date = -1;
    }

    public Round(LinkedList<Request> requests, int date) {
        this();
        this.requests = requests;
        this.date = date;
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Round -----\n\n";
        str += "Date : " + date + "\n";
        for (Request r : requests) {
            str += r.toString();
        }
        str += "-----------------\n\n";
        return str;
    }
}
