package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import instance.Instance;
import instance.reseau.Machine;
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
            Truck truck = readTruck(br);
            int technicianDistanceCost = readTechnicianDistanceCost(br);
            int technicianDayCost = readTechnicianDayCost(br);
            int technicianCost = readTechnicianCost(br);
            List<Machine> machines = readMachines(br);
            System.out.println(machines);
            System.out.println(truck);
        } catch (FileNotFoundException ex) {
            throw new FileExistException(instanceFile.getName());
        } catch (IOException ex) {
            throw new ReaderException("IO exception", ex.getMessage());
        }

        return null;
    }

    private String readDataset(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("DATASET = "))
            line = br.readLine();

        line = line.replace("DATASET = ", "");
        return line;
    }

    private String readName(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("NAME = "))
            line = br.readLine();

        line = line.replace("NAME = ", "");
        return line;
    }

    private int readDays(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("DAYS = "))
            line = br.readLine();

        line = line.replace("DAYS = ", "");
        return Integer.parseInt(line);
    }

    private int readTechnicianDistanceCost(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("TECHNICIAN_DISTANCE_COST = "))
            line = br.readLine();

        line = line.replace("TECHNICIAN_DISTANCE_COST = ", "");
        return Integer.parseInt(line);
    }

    private int readTechnicianDayCost(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("TECHNICIAN_DAY_COST = "))
            line = br.readLine();

        line = line.replace("TECHNICIAN_DAY_COST = ", "");
        return Integer.parseInt(line);
    }

    private int readTechnicianCost(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("TECHNICIAN_COST = "))
            line = br.readLine();

        line = line.replace("TECHNICIAN_COST = ", "");
        return Integer.parseInt(line);
    }

    private Truck readTruck(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("TRUCK_CAPACITY = "))
            line = br.readLine();

        line = line.replace("TRUCK_CAPACITY = ", "");
        int capacity = Integer.parseInt(line);

        line = br.readLine();
        line = line.replace("TRUCK_MAX_DISTANCE = ", "");
        int maxDistance = Integer.parseInt(line);

        line = br.readLine();
        line = br.readLine();
        line = line.replace("TRUCK_DISTANCE_COST = ", "");
        int distanceCost = Integer.parseInt(line);

        line = br.readLine();
        line = line.replace("TRUCK_DAY_COST = ", "");
        int dayCost = Integer.parseInt(line);

        line = br.readLine();
        line = line.replace("TRUCK_COST = ", "");
        int cost = Integer.parseInt(line);

        Truck truck = new Truck(capacity, maxDistance, distanceCost, dayCost);

        return truck;
    }

    private List<Machine> readMachines(BufferedReader br) throws IOException {
        List<Machine> machines = new ArrayList<>();
        String line = br.readLine();
        while (!line.contains("MACHINES = "))
            line = br.readLine();

        line = line.replace("MACHINES = ", "");
        int machinesLength = Integer.parseInt(line);

        for (int i = 0; i < machinesLength; i++) {
            line = br.readLine();
            Scanner scanner = new Scanner(line);
            List<Integer> machinesData = new ArrayList<>();
            while (scanner.hasNextInt())
                machinesData.add(scanner.nextInt());

            int id = machinesData.get(0);
            int size = machinesData.get(1);
            int penaltyByDay = machinesData.get(2);

            Machine machine = new Machine(id, size, penaltyByDay);
            machines.add(machine);

            scanner.close();
        }

        return machines;
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
