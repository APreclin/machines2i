package solution.tournee;

import java.util.LinkedList;

import instance.reseau.Request;

public abstract class Round {

    protected LinkedList<Request> requests;
    protected int totalCost;

    public abstract boolean addRequest(Request request);

    public abstract boolean addRequestSolution2(Request request);

    protected Round() {
        requests = new LinkedList<Request>();
        this.totalCost = 0;
    }

    protected Round(LinkedList<Request> requests) {
        this();
        this.requests = requests;
    }

    public LinkedList<Request> getRequests() {
        return requests;
    }

    public int getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n----- Round -----\n";
        str += "Total Cost : " + totalCost + "\n";
        for (Request r : requests) {
            str += r.toString();
        }
        str += "-----------------\n";
        return str;
    }

}
