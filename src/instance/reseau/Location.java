package instance.reseau;

import java.util.HashMap;

public class Location {

    private int id;
    private int x;
    private int y;
    private HashMap<Location, Road> roadsStartingBy;

    public Location() {
        this.id = 0;
        this.x = 0;
        this.y = 0;
        this.roadsStartingBy = new HashMap<Location, Road>();
    }

    public Location(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.roadsStartingBy = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public HashMap<Location, Road> getRoadsStartingBy() {
        return roadsStartingBy;
    }

    /**
     * Get distance between this location and destination
     * 
     * @param destination
     * @return the distance between this location and destination passed in
     *         parameter
     */
    public int getDistanceTo(Location destination) {
        if (destination == null)
            return Integer.MAX_VALUE;

        int x = destination.getX() - this.getX();
        int y = destination.getY() - this.getY();

        return (int) Math.round(Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2))));
    }

    /**
     * Add road between this location and destination if not already created
     * 
     * @param destination
     * @return whether the road has been created or not
     */
    public boolean addRoad(Location destination) {
        if (destination == null)
            return false;

        Road RoadTemp = roadsStartingBy.put(destination, new Road(this, destination));

        if (RoadTemp == null)
            return true;

        roadsStartingBy.put(destination, RoadTemp);
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        String str = "";
        if (id != 0)
            str += "----- Location n°" + id + " -----\n\n";
        else
            str += "---- Depôt ---- \n\n";
        str += "ID = " + id + "\n";
        str += "X = " + x + "\n";
        str += "Y = " + y + "\n\n";
        str += "------------------------\n";
        return str;
    }

    public static void main(String[] args) {

        // Création d'une location
        Location loc1 = new Location(1, 4, 5);
        System.out.println(loc1.toString());

        // Test de la fonction getDistance
        Location loc2 = new Location(2, 5, 5);
        System.out.println(loc1.getDistanceTo(loc2));

        // Test de l'ajout de route
        System.out.println(loc1.addRoad(loc2));
        System.out.println(loc1.getRoadsStartingBy().toString());
    }
}
