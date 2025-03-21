package ui;

import model.Calendar;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

public class MonthPanel extends JPanel {

    // EFFECTS: constructs a MonthPanel with the given calendar and calendar controller.
    public MonthPanel(Calendar calendar, CalendarController calendarController) {
    }

    // EFFECTS: creates and returns the month panel UI.
    public JPanel createPanel() {
        return null;
    }

    // EFFECTS: returns the amount of whitespace needed before the first day of the month.
    public int getWhiteSpace() {
        return 0;
    }

    private class DayAction extends AbstractAction {
        
        // EFFECTS: Handles selecting a day and updating the calendar view.
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
}
