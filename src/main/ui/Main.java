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
        Calendar calendar = new Calendar("My Calendar", LocalDate.now().getYear());

        System.out.println();

        try {
        new CalendarUI(calendar);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

}
