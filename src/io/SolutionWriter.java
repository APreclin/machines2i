package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import instance.Instance;

public class SolutionWriter {

    private Instance instance;

    public SolutionWriter(Instance instance) {
        this.instance = instance;
    }

    public Instance getInstance() {
        return instance;
    }

    public void rightSolution() {
        try {

            File file = new File("solution_" + this.instance.getName() + ".txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("DATASET = " + this.instance.getDataset());
            bw.write("NAME = " + this.instance.getName());
            bw.write("TRUCK_DISTANCE = ????????????");
            bw.write("NUMBER_OF_TRUCK_DAYS = ????????????");
            bw.write("NUMBER_OF_TRUCKS_USED = ????????????");
            bw.write("TECHNICIAN_DISTANCE = ????????????");
            bw.write("NUMBER_OF_TECHNICIAN_DAYS = ????????????");
            bw.write("NUMBER_OF_TECHNICIANS_USED = ????????????");
            bw.write("IDLE_MACHINE_COSTS = ????????????");
            bw.write("TOTAL_COST = ????????????");
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
