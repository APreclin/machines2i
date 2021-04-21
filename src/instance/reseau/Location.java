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
    }

    public Location(int id, int x, int y) {
        this();
        this.id = id;
        this.x = x;
        this.y = y;
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

    public int getDistanceTo(Location destination) {
        if (destination == null)
            return Integer.MAX_VALUE;
        Road tempRoad = new Road(this, destination);
        if (roadsStartingBy.containsValue(tempRoad)) {
            return tempRoad.getDistance();
        }
        else
            return Integer.MAX_VALUE;
    }

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
}
