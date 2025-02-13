package model;

import java.util.List;

// A Subcategory with a parent category, priority level, and descriptive tags
public class Subcategory extends Category {

    private Category parentCategory;
    private int priorityLevel;
    private List<String> tags;
    
    // REQUIRES: priorityLevel is 1, 2, or 3
    // EFFECTS: initializes a Subcategory with default values
    public Subcategory(Category parentCategory, int priorityLevel, List<String> tags) {
        this.parentCategory = parentCategory;
        this.priorityLevel = priorityLevel;
        this.tags = tags;
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

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

}
