package solution.tournee;

import java.util.LinkedList;

import instance.reseau.Request;

public abstract class Round {

    protected LinkedList<Request> requests;
    protected int totalCost;
    protected int date;

    public abstract boolean addRequest(Request request);

    protected Round() {
        requests = new LinkedList<Request>();
        totalCost = 0;
        date = -1;
    }

    protected Round(LinkedList<Request> requests, int date) {
        this();
        this.requests = requests;
        this.date = date;
    }

    public LinkedList<Request> getRequests() {
        return requests;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getDate() {
        return date;
    }

    public boolean follows(Round otherRound) {
        if (this.date - otherRound.getDate() == 1)
            return true;
        else
            return false;
    }

    public int getDateDiff(Round otherRound) {
        return this.date - otherRound.getDate();
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
