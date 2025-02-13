package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventTest {
    
    private Category category;
    private Subcategory sc;
    private Time startTime;
    private Time endTime;
    private List<Day> recurringDays;
    private Day day1;
    private Day day2;
    private Event event;

    @BeforeEach
    void setup() {
        category = new Category();
        sc = new Subcategory(null, 2, null);
        startTime = new Time(1, 4);
        startTime = new Time(2, 4);

        day1 = new Day(new Year(2025), new Month(new Year(2025), 0), 22);
        day2 = new Day(new Year(2025), new Month(new Year(2025), 3), 1);
        
        recurringDays = new ArrayList<Day>();

        recurringDays.add(day1);

        event = new Event(category, sc, startTime, endTime, "test", recurringDays);
    }

    @Test
    void testEventConstructor() {
        assertEquals(category, event.getCategory());
        assertEquals(sc, event.getSubcategory());
        assertEquals(startTime, event.getStartTime());
        assertEquals(endTime, event.getEndTime());
        assertEquals("test", event.getName());
        assertEquals(recurringDays, event.getRecurringDays());

        for (Day d: recurringDays) {
            assertEquals(event, d.getEvents().get(0));
        }
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
        Day day3 = new Day(new Year(2024), new Month(new Year(2024), 0), 1);
        
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