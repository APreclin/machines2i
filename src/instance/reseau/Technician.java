package instance.reseau;

import java.util.HashMap;

public class Technician {

    private int id;
    private Location home;
    private int maxDistance;
    private int maxRequests;
    private HashMap<Integer, Boolean> installation;

    public Technician() {
        this.id = 0;
        this.home = new Location();
        this.maxDistance = 0;
        this.maxRequests = 0;
        this.installation = new HashMap<Integer, Boolean>();
    }
}
