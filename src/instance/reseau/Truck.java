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
        this.cost = 0;
    }

    public Truck(int capacity, int maxDistance, int distanceCost, int dayCost) {
        this();
        this.capacity = capacity;
        this.maxDistance = maxDistance;
        this.distanceCost = distanceCost;
        this.dayCost = dayCost;
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Truck -----\n\n";
        str += "Capacity : " + capacity + "\n";
        str += "Max distance : " + maxDistance + "\n";
        str += "Distance cost : " + distanceCost + "\n";
        str += "Day cost : " + dayCost + "\n";
        str += "Cost : " + cost + "\n";
        str += "-----------------\n\n";
        return str;
    }

}
