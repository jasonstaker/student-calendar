package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

/**
 * Source: UBC CPSC 210, JsonSerializationDemo 
 * (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
 * ** MODIFIED FOR THESE PURPOSES **
 */

// Represents a reader that reads calendar from JSON data stored in file
public class JsonReader {
    private String source;
    Calendar calendar;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads calendar from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Calendar read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCalendar(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses calendar from JSON object and returns it
    private Calendar parseCalendar(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        int yearNumber = jsonObject.getJSONArray("years").getJSONObject(1).getInt("yearNumber");
        Calendar calendar = new Calendar(title, yearNumber);
        this.calendar = calendar;
        addYears(calendar, jsonObject);
        addCategories(calendar, jsonObject);
        addSubcategories(calendar, jsonObject);

        return calendar;
    }

    // MODIFIES: calendar
    // EFFECTS: parses years from JSON object and adds them to calendar
    private void addYears(Calendar calendar, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("years");
        int count = 0;
        for (Object json : jsonArray) {
            JSONObject nextYear = (JSONObject) json;
            addYear(calendar.getYears().get(count), nextYear);
            count++;
        }
    }

    // MODIFIES: calendar
    // EFFECTS: parses year from JSON object and adds it to calendar
    private void addYear(Year year, JSONObject jsonObject) {
        year.setYearNumber(jsonObject.getInt("yearNumber"));
        int currentMonthIndex = jsonObject.getInt("currentMonthIndex");
        year.setCurrentMonthIndex(currentMonthIndex);
        year.setCurrentMonth(year.getMonths().get(currentMonthIndex));
        addMonths(year, jsonObject);
    }

    // MODIFIES: year
    // EFFECTS: parses months from JSON object and adds them to year
    private void addMonths(Year year, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("months");
        int count = 0;
        for (Object json : jsonArray) {
            JSONObject nextMonth = (JSONObject) json;
            addMonth(year, year.getMonths().get(count), nextMonth);
            count++;
        }
    }

    // MODIFIES: year
    // EFFECTS: parses month from JSON object and adds it to year
    private void addMonth(Year year, Month month, JSONObject jsonObject) {
        month.setName(jsonObject.getString("name"));
        month.setMonthNumber(jsonObject.getInt("monthNumber"));
        month.setYear(year);
        addDays(year, month, jsonObject);
    }

    // MODIFIES: month
    // EFFECTS: parses days from JSON object and adds them to month
    private void addDays(Year year, Month month, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("days");
        int count = 0;
        for (Object json : jsonArray) {
            JSONObject nextDay = (JSONObject) json;
            addDay(year, month, month.getDays().get(count), nextDay);
            count++;
        }
    }

    // MODIFIES: month
    // EFFECTS: parses day from JSON object and adds it to month
    private void addDay(Year year, Month month, Day day, JSONObject jsonObject) {
        day.setYear(year);
        day.setMonth(month);
        day.setDayNumber(jsonObject.getInt("dayNumber"));
        addEvents(day, jsonObject);
    }

    // MODIFIES: calendar
    // EFFECTS: parses categories from JSON object and adds them to calendar
    private void addCategories(Calendar calendar, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("categories");
        for (Object json : jsonArray) {
            JSONObject nextCategory = (JSONObject) json;
            addCategory(calendar, nextCategory);
        }
    }

    // MODIFIES: calendar
    // EFFECTS: parses category from JSON object and adds it to calendar
    private void addCategory(Calendar calendar, JSONObject jsonObject) {
        List<Category> categories = calendar.getCategories();
        String name = jsonObject.getString("name");
        String location = jsonObject.getString("location");
        List<String> links = new ArrayList<String>();
        List<String> notes = new ArrayList<String>();

        for (int i = 0; i < jsonObject.getJSONArray("links").length(); i++) {
            links.add(jsonObject.getJSONArray("links").getString(i));
        }

        for (int i = 0; i < jsonObject.getJSONArray("notes").length(); i++) {
            notes.add(jsonObject.getJSONArray("notes").getString(i));
        }

        Category category = new Category(name, new ArrayList<Subcategory>(), location, links, notes);
        categories.add(category);
    }

    // MODIFIES: event
    // EFFECTS: parses category from JSON object and adds it to event
    private void addCategory(Event event, JSONObject jsonObject) {
        jsonObject = jsonObject.getJSONObject("category");
        String name = jsonObject.getString("name");
        String location = jsonObject.getString("location");
        List<String> links = new ArrayList<String>();
        List<String> notes = new ArrayList<String>();

        for (int i = 0; i < jsonObject.getJSONArray("links").length(); i++) {
            links.add(jsonObject.getJSONArray("links").getString(i));
        }

        for (int i = 0; i < jsonObject.getJSONArray("notes").length(); i++) {
            notes.add(jsonObject.getJSONArray("notes").getString(i));
        }

        Category category = new Category(name, new ArrayList<Subcategory>(), location, links, notes);
        
        event.setCategory(category);
    }

    // MODIFIES: calendar
    // EFFECTS: parses subcategories from JSON object and adds them to calendar
    private void addSubcategories(Calendar calendar, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("subcategories");
        for (Object json : jsonArray) {
            JSONObject nextSubcategory = (JSONObject) json;
            addSubcategory(calendar, nextSubcategory);
        }
    }

    // MODIFIES: calendar
    // EFFECTS: parses subcategory from JSON object and adds it to calendar
    private void addSubcategory(Calendar calendar, JSONObject jsonObject) {
        List<Subcategory> subcategories = calendar.getSubcategories();
        String name = jsonObject.getString("name");
        String location = jsonObject.getString("location");
        List<String> links = new ArrayList<String>();
        List<String> notes = new ArrayList<String>();
        List<String> tags = new ArrayList<String>();

        for (int i = 0; i < jsonObject.getJSONArray("links").length(); i++) {
            links.add(jsonObject.getJSONArray("links").getString(i));
        }

        for (int i = 0; i < jsonObject.getJSONArray("notes").length(); i++) {
            notes.add(jsonObject.getJSONArray("notes").getString(i));
        }

        for (int i = 0; i < jsonObject.getJSONArray("tags").length(); i++) {
            tags.add(jsonObject.getJSONArray("tags").getString(i));
        }

        Category parentCategory = jsonObject.isNull("parentCategory") ? null : 
                                    new Category(jsonObject.getJSONObject("parentCategory").getString("name"));
        int priorityLevel = jsonObject.getInt("priorityLevel");

        Subcategory subcategory = new Subcategory(parentCategory, priorityLevel, tags, name, location, links, notes);
        subcategories.add(subcategory);
    }

    // MODIFIES: event
    // EFFECTS: parses subcategory from JSON object and adds it to event
    private void addSubcategory(Event event, JSONObject jsonObject) {
        String subcategoryName = jsonObject.getJSONObject("subcategory").getString("name");
        Subcategory subcategory = new Subcategory(subcategoryName);
        event.setSubcategory(subcategory);
    }

    // MODIFIES: day
    // EFFECTS: parses events from JSON object and adds them to day
    private void addEvents(Day day, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        for (Object json : jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(day, nextEvent);
        }
    }

    // MODIFIES: day
    // EFFECTS: parses event from JSON object and adds it to day
    private void addEvent(Day day, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Event event = new Event(null, null, null, null, name, new ArrayList<Day>());
        addCategory(event, jsonObject);
        addSubcategory(event, jsonObject);
        addStartTime(event, jsonObject);
        addEndTime(event, jsonObject);
        addRecurringDays(event, jsonObject);
        day.addEvent(event);
    }

    // MODIFIES: event
    // EFFECTS: parses startTime from JSON object and adds it to event
    private void addStartTime(Event event, JSONObject jsonObject) {
        JSONObject startTimeJson = jsonObject.getJSONObject("startTime");
        Time startTime = new Time(startTimeJson.getInt("hour"), startTimeJson.getInt("minute"));
        event.setStartTime(startTime);
    }

    // MODIFIES: event
    // EFFECTS: parses endTime from JSON object and adds it to event
    private void addEndTime(Event event, JSONObject jsonObject) {
        JSONObject endTimeJson = jsonObject.getJSONObject("endTime");
        Time endTime = new Time(endTimeJson.getInt("hour"), endTimeJson.getInt("minute"));
        event.setStartTime(endTime);
    }

    // MODIFIES: event
    // EFFECTS: parses recurringDays from JSON object and adds it to event
    private void addRecurringDays(Event event, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("recurringDays");
        for (int i = 0; i < jsonArray.length(); i++) {
            addRecurringDay(event, jsonArray.getJSONObject(i));
        }
    }

    // MODIFIES: event
    // EFFECTS: parses recurringDay from JSON object and adds it to event
    private void addRecurringDay(Event event, JSONObject jsonObject) {
        int yearNumber = jsonObject.getInt("yearNumber");
        int monthNumber = jsonObject.getInt("monthNumber");
        int dayNumber = jsonObject.getInt("dayNumber");

        Year year = calendar.getYears().get(yearNumber - calendar.getLowestYear().getYearNumber());

        event.addRecurringDay(year.getMonths().get(monthNumber).getDays().get(dayNumber - 1));
    }


}
