package ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import model.Calendar;

public class YearPanel extends JPanel {

    // fields
    private static final String[] MONTHS = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };

    // MODIFIES: this
    // EFFECTS: constructs a YearPanel object and initializes UI components.
    public YearPanel(Calendar calendar, CalendarController calendarController) {
        
    }

    // MODIFIES: this
    // EFFECTS: Creates and returns the main panel displaying the months.
    public JPanel createPanel() {
        return null;
    }

    private class MonthAction extends AbstractAction {
        // EFFECTS: decides which month to enter once a user selects one
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    }
}
