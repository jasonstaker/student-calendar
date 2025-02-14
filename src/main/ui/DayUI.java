package ui;

import model.Calendar;
import model.Day;
import model.Month;
import model.Year;
import model.Event;
import model.Time;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

//
public class DayUI extends UI {

    private Calendar calendar;
    private Scanner scanner;

    // TODO
    public DayUI(Calendar calendar) {
        this.calendar = calendar;
        this.scanner = new Scanner(System.in);
    }

    // MODIFIES: this, day
    // EFFECTS: starts the Day loop which displays the day menu
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
                if (handleDayChoice(getBackChoice(), day)) {
                    isRunning = false;
                }

            }

        }
    }

    // EFFECTS: displays the day menu for the given day
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
    // EFFECTS: chooses what to display based on the given choice
    private boolean handleDayChoice(int choice, Day day) {
        makeWhiteSpace();
        if (choice == -1) {
            return true;
        }
        
        startDay(day);

        return true;
    }

    // EFFECTS: TODO:
    private int getBackChoice() {
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("back")) {
                return -1;
            }
            System.out.print("Invalid input. Type 'back' to return: ");
        }
    }

}
