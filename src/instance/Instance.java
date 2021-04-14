package instance;

public class Instance {
    // Attributs
    private String dataset;
    private String name;
    private int days;
    private int technicianDistanceCost;
    private int technicianDayCost;
    private int technicianCost;

    public Instance() {
        this.dataset = "";
        this.name = "";
        this.days = 0;
        this.technicianDistanceCost = 0;
        this.technicianDayCost = 0;
        this.technicianCost = 0;
    }

    public String getDataset() {
        return dataset;
    }

    public String getName() {
        return name;
    }

    public int getDays() {
        return days;
    }

    public int getTechnicianDistanceCost() {
        return technicianDistanceCost;
    }

    public int getTechnicianDayCost() {
        return technicianDayCost;
    }

    public int getTechnicianCost() {
        return technicianCost;
    }
}
