package instance.reseau;

public class Truck {

    private int capacity;
    private int maxDistance;
    private int distanceCost;
    private int dayCost;
    private int cost;

    public Truck() {
        this.capacity = 0;
        this.maxDistance = 0;
        this.distanceCost = 0;
        this.dayCost = 0;
        //TODO : mettre cost dans instance
        this.cost = 0;
    }

    public Truck(int capacity, int maxDistance, int distanceCost, int dayCost, int cost) {
        this();
        this.capacity = capacity;
        this.maxDistance = maxDistance;
        this.distanceCost = distanceCost;
        this.dayCost = dayCost;
        this.cost = cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public int getDistanceCost() {
        return distanceCost;
    }

    public int getDayCost() {
        return dayCost;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Truck -----\n\n";
        str += "Capacity : " + capacity + "\n";
        str += "Max distance : " + maxDistance + "\n";
        str += "Distance cost : " + distanceCost + "\n";
        str += "Day cost : " + dayCost + "\n";
        str += "Cost : " + cost + "\n\n";
        str += "-----------------\n";
        return str;
    }

    public static void main(String[] args) {

        // Création d'un camion simple
        Truck t = new Truck(20, 50, 5, 10, 10);
        System.out.println(t.toString());
    }
}
