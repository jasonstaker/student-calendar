package model;

import java.util.List;
import java.util.ArrayList;

// A Day that stores the month, day number, and any events that are starting, ongoing, and/or ending
public class Day {
    
    Month month;
    int dayNumber;
    List<Event> events;

    // REQUIRES: 0 < dayNumber <= the last day number for the given month
    // EFFECTS: initializes a Day with no events, a given month, and a given dayNumber
    public Day(Month month, int dayNumber) {
        this.month = month;
        this.dayNumber = dayNumber;
        events = new ArrayList<Event>();
    }

    // MODIFIES: this 
    // EFFECTS: adds the given event to the events for this Day, sorted by starting time
    public void addEvent(Event newEvent) {
        int index = 0;
        Time newTime = newEvent.getStartTime();

        for (int i = 0; i < events.size(); i++) {
            Time time = events.get(i).getStartTime();
            
            index = i;
            
            if (newTime.isBefore(time) || newTime.equals(time)) {
                break;
            }

        }

        events.add(index, newEvent);
    }

    // MODIFIES: this 
    // EFFECTS: removes the given event to the events for this Day if it exists, nothing otherwise
    public void removeEvent(Event removedEvent) {
        events.remove(removedEvent);
    }

    // MODIFIES: this
    // EFFECTS: removes all the events from this Day
    public void clearEvents() {
        events = new ArrayList<Event>();
    }

    // EFFECTS: returns true if there are any events on this Day, false otherwise
    public boolean isActive() {
        return (events.size() > 0);
    }

    /*
     * GETTERS/SETTERS
     */

    public Month getMonth() {
        return month;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

}
