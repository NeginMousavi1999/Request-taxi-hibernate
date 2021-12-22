import dao.DriverDao;
import dao.PassengerDao;
import dao.TripDao;
import dao.VehicleDao;
import enumerations.*;
import exceptions.UserInputValidation;
import models.members.Driver;
import models.members.Passenger;
import models.trip.Trip;
import models.vehicles.Vehicle;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Negin Mousavi
 */
public class TaxiManager {
    Scanner scanner = new Scanner(System.in);
    PassengerDao passengerDao = new PassengerDao();
    DriverDao driverDao = new DriverDao();
    VehicleDao vehicleDao = new VehicleDao();
    TripDao tripDao = new TripDao();
    Gender gender;
    PaymentMethod paymentMethod;
    String origin, destination;
    private String fName, lName, personalId, phoneNum;
    private int birthYear;

    public static void handleExceptionForMobileNumFormat(String input) {
        String regex = "09[0-9]{9}";
        if (!Pattern.matches(regex, input))
            throw new UserInputValidation("some thing is not correct about this phone number");
    }

    public static void handleExceptionForPlaqueFormat(String input) {
        String regex = "[0-9]{2}[a-z][0-9]{2}";
        if (!Pattern.matches(regex, input))
            throw new UserInputValidation("the format of plaque must be like: 99x99");
    }

