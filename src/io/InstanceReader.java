package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import instance.Instance;
import instance.reseau.Location;
import instance.reseau.Machine;
import instance.reseau.Request;
import instance.reseau.Technician;
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
    private LinkedHashMap<Integer, Location> locations;
    private LinkedHashMap<Integer, Machine> machines;
    private LinkedHashMap<Integer, Request> requests;

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
            LinkedHashMap<Integer, Machine> machines = readMachines(br);
            this.machines = machines;
            LinkedHashMap<Integer, Location> locations = readLocations(br);
            this.locations = locations;
            LinkedHashMap<Integer, Request> requests = readRequests(br);
            this.requests = requests;
            System.out.println(requests);
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

        Truck truck = new Truck(capacity, maxDistance, distanceCost, dayCost, cost);

        return truck;
    }

    private LinkedHashMap<Integer, Machine> readMachines(BufferedReader br) throws IOException {
        LinkedHashMap<Integer, Machine> machines = new LinkedHashMap<Integer, Machine>();
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
            machines.put(id, machine);

            scanner.close();
        }

        return machines;
    }

    private LinkedHashMap<Integer, Location> readLocations(BufferedReader br) throws IOException {
        LinkedHashMap<Integer, Location> locations = new LinkedHashMap<Integer, Location>();
        String line = br.readLine();
        while (!line.contains("LOCATIONS = "))
            line = br.readLine();

        line = line.replace("LOCATIONS = ", "");
        int locationsLength = Integer.parseInt(line);

        for (int i = 0; i < locationsLength; i++) {
            line = br.readLine();
            Scanner scanner = new Scanner(line);
            List<Integer> locationsData = new ArrayList<>();
            while (scanner.hasNextInt())
                locationsData.add(scanner.nextInt());

            int id = locationsData.get(0);
            int x = locationsData.get(1);
            int y = locationsData.get(2);

            Location location = new Location(id, x, y);
            locations.put(id, location);

            scanner.close();
        }

        return locations;
    }

    private LinkedHashMap<Integer, Technician> readTechnicians(BufferedReader br) throws IOException {
        LinkedHashMap<Integer, Technician> technicians = new LinkedHashMap<Integer, Technician>();
        String line = br.readLine();
        while (!line.contains("TECHNICIANS = "))
            line = br.readLine();

        line = line.replace("TECHNICIANS = ", "");
        int techniciansLength = Integer.parseInt(line);

        for (int i = 0; i < techniciansLength; i++) {
            line = br.readLine();
            Scanner scanner = new Scanner(line);
            List<Integer> techniciansData = new ArrayList<>();
            while (scanner.hasNextInt())
                techniciansData.add(scanner.nextInt());

            int id = techniciansData.get(0);
            int idLocation = techniciansData.get(1);
            Location location = getLocation(idLocation);
            int maxDistance = techniciansData.get(2);
            int maxRequests = techniciansData.get(3);

            Technician technician = new Technician(id, location, maxDistance, maxRequests);
            technicians.put(id, technician);

            scanner.close();
        }

        return technicians;
    }

    private Location getLocation(int id) {
        return this.locations.get(id);
    }

    private Machine getMachine(int id) {
        return this.machines.get(id);
    }

    private LinkedHashMap<Integer, Request> readRequests(BufferedReader br) throws IOException {
        LinkedHashMap<Integer, Request> requests = new LinkedHashMap<Integer, Request>();
        String line = br.readLine();
        while (!line.contains("REQUESTS ="))
            line = br.readLine();

        line = line.replace("REQUESTS = ", "");
        int requestsLength = Integer.parseInt(line);

        for (int i = 0; i < requestsLength; i++) {
            line = br.readLine();
            Scanner scanner = new Scanner(line);
            List<Integer> requestsData = new ArrayList<>();
            while (scanner.hasNext())
                requestsData.add(scanner.nextInt());

            int id = requestsData.get(0);
            Location requestLocation = getLocation(requestsData.get(1));
            int firstDay = requestsData.get(2);
            int lastDay = requestsData.get(3);
            Machine machine = getMachine(requestsData.get(4));
            int nbMachines = requestsData.get(5);

            Request request = new Request(id, requestLocation, firstDay, lastDay, machine, nbMachines);
            requests.put(id, request);

            scanner.close();
        }

        return requests;
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
