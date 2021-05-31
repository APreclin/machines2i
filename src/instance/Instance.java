package instance;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
    private int truckCost;
    private int technicianCost;
    private LinkedHashMap<Integer, Technician> technicians;
    private LinkedHashMap<Integer, Location> locations;
    private LinkedHashMap<Integer, Machine> machines;
    private LinkedHashMap<Integer, Request> requests;
    private LinkedHashMap<Integer, Truck> trucks;
    private Truck truckModel;

    public Instance() {
        this.dataset = "";
        this.name = "";
        this.days = 0;
        this.truckCost = 0;
        this.technicianCost = 0;
        this.technicians = new LinkedHashMap<Integer, Technician>();
        this.locations = new LinkedHashMap<Integer, Location>();
        this.machines = new LinkedHashMap<Integer, Machine>();
        this.requests = new LinkedHashMap<Integer, Request>();
        this.trucks = new LinkedHashMap<Integer, Truck>();
        this.truckModel = new Truck();
    }

    public Instance(String dataset, String name, int days, int technicianCost, Truck truck, int truckCost) {
        this();
        this.dataset = dataset;
        this.name = name;
        this.days = days;
        this.truckCost = truckCost;
        this.technicianCost = technicianCost;
        this.technicians = new LinkedHashMap<Integer, Technician>();
        this.locations = new LinkedHashMap<Integer, Location>();
        this.machines = new LinkedHashMap<Integer, Machine>();
        this.requests = new LinkedHashMap<Integer, Request>();
        this.truckModel = truck;
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

    public int getTruckCost() {
        return truckCost;
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

    public Truck getTruckModel() {
        return truckModel;
    }

    public Location getDepot() {
        if (locations == null)
            return null;

        return locations.get(1);

    }

    /**
     * Get Technicians able to install the machine passed in parameter
     * 
     * @param machine the machine we want to install
     * @return a HashMap of Technician able to install 'machine'
     */
    public HashMap<Integer, Technician> getTechnicians(Machine machine) {
        HashMap<Integer, Technician> techniciansList = new HashMap<Integer, Technician>();

        for (Technician tech : technicians.values()) {
            if (tech.checkMachineAbility(machine.getId()))
                techniciansList.put(tech.getId(), tech);
        }

        return techniciansList;
    }

    /**
     * Add Location if not already in locations HashMap
     * 
     * @param location the location we want to add
     * @return whether the location has been added or not to our locations HashMap
     */
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

    /**
     * Add Truck if not already in trucks HashMap
     * 
     * @param truck the truck we want to add
     * @return wheter the truck has been added or not to our trucks HashMap
     */
    public boolean addTruck(Truck truck) {
        if (truck == null)
            return false;

        int id = truck.getId();
        Truck truckTemp = this.trucks.put(id, truck);
        if (truckTemp == null)
            return true;

        this.trucks.put(id, truck);

        return false;
    }

    /**
     * Add Machine if not already in machines HashMap
     * 
     * @param machine the machine we want to add
     * @return wheter the machine has been added or not to our machines HashMap
     */
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

    /**
     * Add Request if not already in requests HashMap
     * 
     * @param machine the request we want to add
     * @return wheter the request has been added or not to our requests HashMap
     */
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

    /**
     * Add Technician if not already in technicians HashMap
     * 
     * @param technician the technician we want to add
     * @return whether the technician has been added or not to our technicians
     *         HashMap
     */
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

    /**
     * Get the first Technician able to install the machine passed in parameter
     * 
     * @param machine the machine we want to install
     * @return the first Technician able to install 'machine'
     */
    public Technician getTechnician(Machine machine) {
        for (Technician tech : technicians.values()) {
            if (tech.checkMachineAbility(machine.getId()))
                return tech;
        }

        return new Technician();
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Instance -----\n\n";
        str += "Dataset : " + dataset + "\n";
        str += "Name : " + name + "\n";
        str += "Days : " + days + "\n\n";
        str += truckModel.toString();
        str += "Technician Cost : " + technicianCost + "\n";
        str += "Truck Cost : " + truckCost + "\n\n";
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
