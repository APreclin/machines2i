package solution.tournee;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import instance.Instance;
import instance.reseau.Location;
import instance.reseau.Request;
import instance.reseau.Truck;
import operateur.InRoundExchange;
import operateur.InRoundMove;
import operateur.InRoundOperator;
import operateur.InRoundOperatorType;
import solution.Day;

public class DeliveryRound extends Round {
    private Truck truck;
    private Location depot;
    private int currentCharge;
    private int currentDistance;
    private Day deliveryDay;

    private LinkedHashMap<Integer, Integer> returnToDepot;

    public DeliveryRound() {
        super();
        depot = new Location();
        this.currentCharge = 0;
        this.currentDistance = 0;
        this.returnToDepot = new LinkedHashMap<Integer, Integer>();
    }

    public DeliveryRound(Truck truck, Location depot, Day deliveryDay) {
        super();
        this.depot = depot;
        this.currentCharge = 0;
        this.currentDistance = 0;
        this.truck = truck;
        this.deliveryDay = deliveryDay;
        this.returnToDepot = new LinkedHashMap<Integer, Integer>();
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
        this.returnToDepot = new LinkedHashMap<Integer, Integer>();
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

    public LinkedHashMap<Integer, Integer> getReturnToDepot() {
        return new LinkedHashMap<>(returnToDepot);
    }

    public Location getCurrent(int position) {
        if (position == requests.size()) {
            return this.getDepot();
        } else if (isInsertionPositionValid(position)) {
            return requests.get(position).getLocation();
        } else
            return null;
    }

    public Location getPrec(int position) {
        if (position == 0) {
            return this.getDepot();
        } else if (isInsertionPositionValid(position - 1)) {
            return requests.get(position - 1).getLocation();
        } else
            return null;
    }

    public Location getNext(int position) {
        if (position == requests.size() - 1) {
            return this.getDepot();
        } else if (isPositionValid(position + 1)) {
            return requests.get(position + 1).getLocation();
        } else
            return null;
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

    @Override
    public boolean checkAddingRequest(Request request) {
        // Ajouter le cout de la tournée
        int nbMachines = request.getNbMachines();
        int machineSize = request.getMachine().getSize();
        int requestSize = machineSize * nbMachines;
        int totalSize = requestSize + this.currentCharge;
        int truckCapacity = this.truck.getCapacity();

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

            return true;
        }

        Location lastLocation = this.requests.getLast().getLocation();
        int lastLocationToRequestLocation = lastLocation.getDistanceTo(requestLocation);
        int lastLocationToDepot = returnToDepot(lastLocation);
        int newDistance = lastLocationToRequestLocation - lastLocationToDepot + requestLocationToDepot;

        if (newDistance + this.currentDistance > truckMaxDistance)
            return false;

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

    // ********************************************************************************************
    // *************** FONCTIONS POUR LA SOLUTION2
    // ******************************************
    // ********************************************************************************************

    /**
     * Check if it is possible to add request to the list of requests. Check if the
     * capacity of the truck is respected. Check if the max distance of the truck is
     * respected.
     * 
     * @param request
     * @return whether the request was add or not to the list of requests
     */
    @Override
    public boolean addRequestSolution2(Request request) {
        // Ajouter le cout de la tournée
        int nbMachines = request.getNbMachines();
        int machineSize = request.getMachine().getSize();
        int requestSize = machineSize * nbMachines;
        int totalSize = requestSize + this.currentCharge;
        int truckCapacity = this.truck.getCapacity();
        int truckDistanceCost = this.truck.getDistanceCost();

        if (deliveryDay.getDate() < request.getFirstDay() || deliveryDay.getDate() > request.getLastDay())
            return false;

        if (totalSize > truckCapacity && !continueDelivery(request))
            return false;

        Location requestLocation = request.getLocation();
        int requestLocationToDepot = returnToDepot(requestLocation);
        int truckMaxDistance = this.truck.getMaxDistance();

        if (totalSize > truckCapacity && continueDelivery(request)) {
            int locationToDepotRoundTrip = requestLocationToDepot * 2;

            this.returnToDepot.put(this.requests.size(), this.currentCharge);
            this.requests.add(request);
            this.currentCharge = requestSize;
            this.currentDistance += locationToDepotRoundTrip;
            this.totalCost += locationToDepotRoundTrip * truckDistanceCost;
            request.setDeliveryDate(this.deliveryDay.getDate());

            return true;
        }

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

    public boolean continueDelivery(Request request) {

        int nbMachines = request.getNbMachines();
        int machineSize = request.getMachine().getSize();
        int requestSize = machineSize * nbMachines;
        int truckCapacity = this.truck.getCapacity();

        Location requestLocation = request.getLocation();
        int requestLocationToDepot = returnToDepot(requestLocation);
        int truckMaxDistance = this.truck.getMaxDistance();

        if (this.currentDistance + (requestLocationToDepot * 2) > truckMaxDistance)
            return false;

        if (requestSize > truckCapacity)
            return false;

        return true;
    }

    // *********************************************************************************
    // ********* FIN DES FONCTIONS UTILISEES POUR LA SOLUTION2
    // ******************
    // *********************************************************************************

    // ********************************************************************************************
    // *************** FONCTIONS POUR LA SOLUTION3
    // ******************************************
    // ********************************************************************************************

    @Override
    public InRoundOperator getBestInRoundOperator(InRoundOperatorType type) {
        if (requests == null)
            return InRoundOperator.getInRoundOperator(type, this, 0, -1);
        InRoundOperator bestOp = InRoundOperator.getInRoundOperator(type, this, 0, 0); // Operateur impossible pour
                                                                                       // avoir un cout maximal
        for (int i = 0; i < requests.size(); i++) {
            for (int j = 0; j <= requests.size(); j++) {
                InRoundOperator newOp = InRoundOperator.getInRoundOperator(type, this, i, j);
                if (newOp.getDeltaDistance() < bestOp.getDeltaDistance()) {
                    bestOp = newOp;
                }
            }
        }
        return bestOp;
    }

    public boolean doMove(InRoundMove infos) {
        LinkedList<Request> requestsSave = this.getRequests();
        int positionI = infos.getPositionI();
        int positionJ = infos.getPositionJ();
        Request clientToMove = infos.getRequestI();
        if (infos.isMovementPossible()) {
            if (positionI < positionJ) {
                this.requests.add(positionJ, clientToMove);
                this.requests.remove(positionI);
            } else {
                this.requests.remove(positionI);
                this.requests.add(positionJ, clientToMove);
            }
            updateCapacitiesAfterInsert(infos.getPositionJ(), infos.getRequestI());
            updateCapacitiesAfterRemove(infos.getPositionI(), infos.getRequestI());
            this.totalCost += infos.getDeltaDistance() * this.truck.getDistanceCost();
            this.currentDistance += infos.getDeltaDistance();

            if (this.check()) {
                return true;
            } else {
                this.requests = requestsSave;
                this.totalCost -= infos.getDeltaDistance() * this.truck.getDistanceCost();
                this.currentDistance -= infos.getDeltaDistance();
                return false;
            }
        }
        return false;
    }

    public boolean doExchange(InRoundExchange infos) {
        LinkedList<Request> requestsSave = this.getRequests();
        int positionI = infos.getPositionI();
        int positionJ = infos.getPositionJ();
        Request clientI = infos.getRequestI();
        Request clientJ = infos.getRequestJ();
        if (infos.isMovementPossible()) {

            if (positionI < positionJ) {
                this.requests.add(positionJ, clientI);
                this.requests.remove(positionI);
                this.requests.add(positionI, clientJ);
                this.requests.remove(positionJ + 1);
            } else {
                this.requests.add(positionI, clientJ);
                this.requests.remove(positionJ);
                this.requests.add(positionJ, clientI);
                this.requests.remove(positionI + 1);
            }

            this.totalCost += infos.getDeltaDistance() * truck.getDistanceCost();
            this.currentDistance += infos.getDeltaDistance();
            updateCapacitiesAfterInsert(infos.getPositionJ(), infos.getRequestI());
            updateCapacitiesAfterRemove(infos.getPositionI(), infos.getRequestI());
            updateCapacitiesAfterInsert(infos.getPositionI(), infos.getRequestJ());
            updateCapacitiesAfterRemove(infos.getPositionJ(), infos.getRequestJ());

            if (this.check()) {
                return true;
            } else {
                this.requests = requestsSave;
                this.totalCost -= infos.getDeltaDistance() * truck.getDistanceCost();
                this.currentDistance -= infos.getDeltaDistance();
                return false;
            }

        }
        return false;
    }

    public boolean check() {
        if (calcTotalCost() != totalCost)
            return false;
        return true;
    }

    private int calcTotalCost() {
        int totalRealDistance = 0;
        if (requests.size() == 0)
            return 0;
        Request lastRequestDone = requests.getFirst();
        for (Request r : requests) {
            if (r.equals(requests.getFirst()))
                totalRealDistance += depot.getDistanceTo(r.getLocation());
            else
                totalRealDistance += lastRequestDone.getDistanceTo(r);
            lastRequestDone = r;
        }
        if (!requests.isEmpty())
            totalRealDistance += requests.getLast().getLocation().getDistanceTo(depot);

        return totalRealDistance * truck.getDistanceCost();
    }

    public boolean checkInsertCapacities(int position, Request r) {
        if (!returnToDepot.isEmpty()) {
            int charge = Integer.MAX_VALUE;
            do {
                try {
                    charge = returnToDepot.get(position);
                } catch (IndexOutOfBoundsException e) {
                    position++;
                }
            } while(charge == Integer.MAX_VALUE);
            int machinesSize = r.getNbMachines() * r.getMachine().getSize();
            if (machinesSize + charge <= this.truck.getCapacity()) {
                return true;
            }
            else
                return false;
        }
        else {
            int machinesSize = r.getNbMachines() * r.getMachine().getSize();
            if (currentCharge + machinesSize <= truck.getCapacity())
                return true;
            else
                return false;
        }
    }

    public void updateCapacitiesAfterInsert(int position, Request r) {
        if (!returnToDepot.isEmpty()) {
            int charge = Integer.MAX_VALUE;
            do {
                try {
                    charge = returnToDepot.get(position);
                } catch (IndexOutOfBoundsException e) {
                    position++;
                }
            } while(charge == Integer.MAX_VALUE);
            int machinesSize = r.getNbMachines() * r.getMachine().getSize();
            returnToDepot.put(position, charge-machinesSize);
        }
    }

    public void updateCapacitiesAfterRemove(int position, Request r) {
        if (!returnToDepot.isEmpty()) {
            int charge = Integer.MAX_VALUE;
            do {
                try {
                    charge = returnToDepot.get(position);
                } catch (IndexOutOfBoundsException e) {
                    position++;
                }
            } while(charge == Integer.MAX_VALUE);
            int machinesSize = r.getNbMachines() * r.getMachine().getSize();
            returnToDepot.put(position, charge+machinesSize);
        }
    }

    // ********************************************************************************************
    // ************ FIN DES FONCTIONS POUR LA SOLUTION3
    // *************************************
    // ********************************************************************************************

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
