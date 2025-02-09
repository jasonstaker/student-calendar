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

    @Test
    void testMonthIncrement() {
        calendar.incrementMonthIndex();

        assertEquals(1, calendar.getCurrentMonthIndex());
    }

    @Test
    void testMonthIncrementUpper() {
        for (int i = 0; i < 13; i++) {
            calendar.incrementMonthIndex();
        }

        assertEquals(11, calendar.getCurrentMonthIndex());
    }

    @Test
    void testMonthDecrement() {
        calendar.incrementMonthIndex();
        calendar.incrementMonthIndex();
        calendar.decrementMonthIndex();

        assertEquals(1, calendar.getCurrentMonthIndex());
    }

    @Test
    void testMonthDecrementLower() {
        calendar.decrementMonthIndex();

        assertEquals(0, calendar.getCurrentMonthIndex());
    }
    
}
