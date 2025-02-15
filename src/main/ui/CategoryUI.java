package ui;

import model.Calendar;
import model.Category;
import model.Subcategory;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

// The CategoryUI manages the UI for navigating and interacting with categories, 
// allowing users to add and removes categories and subcategories.
public class CategoryUI extends UI {

    // fields
    private Calendar calendar;
    private List<Category> categories;
    private List<Subcategory> subcategories;
    private Scanner scanner;

    // EFFECTS: initializes this CategoryUI with the given calendar, and an empty list of categories and subcategories
    public CategoryUI(Calendar calendar) {
        this.calendar = calendar;
        categories = new ArrayList<Category>();
        subcategories = new ArrayList<Subcategory>();
        scanner = new Scanner(System.in);
    }

    // EFFECTS: starts the Category Manage loop which displays the Category Manage Menu
    public void startCategoryMenu() {
        boolean isRunning = true;
        String prompt = "Enter the corresponding number or type 'back' to return: ";

        while (isRunning) {
            displayCategoryMenu();

            if (handleCategoryMenuInput(getChoice(1, 4, prompt, true))) {
                isRunning = false;
            }

        }
    }

    // EFFECTS: conducts the process to add a category
    private Category startAddCategoryProcess() {
        String name;
        String location;
        List<String> links = new ArrayList<String>();
        List<String> notes = new ArrayList<String>();

        System.out.print("Enter the name: ");
        name = scanner.nextLine();

        System.out.print("\nEnter the location: ");
        location = scanner.nextLine();

        System.out.print("\nEnter the links, separated by commas: ");

        String[] temp1 = scanner.nextLine().split(",");

        for (String link: temp1) {
            links.add(link.trim());
        }

        System.out.print("\nEnter the notes, separated by commas: ");

        String[] temp2 = scanner.nextLine().split(",");

        for (String note: temp2) {
            notes.add(note.trim());
        }

        Category category = new Category(name, subcategories, location, links, notes);

        makeWhiteSpace();

        return category;
    }

    // EFFECTS: starts the remove category loop which displays the remove category menu
    private void startRemoveCategoryMenu() {
        boolean isRunning = true;
        String prompt = "Enter the corresponding number to remove or type 'back' to return: ";

        while (isRunning) {
            displayRemoveCategoryMenu();
            int lower = 1;
            int upper = categories.size();

            if (!categories.isEmpty()) {
                if (handleRemoveCategoryMenuInput(getChoice(lower, upper, prompt, true))) {
                    isRunning = false;
                }
            } else {
                if (handleRemoveCategoryMenuInput(getBackChoice(scanner))) {
                    isRunning = false;
                }

            }

        }
    }

    // MODIFIES: this, calendar
    // EFFECTS: conducts the process to add a subcategory
    private void startAddSubcategoryProcess() {
        String prompt = "Enter the corresponding number: ";
        Category parentCategory;
        int priorityLevel;
        Category modelCategory;

        int lower = 1;
        int upper = categories.size();

        if (!categories.isEmpty()) {
            displayAddParentCategoryMenu();
            parentCategory = categories.get(getChoice(lower, upper, prompt, false) - 1);
        } else {
            System.out.println("You cannot add a subcategory without a category!\n");
            return;
        }

        displayAddPriorityLevelMenu();

        priorityLevel = getChoice(1, 3, "Enter the corresponding number: ", false);
        System.out.println();

        modelCategory = startAddCategoryProcess();
        Subcategory subcategory = new Subcategory(parentCategory, priorityLevel, null, 
                                                    modelCategory.getName(),  modelCategory.getLocation(), 
                                                    modelCategory.getLinks(), modelCategory.getNotes());
        calendar.addSubcategory(subcategory);
        subcategories.add(subcategory);

    }

    // EFFECTS: starts the remove subcategory loop which displays the remove subcategory menu
    private void startRemoveSubcategoryMenu() {
        boolean isRunning = true;
        String prompt = "Enter the corresponding number to remove or type 'back' to return: ";

        while (isRunning) {
            displayRemoveSubcategoryMenu();
            int lower = 1;
            int upper = subcategories.size();

            if (!subcategories.isEmpty()) {
                if (handleRemoveSubcategoryMenuInput(getChoice(lower, upper, prompt, true))) {
                    isRunning = false;
                }
            } else {
                if (handleRemoveSubcategoryMenuInput(getBackChoice(scanner))) {
                    isRunning = false;
                }

            }

        }
    }

