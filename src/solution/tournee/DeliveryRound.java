package solution.tournee;

import java.util.LinkedList;

import instance.reseau.Request;

public class DeliveryRound extends Round {

    private int currentCharge;

    public DeliveryRound() {

        super();
        this.currentCharge = 0;
    }

    public DeliveryRound(LinkedList<Request> requests, int date) {
        super(requests, date);
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Delivery Round -----\n\n";
        str += "Current charge : " + currentCharge + "\n";
        str += super.toString();
        str += "--------------------------\n\n";
        return str;
    }

}
