package app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainView mainMenuView = new MainView(scanner);
        mainMenuView.start();
        scanner.close();
    }
}
