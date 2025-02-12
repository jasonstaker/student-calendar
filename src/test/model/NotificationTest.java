package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotificationTest {

    Notification notification;
    Day day1;
    Day day2;
    List<Day> dayList;
    
    @BeforeEach
    void setup() {
        day1 = new Day(new Month(0), 10);
        day2 = new Day(new Month(4), 22);

        dayList = new ArrayList<Day>();
        dayList.add(day1);
        dayList.add(day2);

        notification = new Notification("test", 1, null, dayList, "test message");
    }

    @Test
    void testNotificationConstructor() {
        assertEquals("test", notification.getTitle());
        assertEquals(1, notification.getUrgencyLevel());
        assertNull(notification.getEvent());
        assertEquals(dayList, notification.getReminderDays());
        assertEquals("test message", notification.getMessage());
    }

    @Test
    void testNotificationToString() {
        assertTrue(false);
        // TODO: once you figure out the UI implement the method
    }

    @Test
    void testHasReminderOnPass() {
        assertTrue(notification.hasReminderOn(day1));
    }

    @Test
    void testHasReminderOnFail() {
        assertFalse(notification.hasReminderOn(new Day(new Month(4), 1)));
    }

    @Test
    void testGetUrgencyAsStringLow() {
        assertEquals("Low", notification.getUrgencyLevel());
    }

    @Test
    void testGetUrgencyAsStringMedium() {
        notification.setUrgencyLevel(2);
        assertEquals("Medium", notification.getUrgencyLevel());
    }

    @Test
    void testGetUrgencyAsStringHigh() {
        notification.setUrgencyLevel(3);
        assertEquals("High", notification.getUrgencyLevel());
    }

    @Test
    void testAddReminderDate() {
        notification.setReminderDays(new ArrayList<Day>());
        notification.addReminderDate(day1);

        assertEquals(day1, notification.getReminderDays().get(0));
        assertEquals(1, notification.getReminderDays().size());
    }

    @Test
    void testAddReminderDateComplex() {
        Day day3 = new Day(new Month(4), 23);
        Day day4 = new Day(new Month(5), 1);
        Day day5 = new Day(new Month(5), 2);
        Day day6 = new Day(new Month(5), 3);
        Day day7 = new Day(new Month(6), 3);

        notification.addReminderDate(day5);
        notification.addReminderDate(day7);
        notification.addReminderDate(day4);
        notification.addReminderDate(day6);
        notification.addReminderDate(day3);

        assertEquals(day1, notification.getReminderDays().get(0));
        assertEquals(day1, notification.getReminderDays().get(1));
        assertEquals(day1, notification.getReminderDays().get(2));
        assertEquals(day1, notification.getReminderDays().get(3));
        assertEquals(day1, notification.getReminderDays().get(4));
        assertEquals(day1, notification.getReminderDays().get(5));
        assertEquals(day1, notification.getReminderDays().get(6));
        assertEquals(7, notification.getReminderDays().size());
    }
    
}
