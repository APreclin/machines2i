package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;

import instance.Instance;
import instance.reseau.Truck;
import io.exception.FileExistException;
import io.exception.FormatFileException;
import io.exception.ReaderException;

/**
 * Classe permettant de lire une instance pour le projet Machines2i
 * 
 * Les instances sont au format ".txt".
 */

public class InstanceReader {
    /**
     * Le fichier contenant l'instance.
     */
    private File instanceFile;

    public InstanceReader() throws ReaderException {
        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);

        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();

        if (!file.getAbsolutePath().endsWith(".txt")) {
            throw new FormatFileException("txt", "txt");
        }

        this.instanceFile = file;
    }

    public Instance readInstance() throws ReaderException {
        try {
            FileReader f = new FileReader(this.instanceFile.getAbsolutePath());
            BufferedReader br = new BufferedReader(f);
            String dataset = readDataset(br);
            String name = readName(br);
            int days = readDays(br);
            int technicianDistanceCost = readTechnicianDistanceCost(br);
            int technicianDayCost = readTechnicianDayCost(br);
            int technicianCost = readTechnicianCost(br);
        } catch (FileNotFoundException ex) {
            throw new FileExistException(instanceFile.getName());
        } catch (IOException ex) {
            throw new ReaderException("IO exception", ex.getMessage());
        }

        return null;
    }

    private String readDataset(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("DATASET = ")) {
            line = br.readLine();
        }
        line = line.replace("DATASET = ", "");
        return line;
    }

    private String readName(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("NAME = ")) {
            line = br.readLine();
        }
        line = line.replace("NAME = ", "");
        return line;
    }

    private int readDays(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("DAYS = ")) {
            line = br.readLine();
        }
        line = line.replace("DAYS = ", "");
        return Integer.parseInt(line);
    }

    private int readTechnicianDistanceCost(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("TECHNICIAN_DISTANCE_COST = ")) {
            line = br.readLine();
        }
        line = line.replace("TECHNICIAN_DISTANCE_COST = ", "");
        return Integer.parseInt(line);
    }

    private int readTechnicianDayCost(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("TECHNICIAN_DAY_COST = ")) {
            line = br.readLine();
        }
        line = line.replace("TECHNICIAN_DAY_COST = ", "");
        return Integer.parseInt(line);
    }

    private int readTechnicianCost(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("TECHNICIAN_COST = ")) {
            line = br.readLine();
        }
        line = line.replace("TECHNICIAN_COST = ", "");
        return Integer.parseInt(line);
    }

    private Truck readTruck(BufferedReader br) throws IOException {
        return null;
    }

    public static void main(String[] args) {
        try {
            InstanceReader reader = new InstanceReader();
            Instance instance = reader.readInstance();
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
