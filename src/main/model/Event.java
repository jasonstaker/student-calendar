package model;

import java.util.List;

// An Event that has a Category and/or Subcategory, time, name, and reccuring status
public class Event {
    
    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public Event() {

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public boolean isRecurring() {
        return false;
    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public boolean occursOn(Day day) {
        return false;
    }

    // TODO: events are SUPER complex so add constructors as needed

    /*
     * GETTERS/SETTERS
     */

    public String getName() {
        return "";
    }

    public Category getCategory() {
        return null;
    }

    public Subcategory getSubcategory() {
        return null;
    }

    public List<Day> getRecurringDays() {
        return null;
    }

    public Time getStartTime() {
        return null;
    }

    public Time getEndTime() {
        return null;
    }

    public void setName(String name) {

    }

    public void setCategory(Category category)  {

    }

    public void setSubcategory(Subcategory subcategory) {

    }

    public void setRecurringDays(List<Day> recurringDays) {

    }

    public void setStartTime(Time startTime) {

    }

    public void setEndTime(Time endTime) {

    }

}
