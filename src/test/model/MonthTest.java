package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MonthTest {
    
    Month month1;
    Month month2;
    Month month3;

    @BeforeEach
    void setup() {
        month1 = new Month(4);
        month2 = new Month(5);
        month3 = new Month(6);
    }

    @Test
    void testMonthConstructor() {
        assertEquals(4, month1.getMonthNumber());
        assertEquals(31, month1.getDays().size());
        assertEquals("May", month1.getName());
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
}
