package model;

// A Calendar with a title that stores a list of months and a current month
public class Calendar {

    private String title;
    private Month[] months;
    private int currentMonthIndex;

    // EFFECTS: initializes a new Calendar with a given title
    public Calendar(String title) {
        this.title = title;
        currentMonthIndex = 0;
        months = new Month[12];

        for(int i = 0; i < 12; i++) {
            months[i] = new Month(i);
        }
    }

    // MODIFIES: this
    // EFFECTS: increments the month index if it does not exceed 11, nothing otherwise
    public void incrementMonthIndex() {
        currentMonthIndex++;
        
        if(currentMonthIndex > 11) {
            currentMonthIndex = 11;
        }
    }

    // MODIFIES: this
    // EFFECTS: decrements the month index if it does not go below 0, nothing otherwise
    public void decrementMonthIndex() {
        currentMonthIndex--;

        if(currentMonthIndex < 0) {
            currentMonthIndex = 0;
        }
    }

    /*
     * GETTERS/SETTERS
     */
    
    public String getTitle() {
        return title;
    }

    public Month[] getMonths() {
        return months;
    }

    public int getCurrentMonthIndex() {
        return currentMonthIndex;
    }
    
}
