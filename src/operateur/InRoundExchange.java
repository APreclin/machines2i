package operateur;

import solution.tournee.DeliveryRound;
import solution.tournee.InstallationRound;

public class InRoundExchange extends InRoundOperator{

    public InRoundExchange () {
        super();
    }

    public InRoundExchange (InstallationRound round, int positionI, int positionJ) {
        super(round, positionI, positionJ);
        this.installationRound = round;
        if (!round.isPositionValid(positionI) || !round.isInsertionPositionValid(positionJ)) {
            deltaCost = Integer.MAX_VALUE;
            return;
        }
        deltaCost = evalDeltaCost();
    }

    public InRoundExchange (DeliveryRound round, int positionI, int positionJ) {
        super(round, positionI, positionJ);
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
