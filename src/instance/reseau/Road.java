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
        distance = (int) Math.round(Math.sqrt(
                (Math.pow(destination.getX() - origin.getX(), 2) + Math.pow(destination.getY() - origin.getY(), 2))));
    }

    public int getDistance() {
        return distance;
    }

    public static void main(String[] args) {
        // TODO: test unitaire road
    }
}
