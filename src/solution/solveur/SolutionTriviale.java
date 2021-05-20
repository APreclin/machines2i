package solution.solveur;

import instance.Instance;
import instance.reseau.Request;
import io.InstanceReader;
import io.exception.ReaderException;
import solution.Solution;

public class SolutionTriviale implements Solveur {

    @Override
    public Solution solve(Instance instance) {
        Solution sol = new Solution(instance);
        for (Request r : instance.getRequests().values()) {
            if (!sol.addRequestExistingDeliveryRound(r))
                sol.addRequestNewDeliveryRound(r);
            if (!sol.addRequestExistingInstallationRound(r))
                sol.addRequestNewInstallationRound(r);

            sol.calculPenalities();
        }
        return sol;
    }

    @Override
    public String getNom() {
        return "Solution Triviale ";
    }

    public static void main(String[] args) {
        try {
            InstanceReader reader = new InstanceReader();
            Instance instance = reader.readInstance();
            System.out.println(instance);
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
