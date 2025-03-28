package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a log of alarm system events.
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the system and that the system has global access
 * to the single instance of the EventLog.
 */
public class CalendarEventLog implements Iterable<CalendarEvent> {
    /** the only CalendarEventLog in the system (Singleton Design Pattern) */
    private static CalendarEventLog theLog;
    private Collection<CalendarEvent> events;

    /**
     * Prevent external construction.
     * (Singleton Design Pattern).
     */
    private CalendarEventLog() {
        events = new ArrayList<CalendarEvent>();
    }

    /**
     * Gets instance of EventLog - creates it
     * if it doesn't already exist.
     * (Singleton Design Pattern)
     * 
     * @return instance of EventLog
     */
    public static CalendarEventLog getInstance() {
        if (theLog == null) {
            theLog = new CalendarEventLog();
        }

        return theLog;
    }

    /**
     * Adds an event to the event log.
     * 
     * @param e the event to be added
     */
    public void logEvent(CalendarEvent e) {
        events.add(e);
    }

    /**
     * Clears the event log and logs the event.
     */
    public void clear() {
        events.clear();
        logEvent(new CalendarEvent("Event log cleared."));
    }

    @Override
    public Iterator<CalendarEvent> iterator() {
        return events.iterator();
    }
}