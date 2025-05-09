package ui;

import model.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

// DayPanel displays all the events in the currently selected day, with a view/remove/add event option
public class DayPanel extends JPanel {

    // fields
    private Calendar calendar;
    private JPanel mainPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private DayTimelinePanel dayTimelinePanel;

    // EFFECTS: constructs a DayPanel with the given calendar and controller;
    //          initializes the panel layout by creating and adding the main panel
    public DayPanel(Calendar calendar) {
        this.calendar = calendar;
        this.add(createPanel());
    }

    // EFFECTS: creates and returns the main panel layout for the day view,
    //          initializing primary panels and constructing left and right sections
    public JPanel createPanel() {
        initializePrimaryPanels();
        createLeftPanel();
        createRightPanel();
        return mainPanel;
    }

    // MODIFIES: this
    // EFFECTS: initializes the primary panels (mainPanel, leftPanel, rightPanel)
    //          with appropriate layout managers
    private void initializePrimaryPanels() {
        mainPanel = new JPanel(new BorderLayout());
        leftPanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel(new BorderLayout());
    }

    // MODIFIES: this, leftPanel, mainPanel
    // EFFECTS: creates the left panel containing the events menu with options
    //          to view, remove, or add events, and adds it to the main panel
    private void createLeftPanel() {
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
    }

    // MODIFIES: this, rightPanel, mainPanel
    // EFFECTS: creates the right panel containing a timeline graphic for the current day,
    //          and adds it to the main panel
    private void createRightPanel() {
        JPanel timelinePanel = new JPanel();
        Day currentDay = calendar.getCurrentYear().getCurrentMonth().getCurrentDay();

        timelinePanel.setBorder(BorderFactory.createTitledBorder("Timeline Graphic"));
        dayTimelinePanel = new DayTimelinePanel(currentDay);
        timelinePanel.add(dayTimelinePanel);
        rightPanel.add(timelinePanel);

        mainPanel.add(rightPanel, BorderLayout.EAST);
    }

    // EFFECTS: produces and returns a panel for viewing the details of the given event,
    //          including name, start time, end time, category, and subcategory
    private JPanel createViewEventPanel(Event event) {
        JPanel viewEventPanel = new JPanel();
        viewEventPanel.setBorder(BorderFactory.createTitledBorder("View event: " + event.getName()));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        Category category = null;
        Subcategory subcategory = null;
        if (event.getCategory() != null) {
            category = calendar.getCategory(event.getCategory().getId());
        }
        if (event.getSubcategory() != null) {
            subcategory = calendar.getSubcategory(event.getSubcategory().getId());
        }

        leftPanel.add(new JLabel("Name: " + event.getName()));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(new JLabel("Start Time: " + event.getStartTime().timeToString()));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(new JLabel("End Time: " + event.getEndTime().timeToString()));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(new JLabel("Category: " + (category == null ? "None" : event.getCategory().getName())));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(new JLabel("Subcategory: " + (subcategory == null ? "None" : event.getSubcategory().getName())));

        viewEventPanel.add(leftPanel, BorderLayout.WEST);

        return viewEventPanel;
    }

    // NOTE: This internal class exists solely to satisfy checkstyle requirements
    private static class TimePanel {
        JPanel panel;
        JComboBox<Integer> hour;
        JComboBox<String> minute;

        TimePanel(JPanel panel, JComboBox<Integer> hour, JComboBox<String> minute) {
            this.panel = panel;
            this.hour = hour;
            this.minute = minute;
        }
    }

    // MODIFIES: calendar, this
    // EFFECTS: creates an add event panel based on user input and returns it
    private JPanel createAddEventPanel() {
        JPanel addEventPanel = new JPanel(new BorderLayout());
        addEventPanel.setBorder(BorderFactory.createTitledBorder("Add event"));

        JPanel leftPanel = createLeftAddEventPanel();
        JPanel rightPanel = createRightAddEventPanel();

        JTextField nameField = new JTextField("", 15);
        TimePanel startTime = createTimePanel(true);
        TimePanel endTime = createTimePanel(false);
        JComboBox<CategoryItem> categoryDropdown = createCategoryDropdown();
        JComboBox<CategoryItem> subcategoryDropdown = createSubcategoryDropdown();
        JPanel recurringDaysPanel = createRecurringDaysPanel();

        addComponentsToRightPanel(rightPanel, nameField, startTime, endTime,
                                   categoryDropdown, subcategoryDropdown, recurringDaysPanel);

        addTimeListeners(startTime, endTime);

        JButton submitButton = createSubmitButton(startTime.hour, startTime.minute, endTime.hour,
                                                    endTime.minute, categoryDropdown, subcategoryDropdown,
                                                    nameField, recurringDaysPanel);

        addPanelsToAddEventPanel(addEventPanel, leftPanel, rightPanel, submitButton);

        return addEventPanel;
    }

