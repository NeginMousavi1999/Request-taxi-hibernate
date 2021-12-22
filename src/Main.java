import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Negin Mousavi
 */
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        TaxiManager taxiManager = new TaxiManager();
        welcome();
        int choice;

        choices:
        do {
            System.out.print("choose from below:\n" +
                    "1.Add a group of drivers  \n" +
                    "2.Add a group of passengers  \n" +
                    "3.Driver signup or login  \n" +
                    "4.Passenger signup or login  \n" +
                    "5.Show ongoing travels  \n" +
                    "6.Show a list of drivers \n" +
                    "7.Show a list of passengers\n" +
                    "8.exit\n" +
                    "your choice is: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    taxiManager.createDriver(1);
                    printStar();
                    break;

                case 2:
                    taxiManager.createPassenger(2);
                    printStar();
                    break;

                case 3:
                    taxiManager.signupOrLogin(3);
                    printStar();
                    break;

                case 4:
                    taxiManager.signupOrLogin(4);
                    printStar();
                    break;

                case 5:
                    taxiManager.showAllOngoingTravels();
                    printStar();
                    break;

                case 6:
                    taxiManager.showAllDrivers();
                    printStar();
                    break;

                case 7:
                    taxiManager.showAllPassengers();
                    printStar();
                    break;

                case 8:
                    printStar();
                    break choices;

                default:
                    printInvalidInput();
                    printStar();
            }

        } while (true);
    }

    public static void printInvalidInput() {
        System.out.println("invalid input");
    }

    public static void printStar() {
        System.out.println("**********************************************************");
    }

    public static void welcome() {
        System.out.println("  _______         _                                          \n" +
                " |__   __|       (_)        /\\                               \n" +
                "    | | __ ___  ___        /  \\   __ _  ___ _ __   ___ _   _ \n" +
                "    | |/ _` \\ \\/ / |      / /\\ \\ / _` |/ _ \\ '_ \\ / __| | | |\n" +
                "    | | (_| |>  <| |     / ____ \\ (_| |  __/ | | | (__| |_| |\n" +
                "    |_|\\__,_/_/\\_\\_|    /_/    \\_\\__, |\\___|_| |_|\\___|\\__, |\n" +
                "                                  __/ |                 __/ |\n" +
                "                                 |___/                 |___/ ");
    }
}
