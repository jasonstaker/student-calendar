package model;

import java.util.List;

// A Subcategory with a parent category, priority level, and descriptive tags
public class Subcategory extends Category {
    
    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public Subcategory() {

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public void addTag(String name) {

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
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
