package model;

import java.util.List;
import java.util.ArrayList;

// A Calendar with a title that stores a list of years and a current year
public class Calendar {

    private String title;
    private List<Year> years;
    private int currentYearIndex;

    // REQUIRES: currentYear is a valid year
    // EFFECTS: initializes a new Calendar with a given title
    public Calendar(String title, int currentYear) {
        this.title = title;
        currentYearIndex = 1;
        years = new ArrayList<Year>();
        
        for (int i = -1; i <= 1; i++) {
            years.add(new Year(currentYear + i));
        }
    }

    // MODIFIES: this
    // EFFECTS: increments the Year index if it does not exceed the highest year, nothing otherwise
    public void incrementYearIndex() {
        currentYearIndex++;
        
        if (currentYearIndex >= years.size()) {
            currentYearIndex = years.size() - 1;
        }
    }

    // MODIFIES: this
    // EFFECTS: decrements the year index if it does not go below 0, nothing otherwise
    public void decrementYearIndex() {
        currentYearIndex--;
        
        if (currentYearIndex < 0) {
            currentYearIndex = 0;
        }
    }

    // REQUIRES: years.size() > 0
    // EFFECTS: returns the first year in years
    public Year getLowestYear() {
        return years.get(0);
    }

    // REQUIRES: years.size() > 0
    // EFFECTS: returns the highest year in years
    public Year getHighestYear() {
        return years.get(years.size() - 1);
    }

    // EFFECTS: takes in a given string and determines whether it is a valid date in the calendar
    public Boolean isInCalendar(String date) {
        int year;
        int month;
        int day;
        if (date.length() != 10) {
            return false;
        }

        try {
            year = Integer.parseInt(date.substring(0, 4));
            month = Integer.parseInt(date.substring(5, 7));
            day = Integer.parseInt(date.substring(8, 10));
        } catch (Exception e) {
            return false;
        }

        for (int i = 0; i < years.size(); i++) {
            if (years.get(i).getYearNumber() == year) {
                if (month >= 1 && month <= 12) {
                    Month monthObj = years.get(i).getMonths().get(month - 1);
                    if (day >= 1 && day <= monthObj.getDays().size()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // REQUIRES: date is a valid date in YYYY/MM/DD
    // EFFECTS: returns the Day object in this Calender based on the given date
    public Day dateToDay(String date) {

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));

        Day dayObj = null;

        for (int i = 0; i < years.size(); i++) {
            if (years.get(i).getYearNumber() == year) {
                dayObj = years.get(i).getMonths().get(month - 1).getDays().get(day - 1);
            }
        }

        return dayObj;
    }

    /*
     * GETTERS/SETTERS
     */
    
    public String getTitle() {
        return title;
    }

    public List<Year> getYears() {
        return years;
    }

    public Year getCurrentYear() {
        return years.get(currentYearIndex);
    }
    
}
