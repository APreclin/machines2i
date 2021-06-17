package operateur;

import instance.reseau.Request;
import solution.tournee.DeliveryRound;
import solution.tournee.InstallationRound;
import solution.tournee.Round;

public abstract class InRoundOperator {

    protected int deltaDistance = Integer.MAX_VALUE;
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
        this.deltaDistance = evalDeltaDistance();
    }

    public InRoundOperator(InstallationRound round, int positionI, int positionJ) {
        this.installationRound = round;
        requestI = this.installationRound.getRequestByPosition(positionI);
        requestJ = this.installationRound.getRequestByPosition(positionJ);
        this.requestI= round.getRequestByPosition(positionI);
        this.requestJ = round.getRequestByPosition(positionJ);
        this.deltaDistance = evalDeltaDistance();
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

    public int getDeltaDistance () {
        return deltaDistance;
    }

    public Round getDeliveryRound() {
        return deliveryRound;
    }
    
    public Round getInstallationRound() {
        return installationRound;
    }

    public Request getRequestI() {
        return requestI;
    }

    public Request getRequestJ() {
        return requestJ;
    }

    public int getPositionI() {
        return positionI;
    }

    public int getPositionJ() {
        return positionJ;
    }

    public int getDistanceCost() {
        if (installationRound != null)
            return installationRound.getTechnician().getDistanceCost();
        else if (deliveryRound != null)
            return deliveryRound.getTruck().getDistanceCost();
        return Integer.MAX_VALUE;
    }

    // ************************* FONCTIONS UTILITAIRES

    protected abstract int evalDeltaDistance();

    public boolean doMovementIfPossible() {
        if (isMovementPossible()) {
 
            if (doMouvement())
                return true;
        }
        return false;
    }

    protected abstract boolean doMouvement();

    protected abstract boolean checkDeliveryRoundCapacities();

    public boolean isMovementPossible() {
        if (deliveryRound != null) {
            return checkDeliveryRoundCapacities();
        }
        else if (this.deltaDistance < Integer.MAX_VALUE)
            return true;
        else
            return false;
    }

    public boolean isMovementBetter() {
        if (this.deltaDistance < 0)
            return true;
        else
            return false;
    }

    public boolean isBetter(InRoundOperator op) {
        if (this.deltaDistance < op.getDeltaDistance())
            return true;
        else
            return false;
    }
    
    /*
    @Override
    public int evalDeltaDistance() {
        if (this.deliveryRound != null) {
            int coutInsertion = this.deliveryRound.deltaDistanceInsertion(positionJ, requestI, false);
            int coutSuppression = this.deliveryRound.deltaDistanceSuppression(positionI);
            return coutInsertion + coutSuppression;
        }
        else {
            int coutInsertion = this.installationRound.deltaDistanceInsertion(positionJ, requestI, false);
            int coutSuppression = this.installationRound.deltaDistanceSuppression(positionI);
            return coutInsertion + coutSuppression;
        }
    }
    */
    
}
