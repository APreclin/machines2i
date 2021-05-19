package solution;

import java.util.LinkedList;

import instance.Instance;
import instance.reseau.Machine;
import instance.reseau.Request;
import instance.reseau.Technician;
import solution.tournee.DeliveryRound;
import solution.tournee.InstallationRound;
import solution.tournee.Round;

public class Solution {
    private Instance instance;
    private LinkedList<InstallationRound> installationRounds;
    private LinkedList<DeliveryRound> deliveryRounds;
    private Integer truckDistance;
    private Integer nbTruckDays;
    private Integer nbTrucksUsed;
    private Integer technicianDistance;
    private Integer nbTechniciansDays;
    private Integer nbTechniciansUsed;
    private Integer idleMachineCosts;
    private Integer totalCost;

    public Solution() {
        instance = new Instance();
        truckDistance = 0;
        nbTruckDays = 0;
        nbTrucksUsed = 0;
        technicianDistance = 0;
        nbTechniciansDays = 0;
        nbTechniciansUsed = 0;
        idleMachineCosts = 0;
        totalCost = 0;
        installationRounds = new LinkedList<InstallationRound>();
        deliveryRounds = new LinkedList<DeliveryRound>();
    }

    public Solution(Instance instanceToCopy) {
        this();
        instance = instanceToCopy;
    }

    public Integer getTruckDistance() {
        return truckDistance;
    }

    public Integer getNbTruckDays() {
        return nbTruckDays;
    }

    public Integer getNbTrucksUsed() {
        return nbTrucksUsed;
    }

    public Integer getTechnicianDistance() {
        return technicianDistance;
    }

    public Integer getNbTechniciansDays() {
        return nbTechniciansDays;
    }

    public Integer getNbTechniciansUsed() {
        return nbTechniciansUsed;
    }

    public Integer getIdleMachineCosts() {
        return idleMachineCosts;
    }

    public Integer getTotalCost() {
        return this.totalCost;
    }

    public Instance getInstance() {
        return instance;
    }

    public LinkedList<DeliveryRound> getDeliveryRounds() {
        return new LinkedList<DeliveryRound>(deliveryRounds);
    }

    public LinkedList<InstallationRound> getInstallationRounds() {
        return new LinkedList<InstallationRound>(installationRounds);
    }

    /**
     * Adds requestToAdd to a new DeliveryRound. The new DeliveryRound is added to
     * the deliveryRounds list
     * 
     * @param requestToAdd
     */
    public void addRequestNewDeliveryRound(Request requestToAdd) {
        DeliveryRound tempRound = new DeliveryRound(instance);
        tempRound.addRequest(requestToAdd);

        deliveryRounds.push(tempRound);
        totalCost += tempRound.getTotalCost();
    }

    /**
     * Adds requestToAdd to a new InstallationRound. The new InstallationRound is
     * added to the installationRounds list
     * 
     * @param requestToAdd
     */
    public void addRequestNewInstallationRound(Request requestToAdd) {
        Machine machine = requestToAdd.getMachine();
        Technician technician = instance.getTechnician(machine);
        InstallationRound tempRound = new InstallationRound(technician, requestToAdd.getFirstDay());
        tempRound.addRequest(requestToAdd);

        installationRounds.push(tempRound);
        totalCost += tempRound.getTotalCost();
    }

    /**
     * Adds requestToAdd to an existing DeliveryRound if deliveryRounds is not null
     * 
     * @param requestToAdd
     * @return whether the requestToAdd was added or not
     */
    public boolean addRequestExistingDeliveryRound(Request requestToAdd) {
        if (deliveryRounds.isEmpty())
            return false;

        for (Round t : deliveryRounds) {
            int ancienCout = t.getTotalCost();
            if (t.addRequest(requestToAdd)) {
                totalCost += t.getTotalCost() - ancienCout;
                return true;
            }
        }

        return false;
    }

    /**
     * Adds requestToAdd to an existing InstallationRound if installationRounds is
     * not null
     * 
     * @param requestToAdd
     * @return whether the requestToAdd was added or not
     */
    public boolean addRequestExistingInstallationRound(Request requestToAdd) {
        if (installationRounds.isEmpty())
            return false;

        for (InstallationRound t : installationRounds) {
            int ancienCout = t.getTotalCost();
            if (t.addRequest(requestToAdd)) {
                totalCost += t.getTotalCost() - ancienCout;
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Solution [deliveryRounds=" + deliveryRounds + ", idleMachineCosts=" + idleMachineCosts
                + ", installationRounds=" + installationRounds + ", instance=" + instance + ", nbTechniciansDays="
                + nbTechniciansDays + ", nbTechniciansUsed=" + nbTechniciansUsed + ", nbTruckDays=" + nbTruckDays
                + ", nbTrucksUsed=" + nbTrucksUsed + ", technicianDistance=" + technicianDistance + ", totalCost="
                + totalCost + ", truckDistance=" + truckDistance + "]";
    }

}
