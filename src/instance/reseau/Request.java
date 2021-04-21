package instance.reseau;

public class Request {

    private int id;
    private Location location;
    private Machine machine;
    private int firstDay;
    private int lastDay;
    private int nbMachines;

    public Request() {

        this.id = 0;
        this.location = new Location();
        this.machine = new Machine();
        this.firstDay = -1;
        this.lastDay = -1;
        this.nbMachines = 0;
    }

    public Request(int id, Location location, int firstDay, int lastDay, Machine machine, int nbMachines) {
        this();
        this.id = id;
        this.location = location;
        this.machine = machine;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.nbMachines = nbMachines;
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
        str += "Nb machines :" + nbMachines + "\n";
        str += "First Day : " + firstDay + "\n";
        str += "Last Day : " + lastDay + "\n\n";
        str += "-----------------------\n";
        return str;
    }

}
