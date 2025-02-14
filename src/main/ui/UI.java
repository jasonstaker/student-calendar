package ui;

import java.util.Scanner;

public class UI {

    Scanner scanner = new Scanner(System.in);

    // EFFECTS: prints out a basic space between UI's
    public void makeWhiteSpace() {
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println();
        System.out.println();
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    // REQUIRES: lower <= upper
    // EFFECTS: gets a valid choice from the user, if invalid it prompts and loops
    public int getChoice(int lower, int upper, String prompt) {
        System.out.print("Choose an option: ");
    
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
    
        int input = scanner.nextInt();
    
        while (input < lower || input > upper) {
            if(lower == upper) {
                System.out.print("Please enter a valid number");
            } else {
            System.out.print("Please enter a number between " + lower + " and " + upper + ": ");
            }

            while (!scanner.hasNextInt()) {
                if(lower == upper) {
                    System.out.print("Invalid input. Please enter a valid number");
                } else {
                System.out.print("Invalid input. Please enter a number between " + lower + " and " + upper + ": ");
                }
                scanner.next();
            }
            input = scanner.nextInt();
        }

        return input;
    }

}
