package model;

import java.util.List;

// A Notification with a title, urgency level, event, reminder date(s), and message
public class Notification {
    
    // EFFECTS: initializes a Notification with default values
    public Notification() {

    }

    // EFFECTS: returns a String representation of a Notification
    public String notificationToString() {
        return "";
    }

    // EFFECTS: returns true if the Notification is set to remind the user on a given Day
    public boolean hasReminderOn(Day day) {
        return false;
    }

    // EFFECTS: returns the urgency level of the Notification as a string
    //          1: "Low"
    //          2: "Medium"
    //          3; "High"
    public String getUrgencyAsString() {
        return "";
    }

    // TODO: notification are SUPER complex so add constructors as needed

    /*
     * GETTERS/SETTERS
     */

    public String getTitle() {
        return "";
    }

    public int getUrgencyLevel() {
        return -1;
    }

    public Event getEvent() {
        return null;
    }

    public List<Day> getReminderDays() {
        return null;
    }

    public String getMessage() {
        return "";
    }

    public void setTitle(String title) {

    }

    public void setUrgencyLevel(int urgencyLevel) {

    }

    public void setEvent(Event event) {

    }

    public void setReminderDays(List<Day> reminderDays) {

    }

    public void setMessage(String message) {

    }

}
