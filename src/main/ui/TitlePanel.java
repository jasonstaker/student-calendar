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

    // fields
    private static final String[] MONTHS = { "January", "February", "March", "April", "May", "June",
                                             "July", "August", "September", "October", "November", "December" };
    private Calendar calendar;
    private CalendarUI calendarUI;
    private CalendarController calendarController;
    private JLabel titleLabel;
    private String calendarTitle;
    private Integer year;
    private Integer month;
    private Integer day;

    // EFFECTS: initializes the panel's fields with given calendar, calendarUI, and starting year
    public TitlePanel(Calendar calendar, CalendarController calendarController, CalendarUI calendarUI, int year) {
        this.calendar = calendar;
        this.calendarUI = calendarUI;
        this.calendarController = calendarController;
        this.calendarTitle = calendar.getTitle();
        this.year = year;
        this.month = null;
        this.day = null;
        this.add(createBackButton(), BorderLayout.WEST);
        this.add(createPanel(), BorderLayout.CENTER);
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

    // EFFECTS: creates the back button with its associated listener
    public JButton createBackButton() {
        JButton backButton = new JButton("X");
        backButton.addActionListener(new BackButtonAction());
        
        return backButton;
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
            month = calendar.getCurrentYear().getCurrentMonth().getMonthNumber();
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

        // MODIFIES: calendar
        // EFFECTS: Navigates to the previous month or year, updates the calendar view, and refreshes the title
        @Override
        public void actionPerformed(ActionEvent evt) {
            navigateLeft();
        }
    }

    // MODIFIES: calendar, calendarUI
    // EFFECTS: navigates to the previous month, year, or day, updates the calendar view, and refreshes the title
    private void navigateLeft() {
        if (month == null) {
            navigateLeftYear();
        } else if (day == null) {
            navigateLeftMonth();
        } else {
            navigateLeftDay();
        }
        updateTitle();
    }

    // MODIFIES: calendar, calendarUI
    // EFFECTS: navigates to the previous year if it can, updating the calendar view and title, nothing otherwise
    private void navigateLeftYear() {
        calendar.decrementYearIndex();
    }

    // MODIFIES: calendar, calendarUI
    // EFFECTS: navigates to the previous month if it can, updating the calendar view and title, nothing otherwise
    private void navigateLeftMonth() {
        int currMonth = calendar.getCurrentYear().getCurrentMonth().getMonthNumber();
        if (currMonth == 0 && calendar.getLowestYear().getYearNumber() != year) {
            calendar.decrementYearIndex();
            calendar.getCurrentYear().setCurrentMonthIndex(11);
        } else {
            calendar.getCurrentYear().decrementMonthIndex();
        }
        calendarUI.displayMonthSelection();
    }

    // MODIFIES: calendar, calendarUI
    // EFFECTS: navigates to the pervious day if it can, updating the calendar view and title, nothing otherwise
    private void navigateLeftDay() {
        int currDay = calendar.getCurrentYear().getCurrentMonth().getCurrentDay().getDayNumber();
        int currMonth = calendar.getCurrentYear().getCurrentMonth().getMonthNumber();
        if (currDay == 1) {
            if (currMonth == 0 && calendar.getLowestYear().getYearNumber() != year) {
                calendar.decrementYearIndex();
                calendar.getCurrentYear().setCurrentMonthIndex(11);
                int lastDay = calendar.getCurrentYear().getCurrentMonth().getDays().size() - 1;
                calendar.getCurrentYear().getCurrentMonth().setCurrentDayIndex(lastDay);
            } else if (currMonth != 0) {
                calendar.getCurrentYear().decrementMonthIndex();
                int lastDay = calendar.getCurrentYear().getCurrentMonth().getDays().size() - 1;
                calendar.getCurrentYear().getCurrentMonth().setCurrentDayIndex(lastDay);
            } else {
                calendar.getCurrentYear().getCurrentMonth().decrementDayIndex();
            }
        } else {
            calendar.getCurrentYear().getCurrentMonth().decrementDayIndex();
        }
        calendarUI.displayDaySelection();
    }

    private class RightArrowAction extends AbstractAction {
        // EFFECTS: Initializes the right arrow action with the corresponding arrow symbol.
        RightArrowAction() {
            super("⮞");
        }

        // MODIFIES: calendar, calendarUI
        // EFFECTS: navigates to the next month or year, updates the calendar view, and refreshes the title.
        @Override
        public void actionPerformed(ActionEvent evt) {
            navigateRight();
        }
    }

    // MODIFIES: calendar, calendarUI
    // EFFECTS: navigates to the next month, year, or day, updates the calendar view, and refreshes the title.
    private void navigateRight() {
        if (month == null) {
            navigateRightYear();
        } else if (day == null) {
            navigateRightMonth();
        } else {
            navigateRightDay();
        }
        updateTitle();
    }

    // MODIFIES: calendar, calendarUI
    // EFFECTS: navigates to the next year if it can, updating the calendar view and title, nothing otherwise
    private void navigateRightYear() {
        calendar.incrementYearIndex();
    }

    // MODIFIES: calendar, calendarUI
    // EFFECTS: navigates to the next month if it can, updating the calendar view and title, nothing otherwise
    private void navigateRightMonth() {
        int currMonth = calendar.getCurrentYear().getCurrentMonth().getMonthNumber();
        if (currMonth == 11 && calendar.getHighestYear().getYearNumber() != year) {
            calendar.incrementYearIndex();
            calendar.getCurrentYear().setCurrentMonthIndex(0);
        } else {
            calendar.getCurrentYear().incrementMonthIndex();
        }
        calendarUI.displayMonthSelection();
    }

    // MODIFIES: calendar, calendarUI
    // EFFECTS: navigates to the next day if it can, updating the calendar view and title, nothing otherwise
    private void navigateRightDay() {
        int currDay = calendar.getCurrentYear().getCurrentMonth().getCurrentDay().getDayNumber();
        int totalDays = calendar.getCurrentYear().getCurrentMonth().getDays().size();
        int currMonth = calendar.getCurrentYear().getCurrentMonth().getMonthNumber();
        if (currDay == totalDays) {
            if (currMonth == 11 && calendar.getHighestYear().getYearNumber() != year) {
                calendar.incrementYearIndex();
                calendar.getCurrentYear().setCurrentMonthIndex(0);
                calendar.getCurrentYear().getCurrentMonth().setCurrentDayIndex(0);
            } else if (currMonth != 11) {
                calendar.getCurrentYear().incrementMonthIndex();
                calendar.getCurrentYear().getCurrentMonth().setCurrentDayIndex(0);
            } else {
                calendar.getCurrentYear().getCurrentMonth().incrementDayIndex();
            }
        } else {
            calendar.getCurrentYear().getCurrentMonth().incrementDayIndex();
        }
        calendarUI.displayDaySelection();
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
    // EFFECTS: decrements the calendar view depth
    private void decrementDepth() {
        if (day != null) {
            day = null;
        } else if (month != null) {
            month = null;
        } else {
            System.exit(1);
        }
    }

    private class BackButtonAction extends AbstractAction {
        // EFFECTS: handles going back in the calendar
        @Override
        public void actionPerformed(ActionEvent e) {
            decrementDepth();
            calendarController.decrementDepth();
        }
    }

}
