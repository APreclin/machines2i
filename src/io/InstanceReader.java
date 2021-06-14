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
import io.exception.OpenFileException;
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
    private int distanceCost;
    private int dayCost;

    public InstanceReader() throws ReaderException {
        this.locations = new LinkedHashMap<Integer, Location>();
        this.machines = new LinkedHashMap<Integer, Machine>();

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

    public InstanceReader(String inputPath) throws ReaderException {
        this.locations = new LinkedHashMap<Integer, Location>();
        this.machines = new LinkedHashMap<Integer, Machine>();

        if (inputPath == null) {
            throw new OpenFileException();
        }
        if (!inputPath.endsWith(".txt")) {
            throw new FormatFileException("txt", "txt");
        }
        String instanceName = inputPath;
        this.instanceFile = new File(instanceName);
    }

    /**
     * Read file loaded
     * 
     * @return the instance read
     * @throws ReaderException when data is missing or in the wrong format
     */
    public Instance readInstance() throws ReaderException {
        try {
            FileReader f = new FileReader(this.instanceFile.getAbsolutePath());
            BufferedReader br = new BufferedReader(f);
            String dataset = readDataset(br);
            String name = readName(br);
            int days = readDays(br);
            Truck truck = readTruck(br);
            int truckCost = readTruckCost(br);
            distanceCost = readTechnicianDistanceCost(br);
            dayCost = readTechnicianDayCost(br);
            int technicianCost = readTechnicianCost(br);
            LinkedHashMap<Integer, Machine> machines = readMachines(br);
            this.machines = machines;
            LinkedHashMap<Integer, Location> locations = readLocations(br);
            this.locations = locations;
            LinkedHashMap<Integer, Request> requests = readRequests(br);
            LinkedHashMap<Integer, Technician> technicians = readTechnicians(br);

            Instance instance = new Instance(dataset, name, days, technicianCost, truck, truckCost);
            for (Location location : locations.values())
                instance.addLocation(location);

            for (Machine machine : machines.values())
                instance.addMachine(machine);

            for (Request request : requests.values())
                instance.addRequest(request);

            for (Technician technician : technicians.values())
                instance.addTechnician(technician);

            return instance;
        } catch (FileNotFoundException ex) {
            throw new FileExistException(instanceFile.getName());
        } catch (IOException ex) {
            throw new ReaderException("IO exception", ex.getMessage());
        }
    }

    /**
     * Read dataset line
     * 
     * @param br
     * @return the dataset of the instance
     * @throws IOException
     */
    private String readDataset(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("DATASET = "))
            line = br.readLine();

        line = line.replace("DATASET = ", "");
        return line;
    }

    /**
     * Read name line
     * 
     * @param br
     * @return the name of the instance
     * @throws IOException
     */
    private String readName(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("NAME = "))
            line = br.readLine();

        line = line.replace("NAME = ", "");
        return line;
    }

    /**
     * Read days line
     * 
     * @param br
     * @return the number of days of the instance
     * @throws IOException
     */
    private int readDays(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("DAYS = "))
            line = br.readLine();

        line = line.replace("DAYS = ", "");
        return Integer.parseInt(line);
    }

    /**
     * Read techniciansDistanceCost line
     * 
     * @param br
     * @return the techniciansDistanceCost
     * @throws IOException
     */
    private int readTechnicianDistanceCost(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("TECHNICIAN_DISTANCE_COST = "))
            line = br.readLine();

        line = line.replace("TECHNICIAN_DISTANCE_COST = ", "");
        return Integer.parseInt(line);
    }

    /**
     * Read technicianDayCost line
     * 
     * @param br
     * @return the technicianDayCost
     * @throws IOException
     */
    private int readTechnicianDayCost(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("TECHNICIAN_DAY_COST = "))
            line = br.readLine();

        line = line.replace("TECHNICIAN_DAY_COST = ", "");
        return Integer.parseInt(line);
    }

    /**
     * Read technicianCost
     * 
     * @param br
     * @return the technicianCost
     * @throws IOException
     */
    private int readTechnicianCost(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("TECHNICIAN_COST = "))
            line = br.readLine();

        line = line.replace("TECHNICIAN_COST = ", "");
        return Integer.parseInt(line);
    }

    /**
     * Read the truck
     * 
     * @param br
     * @return a new instance of Truck
     * @throws IOException
     */
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

        Truck truck = new Truck(0, capacity, maxDistance, distanceCost, dayCost);

        return truck;
    }

    /**
     * Read truckCost
     * 
     * @param br
     * @return the truckCost
     * @throws IOException
     */
    private int readTruckCost(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.contains("TRUCK_COST = "))
            line = br.readLine();

        line = line.replace("TRUCK_COST = ", "");
        return Integer.parseInt(line);
    }

    /**
     * Read all the Machine
     * 
     * @param br
     * @return a HashMap of Machine
     * @throws IOException
     */
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

    /**
     * Read all the Location
     * 
     * @param br
     * @return a HashMap of Location
     * @throws IOException
     */
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

    /**
     * Read all the Technician
     * 
     * @param br
     * @return a HashMap of Technician
     * @throws IOException
     */
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

            LinkedHashMap<Integer, Boolean> abilities = new LinkedHashMap<Integer, Boolean>();

            int id = techniciansData.get(0);
            int idLocation = techniciansData.get(1);
            Location location = getLocation(idLocation);
            int maxDistance = techniciansData.get(2);
            int maxRequests = techniciansData.get(3);

            int idMachine = 1;
            for (int j = 4; j < techniciansData.size(); j++) {
                Boolean ability = techniciansData.get(j) == 1;
                abilities.put(idMachine++, ability);
            }

            Technician technician = new Technician(id, location, maxDistance, maxRequests, distanceCost, dayCost,
                    abilities);
            technicians.put(id, technician);

            scanner.close();
        }

        return technicians;
    }

    /**
     * Get Location with an id
     * 
     * @param id
     * @return Location which has id as key
     */
    private Location getLocation(int id) {
        return this.locations.get(id);
    }

    /**
     * Get Machine with an id
     * 
     * @param id
     * @return Machine which has id as key
     */
    private Machine getMachine(int id) {
        return this.machines.get(id);
    }

    /**
     * Read all the Request
     * 
     * @param br
     * @return a HashMap of Request
     * @throws IOException
     */
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
            System.out.println(instance);
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
