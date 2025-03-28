package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import persistence.Writable;

// A Calendar with a title that stores a list of years, categories, subcategories, and a current year
public class Calendar implements Writable {

    // fields
    private static final int YEAR_RANGE = 10;
    private String title;
    private List<Year> years;
    private int currentYearIndex;
    private List<Event> events;
    private List<Category> categories;
    private List<Subcategory> subcategories;
    private int categoryId;
    private int subcategoryId;
    private int eventId;

    // REQUIRES: currentYear corresponds to a valid year number
    // EFFECTS: initializes a new Calendar with a given title, a current year, and a
    // list of years from -YEAR_RANGE TO YEAR_RANGE
    public Calendar(String title, int currentYear) {
        this.title = title;
        currentYearIndex = 1;
        years = new ArrayList<Year>();
        events = new ArrayList<Event>();
        categories = new ArrayList<Category>();
        subcategories = new ArrayList<Subcategory>();
        categoryId = 0;
        subcategoryId = 0;
        eventId = 0;

        for (int i = -YEAR_RANGE; i <= YEAR_RANGE; i++) {
            years.add(new Year(currentYear + i));
        }
    }

    // MODIFIES: this
    // EFFECTS: increments the Year index if it does not exceed the highest year,
    // nothing otherwise
    public void incrementYearIndex() {
        currentYearIndex++;

        if (currentYearIndex >= years.size()) {
            currentYearIndex = years.size() - 1;
        }
    }

