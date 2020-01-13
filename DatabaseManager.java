import java.io.*;
import java.util.ArrayList;

/**
 * <h1>Database Manager</h1>
 * <p>
 * Used to locally save and retrieve data.
 *
 * @author Jackson Oriez and Alexmaxfield
 * @version 12/4/2018
 */
public class DatabaseManager {

    /**
     * Creates an ArrayList of Vehicles from the passed CSV file. The values are in
     * the CSV file as followed:
     * <ol>
     * <li>Vehicle Type (Truck/Drone/Cargo Plane)</li>
     * <li>Vehicle License Plate</li>
     * <li>Maximum Carry Weight</li>
     * </ol>
     * If filePath does not exist, a blank ArrayList will be returned.
     *
     * @param file CSV File
     * @return ArrayList of vehicles
     */
    public static ArrayList<Vehicle> loadVehicles(File file) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        ArrayList<String> lines = new ArrayList<>();
        if (!file.exists()) {
            return vehicles;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                } else {
                    lines.add(line);
                }
            }
            for (int i = 0; i < lines.size(); i++) {
                String[] info = lines.get(i).split(",");
                if (info[0].equals("Drone")) {
                    Drone drone = new Drone(info[1], Double.parseDouble(info[2]));
                    vehicles.add(drone);
                } else if (info[0].equals("Truck")) {
                    Truck truck = new Truck(info[1], Double.parseDouble(info[2]));
                    vehicles.add(truck);
                } else if (info[0].equals("CargoPlane")) {
                    CargoPlane cargoPlane = new CargoPlane(info[1], Double.parseDouble(info[2]));
                    vehicles.add(cargoPlane);
                } else {
                    Vehicle vehicle = new Vehicle(info[1], Double.parseDouble(info[2]));
                    vehicles.add(vehicle);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicles;
    }


    /**
     * Creates an ArrayList of Packages from the passed CSV file. The values are in
     * the CSV file as followed:
     * <ol>
     * <li>ID</li>
     * <li>Product Name</li>
     * <li>Weight</li>
     * <li>Price</li>
     * <li>Address Name</li>
     * <li>Address</li>
     * <li>City</li>
     * <li>State</li>
     * <li>ZIP Code</li>
     * </ol>
     * <p>
     * If filePath does not exist, a blank ArrayList will be returned.
     *
     * @param file CSV File
     * @return ArrayList of packages
     */
    public static ArrayList<Package> loadPackages(File file) {
        ArrayList<Package> packages = new ArrayList<>();
        ArrayList<String> lines = new ArrayList<>();
        if (!file.exists()) {
            return packages;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                } else {
                    lines.add(line);
                }
                lines.add(line);
            }
            for (int i = 0; i < lines.size(); i++) {
                String[] info = lines.get(i).split(",");
                String id = info[0];
                String product = info[1];
                double weight = Double.parseDouble(info[2]);
                double price = Double.parseDouble(info[3]);
                String buyer = info[4];
                String address = info[5];
                String city = info[6];
                String state = info[7];
                int zip = Integer.parseInt(info[8]);
                ShippingAddress destination = new ShippingAddress(buyer, address, city, state, zip);
                Package newPackage = new Package(id, product, weight, price, destination);
                packages.add(newPackage);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packages;
    }


    /**
     * Returns the total Profits from passed text file. If the file does not exist 0
     * will be returned.
     *
     * @param file file where profits are stored
     * @return profits from file
     */
    public static double loadProfit(File file) {
        if (!file.exists()) {
            return 0.0;
        }
        double profit = 0.0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            profit = Double.parseDouble(line);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return profit;
    }


    /**
     * Returns the total number of packages shipped stored in the text file. If the
     * file does not exist 0 will be returned.
     *
     * @param file file where number of packages shipped are stored
     * @return number of packages shipped from file
     */
    public static int loadPackagesShipped(File file) {
        if (!file.exists()) {
            return 0;
        }
        int number = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            number = Integer.parseInt(line);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return number;
    }


    /**
     * Returns whether or not it was Prime Day in the previous session. If file does
     * not exist, returns false.
     *
     * @param file file where prime day is stored
     * @return whether or not it is prime day
     */
    public static boolean loadPrimeDay(File file) {
        if (!file.exists()) {
            return false;
        }
        boolean primeDay = false;
        int number;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            number = Integer.parseInt(line);
            primeDay = (number == 1);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return primeDay;
    }


    /**
     * Saves (writes) vehicles from ArrayList of vehicles to file in CSV format one vehicle per line.
     * Each line (vehicle) has following fields separated by comma in the same order.
     * <ol>
     * <li>Vehicle Type (Truck/Drone/Cargo Plane)</li>
     * <li>Vehicle License Plate</li>
     * <li>Maximum Carry Weight</li>
     * </ol>
     *
     * @param file     File to write vehicles to
     * @param vehicles ArrayList of vehicles to save to file
     */
    public static void saveVehicles(File file, ArrayList<Vehicle> vehicles) {
        if (vehicles.size() > 0) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                for (int i = 0; i < vehicles.size(); i++) {
                    String vehicle = vehicles.get(i).getClass().getName();
                    if (vehicle.equals("CargoPlane")) {
                        vehicle = "Cargo Plane";
                    }
                    String output =
                            vehicle + "," + vehicles.get(i).getLicensePlate() +
                                    "," + vehicles.get(i).getMaxWeight();
                    bw.newLine();
                    bw.write(output);
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * Saves (writes) packages from ArrayList of package to file in CSV format one package per line.
     * Each line (package) has following fields separated by comma in the same order.
     * <ol>
     * <li>ID</li>
     * <li>Product Name</li>
     * <li>Weight</li>
     * <li>Price</li>
     * <li>Address Name</li>
     * <li>Address</li>
     * <li>City</li>
     * <li>State</li>
     * <li>ZIP Code</li>
     * </ol>
     *
     * @param file     File to write packages to
     * @param packages ArrayList of packages to save to file
     */
    public static void savePackages(File file, ArrayList<Package> packages) {
        if (packages.size() > 0) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                for (int i = 0; i < packages.size(); i++) {
                    String output =
                            packages.get(i).getID() + "," + packages.get(i).getProduct() +
                                    "," + packages.get(i).getWeight() + "," + packages.get(i).getPrice() +
                                    "," + packages.get(i).getDestination().getName() +
                                    "," + packages.get(i).getDestination().getAddress() +
                                    "," + packages.get(i).getDestination().getCity() +
                                    "," + packages.get(i).getDestination().getState() +
                                    "," + packages.get(i).getDestination().getZipCode();
                    bw.newLine();
                    bw.write(output);
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Saves profit to text file.
     *
     * @param file   File to write profits to
     * @param profit Total profits
     */

    public static void saveProfit(File file, double profit) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String money = profit + "";
            bw.write(money);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Saves number of packages shipped to text file.
     *
     * @param file      File to write profits to
     * @param nPackages Number of packages shipped
     */

    public static void savePackagesShipped(File file, int nPackages) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String number = nPackages + "";
            bw.write(number);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Saves status of prime day to text file. If it is primeDay "1" will be
     * writtern, otherwise "0" will be written.
     *
     * @param file     File to write profits to
     * @param primeDay Whether or not it is Prime Day
     */

    public static void savePrimeDay(File file, boolean primeDay) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            int number;
            if (primeDay) {
                number = 1;
            } else {
                number = 0;
            }
            bw.write(number);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}