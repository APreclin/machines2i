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

    @Override
    public String toString() {
        return "Location [id=" + id + ", x=" + x + ", y=" + y + "]";
    }
}
