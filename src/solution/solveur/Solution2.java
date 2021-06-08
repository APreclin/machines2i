package solution.solveur;

import instance.Instance;
import instance.reseau.Request;
import io.InstanceReader;
import io.SolutionWriter;
import io.exception.ReaderException;
import solution.Solution;

public class Solution2 implements Solveur {

    @Override
    public Solution solve(Instance instance) {
        Solution sol = new Solution(instance);
        for (Request r : instance.getRequests().values()) {
            if (!sol.addRequestExistingDeliveryRoundSolution2(r))
                sol.addRequestNewDeliveryRoundSolution1(r);
            if (!sol.addRequestExistingInstallationRoundSolution1(r))
                sol.addRequestNewInstallationRoundSolution1(r);
        }

        sol.calculPenalties();
        sol.getMaxTruckDays();

        SolutionWriter sw = new SolutionWriter(instance, sol);
        sw.writeSolution();

        return sol;
    }

    @Override
    public String getNom() {
        return "Solution 2 ";
    }

    public static void main(String[] args) {
        try {
            InstanceReader reader = new InstanceReader();
            Instance instance = reader.readInstance();
            Solution2 sol = new Solution2();
            sol.solve(instance);
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
