package solution;

import java.util.HashSet;

import solution.tournee.DeliveryRound;
import solution.tournee.InstallationRound;

public class Day {
    private int date;

    private HashSet<InstallationRound> installationRounds;
    private HashSet<DeliveryRound> deliveryRounds;

    public Day() {
        this.date = -1;
    }

    public Day(int date) {
        this.date = date;
    }

    public int getDate() {
        return date;
    }

    public HashSet<InstallationRound> getInstallationRounds() {
        return new HashSet<>(this.installationRounds);
    }

    public HashSet<DeliveryRound> getDeliveryRounds() {
        return new HashSet<>(this.deliveryRounds);
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
}
