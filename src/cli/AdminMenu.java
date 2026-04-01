package cli;

import model.Admin;
import model.Student;
import model.Company;
import model.Drive;
import model.Application;
import service.UserService;
import service.DriveService;
import service.ApplicationService;
import service.ReportService;
import utils.UIHelper;

import java.util.ArrayList;
import java.util.Scanner;

// AdminMenu handles all admin interactions via CLI
public class AdminMenu {

    private Scanner scanner;
    private UserService userService;
    private DriveService driveService;
    private ApplicationService applicationService;
    private ReportService reportService;
    private Admin loggedInAdmin;

    public AdminMenu(Scanner scanner, UserService userService, DriveService driveService,
                     ApplicationService applicationService, ReportService reportService) {
        this.scanner = scanner;
        this.userService = userService;
        this.driveService = driveService;
        this.applicationService = applicationService;
        this.reportService = reportService;
    }

    // Main admin menu loop
    public void showMenu(Admin admin) {
        this.loggedInAdmin = admin;
        int choice = 0;

        while (choice != 7) {
            UIHelper.printBlankLine();
            UIHelper.printHeading("ADMIN PANEL - Welcome, " + loggedInAdmin.getName());
            UIHelper.printMenuItem(1, "View All Registered Students");
            UIHelper.printMenuItem(2, "View All Registered Companies");
            UIHelper.printMenuItem(3, "View All Campus Drives");
            UIHelper.printMenuItem(4, "View All Applications");
            UIHelper.printMenuItem(5, "View Student Profile Details");
            UIHelper.printMenuItem(6, "Generate Summary Report");
            UIHelper.printMenuItem(7, "Logout");
            UIHelper.printLine();
            UIHelper.printChoicePrompt();

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                UIHelper.printError("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    viewAllStudents();
                    break;
                case 2:
                    viewAllCompanies();
                    break;
                case 3:
                    viewAllDrives();
                    break;
                case 4:
                    viewAllApplications();
                    break;
                case 5:
                    viewStudentProfile();
                    break;
                case 6:
                    generateReport();
                    break;
                case 7:
                    UIHelper.printInfo("Logging out of admin panel. Goodbye!");
                    break;
                default:
                    UIHelper.printError("Invalid choice. Enter a number between 1 and 7.");
            }
        }
    }

    // 1. View all registered students
    private void viewAllStudents() {
        UIHelper.printSubHeading("ALL REGISTERED STUDENTS");
        ArrayList<Student> students = userService.getAllStudents();

        if (students.isEmpty()) {
            UIHelper.printInfo("No students registered yet.");
            return;
        }

        for (int i = 0; i < students.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + students.get(i).getDisplayInfo());
        }
        UIHelper.printLine();
        System.out.println("  Total students: " + students.size());
    }

    // 2. View all registered companies
    private void viewAllCompanies() {
        UIHelper.printSubHeading("ALL REGISTERED COMPANIES");
        ArrayList<Company> companies = userService.getAllCompanies();

        if (companies.isEmpty()) {
            UIHelper.printInfo("No companies registered yet.");
            return;
        }

        for (int i = 0; i < companies.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + companies.get(i).getDisplayInfo());
        }
        UIHelper.printLine();
        System.out.println("  Total companies: " + companies.size());
    }

    // 3. View all campus drives
    private void viewAllDrives() {
        UIHelper.printSubHeading("ALL CAMPUS DRIVES");
        ArrayList<Drive> drives = driveService.getAllDrives();

        if (drives.isEmpty()) {
            UIHelper.printInfo("No drives posted yet.");
            return;
        }

        for (int i = 0; i < drives.size(); i++) {
            System.out.println("  [Drive " + (i + 1) + "]");
            drives.get(i).printDetails();

            int appCount = applicationService.getApplicationsByDrive(
                    drives.get(i).getDriveId()).size();
            System.out.println("  Total Applicants: " + appCount);
            UIHelper.printLine();
        }
        System.out.println("  Total drives: " + drives.size());
    }

    // 4. View all applications across the system
    private void viewAllApplications() {
        UIHelper.printSubHeading("ALL APPLICATIONS");
        ArrayList<Application> apps = applicationService.getAllApplications();

        if (apps.isEmpty()) {
            UIHelper.printInfo("No applications submitted yet.");
            return;
        }

        int applied = 0, shortlisted = 0, rejected = 0;

        for (int i = 0; i < apps.size(); i++) {
            System.out.println("  [" + (i + 1) + "] AppID: " + apps.get(i).getApplicationId()
                    + " | Student: " + apps.get(i).getStudentName()
                    + " | Company: " + apps.get(i).getCompanyName()
                    + " | Role: " + apps.get(i).getJobRole()
                    + " | Status: " + apps.get(i).getStatus());

            String status = apps.get(i).getStatus();
            if (status.equals("APPLIED")) applied++;
            else if (status.equals("SHORTLISTED")) shortlisted++;
            else if (status.equals("REJECTED")) rejected++;
        }

        UIHelper.printLine();
        System.out.println("  Total: " + apps.size()
                + " | Applied: " + applied
                + " | Shortlisted: " + shortlisted
                + " | Rejected: " + rejected);
    }

    // 5. View a specific student's full profile
    private void viewStudentProfile() {
        UIHelper.printSubHeading("VIEW STUDENT PROFILE");

        System.out.print("  Enter Student ID: ");
        String studentId = scanner.nextLine().trim();

        Student student = userService.getStudentById(studentId);
        if (student == null) {
            UIHelper.printError("Student not found with ID: " + studentId);
            return;
        }

        UIHelper.printLine();
        System.out.println("  STUDENT PROFILE:");
        UIHelper.printLine();
        System.out.println("  Student ID : " + student.getUserId());
        System.out.println("  Name       : " + student.getName());
        System.out.println("  Email      : " + student.getEmail());
        System.out.println("  Branch     : " + student.getBranch());
        System.out.println("  CGPA       : " + student.getCgpa());
        System.out.println("  Backlogs   : " + student.getBacklogs());
        System.out.println("  Skills     : " + student.getSkills().toString());
        System.out.println("  Resume Score: " + student.getResumeScore() + " / 100");
        UIHelper.printLine();

        // Show their applications
        ArrayList<Application> apps = applicationService.getApplicationsByStudent(studentId);
        System.out.println("  APPLICATIONS (" + apps.size() + " total):");
        if (apps.isEmpty()) {
            System.out.println("  No applications submitted.");
        } else {
            for (int i = 0; i < apps.size(); i++) {
                System.out.println("  -> " + apps.get(i).getCompanyName()
                        + " | " + apps.get(i).getJobRole()
                        + " | Status: " + apps.get(i).getStatus());
            }
        }
        UIHelper.printLine();
    }

    // 6. Generate system-wide summary report
    private void generateReport() {
        UIHelper.printSubHeading("GENERATING SUMMARY REPORT");
        reportService.generateSummaryReport();
    }
}
