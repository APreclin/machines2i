package solution.tournee;

import java.util.LinkedList;

import instance.reseau.Request;

public abstract class Round {

    protected LinkedList<Request> requests;
    protected int totalCost;

    public abstract boolean addRequest(Request request);

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

    /**
     * Check if the date of this round follows the date of otherRound
     * 
     * @param otherRound
     * @return whether the date of this round follows the date of otherRound or not
     */
    public boolean follows(Round otherRound) {
        return getDateDiff(otherRound) == 1;
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
        str += "\n----- Round -----\n";
        str += "Total Cost : " + totalCost + "\n";
        for (Request r : requests) {
            str += r.toString();
        }
        str += "-----------------\n";
        return str;
    }

}
