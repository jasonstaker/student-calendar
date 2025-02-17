package ui;

import model.Calendar;

// The EventUI manages the UI for navigating and interacting with the main menu 
// allowing users to view the other various UI menus and quit the program
public class MainMenuUI extends UI {

    // fields
    private EventUI eventUI;
    private CategoryUI categoryUI;
    private CalendarUI calendarUI;

    // EFFECTS: initializes the main menu with a new eventUI, categoryUI, and calendarUI
    public MainMenuUI(Calendar calendar) {
        this.eventUI = new EventUI(calendar);
        this.categoryUI = new CategoryUI(calendar);
        this.calendarUI = new CalendarUI(calendar, eventUI);
    }

    // EFFECTS: starts the programs loop
    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            displayMainMenu();
            if (handleMenuChoice(getChoice(1, 4, "Choose an option: ", false))) {
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
        System.out.println("4. Exit");
    }

    // REQUIRES: 1 <= choice <= 4
    // MODIFIES: this, calendarUI, eventUI, categoryUI
    // EFFECTS: chooses the appropiate UI to enter based on the given choice, if true exit program
    private boolean handleMenuChoice(int choice) {
        if (choice == 1) {
            makeWhiteSpace();
            calendarUI.startCalendar();
            return false;
        } else if (choice == 2) {
            makeWhiteSpace();
            eventUI.startEventMenu();
            return false;
        } else if (choice == 3) {
            makeWhiteSpace();
            categoryUI.startCategoryMenu();
            return false;
        } else {
            return true;
        }
    }
    
}
