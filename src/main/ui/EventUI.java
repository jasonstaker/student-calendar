package ui;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import model.*;

// The EventUI manages the UI for navigating and interacting with events, 
// allowing users to view, add, and remove events.
public class EventUI extends UI {

    // fields
    private Calendar calendar;
    private Scanner scanner;
    private List<Event> events;
    private List<Category> categories;
    private List<Subcategory> subcategories;

    // EFFECTS: Initializes this EventUI with the given Calendar, and adds all the years events to events
    public EventUI(Calendar calendar) {
        this.calendar = calendar;
        scanner = new Scanner(System.in);

        events = new ArrayList<Event>();
        categories = calendar.getCategories();
        subcategories = calendar.getSubcategories();

        for (Year year: calendar.getYears()) {
            for (Month month: year.getMonths()) {
                for (Day day: month.getDays()) {
                    events.addAll(day.getEvents());
                }
            }
        }

    }

    // EFFECTS: starts the manage event loop which displays the manage event menu
    protected void startEventMenu() {
        boolean isRunning = true;
        String prompt = "Enter the corresponding number or type 'back' to return: ";

        while (isRunning) {
            displayEventMenu();

            if (handleEventMenuChoice(getChoice(1, 3, prompt, true))) {
                isRunning = false;
            }

        }
    }

    // EFFECTS: starts the view events loop which displays the view events menu
    private void startViewEventsMenu() {
        boolean isRunning = true;
        String prompt = "Enter the corresponding number to view details or type 'back' to return: ";

        while (isRunning) {
            displayViewEventsMenu();
            int lower = 1;
            int upper = events.size();

            if (!events.isEmpty()) {
                if (handleViewEventsMenuChoice(getChoice(lower, upper, prompt, true))) {
                    isRunning = false;
                }
            } else {
                if (handleViewEventsMenuChoice(getBackChoice(scanner))) {
                    isRunning = false;
                }

            }

        }
    }

    // EFFECTS: starts the view event loop which displays the view event menu for a single given event
    public void startViewEventMenu(Event event) {
        boolean isRunning = true;

        while (isRunning) {
            displayViewEventMenu(event);

            if (handleViewEventsMenuChoice(getBackChoice(scanner))) {
                isRunning = false;
            }

        }
    }

    // MODIFIES: this, event
    // EFFECTS: starts the add event process which makes an event object based on user input
    private void startAddEventProcess() {
        String name = addEventName();
        Category category = addEventCategory();
        Subcategory subcategory = addEventSubcategory();
        List<Day> recurringDays = addEventRecurringDays();
        Time startTime = addEventStartTime();
        Time endTime = addEventEndTime(startTime);

        Event event = new Event(category, subcategory, startTime, endTime, name, recurringDays);

        for (int i = 0; i < event.getRecurringDays().size(); i++) {
            events.add(event);
        }
        makeWhiteSpace();
    }

    // EFFECTS: starts the remove event loop which displays the remove events menu
    private void startRemoveEventMenu() {
        boolean isRunning = true;
        String prompt = "Enter the corresponding number to remove or type 'back' to return: ";

        while (isRunning) {
            displayRemoveEventMenu();
            int lower = 1;
            int upper = events.size();

            if (!events.isEmpty()) {
                if (handleRemoveEventMenuChoice(getChoice(lower, upper, prompt, true))) {
                    isRunning = false;
                }
            } else {
                if (handleRemoveEventMenuChoice(getBackChoice(scanner))) {
                    isRunning = false;
                }

            }

        }
    }

    // EFFECTS: displays the manage events menu
    private void displayEventMenu() {
        System.out.println("--- Manage Events ---");
        System.out.println("1. View Events");
        System.out.println("2. Add Event");
        System.out.println("3. Remove Event");
    }

