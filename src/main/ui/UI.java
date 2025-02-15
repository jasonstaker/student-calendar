package ui;

import java.util.Scanner;

// The UI class provides methods for handling user input and displaying basic whitespace
public class UI {

    // fields
    private Scanner scanner = new Scanner(System.in);

    // EFFECTS: prints out a basic space between UI's
    protected void makeWhiteSpace() {
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println();
        System.out.println();
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    // REQUIRES: lower <= upper
    // EFFECTS: gets a valid choice from the user, if invalid it prompts and loops
    protected int getChoice(int lower, int upper, String prompt, boolean allowBack) {
        System.out.print(prompt);
        
        while (true) {
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                
                if (input >= lower && input <= upper) {
                    return input;
                } else {
                    System.out.print("Please enter a number between " + lower + " and " + upper + ": ");
                }
            } else {
                String inputStr = scanner.next();
                
                if (allowBack && inputStr.equals("back")) {
                    return -1;
                }

                if (allowBack) {
                    System.out.print("Invalid input. Enter a number or type 'back': ");
                } else {
                    System.out.print("Invalid input. Enter a number: ");
                }
                
            }
        }
    }

    // MODFIIES: scanner
    // EFFECTS: returns -1 if 'back' is entered, prompts for valid input otherwise
    protected int getBackChoice(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("back")) {
                return -1;
            }
            System.out.print("Invalid input. Type 'back' to return: ");
        }
    }

}
