package persistence;

import model.Calendar;
import org.json.JSONObject;


import java.io.*;

/**
 * Source: UBC CPSC 210, JsonSerializationDemo 
 * (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
 * ** MODIFIED FOR THESE PURPOSES **
 */

// Represents a writer that writes JSON representation of calendar to file
public class JsonWriter {

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of calendar to file
    public void write(Calendar calendar) {
        
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        
    }
}
