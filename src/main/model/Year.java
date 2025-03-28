package model;

import java.util.List;

import java.util.ArrayList;

// A Year with a year number that contains a list of months and a current month/index
public class Year {

    // fields
    private int yearNumber;
    private List<Month> months;
    private int currentMonthIndex;

    // initializes a Year with a given year number and 12 months
    public Year(int yearNumber) {
        this.yearNumber = yearNumber;
        currentMonthIndex = 0;

        months = new ArrayList<Month>();
    
        for (int i = 0; i < 12; i++) {
            months.add(new Month(this, i));
        }

    }

    // MODIFIES: this
    // EFFECTS: increments the month index if it is < 11, nothing otherwise
    public void incrementMonthIndex() {
        currentMonthIndex++;

        if (currentMonthIndex > 11) {
            currentMonthIndex = 11;
        }

    }

    // MODIFIES: this
    // EFFECTS: decrements the month index if it is > 0, nothing otherwise
    public void decrementMonthIndex() {
        currentMonthIndex--;

        if (currentMonthIndex < 0) {
            currentMonthIndex = 0;
        }

    }

    // EFFECTS: returns true if this year is before the given year, false otherwise
    public boolean isBefore(Year year) {
        return this.yearNumber < year.getYearNumber();
    }

    // EFFECTS: returns true if this year is after the given year, false otherwise
    public boolean isAfter(Year year) {
        return this.yearNumber > year.getYearNumber();
    }

    // EFFECTS: returns true if this year is a leap year, false otherwise
    public boolean isLeapYear() {
        if (this.yearNumber % 400 == 0) {
            return true;
        } else if (this.yearNumber % 100 == 0) {
            return false;
        }

        return (this.yearNumber % 4 == 0);
    }

    /*
     * GETTERS/SETTERS
     */

    public int getYearNumber() {
        return yearNumber;
    }

    public List<Month> getMonths() {
        return months;
    }

    public Month getCurrentMonth() {
        return months.get(currentMonthIndex);
    }

    public void setCurrentMonthIndex(int currentMonthIndex) {
        this.currentMonthIndex = currentMonthIndex;
    }
    
}