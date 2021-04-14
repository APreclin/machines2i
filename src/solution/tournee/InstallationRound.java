package solution.tournee;

import java.util.LinkedList;

import instance.reseau.Request;

public class InstallationRound extends Round {

    private int nbRequests;

    public InstallationRound() {

        super();
        this.nbRequests = 0;
    }

    public InstallationRound(LinkedList<Request> requests, int date, int nbRequests) {
        super(requests, date);
        this.nbRequests = nbRequests;
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Installation Round -----\n\n";
        str += "Nb requests : " + nbRequests + "\n";
        str += super.toString();
        str += "------------------------------\n\n";
        return str;
    }

}