    public void createDriver(int caseNum) throws InterruptedException {
        int count = 0;
        int addedSuc = 0;
        if (caseNum == 3)
            count = 1;
        else if (caseNum == 1) {
            System.out.print("Enter count of drivers you wanna add: ");
            count = scanner.nextInt();
        }
        Driver[] drivers = new Driver[count];

        for (int i = 0; i < count; i++) {
            System.out.println("** information for new driver **");
            getCommonInformationInputs();
            if (driverDao.isObjectFound(personalId)) {
                System.out.println("you can't register, there is a driver with this personal id!");
                return;
            }
            scanner.nextLine();

            String location;
            do {
                try {
                    System.out.print("enter your location(x,y): ");
                    location = scanner.nextLine();
                    if (location.split(",").length != 2)
                        throw new ArrayIndexOutOfBoundsException();
                    break;
                } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                    Main.printInvalidInput();
                }
            } while (true);

            Vehicle vehicle;
            String vehiclePlaque;
            while (true) {
                try {
                    System.out.print("vehicle plaque: ");
                    vehiclePlaque = scanner.nextLine();
                    handleExceptionForPlaqueFormat(vehiclePlaque);
                    break;
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                    Thread.sleep(1000);
                }
            }

            TypeOfVehicle typeOfVehicle;
            if (isVehiclePlaqueExists(vehiclePlaque)) {
                System.out.println("you can choose this vehicle plaque because it is exits already...");
                return;
            } else {
                do {
                    typeOfVehicle = chooseTypeOfVehicle();
                } while (typeOfVehicle == null);
                scanner.nextLine();
                System.out.print("vehicle name: ");
                String name = scanner.nextLine();
                System.out.print("vehicle color: ");
                String color = scanner.nextLine();
                vehicle = createVehicleAndReturn(name, color, vehiclePlaque, typeOfVehicle);
            }
            drivers[i] = new Driver(personalId, fName, lName, gender, phoneNum, birthYear, typeOfVehicle, vehicle, location);
            addedSuc += driverDao.addNewDriver(drivers[i]);
            drivers[i].setId(driverDao.getId(personalId));
            System.out.println("id: " + drivers[i].getId());
        }
        if (addedSuc == count)
            System.out.println(count + " new driver(s) were added successfully");
        else
            System.out.println("some thing were wrong...");
    }

    public TypeOfVehicle chooseTypeOfVehicle() {
        TypeOfVehicle typeOfVehicle = null;
        System.out.print("which vehicle you have? (1.car 2.van 3.motorcycle) : ");
        int chosenTypeOfVehicle = scanner.nextInt();
        switch (chosenTypeOfVehicle) {
            case 1:
                typeOfVehicle = TypeOfVehicle.CAR;
                break;

            case 2:
                typeOfVehicle = TypeOfVehicle.VAN;
                break;

            case 3:
                typeOfVehicle = TypeOfVehicle.MOTORCYCLE;
                break;

            default:
                Main.printInvalidInput();
        }
        return typeOfVehicle;
    }

    private Vehicle createVehicleAndReturn(String name, String color, String plaque, TypeOfVehicle typeOfVehicle) {
        Vehicle vehicle = new Vehicle(name, color, plaque, typeOfVehicle);
        vehicleDao.addNewVehicle(vehicle);
        vehicle.setId(vehicleDao.getId(plaque));
        return vehicle;
    }

    private boolean isVehiclePlaqueExists(String vehiclePlaque) {
        return vehicleDao.isObjectFound(vehiclePlaque);
    }

    public void createPassenger(int caseNum) throws InterruptedException {
        int count = 0;
        int addedSuc = 0;
        if (caseNum == 4) {
            count = 1;
        } else if (caseNum == 2) {
            System.out.print("Enter count of passengers you wanna add: ");
            count = scanner.nextInt();
        }
        Passenger[] passengers = new Passenger[count];

        for (int i = 0; i < count; i++) {
            System.out.println("** information for new passenger **");
            getCommonInformationInputs();
            if (passengerDao.isObjectFound(personalId)) {
                System.out.println("you can't register, there is a driver with this personal id!");
                return;
            }
            scanner.nextLine();
            System.out.print("account balance: ");
            double accountBalance = scanner.nextDouble();
            passengers[i] = new Passenger(personalId, fName, lName, gender, phoneNum, birthYear, accountBalance);
            addedSuc += passengerDao.addNewPassenger(passengers[i]);
            passengers[i].setId(passengerDao.getId(personalId));
            System.out.println("id: " + passengers[i].getId());
        }
        if (addedSuc == count)
            System.out.println(count + " new passenger(s) were added successfully");
        else
            System.out.println("some thing were wrong...");
    }

    public void getCommonInformationInputs() throws InterruptedException {
        scanner.nextLine();
        System.out.print("first name: ");
        fName = scanner.nextLine();

        System.out.print("last name: ");
        lName = scanner.nextLine();

        System.out.print("personal id: ");
        personalId = scanner.nextLine();

        while (true) {
            try {
                System.out.print("gender(female/male): ");
                gender = Gender.valueOf(scanner.nextLine().toUpperCase());
                break;
            } catch (UserInputValidation | NullPointerException | IllegalArgumentException e) {
                System.out.println("you must enter female or male...");
                Thread.sleep(1000);
            }
        }

        while (true) {
            try {
                System.out.print("phone number: ");
                phoneNum = scanner.nextLine();
                handleExceptionForMobileNumFormat(phoneNum);
                break;
            } catch (UserInputValidation e) {
                System.out.println(e.getLocalizedMessage());
                Thread.sleep(1000);
            }
        }

        while (true) {
            System.out.print("birth year: ");
            if (scanner.hasNextInt()) {
                birthYear = scanner.nextInt();
                break;
            } else {
                scanner.nextLine();
                System.out.println("Enter a valid Integer value");
                Thread.sleep(1000);
            }
        }
    }

    public void showAllPassengers() {
        List<Passenger> passengers = passengerDao.showAllPassengers();
        passengers.forEach(System.out::println);
    }

    public void showAllDrivers() {
        List<Driver> drivers = driverDao.showAllDrivers();
        drivers.forEach(System.out::println);
    }

    public void signupOrLogin(int caseNum) throws InterruptedException {
        System.out.print("enter your Personal Id: ");
        String inputPersonalId = scanner.nextLine();
        if (caseNum == 3) {
            Driver driver = driverDao.returnDriverByPersonalCode(inputPersonalId);
            if (driver == null)
                registerOrExit("d");
            else {
                System.out.println("you are authenticated ");
                String status = driver.getUserStatus().toString();
                if (status.equals(UserStatus.WAITING.toString()))
                    System.out.println("and waiting for the trip");
                else if (status.equals(UserStatus.NO_REQUEST.toString())) {
                    showOptionsForDriverWithNoRequest(driver);
                    scanner.nextLine();

                } else if (status.equals(UserStatus.ON_TRIP.toString())) {
                    showOptionsForDriverWhileTraveling(driver);
                }
            }

        } else if (caseNum == 4) {
            Passenger passenger = passengerDao.returnPassengerByPersonalCode(inputPersonalId);
            if (passenger == null)
                registerOrExit("p");
            else {
                String status = passenger.getUserStatus().toString();
                if (status.equals(UserStatus.NO_REQUEST.toString()))
                    showPassengerLoginMenu(passenger);
                else if (status.equals(UserStatus.WAITING.toString()))
                    System.out.println("and waiting for acceptation");
                else if (status.equals(UserStatus.ON_TRIP.toString())) {
                    System.out.println("you are on a trip");
                }
            }
        }
    }

    public void showAllOngoingTravels() {
        List<Trip> allTrips = tripDao.showAllTrips();
        for (Trip trip : allTrips) {
            if (trip.getTripStatus().equals(TripStatus.ON_TRIP)) {
                Passenger passenger = passengerDao.returnPassengerById(trip.getPassenger().getId());
                Driver driver = driverDao.returnDriverById(trip.getDriver().getId());
                System.out.println("information about trip:");
                System.out.println(passenger.toString());
                System.out.println(driver.toString());
                System.out.println("Origin: " + trip.getOrigin() + "\nDestination" + trip.getDestination());
                Main.printStar();
            }
        }
    }

    public void showOptionsForDriverWithNoRequest(Driver driver) {
        System.out.print("1.accept a trip\n2.Exit\nwhat do you wanna do? : ");
        int chosenOption = scanner.nextInt();
        switch (chosenOption) {
            case 1:
                driver.setUserStatus(UserStatus.WAITING);
                driverDao.update(driver);
                break;
            case 2:
                break;
            default:
                Main.printInvalidInput();
        }
    }

    public void showOptionsForDriverWhileTraveling(Driver driver) {
        boolean confirmCashReceipt = false;
        int chosenOption;
        Trip driverTrip = tripDao.findTripByDriverId(driver.getId());
        PaymentMethod paymentMethod;
        try {
            paymentMethod = driverTrip.getPaymentMethod();
        } catch (NullPointerException nullPointerException) {
            System.out.println("something in your db is wrong! check the driver with name " + driver.getFirstName());
            return;
        }
        do {
            System.out.print("and you are on a trip\n1.Confirm cash receipt\n2.Travel finished\n3.Exit\nwhat do you wanna do? : ");
            chosenOption = scanner.nextInt();
            switch (chosenOption) {
                case 1:
                    if (paymentMethod.equals(PaymentMethod.ACCOUNT_BALANCE))
                        System.out.println("This passenger has paid with account balance! you can finish the trip");
                    else {
                        confirmCashReceipt = true;
                        System.out.println("Thank you for confirmation :) you can finish the trip");
                    }
                    continue;
                case 2:
                    if (paymentMethod.equals(PaymentMethod.CASH) && !confirmCashReceipt) {
                        System.out.println("you must confirm cash receipt, then you can finished the trip");
                        continue;
                    } else {
                        Passenger passenger = passengerDao.returnPassengerById(driverTrip.getPassenger().getId());
                        passenger.setUserStatus(UserStatus.NO_REQUEST);
                        passengerDao.update(passenger);
                        tripDao.updateStatus(driverTrip, TripStatus.FINISHED);
                        driver.setUserStatus(UserStatus.NO_REQUEST);
                        driverDao.update(driver);
                        driverDao.update(driver);
                        System.out.println("your trip has ended... have a good time");

                    }
                    break;

                case 3:
                    break;
                default:
                    Main.printInvalidInput();
            }
        } while (chosenOption != 3);
    }

    public void registerOrExit(String userType) throws InterruptedException {
        System.out.print("1)register\n2)exit\nwhat do you wanna do? : ");
        int answer = scanner.nextInt();
        switch (answer) {
            case 1:
                if (userType.equals("p"))
                    createPassenger(4);
                else
                    createDriver(3);
                break;
            case 2:
                scanner.nextLine();
                break;
            default:
                Main.printInvalidInput();
        }
    }

    public void showPassengerLoginMenu(Passenger passenger) throws InterruptedException {
        while (true) {
            System.out.print("you are authenticate\n1)Travel request (cash payment)\n2.Travel request (payment from account balance)\n" +
                    "3.Increase account balance\n4)Exit\nwhat do you wanna do? : ");
            int answer = scanner.nextInt();
            if (answer == 1 || answer == 2) {
                int cost = setOriginAndDestinationAndReturnCost();
                if (cost == -1)
                    return;
                System.out.println("cost is: " + cost);
                if (answer == 2) {
                    if (cost > passenger.getAccountBalance()) {
                        System.out.print("the cost is more than account balance. do you wanna choose something else?(y or n): ");
                        String showAgainOrCancel = scanner.nextLine();
                        if (!showAgainOrCancel.equals("y"))
                            break;
                        else continue;

                    }
                    paymentMethod = PaymentMethod.ACCOUNT_BALANCE;
                } else
                    paymentMethod = PaymentMethod.CASH;

                Trip trip = new Trip(passenger, origin, destination, cost, paymentMethod);
                System.out.println("wait until driver accept....");
                Thread.sleep(3000);
                Driver accDriver = findNearestDriverByPersonalId();
                trip.setDriver(accDriver);
                System.out.println("your request is accepted by: " + accDriver.getFirstName() + " " + accDriver.getLastName());

                accDriver.setUserStatus(UserStatus.ON_TRIP);
                driverDao.update(accDriver);
                passenger.setUserStatus(UserStatus.ON_TRIP);
                passengerDao.update(passenger);
                tripDao.addNewTrip(trip);
                break;

            } else if (answer == 3) {
                System.out.print("enter amount to increase: ");
                double amount = scanner.nextDouble();
                double newAccountBalance = passenger.increaseAccountBalance(amount);
                passenger.setAccountBalance(newAccountBalance);
                passengerDao.update(passenger);
                break;

            } else if (answer == 4)
                break;

            else
                Main.printInvalidInput();
        }
    }

    public int setOriginAndDestinationAndReturnCost() {
        scanner.nextLine();
        try {
            System.out.print("enter coordinates of origin(split with ',' ): ");
            origin = scanner.nextLine();
            System.out.print("enter coordinates of destination(split with ',' ): ");
            destination = scanner.nextLine();
            return calculateTheDistanceBetweenTwoPoints(origin, destination) * 1000;
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            Main.printInvalidInput();
            return -1;
        }
    }

    public int calculateTheDistanceBetweenTwoPoints(String a, String b) {
        int space;
        int xO = Integer.parseInt(a.split(",")[0]);
        int yO = Integer.parseInt(a.split(",")[1]);
        int xD = Integer.parseInt(b.split(",")[0]);
        int yD = Integer.parseInt(b.split(",")[1]);
        int c = xD - xO;
        int d = yD - yO;
        space = (int) Math.sqrt(Math.pow(c, 2) + Math.pow(d, 2));
        return space;
    }

    public Driver findNearestDriverByPersonalId() {
        List<String> locations = driverDao.findLocationOfWaitingStatus();
        int min = Integer.MAX_VALUE;
        String dLocation = null;
        for (String location : locations) {
            int space = calculateTheDistanceBetweenTwoPoints(location, origin);
            if (space < min) {
                min = space;
                dLocation = location;
            }
        }
        return driverDao.returnDriverByLocation(dLocation);
    }
}