package instance.reseau;

public class Location {

    private int id;
    private int x;
    private int y;

    public Location() {
        this.id = 0;
        this.x = 0;
        this.y = 0;
    }

    public Location(int id, int x, int y) {
        this();
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        String str = "";
        str += "----- Location -----\n\n";
        str += "ID = " + id + "\n";
        str += "X = " + x + "\n";
        str += "Y = " + y + "\n";
        str += "--------------------\n\n";
        return str;
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
        Location other = (Location) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
