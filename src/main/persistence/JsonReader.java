package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

/**
 * Source: UBC CPSC 210, JsonSerializationDemo 
 * (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
 * ** MODIFIED FOR THESE PURPOSES **
 */

// Represents a reader that reads calendar from JSON data stored in file
public class JsonReader {

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Calendar read() throws IOException {
        return null;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return null;
    }

    // EFFECTS: parses calendar from JSON object and returns it
    private Calendar parseCalendar(JSONObject jsonObject) {
        return null;
    }

    // MODIFIES: calendar
    // EFFECTS: parses years from JSON object and adds them to calendar
    private void addYears(Calendar calendar, JSONObject jsonObject) {
        
    }

    // MODIFIES: calendar
    // EFFECTS: parses year from JSON object and adds it to calendar
    private void addYear(Calendar calendar, JSONObject jsonObject) {
        
    }

    // MODIFIES: year
    // EFFECTS: parses months from JSON object and adds them to year
    private void addMonths(Year year, JSONObject jsonObject) {

    }

    // MODIFIES: year
    // EFFECTS: parses month from JSON object and adds it to year
    private void addMonth(Year year, JSONObject jsonObject) {

    }

    // MODIFIES: month
    // EFFECTS: parses days from JSON object and adds them to month
    private void addDays(Month month, JSONObject jsonObject) {

    }

    // MODIFIES: month
    // EFFECTS: parses day from JSON object and adds it to month
    private void addDay(Month month, JSONObject jsonObject) {

    }

    // MODIFIES: calendar
    // EFFECTS: parses categories from JSON object and adds them to calendar
    private void addCategories(Calendar calendar, JSONObject jsonObject) {
        
    }

    // MODIFIES: calendar
    // EFFECTS: parses category from JSON object and adds it to calendar
    private void addCategory(Calendar calendar, JSONObject jsonObject) {
        
    }

    // MODIFIES: calendar
    // EFFECTS: parses subcategories from JSON object and adds them to calendar
    private void addSubcategories(Calendar calendar, JSONObject jsonObject) {
        
    }

    // MODIFIES: calendar
    // EFFECTS: parses subcategory from JSON object and adds it to calendar
    private void addSubcategory(Calendar calendar, JSONObject jsonObject) {
        
    }

    // MODIFIES: day
    // EFFECTS: parses events from JSON object and adds it to day
    private void addEvents(Day day, JSONObject jsonObject) {
        
    }

    // MODIFIES: day
    // EFFECTS: parses event from JSON object and adds it to day
    private void addEvent(Day day, JSONObject jsonObject) {
        
    }

    // MODIFIES: event
    // EFFECTS: parses category from JSON object and adds it to event
    private void addCategory(Event event, JSONObject jsonObject) {
        
    }

    // MODIFIES: category
    // EFFECTS: parses subcategories from JSON object and adds it to category
    private void addSubcategories(Category category, JSONObject jsonObject) {
        
    }

    // MODIFIES: category
    // EFFECTS: parses subcategory from JSON object and adds it to category
    private void addSubcategory(Category category, JSONObject jsonObject) {
        
    }

    // MODIFIES: event
    // EFFECTS: parses subcategory from JSON object and adds it to event
    private void addSubcategory(Event event, JSONObject jsonObject) {
        
    }

    // MODIFIES: event
    // EFFECTS: parses startTime from JSON object and adds it to event
    private void addStartTime(Event event, JSONObject jsonObject) {
        
    }

    // MODIFIES: event
    // EFFECTS: parses endTime from JSON object and adds it to event
    private void addEndTime(Event event, JSONObject jsonObject) {
        
    }

    // MODIFIES: event
    // EFFECTS: parses recurringDays from JSON object and adds it to event
    private void addRecurringDays(Event event, JSONObject jsonObject) {
        
    }

    // MODIFIES: event
    // EFFECTS: parses recurringDay from JSON object and adds it to event
    private void addRecurringDay(Event event, JSONObject jsonObject) {
        
    }

}
