package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeTest {

    private Time time1;
    private Time time2;
    private Time time3;
    
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
        assertEquals("12:24 P.M.", (new Time(12, 24)).timeToString());
        assertEquals("12:24 A.M.", (new Time(0, 24)).timeToString());
        assertEquals("1:01 P.M.", time3.timeToString());
    }

    @Test
    void testEqualsPass() {
        assertTrue(time1.equals(time1));
    }

    @Test
    void testEqualsFailBefore() {
        assertFalse(time1.equals(time2));
    }

    @Test
    void testEqualsFailAfter() {
        assertFalse(time3.equals(time2));
    }

    @Test
    void testIsBeforePass() {
        assertTrue(time1.isBefore(time2));
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
    void testIsAfterPassHour() {
        assertTrue((new Time(2, 22)).isAfter(new Time(1, 23)));
    }

    @Test
    void testIsAfterFailHour() {
        assertFalse((new Time(1, 22)).isAfter(new Time(2, 23)));
    }

    @Test
    void testIsAfterFail() {
        assertFalse(time1.isAfter(time1));
    }

    @Test
    void testIsValidTimePass() {
        assertTrue(time1.isValidTime("04:55"));
    }

    @Test
    void testIsValidTimeFailHourLower() {
        assertFalse(time1.isValidTime("-1:55"));
    }

    @Test
    void testIsValidTimeFailHourUpper() {
        assertFalse(time1.isValidTime("24:55"));
    }

    @Test
    void testIsValidTimeFailMinuteLower() {
        assertFalse(time1.isValidTime("12:-1"));
    }

    @Test
    void testIsValidTimeFailMinuteUpper() {
        assertFalse(time1.isValidTime("12:61"));
    }

    @Test
    void testIsValidTimeFailColon() {
        assertFalse(time1.isValidTime("12 61"));
    }

    @Test
    void testIsValidTimeFailString() {
        assertFalse(time1.isValidTime("1d:61"));
    }

    @Test
    void testIsValidTimeFailWord() {
        assertFalse(time1.isValidTime("fail :("));
    }

    @Test
    void testStringToTime() {
        Time time = time1.stringToTime("04:06");
        
        assertEquals(4, time.getHour());
        assertEquals(6, time.getMinute());
    }

}
