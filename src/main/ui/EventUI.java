package ui;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import model.*;

// the EventUI handles interactions for the Events 
public class EventUI extends UI {

    private Calendar calendar;
    private Scanner scanner;
    private List<Event> events;
    private List<Category> categories;
    private List<Subcategory> subcategories;

    // EFFECTS: Initializes this EventUI with the given Calendar
    public EventUI(Calendar calendar) {
        this.calendar = calendar;
        scanner = new Scanner(System.in);

        events = new ArrayList<Event>();
        categories = new ArrayList<Category>();
        subcategories = new ArrayList<Subcategory>();

        for (Year year: calendar.getYears()) {
            for (Month month: year.getMonths()) {
                for (Day day: month.getDays()) {
                    events.addAll(day.getEvents());
                }
            }
        }

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public void startEventMenu() {
        boolean isRunning = true;
        String prompt = "Enter the corresponding number or type 'back' to return: ";

        while (isRunning) {
            displayEventMenu();

            if (handleEventMenuChoice(getChoice(1, 3, prompt, true))) {
                isRunning = false;
            }

        }
    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
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

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    private void startViewEventMenu(Event event) {
        boolean isRunning = true;

        while (isRunning) {
            displayViewEventMenu(event);

            if (handleViewEventsMenuChoice(getBackChoice(scanner))) {
                isRunning = false;
            }

        }
    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    private void startAddEventProcess() {
        String name = addEventName();
        Category category = addEventCategory();
        Subcategory subcategory = addEventSubcategory();
        List<Day> recurringDays = addEventRecurringDays();
        Time startTime = addEventStartTime();
        Time endTime = addEventEndTime(startTime);

        Event event = new Event(category, subcategory, startTime, endTime, name, recurringDays);

        for (Day day: event.getRecurringDays()) {
            events.add(event);
        }
        makeWhiteSpace();
    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
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

    // EFFECTS: 
    private void displayEventMenu() {
        System.out.println("--- Manage Events ---");
        System.out.println("1. View Events");
        System.out.println("2. Add Event");
        System.out.println("3. Remove Event");
    }

    // EFFECTS: 
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
                }
            }
            System.out.println();
        } else {
            System.out.println("No events scheduled for this day.\n");
            System.out.print("Type 'back' to return: ");
        }
    }

    // EFFECTS: 
    private void displayViewEventMenu(Event event) {
        System.out.println("--- Event Details ---");
        System.out.println("Name: " + event.getName());
        if (event.getSubcategory() == null) {
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
            System.out.println(day.getMonth().getMonthNumber() + "/" + day.getDayNumber());
        }

        System.out.print("\nType 'back' to return: ");
    }

    // EFFECTS: 
    private String addEventName() {
        System.out.print("Enter event name: ");
        
        return scanner.nextLine();
    }

    // EFFECTS: 
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

    // EFFECTS: 
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

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
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

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
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

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
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

    // EFFECTS: 
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

    // REQUIRES:
    // MODIFIES: 
    // EFFECTS: 
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

    // REQUIRES:
    // MODIFIES: 
    // EFFECTS: 
    private boolean handleViewEventsMenuChoice(int choice) {
        makeWhiteSpace();

        if (choice == -1) {
            return true;
        }

        startViewEventMenu(events.get(choice - 1));
        return false;
    }

    // REQUIRES: 
    // MODFIIES: 
    // EFFECTS: 
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
                category = categories.get(choice - 1);
            }

        }

        return category;
    }

    // REQUIRES: 
    // MODFIIES: 
    // EFFECTS: 
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
                subcategory = subcategories.get(choice - 1);
            }

        }

        return subcategory;
    }

    private boolean handleRemoveEventMenuChoice(int choice) {
        makeWhiteSpace();

        if (choice == -1) {
            return true;
        }

        for(Day day: events.get(choice - 1).getRecurringDays()) {
            day.removeEvent(events.get(choice - 1));
        }

        events.remove(choice - 1);

        return false;
    }

}
