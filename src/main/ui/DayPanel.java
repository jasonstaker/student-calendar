package ui;

import model.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPanel;

public class DayPanel extends JPanel {

    // EFFECTS: constructs a DayPanel with the given calendar and controller.
    public DayPanel(Calendar calendar, CalendarController calendarController) {
    }

    // EFFECTS: creates and returns the main panel layout for the day view.
    public JPanel createPanel() {
        return null;
    }

    private class ViewEventAction extends AbstractAction {
        
        // EFFECTS: Handles action event for viewing an event.
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    private class RemoveEventAction extends AbstractAction {
        
        // EFFECTS: Handles action event for removing an event.
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    private class AddEventAction extends AbstractAction {
        
        // EFFECTS: Handles action event for adding a new event.
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
}
