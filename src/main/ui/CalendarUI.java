package ui;

import model.Calendar;
import model.Category;
import model.Subcategory;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

// Represents application's main window frame
class CalendarUI extends JFrame {

    // fields
    private static final String JSON_STORE = "./data/calendar.json";
    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;
    private Calendar calendar;
    private JPanel mainPanel;
    private YearPanel yearPanel;
    private MonthPanel monthPanel;
    private DayPanel dayPanel;
    private TitlePanel titlePanel;
    private JPanel contentPanel;
    private JPanel categoryPanel;
    private CalendarController calendarController;
    JMenuBar menuBar;
    JsonWriter jsonWriter;
    JsonReader jsonReader;

    // EFFECTS: initializes the CalendarUI frame, configures panels, and displays the main window
    public CalendarUI(Calendar calendar) {
        this.calendar = calendar;
        setTitle("Calendar UI");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeCalendarUI();
    }

    // EFFECTS: initializes all panels, menu bar, and finalizes the UI
    public void initializeCalendarUI() {
        initializeMainPanels();
        initializeMenuBar();
        finalizeUI();
    }

    // MODIFIES: this
    // EFFECTS: initializes main panels and associates the calendar with its controller
    private void initializeMainPanels() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        categoryPanel = new JPanel();
        categoryPanel.add(createCategoryPanel());
        mainPanel.add(categoryPanel, BorderLayout.SOUTH);

        calendarController = new CalendarController(calendar, this);
        titlePanel = new TitlePanel(calendar, calendarController, this, calendar.getCurrentYear().getYearNumber());
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        yearPanel = new YearPanel(calendar, calendarController);
        monthPanel = new MonthPanel(calendar, calendarController);
        dayPanel = new DayPanel(calendar);
        calendarController.setFields(titlePanel, yearPanel, monthPanel);

        contentPanel.add(yearPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu bar with save and load options
    private void initializeMenuBar() {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new SaveMenuListener());
        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.addActionListener(new LoadMenuListener());
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: finalizes UI setup by making the frame visible and revalidating/repainting panels
    private void finalizeUI() {
        setVisible(true);
        try {
            setIconImage(ImageIO.read(new File("bin/calendaricon.png")));
        } catch (IOException ioe) {
            // no image
        }
        mainPanel.revalidate();
        contentPanel.revalidate();
        titlePanel.revalidate();
        mainPanel.repaint();
        contentPanel.repaint();
        titlePanel.repaint();
    }

    // MODIFIES: contentPanel, yearPanel
    // EFFECTS: displays all months in the current year as buttons for the user to select
    public void displayYearSelection() {
        contentPanel.removeAll();
        categoryPanel.setVisible(true);
        yearPanel = new YearPanel(calendar, calendarController);
        contentPanel.add(yearPanel, BorderLayout.CENTER);
        finalizeUI();
    }

    // MODIFIES: contentPanel, categoryPanel
    // EFFECTS: displays all days in the current month as buttons for the user to select
    public void displayMonthSelection() {
        contentPanel.removeAll();
        categoryPanel.setVisible(false);
        monthPanel = new MonthPanel(calendar, calendarController);
        contentPanel.add(monthPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // MODIFIES: contentPanel, dayPanel
    // EFFECTS: displays day view for the current day in the calendar
    public void displayDaySelection() {
        contentPanel.removeAll();
        dayPanel = new DayPanel(calendar);
        contentPanel.add(dayPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // EFFECTS: creates and returns the category management panel
    public JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.add(createAddCategoryCard());
        categoryPanel.add(createRemoveCategoryPanel());
        categoryPanel.add(createAddSubcategoryCard());
        categoryPanel.add(createRemoveSubcategoryPanel());
        return categoryPanel;
    }

    // EFFECTS: creates and returns the add-category card panel
    private JPanel createAddCategoryCard() {
        JPanel addCategoryCard = new JPanel(new CardLayout());
        JPanel addCategoryView = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addCategoryButton = new JButton("Add Category");
        addCategoryView.add(addCategoryButton);

        JPanel addCategoryInput = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addCategoryInput.add(new JLabel("Name: "));
        JTextField categoryNameField = new JTextField(15);
        addCategoryInput.add(categoryNameField);
        JButton submitCategoryButton = new JButton("Submit");
        addCategoryInput.add(submitCategoryButton);

        addCategoryCard.add(addCategoryView, "view");
        addCategoryCard.add(addCategoryInput, "input");

        addCategoryButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) addCategoryCard.getLayout();
            cl.show(addCategoryCard, "input");
        });

        submitCategoryButton.addActionListener(e -> 
                handleSubmitCategoryAction(categoryNameField, addCategoryCard)
        );
        return addCategoryCard;
    }

    // MODIFIES: this
    // EFFECTS: handles the submit action for adding a category
    private void handleSubmitCategoryAction(JTextField categoryNameField, JPanel addCategoryCard) {
        String categoryName = categoryNameField.getText().trim();
        if (!categoryName.isEmpty()) {
            calendar.addCategory(new Category(categoryName));
        }
        categoryNameField.setText("");
        CardLayout cl = (CardLayout) addCategoryCard.getLayout();
        cl.show(addCategoryCard, "view");
        getContentPane().removeAll();
        initializeCalendarUI();
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    // EFFECTS: creates and returns the remove-category panel
    private JPanel createRemoveCategoryPanel() {
        JPanel removeCategoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        removeCategoryPanel.add(new JLabel("Remove Category: "));
        JComboBox<CategoryItem> removeCategoryDropdown = new JComboBox<>();
        for (Category category : calendar.getCategories()) {
            removeCategoryDropdown.addItem(new CategoryItem(category.getId(), category.getName()));
        }
        removeCategoryDropdown.addActionListener(e -> 
                handleRemoveCategoryAction(removeCategoryDropdown)
        );
        removeCategoryPanel.add(removeCategoryDropdown);
        return removeCategoryPanel;
    }

    // MODIFIES: this
    // EFFECTS: handles removal of a selected category
    private void handleRemoveCategoryAction(JComboBox<CategoryItem> removeCategoryDropdown) {
        int id = ((CategoryItem) removeCategoryDropdown.getSelectedItem()).getId();
        calendar.removeCategory(calendar.getCategory(id));
        getContentPane().removeAll();
        initializeCalendarUI();
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    // EFFECTS: creates and returns the add-subcategory card panel
    private JPanel createAddSubcategoryCard() {
        JPanel addSubcategoryCard = new JPanel(new CardLayout());
        JPanel addSubcategoryView = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addSubcategoryButton = new JButton("Add Subcategory");
        addSubcategoryView.add(addSubcategoryButton);

        JPanel addSubcategoryInput = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addSubcategoryInput.add(new JLabel("Name: "));
        JTextField subcategoryNameField = new JTextField(15);
        addSubcategoryInput.add(subcategoryNameField);
        JButton submitSubcategoryButton = new JButton("Submit");
        addSubcategoryInput.add(submitSubcategoryButton);

        addSubcategoryCard.add(addSubcategoryView, "view");
        addSubcategoryCard.add(addSubcategoryInput, "input");

        addSubcategoryButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) addSubcategoryCard.getLayout();
            cl.show(addSubcategoryCard, "input");
        });

        submitSubcategoryButton.addActionListener(e -> 
                handleSubmitSubcategoryAction(subcategoryNameField, addSubcategoryCard)
        );
        return addSubcategoryCard;
    }

