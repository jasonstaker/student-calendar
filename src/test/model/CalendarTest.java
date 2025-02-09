package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalendarTest {
    
    Calendar calendar;

    @BeforeEach
    void setup() {
        calendar = new Calendar("test");
    }

    @Test
    void testCalendarConstructor() {
        assertEquals("test", calendar.getTitle());
        assertEquals(12, calendar.getMonths().length);
        assertEquals(0, calendar.getCurrentMonthIndex());
    }
}
