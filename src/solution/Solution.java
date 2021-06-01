package solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import instance.Instance;
import instance.reseau.Machine;
import instance.reseau.Request;
import instance.reseau.Technician;
import instance.reseau.Truck;
import io.InstanceReader;
import io.exception.ReaderException;
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
        Day newDay = new Day(date);
        Truck truck = new Truck(++idTrucks, instance.getTruckModel());
        instance.addTruck(truck);

        Day oldDay = days.put(date, newDay);

        if (oldDay != null) {
            days.put(date, oldDay);
            newDay = oldDay;
        }

        DeliveryRound tempRound = new DeliveryRound(truck, instance, newDay);
        tempRound.addRequest(requestToAdd);
        days.get(date).addDeliveryRound(tempRound);
        newDay.addTruck();

        totalCost += tempRound.getTotalCost();
        truckDistance += tempRound.getCurrentDistance();

        // totalCost += instance.getTruckCost();
        totalCost += truck.getDayCost();
        nbTruckDays++;
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

        for (int dateInc = requestToAdd.getDeliveryDate() + 1; dateInc < requestToAdd.getLastDay(); dateInc++) {
            // Test sur tous les jours de l'horizon tant qu'on ne trouve pas
            for (Technician tech : technicians.values()) {
                // Test d'une nouvelle tournée avec tous les techniciens possibles tant qu'on ne
                // trouve pas

                // l'objet jour ne sera ajouté que si il contient une tournée valide
                Day newDay = new Day(dateInc);

                // Création d'une tournée temporaire de test
                InstallationRound tempRound = new InstallationRound(tech, newDay);
                if (tempRound.addRequest(requestToAdd)) {
                    // ajout du jour à la liste dans la solution (récup de l'existant si jamais)
                    newDay = this.addDay(newDay);
                    newDay.addInstallationRound(tempRound);
                    newDay.addTechnician();

                    // update du cout
                    totalCost += tempRound.getTotalCost();
                    technicianDistance += tempRound.getCoveredDistance();

                    nbTechniciansDays++;
                    nbTechniciansUsed++;
                    totalCost += instance.getTechnicianCost();
                    totalCost += tech.getDayCost();

                    return;
                }
            }
        }

    }

    /**
     * Adds requestToAdd to an existing DeliveryRound if deliveryRounds is not null
     * 
     * @param requestToAdd
     * @return whether the requestToAdd was added or not
     */
    public boolean addRequestExistingDeliveryRound(Request requestToAdd) {
        if (days.isEmpty())
            return false;

        for (Day d : days.values()) {
            if (d.getDeliveryRounds().isEmpty())
                return false;
            for (DeliveryRound t : d.getDeliveryRounds()) {
                int oldCost = t.getTotalCost();
                int oldDistance = t.getCurrentDistance();
                if (t.addRequest(requestToAdd)) {
                    totalCost += t.getTotalCost() - oldCost; // maj du cout (rempalcement de l'ancien)
                    truckDistance += t.getCurrentDistance() - oldDistance;
                    return true;
                }
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
        if (days.isEmpty())
            return false;

        for (Day d : days.values()) {
            if (d.getDeliveryRounds().isEmpty())
                return false;
            for (InstallationRound t : d.getInstallationRounds()) {
                int oldCost = t.getTotalCost();
                int oldDistance = t.getCoveredDistance();
                if (t.addRequest(requestToAdd)) {
                    totalCost += t.getTotalCost() - oldCost;
                    technicianDistance += t.getCoveredDistance() - oldDistance;

                    return true;
                }
            }
        }

        return false;
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

    /*
     * Vérifie si un camion est déjà utilisé
     */
    public boolean isTruckUsed(Truck truck) {
        HashSet<DeliveryRound> deliveryRounds = this.getDeliveryRounds();
        for (DeliveryRound dr : deliveryRounds) {
            if (dr.getTruck().equals(truck))
                return true;
        }
        return false;
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
