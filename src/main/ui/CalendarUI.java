package ui;

import model.Calendar;
import model.Year;
import model.Month;

// The CalendarUI manages the UI for navigating and interacting with the calendar, 
// allowing users to view and select years, months, and days.
public class CalendarUI extends UI {

    private Calendar calendar;
    private DayUI dayUI;

    // EFFECTS: initializes the Calendar UI with the given calendar, passing the eventUI to the dayUI
    public CalendarUI(Calendar calendar, EventUI eventUI) {
        this.calendar = calendar;
        dayUI = new DayUI(calendar, eventUI);
    }

    // EFFECTS: starts the Calendar loop which displays the years
    public void startCalendar() {
        boolean isRunning = true;
        int lower = calendar.getLowestYear().getYearNumber();
        int upper = calendar.getHighestYear().getYearNumber();

        while (isRunning) {
            displayCalendarMenu();
            if (handleCalendarChoice(getChoice(lower, upper, "Enter a year (or type 'back' to return): ", true))) {
                isRunning = false;
            }
        }
    }

    // EFFECTS: starts the Year loop which displays the months
    private void startYear(Year year) {
        boolean isRunning = true;

        while (isRunning) {
            displayYearMenu(year);
            if (handleYearChoice(getChoice(1, 12, "Choose a month (or type 'back' to return): ", true), year)) {
                isRunning = false;
            }
        }
    }

    // EFFECTS: starts the Month loop which displays the days
    private void startMonth(Month month) {
        boolean isRunning = true;
        int lower = month.getLowestDay().getDayNumber();
        int upper = month.getHighestDay().getDayNumber();

        while (isRunning) {
            displayMonthMenu(month);
            if (handleMonthChoice(getChoice(lower, upper, "Choose a day (or type 'back' to return): ", true), month)) {
                isRunning = false;
            }
        }
    }

    // EFFECTS: displays the year menu in this calendar
    private void displayCalendarMenu() {
        System.out.println("--- " + calendar.getTitle() + " ---");
        System.out.println("--- Select a Year ---");

        for (Year year: calendar.getYears()) {
            System.out.println(year.getYearNumber());
        }
    }

    // EFFECTS: displays the month menu in the given year
    private void displayYearMenu(Year year) {
        System.out.println("--- Year: " + year.getYearNumber() + " ---");
        System.out.println("--- Select a Month ---");

        for (int i = 1; i <= 12; i++) {
            System.out.println(i + ". " + year.getMonths().get(i - 1).getName());
        }
    }

    // EFFECTS: displays the day menu in the given month
    private void displayMonthMenu(Month month) {
        System.out.println("--- Year: " + month.getYear().getYearNumber() + " | Month: " + month.getName() + " ----");
        System.out.println("--- Select a Day ---");

        for (int i = 1; i <= 28; i++) {
            System.out.printf("%2d  ", i);
            if (i % 7 == 0) { 
                System.out.println();
            }
        }

        for (int i = 29; i <= month.getDays().size(); i++) {
            System.out.printf("%2d  ", i);
        }
        
        System.out.println();
    }

    // REQUIRES: choice corresponds to an integer on the menu or -1
    // EFFECTS: chooses the correct year based on the given choice
    private boolean handleCalendarChoice(int choice) {
        makeWhiteSpace();
        if (choice == -1) {
            return true;
        }
        
        int index = choice - calendar.getLowestYear().getYearNumber();
        startYear(calendar.getYears().get(index));

        return false;
    }

    // REQUIRES: choice corresponds to an integer on the menu or -1
    // EFFECTS: chooses the correct month based on the given choice
    private boolean handleYearChoice(int choice, Year year) {
        makeWhiteSpace();
        if (choice == -1) {
            return true;
        }
        
        int index = choice - 1;
        startMonth(year.getMonths().get(index));

        return false;
    }

    // REQUIRES: choice corresponds to an integer on the menu or -1
    // EFFECTS: chooses the correct day based on the given choice
    private boolean handleMonthChoice(int choice, Month month) {
        makeWhiteSpace();
        if (choice == -1) {
            return true;
        }
        
        int index = choice - 1;
        dayUI.startDay(month.getDays().get(index));

        return false;
    }

}
