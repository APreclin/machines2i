package solution.tournee;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import instance.reseau.Location;
import instance.reseau.Request;
import instance.reseau.Technician;

public class InstallationRound extends Round {

    private int nbRequests;
    private Technician technician;
    private Integer coveredDistance;

    public InstallationRound(Technician technician, int date) {
        super();
        this.technician = new Technician();
        this.setTechnician(technician);
        this.date = date;
        this.nbRequests = 0;
        this.coveredDistance = 0;
    }

    public int getNbRequests() {
        return nbRequests;
    }

    private Request getLastRequest() {
        if (requests != null)
            return requests.getLast();

        return null;
    }

    public boolean setTechnician(Technician technician) {
        if (this.technician == null) {
            if (technician.addInstallationRound(this)) {
                this.technician = technician;
                this.totalCost = technician.getDayCost();
                return true;
            }
            else {
                return false;
            }
        }
        else 
            return false;      
    }

    /**
     * Check if it is possible to add request to the list of requests. Check if the
     * technician is able to install the machine specified in the request. Check if
     * the different constraints of the technician are respected (rest days,
     * consecutives days, max distance, max requests etc).
     * 
     * @param request
     * @return whether the request was added to the list of requests or not
     */
    @Override
    public boolean addRequest(Request request) {
        if (request == null)
            return false;

        boolean checkMachineComp = this.checkMachineComp(request);
        boolean isRoundAdded = technician.addInstallationRound(this);

        if (requests.isEmpty()) {
            if (!checkMachineComp || !isRoundAdded)
                return false;

            Location techHome = this.technician.getHome();
            Location requestLocation = request.getLocation();
            int distToTechHome = techHome.getDistanceTo(requestLocation);

            request.setInstallationDate(this.date);
            this.requests.push(request);
            this.coveredDistance += distToTechHome;

            return true;
        }

        int techMaxDistance = this.technician.getMaxDistance();
        int techMaxRequests = this.technician.getMaxRequests();
        int distToLastRequest = this.getLastRequest().getDistanceTo(request);
       

        boolean isDistanceRespected = this.coveredDistance + distToLastRequest <= techMaxDistance;
        boolean isNbRequestsRespected = this.nbRequests < techMaxRequests;

        if (!isNbRequestsRespected || !isDistanceRespected || !checkMachineComp || !isRoundAdded)
            return false;

        request.setInstallationDate(this.date);
        this.requests.push(request);
        this.coveredDistance += distToLastRequest;

        return true;
    }

    /*
    private void doAddRequest(Request request) {
        int distanceCost = this.requests.getLast().getDistanceTo(request);
        this.requests.push(request);
        request.setInstallationDate(this.date);     // maj de la date au niveau de la requete
        this.totalCost += distanceCost;       //ajout du cout en fonction de la distance
    }*/

    /**
     * Check if the technician is able to install the machine specified in the
     * request
     * 
     * @param request
     * @return whether the technician is able to install the machine specified in
     *         the request
     */
    private boolean checkMachineComp(Request request) {
        if (request == null || request.getMachine() == null || this.technician.getId() == 0)
            return false;

        int machineId = request.getMachine().getId();

        if (!this.technician.checkMachineAbility(machineId))
            return false;

        return true;

    }

    @Override
    public String toString() {
        String str = "";
        str += "\n----- Installation Round -----\n\n";
        str += technician + "\n";
        str += "Nb requests : " + nbRequests + "\n";
        str += "Covered distance : " + coveredDistance + "\n\n";
        str += super.toString();
        str += "------------------------------\n\n";
        return str;
    }

    public static void main(String[] args) {
        //TODO : finir test unitaire installationRound
        // Création d'une installation round simple
        Location home = new Location(1, 2, 0);
        LinkedHashMap<Integer, Boolean> abilities = new LinkedHashMap<Integer, Boolean>();
        abilities.put(1, true);
        abilities.put(2, false);

        HashMap<String, Integer> couts = new HashMap<>();
        couts.put("distanceCost", 2);
        couts.put("dayCost", 50);
        couts.put("cost", 30);
        Technician t = new Technician(1, home, 20, 4, couts, abilities);
        InstallationRound ir = new InstallationRound(t, 5);
        System.out.println(ir.toString());
    }

}
