package model;

import java.util.List;

// A Subcategory with a parent category, priority level, and descriptive tags
public class Subcategory extends Category {
    
    // EFFECTS: initializes a Subcategory with default values
    public Subcategory() {

    }

    // MODIFIES: this
    // EFFECTS: adds a tag with the given name to the tags of this Subcategory
    public void addTag(String name) {

    }

    // MODIFIES: this
    // EFFECTS: removes a tag with the given name from the tags if it is present, nothing otherwise
    public void removeTag(String name) {

    }

    /*
     * GETTERS/SETTERS
     */

    public Category getParentCategory() {
        return null;
    }

    public int getPriorityLevel() {
        return -1;
    }

    public List<String> getTags() {
        return null;
    }

    public void setParentCategory(Category parentCategory) {

    }

    public void setPriorityLevel(int priorityLevel) {

    }

}
