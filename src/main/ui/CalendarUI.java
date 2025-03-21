package ui;

import model.Calendar;
import java.awt.BorderLayout;
import javax.swing.*;

// Represents application's main window frame.
class CalendarUI extends JFrame {

    // FIELDS
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
    private CalendarController calendarController;

    // EFFECTS: Initializes the CalendarUI frame, configures panels, and displays the main window.
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

        titlePanel = new TitlePanel(calendar, this, calendar.getCurrentYear().getYearNumber());
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        categoryPanel = new JPanel();
        // TODO: Fill in category panel with appropriate components.
        categoryPanel.add(new JLabel("Category Panel"));
        mainPanel.add(categoryPanel, BorderLayout.SOUTH);

        calendarController = new CalendarController(calendar, this);
        yearPanel = new YearPanel(calendar, calendarController);
        monthPanel = new MonthPanel(calendar, calendarController);
        dayPanel = new DayPanel(calendar, calendarController);

        calendarController.setFields(titlePanel, yearPanel, monthPanel);

        contentPanel.add(yearPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        setVisible(true);
    }

    // MODIFIES: contentPanel, yearPanel
    // EFFECTS: Displays all months in the current year as buttons for the user to select
    public void displayYearSelection() {
        contentPanel.removeAll();
        yearPanel = new YearPanel(calendar, calendarController);
        contentPanel.add(yearPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    // REQUIRES: year is a valid year in calendar
    // MODIFIES: contentPanel, titlePanel, monthPanel
    // EFFECTS: Displays all days in the current month as buttons for the user to select
    public void displayMonthSelection() {
        contentPanel.removeAll();
        monthPanel = new MonthPanel(calendar, calendarController);
        contentPanel.add(monthPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // REQUIRES: year and month are valid in calendar
    // MODIFIES: contentPanel, titlePanel, dayPanel
    // EFFECTS: Displays day view for the current day in the calendar, allowing users to interact with events
    public void displayDaySelection() {
        contentPanel.removeAll();
        dayPanel = new DayPanel(calendar, calendarController);
        contentPanel.add(dayPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // EFFECTS: Displays the category/subcategory management menu via a dialog.
    public void showCategoryManagementMenu() {
        // TODO
    }
}