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
            bw.write("----- SOLUTION : " + this.instance.getName() + " -----");
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