    // MODIFIES: rightPanel
    // EFFECTS: adds the provided components to the rightPanel.
    private void addComponentsToRightPanel(JPanel rightPanel, JTextField nameField,
            TimePanel startTime, TimePanel endTime, 
            JComboBox<CategoryItem> categoryDropdown, JComboBox<CategoryItem> subcategoryDropdown,
            JPanel recurringDaysPanel) {
        rightPanel.add(nameField);
        rightPanel.add(startTime.panel);
        rightPanel.add(endTime.panel);
        rightPanel.add(categoryDropdown);
        rightPanel.add(subcategoryDropdown);
        rightPanel.add(recurringDaysPanel);
    }

    // MODIFIES: startTime, endTime
    // EFFECTS: adds listeners to the time dropdowns to update end time options based on the start time
    private void addTimeListeners(TimePanel startTime, TimePanel endTime) {
        startTime.hour.addActionListener(new UpdateEndTimeDropdown(startTime.hour, startTime.minute,
                                                                   endTime.hour, endTime.minute));
        startTime.minute.addActionListener(new UpdateEndTimeDropdown(startTime.hour, startTime.minute,
                                                                     endTime.hour, endTime.minute));
        endTime.hour.addActionListener(new EndHourListener(startTime.hour, startTime.minute,
                                                           endTime.hour, endTime.minute));
    }

    // MODIFIES: addEventPanel
    // EFFECTS: adds the leftPanel, rightPanel, and submitButton to the addEventPanel
    private void addPanelsToAddEventPanel(JPanel addEventPanel, JPanel leftPanel, JPanel rightPanel,
                                          JButton submitButton) {
        addEventPanel.add(leftPanel, BorderLayout.WEST);
        addEventPanel.add(rightPanel, BorderLayout.EAST);
        addEventPanel.add(submitButton, BorderLayout.SOUTH);
    }

