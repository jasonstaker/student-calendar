package model;

import java.util.ArrayList;
import java.util.List;

// A Month with a month number that contains a list of days
public class Month {

    private static final String[] months = {"January", "February", "March", "April", "May", "June", "July", 
                                            "August", "September", "October", "November", "December"};
    private static final int[] dayInfo = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private String name;
    private int monthNumber;
    private List<Day> days;
    
    // REQUIRES: 0 <= monthNumber <= 11
    // EFFECTS: initializes a Month with default values and a given monthNumber
    public Month(int monthNumber) {
        this.monthNumber = monthNumber;
        name = months[monthNumber];

        days = new ArrayList<Day>();

        for(int i = 0; i < dayInfo[monthNumber]; i++) {
            days.add(new Day(this, i));
        }
    }

    // EFFECTS: returns true if the given day exists in this Month
    public boolean hasDay(Day day) {
        return days.contains(day);
    }

    // Effects: returns true if this Month is before the given Month
    public boolean isBefore(Month month) {
        return this.monthNumber < month.getMonthNumber();
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

}