    // MODIFIES: this
    // EFFECTS: decrements the year index if it does not go below 0, nothing
    // otherwise
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
    // EFFECTS: takes in a given string and determines whether it is a valid date in
    // the calendar
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
            if (!date.substring(4, 5).equals("/")
                    || !date.substring(7, 8).equals("/")) {
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
    // EFFECTS: adds the given event to this Calendars list of events
    public void addEvent(Event event) {
        events.add(event);
        String dayModifier = event.getRecurringDays().size() == 1 ? "day" : "days";
        String eventDescription = "Event added to " + event.getRecurringDays().size() + " " + dayModifier
                + " in the calendar.";
        CalendarEventLog.getInstance().logEvent(new CalendarEvent(eventDescription));
    }

    // MODIFIES: this
    // EFFECTS: adds the given category to this Calendars list of categories
    public void addCategory(Category category) {
        categories.add(category);
        CalendarEventLog.getInstance().logEvent(new CalendarEvent("Category added to calendar."));
    }

    // MODIFIES: this
    // EFFECTS: adds the given subcategory to this Calendars list of subcategories
    public void addSubcategory(Subcategory subcategory) {
        subcategories.add(subcategory);
        CalendarEventLog.getInstance().logEvent(new CalendarEvent("Subcategory added to calendar."));
    }

    // MODIFIES: this
    // EFFECTS: remove the given event from the list of events if it is present,
    // nothing otherwise
    public void removeEvent(Event event) {
        events.remove(event);
        String dayModifier = event.getRecurringDays().size() == 1 ? "day" : "days";
        String eventDescription = "Event removed from " + event.getRecurringDays().size() + " " + dayModifier
                + " in the calendar.";
        CalendarEventLog.getInstance().logEvent(new CalendarEvent(eventDescription));
    }

    // MODIFIES: this
    // EFFECTS: remove the given category from the list of categories if it is
    // present, nothing otherwise
    public void removeCategory(Category category) {
        categories.remove(category);
        CalendarEventLog.getInstance().logEvent(new CalendarEvent("Category removed from calendar."));
    }

    // MODIFIES: this
    // EFFECTS: remove the given subcategory from the list of subcategories if it is
    // present, nothing otherwise
    public void removeSubcategory(Subcategory subcategory) {
        subcategories.remove(subcategory);
        CalendarEventLog.getInstance().logEvent(new CalendarEvent("Subcategory removed from calendar."));
    }

    // EFFECTS: grabs the static ids from category, subcategory, and event
    public void setIds() {
        categoryId = Category.getIdNumber();
        eventId = Event.getIdNumber();
    }

    // EFFECTS: returns the event with the given id, null if it is not in events
    public Event getEvent(int id) {
        Event event = null;

        for (Event e : events) {
            if (e.getId() == id) {
                event = e;
                break;
            }
        }

        return event;
    }

    // EFFECTS: returns the category with the given id, null if it is not in
    // category
    public Category getCategory(int id) {
        Category category = null;

        for (Category c : categories) {
            if (c.getId() == id) {
                category = c;
                break;
            }
        }

        return category;
    }

    // EFFECTS: returns the subcategory with the given id, null if it is not in
    // subcategories
    public Subcategory getSubcategory(int id) {
        Subcategory subcategory = null;

        for (Subcategory s : subcategories) {
            if (s.getId() == id) {
                subcategory = s;
                break;
            }
        }

        return subcategory;
    }

    // REQUIRES: currentDay is in the calendar, margin > 0
    // EFFECTS: returns the day that is margin days away from the current day, null
    // if no such days exists
    public Day fromOffset(Day currentDay, int margin) {
        Year currentYear = currentDay.getYear();
        Month currentMonth = currentDay.getMonth();

        while (true) {
            if (margin > currentMonth.getDays().size() - currentDay.getDayNumber()) {
                if (currentMonth.getMonthNumber() == 11) {
                    if (currentYear.equals(getHighestYear())) {
                        return null;
                    }
                    margin -= currentMonth.getDays().size() - currentDay.getDayNumber();
                    currentYear = years.get(currentYear.getYearNumber() - getLowestYear().getYearNumber() + 1);
                    currentMonth = currentYear.getMonths().get(0);
                    currentDay = currentMonth.getDays().get(0);
                } else {
                    margin -= currentMonth.getDays().size() - currentDay.getDayNumber() + 1;
                    currentMonth = currentYear.getMonths().get(currentMonth.getMonthNumber() + 1);
                    currentDay = currentMonth.getDays().get(0);
                }
            } else {
                currentDay = currentMonth.getDays().get(currentDay.getDayNumber() + margin - 1);
                break;
            }
        }

        return currentDay;
    }

    // EFFECTS: prints all CalendarEvent's in the CalendarEventLog, prints
    // "Nothing." otherwise
    public void printLog(CalendarEventLog el) {
        System.out.println("Event Log:");

        if (!el.iterator().hasNext()) {
            System.out.print("  Nothing.");
        }

        for (CalendarEvent next : el) {
            System.out.println("  " + next.toString() + "\n");
        }
    }

    /*
     * GETTERS/SETTERS
     */

    public int getYearRange() {
        return YEAR_RANGE;
    }

    public String getTitle() {
        return title;
    }

    public List<Year> getYears() {
        return years;
    }

    public Year getCurrentYear() {
        return years.get(currentYearIndex);
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setYears(List<Year> years) {
        this.years = years;
    }

    // EFFECTS: returns this calendar as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        List<Integer> yearNumbers = new ArrayList<Integer>();

        for (Year year : years) {
            yearNumbers.add(year.getYearNumber());
        }

        json.put("title", title);
        json.put("years", yearNumbers);
        json.put("eventId", eventId);
        json.put("categoryId", categoryId);
        json.put("currentYearIndex", currentYearIndex);
        json.put("events", eventsToJson());
        json.put("categories", categoriesToJson());
        json.put("subcategories", subcategoriesToJson());
        return json;
    }

    // EFFECTS: returns events in this calendar as a JSONArray
    private JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Event event : events) {
            jsonArray.put(event.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns categories in this calendar as a JSONArray
    private JSONArray categoriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Category category : categories) {
            jsonArray.put(category.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns subcategories in this calendar as a JSONArray
    private JSONArray subcategoriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Subcategory subcategory : subcategories) {
            jsonArray.put(subcategory.toJson());
        }

        return jsonArray;
    }

}
