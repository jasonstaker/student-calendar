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

    // fields
    private String source;
    private Calendar calendar;

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
        int yearNumber = (int)jsonObject.getJSONArray("years").get(1);
        Calendar calendar = new Calendar(title, yearNumber);
        this.calendar = calendar;
        Category.setIdNumber(jsonObject.getInt("categoryId"));
        Event.setIdNumber(jsonObject.getInt("eventId"));
        addCategories(jsonObject);
        for (Subcategory subcategory : addSubcategories(jsonObject)) {
            calendar.addSubcategory(subcategory);
        }
        addYears(jsonObject);
        addEvents(jsonObject);

        return calendar;
    }

    // MODIFIES: calendar
    // EFFECTS: parses years from JSON object and adds them to calendar
    private void addYears(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("years");
        List<Year> years = new ArrayList<Year>();

        for (Object year : jsonArray) {
            years.add(new Year((int)year));
        }

        calendar.setYears(years);
    }

    // EFFECTS: parses events from JSON object and adds them to both calendar and their recurring days
    private void addEvents(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject event = (JSONObject) jsonArray.get(i);
            parseEvent(event);
        }
    }

    // MODIFIES: day
    // EFFECTS: parses event from JSON object and returns it
    private void parseEvent(JSONObject jsonObject) {
        Category category = jsonObject.isNull("category") 
                                    ? null 
                                    : calendar.getCategory(jsonObject.getInt("category"));
        Subcategory subcategory = jsonObject.isNull("subcategory") 
                                    ? null 
                                    : calendar.getSubcategory(jsonObject.getInt("subcategory"));
        Time startTime = parseTime(jsonObject.getJSONObject("startTime"));
        Time endTime = parseTime(jsonObject.getJSONObject("endTime"));
        String name = jsonObject.getString("name");
        int id = jsonObject.getInt("id");
        List<Day> recurringDays = new ArrayList<Day>();

        for (Object json : jsonObject.getJSONArray("recurringDays")) {
            recurringDays.add(parseRecurringDay((JSONObject) json));
        }

        Event event = new Event(category, subcategory, startTime, endTime, name, recurringDays);
        Event.decrementId();
        event.setId(id);
    }

    // MODIFIES: calendar
    // EFFECTS: parses categories from JSON object and adds them to calendar
    private void addCategories(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("categories");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject category = (JSONObject) jsonArray.get(i);
            calendar.addCategory(parseCategory(category));
        }
    }

    // EFFECTS: parses category from JSON object and returns it
    private Category parseCategory(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        List<Subcategory> subcategories = new ArrayList<Subcategory>();
        for (Object jsonInt : jsonObject.getJSONArray("subcategories")) {
            subcategories.add(calendar.getSubcategory((int)jsonInt));
        }
        String location = jsonObject.getString("location");
        int id = jsonObject.getInt("id");
        List<String> links = new ArrayList<String>();
        List<String> notes = new ArrayList<String>();

        jsonObject.getJSONArray("links").forEach(item -> links.add((String) item));
        jsonObject.getJSONArray("notes").forEach(item -> notes.add((String) item));

        Category category = new Category(name, subcategories, location, links, notes);
        category.setId(id);
        Category.decrementId();
        
        return category;
    }

    // EFFECTS: parses subcategories from JSON object and returns the list
    private List<Subcategory> addSubcategories(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("subcategories");
        List<Subcategory> subcategories = new ArrayList<Subcategory>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject subcategory = (JSONObject) jsonArray.get(i);
            subcategories.add(parseSubcategory(subcategory));
        }

        return subcategories;
    }

    // EFFECTS: parses subcategory from JSON object and returns it
    private Subcategory parseSubcategory(JSONObject jsonObject) {
        Category parentCategory = jsonObject.isNull("parentCategory") 
                                    ? null 
                                    : calendar.getCategory(jsonObject.getInt("parentCategory"));
        int priorityLevel = jsonObject.getInt("priorityLevel");
        String name = jsonObject.getString("name");
        String location = jsonObject.getString("location");
        int id = jsonObject.getInt("id");
        List<String> links = new ArrayList<String>();
        List<String> notes = new ArrayList<String>();
        List<String> tags = new ArrayList<String>();

        jsonObject.getJSONArray("links").forEach(item -> links.add((String) item));
        jsonObject.getJSONArray("notes").forEach(item -> notes.add((String) item));
        jsonObject.getJSONArray("tags").forEach(item -> tags.add((String) item));

        Subcategory subcategory = new Subcategory(parentCategory, priorityLevel, tags, name, location, links, notes);
        subcategory.setId(id);
        Subcategory.decrementId();
        
        return subcategory;
    }

    // EFFECTS: parses time from JSON object and returns it
    private Time parseTime(JSONObject jsonObject) {
        return new Time(jsonObject.getInt("hour"), jsonObject.getInt("minute"));
    }

    // EFFECTS: parses recurringDay from JSON object and returns it
    private Day parseRecurringDay(JSONObject jsonObject) {
        int yearNumber = jsonObject.getInt("yearNumber");
        int monthNumber = jsonObject.getInt("monthNumber");
        int dayNumber = jsonObject.getInt("dayNumber");
        Year year =  calendar.getYears().get(yearNumber - calendar.getLowestYear().getYearNumber());
        
        return year.getMonths().get(monthNumber).getDays().get(dayNumber - 1);
    }


}
