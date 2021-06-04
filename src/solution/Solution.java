package solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import instance.Instance;
import instance.reseau.Machine;
import instance.reseau.Request;
import instance.reseau.Technician;
import instance.reseau.Truck;
import solution.tournee.DeliveryRound;
import solution.tournee.InstallationRound;

public class Solution {
    private Instance instance;
    private LinkedHashMap<Integer, Day> days;
    private Integer truckDistance;
    private Integer nbTruckDays;
    private Integer nbTrucksUsed;
    private Integer technicianDistance;
    private Integer nbTechniciansDays;
    private Integer nbTechniciansUsed;
    private Integer idleMachineCosts;
    private Integer totalCost;
    private Integer idTrucks = 0;

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
        days = new LinkedHashMap<Integer, Day>();
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

    public LinkedHashMap<Integer, Day> getDays() {
        return new LinkedHashMap<Integer, Day>(days);
    }

    public HashSet<DeliveryRound> getDeliveryRounds() {
        HashSet<DeliveryRound> deliveryRounds = new HashSet<>();
        for (Day d : days.values()) {
            for (DeliveryRound t : d.getDeliveryRounds()) {
                deliveryRounds.add(t);
            }
        }
        return deliveryRounds;
    }

    public HashSet<InstallationRound> getInstallationRounds() {
        HashSet<InstallationRound> installatRounds = new HashSet<>();
        for (Day d : days.values()) {
            for (InstallationRound t : d.getInstallationRounds()) {
                installatRounds.add(t);
            }
        }
        return installatRounds;
    }

    /**
     * Adds requestToAdd to a new DeliveryRound. The new DeliveryRound is added to
     * the deliveryRounds list
     * 
     * @param requestToAdd
     */
    public void addRequestNewDeliveryRound(Request requestToAdd) {
        int date = requestToAdd.getFirstDay();
        Day deliveryDay = new Day(date);
        Truck truck = createNewTruck();

        DeliveryRound tempRound = new DeliveryRound(truck, instance, deliveryDay);
        tempRound.addRequest(requestToAdd);

        deliveryDay = this.addDay(deliveryDay);
        deliveryDay.addDeliveryRound(tempRound);
        deliveryDay.addTruck();

        totalCost += tempRound.getTotalCost();
        totalCost += truck.getDayCost();
        truckDistance += tempRound.getCurrentDistance();
    }

    public Truck createNewTruck() {

        Truck truck = new Truck(++idTrucks, instance.getTruckModel());
        instance.addTruck(truck);
        totalCost += instance.getTruckCost();
        nbTruckDays++;

        return truck;
    }

    /**
     * Adds requestToAdd to a new InstallationRound. The new InstallationRound is
     * added to the installationRounds list
     * 
     * @param requestToAdd
     */
    public void addRequestNewInstallationRound(Request requestToAdd) {
        Machine machine = requestToAdd.getMachine();
        HashMap<Integer, Technician> technicians = instance.getTechnicians(machine);
        Day installationDay = new Day(requestToAdd.getDeliveryDate() + 1);

        for (Technician tech : technicians.values()) {
            InstallationRound tempRound = new InstallationRound(tech, installationDay);

            if (!tech.getIsUsed() && tempRound.addRequest(requestToAdd)) {

                tech.addInstallationRound(tempRound);
                tech.setIsUsed();
                installationDay = this.addDay(installationDay);
                installationDay.addInstallationRound(tempRound);
                installationDay.addTechnician();

                technicianDistance += tempRound.getCoveredDistance();
                totalCost += instance.getTechnicianCost();
                totalCost += tempRound.getTotalCost();

                nbTechniciansDays++;
                nbTechniciansUsed++;

                return;
            }
        }
    }

    /*
     * Add a Day to the days list of the current Solution (means that the day has at
     * least one valid round) !! Bien récupérer la valeur lors de l'appel : newDay =
     * addDay(newDay);
     */
    private Day addDay(Day d) {
        Day oldDay = days.put(d.getDate(), d);

        if (oldDay != null) {
            days.put(d.getDate(), oldDay);
            d = oldDay;
        }
        return d;
    }

    /**
     * Calculate the penalties by days and add them to the total cost
     */
    public void calculPenalties() {
        int totalPenalties = 0;

        for (Day day : days.values())
            if (!day.getInstallationRounds().isEmpty())
                for (InstallationRound ir : day.getInstallationRounds())
                    for (Request r : ir.getRequests()) {

                        int penalty = r.getMachine().getPenaltyByDay();
                        int deliveryDate = r.getDeliveryDate();
                        int installationDate = r.getInstallationDate();
                        int diff = installationDate - deliveryDate;

                        if (diff >= 2)
                            totalPenalties += (diff - 1) * penalty;
                    }

        this.idleMachineCosts = totalPenalties;
        totalCost += totalPenalties;
    }

    /**
     * Retourne le nobmre maximal de camions utilisés en une journée
     * 
     * @return
     */
    public void getMaxTruckDays() {
        int maxNbTruck = 0;

        for (Day d : days.values()) {
            int trucksPerDay = d.getNbTruck();

            if (trucksPerDay > maxNbTruck)
                maxNbTruck = trucksPerDay;
        }

        this.nbTrucksUsed = maxNbTruck;
        this.totalCost += maxNbTruck * this.instance.getTruckCost();
    }

    /**
     * Retourne le nobmre maximal de techniciens utilisés en une journée
     * 
     * @return
     */
    public void getMaxTechnicianDays() {
        int maxNbTechnician = 0;

        for (Day d : days.values()) {
            int techniciansPerDay = d.getNbTechnician();

            if (techniciansPerDay > maxNbTechnician)
                maxNbTechnician = techniciansPerDay;
        }

        this.nbTechniciansUsed = maxNbTechnician;
    }

    @Override
    public String toString() {
        return "Solution [days=" + days + ", idleMachineCosts=" + idleMachineCosts + ", instance=" + instance
                + ", nbTechniciansDays=" + nbTechniciansDays + ", nbTechniciansUsed=" + nbTechniciansUsed
                + ", nbTruckDays=" + nbTruckDays + ", nbTrucksUsed=" + nbTrucksUsed + ", technicianDistance="
                + technicianDistance + ", totalCost=" + totalCost + ", truckDistance=" + truckDistance + "]";
    }

    public static void main(String[] args) {
        /*
         * Instance i1 = new Instance(); try { InstanceReader reader = new
         * InstanceReader(); i1 = reader.readInstance();
         * System.out.println("Instance lue avec success !"); } catch (ReaderException
         * ex) { System.out.println(ex.getMessage()); } Solution s1 = new Solution(i1);
         * for (Request r : i1.getRequests().values()) { if
         * (s1.addRequestExistingDeliveryRound(r)) {
         * s1.addRequestExistingInstallationRound(r); } else {
         * s1.addRequestNewDeliveryRound(r); s1.addRequestNewInstallationRound(r); } }
         * System.out.println(s1);
         */
    }

}
