package instance.reseau;

public class Machine {

    private int id;
    private int size;
    private int penaltyByDay;
    private int deliveryDate;
    private int installationDate;

    public Machine() {
        this.id = 0;
        this.size = 0;
        this.penaltyByDay = 0;
        this.deliveryDate = 0;
        this.installationDate = 0;
    }

    public Machine(int id, int size, int penaltyByDay) {
        this();
        this.id = id;
        this.size = size;
        this.penaltyByDay = penaltyByDay;
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
        Machine other = (Machine) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Machine -----\n\n";
        str += "ID : " + id + "\n";
        str += "Size : " + size + "\n";
        str += "Penalty by day : " + penaltyByDay + "\n";
        str += "Delivery date : " + deliveryDate + "\n";
        str += "Installation date : " + installationDate + "\n";
        str += "-------------------\n\n";
        return str;
    }
}