    // MODIFIES: this
    // EFFECTS: handles the submit action for adding a subcategory
    private void handleSubmitSubcategoryAction(JTextField subcategoryNameField, JPanel addSubcategoryCard) {
        String subcategoryName = subcategoryNameField.getText().trim();
        if (!subcategoryName.isEmpty()) {
            calendar.addSubcategory(new Subcategory(subcategoryName));
        }
        subcategoryNameField.setText("");
        CardLayout cl = (CardLayout) addSubcategoryCard.getLayout();
        cl.show(addSubcategoryCard, "view");
        getContentPane().removeAll();
        initializeCalendarUI();
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    // EFFECTS: creates and returns the remove-subcategory panel
    private JPanel createRemoveSubcategoryPanel() {
        JPanel removeSubcategoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        removeSubcategoryPanel.add(new JLabel("Remove Subcategory: "));
        JComboBox<CategoryItem> removeSubcategoryDropdown = new JComboBox<>();
        for (Subcategory subcategory : calendar.getSubcategories()) {
            removeSubcategoryDropdown.addItem(new CategoryItem(subcategory.getId(), subcategory.getName()));
        }
        removeSubcategoryDropdown.addActionListener(e -> 
                handleRemoveSubcategoryAction(removeSubcategoryDropdown)
        );
        removeSubcategoryPanel.add(removeSubcategoryDropdown);
        return removeSubcategoryPanel;
    }

    // MODIFIES: this
    // EFFECTS: handles removal of a selected subcategory
    private void handleRemoveSubcategoryAction(JComboBox<CategoryItem> removeSubcategoryDropdown) {
        int id = ((CategoryItem) removeSubcategoryDropdown.getSelectedItem()).getId();
        calendar.removeSubcategory(calendar.getSubcategory(id));
        getContentPane().removeAll();
        initializeCalendarUI();
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    // Inner classes for Save and Load actions
    
    private class SaveMenuListener extends AbstractAction {
        // EFFECTS: handles saving the calendar
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                calendar.setIds();
                jsonWriter.open();
                jsonWriter.write(calendar);
                jsonWriter.close();
                getContentPane().removeAll();
                initializeCalendarUI();
                getContentPane().revalidate();
                getContentPane().repaint();
            } catch (FileNotFoundException x) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    private class LoadMenuListener extends AbstractAction {
        // EFFECTS: handles loading the calendar
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                calendar = jsonReader.read();
                getContentPane().removeAll();
                initializeCalendarUI();
                getContentPane().revalidate();
                getContentPane().repaint();
            } catch (IOException x) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // custom class so categories/subcategories can be stored by id in the drop box menus
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

        // EFFECTS: returns the name for display in combo boxes
        @Override
        public String toString() {
            return name;
        }
    }
}
