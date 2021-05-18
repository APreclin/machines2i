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
    private int currentDistance;

    public DeliveryRound() {
        super();
        this.currentCharge = 0;
        truck = new Truck();
        depot = new Location();
        this.currentCharge = 0;
        this.currentDistance = 0;
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
        // Ajouter le cout de la tournée
        if ((request.getMachine().getSize() * request.getNbMachines() + this.currentCharge) <= this.truck
                .getCapacity()) {
            if (this.currentDistance == 0) {
                if (this.depot.getDistanceTo(request.getLocation()) + returnToDepot(request.getLocation()) <= this.truck
                        .getMaxDistance()) {
                    this.requests.add(request);
                    this.currentCharge += request.getMachine().getSize() * request.getNbMachines();
                    this.currentDistance += this.depot.getDistanceTo(request.getLocation())
                            + returnToDepot(request.getLocation());
                    request.setDeliveryDate(this.date);
                    return true;
                } else
                    return false;
            } else {
                if ((this.requests.getLast().getLocation().getDistanceTo(request.getLocation()) + this.currentDistance
                        - returnToDepot(requests.getLast().getLocation())
                        + returnToDepot(request.getLocation()) <= this.truck.getMaxDistance())) {
                    this.requests.add(request);
                    this.currentCharge += request.getMachine().getSize() * request.getNbMachines();
                    this.currentDistance += this.requests.getLast().getLocation().getDistanceTo(request.getLocation())
                            - returnToDepot(requests.getLast().getLocation()) + returnToDepot(request.getLocation());
                    request.setDeliveryDate(this.date);
                    return true;
                } else
                    return false;
            }
        } else
            return false;
    }

    public int returnToDepot(Location location) {
        return location.getDistanceTo(this.depot);
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
        // TODO: test unitaire deliveryRound
    }

}
