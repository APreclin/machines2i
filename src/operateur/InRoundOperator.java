package operateur;

import instance.reseau.Request;
import solution.tournee.DeliveryRound;
import solution.tournee.InstallationRound;
import solution.tournee.Round;

public abstract class InRoundOperator {

    protected int deltaCost = Integer.MAX_VALUE;
    protected InstallationRound installationRound;
    protected DeliveryRound deliveryRound;

    protected Request requestI;
    protected Request requestJ;
    protected int positionI;
    protected int positionJ;

    public InRoundOperator() {
        positionJ = 0;
        positionI = 0;
        requestI = new Request();
        requestJ = new Request();
    }

    public InRoundOperator(DeliveryRound round, int positionI, int positionJ) {
        this.deliveryRound = round;
        this.positionI = positionI;
        this.positionJ = positionJ;
        requestI = this.deliveryRound.getRequestByPosition(positionI);
        requestJ = this.deliveryRound.getRequestByPosition(positionJ);
        this.requestI= round.getRequestByPosition(positionI);
        this.requestJ = round.getRequestByPosition(positionJ);
        this.deltaCost = evalDeltaCost();
    }

    public InRoundOperator(InstallationRound round, int positionI, int positionJ) {
        this.installationRound = round;
        requestI = this.installationRound.getRequestByPosition(positionI);
        requestJ = this.installationRound.getRequestByPosition(positionJ);
        this.requestI= round.getRequestByPosition(positionI);
        this.requestJ = round.getRequestByPosition(positionJ);
        this.deltaCost = evalDeltaCost();
    }

    public static InRoundOperator getInRoundOperator(InRoundOperatorType type) {
        switch(type) {
            case INROUND_MOVE:
                return new InRoundMove();
            case INROUND_EXCHANGE:
                return new InRoundExchange();
            default:
            return null;
        }
    }

    public static InRoundOperator getInRoundOperator(InRoundOperatorType type, InstallationRound ir, int positionI, int positionJ) {
        switch(type) {
            case INROUND_MOVE:
                return new InRoundMove(ir, positionI, positionJ);
            case INROUND_EXCHANGE:
                return new InRoundExchange(ir, positionI, positionJ);
            default:
            return null;
        }
    }

    public static InRoundOperator getInRoundOperator(InRoundOperatorType type, DeliveryRound dr, int positionI, int positionJ) {
        switch(type) {
            case INROUND_MOVE:
                return new InRoundMove(dr, positionI, positionJ);
            case INROUND_EXCHANGE:
                return new InRoundExchange(dr, positionI, positionJ);
            default:
            return null;
        }
    }

    public int getDeltaCost () {
        return deltaCost;
    }

    public Round getDeliveryRound() {
        return deliveryRound;
    }
    
    public Round getInstallationRound() {
        return installationRound;
    }

    protected abstract int evalDeltaCost();

    public boolean doMovementIfPossible() {
        if (isMovementPossible()) {
            if (doMouvement())
                return true;
        }
        return false;
    }

    protected abstract boolean doMouvement();

    public boolean isMovementPossible() {
        if (this.deltaCost < Integer.MAX_VALUE)
            return true;
        else
            return false;
    }

    public boolean isMovementBetter() {
        if (this.deltaCost < 0)
            return true;
        else
            return false;
    }

    public boolean isBetter(InRoundOperator op) {
        if (this.deltaCost < op.getDeltaCost())
            return true;
        else
            return false;
    }
    
    /*
    @Override
    public int evalDeltaCost() {
        if (this.deliveryRound != null) {
            int coutInsertion = this.deliveryRound.deltaCostInsertion(positionJ, requestI, false);
            int coutSuppression = this.deliveryRound.deltaCostSuppression(positionI);
            return coutInsertion + coutSuppression;
        }
        else {
            int coutInsertion = this.installationRound.deltaCostInsertion(positionJ, requestI, false);
            int coutSuppression = this.installationRound.deltaCostSuppression(positionI);
            return coutInsertion + coutSuppression;
        }
    }
    */
    
}
