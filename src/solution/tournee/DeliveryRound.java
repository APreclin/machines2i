package solution.tournee;

import java.util.LinkedList;

import instance.Instance;
import instance.reseau.Location;
import instance.reseau.Machine;
import instance.reseau.Request;
import instance.reseau.Truck;

public class DeliveryRound extends Round {

    private Truck truck;
    private Location depot;
    private int currentCharge;
    private int currentDistance;

    public DeliveryRound() {
        super();
        truck = new Truck();
        depot = new Location();
        this.currentCharge = 0;
        this.currentDistance = 0;
    }

    public DeliveryRound(Truck truck, Location depot) {
        super();
        this.truck = truck;
        this.depot = depot;
        this.currentCharge = 0;
        this.currentDistance = 0;
    }

    public DeliveryRound(LinkedList<Request> requests, int date) {
        super(requests, date);
    }

    public DeliveryRound(Instance instanceToCopy) {
        super();
        truck = instanceToCopy.getTruck();
        depot = instanceToCopy.getDepot();
        currentCharge = 0;
    }

    public int getCurrentCharge() {
        return currentCharge;
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
                    this.totalCost = this.currentDistance;
                    return true;
                } else
                    return false;
            } else {
                if ((this.requests.getLast().getLocation().getDistanceTo(request.getLocation()) + this.currentDistance
                        - returnToDepot(requests.getLast().getLocation())
                        + returnToDepot(request.getLocation()) <= this.truck.getMaxDistance())) {
                    this.currentCharge += request.getMachine().getSize() * request.getNbMachines();
                    this.currentDistance += this.requests.getLast().getLocation().getDistanceTo(request.getLocation())
                            - returnToDepot(requests.getLast().getLocation()) + returnToDepot(request.getLocation());
                    request.setDeliveryDate(this.date);
                    this.requests.add(request);
                    this.totalCost = this.currentDistance;
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
        str += "\n----- Delivery Round -----\n\n";
        str += "Current charge : " + currentCharge + "\n";
        str += "Current distance : " + currentDistance + "\n\n";
        str += depot + "\n";
        str += truck + "\n";
        str += super.toString();
        str += "--------------------------\n\n";
        return str;
    }

    public static void main(String[] args) {

        // Création d'une delivery round simple
        Location depot = new Location(0, 0, 0);
        Truck truck = new Truck(20, 50, 5, 100);
        DeliveryRound dr = new DeliveryRound(truck, depot);
        // System.out.println(dr.toString());

        // Ajout d'une requete qui dépasse la capacité du camion
        Location l = new Location(1, 1, 0);
        Machine m = new Machine(1, 10, 20);
        Request r = new Request(1, l, 1, 3, m, 3);
        // System.out.println(dr.addRequest(r));

        // Ajout d'une requete qui ne dépasse pas la capacité mais qui dépasse la
        // distance
        Location l2 = new Location(2, 1000, 0);
        Request r2 = new Request(2, l2, 1, 3, m, 1);
        // System.out.println(dr.addRequest(r2));

        // Ajout d'une requete qui est ajoutée à partir du dépôt
        Request r3 = new Request(3, l, 1, 3, m, 1);
        // System.out.println(dr.addRequest(r3));
        // System.out.println(dr.toString());

        // Ajout d'une 2e tournée
        Location l3 = new Location(3, 2, 0);
        Request r4 = new Request(4, l3, 1, 3, m, 1);
        // System.out.println(dr.addRequest(r3));
        // System.out.println(dr.addRequest(r4));
        // System.out.println(dr.toString());
    }

}
