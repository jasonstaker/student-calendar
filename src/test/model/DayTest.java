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
    Event e1;
    Event e2;
    List<Day> recurringDays;

    @BeforeEach
    void setup() {
        month = new Month(0);
        day = new Day(month, 5);

        recurringDays = new ArrayList<Day>();
        e1 = new Event(null, null, new Time(1,0), null, "", recurringDays);
        e2 = new Event(null, null, new Time(2,0), null, "", recurringDays);
    }

    @Test
    void testDayConstructor() {
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
        assertEquals(e1, day.getEvents().get(1));
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
}
