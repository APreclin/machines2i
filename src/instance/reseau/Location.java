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
        this.roadsStartingBy = new HashMap<>();
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

    @Override
    public String toString() {
        String str = "";
        str += "----- Location n°" + id + " -----\n\n";
        str += "ID = " + id + "\n";
        str += "X = " + x + "\n";
        str += "Y = " + y + "\n\n";
        str += "------------------------\n";
        return str;
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
        Road tempRoad = new Road(this, destination);
        if (roadsStartingBy.containsValue(tempRoad)) {
            return tempRoad.getDistance();
        } else
            return Integer.MAX_VALUE;
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
        else {
            roadsStartingBy.put(destination, RoadTemp);
            return false;
        }

    }

    public static void main(String[] args) {
        Location l1 = new Location(0, 0, 0);
        Location l2 = new Location(1, 0, 10);
        Location l3 = new Location(1, 0, 10);
        Road r1 = new Road(l1, l2);
        Road r2 = new Road(l2, l3);
        l1.addRoad(l2);
        l2.addRoad(l3);
        System.out.println("Distance l1-> l2 : " + l1.getDistanceTo(l2));  
        System.out.println("Distance l2-> l3 : " + l2.getDistanceTo(l3));  
    }
}
