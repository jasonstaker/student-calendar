package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CalendarEvent class
 */
public class CalendarEventTest {
    private CalendarEvent e1;
    private CalendarEvent e2;
    private CalendarEvent e3;
    private Date d;

    @BeforeEach
    public void runBefore() {
        d = Calendar.getInstance().getTime();
        e1 = new CalendarEvent("Event added to calendar");
        e2 = new CalendarEvent("Event added to calendar");
        e3 = new CalendarEvent("Different event");
    }

    @Test
    public void testCalendarEvent() {
        assertEquals("Event added to calendar", e1.getDescription());
        assertEquals(d, e1.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Event added to calendar", e1.toString());
    }

    @Test
    public void testEqualsSameObject() {
        assertEquals(e1, e1);
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals(e1, null);
    }

    @Test
    public void testEqualsDifferentClass() {
        assertNotEquals(e1, "Not a CalendarEvent");
    }

    @Test
    public void testEqualsSameValues() {
        assertEquals(e1, e2);
    }

    @Test
    public void testEqualsDifferentValues() {
        assertNotEquals(e1, e3);
    }

    @Test
    public void testEqualsDifferentDates() {
        CalendarEvent e4 = new CalendarEvent("Event added to calendar");
        assertNotEquals(e1, e4);
    }

    @Test
    public void testHashCodeConsistency() {
        int hash1 = e1.hashCode();
        int hash2 = e1.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    public void testHashCodeDifferentObjects() {
        assertEquals(e1.hashCode(), e2.hashCode());
        assertNotEquals(e1.hashCode(), e3.hashCode());
    }
}