    // EFFECTS: displays the view events menu
    private void displayViewEventsMenu() {
        List<Event> hasAppeared = new ArrayList<Event>();

        System.out.println("--- View Events ---\n");

        if (!events.isEmpty()) {
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                Day day = event.getRecurringDays().get(Collections.frequency(hasAppeared, event));

                System.out.print((i + 1) + ". " + day.getYear().getYearNumber() + "/");
                System.out.print((day.getMonth().getMonthNumber() + 1) + "/" + day.getDayNumber());
                if (event.getCategory() == null) {
                    System.out.print(" - " + event.getName() + " (None)\n");
                } else {
                    System.out.print(" - " + event.getName() + " (" + event.getCategory().getName() + ")\n");
                    hasAppeared.add(event);
                }
            }
            System.out.println();
        } else {
            System.out.println("No events scheduled.\n");
            System.out.print("Type 'back' to return: ");
        }
    }

    // EFFECTS: displays the view event menu
    private void displayViewEventMenu(Event event) {
        System.out.println("--- Event Details ---");
        System.out.println("Name: " + event.getName());
        if (event.getCategory() == null) {
            System.out.println("Category: None");
        } else {
            System.out.println("Category: " + event.getCategory().getName());
        }
        if (event.getSubcategory() == null) {
            System.out.println("Subcategory: None");
        } else {
            System.out.println("Subcategory: " + event.getSubcategory().getName());
        }
        System.out.println("Start Time: " + event.getStartTime().timeToString());
        System.out.println("End Time: " + event.getEndTime().timeToString());
        System.out.println("Occurs On:");

        for (Day day: event.getRecurringDays()) {
            System.out.print("  - " + day.getYear().getYearNumber() + "/");
            System.out.println((day.getMonth().getMonthNumber() + 1) + "/" + day.getDayNumber());
        }

        System.out.print("\nType 'back' to return: ");
    }

    // EFFECTS: prompts the user to get the name for an event
    private String addEventName() {
        System.out.print("Enter event name: ");
        
        return scanner.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: displays a category selection menu and returns the selected category, or null if 'none' is chosen.
    private Category addEventCategory() {
        Category category = null;
        System.out.println("\n--- Choose a Category ---");

        for (Event event: events) {
            if (!categories.contains(event.getCategory()) && event.getCategory() != null) {
                categories.add(event.getCategory());
            }
        }

        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }

        System.out.println((categories.size() + 1) + ". None");

        category = handleCategoryInput();

        return category;
    }

    // MODIFIES: this
    // EFFECTS: displays a subcategory selection menu and returns the selected subcategory, or null if 'none' is chosen.
    private Subcategory addEventSubcategory() {
        Subcategory subcategory = null;
        System.out.println("\n--- Choose a Subcategory ---");

        for (Event event: events) {
            if (!subcategories.contains(event.getSubcategory())) {
                subcategories.add(event.getSubcategory());
            }
        }

        for (int i = 0; i < subcategories.size(); i++) {
            System.out.println((i + 1) + ". " + subcategories.get(i).getName());
        }

        System.out.println((subcategories.size() + 1) + ". None");

        subcategory = handleSubcategoryInput();

        return subcategory;
    }

    // EFFECTS: prompts the user to add recurring days and returns a list of valid Day objects.
    private List<Day> addEventRecurringDays() {
        List<Day> recurringDays = new ArrayList<Day>();

        System.out.println("\n--- Choose Recurring Day(s) ---");
        System.out.println("Type 'done' when you are finished entering dates.");

        while (true) {
            System.out.print("Enter a date in YYYY/MM/DD format: ");
            String input = scanner.nextLine();
            
            if (input.equals("done") && recurringDays.size() != 0) {
                break;
            }

            if (calendar.isInCalendar(input)) {
                Day day = calendar.dateToDay(input);
                recurringDays.add(day);
                System.out.print("Date added: " + day.getMonth().getName() + " " + day.getDayNumber());
                System.out.println(", " + day.getYear().getYearNumber() + "\n");
            } else {
                System.out.print("Invalid input. ");
            }

        }

        return recurringDays;
    }

    // EFFECTS: prompts the user to get the start time for an event, returning the start time
    private Time addEventStartTime() {
        Time startTime = new Time(0, 0);
        System.out.println();

        while (true) {
            System.out.print("Enter event start time (HH:MM): ");
            String input = scanner.nextLine();

            if (startTime.isValidTime(input)) {
                startTime = startTime.stringToTime(input);
                break;
            } else {
                System.out.print("Invalid input. ");
            }

        }

        return startTime;
    }

    // EFFECTS: prompts the user to get the end time for an event, returning the end time
    private Time addEventEndTime(Time startTime) {
        Time endTime = new Time(0, 0);

        while (true) {
            System.out.print("Enter event end time (HH:MM): ");
            String input = scanner.nextLine();

            if (endTime.isValidTime(input) && endTime.stringToTime(input).isAfter(startTime)) {
                endTime = endTime.stringToTime(input);
                break;
            } else {
                System.out.print("Invalid input. ");
            }

        }

        return endTime;
    }

    // EFFECTS: displays the remove event menu
    private void displayRemoveEventMenu() {
        List<Event> hasAppeared = new ArrayList<Event>();

        System.out.println("--- Remove Events ---\n");

        if (!events.isEmpty()) {
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                Day day = event.getRecurringDays().get(Collections.frequency(hasAppeared, event));

                System.out.print((i + 1) + ". " + day.getYear().getYearNumber() + "/");
                System.out.print((day.getMonth().getMonthNumber() + 1) + "/" + day.getDayNumber());
                if (event.getCategory() == null) {
                    System.out.print(" - " + event.getName() + " (None)\n");
                } else {
                    System.out.print(" - " + event.getName() + " (" + event.getCategory().getName() + ")\n");
                }
            }
            System.out.println();
        } else {
            System.out.println("No events scheduled for this day.\n");
            System.out.print("Type 'back' to return: ");
        }
    }

    // REQUIRES: choice is a valid integer in the event menu or -1
    // EFFECTS: processes the user's choice from the event menu:
    // 1. Starts the event viewing process if choice is 1
    // 2. Starts the event adding process if choice is 2
    // 3. Starts the event removal process if choice is 3
    // 4. Exits the menu and returns true if choice is -1
    private boolean handleEventMenuChoice(int choice) {
        makeWhiteSpace();

        if (choice == -1) {
            return true;
        } else if (choice == 1) {
            startViewEventsMenu();
            return false;
        } else if (choice == 2) {
            startAddEventProcess();
            return false;
        } else if (choice == 3) {
            startRemoveEventMenu();
            return false;
        }

        return false;
    }

    // REQUIRES: choice is a valid integer corresponding to a menu or -1
    // EFFECTS: processes the user's choice from the view events menu:
    // 1. Starts the event viewing process for the selected event if choice is valid
    // -1 exits the menu and returns true
    private boolean handleViewEventsMenuChoice(int choice) {
        makeWhiteSpace();

        if (choice == -1) {
            return true;
        }

        startViewEventMenu(events.get(choice - 1));
        return false;
    }

    // EFFECTS: prompts the user to select a category from the list of categories:
    // 1. Returns the selected category if valid
    // 2. Returns null if the user chooses "None" (option size + 1)
    private Category handleCategoryInput() {
        Category category = null;
        boolean isRunning = true;
        String prompt = "Select a category number: ";

        while (isRunning) {
            int lower = 1;
            int upper = categories.size() + 1;
            int choice = getChoice(lower, upper, prompt, false);

            if (choice == (categories.size() + 1)) {
                isRunning = false;
                category = null;
            } else {
                isRunning = false;
                category = categories.get(choice - 1);
            }

        }

        return category;
    }

    // EFFECTS: prompts the user to select a subcategory from the list of subcategories:
    // 1. Returns the selected subcategory if valid
    // 2. Returns null if the user chooses "None" (option size + 1)
    private Subcategory handleSubcategoryInput() {
        Subcategory subcategory = null;
        boolean isRunning = true;
        String prompt = "Select a subcategory number: ";

        while (isRunning) {
            int lower = 1;
            int upper = subcategories.size() + 1;
            int choice = getChoice(lower, upper, prompt, false);

            if (choice == (subcategories.size() + 1)) {
                isRunning = false;
                subcategory = null;
            } else {
                isRunning = false;
                subcategory = subcategories.get(choice - 1);
            }

        }

        return subcategory;
    }

    // REQUIRES: choice is a valid integer corresponding to an event in the events list, or -1
    // MODIFIES: this, events, Day
    // EFFECTS: handles the user's choice from the remove event menu:
    // 1. Removes the event from its recurring days in the calendar
    // 2. Removes the event from the events list
    // 3. Exits the menu and returns true if choice is -1
    private boolean handleRemoveEventMenuChoice(int choice) {
        makeWhiteSpace();

        if (choice == -1) {
            return true;
        }

        for (Day day: events.get(choice - 1).getRecurringDays()) {
            day.removeEvent(events.get(choice - 1));
        }

        events.remove(choice - 1);

        return false;
    }

}
