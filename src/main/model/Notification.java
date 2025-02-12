package model;

import java.util.List;

// A Notification with a title, urgency level, event, reminder date(s), and message
public class Notification {

    private String title;
    private int urgencyLevel;
    private Event event;
    private List<Day> reminderDates;
    private String message;
    
    // REQUIRES: urgencyLevel is 1, 2, or 3
    // EFFECTS: initializes a Notification with default values
    public Notification(String title, int urgencyLevel, Event event, List<Day> reminderDates, String message) {
        this.title = title;
        this.urgencyLevel = urgencyLevel;
        this.event = event;
        this.reminderDates = reminderDates;
        this.message = message;
    }

    // EFFECTS: returns a String representation of a Notification
    public String notificationToString() {
        return "TO BE IMPLEMENTED";
    }

    // EFFECTS: returns true if the Notification is set to remind the user on a given Day
    public boolean hasReminderOn(Day day) {
        return reminderDates.contains(day);
    }

    // EFFECTS: returns the urgency level of the Notification as a string
    //          1: "Low"
    //          2: "Medium"
    //          3; "High"
    public String getUrgencyAsString() {
        if (urgencyLevel == 1) {
            return "Low";
        } else if (urgencyLevel == 2) {
            return "Medium";
        } else {
            return "High";
        }
    }

    // REQUIRES: reminderDate is unique from any day in reminderDates
    // MODIFIES: this
    // EFFECTS: Adds the reminderDate in increasing order to reminderDates
    public void addReminderDate(Day reminderDate) {
        int index = 0;

        for (int i = 0; i < reminderDates.size(); i++) {
            Day day = reminderDates.get(i);
            
            if (reminderDate.isBefore(day)) {
                break;
            }

            index++;

        }

        if(index == reminderDates.size()) {
            reminderDates.add(reminderDate);
        } else {
            reminderDates.add(index, reminderDate);
        }
    }

    /*
     * GETTERS/SETTERS
     */

    public String getTitle() {
        return title;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public Event getEvent() {
        return event;
    }

    public List<Day> getReminderDays() {
        return reminderDates;
    }

    public String getMessage() {
        return message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrgencyLevel(int urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setReminderDays(List<Day> reminderDates) {
        this.reminderDates = reminderDates;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
