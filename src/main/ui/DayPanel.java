package ui;

import model.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

// A view of the events for the day, with ability to modify events
public class DayPanel extends JPanel {

    // fields
    private Calendar calendar;
    private CalendarController calendarController;
    private JPanel mainPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;

    // EFFECTS: constructs a DayPanel with the given calendar and controller
    public DayPanel(Calendar calendar, CalendarController calendarController) {
        this.calendar = calendar;
        this.calendarController = calendarController;
        this.add(createPanel());
    }

    // EFFECTS: creates and returns the main panel layout for the day view.
    public JPanel createPanel() {
        mainPanel = new JPanel(new BorderLayout());
        leftPanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel(new BorderLayout());

        JPanel eventsMenuPanel = new JPanel();
        eventsMenuPanel.setLayout(new GridLayout(0, 1));
        eventsMenuPanel.setBorder(BorderFactory.createTitledBorder("Events Menu"));

        Day currentDay = calendar.getCurrentYear().getCurrentMonth().getCurrentDay();

        eventsMenuPanel.add(new JLabel("View Event:"));
        JComboBox<EventItem> viewEventsDropdown = new JComboBox<>();
        for (Event event : currentDay.getEvents()) {
            viewEventsDropdown.addItem(new EventItem(event.getId(), event.getName()));
        }

        viewEventsDropdown.addActionListener(new ViewEventAction());
        eventsMenuPanel.add(viewEventsDropdown);

        eventsMenuPanel.add(new JLabel("Remove Event:"));
        JComboBox<EventItem> removeEventsDropdown = new JComboBox<>();
        for (Event event : currentDay.getEvents()) {
            removeEventsDropdown.addItem(new EventItem(event.getId(), event.getName()));
        }
        removeEventsDropdown.addActionListener(new RemoveEventAction());
        eventsMenuPanel.add(removeEventsDropdown);

        JButton addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(new AddEventAction());
        eventsMenuPanel.add(addEventButton);
        leftPanel.add(eventsMenuPanel);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        JPanel timelinePanel = new JPanel();
        timelinePanel.setBorder(BorderFactory.createTitledBorder("Timeline Graphic"));
        timelinePanel.add(new DayTimelinePanel(currentDay));
        rightPanel.add(timelinePanel);

        mainPanel.add(rightPanel, BorderLayout.EAST);

        return mainPanel;
    }

    // EFFECTS: produces the view event panel based on the given event
    private JPanel createViewEventPanel(Event event) {
        JPanel viewEventPanel = new JPanel();
        viewEventPanel.setBorder(BorderFactory.createTitledBorder("View event: " + event.getName()));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        leftPanel.add(new JLabel("Name: " + event.getName()));
        leftPanel.add(add(Box.createVerticalStrut(10)));
        leftPanel.add(new JLabel("Start Time: " + event.getStartTime().timeToString()));
        leftPanel.add(add(Box.createVerticalStrut(10)));
        leftPanel.add(new JLabel("End Time: " + event.getEndTime().timeToString()));
        leftPanel.add(add(Box.createVerticalStrut(10)));
        leftPanel.add(new JLabel("Category: " + (event.getCategory() == null ? "None" : event.getCategory().getName())));
        leftPanel.add(add(Box.createVerticalStrut(10)));
        leftPanel.add(new JLabel("Subcategory: " + (event.getSubcategory() == null ? "None" : event.getSubcategory().getName())));

        viewEventPanel.add(leftPanel, BorderLayout.WEST);

        return viewEventPanel;
    }

