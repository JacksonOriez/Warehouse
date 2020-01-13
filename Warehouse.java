import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Warehouse</h1>
 *
 * @author Jackson Oriez and Alexmaxfield
 * @version 12/4/2018
 */

public class Warehouse {
    final static String FOLDER_PATH = "files/";
    final static File VEHICLE_FILE = new File(FOLDER_PATH + "VehicleList.csv");
    final static File PACKAGE_FILE = new File(FOLDER_PATH + "PackageList.csv");
    final static File PROFIT_FILE = new File(FOLDER_PATH + "Profit.txt");
    final static File N_PACKAGES_FILE = new File(FOLDER_PATH + "NumberOfPackages.txt");
    final static File PRIME_DAY_FILE = new File(FOLDER_PATH + "PrimeDay.txt");
    final static double PRIME_DAY_DISCOUNT = .15;
    final static String ERROR_MESSAGE = "Error: Option not available.\n";
    final static String VEHICLE_ERROR = "Error: No vehicles available.";
    final static String PACKAGE_ERROR = "Error: No packages available.";
    final static String SEND_VEHICLE_ERROR = "Error: No vehicles of selected type are available.";
    final static String ADD_VEHICLE_MENU = "Vehicle Options:\n" +
            "1) Truck\n" +
            "2) Drone\n" +
            "3) Cargo Plane";
    final static String SEND_VEHICLE_MENU = "Options:\n" +
            "1) Send Truck\n" +
            "2) Send Drone\n" +
            "3) Send Cargo Plane\n" +
            "4) Send First Available";
    final static String ZIP_MENU = "ZIP Code Options:\n" +
            "1) Send to first ZIP Code\n" +
            "2) Send to mode of ZIP Codes";
    static String mainMenu = "==========Options==========\n" +
            "1) Add Package\n" +
            "2) Add Vehicle\n" +
            "3) Activate Prime Day\n" +
            "4) Send Vehicle\n" +
            "5) Print Statistics\n" +
            "6) Exit\n" +
            "===========================";

