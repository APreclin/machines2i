package solution.tournee;

import java.util.Comparator;
import java.util.LinkedHashMap;
import instance.reseau.Location;
import instance.reseau.Machine;
import instance.reseau.Request;
import instance.reseau.Technician;
import operateur.InRoundOperator;
import operateur.InRoundOperatorType;
import solution.Day;

public class InstallationRound extends Round implements Comparable<InstallationRound> {
    private Technician technician;
    private Integer coveredDistance;
    private Day installationDay;

    public InstallationRound(Technician technician, Day day) {
        super();
        this.installationDay = day;
        this.coveredDistance = 0;
        this.technician = technician;
    }

    public Day getInstallationDay() {
        return installationDay;
    }

    /*
     * private Request getLastRequest() { if (requests == null) return null;
     * 
     * return requests.getLast(); }
     */

    /*
     * private Location getLastLocation() { if (requests.isEmpty()) return
     * technician.getHome();
     * 
     * return requests.getLast().getLocation(); }
     */

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
        if (!technician.isWorkingOnDay(this.installationDay))
            // Le cout journalier n'est ajouté que si le technicien ne travaillait pas deja
            // ce jour
            this.totalCost = technician.getDayCost();
        return true;
    }

    public Technician getTechnician() {
        return technician;
    }

    public Location getCurrent(int position) {
        if (position == requests.size()) {
            return this.getTechnician().getHome();
        }
        else if (isInsertionPositionValid(position)) { 
            return requests.get(position).getLocation();
        }
        else
            return null;
    }

    public Location getPrec(int position) {
        if (position == 0) {
            return this.getTechnician().getHome();
        }
        else if (isInsertionPositionValid(position-1)) { 
            return requests.get(position-1).getLocation();
        }
        else
            return null;
    }

    public Location getNext(int position) {
        if (position == requests.size()-1) {
            return this.getTechnician().getHome();
        }
        else if (isPositionValid(position+1)) { 
            return requests.get(position+1).getLocation();
        }
        else
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
        if (request == null || technician == null)
            return false;

        if (installationDay.getDate() < request.getFirstDay() || installationDay.getDate() <= request.getDeliveryDate()
                || installationDay.getDate() > request.getLastDay())
            // l'installation ne peut pas se faire avant ou le même jour que la livraison
            return false;

        boolean checkMachineComp = this.checkMachineComp(request);
        int technicianMaxDistance = this.technician.getMaxDistance();
        Location requestLocation = request.getLocation();
        int requestLocationToHome = returnToHome(requestLocation);

        if (requests.isEmpty()) {
            int locationToHomeTrip = requestLocationToHome * 2;
            if (!checkMachineComp || locationToHomeTrip > technicianMaxDistance)
                return false;

            doAddRequest(request, requestLocationToHome * 2);

            return true;
        }

        int techMaxRequests = this.technician.getMaxRequests();
        boolean isNbRequestsRespected = this.requests.size() < techMaxRequests;
        
        Location lastLocation = this.requests.getLast().getLocation();
        int lastLocationToRequestLocation = lastLocation.getDistanceTo(requestLocation);
        int lastLocationToHome = returnToHome(lastLocation);
        int newDistance = lastLocationToRequestLocation - lastLocationToHome + requestLocationToHome;
        boolean isDistanceRespected = (newDistance + this.coveredDistance <= technicianMaxDistance);

        if (!isNbRequestsRespected || !isDistanceRespected || !checkMachineComp)
            return false;

        doAddRequest(request, newDistance);

        return true;
    }

    @Override
    public boolean checkAddingRequest(Request request) {
        if (request == null || technician == null)
            return false;

        if (installationDay.getDate() < request.getFirstDay() || installationDay.getDate() <= request.getDeliveryDate()
                || installationDay.getDate() > request.getLastDay())
            // l'installation ne peut pas se faire avant ou le même jour que la livraison
            return false;

        boolean checkMachineComp = this.checkMachineComp(request);
        int technicianMaxDistance = this.technician.getMaxDistance();
        Location requestLocation = request.getLocation();
        int requestLocationToHome = returnToHome(requestLocation);

        if (requests.isEmpty()) {
            int locationToHomeTrip = requestLocationToHome * 2;
            if (!checkMachineComp || locationToHomeTrip > technicianMaxDistance)
                return false;

            return true;
        }

        int techMaxRequests = this.technician.getMaxRequests();
        boolean isNbRequestsRespected = this.requests.size() < techMaxRequests;
        
        Location lastLocation = this.requests.getLast().getLocation();
        int lastLocationToRequestLocation = lastLocation.getDistanceTo(requestLocation);
        int lastLocationToHome = returnToHome(lastLocation);
        int newDistance = lastLocationToRequestLocation - lastLocationToHome + requestLocationToHome;
        boolean isDistanceRespected = (newDistance + this.coveredDistance <= technicianMaxDistance);

        if (!isNbRequestsRespected || !isDistanceRespected || !checkMachineComp)
            return false;

        return true;
    }

     /**
     * Réalise les actions nécessaires à l'ajout d'une requête (sans prendre en compte les vérifications qui sont faites par la méthodes appelante)
     * 
     * @param request requête à ajouter
     * @param distance distance engendrée par cet ajout
     * @return void
     */
    private void doAddRequest(Request request, int distance) {
        request.setInstallationDate(this.installationDay.getDate());
        this.requests.addLast(request);
        this.coveredDistance += distance;
        this.totalCost += distance * this.technician.getDistanceCost();
    }

    /**
     * Distance between location and the depot
     * 
     * @param location
     * @return the distance between location and the depot
     */
    public int returnToHome(Location location) {
        return location.getDistanceTo(this.technician.getHome());
    }

    @Override
    public InRoundOperator getBestInRoundOperator(InRoundOperatorType type) {
        if (requests == null)
            return InRoundOperator.getInRoundOperator(type, this, 0, -1);
        InRoundOperator bestOp = InRoundOperator.getInRoundOperator(type, this, 0, 0);   // Operateur impossible pour avoir un cout maximal
        for (int i = 0 ; i < requests.size() ; i++) {
            for (int j = 0 ; j <= requests.size() ; j++) {
                InRoundOperator newOp = InRoundOperator.getInRoundOperator(type, this, i, j);
                if (newOp.getDeltaCost() < bestOp.getDeltaCost()) {
                    bestOp = newOp;
                }
            }
        }
        return bestOp;
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
        str += "Date : " + installationDay.getDate() + "\n";
        str += technician + "\n";
        str += "Covered distance : " + coveredDistance + "\n";
        str += super.toString();
        str += "------------------------------\n";
        return str;
    }

    @Override
    public int compareTo(InstallationRound ir) {
        return ir.getInstallationDay().getDateDiff(this.getInstallationDay());
    }

    public static Comparator<InstallationRound> InstallationRoundDateComparator 
                          = new Comparator<InstallationRound>() {

        public int compare(InstallationRound ir1, InstallationRound ir2) {
          return ir2.compareTo(ir1);
        }
    };

    public static void main(String[] args) {
        // Création d'une installation round simple
        Location home = new Location(1, 0, 0);
        Day d = new Day(5);

        LinkedHashMap<Integer, Boolean> abilities = new LinkedHashMap<Integer, Boolean>();
        abilities.put(1, false);
        abilities.put(2, true);

        Technician t = new Technician(1, home, 20, 4, 20, 10, abilities);
        InstallationRound ir = new InstallationRound(t, d);
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
