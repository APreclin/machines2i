package operateur;

import solution.tournee.DeliveryRound;
import solution.tournee.InstallationRound;

public class InRoundMove extends InRoundOperator {

    public InRoundMove () {
        super();
    }

    public InRoundMove (InstallationRound round, int positionI, int positionJ) {
        this.installationRound = round;
        if (!round.isPositionValid(positionI) || !round.isInsertionPositionValid(positionJ)) {
            deltaCost = Integer.MAX_VALUE;
            return;
        }
        deltaCost = evalDeltaCost();
    }

    public InRoundMove (DeliveryRound round, int positionI, int positionJ) {
        this.deliveryRound = round;
        if (!round.isPositionValid(positionI) || !round.isInsertionPositionValid(positionJ)) {
            deltaCost = Integer.MAX_VALUE;
            return;
        }
        deltaCost = evalDeltaCost();
    }

    @Override
    protected int evalDeltaCost() {
        if (installationRound == null && deliveryRound == null)
            return Integer.MAX_VALUE;
        else if (this.installationRound != null)
            return this.installationRound.deltaCostExchange(this.positionI, this.positionJ);
        else if (this.deliveryRound != null)
            return this.deliveryRound.deltaCostExchange(this.positionI, this.positionJ);
        // cas impossible
        return Integer.MAX_VALUE;
    }

    @Override
    protected boolean doMouvement() {
        // TODO Auto-generated method stub
        return false;
    }
}
