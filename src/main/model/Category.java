package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// A Category with a name and a list of subcategories
// Also contains a location, important links, notes, and attachments that are all toggleable
public class Category implements Writable {

    // fields
    private static int idNumber = 0;
    private String name;
    private List<Subcategory> subcategories;
    private String location;
    private List<String> links;
    private List<String> notes;
    private int id;

    // EFFECTS: initializes an empty Category with default values
    public Category() {
        name = "";
        subcategories = new ArrayList<Subcategory>();
        location = "";
        links = new ArrayList<String>();
        notes = new ArrayList<String>();
        id = idNumber;
        idNumber++;
    }

    // EFFECTS: initializes an empty Category with default values and a given name
    public Category(String name) {
        this.name = name;
        subcategories = new ArrayList<Subcategory>();
        location = "";
        links = new ArrayList<String>();
        notes = new ArrayList<String>();
        id = idNumber;
        idNumber++;
    }

    // EFFECTS: initializes an empty Category with default values, a given name, and
    // a given list of subcategories
    public Category(String name, List<Subcategory> subcategories,
            String location, List<String> links, List<String> notes) {
        this.name = name;
        this.subcategories = subcategories;
        this.location = location;
        this.links = links;
        this.notes = notes;
        id = idNumber;
        idNumber++;
    }

    // MODIFIES: this
    // EFFECTS: adds the given subcategory to the list of subcategories in this
    // Category
    public void addSubcategory(Subcategory newSubcategory) {
        subcategories.add(newSubcategory);
    }

    // MODIFIES: this
    // EFFECTS: removes the given subcategory from the subcategories in this
    // Category if it exists, nothing otherwise
    public void removeSubcategory(Subcategory removedSubcategory) {
        subcategories.remove(removedSubcategory);
    }

    // MODIFIES: this
    // EFFECTS: adds the given URL to the links in this Category
    public void addLink(String newUrl) {
        links.add(newUrl);
    }

    // MODIFIES: this
    // EFFECTS: removes the given URL from the links in this Category if it exists,
    // nothing otherwise
    public void removeLink(String removedUrl) {
        links.remove(removedUrl);
    }

    // REQUIRES: 0 <= index < links.size(), not used when links is empty
    // MODIFIES: this
    // EFFECTS: removes the link at index of the links in this Category
    public void removeLink(int index) {
        links.remove(index);
    }

    // MODIFIES: this
    // EFFECTS: adds the given note to the notes in this Category
    public void addNote(String newNote) {
        notes.add(newNote);
    }

    // MODIFIES: this
    // EFFECTS: removes the given note from the notes in this Category if it exists,
    // nothing otherwise
    public void removeNote(String removedNote) {
        notes.remove(removedNote);
    }

    // REQUIRES: 0 <= index < notes.size(), not used when notes is empty
    // MODIFIES: this
    // EFFECTS: removes the note at index of the notes in this Category
    public void removeNote(int index) {
        notes.remove(index);
    }

    // MODIFIES: this
    // EFFECTS: decrements the static id for Category
    public static void decrementId() {
        idNumber--;
    }

    /*
     * GETTERS/SETTERS
     */

    public String getName() {
        return name;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getLinks() {
        return links;
    }

    public List<String> getNotes() {
        return notes;
    }

    public int getId() {
        return id;
    }

    public static int getIdNumber() {
        return idNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public void setName(String name) {
        this.name = name;
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
        json.put("name", name);
        json.put("subcategories", subcategoriesToJson());
        json.put("location", location);
        json.put("links", new JSONArray(links));
        json.put("notes", new JSONArray(notes));
        return json;
    }

    public int toJsonId() {
        return id;
    }

    // EFFECTS: returns subcategories in this Category as a JSON array
    private JSONArray subcategoriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Subcategory subcategory : subcategories) {
            jsonArray.put(subcategory.getId());
        }

        return jsonArray;
    }

}