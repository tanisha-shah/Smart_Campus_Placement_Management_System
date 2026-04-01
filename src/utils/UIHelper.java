package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

// UIHelper - prints formatted menus and messages to the terminal
public class UIHelper {

    // Print a line of dashes
    public static void printLine() {
        System.out.println("--------------------------------------------------");
    }

    // Print a thick line of equals signs
    public static void printThickLine() {
        System.out.println("==================================================");
    }

    // Print a heading with borders
    public static void printHeading(String title) {
        printThickLine();
        System.out.println("  " + title);
        printThickLine();
    }

    // Print a sub-heading
    public static void printSubHeading(String title) {
        printLine();
        System.out.println("  >> " + title);
        printLine();
    }

    // Print success message
    public static void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    // Print error message
    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    // Print info message
    public static void printInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    // Print warning message
    public static void printWarning(String message) {
        System.out.println("[WARNING] " + message);
    }

    // Get today's date as a string
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date());
    }

    // Generate a unique ID based on timestamp
    public static String generateId(String prefix) {
        long time = System.currentTimeMillis();
        return prefix + time;
    }

    // Print the main welcome banner
    public static void printWelcomeBanner() {
        System.out.println();
        printThickLine();
        System.out.println("  ****   SMART CAMPUS PLACEMENT SYSTEM   ****");
        System.out.println("        Your Gateway to Dream Careers");
        printThickLine();
        System.out.println();
    }

    // Print a simple numbered menu item
    public static void printMenuItem(int number, String item) {
        System.out.println("  " + number + ". " + item);
    }

    // Print "Enter choice:" prompt
    public static void printChoicePrompt() {
        System.out.print("  Enter your choice: ");
    }

    // Print empty line for spacing
    public static void printBlankLine() {
        System.out.println();
    }
}
