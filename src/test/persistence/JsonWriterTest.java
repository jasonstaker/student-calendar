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

    private Calendar calendar;
    private JsonReader reader;
    private JsonWriter writer;

    @Test
    void testWriterInvalidFile() {
        try {
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
            writeEmptyCalendar();

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
            writeEmptyCalendar();

            Year year = reader.read().getYears().get(0);
            checkYear(2015, year.getMonths(), year.getMonths().get(0), 0, year);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyMonth() {
        try {
            writeEmptyCalendar();

            Month month = reader.read().getYears().get(0).getMonths().get(0);
            checkMonth("January", month.getYear(), 0, month.getDays(), month);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyDay() {
        try {
            writeEmptyCalendar();

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
            writeGeneralCalendar();

            assertEquals("calendar", calendar.getTitle());
            List<String> links = new ArrayList<String>();
            List<String> notes = new ArrayList<String>();
            List<String> tags = new ArrayList<String>();
            links.add("link");
            notes.add("note");
            tags.add("tag");
            checkCategory("category", "", links, notes, calendar.getCategories().get(0));
            checkSubcategory(calendar.getCategories().get(0), 1, tags, calendar.getSubcategories().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralYear() {
        try {
            writeGeneralCalendar();
            
            Year year = calendar.getYears().get(1);
            checkYear(2016, year.getMonths(), year.getMonths().get(0), 0, year);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMonth() {
        try {
            writeGeneralCalendar();
            
            Month month = calendar.getYears().get(0).getMonths().get(0);
            checkMonth("January", month.getYear(), 0, month.getDays(), month);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDay() {
        try {
            writeGeneralCalendar();
            
            Day day = calendar.getYears().get(0).getMonths().get(0).getDays().get(0);
            Event event = day.getEvents().get(0);
            List<String> links = new ArrayList<String>();
            List<String> notes = new ArrayList<String>();
            List<String> tags = new ArrayList<String>();
            links.add("link");
            notes.add("note");
            checkDay(calendar.getYears().get(0), calendar.getYears().get(0).getCurrentMonth(), 1, day);
            checkEvent("event", event);
            checkCategory("category", "", links, notes, event.getCategory());
            checkSubcategory(event.getCategory(), 1, tags, event.getSubcategory());
            checkTime(12, 00, event.getStartTime());
            checkTime(13, 00, event.getEndTime());
            checkDay(day.getYear(), day.getMonth(), 1, day.getEvents().get(0).getRecurringDays().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // EFFECTS: makes the general calendar used for testing
    private Calendar makeGeneralCalendar() {
        Calendar calendar = new Calendar("calendar", 2025);
        List<String> notes = new ArrayList<String>();
        List<String> links = new ArrayList<String>();
        List<String> tags = new ArrayList<String>();

        notes.add("note");
        links.add("link");
        tags.add("tag");

        Category category = new Category("category", new ArrayList<Subcategory>(), "", links, notes);
        Subcategory subcategory1 = new Subcategory(category, 1, tags, "first", "", links, notes);
        Subcategory subcategory2 = new Subcategory("second");

        subcategory1.addSubcategory(subcategory2);
        category.addSubcategory(subcategory1);
        category.addSubcategory(subcategory2);

        List<Day> recurringDays = new ArrayList<Day>();
        Day day = calendar.getYears().get(0).getMonths().get(0).getDays().get(0);

        recurringDays.add(day);

        Event event = new Event(category, subcategory2, new Time(12, 0), new Time(13, 0), "event", recurringDays);
        calendar.addCategory(category);
        calendar.addSubcategory(subcategory1);
        calendar.addSubcategory(subcategory2);
        calendar.addEvent(event);

        return calendar;
    }

    private void writeEmptyCalendar() throws IOException {
        calendar = new Calendar("title", 2025);
        writer = new JsonWriter("./data/testWriterEmptyCalendar.json");
        writer.open();
        writer.write(calendar);
        writer.close();

        reader = new JsonReader("./data/testWriterEmptyCalendar.json");
        calendar = reader.read();
    }

    private void writeGeneralCalendar() throws IOException {
        calendar = makeGeneralCalendar();
        writer = new JsonWriter("./data/testWriterGeneralCalendar.json");
        writer.open();
        writer.write(calendar);
        writer.close();

        reader = new JsonReader("./data/testWriterGeneralCalendar.json");
        calendar = reader.read();
    }
}