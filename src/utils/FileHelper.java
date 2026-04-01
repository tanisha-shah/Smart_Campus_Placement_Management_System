package utils;

import java.io.*;
import java.util.ArrayList;

// FileHelper - utility class for all file read/write operations
// This handles saving and loading data from text files
public class FileHelper {

    // Base directory where all data files will be stored
    private static final String DATA_DIR = "data/";

    // File names for each type of data
    public static final String STUDENTS_FILE = DATA_DIR + "students.txt";
    public static final String COMPANIES_FILE = DATA_DIR + "companies.txt";
    public static final String ADMINS_FILE = DATA_DIR + "admins.txt";
    public static final String DRIVES_FILE = DATA_DIR + "drives.txt";
    public static final String APPLICATIONS_FILE = DATA_DIR + "applications.txt";
    public static final String NOTIFICATIONS_FILE = DATA_DIR + "notifications.txt";

    // Create data directory and files if they don't exist
    public static void initializeFiles() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }

        // Create each file if it doesn't exist
        createFileIfNotExists(STUDENTS_FILE);
        createFileIfNotExists(COMPANIES_FILE);
        createFileIfNotExists(ADMINS_FILE);
        createFileIfNotExists(DRIVES_FILE);
        createFileIfNotExists(APPLICATIONS_FILE);
        createFileIfNotExists(NOTIFICATIONS_FILE);

        // Create default admin if admin file is empty
        createDefaultAdmin();
    }

    // Helper to create a file if it doesn't already exist
    private static void createFileIfNotExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating file: " + filePath);
            }
        }
    }

    // Create a default admin account on first run
    private static void createDefaultAdmin() {
        ArrayList<String> lines = readAllLines(ADMINS_FILE);
        if (lines.isEmpty()) {
            // Default admin: admin@campus.edu, password: admin123
            String defaultAdmin = "ADMIN001,Campus Admin,admin@campus.edu,admin123,ADMIN,ADMIN2024";
            writeLineToFile(ADMINS_FILE, defaultAdmin);
        }
    }

    // Read all lines from a file and return as ArrayList
    public static ArrayList<String> readAllLines(String filePath) {
        ArrayList<String> lines = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            // File might not exist yet, return empty list
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return lines;
    }

    // Append a single line to a file
    public static void writeLineToFile(String filePath, String line) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(filePath, true)); // true = append mode
            writer.println(line);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + filePath);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    // Overwrite entire file with new list of lines (used for updates)
    public static void writeAllLines(String filePath, ArrayList<String> lines) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(filePath, false)); // false = overwrite mode
            for (int i = 0; i < lines.size(); i++) {
                writer.println(lines.get(i));
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + filePath);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    // Check if a file exists and is not empty
    public static boolean fileHasData(String filePath) {
        ArrayList<String> lines = readAllLines(filePath);
        return !lines.isEmpty();
    }
}
