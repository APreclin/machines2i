package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import instance.Instance;
import instance.reseau.Request;
import solution.Solution;
import solution.tournee.DeliveryRound;
import solution.tournee.InstallationRound;

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

    public void rightSolution() {
        try {

            File file = new File(instance.getName() + "_sol.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("DATASET = " + instance.getDataset());
            bw.write("NAME = " + instance.getName());
            bw.write("TRUCK_DISTANCE = " + solution.getTruckDistance());
            bw.write("NUMBER_OF_TRUCK_DAYS = " + solution.getNbTruckDays());
            bw.write("NUMBER_OF_TRUCKS_USED = " + solution.getNbTrucksUsed());
            bw.write("TECHNICIAN_DISTANCE = " + solution.getTechnicianDistance());
            bw.write("NUMBER_OF_TECHNICIAN_DAYS = " + solution.getNbTechniciansDays());
            bw.write("NUMBER_OF_TECHNICIANS_USED = " + solution.getNbTechniciansUsed());
            bw.write("IDLE_MACHINE_COSTS = " + solution.getIdleMachineCosts());
            bw.write("TOTAL_COST = " + solution.getTotalCost());
            // Ajouter les jours
            for (int i = 1; i <= instance.getDays(); i++) {
                bw.write("DAY = " + String.valueOf(i) + "\n");
                if (solution.getDays().containsKey(i)) {
                    HashSet<DeliveryRound> deliveryRounds = solution.getDays().get(i).getDeliveryRounds();
                    HashSet<InstallationRound> installationRounds = solution.getDays().get(i).getInstallationRounds();
                    bw.write("NUMBER_OF_TRUCKS = " + String.valueOf(deliveryRounds.size()) + "\n");
                    if (!deliveryRounds.isEmpty()) {
                        for (DeliveryRound deliveryRound : deliveryRounds) {
                            for (Request request : deliveryRound.getRequests()) {
                                bw.write(String.valueOf(request.getId()));
                                bw.write(" ");
                            }
                            bw.write("\n");
                        }
                    }
                    bw.write("NUMBER_OF_TECHNICIANS = " + String.valueOf(installationRounds.size()) + "\n");
                    if (!installationRounds.isEmpty()) {
                        for (InstallationRound installationRound : installationRounds) {
                            for (Request request : installationRound.getRequests()) {
                                bw.write(String.valueOf(request.getId()));
                                bw.write(" ");
                            }
                            bw.write("\n");
                        }
                    }
                } else {
                    bw.write("NUMBER_OF_TRUCKS = 0\n");
                    bw.write("NUMBER_OF_TECHNICIANS = 0\n");
                }
            }

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
