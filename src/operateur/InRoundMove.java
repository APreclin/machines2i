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
            deltaDistance = Integer.MAX_VALUE;
            return;
        }
        deltaDistance = evalDeltaDistance();
    }

    public InRoundMove (DeliveryRound round, int positionI, int positionJ) {
        this.deliveryRound = round;
        if (!round.isPositionValid(positionI) || !round.isInsertionPositionValid(positionJ)) {
            deltaDistance = Integer.MAX_VALUE;
            return;
        }
        deltaDistance = evalDeltaDistance();
    }

    @Override
    protected int evalDeltaDistance() {
        if (installationRound == null && deliveryRound == null)
            return Integer.MAX_VALUE;
        else if (this.installationRound != null)
            return this.installationRound.deltaDistanceExchange(this.positionI, this.positionJ);
        else if (this.deliveryRound != null)
            return this.deliveryRound.deltaDistanceExchange(this.positionI, this.positionJ);
        // cas impossible
        return Integer.MAX_VALUE;
    }

    @Override
    protected boolean doMouvement() {
        if (installationRound != null)
            return installationRound.doMove(this); 
        else if (deliveryRound != null)
            return deliveryRound.doMove(this);
        return false;
    }
}
