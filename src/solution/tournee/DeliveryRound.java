package solution.tournee;

import instance.Instance;
import instance.reseau.Location;
import instance.reseau.Request;
import instance.reseau.Truck;
import solution.Day;

public class DeliveryRound extends Round {
    private Truck truck;
    private Location depot;
    private int currentCharge;
    private int currentDistance;
    private Day deliveryDay;

    public DeliveryRound() {
        super();
        depot = new Location();
        this.currentCharge = 0;
        this.currentDistance = 0;
    }

    public DeliveryRound(Truck truck, Location depot, Day deliveryDay) {
        super();
        this.depot = depot;
        this.currentCharge = 0;
        this.currentDistance = 0;
        this.truck = truck;
        this.deliveryDay = deliveryDay;
    }

    /*
     * public DeliveryRound(LinkedList<Request> requests, int deliveryDay) {
     * super(requests, deliveryDay); }
     */

    public DeliveryRound(Truck truck, Instance instanceToCopy, Day deliveryDay) {
        super();
        this.currentCharge = 0;
        this.deliveryDay = deliveryDay;
        this.truck = truck;
        this.depot = instanceToCopy.getDepot();
    }

    public Day getDeliverDay() {
        return deliveryDay;
    }

    public int getCurrentDistance() {
        return currentDistance;
    }

    public int getCurrentCharge() {
        return currentCharge;
    }

    public Truck getTruck() {
        return this.truck;
    }

    public Location getDepot() {
        return this.depot;
    }

    /**
     * Check if it is possible to add request to the list of requests. Check if the
     * capacity of the truck is respected. Check if the max distance of the truck is
     * respected.
     * 
     * @param request
     * @return whether the request was add or not to the list of requests
     */
    @Override
    public boolean addRequest(Request request) {
        // Ajouter le cout de la tournée
        int nbMachines = request.getNbMachines();
        int machineSize = request.getMachine().getSize();
        int requestSize = machineSize * nbMachines;
        int totalSize = requestSize + this.currentCharge;
        int truckCapacity = this.truck.getCapacity();
        int truckDistanceCost = this.truck.getDistanceCost();

        if (deliveryDay.getDate() < request.getFirstDay() || deliveryDay.getDate() > request.getLastDay())
            return false;

        if (totalSize > truckCapacity)
            return false;

        Location requestLocation = request.getLocation();
        int requestLocationToDepot = returnToDepot(requestLocation);
        int truckMaxDistance = this.truck.getMaxDistance();

        if (this.currentDistance == 0) {
            int locationToDepotRoundTrip = requestLocationToDepot * 2;

            if (locationToDepotRoundTrip > truckMaxDistance)
                return false;

            this.requests.add(request);
            this.currentCharge += requestSize;
            this.currentDistance += locationToDepotRoundTrip;
            request.setDeliveryDate(this.deliveryDay.getDate());
            this.totalCost += this.currentDistance * truckDistanceCost;

            return true;
        }

        Location lastLocation = this.requests.getLast().getLocation();
        int lastLocationToRequestLocation = lastLocation.getDistanceTo(requestLocation);
        int lastLocationToDepot = returnToDepot(lastLocation);
        int newDistance = lastLocationToRequestLocation - lastLocationToDepot + requestLocationToDepot;
        int lastDistance = this.currentDistance * truckDistanceCost;

        if (newDistance + this.currentDistance > truckMaxDistance)
            return false;

        this.currentCharge += requestSize;
        this.currentDistance += newDistance;
        request.setDeliveryDate(this.deliveryDay.getDate());
        this.requests.add(request);
        this.totalCost += this.currentDistance * truckDistanceCost - lastDistance;

        return true;
    }

    /**
     * Distance between location and the depot
     * 
     * @param location
     * @return the distance between location and the depot
     */
    public int returnToDepot(Location location) {
        return location.getDistanceTo(this.depot);
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n----- Delivery Round -----\n";
        str += "Current charge : " + currentCharge + "\n";
        str += "Current distance : " + currentDistance + "\n";
        str += "Date : " + deliveryDay.getDate() + "\n";
        str += depot;
        str += truck;
        str += super.toString();
        str += "--------------------------\n";
        return str;
    }

    public static void main(String[] args) {

        /*
         * // Création d'une delivery round simple Location depot = new Location(0, 0,
         * 0); Truck truck = new Truck(1, 20, 50, 2, 100); Day deliveryDay = new Day(0);
         * DeliveryRound dr = new DeliveryRound(truck, depot, deliveryDay); //
         * System.out.println(dr.toString());
         * 
         * // Ajout d'une requete qui dépasse la capacité du camion Location l = new
         * Location(1, 1, 0); Machine m = new Machine(1, 10, 20); Request r = new
         * Request(1, l, 1, 3, m, 3); // System.out.println(dr.addRequest(r));
         * 
         * // Ajout d'une requete qui ne dépasse pas la capacité mais qui dépasse la //
         * distance Location l2 = new Location(2, 1000, 0); Request r2 = new Request(2,
         * l2, 1, 3, m, 1); // System.out.println(dr.addRequest(r2));
         * 
         * // Ajout d'une requete qui est ajoutée à partir du dépôt Request r3 = new
         * Request(3, l, 1, 3, m, 1); // System.out.println(dr.addRequest(r3)); //
         * System.out.println(dr.toString());
         * 
         * // Ajout d'une 2e tournée Location l3 = new Location(3, 2, 0); Request r4 =
         * new Request(4, l3, 1, 3, m, 1); System.out.println(dr.addRequest(r3));
         * System.out.println(dr.addRequest(r4)); System.out.println(dr.toString());
         */
    }

}
