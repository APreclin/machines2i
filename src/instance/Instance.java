package instance;

import java.util.LinkedList;

import instance.reseau.Location;
import instance.reseau.Machine;
import instance.reseau.Technician;
import instance.reseau.Truck;
import solution.Request;

public class Instance {
    private String dataset;
    private String name;
    private int days;
    private int technicianDistanceCost;
    private int technicianDayCost;
    private int technicianCost;
    private LinkedList<Technician> technicians;
    private LinkedList<Location> locations;
    private LinkedList<Machine> machines;
    private LinkedList<Request> requests;
    private Truck truck;

    public Instance() {
        this.dataset = "";
        this.name = "";
        this.days = 0;
        this.technicianDistanceCost = 0;
        this.technicianDayCost = 0;
        this.technicianCost = 0;
        this.technicians = new LinkedList<Technician>();
        this.locations = new LinkedList<Location>();
        this.machines = new LinkedList<Machine>();
        this.requests = new LinkedList<Request>();
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

    public LinkedList<Technician> getTechnicians() {
        return new LinkedList<Technician>(technicians);
    }

    public LinkedList<Location> getLocations() {
        return new LinkedList<Location>(locations);
    }

    public LinkedList<Machine> getMachines() {
        return new LinkedList<Machine>(machines);
    }

    public LinkedList<Request> getRequests() {
        return new LinkedList<Request>(requests);
    }

    public Truck getTruck() {
        return truck;
    }

    @Override
    public String toString() {
        return "Instance [dataset=" + dataset + ", days=" + days + ", locations=" + locations + ", machines=" + machines
                + ", name=" + name + ", requests=" + requests + ", technicianCost=" + technicianCost
                + ", technicianDayCost=" + technicianDayCost + ", technicianDistanceCost=" + technicianDistanceCost
                + ", technicians=" + technicians + ", truck=" + truck + "]";
    }
}
