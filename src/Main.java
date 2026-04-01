import cli.AuthMenu;
import cli.StudentMenu;
import cli.CompanyMenu;
import cli.AdminMenu;
import model.Student;
import model.Company;
import model.Admin;
import service.UserService;
import service.DriveService;
import service.ApplicationService;
import service.NotificationService;
import service.ReportService;
import utils.FileHelper;
import utils.UIHelper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FileHelper.initializeFiles();

        Scanner scanner = new Scanner(System.in);

        UserService userService = new UserService();
        DriveService driveService = new DriveService();
        ApplicationService applicationService = new ApplicationService();
        NotificationService notificationService = new NotificationService();
        ReportService reportService = new ReportService(userService, driveService, applicationService);

        AuthMenu authMenu = new AuthMenu(scanner, userService);

        StudentMenu studentMenu = new StudentMenu(scanner, userService, driveService,
                applicationService, notificationService, reportService);

        CompanyMenu companyMenu = new CompanyMenu(scanner, driveService,
                applicationService, notificationService, userService);

        AdminMenu adminMenu = new AdminMenu(scanner, userService, driveService,
                applicationService, reportService);

        int mainChoice = 0;

        while (mainChoice != 4) {
            UIHelper.printWelcomeBanner();
            System.out.println("  Who are you?");
            UIHelper.printLine();
            UIHelper.printMenuItem(1, "Student");
            UIHelper.printMenuItem(2, "Company");
            UIHelper.printMenuItem(3, "Admin");
            UIHelper.printMenuItem(4, "Exit");
            UIHelper.printLine();
            UIHelper.printChoicePrompt();

            try {
                mainChoice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                UIHelper.printError("Please enter a valid number (1-4).");
                continue;
            }

            if (mainChoice == 1) {
                // === STUDENT SECTION ===
                handleStudentSection(scanner, authMenu, studentMenu);

            } else if (mainChoice == 2) {
                // === COMPANY SECTION ===
                handleCompanySection(scanner, authMenu, companyMenu);

            } else if (mainChoice == 3) {
                // === ADMIN SECTION ===
                handleAdminSection(authMenu, adminMenu);

            } else if (mainChoice == 4) {
                UIHelper.printThickLine();
                System.out.println("  Thank you for using Smart Campus Placement System!");
                System.out.println("  Best of luck with your placements!");
                UIHelper.printThickLine();
            } else {
                UIHelper.printError("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }

        scanner.close();
    }

    // Handle the student section - register or login
    private static void handleStudentSection(Scanner scanner, AuthMenu authMenu,
                                              StudentMenu studentMenu) {
        UIHelper.printBlankLine();
        UIHelper.printHeading("STUDENT PORTAL");
        UIHelper.printMenuItem(1, "Register (New Student)");
        UIHelper.printMenuItem(2, "Login (Existing Student)");
        UIHelper.printMenuItem(3, "Back to Main Menu");
        UIHelper.printLine();
        UIHelper.printChoicePrompt();

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            UIHelper.printError("Invalid input.");
            return;
        }

        if (choice == 1) {
            Student student = authMenu.registerStudent();
            if (student != null) {
                studentMenu.showMenu(student);
            }
        } else if (choice == 2) {
            Student student = authMenu.loginStudent();
            if (student != null) {
                studentMenu.showMenu(student);
            }
        } else if (choice == 3) {
            return;
        } else {
            UIHelper.printError("Invalid choice.");
        }
    }

    // Handle the company section - register or login
    private static void handleCompanySection(Scanner scanner, AuthMenu authMenu,
                                              CompanyMenu companyMenu) {
        UIHelper.printBlankLine();
        UIHelper.printHeading("COMPANY PORTAL");
        UIHelper.printMenuItem(1, "Register (New Company)");
        UIHelper.printMenuItem(2, "Login (Existing Company)");
        UIHelper.printMenuItem(3, "Back to Main Menu");
        UIHelper.printLine();
        UIHelper.printChoicePrompt();

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            UIHelper.printError("Invalid input.");
            return;
        }

        if (choice == 1) {
            Company company = authMenu.registerCompany();
            if (company != null) {
                companyMenu.showMenu(company);
            }
        } else if (choice == 2) {
            Company company = authMenu.loginCompany();
            if (company != null) {
                companyMenu.showMenu(company);
            }
        } else if (choice == 3) {
            return;
        } else {
            UIHelper.printError("Invalid choice.");
        }
    }

    // Handle the admin section - login only
    private static void handleAdminSection(AuthMenu authMenu, AdminMenu adminMenu) {
        Admin admin = authMenu.loginAdmin();
        if (admin != null) {
            adminMenu.showMenu(admin);
        }
    }
}
