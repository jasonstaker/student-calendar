package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DayTest {
    
    Day day;
    Month month;
    Year year;
    Event e1;
    Event e2;
    List<Day> recurringDays;

    @BeforeEach
    void setup() {
        year = new Year(2025);
        month = new Month(year, 0);
        day = new Day(year, month, 5);

        recurringDays = new ArrayList<Day>();
        e1 = new Event(null, null, new Time(1,0), null, "", recurringDays);
        e2 = new Event(null, null, new Time(2,0), null, "", recurringDays);
    }

    @Test
    void testDayConstructor() {
        assertEquals(year, day.getYear());
        assertEquals(month, day.getMonth());
        assertEquals(5, day.getDayNumber());
        assertEquals(0, day.getEvents().size());
    }

    @Test
    void testAddEvent() {
        day.addEvent(e1);

        assertEquals(e1, day.getEvents().get(0));
    }

    @Test
    void testAddEventsBefore() {
        day.addEvent(e2);
        day.addEvent(e1);

        assertEquals(e1, day.getEvents().get(0));
        assertEquals(e2, day.getEvents().get(1));
    }

    @Test
    void testRemoveEvent() {
        day.addEvent(e1);
        day.removeEvent(e1);

        assertEquals(0, day.getEvents().size());
    }

    @Test
    void testRemoveEventDeep() {
        day.addEvent(e1);
        day.addEvent(e2);
        day.removeEvent(e1);

        assertEquals(e2, day.getEvents().get(0));
        assertEquals(1, day.getEvents().size());
    }

    @Test
    void testClearEventsEmpty() {
        day.clearEvents();

        assertEquals(0, day.getEvents().size());
    }

    @Test
    void testClearEventsOne() {
        day.addEvent(e1);
        day.clearEvents();

        assertEquals(0, day.getEvents().size());
    }

    @Test
    void testClearEventsMultiple() {
        day.addEvent(e1);
        day.addEvent(e2);
        day.clearEvents();

        assertEquals(0, day.getEvents().size());
    }

    @Test
    void testIsActivePass() {
        day.addEvent(e1);
        assertTrue(day.isActive());
    }

    @Test
    void testIsActiveFail() {
        assertFalse(day.isActive());
    }

    @Test
    void testIsBeforeDayPass() {
        Day testDay = new Day(year, new Month(year, 0), 4);
        assertTrue(testDay.isBefore(day));
    }

    @Test
    void testIsBeforeDayFail() {
        Day testDay = new Day(year, new Month(year, 0), 5);
        assertFalse(testDay.isBefore(day));
    }

    @Test
    void testIsBeforeMonthPass() {
        Day testDay = new Day(year, new Month(year, 1), 5);
        assertTrue(day.isBefore(testDay));
    }

    @Test
    void testIsBeforeMonthFail() {
        Day testDay = new Day(year, new Month(year, 0), 5);
        assertFalse(day.isBefore(testDay));
    }

    @Test
    void testIsBeforeYearPass() {
        Day testDay = new Day(new Year(2024), new Month(new Year(2024), 0), 5);
        assertTrue(day.isBefore(testDay));
    }

    @Test
    void testIsBeforeYearFail() {
        Day testDay = new Day(year, new Month(year, 0), 5);
        assertFalse(day.isBefore(testDay));
    }

}