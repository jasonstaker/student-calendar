package model;

import java.util.List;

// An Event that has a Category and/or Subcategory, time, name, and reccuring days
public class Event {
    
    // EFFECTS: initializes an Event with given category, subcategory, time, name, and recurring days
    public Event(Category category, Subcategory subcategory, Time time, String name, List<Day> recurringDays) {
        
    }

    // EFFECTS: returns true if the event happens more than once
    public boolean isRecurring() {
        return false;
    }

    // EFFECTS: returns true if the this Event occurs on the given day
    public boolean occursOn(Day day) {
        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds the given day to the reccuring days if it is not there, nothing otherwise
    public void addRecurringDay(Day addDay) {

    }

    // MODIFIES: this
    // EFFECTS: removes the given day from the recurring days if it exists, nothing otherwise
    public void removeRecurringDay(Day removedDay) {

    }

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
