package cli;

import java.util.ArrayList;
import java.util.Scanner;
import model.Application;
import model.Drive;
import model.Student;
import service.ApplicationService;
import service.DriveService;
import service.ReportService;
import service.UserService;

public class StudentMenu {

    private Scanner scanner;
    private UserService userService;
    private DriveService driveService;
    private ApplicationService applicationService;
    private ReportService reportService;
    private Student loggedInStudent;

    public StudentMenu(Scanner sc, UserService us, DriveService ds,
                       ApplicationService as, ReportService rs) {

        scanner = sc;
        userService = us;
        driveService = ds;
        applicationService = as;
        reportService = rs;
    }

    // MAIN MENU
    public void showMenu(Student stu) {
        loggedInStudent = stu;
        int choice = 0;

        while (choice != 8) {

            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("Welcome, " + loggedInStudent.getName());

            System.out.println("1. View All Drives");
            System.out.println("2. View Eligible Drives");
            System.out.println("3. Apply for Drive");
            System.out.println("4. Track Applications");
            System.out.println("5. Skill Gap Analysis");
            System.out.println("6. Resume Score");
            System.out.println("7. Manage Skills");
            System.out.println("8. Logout");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }

            switch (choice) {
                case 1: viewAllDrives(); break;
                case 2: viewEligibleDrives(); break;
                case 3: applyForDrive(); break;
                case 4: trackApplications(); break;
                case 5: analyzeSkillGap(); break;
                case 6: viewResumeScore(); break;
                case 7: manageSkills(); break;
                case 8: System.out.println("Logging out..."); break;
                default: System.out.println("Invalid choice");
            }
        }
    }

    // 1. View all drives
    private void viewAllDrives() {

        System.out.println("\n--- ALL DRIVES ---");

        ArrayList<Drive> drives = driveService.getActiveDrives();

        if (drives.size() == 0) {
            System.out.println("No drives available");
            return;
        }

        for (int i = 0; i < drives.size(); i++) {
            System.out.println("\nDrive " + (i + 1));
            drives.get(i).printDetails();
        }

        System.out.println("Total: " + drives.size());
    }

    // 2. View eligible drives
    private void viewEligibleDrives() {

        System.out.println("\n--- ELIGIBLE DRIVES ---");

        ArrayList<Drive> drives = driveService.getEligibleDrives(loggedInStudent);

        if (drives.size() == 0) {
            System.out.println("No eligible drives");
            return;
        }

        for (int i = 0; i < drives.size(); i++) {
            System.out.println("\nDrive " + (i + 1));
            drives.get(i).printDetails();
        }

        System.out.println("Total: " + drives.size());
    }

    // 3. Apply for drive
    private void applyForDrive() {

        System.out.println("\n--- APPLY FOR DRIVE ---");

        ArrayList<Drive> drives = driveService.getEligibleDrives(loggedInStudent);

        if (drives.size() == 0) {
            System.out.println("No eligible drives");
            return;
        }

        for (int i = 0; i < drives.size(); i++) {
            System.out.println((i + 1) + ". "
                    + drives.get(i).getCompanyName()
                    + " | " + drives.get(i).getJobRole()
                    + " | ID: " + drives.get(i).getDriveId());
        }

        System.out.print("Enter Drive ID: ");
        String id = scanner.nextLine();

        try {
            Drive d = driveService.getDriveById(id);
            applicationService.applyForDrive(loggedInStudent, d);
            System.out.println("Applied successfully");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // 4. Track applications
    private void trackApplications() {

        System.out.println("\n--- MY APPLICATIONS ---");

        ArrayList<Application> apps =
                applicationService.getApplicationsByStudent(loggedInStudent.getUserId());

        if (apps.size() == 0) {
            System.out.println("No applications found");
            return;
        }

        for (int i = 0; i < apps.size(); i++) {
            apps.get(i).printDetails();
        }

        System.out.println("Total: " + apps.size());
    }

    // 5. Skill gap analysis
    private void analyzeSkillGap() {

        System.out.println("\n--- SKILL GAP ---");

        ArrayList<Drive> drives = driveService.getActiveDrives();

        for (int i = 0; i < drives.size(); i++) {
            System.out.println((i + 1) + ". "
                    + drives.get(i).getCompanyName()
                    + " | ID: " + drives.get(i).getDriveId());
        }

        System.out.print("Enter Drive ID: ");
        String id = scanner.nextLine();

        try {
            Drive d = driveService.getDriveById(id);

            ArrayList<String> missing =
                    driveService.analyzeSkillGap(loggedInStudent, d);

            System.out.println("Missing skills: " + missing);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // 6. Resume score
    private void viewResumeScore() {

        Student fresh = userService.getStudentById(loggedInStudent.getUserId());

        if (fresh != null) {
            loggedInStudent = fresh;
        }

        reportService.generateResumeReport(loggedInStudent);
    }

    // 7. Manage skills
    private void manageSkills() {

        int choice = 0;

        while (choice != 3) {

            System.out.println("\nYour Skills: " + loggedInStudent.getSkills());

            System.out.println("1. Add Skill");
            System.out.println("2. Remove Skill");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }

            if (choice == 1) {

                System.out.print("Enter skill: ");
                String s = scanner.nextLine();

                if (!s.isEmpty()) {
                    loggedInStudent.addSkill(s);
                    userService.updateStudent(loggedInStudent);
                    System.out.println("Skill added");
                }

            } else if (choice == 2) {

                System.out.print("Enter skill: ");
                String s = scanner.nextLine();

                ArrayList<String> list = loggedInStudent.getSkills();
                boolean removed = false;

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equalsIgnoreCase(s)) {
                        list.remove(i);
                        removed = true;
                        break;
                    }
                }

                if (removed) {
                    userService.updateStudent(loggedInStudent);
                    System.out.println("Skill removed");
                } else {
                    System.out.println("Skill not found");
                }
            }
        }
    }
}