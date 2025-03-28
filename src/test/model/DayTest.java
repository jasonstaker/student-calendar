package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DayTest {
    
    private Day day;
    private Month month;
    private Year year;
    private Event event1;
    private Event event2;
    private List<Day> recurringDays;

    @BeforeEach
    void setup() {
        year = new Year(2025);
        month = new Month(year, 0);
        day = new Day(year, month, 5);

        recurringDays = new ArrayList<Day>();
        event1 = new Event(null, null, new Time(1,0), null, "", recurringDays);
        event2 = new Event(null, null, new Time(2,0), null, "", recurringDays);
    }

    @Test
    void testDayConstructor() {
        assertEquals(year, day.getYear());
        assertEquals(month, day.getMonth());
        assertEquals(5, day.getDayNumber());
        assertEquals(new ArrayList<Event>(), day.getEvents());
    }

    @Test
    void testAddEvent() {
        day.addEvent(event1);

        assertEquals(event1, day.getEvents().get(0));
    }

    @Test
    void testAddEventsBefore() {
        day.addEvent(event2);
        day.addEvent(event1);

        assertEquals(event1, day.getEvents().get(0));
        assertEquals(event2, day.getEvents().get(1));
    }

    @Test
    void testAddEventsSameDay() {
        day.addEvent(event2);
        day.addEvent(event2);
        day.addEvent(event1);

        assertEquals(event1, day.getEvents().get(0));
        assertEquals(event2, day.getEvents().get(1));
        assertEquals(event2, day.getEvents().get(2));
    }


    @Test
    void testRemoveEvent() {
        day.addEvent(event1);
        day.removeEvent(event1);

        assertEquals(0, day.getEvents().size());
    }

    @Test
    void testRemoveEventDeep() {
        day.addEvent(event1);
        day.addEvent(event2);
        day.removeEvent(event1);

        assertEquals(event2, day.getEvents().get(0));
        assertEquals(1, day.getEvents().size());
    }

    @Test
    void testClearEventsEmpty() {
        day.clearEvents();

        assertEquals(new ArrayList<Event>(), day.getEvents());
    }

    @Test
    void testClearEventsOne() {
        day.addEvent(event1);
        day.clearEvents();

        assertEquals(new ArrayList<Event>(), day.getEvents());
    }

    @Test
    void testClearEventsMultiple() {
        day.addEvent(event1);
        day.addEvent(event2);
        day.clearEvents();

        assertEquals(new ArrayList<Event>(), day.getEvents());
    }

    @Test
    void testIsActivePass() {
        day.addEvent(event1);
        assertTrue(day.isActive());
    }

    @Test
    void testIsActivePassMultiple() {
        day.addEvent(event1);
        day.addEvent(event2);
        assertTrue(day.isActive());
    }

    @Test
    void testIsActiveFail() {
        assertFalse(day.isActive());
    }

    @Test
    void testIsBeforeDayPass() {
        Day testDay = new Day(year, month, 4);
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
        Day testDay = new Day(new Year(2026), new Month(new Year(2026), 0), 5);
        assertTrue(day.isBefore(testDay));
    }

    @Test
    void testIsBeforeSameMonthPass() {
        Day testDay = new Day(year, month, 6);
        assertTrue(day.isBefore(testDay));
    }

    @Test
    void testIsBeforeYearFail() {
        Day testDay = new Day(year, month, 4);
        assertFalse(day.isBefore(testDay));
    }

    @Test
    void testIsBeforeSameMonthFail() {
        Day testDay = new Day(year, new Month(year, 1), 5);
        assertTrue(day.isBefore(testDay));
    }

    @Test
    void testSetYear() {
        Year newYear = new Year(2030);
        day.setYear(newYear);
        assertEquals(newYear, day.getYear());
    }

    @Test
    void testSetMonth() {
        Month newMonth = new Month(year, 1);
        day.setMonth(newMonth);
        assertEquals(newMonth, day.getMonth());
    }

    @Test
    void testSetDayNumber() {
        day.setDayNumber(10);
        assertEquals(10, day.getDayNumber());
    }


}