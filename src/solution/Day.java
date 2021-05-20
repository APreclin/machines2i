package solution;

import java.util.HashSet;

import solution.tournee.DeliveryRound;
import solution.tournee.InstallationRound;

public class Day {
    private int date;

    private HashSet<InstallationRound> installationRounds;
    private HashSet<DeliveryRound> deliveryRounds;

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
}
