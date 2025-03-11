package oldui;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Calendar;
import persistence.JsonReader;
import persistence.JsonWriter;

// The EventUI manages the UI for navigating and interacting with the main menu 
// allowing users to view the other various UI menus and quit the program
public class MainMenuUI extends UI {

    // fields
    private static final String JSON_STORE = "./data/calendar.json";
    private Calendar calendar;
    private EventUI eventUI;
    private CategoryUI categoryUI;
    private CalendarUI calendarUI;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: initializes the main menu with a new eventUI, categoryUI, and calendarUI
    public MainMenuUI(Calendar calendar) {
        this.calendar = calendar;
        this.eventUI = new EventUI(calendar);
        this.categoryUI = new CategoryUI(calendar);
        this.calendarUI = new CalendarUI(calendar, eventUI);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: starts the programs loop
    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            displayMainMenu();
            if (handleMenuChoice(getChoice(1, 6, "Choose an option: ", false))) {
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
        System.out.println("4. Save Calendar");
        System.out.println("5. Load Calendar");
        System.out.println("6. Exit");
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
        } else if (choice == 4) {
            makeWhiteSpace();
            saveCalendar();
            return false;
        } else if (choice == 5) {
            makeWhiteSpace();
            loadCalendar();
            return false;
        } else {
            return true;
        }
    }

    // EFFECTS: saves the calendar to file
    private void saveCalendar() {
        try {
            calendar.setIds();
            jsonWriter.open();
            jsonWriter.write(calendar);
            jsonWriter.close();
            System.out.println("Saved " + calendar.getTitle() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads calendar from file
    private void loadCalendar() {
        try {
            calendar = jsonReader.read();
            eventUI = new EventUI(calendar);
            categoryUI = new CategoryUI(calendar);
            calendarUI = new CalendarUI(calendar, eventUI);
            System.out.println("Loaded " + calendar.getTitle() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
    
}
