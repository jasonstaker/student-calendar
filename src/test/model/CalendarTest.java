package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

public class CalendarTest {
    
    private Calendar calendar;

    @BeforeEach
    void setup() {
        calendar = new Calendar("test", 2025);
    }

    @Test
    void testCalendarConstructor() {
        List<Category> categories = new ArrayList<Category>();
        List<Subcategory> subcategories = new ArrayList<Subcategory>();

        assertEquals("test", calendar.getTitle());
        assertEquals(2015, calendar.getYears().get(0).getYearNumber());
        assertEquals(2016, calendar.getYears().get(1).getYearNumber());
        assertEquals(2017, calendar.getYears().get(2).getYearNumber());
        assertEquals(calendar.getYears().get(1), calendar.getCurrentYear());
        assertEquals(categories, calendar.getCategories());
        assertEquals(subcategories, calendar.getSubcategories());
        assertEquals(10, calendar.getYearRange());
    }

    @Test
    void testMonthIncrement() {
        calendar.incrementYearIndex();

        assertEquals(calendar.getYears().get(2), calendar.getCurrentYear());
    }

    @Test
    void testMonthIncrementUpper() {
        for (int i = 0; i < 100; i++) {
            calendar.incrementYearIndex();
        }

        assertEquals(calendar.getYears().get(20), calendar.getCurrentYear());
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
    
    @Test
    void testGetLowestYear() {
        assertEquals(2015, calendar.getLowestYear().getYearNumber());
    }

    @Test
    void testGetHighestYear() {
        assertEquals(2035, calendar.getHighestYear().getYearNumber());
    }
    
    @Test
    void testIsInCalendarPassLower() {
        assertTrue(calendar.isInCalendar("2024/01/01"));
    }

    @Test
    void testIsInCalendarPassUpper() {
        assertTrue(calendar.isInCalendar("2026/12/31"));
    }

    @Test
    void testIsInCalendarFailInvalidYear() {
        assertFalse(calendar.isInCalendar("2039/01/01"));
    }

    @Test
    void testIsInCalendarFailInvalidDayUpper() {
        assertFalse(calendar.isInCalendar("2024/01/50"));
    }

    @Test
    void testIsInCalendarFailInvalidDayLower() {
        assertFalse(calendar.isInCalendar("2024/01/00"));
    }

    @Test
    void testIsInCalendarFailInvalidMonthUpper() {
        assertFalse(calendar.isInCalendar("2024/50/01"));
    }

    @Test
    void testIsInCalendarFailInvalidMonthLower() {
        assertFalse(calendar.isInCalendar("2024/-1/01"));
    }

    @Test
    void testIsInCalendarFailSlashFirst() {
        assertFalse(calendar.isInCalendar("2024 01 01"));
    }

    @Test
    void testIsInCalendarFailSlashLast() {
        assertFalse(calendar.isInCalendar("2024/01 01"));
    }

    @Test
    void testIsInCalendarFailIncorrectNumberFormat() {
        assertFalse(calendar.isInCalendar("2024/1/01"));
    }

    @Test
    void testIsInCalendarFailOutOfBounds() {
        assertFalse(calendar.isInCalendar("2024/51/01"));
    }

    @Test
    void testIsInCalendarFailString() {
        assertFalse(calendar.isInCalendar("2024/ff/01"));
    }

    @Test
    void testIsInCalendarFailWord() {
        assertFalse(calendar.isInCalendar("fail :)"));
    }

    @Test
    void testDateToDaySinglesDay() {
        Year year = calendar.getLowestYear();
        Month month = year.getMonths().get(11);
        Day day = month.getDays().get(4);

        assertEquals(day, calendar.dateToDay("2015/12/05"));
    }

    @Test
    void testDateToDaySinglesMonth() {
        Year year = calendar.getLowestYear();
        Month month = year.getMonths().get(3);
        Day day = month.getDays().get(11);
        
        assertEquals(day, calendar.dateToDay("2015/04/12"));
    }

    @Test
    void testDateToDayUpper() {
        Year year = calendar.getHighestYear();
        Month month = year.getMonths().get(11);
        Day day = month.getDays().get(30);
        
        assertEquals(day, calendar.dateToDay("2035/12/31"));
    }

    @Test
    void testDateToDayLower() {
        Year year = calendar.getLowestYear();
        Month month = year.getMonths().get(0);
        Day day = month.getDays().get(0);
        
        assertEquals(day, calendar.dateToDay("2015/01/01"));
    }

    @Test
    void testAddCategory() {
        Category category = new Category();
        List<Category> categories = new ArrayList<Category>();
        categories.add(category);

        calendar.addCategory(category);

        assertEquals(categories, calendar.getCategories());
    }

    @Test
    void testAddSubcategory() {
        List<String> emptyList = new ArrayList<String>();
        Subcategory subcategory = new Subcategory(null, 1, emptyList, "", "", emptyList, emptyList);
        List<Subcategory> subcategories = new ArrayList<Subcategory>();
        subcategories.add(subcategory);

        calendar.addSubcategory(subcategory);

        assertEquals(subcategories, calendar.getSubcategories());
    }

    @Test
    void testRemoveCategory() {
        Category category = new Category();
        List<Category> categories = new ArrayList<Category>();
        categories.add(category);
        calendar.addCategory(category);

        assertEquals(categories, calendar.getCategories());

        calendar.removeCategory(category);
        categories.remove(category);

        assertEquals(categories, calendar.getCategories());
    }

    @Test
    void testRemoveSubcategory() {
        List<String> emptyList = new ArrayList<String>();
        Subcategory subcategory = new Subcategory(null, 1, emptyList, "", "", emptyList, emptyList);
        List<Subcategory> subcategories = new ArrayList<Subcategory>();
        subcategories.add(subcategory);
        calendar.addSubcategory(subcategory);

        assertEquals(subcategories, calendar.getSubcategories());

        calendar.removeSubcategory(subcategory);
        subcategories.remove(subcategory);

        assertEquals(subcategories, calendar.getSubcategories());
    }

    @Test
    void testSetIds() {
        Category throwCategory = new Category();
        Subcategory throwSubcategory = new Subcategory("");
        @SuppressWarnings("unused")
        Event throwEvent = new Event(throwCategory, throwSubcategory, null, null, "hello", new ArrayList<Day>());
        calendar.setIds();
        assertTrue(calendar.getCategoryId() == (int)calendar.getCategoryId());
        assertTrue(calendar.getSubcategoryId() == (int)calendar.getSubcategoryId());
        assertTrue(calendar.getEventId() == (int)calendar.getEventId());
    }
    
    @Test
    void testRemoveEvent() {
        List<Day> days = new ArrayList<Day>();
        days.add(new Day(calendar.getYears().get(0), calendar.getYears().get(0).getMonths().get(0), 1));
        Event event = new Event(null, null, null, null, "hello", days);
        calendar.addEvent(event);
        assertEquals(1, calendar.getEvents().size());
        calendar.removeEvent(event);
        assertEquals(0, calendar.getEvents().size());
    }

    @Test
    void testGetEvent() {
        Event event = new Event(null, null, null, null, "Test Event", new ArrayList<>());
        calendar.addEvent(event);
        int id = event.getId();
        assertEquals(event, calendar.getEvent(id));
        assertNull(calendar.getEvent(-1));
    }

    @Test
    void testGetCategory() {
        Category category = new Category("Test Category");
        calendar.addCategory(category);
        int id = category.getId();
        assertEquals(category, calendar.getCategory(id));
        assertNull(calendar.getCategory(-1));
    }

    @Test
    void testFromOffsetWithinMonth() {
        Year lowestYear = calendar.getLowestYear();
        Month january = lowestYear.getMonths().get(0);
        Day janFirst = january.getDays().get(0);
        Day janSecond = calendar.fromOffset(janFirst, 1);
        Day expectedJanSecond = january.getDays().get(1);
        assertEquals(expectedJanSecond, janSecond);
    }

    @Test
    void testFromOffsetNextMonth() {
        Year lowestYear = calendar.getLowestYear();
        Month january = lowestYear.getMonths().get(0);
        Day janFirst = january.getDays().get(0);
        Day febFirst = calendar.fromOffset(janFirst, 31);
        Month february = lowestYear.getMonths().get(1);
        Day expectedFebFirst = february.getDays().get(0);
        assertEquals(expectedFebFirst, febFirst);
    }

    @Test
    void testFromOffsetMultipleYears() {
        Year lowestYear = calendar.getLowestYear();
        Month december = lowestYear.getMonths().get(11);
        Day decLast = december.getDays().get(december.getDays().size() - 1);
        Day nextDay = calendar.fromOffset(decLast, 1);
        Year nextYear = calendar.getYears().get(1);
        Month januaryNextYear = nextYear.getMonths().get(0);
        Day expectedDay = januaryNextYear.getDays().get(1);
        assertEquals(expectedDay, nextDay);
    }

    @Test
    void testFromOffsetNull() {
        Year highestYear = calendar.getHighestYear();
        Month december = highestYear.getMonths().get(11);
        Day decLast = december.getDays().get(december.getDays().size() - 1);
        Day nextDay = calendar.fromOffset(decLast, 1);
        assertNull(nextDay);
    }

    
}
