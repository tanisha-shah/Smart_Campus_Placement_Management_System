import cli.AdminMenu;
import cli.AuthMenu;
import cli.CompanyMenu;
import cli.StudentMenu;
import java.util.Scanner;
import model.Admin;
import model.Company;
import model.Student;
import service.ApplicationService;
import service.DriveService;
import service.UserService;
import service.ReportService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // create service objects
        UserService userService = new UserService();
        DriveService driveService = new DriveService();
        ApplicationService applicationService = new ApplicationService();

        // menus
        AuthMenu authMenu = new AuthMenu(sc, userService);

        ReportService reportService =
        new ReportService(userService, driveService, applicationService);

        StudentMenu studentMenu =
        new StudentMenu(sc, userService, driveService, applicationService, reportService);

        AdminMenu adminMenu =
        new AdminMenu(sc, userService, driveService, applicationService, reportService);

        CompanyMenu companyMenu =
                new CompanyMenu(sc, driveService, applicationService, userService);

        int choice = 0;

        // main menu loop
        while (choice != 4) {
            System.out.println("\n======================================");
            System.out.println(" SMART CAMPUS PLACEMENT SYSTEM ");
            System.out.println("======================================");

            System.out.println("1. Student");
            System.out.println("2. Company");
            System.out.println("3. Admin");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }

            if (choice == 1) {
                studentSection(sc, authMenu, studentMenu);

            } else if (choice == 2) {
                companySection(sc, authMenu, companyMenu);

            } else if (choice == 3) {
                adminSection(authMenu, adminMenu);

            } else if (choice == 4) {
                System.out.println("Exiting...");

            } else {
                System.out.println("Invalid choice");
            }
        }

        sc.close();
    }

    // -------- STUDENT --------
    private static void studentSection(Scanner sc,
                                       AuthMenu authMenu,
                                       StudentMenu studentMenu) {

        System.out.println("\n--- STUDENT ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Back");
        System.out.print("Enter choice: ");

        int ch = 0;

        try {
            ch = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input");
            return;
        }

        if (ch == 1) {
            Student s = authMenu.registerStudent();
            if (s != null) {
                studentMenu.showMenu(s);
            }

        } else if (ch == 2) {
            Student s = authMenu.loginStudent();
            if (s != null) {
                studentMenu.showMenu(s);
            }
        }
    }

    // -------- COMPANY --------
    private static void companySection(Scanner sc,
                                       AuthMenu authMenu,
                                       CompanyMenu companyMenu) {

        System.out.println("\n--- COMPANY ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Back");
        System.out.print("Enter choice: ");

        int ch = 0;

        try {
            ch = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input");
            return;
        }

        if (ch == 1) {
            Company c = authMenu.registerCompany();
            if (c != null) {
                companyMenu.showMenu(c);
            }

        } else if (ch == 2) {
            Company c = authMenu.loginCompany();
            if (c != null) {
                companyMenu.showMenu(c);
            }
        }
    }

    // -------- ADMIN --------
    private static void adminSection(AuthMenu authMenu,
                                     AdminMenu adminMenu) {

        Admin a = authMenu.loginAdmin();

        if (a != null) {
            adminMenu.showMenu(a);
        }
    }
}