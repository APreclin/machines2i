package solution.tournee;

import java.util.LinkedHashMap;
import instance.reseau.Location;
import instance.reseau.Machine;
import instance.reseau.Request;
import instance.reseau.Technician;
import solution.Day;

public class InstallationRound extends Round {
    private Technician technician;
    private Integer coveredDistance;
    private Day installationDay;

    public InstallationRound(Technician technician, Day day) {
        super();
        this.setTechnician(technician);
        this.installationDay = day;
        this.coveredDistance = 0;
    }

    private Request getLastRequest() {
        if (requests == null)
            return null;

        return requests.getLast();
    }

    public Integer getCoveredDistance() {
        return coveredDistance;
    }

    public void setCoveredDistance(Integer coveredDistance) {
        this.coveredDistance = coveredDistance;
    }

    public boolean setTechnician(Technician technician) {
        if (this.technician != null)
            return false;

        this.technician = technician;
        this.totalCost = technician.getDayCost();
        return true;
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
        if (request == null || technician == null)
            return false;

        boolean checkMachineComp = this.checkMachineComp(request);

        if (requests.isEmpty()) {
            if (!checkMachineComp)
                return false;

            Location techHome = this.technician.getHome();
            Location requestLocation = request.getLocation();
            int distToTechHome = techHome.getDistanceTo(requestLocation);

            doAddRequest(request, distToTechHome);

            return true;
        }

        int techMaxDistance = this.technician.getMaxDistance();
        int techMaxRequests = this.technician.getMaxRequests();
        int distToLastRequest = this.getLastRequest().getDistanceTo(request);

        boolean isDistanceRespected = this.coveredDistance + distToLastRequest <= techMaxDistance;
        boolean isNbRequestsRespected = this.requests.size() < techMaxRequests;

        if (!isNbRequestsRespected || !isDistanceRespected || !checkMachineComp)
            return false;

        doAddRequest(request, distToLastRequest);

        return true;
    }

    private void doAddRequest(Request request, int distance) {
        request.setInstallationDate(this.installationDay.getDate());
        this.requests.push(request);
        this.coveredDistance += distance;
        this.totalCost += distance * this.technician.getDistanceCost();
    }

    /*
     * private void doAddRequest(Request request) { int distanceCost =
     * this.requests.getLast().getDistanceTo(request); this.requests.push(request);
     * request.setInstallationDate(this.date); // maj de la date au niveau de la
     * requete this.totalCost += distanceCost; //ajout du cout en fonction de la
     * distance }
     */

    /**
     * Check if the technician is able to install the machine specified in the
     * request
     * 
     * @param request
     * @return whether the technician is able to install the machine specified in
     *         the request
     */
    private boolean checkMachineComp(Request request) {
        if (request == null || request.getMachine() == null)
            return false;

        int machineId = request.getMachine().getId();

        if (!this.technician.checkMachineAbility(machineId))
            return false;

        return true;

    }

    @Override
    public String toString() {
        String str = "";
        str += "\n----- Installation Round -----\n";
        str += technician + "\n";
        str += "Covered distance : " + coveredDistance + "\n";
        str += super.toString();
        str += "------------------------------\n";
        return str;
    }

    public static void main(String[] args) {
        // Création d'une installation round simple
        Location home = new Location(1, 0, 0);

        LinkedHashMap<Integer, Boolean> abilities = new LinkedHashMap<Integer, Boolean>();
        abilities.put(1, false);
        abilities.put(2, true);

        Technician t = new Technician(1, home, 20, 4, 20, 10, abilities);
        InstallationRound ir = new InstallationRound(t, 5);
        // System.out.println(ir.toString());

        // Le technicien ne sait pas installer cette machine (false)
        Location l1 = new Location(1, 10, 0);
        Machine m1 = new Machine(1, 10, 20); // id, size, penaltyByDay
        Request r1 = new Request(1, l1, 1, 3, m1, 1); // id, location, firstDay, lastDay, machine, nbMachines
        System.out.println(ir.addRequest(r1));

        /*
         * Vérification des couts : distance : 20 => cout = 10+20*10=210
         */
        Machine m2 = new Machine(2, 10, 20); // id, size, penaltyByDay
        Request r2 = new Request(1, l1, 1, 3, m2, 1); // id, location, firstDay, lastDay, machine, nbMachines
        System.out.println("r2 ajoutée (val:true) => " + ir.addRequest(r2));
        System.out.println("Cout de la tournée d'installation (val:210) => " + ir.getTotalCost());
    }

}
