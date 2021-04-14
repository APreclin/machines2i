package instance.reseau;

import java.util.HashMap;
import java.util.Map;

public class Technician {

    private int id;
    private Location home;
    private int maxDistance;
    private int maxRequests;
    private HashMap<Integer, Boolean> installation;

    public Technician() {
        this.id = 0;
        this.home = new Location();
        this.maxDistance = 0;
        this.maxRequests = 0;
        this.installation = new HashMap<Integer, Boolean>();
    }

    public Technician(int id, Location location, int maxDistance, int maxRequests) {
        this();
        this.id = id;
        this.home = location;
        this.maxDistance = maxDistance;
        this.maxRequests = maxRequests;
        // TODO: Récupérer le tableau de capacité du technicien
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
        Technician other = (Technician) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Technician -----\n\n";
        str += "ID : " + id + "\n";
        str += home.toString();
        str += "Maximum distance : " + maxDistance + "\n";
        str += "Maximum requests : " + maxRequests + "\n";
        str += "Installation possible : \n[";
        for (Map.Entry<Integer, Boolean> set : installation.entrySet()) {
            str += set.getKey() + " - " + set.getValue() + "\n";
        }
        str += "]";
        str += "----------------------\n\n";
        return str;
    }

}
