package persistence;

import model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkYear(int yearNumber, List<Month> months, Month currentMonth, int currentMonthIndex, Year year) {
        assertEquals(yearNumber, year.getYearNumber());
        checkMonth(currentMonth.getName(), currentMonth.getYear(), currentMonth.getMonthNumber(), 
                    currentMonth.getDays(), months.get(currentMonthIndex));
        assertEquals(currentMonthIndex, year.getCurrentMonth().getMonthNumber());

        for (int i = 0; i < 12; i++) {
            assertEquals(i, year.getMonths().get(i).getMonthNumber());
        }
    }

    protected void checkMonth(String name, Year year, int monthNumber, List<Day> days, Month month) {
        assertEquals(name, month.getName());
        assertEquals(year.getYearNumber(), month.getYear().getYearNumber());
        assertEquals(monthNumber, month.getMonthNumber());
        
        for (int i = 1; i <= days.size(); i++) {
            assertEquals(i, month.getDays().get(i - 1).getDayNumber());
        }
    }

    protected void checkDay(Year year, Month month, int dayNumber, Day day) {
        checkYear(year.getYearNumber(), year.getMonths(), year.getCurrentMonth(), 
                    year.getCurrentMonth().getMonthNumber(), day.getYear());
        checkMonth(month.getName(), month.getYear(), month.getMonthNumber(), month.getDays(), day.getMonth());
        assertEquals(dayNumber, day.getDayNumber());
    }

    protected void checkEvent(String name, Event event) {
        assertEquals(name, event.getName());
    }

    protected void checkCategory(String name, String location, List<String> links, 
                                    List<String> notes, Category category) {
        assertEquals(name, category.getName());
        assertEquals(location, category.getLocation());
        assertEquals(links, category.getLinks());
        assertEquals(notes, category.getNotes());

        for (int i = 0; i < links.size(); i++) {
            links.get(i).equals(category.getLinks().get(i));
        }

        for (int i = 0; i < notes.size(); i++) {
            notes.get(i).equals(category.getNotes().get(i));
        }
    }

    protected void checkSubcategory(Category parentCategory, int priorityLevel, 
                                        List<String> tags, Subcategory subcategory) {
        assertEquals(priorityLevel, subcategory.getPriorityLevel());
        assertEquals(tags.size(), subcategory.getTags().size());

        for (int i = 0; i < tags.size(); i++) {
            tags.get(i).equals(subcategory.getTags().get(i));
        }
    }

    protected void checkTime(int hour, int minute, Time time) {
        assertEquals(hour, time.getHour());
        assertEquals(minute, time.getMinute());
    }

}
