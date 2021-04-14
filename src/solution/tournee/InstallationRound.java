package solution.tournee;

public class InstallationRound extends Round {

    private int nbRequests;

    public InstallationRound() {

        super();
        this.nbRequests = 0;
    }

    @Override
    public String toString() {
        return "InstallationRound [" + super.toString() + "nbRequests=" + nbRequests + "]";
    }

}
