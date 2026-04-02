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

            System.out.println();
            System.out.println("========== ADMIN PANEL ==========");
            System.out.println("Welcome, " + loggedInAdmin.getName());
            System.out.println("1. View All Registered Students");
            System.out.println("2. View All Registered Companies");
            System.out.println("3. View All Campus Drives");
            System.out.println("4. View All Applications");
            System.out.println("5. View Student Profile Details");
            System.out.println("6. Generate Summary Report");
            System.out.println("7. Logout");
            System.out.println("---------------------------------");
            System.out.print("Enter your choice: ");

            try {

                choice = Integer.parseInt(scanner.nextLine().trim());

            } catch (Exception e) {
                System.out.println("Invalid input. Enter number only.");
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
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }


    private void viewAllStudents() {
        System.out.println("\n--- ALL REGISTERED STUDENTS ---");

        ArrayList<Student> students = userService.getAllStudents();

        if (students.size() == 0) {
            System.out.println("No students found.");
            return;
        }

        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getDisplayInfo());
        }

        System.out.println("Total students: " + students.size());
    }



    private void viewAllCompanies() {
        System.out.println("\n--- ALL REGISTERED COMPANIES ---");

        ArrayList<Company> companies = userService.getAllCompanies();

        if (companies.size() == 0) {
            System.out.println("No companies found.");
            return;
        }

        for (int i = 0; i < companies.size(); i++) {
            System.out.println((i + 1) + ". " + companies.get(i).getDisplayInfo());
        }

        System.out.println("Total companies: " + companies.size());
    }


    private void viewAllDrives() {
        System.out.println("\n--- ALL DRIVES ---");

        ArrayList<Drive> drives = driveService.getAllDrives();

        if (drives.size() == 0) {
            System.out.println("No drives available.");
            return;
        }

        for (int i = 0; i < drives.size(); i++) {
            System.out.println("\nDrive " + (i + 1));
            drives.get(i).printDetails();

            int count = applicationService.getApplicationsByDrive(
                    drives.get(i).getDriveId()).size();

            System.out.println("Applicants: " + count);
        }

        System.out.println("Total drives: " + drives.size());
    }



   private void viewAllApplications() {
        System.out.println("\n--- ALL APPLICATIONS ---");

        ArrayList<Application> apps = applicationService.getAllApplications();

        if (apps.size() == 0) {
            System.out.println("No applications found.");
            return;
        }

        int applied = 0;
        int shortlisted = 0;
        int rejected = 0;

        for (int i = 0; i < apps.size(); i++) {
            System.out.println((i + 1) + ". "
                    + apps.get(i).getStudentName()
                    + " | " + apps.get(i).getCompanyName()
                    + " | " + apps.get(i).getJobRole()
                    + " | " + apps.get(i).getStatus());

            String status = apps.get(i).getStatus();

            if (status.equals("APPLIED")) {
                applied++;
            } else if (status.equals("SHORTLISTED")) {
                shortlisted++;
            } else if (status.equals("REJECTED")) {
                rejected++;
            }
        }

        System.out.println("Total: " + apps.size());
        System.out.println("Applied: " + applied);
        System.out.println("Shortlisted: " + shortlisted);
        System.out.println("Rejected: " + rejected);
    }


    private void viewStudentProfile() {

        System.out.println("\n--- VIEW STUDENT PROFILE ---");

        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();

        Student student = userService.getStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found with ID: " + studentId);
            return;
        }

        System.out.println("--------------------------------");
        System.out.println("STUDENT PROFILE");
        System.out.println("--------------------------------");

        System.out.println("Student ID  : " + student.getUserId());
        System.out.println("Name        : " + student.getName());
        System.out.println("Email       : " + student.getEmail());
        System.out.println("Branch      : " + student.getBranch());
        System.out.println("CGPA        : " + student.getCgpa());
        System.out.println("Backlogs    : " + student.getBacklogs());
        System.out.println("Skills      : " + student.getSkills());
        System.out.println("Resume Score: " + student.getResumeScore() + " / 100");

        System.out.println("--------------------------------");


        ArrayList<Application> apps = applicationService.getApplicationsByStudent(studentId);

        System.out.println("APPLICATIONS (" + apps.size() + "):");

        if (apps.size() == 0) {
            System.out.println("No applications found.");
        } else {
            for (int i = 0; i < apps.size(); i++) {
                System.out.println("- " 
                        + apps.get(i).getCompanyName()
                        + " | " + apps.get(i).getJobRole()
                        + " | Status: " + apps.get(i).getStatus());
            }
        }

        System.out.println("--------------------------------");
    }



    private void generateReport() {

        System.out.println("\n--- GENERATING SUMMARY REPORT ---");

        reportService.generateSummaryReport();
    }
}