    // EFFECTS: displays the Manage Category menu
    private void displayCategoryMenu() {
        System.out.println("--- Manage Categories ---");
        System.out.println("1. Add Category");
        System.out.println("2. Remove Category");
        System.out.println("3. Add Subcategory");
        System.out.println("4. Remove Subcategory\n");
    }

    // EFFECTS: displays the Remove Category menu
    private void displayRemoveCategoryMenu() {
        System.out.println("--- Remove Category ---\n");

        if (!categories.isEmpty()) {
            for (int i = 0; i < categories.size(); i++) {
                Category category = categories.get(i);
                System.out.println((i + 1) + ". " + category.getName());
                
            }
            System.out.println();
        } else {
            System.out.println("No categories exist.\n");
            System.out.print("Type 'back' to return: ");
        }
    }

    // EFFECTS: displays the add parent category menu
    private void displayAddParentCategoryMenu() {
        System.out.println("--- Add Subcategory ---");
        System.out.println("Select a parent category:\n");

        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }

        System.out.println();
    }

    // EFFECTS: displays the add priority level menu
    private void displayAddPriorityLevelMenu() {
        System.out.println("\nSelect priority level:\n");
        System.out.println("1. Low");
        System.out.println("2. Medium");
        System.out.println("3. High\n");
    }

    // EFFECTS: displays the remove subcategory menu
    private void displayRemoveSubcategoryMenu() {
        System.out.println("--- Remove Subcategory ---\n");

        if (!subcategories.isEmpty()) {
            for (int i = 0; i < subcategories.size(); i++) {
                Category subcategory = subcategories.get(i);
                System.out.println((i + 1) + ". " + subcategory.getName());
                
            }
            System.out.println();
        } else {
            System.out.println("No subcategories exist.\n");
            System.out.print("Type 'back' to return: ");
        }
    }

    // REQUIRES: choice is valid integer on the category menu or it is -1
    // MODIFIES: this, calendar
    // EFFECTS: processes the user's choice from the category menu:
    // 1. Adds a new category
    // 2. Starts the category removal process
    // 3. Adds a new subcategory
    // 4. Starts the subcategory removal process
    // or -1 which exits the menu (produces true)
    private boolean handleCategoryMenuInput(int choice) {
        makeWhiteSpace();

        if (choice == -1) {
            return true;
        } else if (choice == 1) {
            System.out.println("--- Add Category ---");
            Category category = startAddCategoryProcess();
            calendar.addCategory(category);
            categories.add(category);

            return false;
        } else if (choice == 2) {
            startRemoveCategoryMenu();
            return false;
        } else if (choice == 3) {
            startAddSubcategoryProcess();
            return false;
        } else if (choice == 4) {
            startRemoveSubcategoryMenu();
            return false;
        }

        return false;
    }

    // REQUIRES: choice is valid integer on the remove category menu or it is -1
    // MODIFIES: this, calendar
    // EFFECTS: removes the category at the specified choice from categories, if -1 exit the menu (produces true)
    private boolean handleRemoveCategoryMenuInput(int choice) {
        makeWhiteSpace();

        if (choice == -1) {
            return true;
        }

        Category removedCategory = categories.get(choice - 1);

        calendar.removeCategory(removedCategory);
        categories.remove(choice - 1);

        return false;
    }

    // REQUIRES: choice is valid integer on the remove subcategory menu or it is -1
    // MODIFIES: this, calendar
    // EFFECTS: removes the subcategory at the specified choice from subcategories, if -1 exit the menu (produces true)
    private boolean handleRemoveSubcategoryMenuInput(int choice) {
        makeWhiteSpace();

        if (choice == -1) {
            return true;
        }

        calendar.removeSubcategory(subcategories.get(choice - 1));
        subcategories.remove(choice - 1);

        return false;
    }

}
