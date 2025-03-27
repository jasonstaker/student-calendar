package ui;

import model.Calendar;

// A CalendarController that connects the Calendar model with its UI components.
public class CalendarController {

    // fields
    private Calendar calendar;
    private CalendarUI calendarUI;
    private TitlePanel titlePanel;
    private YearPanel yearPanel;
    private MonthPanel monthPanel;
    private DayPanel dayPanel;
    private int depth;

    // EFFECTS: initializes a new CalendarController with the given calendar and
    // calendarUI
    public CalendarController(Calendar calendar, CalendarUI calendarUI) {
        this.calendar = calendar;
        this.calendarUI = calendarUI;
        depth = 0;
    }

    // MODIFIES: this
    // EFFECTS: updates all views by updating the title panel
    public void updateTitlePanel() {
        titlePanel.updateTitle();
    }

    // MODIFIES: this
    // EFFECTS: sets the UI panels for title, year, and month views
    public void setFields(TitlePanel titlePanel, YearPanel yearPanel, MonthPanel monthPanel) {
        this.titlePanel = titlePanel;
        this.yearPanel = yearPanel;
        this.monthPanel = monthPanel;
    }

    // MODIFIES: this
    // EFFECTS: increments the program depth, where:
    // 0 = months, category; 1 = days; 2 = event
    public void incrementDepth() {
        titlePanel.incrementDepth();
        depth++;
        updateView();
    }

    // MODIFIES: this
    // EFFECTS: decrements the program depth, where:
    // 0 = months, category; 1 = days; 2 = event
    public void decrementDepth() {
        depth--;
        updateView();
    }

    // MODIFIES: calendarUI
    // EFFECTS: updates the view based on the depth of the program
    private void updateView() {
        if (depth == 0) {
            calendarUI.displayYearSelection();
        } else if (depth == 1) {
            calendarUI.displayMonthSelection();
        } else if (depth == 2) {
            calendarUI.displayDaySelection();
        }
        updateTitlePanel();
    }

    /*
     * GETTERS/SETTERS
     */

    public Calendar getCalendar() {
        return calendar;
    }

    public CalendarUI getCalendarUI() {
        return calendarUI;
    }

    public TitlePanel getTitlePanel() {
        return titlePanel;
    }

    public YearPanel getYearPanel() {
        return yearPanel;
    }

    public MonthPanel getMonthPanel() {
        return monthPanel;
    }

    public DayPanel getDayPanel() {
        return dayPanel;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setCalendarUI(CalendarUI calendarUI) {
        this.calendarUI = calendarUI;
    }

    public void setTitlePanel(TitlePanel titlePanel) {
        this.titlePanel = titlePanel;
    }

    public void setYearPanel(YearPanel yearPanel) {
        this.yearPanel = yearPanel;
    }

    public void setMonthPanel(MonthPanel monthPanel) {
        this.monthPanel = monthPanel;
    }

    public void setDayPanel(DayPanel dayPanel) {
        this.dayPanel = dayPanel;
    }
}
