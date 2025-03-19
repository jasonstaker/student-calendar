package ui;

import model.Calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Represents application's main window frame.
 */
class CalendarUI extends JFrame {

    // fields
    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;
    private Calendar calendar;
    private JPanel mainPanel;
    private YearPanel yearPanel;
    private MonthPanel monthPanel;
    private DayPanel dayPanel;
    private TitlePanel titlePanel;
    private JPanel contentPanel;
    private JPanel categoryPanel;

    // EFFECTS: 
    public CalendarUI(Calendar calendar) {
        this.calendar = calendar;
        setTitle("Calendar UI");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        titlePanel = new TitlePanel(calendar, calendar.getCurrentYear().getYearNumber());
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        add(mainPanel);

        setVisible(true);
    }

    // MODIFIES: TODO
    // EFFECTS: displays all available years as buttons and category selection
    public void displayYearSelection() {

    }

    // REQUIRES: year is a valid year in calendar
    // MODIFIES: TODO
    // EFFECTS: displays the months of the selected year, updating the title too
    public void displayMonthSelection(int year) {
        
    }

    // REQUIRES: year and month are valid in calendar
    // MODIFIES: TODO
    // EFFECTS: displays the days of the selected month, updating the title too
    public void displayDaySelection(int year, int month) {
        // Displays all days of the selected month
        // Updates the title to "<- Calendar Title: Month Year ->"
    }

    // REQUIRES: year, month, and day are valid in calendar
    // MODIFIES: TODO
    // EFFECTS: displays the events of the selected day, updating the title too
    public void displayDayView(int year, int month, int day) {
        
    }

    // MODIFIES: TODO
    // EFFECTS: displays the category/subcategory menu
    public void showCategoryManagementMenu() {
        
    }

    // REQUIRES: title is not null
    // MODIFIES: titleLabel
    // EFFECTS: updates the titlelabel to reflect current program state
    public void updateTitle(String title) {
        // Updates the title label in the UI
    }
    
}
