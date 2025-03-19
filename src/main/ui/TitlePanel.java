package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

import model.Calendar;

public class TitlePanel extends JPanel {

    private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June",
                                            "July", "August", "September", "October", "November", "December"};

    
    private Calendar calendar;
    private JLabel titleLabel;
    private String calendarTitle;
    private Integer year;
    private Integer month;
    private Integer day;

    public TitlePanel(Calendar calendar, int year) {
        this.calendar = calendar;
        this.calendarTitle = calendar.getTitle();
        this.year = year;
        this.month = null;
        this.day = null;
        this.add(createPanel());
    }

    public TitlePanel(Calendar calendar, int year, int month) {
        this.calendar = calendar;
        this.calendarTitle = calendar.getTitle();
        this.year = year;
        this.month = month;
        this.day = null;
        this.add(createPanel());
    }

    public TitlePanel(Calendar calendar, int year, int month, int day) {
        this.calendar = calendar;
        this.calendarTitle = calendar.getTitle();
        this.year = year;
        this.month = month;
        this.day = day;
        this.add(createPanel());
    }

    private JPanel createPanel() {
        JPanel titlePanel = new JPanel(true);
        titlePanel.setBorder(BorderFactory
                .createLineBorder(SystemColor.activeCaption));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(createTitleUI(), BorderLayout.CENTER);
        //titlePanel.add(createNotificationUI(), BorderLayout.EAST);
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setForeground(Color.BLACK);

        return titlePanel;
    }

    private JPanel createTitleUI() {
        JPanel titleUI = new JPanel(true);
        JPanel titlePanel = new JPanel(true);
        titleLabel = new JLabel();
        titleUI.setBorder(BorderFactory
                .createLineBorder(SystemColor.activeCaption));
        titleUI.setLayout(new FlowLayout());
        titleUI.setBackground(Color.WHITE);

        updateTitle();
        JButton leftArrow = makeLeftArrow();
        JButton rightArrow = makeRightArrow();
        titleLabel.setForeground(SystemColor.activeCaption);

        titlePanel.add(leftArrow, BorderLayout.WEST);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(rightArrow, BorderLayout.EAST);

        titleUI.add(titlePanel, BorderLayout.CENTER);

        return titleUI;
    }

    private void updateTitle() {
        if (month == null) {
            titleLabel.setText(calendarTitle + ": " + year);
            return;
        } else if (day == null) {
            titleLabel.setText(calendarTitle + ": " + MONTHS[month] + " " + year);
            return;
        }

        titleLabel.setText(calendarTitle + ": " + MONTHS[month] + " " + day + " " + year);
    }

    private JButton makeLeftArrow() {
        JButton leftArrow = new JButton(new LeftArrowAction());
        
        leftArrow.setOpaque(false);
        leftArrow.setContentAreaFilled(false);
        leftArrow.setBorderPainted(false);

        return leftArrow;
    }

    private JButton makeRightArrow() {
        JButton rightArrow = new JButton(new RightArrowAction());
        
        rightArrow.setOpaque(false);
        rightArrow.setContentAreaFilled(false);
        rightArrow.setBorderPainted(false);

        return rightArrow;
    }

    private class LeftArrowAction extends AbstractAction {
        LeftArrowAction() {
            super("\u25C4");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (month == null) {
                calendar.decrementYearIndex();
                year = calendar.getCurrentYear().getYearNumber();
            } else {
                calendar.getCurrentYear().decrementMonthIndex();
                month = calendar.getCurrentYear().getCurrentMonth().getMonthNumber();
            }

            updateTitle();
        }
    }

    private class RightArrowAction extends AbstractAction {
        RightArrowAction() {
            super("\u27A4");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (month == null) {
                calendar.incrementYearIndex();
                year = calendar.getCurrentYear().getYearNumber();
            } else {
                calendar.getCurrentYear().incrementMonthIndex();
                month = calendar.getCurrentYear().getCurrentMonth().getMonthNumber();
            }

            updateTitle();
        }
    }
    
}
