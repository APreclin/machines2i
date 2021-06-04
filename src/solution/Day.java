package solution;

import java.util.HashSet;

import solution.tournee.DeliveryRound;
import solution.tournee.InstallationRound;

public class Day {
    private int date;

    private HashSet<InstallationRound> installationRounds;
    private HashSet<DeliveryRound> deliveryRounds;
    private int nbTruck;
    private int nbTechnician;

    public Day() {
        this.date = -1;
        deliveryRounds = new HashSet<>();
        installationRounds = new HashSet<>();
        this.nbTechnician = 0;
        this.nbTruck = 0;
    }

    public Day(int date) {
        this();
        this.date = date;
        if (date == 8)
            System.out.println("OUI");
    }

    public int getDate() {
        return date;
    }

    public int getNbTruck() {
        return nbTruck;
    }

    public int getNbTechnician() {
        return nbTechnician;
    }

    public HashSet<InstallationRound> getInstallationRounds() {
        return this.installationRounds;
    }

    public HashSet<DeliveryRound> getDeliveryRounds() {
        return this.deliveryRounds;
    }

    public boolean addInstallationRound(InstallationRound ir) {
        return this.installationRounds.add(ir);
    }

    public boolean addDeliveryRound(DeliveryRound dr) {
        return this.deliveryRounds.add(dr);
    }

    /**
     * Calculates and return the difference between otherRound's date and this
     * round's date
     * 
     * @param otherRound
     * @return The difference between otherRound's date and this round's date
     */
    public int getDateDiff(Day otherDay) {
        return this.getDate() - otherDay.getDate();
    }

    /**
     * Check if the date of this round follows the date of otherRound
     * 
     * @param otherRound
     * @return whether the date of this round follows the date of otherRound or not
     */
    public boolean follows(Day otherDay) {
        return getDateDiff(otherDay) == 1;
    }

    public void addTruck() {
        nbTruck++;
    }

    public void addTechnician() {
        this.nbTechnician++;
    }
}
