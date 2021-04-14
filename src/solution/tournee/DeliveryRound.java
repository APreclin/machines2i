package solution.tournee;

public class DeliveryRound extends Round {

    private int charge;

    public DeliveryRound() {

        super();
        this.charge = 0;
    }

    @Override
    public String toString() {
        return "DeliveryRound [" + super.toString() + "charge=" + charge + "]";
    }

}
