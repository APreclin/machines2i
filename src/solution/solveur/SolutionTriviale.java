package solution.solveur;

import java.util.LinkedHashMap;

import instance.Instance;
import instance.reseau.Request;
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
        }
        return sol;
    }

    @Override
    public String getNom() {
        return "Solution Triviale ";
    }

    public static void main(String[] args) {
        //TODO: test unitaire solutionTriviale
        
    }


}
