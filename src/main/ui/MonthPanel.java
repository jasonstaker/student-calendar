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

// TODO
public class MonthPanel extends JPanel {

    // fields
    private Calendar calendar;
    private JPanel monthPanel;
    private CalendarController calendarController;

    // EFFECTS: constructs a MonthPanel with the given calendar and calendar controller.
    public MonthPanel(Calendar calendar, CalendarController calendarController) {
        this.calendar = calendar;
        this.calendarController = calendarController;
        this.add(createPanel());
    }

    // EFFECTS: creates and returns the month panel UI.
    public JPanel createPanel() {
        monthPanel = new JPanel();
        monthPanel.setLayout(new GridLayout(7, 7));
        Month month = calendar.getCurrentYear().getCurrentMonth();

        String[] dayNames = {"S", "M", "T", "W", "T", "F", "S"};

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

    // EFFECTS: returns the amount of whitespace needed before the first day of the month
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

    private class DayAction extends AbstractAction {
        // EFFECTS: Handles selecting a day and updating the calendar view.
        @Override
        public void actionPerformed(ActionEvent e) {
            int dayIndex = Integer.parseInt(e.getActionCommand());
            calendar.getCurrentYear().getCurrentMonth().setCurrentDayIndex(dayIndex - 1);
            calendarController.getCalendarUI().displayDaySelection();
            calendarController.incrementTitleDepth();
            calendarController.updateAllViews();
        }
    }

}
