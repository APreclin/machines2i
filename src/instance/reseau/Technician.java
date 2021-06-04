package instance.reseau;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import solution.tournee.InstallationRound;

public class Technician {

    private int id;
    private Location home;
    private int maxDistance;
    private int maxRequests;
    private int distanceCost;
    private int dayCost;
    private boolean isUsed;
    private LinkedHashMap<Integer, Boolean> abilities;
    private LinkedList<InstallationRound> installationRounds; // ensemble des tournées réalisées ordonnées dans le sens
                                                              // descendant des dates de tournée

    public Technician() {
        this.id = 0;
        this.home = new Location();
        this.maxDistance = 0;
        this.maxRequests = 0;
        this.distanceCost = 0;
        this.dayCost = 0;
        this.isUsed = false;
        this.abilities = new LinkedHashMap<Integer, Boolean>();
        this.installationRounds = new LinkedList<InstallationRound>();
    }

    public Technician(int id, Location location, int maxDistance, int maxRequests, int distanceCost, int dayCost,
            LinkedHashMap<Integer, Boolean> abilities) {
        this();
        this.id = id;
        this.home = location;
        this.maxDistance = maxDistance;
        this.maxRequests = maxRequests;
        this.distanceCost = distanceCost;
        this.dayCost = dayCost;
        this.abilities = abilities;
    }

    public int getId() {
        return id;
    }

    public Location getHome() {
        return home;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public LinkedHashMap<Integer, Boolean> getAbilities() {
        return new LinkedHashMap<Integer, Boolean>(abilities);
    }

    public LinkedList<InstallationRound> getInstallationRounds() {
        return new LinkedList<InstallationRound>(installationRounds);
    }

    public int getDistanceCost() {
        return distanceCost;
    }

    public int getDayCost() {
        return dayCost;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed() {
        isUsed = true;
    }

    public void addInstallationRound(InstallationRound installationRoundGiven) {
        this.installationRounds.push(installationRoundGiven);
        isUsed = true;
    }

    /**
     * Check if this Technician is able to install the machine passed in parameter
     * 
     * @param machineId the id of the machine we want to install
     * @return whether this Technician can install or not the machine passed in
     *         parameter
     */
    public boolean checkMachineAbility(int machineId) {
        // Vérifie que le technicien est capable d'installer la machine indiquée par son
        // ID
        if (!this.abilities.containsKey(machineId))
            return false;

        return this.abilities.get(machineId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Technician other = (Technician) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n----- Technician n°" + id + "-----\n";
        str += "ID : " + id + "\n";
        str += "Location n°" + id + "\n";
        str += "Maximum distance : " + maxDistance + "\n";
        str += "Maximum requests : " + maxRequests + "\n";
        str += "Distance cost : " + distanceCost + "\n";
        str += "Day cost : " + dayCost + "\n\n";
        str += "\nInstallation possible : \n\n";
        for (Map.Entry<Integer, Boolean> set : abilities.entrySet())
            str += "\t Machine n°" + set.getKey() + " - " + set.getValue() + "\n";
        /*
         * if (!installationRounds.isEmpty()) str += "Tournées d'installation : \n"; for
         * (InstallationRound ir : installationRounds) str += ir.toString();
         */
        str += "-------------------------\n";
        return str;
    }

    public static void main(String[] args) {
        /*
         * Location maison1 = new Location(1, 0, 0); Location client1 = new Location(2,
         * 0, 50); Location client2 = new Location(2, 50, 50); Machine m1 = new
         * Machine(1, 5, 2); Machine m2 = new Machine(2, 5, 2); Request r1 = new
         * Request(1, client1, 2, 7, m1, 1); Request r2 = new Request(2, client2, 5, 10,
         * m2, 1); Request r3 = new Request(2, client2, 6, 10, m1, 1); Day d = new
         * Day(5);
         * 
         * LinkedHashMap<Integer, Boolean> abilities = new LinkedHashMap<>();
         * abilities.put(1, true); abilities.put(2, false);
         * 
         * Technician tech1 = new Technician(1, maison1, 90, 2, 2, 50, abilities);
         * 
         * InstallationRound i1 = new InstallationRound(tech1, d);
         * tech1.addInstallationRound(i1); System.out.println("tournée sans requêtes : "
         * + i1); tech1.removeInstallationRound(i1);
         * 
         * InstallationRound i2 = new InstallationRound(tech1, d);
         * tech1.addInstallationRound(i2); i2.addRequest(r1);
         * System.out.println("tournée avec requête faisable : " + i2);
         * tech1.removeInstallationRound(i2);
         * 
         * InstallationRound i3 = new InstallationRound(tech1, d);
         * tech1.addInstallationRound(i3); i3.addRequest(r2);
         * System.out.println("tournée avec requête infaisable par le technicien : " +
         * i3); tech1.removeInstallationRound(i3);
         * 
         * InstallationRound i4 = new InstallationRound(tech1, d);
         * tech1.addInstallationRound(i4); i3.addRequest(r3);
         * System.out.println("tournée avec requête sur un mauvais jour : " + i4);
         * tech1.removeInstallationRound(i4);
         */

    }

}
