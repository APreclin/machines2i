package instance;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import instance.reseau.Location;
import instance.reseau.Machine;
import instance.reseau.Request;
import instance.reseau.Technician;
import instance.reseau.Truck;

public class Instance {
    private String dataset;
    private String name;
    private int days;
    private int technicianDistanceCost;
    private int technicianDayCost;
    private int technicianCost;
    private HashMap<Integer, Technician> technicians;
    private LinkedHashMap<Integer, Location> locations;
    private LinkedHashMap<Integer, Machine> machines;
    private LinkedHashMap<Integer, Request> requests;
    private Truck truck;

    public Instance() {
        this.dataset = "";
        this.name = "";
        this.days = 0;
        this.technicianDistanceCost = 0;
        this.technicianDayCost = 0;
        this.technicianCost = 0;
        this.technicians = new HashMap<Integer, Technician>();
        this.locations = new LinkedHashMap<Integer, Location>();
        this.machines = new LinkedHashMap<Integer, Machine>();
        this.requests = new LinkedHashMap<Integer, Request>();
        this.truck = new Truck();
    }

    public String getDataset() {
        return dataset;
    }

    public String getName() {
        return name;
    }

    public int getDays() {
        return days;
    }

    public int getTechnicianDistanceCost() {
        return technicianDistanceCost;
    }

    public int getTechnicianDayCost() {
        return technicianDayCost;
    }

    public int getTechnicianCost() {
        return technicianCost;
    }

    public HashMap<Integer, Technician> getTechnicians() {
        return new HashMap<Integer, Technician>(technicians);
    }

    public LinkedHashMap<Integer, Location> getLocations() {
        return new LinkedHashMap<Integer, Location>(locations);
    }

    public LinkedHashMap<Integer, Machine> getMachines() {
        return new LinkedHashMap<Integer, Machine>(machines);
    }

    public LinkedHashMap<Integer, Request> getRequests() {
        LinkedHashMap<Integer, Request> clientsTemp = new LinkedHashMap<Integer, Request>(requests);
        return clientsTemp;
    }

    public Truck getTruck() {
        return truck;
    }

    public Location getDepot() {
        if (locations != null)
            return locations.get(1);
        else
            return null;

    }

    public HashMap<Integer, Technician> getTechnicians(Machine machine) {
        HashMap<Integer, Technician> retour = new HashMap<Integer, Technician>();
        for (Technician tech: technicians.values()) {
            //TODO : vérifier que c'est le bon type de machine et ajouter si oui
        }
        return retour;
    }

    @Override
    public String toString() {
        return "Instance [dataset=" + dataset + ", days=" + days + ", locations=" + locations + ", machines=" + machines
                + ", name=" + name + ", requests=" + requests + ", technicianCost=" + technicianCost
                + ", technicianDayCost=" + technicianDayCost + ", technicianDistanceCost=" + technicianDistanceCost
                + ", technicians=" + technicians + ", truck=" + truck + "]";
    }
}
