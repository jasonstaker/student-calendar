package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class YearTest {

    private Year year1;
    private Year year2;

    @BeforeEach
    void setup() {
        year1 = new Year(2025);
        year2 = new Year(2024);
    }

    @Test
    void testYearConstructor() {
        assertEquals("January", year1.getCurrentMonth().getName());
        assertEquals(12, year1.getMonths().size());
        assertEquals(2025, year1.getYearNumber());
    }

    @Test
    void testIncrementMonthIndex() {
        year1.incrementMonthIndex();
        assertEquals("February", year1.getCurrentMonth().getName());
    }

    @Test
    void testIncrementMonthIndexOver() {
        for (int i = 0; i < 18; i++) {
            year1.incrementMonthIndex();
        }
        assertEquals("December", year1.getCurrentMonth().getName());
    }

    @Test
    void testDecrementMonthIndex() {
        year1.incrementMonthIndex();
        year1.incrementMonthIndex();
        year1.decrementMonthIndex();
        assertEquals("February", year1.getCurrentMonth().getName());
    }

    @Test
    void testDecrementMonthIndexUnder() {
        for (int i = 0; i < 18; i++) {
            year1.incrementMonthIndex();
        }

        for (int i = 0; i < 18; i++) {
            year1.decrementMonthIndex();
        }
        assertEquals("January", year1.getCurrentMonth().getName());
    }

    @Test
    void testIsBeforePass() {
        assertTrue(year2.isBefore(year1));
    }

    @Test
    void testIsBeforeFailEqual() {
        assertFalse(year2.isBefore(year2));
    }

    @Test
    void testIsBeforeFailAfter() {
        assertFalse(year1.isBefore(year2));
    }

    @Test
    void testIsAfterPass() {
        assertTrue(year1.isAfter(year2));
    }

    @Test
    void testIsAfterFailEqual() {
        assertFalse(year2.isAfter(year2));
    }

    @Test
    void testIsAfterFailBefore() {
        assertFalse(year2.isAfter(year1));
    }

    @Test
    void testLeapYearCommonPass() {
        Year leapYear = new Year(2024);
        assertTrue(leapYear.isLeapYear());
    }

    @Test
    void testLeapYearCommonFail() {
        Year leapYear = new Year(2023);
        assertFalse(leapYear.isLeapYear());
    }

    @Test
    void testLeapYearDiv100() {
        Year leapYear = new Year(1900);
        assertFalse(leapYear.isLeapYear());
    }

    @Test
    void testLeapYearDiv400() {
        Year leapYear = new Year(2000);
        assertTrue(leapYear.isLeapYear());
    }

    @Test
    void testCurrentMonthSetter() {
        year1.setCurrentMonthIndex(6);
        assertEquals(6, year1.getCurrentMonth().getMonthNumber());
    }
    

}
