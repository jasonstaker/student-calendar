package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// A Calendar with a title that stores a list of years, categories, subcategories, and a current year
public class Calendar {

    // fields
    private String title;
    private List<Year> years;
    private int currentYearIndex;
    private List<Category> categories;
    private List<Subcategory> subcategories;
    private int categoryId;
    private int subcategoryId;
    private int eventId;

    // REQUIRES: currentYear corresponds to a valid year number
    // EFFECTS: initializes a new Calendar with a given title, a current year, and a list of 3 years
    public Calendar(String title, int currentYear) {
        this.title = title;
        currentYearIndex = 1;
        years = new ArrayList<Year>();
        categories = new ArrayList<Category>();
        subcategories = new ArrayList<Subcategory>();
        categoryId = 0;
        subcategoryId = 0;
        eventId = 0;
        
        for (int i = -1; i <= 1; i++) {
            years.add(new Year(currentYear + i));
        }
    }

    // MODIFIES: this
    // EFFECTS: increments the Year index if it does not exceed the highest year, nothing otherwise
    public void incrementYearIndex() {
        currentYearIndex++;
        
        if (currentYearIndex >= years.size()) {
            currentYearIndex = years.size() - 1;
        }
    }

    // MODIFIES: this
    // EFFECTS: decrements the year index if it does not go below 0, nothing otherwise
    public void decrementYearIndex() {
        currentYearIndex--;
        
        if (currentYearIndex < 0) {
            currentYearIndex = 0;
        }
    }

    // REQUIRES: years.size() > 0
    // EFFECTS: returns the year with the lowest year number in years
    public Year getLowestYear() {
        return years.get(0);
    }

    // REQUIRES: years.size() > 0
    // EFFECTS: returns the year with the highest year number in years
    public Year getHighestYear() {
        return years.get(years.size() - 1);
    }

    // REQUIRES: for date to be a valid date it must be in the YYYY/MM/DD format
    // EFFECTS: takes in a given string and determines whether it is a valid date in the calendar
    public Boolean isInCalendar(String date) {
        int year;
        int month;
        int day;
        if (date.length() != 10) {
            return false;
        }

        try {
            year = Integer.parseInt(date.substring(0, 4));
            month = Integer.parseInt(date.substring(5, 7));
            day = Integer.parseInt(date.substring(8, 10));
            if (!date.substring(4,5).equals("/")
                    || !date.substring(7,8).equals("/")) {
                throw new Exception();
            }
        } catch (Exception e) {
            return false;
        }

        for (int i = 0; i < years.size(); i++) {
            if (years.get(i).getYearNumber() == year && month >= 1 && month <= 12) {
                return (day >= 1 && day <= years.get(i).getMonths().get(month - 1).getDays().size());
            }
        }
        
        return false;
    }

    // REQUIRES: date is a valid date in YYYY/MM/DD
    // EFFECTS: returns the Day object in this Calender based on the given date
    public Day dateToDay(String date) {

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));

        Day dayObj = null;

        for (int i = 0; i < years.size(); i++) {
            if (years.get(i).getYearNumber() == year) {
                dayObj = years.get(i).getMonths().get(month - 1).getDays().get(day - 1);
            }
        }

        return dayObj;
    }

    // MODIFIES: this
    // EFFECTS: adds the given category to this Calendars list of categories
    public void addCategory(Category category) {
        categories.add(category);
    }

    // MODIFIES: this
    // EFFECTS: adds the given subcategory to this Calendars list of subcategories
    public void addSubcategory(Subcategory subcategory) {
        subcategories.add(subcategory);
    }

    // MODIFIES: this
    // EFFECTS: remove the given category from the list of categories if it is present, nothing otherwise
    public void removeCategory(Category category) {
        categories.remove(category);
    }

    // MODIFIES: this
    // EFFECTS: remove the given subcategory from the list of subcategories if it is present, nothing otherwise
    public void removeSubcategory(Subcategory subcategory) {
        subcategories.remove(subcategory);
    }

    // EFFECTS: grabs the static ids from category, subcategory, and event
    public void setIds() {
        categoryId = Category.getIdNumber();
        subcategoryId = Subcategory.getIdNumber();
        eventId = Event.getIdNumber();
    }

    /*
     * GETTERS/SETTERS
     */
    
    public String getTitle() {
        return title;
    }

    public List<Year> getYears() {
        return years;
    }

    public Year getCurrentYear() {
        return years.get(currentYearIndex);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("categoryId", categoryId);
        json.put("subcategoryId", subcategoryId);
        json.put("eventId", eventId);
        json.put("title", title);
        json.put("years", yearsToJson());
        json.put("currentYearIndex", currentYearIndex);
        json.put("categories", categoriesToJson());
        json.put("subcategories", subcategoriesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray yearsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Year year : years) {
            jsonArray.put(year.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray categoriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Category category : categories) {
            jsonArray.put(category.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray subcategoriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Subcategory subcategory : subcategories) {
            jsonArray.put(subcategory.toJson());
        }

        return jsonArray;
    }
    
}
