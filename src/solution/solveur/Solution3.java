package solution.solveur;

import instance.Instance;
import instance.reseau.Request;
import io.InstanceReader;
import io.SolutionWriter;
import io.exception.ReaderException;
import operateur.InRoundOperator;
import operateur.InRoundOperatorType;
import solution.Solution;

public class Solution3 implements Solveur {

    @Override
    public Solution solve(Instance instance) {
        Solution sol = new Solution(instance);
        // Algorithme de la Solution1
        for (Request r : instance.getRequests().values()) { 
            if (!sol.addRequestExistingDeliveryRoundSolution1(r))
                sol.addRequestNewDeliveryRoundSolution1(r);
            if (!sol.addRequestExistingInstallationRoundSolution1(r))
                sol.addRequestNewInstallationRoundSolution1(r);
        }

        
        // Algorithme de déplacements des requêtes dans chaque tournée
        boolean improve = true;
        while (improve) {
            improve = false;
            for (InRoundOperatorType ope : InRoundOperatorType.values()) {
                InRoundOperator bestOp = sol.getBestInRoundOperator(ope);
                if (bestOp.isMovementBetter()) {
                    if (sol.doInRoundMovement(bestOp))
                        improve = true;
                }
            }
        }        
        
        sol.calculPenalties();
        sol.getMaxTruckDays();

        SolutionWriter sw = new SolutionWriter(instance, sol);
        sw.writeSolution();
        System.out.println("COUT TOTAL : " + sol.getTotalCost());

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
            Solution3 sol = new Solution3();
            sol.solve(instance);
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
