package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import instance.Instance;
import solution.Solution;

public class SolutionWriter {

    private Instance instance;
    private Solution solution;

    public SolutionWriter(Instance instance, Solution solution) {
        this.instance = instance;
        this.solution = solution;
    }

    public Instance getInstance() {
        return instance;
    }

    public Solution getSolution() {
        return solution;
    }

    public void writeSolution() {
        try {

            File file = new File(instance.getName() + "_sol.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("DATASET = " + instance.getDataset()+"\n");
            bw.write("NAME = " + instance.getName()+"\n");
            /*
            bw.write("TRUCK_DISTANCE = " + solution.getTruckDistance()+"\n");
            bw.write("NUMBER_OF_TRUCK_DAYS = " + solution.getNbTruckDays()+"\n");
            bw.write("NUMBER_OF_TRUCKS_USED = " + solution.getNbTrucksUsed()+"\n");
            bw.write("TECHNICIAN_DISTANCE = " + solution.getTechnicianDistance()+"\n");
            bw.write("NUMBER_OF_TECHNICIAN_DAYS = " + solution.getNbTechniciansDays()+"\n");
            bw.write("NUMBER_OF_TECHNICIANS_USED = " + solution.getNbTechniciansUsed()+"\n");
            bw.write("IDLE_MACHINE_COSTS = " + solution.getIdleMachineCosts()+"\n");
            bw.write("TOTAL_COST = " + solution.getTotalCost());
            */
            // Ajouter les jours
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
