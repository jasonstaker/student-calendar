package ui;

import model.Calendar;

import java.util.Scanner;

// the MainMenuUI handles main menu interactions
public class MainMenuUI {

    private Calendar calendar;
    private Scanner scanner;
    private EventUI eventUI;
    private CategoryUI categoryUI;
    private NotificationUI notificationUI;
    private CalendarUI calendarUI;

    // EFFECTS: initializes the main menu with the given Calendar
    public MainMenuUI(Calendar calendar) {
        this.calendar = calendar;
        this.scanner = new Scanner(System.in);
        this.eventUi = new EventUI(calendar, scanner);
        this.categoryUI = new CategoryUI(calendar, scanner);
        this.notificaitonUI = new NotificationUI(calendar, scanner);
        this.calendarUI = new CalendarUI(calendar, scanner);
    }

    // MODIFIES: this
    // EFFECTS: starts the programs loop
    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            displayMainMenu();
            if (handleMenuChoice(getChoice())) {
                isRunning = false;
            }
        }

        System.out.print("\n\nSee you again!");  
    }

    // EFFECTS: displays the main menu
    private void displayMainMenu() {
        System.out.println("--- Calendar Main Menu ---");
        System.out.println("1. View Calendar");
        System.out.println("2. Manage Events");
        System.out.println("3. Manage Categories");
        System.out.println("4. View Notifications");
        System.out.println("5. Exit");
    }

    // REQUIRES: 1 <= choice <= 5
    // MODIFIES: this
    // EFFECTS: chooses the appropiate UI to enter based on the given choice, if true exit program
    private boolean handleMenuChoice(int choice) {
        if (choice == 1) {
            makeWhiteSpace()
            calendarUI.start();
            return false;
        } else if (choice == 2) {
            makeWhiteSpace()
            eventUI.start();
            return false;
        } else if (choice == 3) {
            makeWhiteSpace()
            categoryUI.start();
            return false;
        } else if (choice == 4) {
            makeWhiteSpace()
            notificationUI.start();
            return false;
        } else {
            return true;
        }
    }

    // EFFECTS: prints out a basic space between UI's
    private void makeWhiteSpace() {
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println();
        System.out.println();
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    // EFFECTS: gets a valid choice from the user, if invalid it prompts and loops
    private int getChoice() {
        System.out.print("Choose an option: ");
    
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Choose an option: ");
            scanner.next();
        }
    
        int input = scanner.nextInt();
    
        while (input < 1 || input > 5) {
            System.out.print("Please enter a number between 1 and 5: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number between 1 and 5: ");
                scanner.next();
            }
            input = scanner.nextInt();
        }

        return input;
    }
    
}
