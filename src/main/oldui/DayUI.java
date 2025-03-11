package oldui;

import model.Calendar;
import model.Day;
import model.Month;
import model.Year;
import model.Event;

import java.util.Scanner;
import java.util.List;

// The DayUI manages the UI for navigating and interacting with days, 
// allowing users to select and view events within days.
public class DayUI extends UI {

    // fields
    private Calendar calendar;
    private Scanner scanner;
    private EventUI eventUI;

    // initializes this DayUI with the given calendar, eventUI, and a new scanner
    // note: eventUI is to display the events in the days
    public DayUI(Calendar calendar, EventUI eventUI) {
        this.calendar = calendar;
        this.scanner = new Scanner(System.in);
        this.eventUI = eventUI;
    }

    // REQUIRES: day is in calendar
    // EFFECTS: starts the day loop which displays the day menu and it's events
    public void startDay(Day day) {
        boolean isRunning = true;
        String prompt = "Type the event number to view details (or type 'back' to return): ";

        while (isRunning) {
            displayDayMenu(day);

            if (!day.getEvents().isEmpty()) {
                int lower = 1;
                int upper = day.getEvents().size();
    
                if (handleDayChoice(getChoice(lower, upper, prompt, true), day)) {
                    isRunning = false;
                }
            } else {
                if (handleDayChoice(getBackChoice(scanner), day)) {
                    isRunning = false;
                }

            }

        }
    }

    // EFFECTS: displays the day menu for the given day, if none, displays a message indicating so
    private void displayDayMenu(Day day) {
        Year year = day.getYear();
        Month month = day.getMonth();

        System.out.println("------------------------------");
        System.out.print(calendar.getTitle() + " - ");
        System.out.print(month.getName() + " " + day.getDayNumber() + ", " + year.getYearNumber() + "\n");
        System.out.println("------------------------------\n");

        if (!day.getEvents().isEmpty()) {
            List<Event> events = day.getEvents();

            System.out.println("Events on this day:");
            for (int i = 1; i <= day.getEvents().size(); i++) {
                System.out.print(i + ". " + events.get(i - 1).getStartTime().timeToString() + " - ");
                System.out.println(events.get(i - 1).getName());
            }
            System.out.println();
        } else {
            System.out.println("No events scheduled for this day.\n\n");
            System.out.print("Type 'back' to return: ");
        }
    }

    // REQUIRES: choice corresponds to an integer on the menu or -1
    // EFFECTS: displays the event details corresponding to the given choice, exits if -1 (produces true)
    private boolean handleDayChoice(int choice, Day day) {
        makeWhiteSpace();
        if (choice == -1) {
            return true;
        }

        eventUI.startViewEventMenu(day.getEvents().get(choice - 1));

        return false;
    }

}
