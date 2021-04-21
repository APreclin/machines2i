package solution;

import java.util.LinkedList;

import instance.Instance;
import instance.reseau.Request;
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

    // **************   TRAITEMENTS   ******************//

    //*****  AJOUTS DANS LES TOURNEES *******/

    public void addRequestNewDeliveryRound (Request RequestToAdd) {
        DeliveryRound tourneeTemp = new DeliveryRound(instance);
        tourneeTemp.addRequest(RequestToAdd);
        if (deliveryRounds == null) {
            deliveryRounds = new LinkedList<DeliveryRound>();
        }
        deliveryRounds.push(tourneeTemp);        
        totalCost += tourneeTemp.getTotalCost();
    }

    public void addRequestNewInstallationRound (Request RequestToAdd) {
        InstallationRound tourneeTemp = new InstallationRound(instance.getTechnician(RequestToAdd.getMachine()));
        tourneeTemp.addRequest(RequestToAdd);
        if (installationRounds == null) {
            installationRounds = new LinkedList<InstallationRound>();
        }
        installationRounds.push(tourneeTemp);        
        totalCost += tourneeTemp.getTotalCost();
    }

    public boolean addRequestExistingDeliveryRound (Request RequestToAdd) {
        if (deliveryRounds == null)
            return false;
        for (Round t : deliveryRounds) {
            int ancienCout = t.getTotalCost();
            if (t.addRequest(RequestToAdd)) {
                totalCost += t.getTotalCost() - ancienCout;
                return true;
            }
        }
        return false;
    }

    public boolean addRequestExistingInstallationRound (Request RequestToAdd) {
        if (installationRounds == null)
            return false;
        for (InstallationRound t : installationRounds) {
            int ancienCout = t.getTotalCost();
            if (t.addRequest(RequestToAdd)) {
                totalCost += t.getTotalCost() - ancienCout;
                return true;
            }
        }
        return false;
    }

    //*****  FIN AJOUTS DANS LES TOURNEES *******/

    // ************** FIN TRAITEMENTS ******************//

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

    @Override
    public String toString() {
        return "Solution [deliveryRounds=" + deliveryRounds + ", idleMachineCosts=" + idleMachineCosts
                + ", installationRounds=" + installationRounds + ", instance=" + instance + ", nbTechniciansUsed="
                + nbTechniciansUsed + ", nbTruckDays=" + nbTruckDays + ", nbTrucksUsed=" + nbTrucksUsed
                + ", technicianDistance=" + technicianDistance + ", totalCost=" + totalCost + ", truckDistance="
                + truckDistance + "]";
    }

}
