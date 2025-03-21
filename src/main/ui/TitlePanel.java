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
import model.Calendar;

// TitlePanel displays the title portion of the calendar UI and provides navigation controls
public class TitlePanel extends JPanel {

    // constants
    private static final String[] MONTHS = { "January", "February", "March", "April", "May", "June",
                                               "July", "August", "September", "October", "November", "December" };

    // fields
    private Calendar calendar;
    private CalendarUI calendarUI;
    private JLabel titleLabel;
    private String calendarTitle;
    private Integer year;
    private Integer month;
    private Integer day;

    // EFFECTS: Initializes TitlePanel fields with given calendar, calendarUI, and starting year; creates and adds the main panel.
    public TitlePanel(Calendar calendar, CalendarUI calendarUI, int year) {
        this.calendar = calendar;
        this.calendarUI = calendarUI;
        this.calendarTitle = calendar.getTitle();
        this.year = year;
        this.month = null;
        this.day = null;
        this.add(createPanel());
    }

    // MODIFIES: titlePanel
    // EFFECTS: creates and returns the main title panel with border and layout settings
    private JPanel createPanel() {
        JPanel titlePanel = new JPanel(true);
        titlePanel.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(createTitleUI(), BorderLayout.CENTER);
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setForeground(Color.BLACK);
        return titlePanel;
    }

    // MODIFIES: titleUI, titkePanel, titleLabel
    // EFFECTS: creates and returns the title UI panel with navigation arrows and the title label.
    private JPanel createTitleUI() {
        JPanel titleUI = new JPanel(true);
        JPanel titlePanel = new JPanel(true);
        titleLabel = new JLabel();
        titleUI.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption));
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

    // REQUIRES: calendar, calendar.getCurrentYear(), and titleLabel are non-null
    // MODIFIES: this, titleLabel
    // EFFECTS: updates the title text based on the calendar's current year, month, and day
    public void updateTitle() {
        year = calendar.getCurrentYear().getYearNumber();
        if (month == null) {
            titleLabel.setText(calendarTitle + ": " + year);
        } else if (day == null) {
            month = calendar.getCurrentYear().getCurrentMonth().getMonthNumber();
            titleLabel.setText(calendarTitle + ": " + MONTHS[month] + " " + year);
        } else {
            day = calendar.getCurrentYear().getCurrentMonth().getCurrentDay().getDayNumber();
            titleLabel.setText(calendarTitle + ": " + MONTHS[month] + " " + day + ", " + year);
        }
        this.revalidate();
        this.repaint();
    }

    // EFFECTS: creates and returns a left arrow JButton with transparent properties
    private JButton makeLeftArrow() {
        JButton leftArrow = new JButton(new LeftArrowAction());
        leftArrow.setOpaque(false);
        leftArrow.setContentAreaFilled(false);
        leftArrow.setBorderPainted(false);
        return leftArrow;
    }

    // EFFECTS: creates and returns a right arrow JButton with transparent properties
    private JButton makeRightArrow() {
        JButton rightArrow = new JButton(new RightArrowAction());
        rightArrow.setOpaque(false);
        rightArrow.setContentAreaFilled(false);
        rightArrow.setBorderPainted(false);
        return rightArrow;
    }

    private class LeftArrowAction extends AbstractAction {
        // EFFECTS: Initializes the left arrow action with the corresponding arrow symbol
        LeftArrowAction() {
            super("⮜");
        }

        // MODIFIES: calendar, calendarUI
        // EFFECTS: Navigates to the previous month or year, updates the calendar view, and refreshes the title
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (month == null) {
                calendar.decrementYearIndex();
            } else {
                if (calendar.getCurrentYear().getCurrentMonth().getMonthNumber() == 0 
                        && calendar.getLowestYear().getYearNumber() != year) {
                    calendar.decrementYearIndex();
                    calendar.getCurrentYear().setCurrentMonthIndex(11);
                } else {
                    calendar.getCurrentYear().decrementMonthIndex();
                }
                calendarUI.displayMonthSelection();
            }
            updateTitle();
        }
    }

    private class RightArrowAction extends AbstractAction {
        // EFFECTS: Initializes the right arrow action with the corresponding arrow symbol.
        RightArrowAction() {
            super("⮞");
        }

        // MODIFIES: calendar, calendarUI
        // EFFECTS: Navigates to the next month or year, updates the calendar view, and refreshes the title.
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (month == null) {
                calendar.incrementYearIndex();
            } else {
                if (calendar.getCurrentYear().getCurrentMonth().getMonthNumber() == 11 
                        && calendar.getHighestYear().getYearNumber() != year) {
                    calendar.incrementYearIndex();
                    calendar.getCurrentYear().setCurrentMonthIndex(0);
                } else {
                    calendar.getCurrentYear().incrementMonthIndex();
                    calendarUI.displayMonthSelection();
                }
            }
            updateTitle();
        }
    }

    // MODIFIES: this
    // EFFECTS: advances the calendar view depth
    protected void incrementDepth() {
        if (month == null) {
            month = calendar.getCurrentYear().getCurrentMonth().getMonthNumber();
        } else if (day == null) {
            day = calendar.getCurrentYear().getCurrentMonth().getCurrentDay().getDayNumber();
        }
    }

    // MODIFIES: this
    // EFFECTS: Devances theh calendar view depth
    protected void decrementtDepth() {
        // TODO: this method and overhaul of depth system
    }

}
