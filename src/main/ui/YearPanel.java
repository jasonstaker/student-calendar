package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Calendar;

public class YearPanel extends JPanel{

    private static final String[] MONTHS = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };


    private Calendar calendar;
    private JPanel yearPanel;

    public YearPanel(Calendar calendar) {
        this.calendar = calendar;
        this.add(createPanel());
    }

    public JPanel createPanel() {
        yearPanel = new JPanel();
	    yearPanel.setLayout(new GridLayout(4,3));
        
        for (int i = 0; i < 12; i++) {
            JButton monthButton = new JButton(MONTHS[i]);
            monthButton.setActionCommand(String.valueOf(i));
            monthButton.addActionListener(new MonthAction());
            monthButton.setPreferredSize(new Dimension(100, 40));
            add(monthButton);
        }
        
        return yearPanel;
    }

    public JPanel createMonthPanel() {
        this.removeAll();
        this.add(new MonthPanel());
        return null;
    }

    private class MonthAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            int monthIndex = Integer.parseInt(e.getActionCommand());
            calendar.getCurrentYear().setCurrentMonthIndex(monthIndex);
            createMonthPanel();
        }

    }
    
}
