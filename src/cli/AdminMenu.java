package cli;

import java.util.ArrayList;
import java.util.Scanner;
import model.Admin;
import model.Application;
import model.Company;
import model.Drive;
import model.Student;
import service.ApplicationService;
import service.DriveService;
import service.ReportService;
import service.UserService;

public class AdminMenu {

    private Scanner scanner;
    private UserService userService;
    private DriveService driveService;
    private ApplicationService applicationService;
    private ReportService reportService;
    private Admin loggedInAdmin;

    public AdminMenu(Scanner sc, UserService us, DriveService ds,
                     ApplicationService as, ReportService rs) {

        scanner = sc;
        userService = us;
        driveService = ds;
        applicationService = as;
        reportService = rs;
    }

    public void showMenu(Admin adminObj) {

        loggedInAdmin = adminObj;
        int choice = 0;

        while (choice != 7) {

            System.out.println("\n========== ADMIN PANEL ==========");
            System.out.println("Welcome, " + loggedInAdmin.getName());

            System.out.println("1. View Students");
            System.out.println("2. View Companies");
            System.out.println("3. View Drives");
            System.out.println("4. View Applications");
            System.out.println("5. View Student Profile");
            System.out.println("6. Generate Report");
            System.out.println("7. Logout");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }

            if (choice == 1) viewAllStudents();
            else if (choice == 2) viewAllCompanies();
            else if (choice == 3) viewAllDrives();
            else if (choice == 4) viewAllApplications();
            else if (choice == 5) viewStudentProfile();
            else if (choice == 6) generateReport();
            else if (choice == 7) System.out.println("Logging out...");
            else System.out.println("Invalid choice");
        }
    }

    // 1. Students
    private void viewAllStudents() {

        System.out.println("\n--- STUDENTS ---");

        ArrayList<Student> list = userService.getAllStudents();

        if (list.size() == 0) {
            System.out.println("No students found");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getDisplayInfo());
        }
    }

    // 2. Companies
    private void viewAllCompanies() {

        System.out.println("\n--- COMPANIES ---");

        ArrayList<Company> list = userService.getAllCompanies();

        if (list.size() == 0) {
            System.out.println("No companies found");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getDisplayInfo());
        }
    }

    // 3. Drives
    private void viewAllDrives() {

        System.out.println("\n--- DRIVES ---");

        ArrayList<Drive> list = driveService.getAllDrives();

        if (list.size() == 0) {
            System.out.println("No drives found");
            return;
        }

        for (int i = 0; i < list.size(); i++) {

            System.out.println("\nDrive " + (i + 1));
            list.get(i).printDetails();

            int count = applicationService
                    .getApplicationsByDrive(list.get(i).getDriveId()).size();

            System.out.println("Applicants: " + count);
        }
    }

    // 4. Applications
    private void viewAllApplications() {

        System.out.println("\n--- APPLICATIONS ---");

        ArrayList<Application> list = applicationService.getAllApplications();

        if (list.size() == 0) {
            System.out.println("No applications found");
            return;
        }

        int applied = 0;
        int shortlisted = 0;
        int rejected = 0;

        for (int i = 0; i < list.size(); i++) {

            System.out.println((i + 1) + ". "
                    + list.get(i).getStudentName()
                    + " | " + list.get(i).getCompanyName()
                    + " | " + list.get(i).getJobRole()
                    + " | " + list.get(i).getStatus());

            String status = list.get(i).getStatus();

            if (status.equals("APPLIED")) applied++;
            else if (status.equals("SHORTLISTED")) shortlisted++;
            else if (status.equals("REJECTED")) rejected++;
        }

        System.out.println("Total: " + list.size());
        System.out.println("Applied: " + applied);
        System.out.println("Shortlisted: " + shortlisted);
        System.out.println("Rejected: " + rejected);
    }

    // 5. Student profile
    private void viewStudentProfile() {

        System.out.println("\n--- STUDENT PROFILE ---");

        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();

        Student s = userService.getStudentById(id);

        if (s == null) {
            System.out.println("Student not found");
            return;
        }

        System.out.println("Name: " + s.getName());
        System.out.println("Email: " + s.getEmail());
        System.out.println("Branch: " + s.getBranch());
        System.out.println("CGPA: " + s.getCgpa());
        System.out.println("Backlogs: " + s.getBacklogs());
        System.out.println("Skills: " + s.getSkills());
        System.out.println("Resume Score: " + s.getResumeScore());

        ArrayList<Application> apps =
                applicationService.getApplicationsByStudent(id);

        System.out.println("Applications: " + apps.size());

        for (int i = 0; i < apps.size(); i++) {
            System.out.println("- "
                    + apps.get(i).getCompanyName()
                    + " | " + apps.get(i).getJobRole()
                    + " | " + apps.get(i).getStatus());
        }
    }

    // 6. Report
    private void generateReport() {

        System.out.println("\n--- REPORT ---");

        reportService.generateSummaryReport();
    }
}