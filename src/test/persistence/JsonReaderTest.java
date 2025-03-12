package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Calendar calendar = reader.read();
            calendar.getClass(); // prevents error
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCalendar() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCalendar.json");
        try {
            Calendar calendar = reader.read();
            assertEquals("title", calendar.getTitle());
            assertEquals(0, calendar.getCategories().size());
            assertEquals(0, calendar.getSubcategories().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyYear() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCalendar.json");
        try {
            Year year = reader.read().getYears().get(0);
            checkYear(2024, year.getMonths(), year.getMonths().get(0), 0, year);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyMonth() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCalendar.json");
        try {
            Month month = reader.read().getYears().get(0).getMonths().get(0);
            checkMonth("January", month.getYear(), 0, month.getDays(), month);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyDay() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCalendar.json");
        try {
            Day day = reader.read().getYears().get(0).getMonths().get(0).getDays().get(0);
            checkDay(reader.read().getYears().get(0), reader.read().getYears().get(0).getCurrentMonth(), 1, day);
            assertEquals(0, day.getEvents().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCalendar() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCalendar.json");
        try {
            Calendar calendar = reader.read();
            assertEquals("title", calendar.getTitle());
            List<String> links = new ArrayList<String>();
            List<String> notes = new ArrayList<String>();
            List<String> tags = new ArrayList<String>();
            links.add("link");
            notes.add("note");
            tags.add("tag");
            checkCategory("category", "", links, notes, calendar.getCategories().get(0));
            checkSubcategory(calendar.getCategories().get(0), 1, tags, calendar.getSubcategories().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralYear() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCalendar.json");
        try {
            Year year = reader.read().getYears().get(1);
            checkYear(2025, year.getMonths(), year.getMonths().get(0), 0, year);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMonth() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCalendar.json");
        try {
            Month month = reader.read().getYears().get(0).getMonths().get(0);
            checkMonth("January", month.getYear(), 0, month.getDays(), month);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDay() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCalendar.json");
        try {
            Day day = reader.read().getYears().get(0).getMonths().get(0).getDays().get(0);
            Event event = day.getEvents().get(0);
            List<String> links = new ArrayList<String>();
            List<String> notes = new ArrayList<String>();
            List<String> tags = new ArrayList<String>();
            links.add("link");
            notes.add("note");
            checkDay(reader.read().getYears().get(0), reader.read().getYears().get(0).getCurrentMonth(), 1, day);
            checkEvent("event", event);
            checkCategory("category", "", links, notes, event.getCategory());
            checkSubcategory(event.getCategory(), 1, tags, event.getSubcategory());
            checkTime(12, 0, event.getStartTime());
            checkTime(13, 0, event.getEndTime());
            checkDay(day.getYear(), day.getMonth(), 1, day.getEvents().get(0).getRecurringDays().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}