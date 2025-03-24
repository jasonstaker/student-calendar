package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
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
    private List<String> emptyList;

    @BeforeEach
    void setup() {
        emptyList = new ArrayList<String>();
        category = new Category();
        sc = new Subcategory(category, 0, emptyList, "", "", emptyList, emptyList);
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
    void testSetMethods() {
        event.setCategory(null);
        event.setEndTime(null);
        event.setId(5);
        event.setRecurringDays(new ArrayList<Day>());
        event.setName("");
        event.setStartTime(null);
        event.setSubcategory(null);
        assertNull(event.getCategory());
        assertNull(event.getEndTime());
        assertEquals(5, event.getId());
        assertEquals(new ArrayList<Day>(), event.getRecurringDays());
        assertEquals("", event.getName());
        assertNull(event.getStartTime());
        assertNull(event.getSubcategory());
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

    @Test
    void testToJsonWithValidCategoryAndSubcategory() {
        Category category = new Category("Work");
        Subcategory subcategory = new Subcategory("Meetings");
        Time startTime = new Time(9, 30);
        Time endTime = new Time(10, 30);
        List<Day> recurringDays = new ArrayList<Day>();

        Event event = new Event(category, subcategory, startTime, endTime, "", recurringDays);

        JSONObject json = event.toJson();

        assertEquals("", json.getString("name"));
        assertEquals(9, json.getJSONObject("startTime").getInt("hour"));
        assertEquals(10, json.getJSONObject("endTime").getInt("hour"));
        assertEquals(category.getId(), json.getInt("category"));
        assertEquals(subcategory.getId(), json.getInt("subcategory"));
        assertEquals(0, json.getJSONArray("recurringDays").length());
    }

    @Test
    void testToJsonWithNullCategoryAndSubcategory() {
        Category category = null;
        Subcategory subcategory = null;
        Time startTime = new Time(9, 30);
        Time endTime = new Time(10, 30);
        List<Day> recurringDays = new ArrayList<Day>();

        Event event = new Event(category, subcategory, startTime, endTime, "", recurringDays);

        JSONObject json = event.toJson();

        assertEquals("", json.getString("name"));
        assertEquals(9, json.getJSONObject("startTime").getInt("hour"));
        assertEquals(10, json.getJSONObject("endTime").getInt("hour"));
        assertNull(json.optJSONObject("category"));
        assertNull(json.optJSONObject("subcategory"));
        assertEquals(0, json.getJSONArray("recurringDays").length());
    }

    @Test
    void testSetRecurringDaysAddsEventToEachDay() {
        event.setRecurringDays(recurringDays);

        assertEquals(1, event.getRecurringDays().size());
        assertTrue(day1.getEvents().contains(event));
    }

}