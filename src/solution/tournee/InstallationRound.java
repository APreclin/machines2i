package solution.tournee;

import java.util.LinkedList;

import instance.reseau.Location;
import instance.reseau.Request;
import instance.reseau.Technician;

public class InstallationRound extends Round {

    private int nbRequests;
    private Technician technician;
    private Integer coveredDistance;

    public InstallationRound() {
        super();
        this.nbRequests = 0;
        this.coveredDistance = 0;
    }

    public InstallationRound(Technician technician) {
        super();
        this.technician = technician;
        nbRequests = 0;
    }

    public InstallationRound(LinkedList<Request> requests, int date, int nbRequests) {
        super(requests, date);
        this.nbRequests = nbRequests;
    }

    public int getNbRequests() {
        return nbRequests;
    }

    private Request getLastRequest() {
        if (requests != null)
            return requests.getLast();

        return null;
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
        str += "----- Installation Round -----\n\n";
        str += "Nb requests : " + nbRequests + "\n";
        str += super.toString();
        str += "------------------------------\n\n";
        return str;
    }

    public static void main(String[] args) {
        // TODO: test unitaire installationRound
    }

}
