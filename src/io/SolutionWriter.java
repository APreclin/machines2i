package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

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

    public void writeSolution() {
        try {
            String solutionName = "solutions/" + instance.getDataset() + "_" + instance.getName() + ".sol";
            File file = new File(solutionName + ".txt");
            File fileJson = new File("solutions/" + instance.getName() + "_sol.json");

            if (!file.exists())
                file.createNewFile();

            if (!fileJson.exists())
                fileJson.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            FileWriter fwJson = new FileWriter(fileJson.getAbsolutePath());

            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("DATASET = " + instance.getDataset() + "\n");
            bw.write("NAME = " + instance.getName() + "\n");
            bw.write("TRUCK_DISTANCE = " + solution.getTruckDistance() + "\n");
            bw.write("NUMBER_OF_TRUCK_DAYS = " + solution.getNbTruckDays() + "\n");
            bw.write("NUMBER_OF_TRUCKS_USED = " + solution.getNbTrucksUsed() + "\n");
            bw.write("TECHNICIAN_DISTANCE = " + solution.getTechnicianDistance() + "\n");
            bw.write("NUMBER_OF_TECHNICIAN_DAYS = " + solution.getNbTechniciansDays() + "\n");
            bw.write("NUMBER_OF_TECHNICIANS_USED = " + solution.getNbTechniciansUsed() + "\n");
            bw.write("IDLE_MACHINE_COSTS = " + solution.getIdleMachineCosts() + "\n");
            bw.write("TOTAL_COST = " + solution.getTotalCost() + "\n");
            /*
             * bw.write("TRUCK_DISTANCE = " + solution.getTruckDistance()+"\n");
             * bw.write("NUMBER_OF_TRUCK_DAYS = " + solution.getNbTruckDays()+"\n");
             * bw.write("NUMBER_OF_TRUCKS_USED = " + solution.getNbTrucksUsed()+"\n");
             * bw.write("TECHNICIAN_DISTANCE = " + solution.getTechnicianDistance()+"\n");
             * bw.write("NUMBER_OF_TECHNICIAN_DAYS = " +
             * solution.getNbTechniciansDays()+"\n");
             * bw.write("NUMBER_OF_TECHNICIANS_USED = " +
             * solution.getNbTechniciansUsed()+"\n"); bw.write("IDLE_MACHINE_COSTS = " +
             * solution.getIdleMachineCosts()+"\n"); bw.write("TOTAL_COST = " +
             * solution.getTotalCost());
             */

            JSONObject obj = new JSONObject();
            obj.put("dataset", instance.getDataset());
            obj.put("name", instance.getName());
            obj.put("truckDistance", solution.getTruckDistance());
            obj.put("numberOfTruckDays", solution.getNbTruckDays());
            obj.put("numberOfTrucksUsed", solution.getNbTrucksUsed());
            obj.put("technicianDistance", solution.getTechnicianDistance());
            obj.put("numberOfTechnicianDays", solution.getNbTechniciansDays());
            obj.put("numberOfTechniciansUsed", solution.getNbTechniciansUsed());
            obj.put("idleMachineCosts", solution.getIdleMachineCosts());
            obj.put("totalCost", solution.getTotalCost());

            JSONArray days = new JSONArray();

            // Ajouter les jours
            for (int i = 1; i <= instance.getDays(); i++) {
                bw.write("DAY = " + String.valueOf(i) + "\n");
                JSONObject day = new JSONObject();

                if (solution.getDays().containsKey(i)) {
                    HashSet<DeliveryRound> deliveryRounds = solution.getDays().get(i).getDeliveryRounds();
                    HashSet<InstallationRound> installationRounds = solution.getDays().get(i).getInstallationRounds();
                    bw.write("NUMBER_OF_TRUCKS = " + String.valueOf(deliveryRounds.size()) + "\n");
                    day.put("numberOfTrucks", deliveryRounds.size());

                    if (!deliveryRounds.isEmpty()) {
                        JSONArray deliveryRoundsArray = new JSONArray();
                        for (DeliveryRound deliveryRound : deliveryRounds) {
                            bw.write(String.valueOf(deliveryRound.getTruck().getId()));
                            bw.write(" ");

                            JSONObject deliveryRoundObject = new JSONObject();
                            deliveryRoundObject.put("id", deliveryRound.getTruck().getId());
                            JSONObject depot = new JSONObject();
                            depot.put("x", deliveryRound.getDepot().getX());
                            depot.put("y", deliveryRound.getDepot().getY());
                            deliveryRoundObject.put("depot", depot);

                            // On récupère après combien de requests on retourne au dépôt
                            int j = 1;
                            LinkedHashMap<Integer, Integer> returnToDepot = deliveryRound.getReturnToDepot();

                            JSONArray requests = new JSONArray();
                            for (Request request : deliveryRound.getRequests()) {
                                bw.write(String.valueOf(request.getId()));
                                bw.write(" ");

                                // Si après cette request on est retourné au dépôt, on l'indique
                                if (returnToDepot != null) {
                                    if (returnToDepot.containsKey(j)) {
                                        bw.write("0");
                                        bw.write(" ");
                                    }

                                    j++;
                                }

                                JSONObject requestObject = new JSONObject();
                                requestObject.put("id", request.getId());
                                JSONObject location = new JSONObject();
                                location.put("x", request.getLocation().getX());
                                location.put("y", request.getLocation().getY());
                                requestObject.put("location", location);

                                requests.add(requestObject);
                            }

                            deliveryRoundsArray.add(deliveryRoundObject);

                            bw.write("\n");
                        }

                        day.put("deliveryRounds", deliveryRoundsArray);
                    }

                    bw.write("NUMBER_OF_TECHNICIANS = " + String.valueOf(installationRounds.size()) + "\n");
                    day.put("numberOfTechnicians", installationRounds.size());

                    if (!installationRounds.isEmpty()) {
                        JSONArray installationRoundsArray = new JSONArray();
                        for (InstallationRound installationRound : installationRounds) {
                            bw.write(String.valueOf(installationRound.getTechnician().getId()));
                            bw.write(" ");

                            JSONObject installationRoundObject = new JSONObject();
                            installationRoundObject.put("id", installationRound.getTechnician().getId());
                            JSONObject home = new JSONObject();
                            home.put("x", installationRound.getTechnician().getHome().getX());
                            home.put("y", installationRound.getTechnician().getHome().getY());
                            installationRoundObject.put("home", home);

                            JSONArray requests = new JSONArray();

                            for (Request request : installationRound.getRequests()) {
                                bw.write(String.valueOf(request.getId()));
                                bw.write(" ");

                                JSONObject requestObject = new JSONObject();
                                requestObject.put("id", request.getId());
                                JSONObject location = new JSONObject();
                                location.put("x", request.getLocation().getX());
                                location.put("y", request.getLocation().getY());
                                requestObject.put("location", location);

                                requests.add(requestObject);
                            }

                            installationRoundsArray.add(installationRoundObject);

                            bw.write("\n");
                        }

                        day.put("installationRounds", installationRoundsArray);
                    }
                } else {
                    bw.write("NUMBER_OF_TRUCKS = 0\n");
                    bw.write("NUMBER_OF_TECHNICIANS = 0\n");
                    day.put("numberOfTrucks", 0);
                    day.put("numberOfTechnicians", 0);
                }

                days.add(day);
            }

            obj.put("days", days);
            System.out.println(obj.toJSONString());
            fwJson.write(obj.toJSONString());
            fwJson.flush();
            fwJson.close();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
