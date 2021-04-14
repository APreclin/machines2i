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

    @Override
    public String toString() {
        return "Truck [capacity=" + capacity + ", cost=" + cost + ", dayCost=" + dayCost + ", distanceCost="
                + distanceCost + ", maxDistance=" + maxDistance + "]";
    }

}
