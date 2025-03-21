package model;

import java.util.List;

import java.util.ArrayList;

// A Day that stores the month, day number, and any events that are starting, ongoing, and/or ending
public class Day {
    
    // fields
    private Year year;
    private Month month;
    private int dayNumber;
    private List<Event> events;

    // REQUIRES: 0 < dayNumber <= the last day number for the given month
    // EFFECTS: initializes a Day with no events, a given year, a given month, a given dayNumber
    public Day(Year year, Month month, int dayNumber) {
        this.year = year;
        this.month = month;
        this.dayNumber = dayNumber;
        events = new ArrayList<Event>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given event to the events for this Day, sorted by starting time
    public void addEvent(Event newEvent) {
        int index = 0;

        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            
            if (newEvent.getStartTime().isBefore(event.getStartTime())) {
                break;
            }

            index++;

        }

        if (index == events.size()) {
            events.add(newEvent);
        } else {
            events.add(index, newEvent);
        }

    }

    // MODIFIES: this 
    // EFFECTS: removes the given event from the events for this Day if it exists, nothing otherwise
    public void removeEvent(Event removedEvent) {
        events.remove(removedEvent);
    }

    // MODIFIES: this
    // EFFECTS: removes all the events from this Day
    public void clearEvents() {
        events.clear();
    }

    // EFFECTS: returns true if there are any events on this Day, false otherwise
    public boolean isActive() {
        return (events.size() > 0);
    }

    // EFFECTS: returns true if this Day is before the given day
    public boolean isBefore(Day day) {
        if (this.month.equals(day.getMonth())) {
            return this.dayNumber < day.getDayNumber();
        }

        return this.month.isBefore(day.getMonth());
    }

    /*
     * GETTERS/SETTERS
     */

    public Year getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public List<Event> getEvents() {
        return events;
    }
    
    public void setYear(Year year) {
        this.year = year;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

}
