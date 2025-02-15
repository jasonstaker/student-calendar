package ui;

import model.Calendar;

import java.util.Scanner;
import java.time.LocalDate;

// initializes the Calendar and begins the UI
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter calendar title: ");

        String calendarTitle = scanner.nextLine();
        Calendar calendar = new Calendar(calendarTitle, LocalDate.now().getYear());

        System.out.println();
        MainMenuUI mainMenuUI = new MainMenuUI(calendar);
        mainMenuUI.start();

        scanner.close();
    }

}
