package model;

import java.util.List;

// A Notification with a title, urgency level, event, reminder date(s), and message
public class Notification {
    
    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public Notification() {

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS:
    public void sendNotification() {

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS:
    public boolean hasReminderOn(Day day) {
        return false;
    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS:
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
