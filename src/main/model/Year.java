package model;

import java.util.List;
import java.util.ArrayList;

// A Year with a year number that contains a list of months and a current month/index
public class Year {

    // initializes a Year with a given year number and 12 months
    public Year(int yearNumber) {

    }

    // MODIFIES: this
    // EFFECTS: increments the month index if it is < 11, nothing otherwise
    public void incrementMonthIndex() {

    }

    // MODIFIES: this
    // EFFECTS: decrements the month index if it is > 0, nothing otherwise
    public void decrementMonthIndex() {

    }

    // EFFECTS: returns true if this year is before the given year, false otherwise
    public boolean isBefore(Year year) {
        return false;
    }

    // EFFECTS: returns true if this year is after the given year, false otherwise
    public boolean isAfter(Year year) {
        return false;
    }

    /*
     * GETTERS/SETTERS
     */

    public int getYearNumber() {
        return -1;
    }

    public List<Month> getMonths() {
        return null;
    }

    public Month getCurrentMonth() {
        return null;
    }

    public void setYearNumber() {
        
    }

    public void setMonths() {
        
    }

    public void setCurrentMonth() {
        
    }
    
}
