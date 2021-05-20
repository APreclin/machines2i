package instance.reseau;

public class Machine {

    private int id;
    private int size;
    private int penaltyByDay;

    public Machine() {
        this.id = 0;
        this.size = 0;
        this.penaltyByDay = 0;
    }

    public Machine(int id, int size, int penaltyByDay) {
        this();
        this.id = id;
        this.size = size;
        this.penaltyByDay = penaltyByDay;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public int getPenaltyByDay() {
        return penaltyByDay;
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
        str += "----- Machine n°" + id + " -----\n\n";
        str += "ID : " + id + "\n";
        str += "Size : " + size + "\n";
        str += "Penalty by day : " + penaltyByDay + "\n\n";
        str += "-----------------------\n";
        return str;
    }

    public static void main(String[] args) {

        // Création d'une machine simple
        Machine m = new Machine(1, 12, 30);
        System.out.println(m.toString());
    }
}
