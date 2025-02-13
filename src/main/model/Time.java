package model;

// A Time with an hour and minute
public class Time {

    private int hour;
    private int minute;
    
    // REQUIRES: 0 <= hour <= 23, 0 <= minute <= 59
    // EFFECTS: initializes a Time with the given hour and minute values
    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    // EFFECTS: returns a string representation of the time
    public String timeToString() {
        String suffix = "A.M.";
        int stringHour = hour % 12;
        String stringMinute = minute + "";

        if (hour == 0) {
            stringHour = 12;
        }

        if (minute < 10) {
            stringMinute = "0" + stringMinute;
        }

        if (hour >= 12) {
            suffix = "P.M.";
        }

        return stringHour + ":" + stringMinute + " " + suffix;
    }

    // EFFECTS: returns true if the given Time is equal to this Time, false otherwise
    public boolean equals(Time newTime) {
        return (this.hour == newTime.getHour()) && (this.minute == newTime.getMinute());
    }

    // EFFECTS: returns true if the given Time is before this Time, false otherwise
    public boolean isBefore(Time newTime) {
        if (this.hour == newTime.hour) {
            return this.minute < newTime.getMinute();
        }

        return this.hour < newTime.getHour();
    }

    // EFFECTS: returns true if the given Time is after this Time, false otherwise
    public boolean isAfter(Time newTime) {
        if (this.hour == newTime.hour) {
            return this.minute > newTime.getMinute();
        }

        return this.hour > newTime.getHour();
    }

    /*
     * GETTERS/SETTERS
     */

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

}
