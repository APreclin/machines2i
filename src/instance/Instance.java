package instance;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

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
    private LinkedHashMap<Integer, Technician> technicians;
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
        this.technicians = new LinkedHashMap<Integer, Technician>();
        this.locations = new LinkedHashMap<Integer, Location>();
        this.machines = new LinkedHashMap<Integer, Machine>();
        this.requests = new LinkedHashMap<Integer, Request>();
        this.truck = new Truck();
    }

    public Instance(String dataset, String name, int days, int technicianDistanceCost, int technicianDayCost,
            int technicianCost, Truck truck) {
        this();
        this.dataset = dataset;
        this.name = name;
        this.days = days;
        this.technicianDistanceCost = technicianDistanceCost;
        this.technicianDayCost = technicianDayCost;
        this.technicianCost = technicianCost;
        this.technicians = new LinkedHashMap<Integer, Technician>();
        this.locations = new LinkedHashMap<Integer, Location>();
        this.machines = new LinkedHashMap<Integer, Machine>();
        this.requests = new LinkedHashMap<Integer, Request>();
        this.truck = truck;
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

    public LinkedHashMap<Integer, Technician> getTechnicians() {
        return new LinkedHashMap<Integer, Technician>(technicians);
    }

    public LinkedHashMap<Integer, Location> getLocations() {
        return new LinkedHashMap<Integer, Location>(locations);
    }

    public LinkedHashMap<Integer, Machine> getMachines() {
        return new LinkedHashMap<Integer, Machine>(machines);
    }

    public LinkedHashMap<Integer, Request> getRequests() {
        return new LinkedHashMap<Integer, Request>(requests);
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
    
    public boolean addLocation(Location location) {
        if (location == null)
            return false;

        int id = location.getId();
        Location tempLocation = this.locations.put(id, location);
        if (tempLocation == null)
            return true;

        this.locations.put(id, tempLocation);
        return false;
    }

    public boolean addMachine(Machine machine) {
        if (machine == null)
            return false;

        int id = machine.getId();
        Machine tempMachine = this.machines.put(id, machine);
        if (tempMachine == null)
            return true;

        this.machines.put(id, tempMachine);
        return false;
    }

    public boolean addRequest(Request request) {
        if (request == null)
            return false;

        int id = request.getId();
        Request tempRequest = this.requests.put(id, request);
        if (tempRequest == null)
            return true;

        this.requests.put(id, tempRequest);
        return false;
    }

    public boolean addTechnician(Technician technician) {
        if (technician == null)
            return false;

        int id = technician.getId();
        Technician tempTechnician = this.technicians.put(id, technician);
        if (tempTechnician == null)
            return true;

        this.technicians.put(id, tempTechnician);
        return false;
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Instance -----\n\n";
        str += "Dataset : " + dataset + "\n";
        str += "Name : " + name + "\n";
        str += "Days : " + days + "\n\n";
        str += truck.toString();
        str += "Technician Cost : " + technicianCost + "\n";
        str += "Technician Day Cost : " + technicianDayCost + "\n";
        str += "Technician Distance Cost : " + technicianDistanceCost + "\n\n";
        str += "Liste des machines : \n\n";
        for (Map.Entry<Integer, Machine> set : this.machines.entrySet())
            str += set.getValue() + "\n";
        str += "Liste des locations : \n\n";
        for (Map.Entry<Integer, Location> set : this.locations.entrySet())
            str += set.getValue() + "\n";
        str += "Liste des requests : \n\n";
        for (Map.Entry<Integer, Request> set : this.requests.entrySet())
            str += set.getValue() + "\n";
        str += "Liste des technicians : \n\n";
        for (Map.Entry<Integer, Technician> set : this.technicians.entrySet())
            str += set.getValue() + "\n";
        str += "------------------------\n\n";
        return str;
    }
}
