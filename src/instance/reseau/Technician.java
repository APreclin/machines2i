package instance.reseau;

import java.util.LinkedHashMap;
import java.util.Map;

import solution.tournee.InstallationRound;

public class Technician {

    private int id;
    private Location home;
    private int maxDistance;
    private int maxRequests;
    private LinkedHashMap<Integer, Boolean> abilities;
    private InstallationRound lastInstallationRound;
    private int nbConsecutiveInstallationRounds;

    public Technician() {
        this.id = 0;
        this.home = new Location();
        this.maxDistance = 0;
        this.maxRequests = 0;
        this.abilities = new LinkedHashMap<Integer, Boolean>();
        this.lastInstallationRound = new InstallationRound();
        this.nbConsecutiveInstallationRounds = 0;
    }

    public Technician(int id, Location location, int maxDistance, int maxRequests,
            LinkedHashMap<Integer, Boolean> abilities) {
        this();
        this.id = id;
        this.home = location;
        this.maxDistance = maxDistance;
        this.maxRequests = maxRequests;
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
        

    public boolean addInstallationRound(InstallationRound ir) {
        if (!ir.follows(lastInstallationRound)) {
            this.nbConsecutiveInstallationRounds = 0;
            this.lastInstallationRound = ir;
            return true;
        } else if (nbConsecutiveInstallationRounds < 5) {
            this.nbConsecutiveInstallationRounds += 1;
            this.lastInstallationRound = ir;
            return true;
        }
        else
            return false;
    }

    public boolean checkMachineAbility(int machineId) {
        // Vérifie que le technicien est capable d'installer la machine indiquée par son ID
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
        str += "Maximum requests : " + maxRequests + "\n\n";
        str += "Installation possible : \n\n";
        for (Map.Entry<Integer, Boolean> set : abilities.entrySet())
            str += "\t Machine n°" + set.getKey() + " - " + set.getValue() + "\n";
        str += "\n-------------------------\n";
        return str;
    }

}
