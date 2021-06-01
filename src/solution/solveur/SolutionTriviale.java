package solution.solveur;

import instance.Instance;
import instance.reseau.Request;
import io.InstanceReader;
import io.SolutionWriter;
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
            SolutionTriviale sol = new SolutionTriviale();
            System.out.println(sol.solve(instance));
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
