package solution;

import java.util.LinkedList;

import instance.Instance;
import solution.tournee.Round;

public class Solution {
    private Instance instance;
    private LinkedList<Round> rounds;
    private Integer truckDistance;
    private Integer nbTruckDays;
    private Integer nbTrucksUsed;
    private Integer technicianDistance;
    private Integer nbTechniciansUsed;
    private Integer idleMachineCosts;
    private Integer totalCost;

    public Solution() {
        instance = new Instance();
        truckDistance = 0;
        nbTruckDays = 0;
        nbTrucksUsed = 0;
        technicianDistance = 0;
        nbTechniciansUsed = 0;
        idleMachineCosts = 0;
        totalCost = 0;
    }

    public Solution(Instance instanceToCopy) {
        instance = instanceToCopy;
        rounds = new LinkedList<Round>();
        totalCost = 0;
    }

    public Integer getTotalCost() {
        return this.totalCost;
    }

    public Instance getInstance() {
        return instance;
    }

    public LinkedList<Round> getRounds() {
        return rounds;
    }

    @Override
    public String toString() {
        return "Solution [idleMachineCosts=" + idleMachineCosts + ", instance=" + instance + ", nbTechniciansUsed="
                + nbTechniciansUsed + ", nbTruckDays=" + nbTruckDays + ", nbTrucksUsed=" + nbTrucksUsed + ", rounds="
                + rounds + ", technicianDistance=" + technicianDistance + ", totalCost=" + totalCost
                + ", truckDistance=" + truckDistance + "]";
    }

}
