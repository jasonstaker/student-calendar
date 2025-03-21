package ui;

import model.Calendar;

public class CalendarController {

    private Calendar calendar;
    private CalendarUI calendarUI;
    private TitlePanel titlePanel;
    private YearPanel yearPanel;
    private MonthPanel monthPanel;
    private DayPanel dayPanel;

    // REQUIRES: 
    // MODIFIES: 
    // EFFECTS: 
    public CalendarController(Calendar calendar, CalendarUI calendarUI) {
        this.calendar = calendar;
        this.calendarUI = calendarUI;
    }

    public void updateAllViews() {
        titlePanel.updateTitle();
        //yearPanel.updateYearPanel();
        //monthPanel.updateMonthPanel();
        //dayPanel.updateDatPanel();
    }

    public void setFields(TitlePanel titlePanel, YearPanel yearPanel, MonthPanel monthPanel) {
        this.titlePanel = titlePanel;
        this.yearPanel = yearPanel;
        this.monthPanel = monthPanel;
    }

    // this function will increment how far the title should be along
    // 0 = months, category
    // 1 = days
    // 2 = event
    public void incrementTitleDepth() {
        titlePanel.incrementDepth();
    }

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