    // MODIFIES: calendar, this
    // EFFECTS: creates an add event panel and adds it to the calendar based on user
    // input
    private JPanel createAddEventPanel() {
        JPanel addEventPanel = new JPanel();
        addEventPanel.setBorder(BorderFactory.createTitledBorder("Add event"));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JPanel startTimePanel = new JPanel();
        startTimePanel.setLayout(new BoxLayout(startTimePanel, BoxLayout.X_AXIS));

        JPanel endTimePanel = new JPanel();
        endTimePanel.setLayout(new BoxLayout(endTimePanel, BoxLayout.X_AXIS));

        JTextField nameField = new JTextField("", 15);

        leftPanel.add(new JLabel("Name: "));
        leftPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(nameField);
        leftPanel.add(new JLabel("Start Time: "));
        leftPanel.add(Box.createVerticalStrut(10));

        Integer[] hours = new Integer[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = i;
        }
        String[] minutes = { "00", "30" };

        JComboBox<Integer> startHour = new JComboBox<>(hours);
        JComboBox<String> startMinute = new JComboBox<>(minutes);

        startTimePanel.add(startHour);
        startTimePanel.add(new JLabel(":"));
        startTimePanel.add(startMinute);
        rightPanel.add(startTimePanel);

        leftPanel.add(new JLabel("End Time: "));
        leftPanel.add(Box.createVerticalStrut(10));

        JComboBox<Integer> endHour = new JComboBox<>();
        JComboBox<String> endMinute = new JComboBox<>();

        endHour.setEnabled(false);
        endMinute.setEnabled(false);

        endTimePanel.add(endHour);
        endTimePanel.add(new JLabel(":"));
        endTimePanel.add(endMinute);
        rightPanel.add(endTimePanel);

        ActionListener updateEndTimeDropdown = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endHour.setEnabled(true);
                endMinute.setEnabled(true);
                endHour.removeAllItems();
                endMinute.removeAllItems();

                int startHourSelected = (int) startHour.getSelectedItem();
                int startMinuteSelected = Integer.parseInt((String) startMinute.getSelectedItem());

                for (int i = startHourSelected; i < 24; i++) {
                    endHour.addItem(i);
                }

                updateEndMinuteDropdown(startHourSelected, startMinuteSelected, endHour, endMinute);
            }
        };

        ActionListener endHourListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int startHourSelected = (int) startHour.getSelectedItem();
                int startMinuteSelected = Integer.parseInt((String) startMinute.getSelectedItem());
                updateEndMinuteDropdown(startHourSelected, startMinuteSelected, endHour, endMinute);
            }
        };

        startHour.addActionListener(updateEndTimeDropdown);
        startMinute.addActionListener(updateEndTimeDropdown);
        endHour.addActionListener(endHourListener);

        leftPanel.add(new JLabel("Category: "));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(new JLabel("Subcategory: "));
        leftPanel.add(Box.createVerticalStrut(10));

        JComboBox<CategoryItem> categoryDropdown = new JComboBox<>();
        categoryDropdown.addItem(new CategoryItem(-1, "None"));

        JComboBox<CategoryItem> subcategoryDropdown = new JComboBox<>();
        subcategoryDropdown.addItem(new CategoryItem(-1, "None"));

        for (Category category : calendar.getCategories()) {
            categoryDropdown.addItem(new CategoryItem(category.getId(), category.getName()));
        }

        for (Category subcategory : calendar.getSubcategories()) {
            categoryDropdown.addItem(new CategoryItem(subcategory.getId(), subcategory.getName()));
            subcategoryDropdown.addItem(new CategoryItem(subcategory.getId(), subcategory.getName()));
        }

        rightPanel.add(categoryDropdown);
        rightPanel.add(subcategoryDropdown);

        JPanel recurringDaysPanel = createRecurringDaysPanel();
        rightPanel.add(recurringDaysPanel);

        ActionListener submitButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startHour.getSelectedItem() == null || startMinute.getSelectedItem() == null ||
                        endHour.getSelectedItem() == null || endMinute.getSelectedItem() == null) {
                    return;
                }

                int startHourInt = (int) startHour.getSelectedItem();
                int startMinuteInt = Integer.parseInt((String) startMinute.getSelectedItem());

                int endHourInt = (int) endHour.getSelectedItem();
                int endMinuteInt = Integer.parseInt((String) endMinute.getSelectedItem());

                Category selectedCategory = calendar
                        .getCategory(((CategoryItem) categoryDropdown.getSelectedItem()).getId());

                if (selectedCategory == null) {
                    selectedCategory = calendar
                            .getSubcategory(((CategoryItem) categoryDropdown.getSelectedItem()).getId());
                }

                String name = nameField.getText();
                Time startTime = new Time(startHourInt, startMinuteInt);
                Time endTime = new Time(endHourInt, endMinuteInt);
                Category category = selectedCategory;
                Subcategory subcategory = calendar
                        .getSubcategory(((CategoryItem) subcategoryDropdown.getSelectedItem()).getId());
                List<Day> recurringDays = parseRecurringDays(recurringDaysPanel);

                Event event = new Event(category, subcategory, startTime, endTime, name, recurringDays);
                calendar.addEvent(event);
                reset();
            }
        };

        JButton submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.addActionListener(submitButtonListener);

        addEventPanel.add(leftPanel, BorderLayout.WEST);
        addEventPanel.add(rightPanel, BorderLayout.EAST);
        addEventPanel.add(submitButton, BorderLayout.SOUTH);

        return addEventPanel;
    }

    // EFFECTS: creates a option for users to choose when the event they are adding
    // occurs
    private JPanel createRecurringDaysPanel() {
        JPanel recurringDaysPanel = new JPanel();
        recurringDaysPanel.setLayout(new BoxLayout(recurringDaysPanel, BoxLayout.Y_AXIS));

        JRadioButton onceButton = new JRadioButton("Once");
        JRadioButton dailyButton = new JRadioButton("Repeat Every Day");
        JRadioButton weeklyButton = new JRadioButton("Repeat Every Week");

        ButtonGroup group = new ButtonGroup();
        group.add(onceButton);
        group.add(dailyButton);
        group.add(weeklyButton);
        onceButton.setSelected(true);

        recurringDaysPanel.add(onceButton);
        recurringDaysPanel.add(dailyButton);
        recurringDaysPanel.add(weeklyButton);

        JPanel durationPanel = new JPanel();
        durationPanel.setLayout(new BoxLayout(durationPanel, BoxLayout.X_AXIS));

        JComboBox<Integer> durationDropdown = new JComboBox<>();
        for (int i = 1; i <= 30; i++) {
            durationDropdown.addItem(i);
        }

        durationPanel.add(new JLabel("Duration: "));
        durationPanel.add(durationDropdown);

        durationDropdown.setEnabled(false);

        onceButton.addActionListener((ActionEvent e) -> durationDropdown.setEnabled(false));
        dailyButton.addActionListener((ActionEvent e) -> durationDropdown.setEnabled(true));
        weeklyButton.addActionListener((ActionEvent e) -> durationDropdown.setEnabled(true));

        recurringDaysPanel.add(durationPanel);

        return recurringDaysPanel;
    }

    // REQUIRES: modelPanel is the recurringDaysPanel in the addEventPanel
    // EFFECTS: returns with a list of the recurring days based on the modelPanel
    private List<Day> parseRecurringDays(JPanel modelPanel) {
        List<Day> recurringDays = new ArrayList<>();

        for (Component component : modelPanel.getComponents()) {
            if (component instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) component;
                if (radioButton.isSelected()) {
                    JPanel durationPanel = (JPanel) modelPanel.getComponents()[3];
                    JComboBox<Integer> durationDropdown = (JComboBox<Integer>) durationPanel.getComponents()[1];

                    String name = radioButton.getText();
                    int duration = (int) (durationDropdown.getSelectedItem());
                    Day currentDay = calendar.getCurrentYear().getCurrentMonth().getCurrentDay();

                    if (name.equals("Repeat Every Day")) {
                        for (int i = 1; i <= duration; i++) {
                            if (calendar.fromOffset(currentDay, i) != null) {
                                recurringDays.add(calendar.fromOffset(currentDay, i));
                            }
                        }
                    } else if (name.equals("Repeat Every Week")) {
                        for (int i = 1; i <= duration; i++) {
                            if (calendar.fromOffset(currentDay, i) != null) {
                                recurringDays.add(calendar.fromOffset(currentDay, i * 7));
                            }
                        }
                    }

                    recurringDays.add(currentDay);
                }
            }
        }

        return recurringDays;
    }

    // REQUIRES: 0 <= startHour <= 23, 1 <= startMinute <= 60
    // MODIFIES: endHour, endMinute
    // EFFECTS: updates the endMinute dropdown based on the startHour, startMinute,
    // endHour
    private void updateEndMinuteDropdown(int startHour, int startMinute, JComboBox<Integer> endHour,
            JComboBox<String> endMinute) {
        endMinute.removeAllItems();
        Integer endHourSelected = (Integer) endHour.getSelectedItem();

        if (endHourSelected == null) {
            endHourSelected = startHour;
            if (endHour.getItemCount() > 0) {
                endHour.setSelectedIndex(0);
            }
        }

        if (endHourSelected == startHour) {
            if (startMinute == 0) {
                endMinute.addItem("00");
                endMinute.addItem("30");
            } else {
                endMinute.addItem("30");
            }
        } else {
            endMinute.addItem("00");
            endMinute.addItem("30");
        }
    }

    // MODIFIES: this
    // EFFECTS: resets the day panel to the original view, updating the panels
    private void reset() {
        this.removeAll();
        this.add(createPanel());
        revalidate();
        repaint();
    }

    private class ViewEventAction extends AbstractAction {
        // EFFECTS: Handles action event for viewing an event.
        @Override
        public void actionPerformed(ActionEvent e) {
            @SuppressWarnings("unchecked")
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            int id = ((EventItem) comboBox.getSelectedItem()).getId();
            rightPanel.removeAll();

            rightPanel.add(createViewEventPanel(calendar.getEvent(id)), BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    private class RemoveEventAction extends AbstractAction {
        // EFFECTS: Handles action event for removing an event.
        @Override
        public void actionPerformed(ActionEvent e) {
            @SuppressWarnings("unchecked")
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            int id = ((EventItem) comboBox.getSelectedItem()).getId();

            Day currentDay = calendar.getCurrentYear().getCurrentMonth().getCurrentDay();
            List<Event> events = currentDay.getEvents();
            for (Event event : events) {
                if (id == event.getId()) {
                    calendar.removeEvent(event);
                    for (Day day : event.getRecurringDays()) {
                        day.removeEvent(event);
                    }
                    break;
                }
            }
            reset();
        }
    }

    private class AddEventAction extends AbstractAction {
        // EFFECTS: Handles action event for adding a new event.
        @Override
        public void actionPerformed(ActionEvent e) {
            mainPanel.removeAll();
            mainPanel.add(createAddEventPanel(), BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    // Custom class so events can be stored by id in the drop box menus
    static class EventItem {
        private final int id;
        private final String name;

        public EventItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        // so the combo box gets the name for the event item to display
        @Override
        public String toString() {
            return name;
        }
    }

    // Custom class so categories/subcategories can be stored by id in the drop box
    // menus
    static class CategoryItem {
        private final int id;
        private final String name;

        public CategoryItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        // so the combo box gets the name for the category item to display
        @Override
        public String toString() {
            return name;
        }
    }

}