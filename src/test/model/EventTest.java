package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventTest {
    
    Category c;
    Subcategory sc;
    Time st;
    Time et;
    List<Day> rd;
    Day day1;
    Day day2;
    Event event;

    @BeforeEach
    void setup() {
        c = new Category();
        sc = new Subcategory();
        st = new Time(1, 4);
        st = new Time(2, 4);

        day1 = new Day(new Month(0), 22);
        day2 = new Day(new Month(3), 1);
        
        rd = new ArrayList<Day>();

        rd.add(day1);

        event = new Event(c, sc, st, et, "test", rd);
    }

    @Test
    void testEventConstructor() {
        assertEquals(c, event.getCategory());
        assertEquals(sc, event.getSubcategory());
        assertEquals(st, event.getStartTime());
        assertEquals(et, event.getEndTime());
        assertEquals("test", event.getName());
        assertEquals(rd, event.getRecurringDays());
    }

    @Test
    void testIsRecurringPass() {
        event.addRecurringDay(day2);

        assertTrue(event.isRecurring());
    }

    @Test
    void testIsRecurringFail() {
        assertFalse(event.isRecurring());
    }

    @Test
    void testOccursOnSinglePass() {
        assertTrue(event.occursOn(day1));
    }

    @Test
    void testOccursOnSingleFail() {
        assertFalse(event.occursOn(day2));
    }

    @Test
    void testOccursOnMultiplePass() {
        event.addRecurringDay(day2);

        assertTrue(event.occursOn(day2));
    }

    @Test
    void testOccursOnMultipleFail() {
        event.addRecurringDay(day1);

        assertFalse(event.occursOn(day2));
    }

    @Test
    void testAddRecurringDay() {
        event.addRecurringDay(day2);

        assertEquals(2, event.getRecurringDays().size());
        assertEquals(day2, event.getRecurringDays().get(1));
    }

    @Test
    void testAddRecurringDayComplex() {
        Day day3 = new Day(new Month(0), 1);
        
        event.addRecurringDay(day2);
        event.addRecurringDay(day3);

        assertEquals(3, event.getRecurringDays().size());
        assertEquals(day3, event.getRecurringDays().get(0));
        assertEquals(day1, event.getRecurringDays().get(1));
        assertEquals(day2, event.getRecurringDays().get(2));
    }

    @Test
    void testRemoveRecurringDay() {
        event.removeRecurringDay(day1);

        assertEquals(0, event.getRecurringDays().size());
    }
}