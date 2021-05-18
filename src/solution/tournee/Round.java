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

    /**
     * Check if the date of this round follows the date of otherRound
     * 
     * @param otherRound
     * @return whether the date of this round follows the date of otherRound or not
     */
    public boolean follows(Round otherRound) {
        if (getDateDiff(otherRound) == 1)
            return true;

        return false;
    }

    /**
     * Calculates and return the difference between otherRound's date and this
     * round's date
     * 
     * @param otherRound
     * @return The difference between otherRound's date and this round's date
     */
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
        str += "Total Cost : " + totalCost + "\n\n";
        str += "-----------------\n";
        return str;
    }

}
