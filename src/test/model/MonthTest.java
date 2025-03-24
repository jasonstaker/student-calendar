package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MonthTest {
    
    private Month month1;
    private Month month2;
    private Month month3;

    private Year year;

    @BeforeEach
    void setup() {
        year = new Year(2025);
        month1 = new Month(year, 4);
        month2 = new Month(year, 5);
        month3 = new Month(year, 6);
    }

    @Test
    void testMonthConstructor() {
        assertEquals(4, month1.getMonthNumber());
        assertEquals(31, month1.getDays().size());
        assertEquals("May", month1.getName());
        assertEquals(year, month1.getYear());
    }

    @Test
    void testHasDayPass() {
        assertTrue(month1.hasDay(month1.getDays().get(0)));
    }

    @Test
    void testHasDayFail() {
        assertFalse(month1.hasDay(month2.getDays().get(0)));
    }

    @Test
    void testIsBeforeBefore() {
        assertTrue(month1.isBefore(month2));
    }

    @Test
    void testIsBeforeEqual() {
        assertFalse(month2.isBefore(month2));
    }

    @Test
    void testIsBeforeAfter() {
        assertFalse(month3.isBefore(month2));
    }

    @Test
    void getLowestDay() {
        assertEquals(month1.getDays().get(0), month1.getLowestDay());
    }

    @Test
    void getHighestDay() {
        assertEquals(month1.getDays().get(30), month1.getHighestDay());
    }

    @Test
    void testLeapYear() {
        Month leapMonth = new Month(new Year(2000), 1);
        assertEquals(29, leapMonth.getDays().size());
    }

    @Test
    void testIncrementDayIndex() {
        int origIndex = 0;
        assertEquals(month1.getDays().get(origIndex), month1.getCurrentDay());
        month1.incrementDayIndex();
        assertEquals(month1.getDays().get(origIndex + 1), month1.getCurrentDay());
    }

    @Test
    void testIncrementDayIndexAtEnd() {
        int lastIndex = month1.getDays().size() - 1;
        month1.setCurrentDayIndex(lastIndex);
        Day current = month1.getCurrentDay();
        month1.incrementDayIndex();
        assertEquals(current, month1.getCurrentDay());
    }

    @Test
    void testDecrementDayIndex() {
        month1.setCurrentDayIndex(2);
        month1.decrementDayIndex();
        assertEquals(month1.getDays().get(1), month1.getCurrentDay());
    }

    @Test
    void testDecrementDayIndexAtZero() {
        month1.setCurrentDayIndex(0);
        Day current = month1.getCurrentDay();
        month1.decrementDayIndex();
        assertEquals(current, month1.getCurrentDay());
    }

    @Test
    void testSetters() {
        month1.setName("Test Month");
        assertEquals("Test Month", month1.getName());
        month1.setMonthNumber(7);
        assertEquals(7, month1.getMonthNumber());
        Year newYear = new Year(2030);
        month1.setYear(newYear);
        assertEquals(newYear, month1.getYear());
        month1.setCurrentDayIndex(5);
        assertEquals(month1.getDays().get(5), month1.getCurrentDay());
    }


}
