package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

// A Subcategory with a parent category, priority level, and descriptive tags
public class Subcategory extends Category {

    private static int idNumber = 0;
    private Category parentCategory;
    private int priorityLevel;
    private List<String> tags;
    private int id;
    
    // REQUIRES: priorityLevel is 1, 2, or 3, links and notes cannot be null
    // EFFECTS: initializes a Subcategory with given category, priority level, tags, name, location, links and notes
    public Subcategory(Category parentCategory, int priorityLevel, List<String> tags, 
                        String name, String location, List<String> links, List<String> notes) {
        super();
        this.setName(name);
        for (String link: links) {
            this.addLink(link);
        }
        for (String note: notes) {
            this.addNote(note);
        }
        this.setLocation(location);
        
        this.parentCategory = parentCategory;
        this.priorityLevel = priorityLevel;
        this.tags = tags;
        id = idNumber;
        idNumber++;
    }

    // EFFECTS: initializes a Subcategory with default values
    public Subcategory(String name) {
        super(name);
        priorityLevel = 1;
        tags = new ArrayList<String>();
    }

    // MODIFIES: this
    // EFFECTS: adds a tag with the given name to the tags of this Subcategory if it is not present, nothing otherwise
    public void addTag(String name) {
        if (!tags.contains(name)) {
            tags.add(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a tag with the given name from the tags if it is present, nothing otherwise
    public void removeTag(String name) {
        tags.remove(name);
    }

    // EFFECTS: returns the priority level of the Subcategory as a string
    //          1: "Low"
    //          2: "Medium"
    //          3; "High"
    public String priorityLevelToString() {
        if (priorityLevel == 1) {
            return "Low";
        } else if (priorityLevel == 2) {
            return "Medium";
        } else {
            return "High";
        }
    }

    // EFFECTS: returns true if the given Category is the parent category of this Subcategory
    public boolean isParent(Category category) {
        return this.parentCategory.equals(category);
    }

    /*
     * GETTERS/SETTERS
     */

    public Category getParentCategory() {
        return parentCategory;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getId() {
        return id;
    }

    public static int getIdNumber() {
        return idNumber;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setIdNumber(int newIdNumber) {
        idNumber = newIdNumber;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", this.getName());
        json.put("location", this.getLocation());
        json.put("links", new JSONArray(this.getLinks()));
        json.put("notes", new JSONArray(this.getNotes()));
        json.put("parentCategory", parentCategory == null ? null : parentCategory.toJson());
        json.put("priorityLevel", priorityLevel);
        json.put("tags", new JSONArray(tags));
        return json;
    }

}
