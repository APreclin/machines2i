package solution.solveur;

import instance.Instance;
import instance.reseau.Request;
import io.InstanceReader;
import io.SolutionWriter;
import io.exception.ReaderException;
import solution.Solution;

public class Solution1 implements Solveur {

    @Override
    public Solution solve(Instance instance) {
        Solution sol = new Solution(instance);
        for (Request r : instance.getRequests().values()) { 
            if (!sol.addRequestExistingDeliveryRoundSolution1(r))
                sol.addRequestNewDeliveryRoundSolution1(r);
            if (!sol.addRequestExistingInstallationRoundSolution1(r))
                sol.addRequestNewInstallationRoundSolution1(r);
        }

        sol.calculPenalties();
        // sol.getMaxTechnicianDays();
        sol.getMaxTruckDays();

        SolutionWriter sw = new SolutionWriter(instance, sol);
        sw.writeSolution();

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
            Solution1 sol = new Solution1();
            sol.solve(instance);
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
