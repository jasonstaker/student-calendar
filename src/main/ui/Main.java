package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

// initializes the Calendar and begins the UI
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter calendar title: ");

        String calendarTitle = scanner.nextLine();
        Calendar calendar = new Calendar(calendarTitle, LocalDate.now().getYear());

        System.out.println();

        JsonWriter jsonWriter = new JsonWriter("./data/test.json");
        JsonReader jsonReader = new JsonReader("./data/test.json");

        List<String> notes = new ArrayList<String>();
        notes.add("note");
        List<String> links = new ArrayList<String>();
        links.add("link");
        List<String> tags = new ArrayList<String>();
        tags.add("tag");
        
        Category category = new Category("Category", new ArrayList<Subcategory>(), calendarTitle, links, notes);
        Subcategory subcategory1 = new Subcategory(category, 1, tags, "first", "", links, notes);
        Subcategory subcategory2 = new Subcategory("second");
        subcategory1.addSubcategory(subcategory2);
        category.addSubcategory(subcategory1);
        category.addSubcategory(subcategory2);
        Day day = calendar.getYears().get(0).getMonths().get(0).getDays().get(0);
        List<Day> days = new ArrayList<Day>();
        days.add(day);
        Event event = new Event(category, subcategory2, new Time(12, 0), new Time(13, 0), "event", days);
        calendar.addCategory(category);
        calendar.addSubcategory(subcategory1);
        calendar.addSubcategory(subcategory2);
        calendar.addEvent(event);

        try {
            calendar.setIds();
            jsonWriter.open();
            jsonWriter.write(calendar);
            jsonWriter.close();
            System.out.println("Saved " + calendar.getTitle() + " to ./data/test.json");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: ./data/test.json");
        }

        try {
            calendar = jsonReader.read();
            System.out.println("Loaded " + calendar.getTitle() + " from ./data/test.json");
        } catch (IOException e) {
            System.out.println("Unable to read from file: ./data/test.json");
        }

        scanner.close();
    }

}
