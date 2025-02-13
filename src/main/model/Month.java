package model;

import java.util.ArrayList;
import java.util.List;

// A Month with a month number that contains a list of days
public class Month {

    private static final String[] months = {"January", "February", "March", "April", "May", "June", "July", 
                                            "August", "September", "October", "November", "December"};
    private static final int[] dayInfo = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private String name;
    private Year year;
    private int monthNumber;
    private List<Day> days;
    
    // REQUIRES: 0 <= monthNumber <= 11
    // EFFECTS: initializes a Month with default values and a given monthNumber
    public Month(Year year, int monthNumber) {
        this.monthNumber = monthNumber;
        name = months[monthNumber];
        this.year = year;

        days = new ArrayList<Day>();
        int loopControl = dayInfo[monthNumber];

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

    // Effects: returns true if this Month is before the given Month
    public boolean isBefore(Month month) {
        if (this.year.equals(month.getYear())) {
            return this.monthNumber < month.getMonthNumber();
        }
        
        return this.year.isBefore(month.getYear());
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

}
