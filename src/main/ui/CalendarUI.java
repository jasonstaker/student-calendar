package ui;

import model.Calendar;

import java.awt.BorderLayout;

import javax.swing.*;

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
    private CalendarController calendarController;

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

        titlePanel = new TitlePanel(calendar, this, calendar.getCurrentYear().getYearNumber());
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        categoryPanel = new JPanel();
        // TODO
        // Filling in category panel with a basic label as a placeholder.
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

    // MODIFIES: TODO
    // EFFECTS: displays all available years as buttons and category selection
    public void displayYearSelection() {
        contentPanel.removeAll();
        yearPanel = new YearPanel(calendar, calendarController);
        contentPanel.add(yearPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    // REQUIRES: year is a valid year in calendar
    // MODIFIES: TODO
    // EFFECTS: displays the months of the selected year, updating the title too
    public void displayMonthSelection() {
        contentPanel.removeAll();
        monthPanel = new MonthPanel(calendar, calendarController);
        contentPanel.add(monthPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // REQUIRES: year and month are valid in calendar
    // MODIFIES: TODO
    // EFFECTS: displays the days of the selected month, updating the title too
    public void displayDaySelection() {
        contentPanel.removeAll();
        dayPanel = new DayPanel(calendar, calendarController);
        contentPanel.add(dayPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // REQUIRES: year, month, and day are valid in calendar
    // MODIFIES: TODO
    // EFFECTS: displays the events of the selected day, updating the title too
    public void displayDayView(int year, int month, int day) {
        // Placeholder: display a dialog with the selected day details.
        JOptionPane.showMessageDialog(this, "Displaying events for " + day + "/" + month + "/" + year);
    }

    // MODIFIES: TODO
    // EFFECTS: displays the category/subcategory menu
    public void showCategoryManagementMenu() {
        // Placeholder: display a dialog for category management.
        JOptionPane.showMessageDialog(this, "Category management menu");
    }
}
