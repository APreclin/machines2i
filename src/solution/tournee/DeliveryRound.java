package solution.tournee;

import java.util.LinkedList;

import instance.Instance;
import instance.reseau.Location;
import instance.reseau.Request;
import instance.reseau.Truck;

public class DeliveryRound extends Round {

    private Truck truck;
    private Location depot;
    private int currentCharge;

    public DeliveryRound() {
        super();
        this.currentCharge = 0;
        truck = new Truck();
        depot = new Location();
        this.currentCharge = 0;
    }

    public DeliveryRound(LinkedList<Request> requests, int date) {
        super(requests, date);
    }

    public int getCurrentCharge() {
        return currentCharge;
    }

    public DeliveryRound(Instance instanceToCopy) {
        super();
        truck = instanceToCopy.getTruck();
        depot = instanceToCopy.getDepot();
        currentCharge = 0;
    }


    @Override
    public boolean addRequest(Request request) {
        //TODO: vérifier la charge du camion
        return false;
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

    public static void main(String[] args) {
        //TODO: test unitaire deliveryRound
    }

}