    /**
     * Main Method
     *
     * @param args list of command line arguements
     */
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        ArrayList<Vehicle> vehicles = databaseManager.loadVehicles(VEHICLE_FILE);
        ArrayList<Package> packages = databaseManager.loadPackages(PACKAGE_FILE);
        double profit = databaseManager.loadProfit(PROFIT_FILE);
        int nPackages = databaseManager.loadPackagesShipped(N_PACKAGES_FILE);
        boolean primeDay = databaseManager.loadPrimeDay(PRIME_DAY_FILE);
        Scanner scanner = new Scanner(System.in);
        boolean keepLooping = true;
        boolean error = false;
        while (keepLooping) {
            if (primeDay) {
                mainMenu = "==========Options==========\n" +
                        "1) Add Package\n" +
                        "2) Add Vehicle\n" +
                        "3) Deactivate Prime Day\n" +
                        "4) Send Vehicle\n" +
                        "5) Print Statistics\n" +
                        "6) Exit\n" +
                        "===========================";
            } else {
                mainMenu = "==========Options==========\n" +
                        "1) Add Package\n" +
                        "2) Add Vehicle\n" +
                        "3) Activate Prime Day\n" +
                        "4) Send Vehicle\n" +
                        "5) Print Statistics\n" +
                        "6) Exit\n" +
                        "===========================";
            }
            System.out.println(mainMenu);
            String choice = scanner.nextLine();
            int choiceInt = -1;
            try {
                choiceInt = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println(ERROR_MESSAGE);
                keepLooping = true;
                error = true;
            }
            if (choiceInt > 6 || choiceInt < 0) {
                if (!error) {
                    System.out.println(ERROR_MESSAGE);
                }
                keepLooping = true;
            } else {
                switch (choiceInt) {
                    case 1:
                        System.out.println("Enter Package ID:");
                        String id = scanner.nextLine();
                        System.out.println("Enter Product Name:");
                        String productName = scanner.nextLine();
                        System.out.println("Enter Weight:");
                        double weight = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.println("Enter Price:");
                        double price = scanner.nextDouble();
                        scanner.nextLine();
                        if (primeDay) {
                            price = price * 0.85;
                        }
                        System.out.println("Enter Buyer Name:");
                        String buyerName = scanner.nextLine();
                        System.out.println("Enter Address:");
                        String address = scanner.nextLine();
                        System.out.println("Enter City:");
                        String city = scanner.nextLine();
                        System.out.println("Enter State:");
                        String state = scanner.nextLine();
                        System.out.println("Enter ZIP Code:");
                        int zip = scanner.nextInt();
                        scanner.nextLine();
                        ShippingAddress destination = new ShippingAddress(buyerName, address, city, state, zip);
                        Package newPackage = new Package(id, productName, weight, price, destination);
                        packages.add(newPackage);
                        System.out.println(newPackage.shippingLabel());
                        break;
                    case 2:
                        boolean keepGoing = false;
                        while (true) {
                            System.out.println(ADD_VEHICLE_MENU);
                            String input = scanner.nextLine();
                            int inputInt = -1;
                            try {
                                inputInt = Integer.parseInt(input);
                            } catch (NumberFormatException e) {
                                System.out.println(ERROR_MESSAGE);
                                keepGoing = true;
                            }
                            if (keepGoing) {
                                continue;
                            } else if (inputInt < 0 || inputInt > 4) {
                                System.out.println(ERROR_MESSAGE);
                            } else {
                                break;
                            }
                        }
                        switch (choiceInt) {
                            case 1:
                                System.out.println("Enter License Plate No.:");
                                String truckNumber = scanner.nextLine();
                                System.out.println("Enter Maximum Carry Weight:");
                                double truckWeight = scanner.nextDouble();
                                scanner.nextLine();
                                Truck truck = new Truck(truckNumber, truckWeight);
                                vehicles.add(truck);
                                break;
                            case 2:
                                System.out.println("Enter License Plate No.:");
                                String droneNumber = scanner.nextLine();
                                System.out.println("Enter Maximum Carry Weight:");
                                double droneWeight = scanner.nextDouble();
                                scanner.nextLine();
                                Drone drone = new Drone(droneNumber, droneWeight);
                                vehicles.add(drone);
                                break;
                            case 3:
                                System.out.println("Enter License Plate No.:");
                                String planeNumber = scanner.nextLine();
                                System.out.println("Enter Maximum Carry Weight:");
                                double planeWeight = scanner.nextDouble();
                                scanner.nextLine();
                                CargoPlane cargoPlane = new CargoPlane(planeNumber, planeWeight);
                                vehicles.add(cargoPlane);
                                break;
                        }
                        break;

                    case 3:
                        if (!primeDay) {
                            primeDay = true;
                            for (int i = 0; i < packages.size(); i++) {
                                Package currentPackage = packages.get(i);
                                double currentPrice = currentPackage.getPrice();
                                packages.get(i).setPrice(currentPrice * 0.85);
                            }
                        } else {
                            primeDay = false;
                            for (int i = 0; i < packages.size(); i++) {
                                Package currentPackage = packages.get(i);
                                double currentPrice = currentPackage.getPrice();
                                packages.get(i).setPrice(currentPrice / 0.85);
                            }
                        }
                        break;

                    case 4:
                        if (vehicles.size() == 0) {
                            System.out.println(VEHICLE_ERROR);
                        } else if (packages.size() == 0) {
                            System.out.println(PACKAGE_ERROR);
                        } else {
                            System.out.println(SEND_VEHICLE_MENU);
                            int vehicleChoice = scanner.nextInt();
                            scanner.nextLine();
                            if (vehicleChoice < 0 || vehicleChoice > 4) {
                                System.out.println(ERROR_MESSAGE);
                                break;
                            }
                            boolean has = false;
                            Vehicle vehicle = new Vehicle();
                            switch (vehicleChoice) {
                                case 1:
                                    for (int i = 0; i < vehicles.size(); i++) {
                                        if (vehicles.get(i) instanceof Truck) {
                                            has = true;
                                            vehicle = vehicles.get(i);
                                        }
                                    }
                                    if (!has) {
                                        System.out.println(SEND_VEHICLE_ERROR);
                                    } else {
                                        System.out.println(ZIP_MENU);
                                        int zipChoice = scanner.nextInt();
                                        scanner.nextLine();
                                        if (zipChoice == 1) {
                                            vehicle.setZipDest(packages.get(0).getDestination().getZipCode());
                                            vehicle.fill(packages);
                                            System.out.println(vehicle.report());
                                            profit = profit + vehicle.getProfit();
                                            nPackages = nPackages + packages.size();
                                        } else if (zipChoice == 2) {
                                            int[] zips = new int[packages.size()];
                                            for (int i = 0; i < packages.size(); i++) {
                                                zips[i] = packages.get(i).getDestination().getZipCode();
                                            }
                                            int mode = 0;
                                            int current;
                                            int counterCurrent;
                                            int counterMost = 0;
                                            for (int i = 0; i < zips.length; i++) {
                                                current = zips[i];
                                                counterCurrent = 1;
                                                for (int j = 0; j < zips.length; j++) {
                                                    if (zips[i] == zips[j] && i != j) {
                                                        current = zips[i];
                                                        counterCurrent++;
                                                    }
                                                    if (counterCurrent > counterMost) {
                                                        mode = current;
                                                        counterMost = counterCurrent;
                                                    }
                                                }
                                            }
                                            vehicle.setZipDest(mode);
                                            vehicle.fill(packages);
                                            System.out.println(vehicle.report());
                                            profit = profit + vehicle.getProfit();
                                            nPackages = nPackages + packages.size();
                                        } else {
                                            System.out.println(ERROR_MESSAGE);
                                        }
                                    }
                                    break;
                                case 2:
                                    for (int i = 0; i < vehicles.size(); i++) {
                                        if (vehicles.get(i) instanceof Drone) {
                                            has = true;
                                            vehicle = vehicles.get(i);
                                        }
                                    }
                                    if (!has) {
                                        System.out.println(SEND_VEHICLE_ERROR);
                                    } else {
                                        System.out.println(ZIP_MENU);
                                        int zipChoice = scanner.nextInt();
                                        scanner.nextLine();
                                        if (zipChoice == 1) {
                                            vehicle.setZipDest(packages.get(0).getDestination().getZipCode());
                                            vehicle.fill(packages);
                                            System.out.println(vehicle.report());
                                            profit = profit + vehicle.getProfit();
                                            nPackages = nPackages + packages.size();
                                        } else if (zipChoice == 2) {
                                            int[] zips = new int[packages.size()];
                                            for (int i = 0; i < packages.size(); i++) {
                                                zips[i] = packages.get(i).getDestination().getZipCode();
                                            }
                                            int mode = 0;
                                            int current;
                                            int counterCurrent;
                                            int counterMost = 0;
                                            for (int i = 0; i < zips.length; i++) {
                                                current = zips[i];
                                                counterCurrent = 1;
                                                for (int j = 0; j < zips.length; j++) {
                                                    if (zips[i] == zips[j] && i != j) {
                                                        current = zips[i];
                                                        counterCurrent++;
                                                    }
                                                    if (counterCurrent > counterMost) {
                                                        mode = current;
                                                        counterMost = counterCurrent;
                                                    }
                                                }
                                            }
                                            vehicle.setZipDest(mode);
                                            vehicle.fill(packages);
                                            System.out.println(vehicle.report());
                                            profit = profit + vehicle.getProfit();
                                            nPackages = nPackages + packages.size();
                                        } else {
                                            System.out.println(ERROR_MESSAGE);
                                        }
                                    }
                                    break;
                                case 3:
                                    for (int i = 0; i < vehicles.size(); i++) {
                                        if (vehicles.get(i) instanceof CargoPlane) {
                                            has = true;
                                            vehicle = vehicles.get(i);
                                        }
                                    }
                                    if (!has) {
                                        System.out.println(SEND_VEHICLE_ERROR);
                                    } else {
                                        System.out.println(ZIP_MENU);
                                        int zipChoice = scanner.nextInt();
                                        scanner.nextLine();
                                        if (zipChoice == 1) {
                                            vehicle.setZipDest(packages.get(0).getDestination().getZipCode());
                                            vehicle.fill(packages);
                                            System.out.println(vehicle.report());
                                            profit = profit + vehicle.getProfit();
                                            nPackages = nPackages + packages.size();
                                        } else if (zipChoice == 2) {
                                            int[] zips = new int[packages.size()];
                                            for (int i = 0; i < packages.size(); i++) {
                                                zips[i] = packages.get(i).getDestination().getZipCode();
                                            }
                                            int mode = 0;
                                            int current;
                                            int counterCurrent;
                                            int counterMost = 0;
                                            for (int i = 0; i < zips.length; i++) {
                                                current = zips[i];
                                                counterCurrent = 1;
                                                for (int j = 0; j < zips.length; j++) {
                                                    if (zips[i] == zips[j] && i != j) {
                                                        current = zips[i];
                                                        counterCurrent++;
                                                    }
                                                    if (counterCurrent > counterMost) {
                                                        mode = current;
                                                        counterMost = counterCurrent;
                                                    }
                                                }
                                            }
                                            vehicle.setZipDest(mode);
                                            vehicle.fill(packages);
                                            System.out.println(vehicle.report());
                                            profit = profit + vehicle.getProfit();
                                            nPackages = nPackages + packages.size();
                                        } else {
                                            System.out.println(ERROR_MESSAGE);
                                        }
                                    }
                                    break;
                                case 4:
                                    vehicle = vehicles.get(0);
                                    System.out.println(ZIP_MENU);
                                    int zipChoice = scanner.nextInt();
                                    scanner.nextLine();
                                    if (zipChoice == 1) {
                                        vehicle.setZipDest(packages.get(0).getDestination().getZipCode());
                                        vehicle.fill(packages);
                                        System.out.println(vehicle.report());
                                        profit = profit + vehicle.getProfit();
                                        nPackages = nPackages + packages.size();
                                    } else if (zipChoice == 2) {
                                        int[] zips = new int[packages.size()];
                                        for (int i = 0; i < packages.size(); i++) {
                                            zips[i] = packages.get(i).getDestination().getZipCode();
                                        }
                                        int mode = 0;
                                        int current;
                                        int counterCurrent;
                                        int counterMost = 0;
                                        for (int i = 0; i < zips.length; i++) {
                                            current = zips[i];
                                            counterCurrent = 1;
                                            for (int j = 0; j < zips.length; j++) {
                                                if (zips[i] == zips[j] && i != j) {
                                                    current = zips[i];
                                                    counterCurrent++;
                                                }
                                                if (counterCurrent > counterMost) {
                                                    mode = current;
                                                    counterMost = counterCurrent;
                                                }
                                            }
                                        }
                                        vehicle.setZipDest(mode);
                                        vehicle.fill(packages);
                                        System.out.println(vehicle.report());
                                        profit = profit + vehicle.getProfit();
                                        nPackages = nPackages + packages.size();
                                    } else {
                                        System.out.println(ERROR_MESSAGE);
                                    }
                                    break;
                            }
                        }
                        break;
                    case 5:
                        printStatisticsReport(profit, nPackages, packages.size());
                        break;
                    case 6:
                        keepLooping = false;
                        databaseManager.savePackages(PACKAGE_FILE, packages);
                        databaseManager.savePackagesShipped(N_PACKAGES_FILE, nPackages);
                        databaseManager.savePrimeDay(PRIME_DAY_FILE, primeDay);
                        databaseManager.saveProfit(PROFIT_FILE, profit);
                        databaseManager.saveVehicles(VEHICLE_FILE, vehicles);
                        break;
                }
            }
        }
    }

    public static void printStatisticsReport(double profit, int nPackages, int packageArraySize) {
        String profitString = String.format("$%.2f", profit);
        String statisticsReport = "\n==========Statistics==========\n" +
                "Profits:                     " + profitString + "\n" +
                "Packages Shipped:                " + nPackages + "\n" +
                "Packages in Warehouse:           " + packageArraySize + "\n" +
                "==============================\n";
        System.out.println(statisticsReport);
    }

    public static void loadVehicle(Drone drone, ArrayList<Package> packages) {
        drone.fill(packages);
    }
}