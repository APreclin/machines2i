package instance.reseau;

public class Road {
    private int distance;
    private Location origin;
    private Location destination;

    public Road(Location originLocation, Location destinationLocation) {
        origin = originLocation;
        destination = destinationLocation;
        distanceCalculation();
    }

    public Location getOrigin() {
        return origin;
    }

    public Location getDestination() {
        return destination;
    }

    /**
     * Calculate distance between origin and destination
     */
    private void distanceCalculation() {
        int x = destination.getX() - origin.getX();
        int y = destination.getY() - origin.getY();

        distance = (int) Math.round(Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2))));
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Road -----\n\n";
        str += origin + "\n";
        str += destination + "\n";
        str += "Distance : " + distance + "\n\n";
        str += "-----------------\n";
        return str;
    }

    public static void main(String[] args) {

        // Création d'une route simple
        Location l1 = new Location(1, 2, 3);
        Location l2 = new Location(2, 3, 3);
        Road r = new Road(l1, l2);
        System.out.println(r.toString());
    }
}
