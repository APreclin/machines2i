package solution;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import instance.Instance;
import instance.reseau.Machine;
import instance.reseau.Request;
import instance.reseau.Technician;
import io.InstanceReader;
import io.exception.ReaderException;
import solution.tournee.DeliveryRound;
import solution.tournee.InstallationRound;

public class Solution {
    private int idRound;
    private Instance instance;
    private LinkedHashMap<Integer, InstallationRound> installationRounds;
    private LinkedHashMap<Integer, DeliveryRound> deliveryRounds;
    private Integer truckDistance;
    private Integer nbTruckDays;
    private Integer nbTrucksUsed;
    private Integer technicianDistance;
    private Integer nbTechniciansDays;
    private Integer nbTechniciansUsed;
    private Integer idleMachineCosts;
    private Integer totalCost;

    public Solution() {
        idRound = 0;
        instance = new Instance();
        truckDistance = 0;
        nbTruckDays = 0;
        nbTrucksUsed = 0;
        technicianDistance = 0;
        nbTechniciansDays = 0;
        nbTechniciansUsed = 0;
        idleMachineCosts = 0;
        totalCost = 0;
        installationRounds = new LinkedHashMap<Integer, InstallationRound>();
        deliveryRounds = new LinkedHashMap<Integer, DeliveryRound>();
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

    public LinkedHashMap<Integer, DeliveryRound> getDeliveryRounds() {
        return new LinkedHashMap<Integer, DeliveryRound>(deliveryRounds);
    }

    public LinkedHashMap<Integer, InstallationRound> getInstallationRounds() {
        return new LinkedHashMap<Integer, InstallationRound>(installationRounds);
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

        deliveryRounds.put(idRound, tempRound);
        totalCost += tempRound.getTotalCost();
        truckDistance += tempRound.getCurrentDistance();
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

        installationRounds.put(idRound, tempRound);
        totalCost += tempRound.getTotalCost();
        technicianDistance += tempRound.getCoveredDistance();
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

        for (DeliveryRound t : deliveryRounds.values()) {
            int oldCost = t.getTotalCost();
            int oldDistance = t.getCurrentDistance();
            if (t.addRequest(requestToAdd)) {
                totalCost += t.getTotalCost() - oldCost; // maj du cout (rempalcement de l'ancien)
                truckDistance += t.getCurrentDistance() - oldDistance;
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

        for (InstallationRound t : installationRounds.values()) {
            int oldCost = t.getTotalCost();
            int oldDistance = t.getCoveredDistance();
            if (t.addRequest(requestToAdd)) {
                totalCost += t.getTotalCost() - oldCost;
                technicianDistance += t.getCoveredDistance() - oldDistance;

                return true;
            }
        }

        return false;
    }

    /**
     * Calculate the penalities by days and add them to the total cost
     */
    public void calculPenalities() {
        if (installationRounds.isEmpty() || deliveryRounds.isEmpty())
            return;

        int totalPenalities = 0;

        for (InstallationRound ir : installationRounds.values()) {
            for (Request r : ir.getRequests()) {

                int penality = r.getMachine().getPenaltyByDay();
                int deliveryDate = r.getDeliveryDate();
                int installationDate = r.getInstallationDate();
                int diff = installationDate - deliveryDate;

                if (diff >= 2)
                    totalPenalities += (diff - 1) * penality;
            }
        }

        totalCost += totalPenalities;
    }

    @Override
    public String toString() {
        return "Solution [deliveryRounds=" + deliveryRounds + ", idleMachineCosts=" + idleMachineCosts
                + ", installationRounds=" + installationRounds + ", instance=" + instance + ", nbTechniciansDays="
                + nbTechniciansDays + ", nbTechniciansUsed=" + nbTechniciansUsed + ", nbTruckDays=" + nbTruckDays
                + ", nbTrucksUsed=" + nbTrucksUsed + ", technicianDistance=" + technicianDistance + ", totalCost="
                + totalCost + ", truckDistance=" + truckDistance + "]";
    }

    public static void main(String[] args) {
        Instance i1 = new Instance();
        try {
            InstanceReader reader = new InstanceReader();
            i1 = reader.readInstance();
            System.out.println("Instance lue avec success !");
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
        Solution s1 = new Solution(i1);
        for (Request r : i1.getRequests().values()) {
            if (s1.addRequestExistingDeliveryRound(r)) {
                s1.addRequestExistingInstallationRound(r);
            } else {
                s1.addRequestNewDeliveryRound(r);
                s1.addRequestNewInstallationRound(r);
            }
        }
        System.out.println(s1);
    }

}
