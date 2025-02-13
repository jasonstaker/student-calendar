package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalendarTest {
    
    Calendar calendar;

    @BeforeEach
    void setup() {
        calendar = new Calendar("test", 2025);
    }

    @Test
    void testCalendarConstructor() {
        assertEquals("test", calendar.getTitle());
        assertEquals(3, calendar.getYears().size());
        assertEquals(calendar.getYears().get(1), calendar.getCurrentYear());
    }

    @Test
    void testMonthIncrement() {
        calendar.incrementYearIndex();

        assertEquals(calendar.getYears().get(2), calendar.getCurrentYear());
    }

    @Test
    void testMonthIncrementUpper() {
        for (int i = 0; i < 5; i++) {
            calendar.incrementYearIndex();
        }

        assertEquals(calendar.getYears().get(2), calendar.getCurrentYear());
    }

    @Test
    void testMonthDecrement() {
        calendar.decrementYearIndex();

        assertEquals(calendar.getYears().get(0), calendar.getCurrentYear());
    }

    @Test
    void testMonthDecrementLower() {
        calendar.decrementYearIndex();
        calendar.decrementYearIndex();

        assertEquals(calendar.getYears().get(0), calendar.getCurrentYear());
    }
    
}
