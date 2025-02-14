package ui;

import model.Calendar;
import model.Year;
import model.Month;
import model.Day;

import java.util.Scanner;

// the CalendarUI handles Calendar interactions
public class CalendarUI extends UI {

    // EFFECTS: initializes the Calendar UI with the given calendar
    public CalendarUI(Calendar calendar, Scanner scanner) {
        
    }

    // MODIFIES: this
    // EFFECTS: starts the Calendar loop which displays the years
    public void startCalendar() {

    }

    // MODIFIES: this, year
    // EFFECTS: starts the Year loop which displays the months
    private void startYear(Year year) {

    }

    // MODIFIES: this, month
    // EFFECTS: starts the Month loop which displays the days
    private void startMonth(Month month) {

    }

    // MODIFIES: this, day
    // EFFECTS: starts the Day loop which displays the day menu
    private void startDay(Day day) {

    }

    // EFFECTS: dispays the years in this' calendar
    private void displayCalendarMenu() {
        
    }

    // EFFECTS: dispays the months in the given year
    private void displayYearMenu(Year year) {

    }

    // EFFECTS: dispays the days in the given month
    private void displayMonthMenu(Month month) {

    }

    // EFFECTS: dispays the day menu for the given day
    private void displayDayMenu(Day day) {
        
    }

    // REQUIRES: choice corresponds to an integer on the menu or -1
    // EFFECTS: chooses the correct year based on the given choice
    private boolean handleCalendarChoice(int choice) {
        return false;
    }

    // REQUIRES: choice corresponds to an integer on the menu or -1
    // EFFECTS: chooses the correct month based on the given choice
    private boolean handleYearChoice(int choice) {
        return false;
    }

    // REQUIRES: choice corresponds to an integer on the menu or -1
    // EFFECTS: chooses the correct day based on the given choice
    private boolean handleMonthChoice(int choice) {
        return false;
    }

    // REQUIRES: choice corresponds to an integer on the menu or -1
    // EFFECTS: chooses what to display based on the given choice
    private boolean handleDayChoice(int choice) {
        return false;
    }

}
