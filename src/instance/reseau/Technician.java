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
    private int cost;
    private LinkedHashMap<Integer, Boolean> abilities;
    private LinkedList<InstallationRound> installationRounds; // ensemble des tournées réalisées ordonnées dans le sens descendant des dates de tournée
    private int nbConsecutiveInstallationRounds;

    public Technician() {
        this.id = 0;
        this.home = new Location();
        this.maxDistance = 0;
        this.maxRequests = 0;
        this.distanceCost = 0;
        this.dayCost = 0;
        this.cost = 0;
        this.abilities = new LinkedHashMap<Integer, Boolean>();
        this.installationRounds = new LinkedList<>();
        this.nbConsecutiveInstallationRounds = 0;
    }

    public Technician(int id, Location location, int maxDistance, int maxRequests, int distanceCost, int dayCost,
            int cost, LinkedHashMap<Integer, Boolean> abilities) {
        this();
        this.id = id;
        this.home = location;
        this.maxDistance = maxDistance;
        this.maxRequests = maxRequests;
        this.distanceCost = distanceCost;
        this.dayCost = dayCost;
        this.cost = cost;
        this.abilities = abilities;
        this.installationRounds = new LinkedList<>();
        this.nbConsecutiveInstallationRounds = 0;
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
        return installationRounds;
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

    public boolean addInstallationRound(InstallationRound installationRoundGiven) {
        // Vérifie qu'il n'y ait pas plus de 5 tournées consécutives
        InstallationRound lastRound = installationRounds.getLast();

        if (installationRoundGiven.getDateDiff(lastRound) <= 0)
            // Si la tournée a lieu le même jour que la tournée actuelle ou un jour passé
            return false;

        if (this.nbConsecutiveInstallationRounds >= 5) {
            // Le nombre max de jours de travail d'affilé est atteint
            if (installationRoundGiven.getDateDiff(lastRound) >= 2) {
                // 2 jours de repos => on peut ajouter la tournée
                this.installationRounds.push(installationRoundGiven);
                this.nbConsecutiveInstallationRounds = 0;
                return true;
            }
            else
                // il n'y a pas 2 jours de repos, il ne peut donc pas enchainer
                return false;
        }
        else {
            // nombre maximum de jours de travail consécutifs pas atteint
            if (installationRoundGiven.follows(lastRound))
                // Si la nouvelle tournée suit la précédente, il faut incrémenter le compteur
                this.nbConsecutiveInstallationRounds++;
            else
                // Si la nouvelle tournée ne suit pas, on remet le compteur à zéro
                this.nbConsecutiveInstallationRounds = 0;
            this.installationRounds.push(installationRoundGiven);
            return true;
        }
    }

    /**
     * Check if this Technician is able to install the machine passed in parameter
     * 
     * @param machineId the id of the machine we want to install
     * @return wheter this Technician can install or not the machine passed in
     *         parameter
     */
    public boolean checkMachineAbility(int machineId) {
        // Vérifie que le technicien est capable d'installer la machine indiquée par son
        // ID
        if (this.abilities.containsKey(machineId))
            return this.abilities.get(machineId);
        else
            return false;
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Technician n°" + id + "-----\n\n";
        str += "ID : " + id + "\n";
        str += "Location n°" + id + "\n";
        str += "Maximum distance : " + maxDistance + "\n";
        str += "Maximum requests : " + maxRequests + "\n";
        str += "Distance cost : " + distanceCost + "\n";
        str += "Day cost : " + dayCost + "\n";
        str += "Cost : " + cost + "\n\n";
        str += "Installation possible : \n\n";
        for (Map.Entry<Integer, Boolean> set : abilities.entrySet())
            str += "\t Machine n°" + set.getKey() + " - " + set.getValue() + "\n";
        str += "\n-------------------------\n";
        return str;
    }

    public static void main(String[] args) {
        Location maison1 = new Location(0,10);
        Technician techos = new Technician(1, );
    }

}