    // EFFECTS: creates and returns a left panel for the add event view containing labels for event fields
    private JPanel createLeftAddEventPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(new JLabel("Name: "));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(new JLabel("Start Time: "));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(new JLabel("End Time: "));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(new JLabel("Category: "));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(new JLabel("Subcategory: "));
        leftPanel.add(Box.createVerticalStrut(10));
        return leftPanel;
    }

    // EFFECTS: creates and returns a right panel for the add event view
    private JPanel createRightAddEventPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        return rightPanel;
    }

    // EFFECTS: creates and returns a TimePanel containing dropdowns for hours and minutes
    //          If isStart is false, disables the hour and minute dropdowns
    private TimePanel createTimePanel(boolean isStart) {
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
        Integer[] hours = new Integer[24];
        for (int i = 0; i < 24; i++) { 
            hours[i] = i; 
        }
        String[] minutes = { "00", "30" };
        JComboBox<Integer> hourCombo = isStart ? new JComboBox<>(hours) : new JComboBox<>();
        JComboBox<String> minuteCombo = new JComboBox<>(minutes);
        if (!isStart) {
            hourCombo.setEnabled(false);
            minuteCombo.setEnabled(false);
        }
        timePanel.add(hourCombo);
        timePanel.add(new JLabel(":"));
        timePanel.add(minuteCombo);
        return new TimePanel(timePanel, hourCombo, minuteCombo);
    }

    // EFFECTS: creates and returns a dropdown populated with categories,
    //          including a "None" option.
    private JComboBox<CategoryItem> createCategoryDropdown() {
        JComboBox<CategoryItem> dropdown = new JComboBox<>();
        dropdown.addItem(new CategoryItem(-1, "None"));
        for (Category category : calendar.getCategories()) {
            dropdown.addItem(new CategoryItem(category.getId(), category.getName()));
        }
        return dropdown;
    }

    // EFFECTS: creates and returns a dropdown populated with subcategories,
    //          including a "None" option.
    private JComboBox<CategoryItem> createSubcategoryDropdown() {
        JComboBox<CategoryItem> dropdown = new JComboBox<>();
        dropdown.addItem(new CategoryItem(-1, "None"));
        for (Category subcategory : calendar.getSubcategories()) {
            dropdown.addItem(new CategoryItem(subcategory.getId(), subcategory.getName()));
        }
        return dropdown;
    }

    // EFFECTS: creates and returns a submit button with an attached action listener
    //          to handle event submission.
    private JButton createSubmitButton(JComboBox<Integer> startHour, JComboBox<String> startMinute,
                                    JComboBox<Integer> endHour, JComboBox<String> endMinute,
                                    JComboBox<CategoryItem> categoryDropdown,
                                    JComboBox<CategoryItem> subcategoryDropdown,
                                    JTextField nameField, JPanel recurringDaysPanel) {
        JButton submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.addActionListener(new SubmitButtonListener(startHour, startMinute, endHour, 
                                                                endMinute, categoryDropdown, 
                                                                subcategoryDropdown, nameField, 
                                                                recurringDaysPanel));
        return submitButton;
    }

    // EFFECTS: creates and returns a panel for selecting the recurring days options for an event
    private JPanel createRecurringDaysPanel() {
        JPanel recurringDaysPanel = new JPanel();
        recurringDaysPanel.setLayout(new BoxLayout(recurringDaysPanel, BoxLayout.Y_AXIS));

        JRadioButton onceButton = new JRadioButton("Once");
        JRadioButton dailyButton = new JRadioButton("Repeat Every Day");
        JRadioButton weeklyButton = new JRadioButton("Repeat Every Week");

        createButtonGroup(onceButton, dailyButton, weeklyButton);

        recurringDaysPanel.add(onceButton);
        recurringDaysPanel.add(dailyButton);
        recurringDaysPanel.add(weeklyButton);

        JPanel durationPanel = new JPanel();
        durationPanel.setLayout(new BoxLayout(durationPanel, BoxLayout.X_AXIS));

        JComboBox<Integer> durationDropdown = new JComboBox<>();
        for (int i = 1; i <= 30; i++) {
            durationDropdown.addItem(i);
        }

        onceButton.addActionListener((ActionEvent e) -> durationDropdown.setEnabled(false));
        dailyButton.addActionListener((ActionEvent e) -> durationDropdown.setEnabled(true));
        weeklyButton.addActionListener((ActionEvent e) -> durationDropdown.setEnabled(true));

        durationPanel.add(new JLabel("Duration: "));
        durationPanel.add(durationDropdown);

        durationDropdown.setEnabled(false);

        recurringDaysPanel.add(durationPanel);

        return recurringDaysPanel;
    }

    // EFFECTS: creates a button group for the given radio buttons and sets the default selection to onceButton.
    private void createButtonGroup(JRadioButton onceButton, JRadioButton dailyButton, JRadioButton weeklyButton) {
        ButtonGroup group = new ButtonGroup();
        group.add(onceButton);
        group.add(dailyButton);
        group.add(weeklyButton);
        onceButton.setSelected(true);
    }

    // REQUIRES: modelPanel is the recurringDaysPanel in the addEventPanel.
    // EFFECTS: returns a list of recurring days for the event based on the selected recurring option
    //          and duration specified in the modelPanel.
    @SuppressWarnings("all")
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
                        addDaysDaily(recurringDays, duration);
                    } else if (name.equals("Repeat Every Week")) {
                        addDaysWeekly(recurringDays, duration);
                    }

                    recurringDays.add(currentDay);
                }
            }
        }
        return recurringDays;
    }

    // REQUIRES: duration > 0
    // MODIFIES: recurringDays
    // EFFECTS: adds to recurringDays the days that follow currentDay on a daily basis,
    //          up to the given duration, if such days exist in the calendar.
    private void addDaysDaily(List<Day> recurringDays, int duration) {
        Day currentDay = calendar.getCurrentYear().getCurrentMonth().getCurrentDay();

        for (int i = 1; i <= duration; i++) {
            Day offsetDay = calendar.fromOffset(currentDay, i);
            if (offsetDay != null) {
                recurringDays.add(offsetDay);
            }
        }
    }

    // REQUIRES: duration > 0
    // MODIFIES: recurringDays
    // EFFECTS: adds to recurringDays the days that follow currentDay on a weekly basis,
    //          up to the given duration, if such days exist in the calendar.
    private void addDaysWeekly(List<Day> recurringDays, int duration) {
        Day currentDay = calendar.getCurrentYear().getCurrentMonth().getCurrentDay();

        for (int i = 1; i <= duration; i++) {
            Day offsetDay = calendar.fromOffset(currentDay, i * 7);
            if (offsetDay != null) {
                recurringDays.add(offsetDay);
            }
        }
    }

    // REQUIRES: 0 <= startHour <= 23, 0 <= startMinute < 60,
    // MODIFIES: endHour, endMinute
    // EFFECTS: updates the endMinute dropdown based on the provided start time and selected end hour
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
    // EFFECTS: resets the DayPanel by removing all components and rebuilding the panel layout
    private void reset() {
        this.removeAll();
        this.add(createPanel());
        revalidate();
        repaint();
    }

    @SuppressWarnings("all")
    private class ViewEventAction extends AbstractAction {
        // EFFECTS: handles the action event for viewing an event
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            int id = ((EventItem) comboBox.getSelectedItem()).getId();
            rightPanel.removeAll();

            rightPanel.add(createViewEventPanel(calendar.getEvent(id)), BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    @SuppressWarnings("all")
    private class RemoveEventAction extends AbstractAction {
        // EFFECTS: handles the action event for removing an event
        @Override
        public void actionPerformed(ActionEvent e) {
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
        // EFFECTS: handles the action event for adding a new event
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

        // EFFECTS: returns the name of the event for display in the combo box
        @Override
        public String toString() {
            return name;
        }
    }

    // Custom class so categories/subcategories can be stored by id in the drop box menus
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

        // EFFECTS: returns the name of the category for display in the combo box.
        @Override
        public String toString() {
            return name;
        }
    }

    private class UpdateEndTimeDropdown extends AbstractAction {
        private JComboBox<Integer> startHour;
        private JComboBox<String> startMinute;
        private JComboBox<Integer> endHour;
        private JComboBox<String> endMinute;

        // EFFECTS: constructs an UpdateEndTimeDropdown with the specified dropdown components
        UpdateEndTimeDropdown(JComboBox<Integer> startHour, JComboBox<String> startMinute, 
                                JComboBox<Integer> endHour, JComboBox<String> endMinute) {
            this.startHour = startHour;
            this.startMinute = startMinute;
            this.endHour = endHour;
            this.endMinute = endMinute;
        }
        
        // EFFECTS: handles the action event by enabling and updating the end time dropdowns 
        //          based on the selected start time
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
    }

    private class EndHourListener extends AbstractAction {
        private JComboBox<Integer> startHour;
        private JComboBox<String> startMinute;
        private JComboBox<Integer> endHour;
        private JComboBox<String> endMinute;

        // EFFECTS: constructs an EndHourListener with the specified dropdown components
        EndHourListener(JComboBox<Integer> startHour, JComboBox<String> startMinute, 
                        JComboBox<Integer> endHour, JComboBox<String> endMinute) {
            this.startHour = startHour;
            this.startMinute = startMinute;
            this.endHour = endHour;
            this.endMinute = endMinute;
        }
        
        // EFFECTS: handles the action event by updating the end minute dropdown based on the new end hour selection
        @Override
        public void actionPerformed(ActionEvent e) {
            int startHourSelected = (int) startHour.getSelectedItem();
            int startMinuteSelected = Integer.parseInt((String) startMinute.getSelectedItem());
            updateEndMinuteDropdown(startHourSelected, startMinuteSelected, endHour, endMinute);
        }
    }

    private class SubmitButtonListener extends AbstractAction {
        private JComboBox<Integer> startHour;
        private JComboBox<String> startMinute;
        private JComboBox<Integer> endHour;
        private JComboBox<String> endMinute;
        private JComboBox<CategoryItem> categoryDropdown;
        private JComboBox<CategoryItem> subcategoryDropdown;
        private JTextField nameField;
        private JPanel recurringDaysPanel;

        // EFFECTS: constructs a SubmitButtonListener with the provided UI components.
        SubmitButtonListener(JComboBox<Integer> startHour, JComboBox<String> startMinute, 
                             JComboBox<Integer> endHour, JComboBox<String> endMinute,
                             JComboBox<CategoryItem> categoryDropdown, JComboBox<CategoryItem> subcategoryDropdown,
                             JTextField nameField, JPanel recurringDaysPanel) {
            this.startHour = startHour;
            this.startMinute = startMinute;
            this.endHour = endHour;
            this.endMinute = endMinute;
            this.categoryDropdown = categoryDropdown;
            this.subcategoryDropdown = subcategoryDropdown;
            this.nameField = nameField;
            this.recurringDaysPanel = recurringDaysPanel;
        }
        
        // EFFECTS: handles submitting a new event based on user input
        @Override
        public void actionPerformed(ActionEvent e) {
            if (canSubmit()) {
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

        // EFFECTS: returns true if any of the time dropdowns have no selection, false otherwise.
        public boolean canSubmit() {
            return startHour.getSelectedItem() == null || startMinute.getSelectedItem() == null 
                    || endHour.getSelectedItem() == null || endMinute.getSelectedItem() == null;
        }
    }

}
