package ui;

import model.Calendar;
import model.Month;
import model.Year;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

// MonthPanel displays all the days for the current month in the current year in calendar
public class MonthPanel extends JPanel {

    // fields
    private Calendar calendar;
    private JPanel monthPanel;
    private CalendarController calendarController;

    // EFFECTS: constructs a MonthPanel with the given calendar and calendar
    // controller and adds the created panel to this
    public MonthPanel(Calendar calendar, CalendarController calendarController) {
        this.calendar = calendar;
        this.calendarController = calendarController;
        this.add(createPanel());
    }

    // EFFECTS: creates and returns the month panel user interface using a grid
    // layout and populates day names and day buttons
    public JPanel createPanel() {
        monthPanel = new JPanel();
        monthPanel.setLayout(new GridLayout(7, 7));
        Month month = calendar.getCurrentYear().getCurrentMonth();

        String[] dayNames = { "S", "M", "T", "W", "T", "F", "S" };

        for (String day : dayNames) {
            JButton button = new JButton(day);
            button.setPreferredSize(new Dimension(50, 45));
            monthPanel.add(button);
        }

        for (int i = 0; i < getWhiteSpace(); i++) {
            monthPanel.add(new JLabel(" "));
        }

        for (int day = 1; day <= month.getDays().size(); day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setActionCommand(String.valueOf(day));
            dayButton.addActionListener(new DayAction());
            dayButton.setPreferredSize(new Dimension(50, 45));
            monthPanel.add(dayButton);
        }

        for (int i = 0; i < (49 - (month.getDays().size() + getWhiteSpace() + 7)); i++) {
            monthPanel.add(new JLabel(" "));
        }

        return monthPanel;
    }

    // EFFECTS: returns the amount of whitespace needed before the first day of the
    // month in the grid layout
    public int getWhiteSpace() {
        int total = 0;

        for (Year year : calendar.getYears()) {
            for (Month month : year.getMonths()) {
                if (month.equals(calendar.getCurrentYear().getCurrentMonth())) {
                    return (total + 4) % 7;
                }
                total += month.getDays().size();
            }
        }
        return -1;
    }

    // EFFECTS: handles day selection by setting the current day index and updating
    // the calendar view
    private class DayAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dayIndex = Integer.parseInt(e.getActionCommand());
            calendar.getCurrentYear().getCurrentMonth().setCurrentDayIndex(dayIndex - 1);
            calendarController.getCalendarUI().displayDaySelection();
            calendarController.incrementDepth();
            calendarController.updateTitlePanel();
        }
    }
}