package model;

import java.util.ArrayList;
import java.util.List;

// A Month with a month number that contains a list of days
public class Month {

    // an array of the month names and one for the number of days for each month 
    private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", 
                                            "August", "September", "October", "November", "December"};
    private static final int[] DAY_INFO = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    // fields
    private String name;
    private Year year;
    private int monthNumber;
    private List<Day> days;
    private int currentDayIndex;
    
    // REQUIRES: 0 <= monthNumber <= 11
    // EFFECTS: initializes a Month with default values, a given monthNumber, and a given Year
    public Month(Year year, int monthNumber) {
        this.monthNumber = monthNumber;
        name = MONTHS[monthNumber];
        this.year = year;

        days = new ArrayList<Day>();
        int loopControl = DAY_INFO[monthNumber];

        if (year.isLeapYear() && monthNumber == 1) {
            loopControl++;
        }

        for (int i = 1; i <= loopControl; i++) {
            days.add(new Day(year, this, i));
        }

    }

    // EFFECTS: returns true if the given day exists in this Month
    public boolean hasDay(Day day) {
        return days.contains(day);
    }

    // EFFECTS: returns true if this Month is before the given Month
    public boolean isBefore(Month month) {
        
        if (this.year.equals(month.getYear())) {
            return this.monthNumber < month.getMonthNumber();
        }
        
        return this.year.isBefore(month.getYear());
    }

    // EFFECTS: returns the first day in days
    public Day getLowestDay() {
        return days.get(0);
    }

    // EFFECTS: returns the highest day in days
    public Day getHighestDay() {
        return days.get(days.size() - 1);
    }

    // EFFECTS: increments the currentDayIndex
    public void incrementDayIndex() {
        if (currentDayIndex == days.size() - 1) {
            return;
        }
        currentDayIndex++;
    }

    // EFFECTS: decrements the currentDayIndex
    public void decrementDayIndex() {
        if (currentDayIndex == 0) {
            return;
        }

        currentDayIndex--;
    }

    /*
     * GETTERS/SETTERS
     */

    public String getName() {
        return name;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public List<Day> getDays() {
        return days;
    }

    public Year getYear() {
        return year;
    }

    public Day getCurrentDay() {
        return days.get(currentDayIndex);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public void setCurrentDayIndex(int currentDayIndex) {
        this.currentDayIndex = currentDayIndex;
    }

}