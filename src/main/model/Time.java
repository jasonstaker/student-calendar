package model;

import org.json.JSONObject;

import persistence.Writable;

// A Time with an hour and minute
public class Time implements Writable {

    // fields
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

        if (hour == 0 || hour == 12) {
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

    // EFFECTS: returns whether the given string is a valid time in HH:MM format
    public Boolean isValidTime(String time) {
        int hour;
        int minute;
        if (time.length() != 5) {
            return false;
        }

        try {
            hour = Integer.parseInt(time.substring(0, 2));
            minute = Integer.parseInt(time.substring(3, 5));
            if (time.charAt(2) != ':') {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        
        if (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59) {
            return true;
        }

        return false;
    }

    // REQUIRES: time is a valid time in HH:MM format
    // EFFECTS: returns a time object with the given hour and minute
    public Time stringToTime(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));

        return new Time(hour, minute);
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

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("hour", hour);
        json.put("minute", minute);
        return json;
    }

}
