package solution.tournee;

import java.util.LinkedList;

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
        this.totalCost = technician.getDayCost();
        nbRequests = 0;
    }

    private Request getLastRequest() {
        if (requests != null)
            return requests.getLast();
        else
            return null;
    }

    @Override
    public boolean addRequest(Request request) {
        //vérifier le type de machine, jours de repos, distance maximale, nombre de demandes
        if (request != null) {
            if (requests.isEmpty()) {
                if (this.checkMachineComp(request)
                && technician.addInstallationRound(this)) {
                    doAddRequest(request);
                    this.coveredDistance += this.technician.getHome().getDistanceTo(request.getLocation());
                    return true;
                }
                else
                    return false;
            }
            else {
                if (this.nbRequests < this.technician.getMaxRequests()
                && (this.coveredDistance + this.getLastRequest().getDistanceTo(request) <= this.technician.getMaxDistance())
                && this.checkMachineComp(request)
                && technician.addInstallationRound(this)) {
                    doAddRequest(request);
                    this.coveredDistance += this.getLastRequest().getDistanceTo(request);
                    return true;
                }
                else
                    return false;
                }
        }
        return false;
    }

    private void doAddRequest(Request request) {
        int distanceCost = this.requests.getLast().getDistanceTo(request);
        this.requests.push(request);
        request.setInstallationDate(this.date);     // maj de la date au niveau de la requete
        this.totalCost += distanceCost;       //ajout du cout en fonction de la distance
    }

    private boolean checkMachineComp(Request request) {
        if (request != null && request.getMachine() != null && (this.technician.getId() != 0)) {
            int machineId = request.getMachine().getId();
            if (this.technician.checkMachineAbility(machineId))
                return true;
            else
                return false;
        }
        return false;
    }

    public InstallationRound(LinkedList<Request> requests, int date, int nbRequests) {
        super(requests, date);
        this.nbRequests = nbRequests;
    }

    public int getNbRequests() {
        return nbRequests;
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
        //TODO: test unitaire installationRound
    }

}
