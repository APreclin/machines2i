package instance.reseau;

public class Machine {

    private int id;
    private int size;
    private int penalityByDay;
    private int deliveryDate;
    private int installationDate;

    public Machine() {
        this.id = 0;
        this.size = 0;
        this.penalityByDay = 0;
        this.deliveryDate = 0;
        this.installationDate = 0;
    }

    @Override
    public String toString() {
        return "Machine [id=" + id + "deliveryDate=" + deliveryDate + ", installationDate=" + installationDate
                + ", penalityByDay=" + penalityByDay + ", size=" + size + "]";
    }
}
