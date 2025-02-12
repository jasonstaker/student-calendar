package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeTest {

    Time time1;
    Time time2;
    Time time3;
    
    @BeforeEach
    void setup() {
        time1 = new Time(1, 24);
        time2 = new Time(1, 25);
        time3 = new Time(13, 1);
    }

    @Test
    void testTimeConstructor() {
        assertEquals(1, time1.getHour());
        assertEquals(24, time1.getMinute());
    }

    @Test
    void testTimeToString() {
        assertEquals("1:24 A.M.", time1.timeToString());
        assertEquals("1:01 P.M.", time3.timeToString());
    }

    @Test
    void testEqualsPass() {
        assertTrue(time1.equals(new Time(1, 24)));
    }

    @Test
    void testEqualsFail() {
        assertFalse(time1.equals(new Time(1, 25)));
    }

    @Test
    void testIsBeforePass() {
        assertTrue(time1.isBefore(new Time(1, 25)));
    }

    @Test
    void testIsBeforeFail() {
        assertFalse(time1.isBefore(time1));
    }

    @Test
    void testIsAfterPass() {
        assertTrue(time1.isAfter(new Time(1, 23)));
    }

    @Test
    void testIsAfterFail() {
        assertFalse(time1.isAfter(time1));
    }

}
