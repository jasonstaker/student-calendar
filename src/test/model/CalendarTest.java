package model;

import static org.junit.Assert.assertEquals;
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
        assertEquals(2024, calendar.getYears().get(0).getYearNumber());
        assertEquals(2025, calendar.getYears().get(1).getYearNumber());
        assertEquals(2026, calendar.getYears().get(2).getYearNumber());
        assertEquals(calendar.getYears().get(1), calendar.getCurrentYear());
        assertEquals(categories, calendar.getCategories());
        assertEquals(subcategories, calendar.getSubcategories());
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
    
    @Test
    void testGetLowestYear() {
        assertEquals(new Year(2024), calendar.getLowestYear());
    }

    @Test
    void testGetHighestYear() {
        assertEquals(new Year(2026), calendar.getLowestYear());
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
    void testIsInCalendarFail() {
        assertFalse(calendar.isInCalendar("2023/01/01"));
    }

    @Test
    void testIsInCalendarFailSlash() {
        assertFalse(calendar.isInCalendar("2024 01 01"));
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
        Month month = year.getMonths().get(12);
        Day day = new Day(year, month, 5);

        assertEquals(day, calendar.dateToDay("2024/12/05"));
    }

    @Test
    void testDateToDaySinglesMonth() {
        Year year = calendar.getLowestYear();
        Month month = year.getMonths().get(4);
        Day day = new Day(year, month, 12);
        
        assertEquals(day, calendar.dateToDay("2024/04/12"));
    }

    @Test
    void testDateToDayUpper() {
        Year year = calendar.getHighestYear();
        Month month = year.getMonths().get(11);
        Day day = new Day(year, month, 31);
        
        assertEquals(day, calendar.dateToDay("2026/12/31"));
    }

    @Test
    void testDateToDayLower() {
        Year year = calendar.getLowestYear();
        Month month = year.getMonths().get(0);
        Day day = new Day(year, month, 1);
        
        assertEquals(day, calendar.dateToDay("2024/01/01"));
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
        Subcategory subcategory = new Subcategory(null, 1, null, null, null, null, null);
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
        Subcategory subcategory = new Subcategory(null, 1, null, null, null, null, null);
        List<Subcategory> subcategories = new ArrayList<Subcategory>();
        subcategories.add(subcategory);
        calendar.addSubcategory(subcategory);

        assertEquals(subcategories, calendar.getSubcategories());

        calendar.removeSubcategory(subcategory);
        subcategories.remove(subcategory);

        assertEquals(subcategories, calendar.getSubcategories());
    }
    
}
