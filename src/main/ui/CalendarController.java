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

    // EFFECTS: initializes a new CalendarController with the given calendar and calendarUI
    public CalendarController(Calendar calendar, CalendarUI calendarUI) {
        this.calendar = calendar;
        this.calendarUI = calendarUI;
    }

    // MODIFIES: this
    // EFFECTS: updates all views by updating the title panel
    // TODO: MODIFY FOR ALL
    public void updateAllViews() {
        titlePanel.updateTitle();
        //yearPanel.updateYearPanel();
        //monthPanel.updateMonthPanel();
        //dayPanel.updateDayPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets the UI panels for title, year, and month views
    public void setFields(TitlePanel titlePanel, YearPanel yearPanel, MonthPanel monthPanel) {
        this.titlePanel = titlePanel;
        this.yearPanel = yearPanel;
        this.monthPanel = monthPanel;
    }

    // MODIFIES: this
    // EFFECTS: increments the title depth, where:
    //          0 = months, category; 1 = days; 2 = event
    public void incrementTitleDepth() {
        titlePanel.incrementDepth();
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
