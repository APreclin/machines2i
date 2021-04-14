package instance.reseau;

public class Request {

    private int id;
    private int firstDay;
    private int lastDay;
    private int nbMachines;

    public Request() {

        this.id = 0;
        this.firstDay = -1;
        this.lastDay = -1;
        this.nbMachines = 0;
    }

    @Override
    public String toString() {
        return "Request [id=" + id + ",firstDay=" + firstDay + ",  lastDay=" + lastDay + ", nbMachines=" + nbMachines
                + "]";
    }

}
