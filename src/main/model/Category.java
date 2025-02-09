package model;

import java.util.List;

// A Category with a name and a list of subcategories
// Also contains a location, important links, notes, and attachments that are all toggleable
public class Category {

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public Category() {

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public Category(String name) {

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public Category(String name, List<Subcategory> subcategories) {

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public void addSubcategory(Subcategory newSubcategory) {

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public void removeSubcategory(Subcategory removedSubcategory) {
        
    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public void addLink(String newUrl) {

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public void removeLink(String removedUrl) {

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public void addNote(String newNote) {

    }

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public void removeNote(String removedUrl) {

    }

    /*
     * GETTERS/SETTERS
     */

    public String getName() {
        return "";
    }

    public List<Subcategory> getSubcategories() {
        return null;
    }

    public String getLocation() {
        return "";
    }

    public List<String> getLinks() {
        return null;
    }

    public List<String> getNotes() {
        return null;
    }

    // TODO: Figure out how to get attachments to work


    public void setLocation(String location) {

    }

    public void setNote(String note) {

    }

    public void setName(String name) {

    }

}