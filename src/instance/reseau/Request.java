package instance.reseau;

public class Request {

    private int id;
    private int firstDay;
    private int lastDay;
    private Machine machine;
    private int nbMachines;
    private Location location;
    private int deliveryDate;
    private int installationDate;

    public Request() {
        this.id = 0;
        this.location = new Location();
        this.machine = new Machine();
        this.firstDay = -1;
        this.lastDay = -1;
        this.deliveryDate = -1;
        this.installationDate = -1;
        this.machine = new Machine();
        this.nbMachines = 0;
        this.location = new Location();
        this.deliveryDate = -1;
        this.installationDate = -1;
    }

    public Integer getDistanceTo(Request request) {
        if (request != null)
            return this.location.getDistanceTo(request.getLocation());
        else
            return Integer.MAX_VALUE;
    }

    public Request(int id, Location location, int firstDay, int lastDay, Machine machine, int nbMachines) {
        this();
        this.id = id;
        this.location = location;
        this.machine = machine;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.nbMachines = nbMachines;
        this.deliveryDate = -1;
        this.installationDate = -1;
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Machine getMachine() {
        return machine;
    }

    public int getFirstDay() {
        return firstDay;
    }

    public int getLastDay() {
        return lastDay;
    }

    public int getNbMachines() {
        return nbMachines;
    }

    public int getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(int deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(int installationDate) {
        this.installationDate = installationDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Request other = (Request) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Request n°" + id + " -----\n\n";
        str += "ID : " + id + "\n";
        str += "Location n°" + this.location.getId() + "\n";
        str += "Machine n°" + this.machine.getId() + "\n";
        str += "Nb machines : " + nbMachines + "\n";
        str += "First Day : " + firstDay + "\n";
        str += "Last Day : " + lastDay + "\n\n";
        str += "-----------------------\n";
        return str;
    }

    // TODO : nullPointerException ici
    public static void main(String[] args) {

        // Création d'une requête simple
        Location l = new Location(1, 2, 3);
        Machine m = new Machine(1, 10, 20);
        Request r = new Request(1, l, 1, 3, m, 2);
        System.out.println(r.toString());

        // Test de la fonction getDistanceTo
        Location l2 = new Location(2, 3, 3);
        Request r2 = new Request(2, l2, 1, 3, m, 4);
        System.out.println(r.getDistanceTo(r2));
    }

}
