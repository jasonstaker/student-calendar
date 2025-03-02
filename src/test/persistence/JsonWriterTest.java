package persistence;

import model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Calendar calendar = new Calendar("title", 2025);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCalendar() {
        try {
            Calendar calendar = new Calendar("title", 2025);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(calendar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalendar.json");
            calendar = reader.read();
            assertEquals("title", calendar.getTitle());
            assertEquals(0, calendar.getCategories().size());
            assertEquals(0, calendar.getSubcategories().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyYear() {
        try {
            Calendar calendar = new Calendar("title", 2025);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(calendar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalendar.json");
            Year year = reader.read().getYears().get(0);
            checkYear(2024, year.getMonths(), year.getMonths().get(0), 0, year);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyMonth() {
        try {
            Calendar calendar = new Calendar("title", 2025);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(calendar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalendar.json");
            Month month = reader.read().getYears().get(0).getMonths().get(0);
            checkMonth("January", month.getYear(), 0, month.getDays(), month);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyDay() {
        try {
            Calendar calendar = new Calendar("title", 2025);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(calendar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalendar.json");
            Day day = reader.read().getYears().get(0).getMonths().get(0).getDays().get(0);
            checkDay(reader.read().getYears().get(0), reader.read().getYears().get(0).getCurrentMonth(), 1, day);
            assertEquals(0, day.getEvents().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCalendar() {
        try {
            Calendar calendar = makeGeneralCalendar();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(calendar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalendar.json");
            calendar = reader.read();
            assertEquals("title", calendar.getTitle());
            List<String> links = new ArrayList<String>();
            List<String> notes = new ArrayList<String>();
            List<String> tags = new ArrayList<String>();
            links.add("link");
            notes.add("note");
            tags.add("tag1");
            tags.add("tag2");
            checkCategory("category", "location", links, notes, calendar.getCategories().get(0));
            checkSubcategory(null, 1, tags, calendar.getSubcategories().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralYear() {
        try {
            Calendar calendar = makeGeneralCalendar();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(calendar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalendar.json");
            calendar = reader.read();
            Year year = calendar.getYears().get(0);
            checkYear(2025, year.getMonths(), year.getMonths().get(0), 0, year);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMonth() {
        try {
            Calendar calendar = makeGeneralCalendar();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(calendar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalendar.json");
            calendar = reader.read();
            Month month = calendar.getYears().get(0).getMonths().get(0);
            checkMonth("January", month.getYear(), 0, month.getDays(), month);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDay() {
        try {
            Calendar calendar = makeGeneralCalendar();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(calendar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalendar.json");
            calendar = reader.read();
            Day day = calendar.getYears().get(0).getMonths().get(0).getDays().get(0);
            Event event = day.getEvents().get(0);
            List<String> links = new ArrayList<String>();
            List<String> notes = new ArrayList<String>();
            List<String> tags = new ArrayList<String>();
            links.add("link");
            notes.add("note");
            tags.add("tag1");
            tags.add("tag2");
            checkDay(calendar.getYears().get(0), calendar.getYears().get(0).getCurrentMonth(), 1, day);
            checkEvent("event", event);
            checkCategory("category", "location", links, notes, event.getCategory());
            checkSubcategory(null, 1, tags, event.getSubcategory());
            checkTime(12, 44, event.getStartTime());
            checkTime(13, 30, event.getEndTime());
            checkDay(day.getYear(), day.getMonth(), 1, day.getEvents().get(0).getRecurringDays().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // EFFECTS: makes the general calendar used for testing
    private Calendar makeGeneralCalendar() {
        Calendar calendar = new Calendar("calendar", 2025);
        List<String> links = new ArrayList<String>();
        List<String> notes = new ArrayList<String>();
        List<String> tags = new ArrayList<String>();
        List<Day> recurringDays = new ArrayList<Day>();
        links.add("link");
        notes.add("note");
        tags.add("tag1");
        tags.add("tag2");

        Category category = new Category("category", null, "location", links, notes);
        Subcategory subcategory = new Subcategory(null, 1, tags, "subcategory", "location", links, notes);
        calendar.addCategory(category);
        calendar.addSubcategory(subcategory);
        Day day = calendar.getYears().get(0).getMonths().get(0).getDays().get(0);
        recurringDays.add(day);
        day.addEvent(new Event(category, subcategory, new Time(12, 44), new Time(13, 30), "event", recurringDays));

        return calendar;
    }
}