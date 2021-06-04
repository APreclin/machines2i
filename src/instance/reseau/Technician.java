package instance.reseau;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import solution.Day;
import solution.tournee.InstallationRound;

public class Technician {

    private int id;
    private Location home;
    private int maxDistance;
    private int maxRequests;
    private int distanceCost;
    private int dayCost;
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

    public boolean addInstallationRound(InstallationRound installationRoundGiven) {
        // Vérifie qu'il n'y ait pas plus de 5 tournées consécutives
        if (checkAddInstallationRound(installationRoundGiven)) {
            this.installationRounds.push(installationRoundGiven);
            this.installationRounds.sort(InstallationRound.InstallationRoundDateComparator);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkAddInstallationRound(InstallationRound installationRoundGiven) {
        // TODO : ajouter la possibilité de faire plusieurs tournées le meme jour tant qu'on reste dans les capacités du technicien
        for (InstallationRound ir : installationRounds) {
            if (installationRoundGiven.getInstallationDay().getDateDiff(ir.getInstallationDay()) == 0)
                return false;
        }
        
        // Vérifie qu'il n'y ait pas plus de 5 tournées consécutives
        if (installationRounds.size() < 5) {
            return true;
        }

        boolean flagGivenRoundIsolated = false;
        InstallationRound round = installationRoundGiven;
        this.installationRounds.add(round);
        this.installationRounds.sort(InstallationRound.InstallationRoundDateComparator);
        int IrIndex = installationRounds.indexOf(round); 
        int nbConsecutiveInstallationRounds = 1;    // Toujours +1 car on compte les ponts entre jours qui sont toujours = nbJours-1

        try {
            InstallationRound beforeRound = installationRounds.get(--IrIndex);

            if (!round.getInstallationDay().follows(beforeRound.getInstallationDay())) {
                 // Si la tournée précédente celle ajoutée ne suit pas, on prend celle d'encore avant pour vérifier les 2 jours de pause à 5 jour de travail
                flagGivenRoundIsolated = true;
                round = installationRounds.get(IrIndex);
                beforeRound = installationRounds.get(--IrIndex);
            }

            while(round.getInstallationDay().follows(beforeRound.getInstallationDay())) {
                nbConsecutiveInstallationRounds++;
                // On regarde combien de tournées consécutives sont présentes avant la tournée à ajouter
                round = beforeRound;
                beforeRound = installationRounds.get(--IrIndex);  // prend la tournée placée avant
            }
        } catch(IndexOutOfBoundsException e) {
            System.out.println(e);
        }

        round = installationRoundGiven;
        IrIndex = installationRounds.indexOf(round);
        InstallationRound afterRound = null;
        try {
            afterRound = installationRounds.get(++IrIndex);
            while(afterRound.getInstallationDay().follows(round.getInstallationDay())) {
                // On regarde combien de tournées consécutives sont présentes après la tournée à ajouter
                round = afterRound;
                afterRound = installationRounds.get(++IrIndex);  // prend la tournée placée avant
                nbConsecutiveInstallationRounds++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e);
        }

        if (nbConsecutiveInstallationRounds > 5) {
            // on ne peut pas demander plus de 5 jours d'affilés
            removeInstallationRound(round);
            return false;
        }

        if (nbConsecutiveInstallationRounds == 5) {
            // vérification qu'à la fin de 5 jours consécutifs le technicien prendra bien 2 jours de congé               
            if (flagGivenRoundIsolated) {
                IrIndex = installationRounds.indexOf(installationRoundGiven);
                InstallationRound lastRound = installationRounds.get(--IrIndex);
                if (installationRoundGiven.getInstallationDay().getDateDiff(lastRound.getInstallationDay()) <= 2) {
                    // La tournée après la dernière tournée consécutive est à moins de 2 jours et il y en a 5 d'affilé, on bloque
                    removeInstallationRound(round);
                    return false;
                }
            }
            else {
                InstallationRound lastConsecutiveRound = round; // Si la tournée donnée etait la dernière consécutive, elle est prise, sinon la dernière consécutive est récupérée dans la boucle vers l'avant
                IrIndex = installationRounds.indexOf(round);
                try {
                    InstallationRound afterLastConsecutiveRound = this.installationRounds.get(++IrIndex);
                    if (afterLastConsecutiveRound.getInstallationDay().getDateDiff(lastConsecutiveRound.getInstallationDay()) <= 2) {
                        // La tournée après la dernière tournée consécutive est à moins de 2 jours et il y en a 5 d'affilé, on bloque
                        removeInstallationRound(round);
                        return false;
                    }
                } catch (Exception e) {
                    // Il n'y a pas de tournée après la dernière, donc on vérifiera au prochaina ajout (5 consécutives avec pause infinie pour l'instant)
                    return true;
                }
            }
        }
        return true;
    }

    public boolean removeInstallationRound(InstallationRound installationRoundGiven) {
        if (installationRoundGiven == null)
            return false;

        if (!this.installationRounds.contains(installationRoundGiven))
            return false; // la tournée n'etait pas contenue

        this.installationRounds.remove(installationRoundGiven);
        return true;
    }

    public int calcNbConsecutiveRounds() {
        // Vérifie qu'il n'y ait pas plus de 5 tournées consécutives
        if (installationRounds.size() == 0)
            return 0;

        InstallationRound beforeRound = installationRounds.getLast();
        int lastIndex = installationRounds.indexOf(beforeRound);
        int nbConsRounds = 0;

        for (int i = 1; i < 6; i++) {
            InstallationRound previousRound = installationRounds.get(lastIndex - i);
            if (beforeRound.getInstallationDay().follows(previousRound.getInstallationDay()))
                nbConsRounds += 1;
            else
                nbConsRounds = 0;
        }

        return nbConsRounds;
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

    /**
     * Check if the technician is already working on a certain day
     * 
     * @param day the day concerned
     * @return whether working or not
     *         
     */
    public boolean isWorkingOnDay(Day day) {
        for (InstallationRound ir : installationRounds) {
            if (ir.getInstallationDay().getDate() == day.getDate())
                return true;
        }
        return false;
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
        Location maison1 = new Location(1, 0, 0);
        Location client1 = new Location(2, 0, 50);
        Location client2 = new Location(2, 50, 50);
        Machine m1 = new Machine(1, 5, 2);
        Machine m2 = new Machine(2, 5, 2);
        Request r1 = new Request(1, client1, 2, 7, m1, 1);
        Request r2 = new Request(2, client2, 5, 10, m2, 1);
        Request r3 = new Request(2, client2, 6, 10, m1, 1);
        Day d = new Day(5);

        LinkedHashMap<Integer, Boolean> abilities = new LinkedHashMap<>();
        abilities.put(1, true);
        abilities.put(2, false);

        Technician tech1 = new Technician(1, maison1, 90, 2, 2, 50, abilities);

        InstallationRound i1 = new InstallationRound(tech1, d);
        tech1.addInstallationRound(i1);
        System.out.println("tournée sans requêtes : " + i1);
        tech1.removeInstallationRound(i1);

        InstallationRound i2 = new InstallationRound(tech1, d);
        tech1.addInstallationRound(i2);
        i2.addRequest(r1);
        System.out.println("tournée avec requête faisable : " + i2);
        tech1.removeInstallationRound(i2);

        InstallationRound i3 = new InstallationRound(tech1, d);
        tech1.addInstallationRound(i3);
        i3.addRequest(r2);
        System.out.println("tournée avec requête infaisable par le technicien : " + i3);
        tech1.removeInstallationRound(i3);

        InstallationRound i4 = new InstallationRound(tech1, d);
        tech1.addInstallationRound(i4);
        i3.addRequest(r3);
        System.out.println("tournée avec requête sur un mauvais jour : " + i4);
        tech1.removeInstallationRound(i4);

    }

}
