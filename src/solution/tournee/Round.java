package solution.tournee;

import java.util.LinkedList;

import instance.reseau.Location;
import instance.reseau.Request;
import operateur.InRoundOperator;
import operateur.InRoundOperatorType;

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

    public Request getRequestByPosition(int position) {
        if (isPositionValid(position))
            return requests.get(position);
        else 
            return null;
    }

    public abstract Location getCurrent(int position);

    public abstract Location getPrec(int position);

    public abstract Location getNext(int position);

    public boolean isPositionValid (int position) {
        if (position >= 0 && position < requests.size())
            return true;
        else
            return false;
    }

    public boolean isInsertionPositionValid (int position) {
        if (position >= 0 && position <= requests.size())
            return true;
        else
            return false;
    }

    public abstract boolean checkAddingRequest(Request request);

    public int deltaCostInsertion(int position, Request requestToAdd, boolean watchCapacity) {
        if (requestToAdd == null) return Integer.MAX_VALUE;
        Location current = getCurrent(position);
        Location prec = getPrec(position);
        if (current == null || prec == null)
            return Integer.MAX_VALUE;
        if (watchCapacity) {
            if (!checkAddingRequest(requestToAdd))
                return Integer.MAX_VALUE;
        }

        int cout1 = prec.getDistanceTo(requestToAdd.getLocation());
        int cout2 = requestToAdd.getLocation().getDistanceTo(current);
        int cout3 = prec.getDistanceTo(current);
        if (cout1 == Integer.MAX_VALUE || cout2 == Integer.MAX_VALUE || cout3 == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        return cout1 + cout2 - cout3;
    }

    public int deltaCostSuppression(int position) {
        Location requestPrec = getPrec(position);
        Location ClientNext = getNext(position);
        Location ClientCurr = getCurrent(position);
        if (requestPrec == null|| ClientCurr == null || ClientNext == null)
            return Integer.MAX_VALUE;
        
        int cout1 = requestPrec.getDistanceTo(ClientNext);
        int cout2 = requestPrec.getDistanceTo(ClientCurr);
        int cout3 = ClientCurr.getDistanceTo(ClientNext);
        if (cout1 == Integer.MAX_VALUE || cout2 == Integer.MAX_VALUE || cout3 == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        return cout1 - ( cout2 + cout3 );
    }

    public int deltaCostMove(int positionI, int positionJ) {
        if (!this.isPositionValid(positionI) || !this.isInsertionPositionValid(positionJ))
            return Integer.MAX_VALUE;
        int coutInsertion = deltaCostInsertion(positionJ, requests.get(positionI), false);
        int coutSuppression = deltaCostSuppression(positionI);
        if (coutInsertion == Integer.MAX_VALUE || coutSuppression == Integer.MAX_VALUE || positionI == positionJ || Math.abs(positionI - positionJ) == 1)
            return Integer.MAX_VALUE;
        else
            return deltaCostInsertion(positionJ, requests.get(positionI), false) + deltaCostSuppression(positionI);
    }

    private int deltaCostConsecutivExchange(int positionI) {
        Location requestI = this.getRequestByPosition(positionI).getLocation();
        Location requestBefore = this.getPrec(positionI);
        Location requestJ = this.getRequestByPosition(positionI+1).getLocation();
        Location requestAfter = this.getNext(positionI+1);

        if (requestI == null || requestBefore == null || requestJ == null || requestAfter == null)
            return Integer.MAX_VALUE;

        int cout = requestBefore.getDistanceTo(requestJ) + requestJ.getDistanceTo(requestI) - requestBefore.getDistanceTo(requestI);
        cout += requestI.getDistanceTo(requestAfter) - (requestI.getDistanceTo(requestJ) + requestJ.getDistanceTo(requestAfter));
        
        return cout;
    }

    public int deltaCostReplace(int position, Request request, boolean watchCapacity) {
        Location requestBefore = this.getPrec(position);
        Location ancientRequest = this.getRequestByPosition(position).getLocation();
        Location requestAfter = this.getNext(position);

        if (requestBefore == null || ancientRequest == null || requestAfter == null || request == null)
            return Integer.MAX_VALUE;
            

        /*
        if (watchCapacity && (demandeTotale + request.getDemande() - this.getRequestByPosition(position).getDemande()) > capacite)
                return Integer.MAX_VALUE;
        */

        int cout = requestBefore.getDistanceTo(request.getLocation()) + request.getLocation().getDistanceTo(requestAfter);
        cout -= requestBefore.getDistanceTo(ancientRequest) + ancientRequest.getDistanceTo(requestAfter);
        return cout;
    }
    
    public int deltaCostExchange(int positionI, int positionJ) {
        if (!this.isPositionValid(positionI) || !this.isPositionValid(positionJ))
            return Integer.MAX_VALUE;
        if (positionI >= positionJ)
            return Integer.MAX_VALUE;
        if (Math.abs(positionI - positionJ) == 1)
            return deltaCostConsecutivExchange(positionI);
        else
            return deltaCostReplace(positionJ, requests.get(positionI), false) + deltaCostReplace(positionI, requests.get(positionJ), false);
    }

    public abstract InRoundOperator getBestInRoundOperator(InRoundOperatorType type);

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
