package app;

import java.util.Scanner;

public class MainView {
    private final Scanner scanner;

    public MainView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        int choice;
        do {
            showMenu();
            System.out.print("Choose: ");
            choice = scanner.nextInt();
            handleChoice(choice);
        } while (choice > 0 && choice <= 4);

        System.out.println("Goodbye!");
    }

    private void showMenu() {
        System.out.println();
        System.out.println("STADIUM TICKET BOOKING SIMULATION");
        System.out.println("1. Browse As Guest");
        System.out.println("2. Login As Fan");
        System.out.println("3. Login As Staff");
        System.out.println("4. Register Fan Account");
        System.out.println("0. Exit");
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Guest feature is not implemented yet.");
                break;
            case 2:
                System.out.println("Fan login is not implemented yet.");
                break;
            case 3:
                System.out.println("Staff login is not implemented yet.");
                break;
            case 4:
                System.out.println("Fan registration is not implemented yet.");
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
