package model;

// A Time with an hour and minute
public class Time {
    
    // REQUIRES: 0 <= hour <= 23, 0 <= minute <= 59
    // EFFECTS: initializes a Time with the given hour and minute values
    public Time(int hour, int minute) {

    }

    // EFFECTS: returns a string representation of the time
    public String timeToString() {
        return "";
    }

    // EFFECTS: returns true if the given Time is equal to this Time, false otherwise
    public boolean equals(Time newTime) {
        return false;
    }

    // EFFECTS: returns true if the given Time is before this Time, false otherwise
    public boolean isBefore(Time newTime) {
        return false;
    }

    // EFFECTS: returns true if the given Time is after this Time, false otherwise
    public boolean isAfter(Time newTime) {
        return false;
    }

    /*
     * GETTERS/SETTERS
     */

    public int getHour() {
        return -1;
    }

    public int getMinute() {
        return -1;
    }

    public void setHour(int hour) {

    }

    public void setMinute(int minute) {

    }

}
