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
    private Integer nbTechniciansUsed;
    private Integer idleMachineCosts;
    private Integer totalCost;

    public Solution() {
        instance = new Instance();
        truckDistance = 0;
        nbTruckDays = 0;
        nbTrucksUsed = 0;
        technicianDistance = 0;
        nbTechniciansUsed = 0;
        idleMachineCosts = 0;
        totalCost = 0;
    }

    public Solution(Instance instanceToCopy) {
        instance = instanceToCopy;
        installationRounds = new LinkedList<InstallationRound>();
        deliveryRounds = new LinkedList<DeliveryRound>();
        totalCost = 0;
    }

    public Integer getTotalCost() {
        return this.totalCost;
    }

    public Instance getInstance() {
        return instance;
    }

    public LinkedList<DeliveryRound> getDeliveryRounds() {
        return deliveryRounds;
    }

    public LinkedList<InstallationRound> getInstallationRounds() {
        return installationRounds;
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

        if (deliveryRounds == null) {
            deliveryRounds = new LinkedList<DeliveryRound>();
        }

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

        if (installationRounds == null) {
            installationRounds = new LinkedList<InstallationRound>();
        }

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
        if (deliveryRounds == null)
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
        if (installationRounds == null)
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
                + ", installationRounds=" + installationRounds + ", instance=" + instance + ", nbTechniciansUsed="
                + nbTechniciansUsed + ", nbTruckDays=" + nbTruckDays + ", nbTrucksUsed=" + nbTrucksUsed
                + ", technicianDistance=" + technicianDistance + ", totalCost=" + totalCost + ", truckDistance="
                + truckDistance + "]";
    }

}
