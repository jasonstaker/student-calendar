package model;

import java.util.List;

// A Month with a month number that contains a list of days
public class Month {
    
    // REQUIRES: 0 <= monthNumber <= 11
    // EFFECTS: initializes a Month with default values and a given monthNumber
    public Month(int monthNumber) {

    }

    // EFFECTS: returns true if the given day exists in this Month
    public boolean hasDay(Day day) {
        return false;
    }

    // Effects: returns true if this Month is before the given Month
    public boolean isBefore(Month month) {
        return false;
    }

    /*
     * GETTERS/SETTERS
     */

    public int getMonthNumber() {
        return -1;
    }

    public List<Day> getDays() {
        return null;
    }

}
