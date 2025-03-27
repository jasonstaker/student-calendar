package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Calendar;

// A YearPanel that displays the months of the current year and allows month selection.
public class YearPanel extends JPanel {

    // fields
    private static final String[] MONTHS = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };
    private Calendar calendar;
    private JPanel yearPanel;
    private CalendarUI calendarUI;
    private CalendarController calendarController;

    // EFFECTS: constructs a YearPanel object with the given calendar and controller
    public YearPanel(Calendar calendar, CalendarController calendarController) {
        this.calendar = calendar;
        this.add(createPanel());
        this.calendarUI = calendarController.getCalendarUI();
        this.calendarController = calendarController;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns the main panel displaying the months of the year
    public JPanel createPanel() {
        yearPanel = new JPanel();
        yearPanel.setLayout(new GridLayout(4, 3));

        for (int i = 0; i < 12; i++) {
            JButton monthButton = new JButton(MONTHS[i]);
            monthButton.setActionCommand(String.valueOf(i));
            monthButton.addActionListener(new MonthAction());
            monthButton.setPreferredSize(new Dimension(100, 40));
            yearPanel.add(monthButton);
        }

        return yearPanel;
    }

    // A class that handles the action of selecting a month from the year panel
    private class MonthAction extends AbstractAction {
        // EFFECTS: decides which month to enter once a user selects one
        @Override
        public void actionPerformed(ActionEvent e) {
            int monthIndex = Integer.parseInt(e.getActionCommand());
            calendar.getCurrentYear().setCurrentMonthIndex(monthIndex);
            calendarUI.displayMonthSelection();
            calendarController.incrementDepth();
            calendarController.updateTitlePanel();
        }
    }
}
