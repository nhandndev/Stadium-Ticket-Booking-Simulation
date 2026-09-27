package app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainMenuView mainMenuView = new MainMenuView(scanner);
        mainMenuView.start();
        scanner.close();
    }
}
