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
