package cli;

import java.util.ArrayList;
import java.util.Scanner;
import model.Application;
import model.Company;
import model.Drive;
import model.Student;
import service.ApplicationService;
import service.DriveService;
import service.UserService;

public class CompanyMenu {

    private Scanner scanner;
    private DriveService driveService;
    private ApplicationService applicationService;
    private UserService userService;
    private Company loggedInCompany;

    public CompanyMenu(Scanner sc, DriveService ds,
                       ApplicationService as, UserService us) {

        scanner = sc;
        driveService = ds;
        applicationService = as;
        userService = us;
    }


    public void showMenu(Company company) {

        loggedInCompany = company;
        int choice = 0;

        while (choice != 5) {

            System.out.println("\n===== COMPANY MENU =====");
            System.out.println("Welcome, " + loggedInCompany.getCompanyName());

            System.out.println("1. Post Drive");
            System.out.println("2. View My Drives");
            System.out.println("3. View Applicants");
            System.out.println("4. Shortlist / Reject");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }

            switch (choice) {
                case 1: postNewDrive(); break;
                case 2: viewMyDrives(); break;
                case 3: viewApplicants(); break;
                case 4: shortlistOrReject(); break;
                case 5: System.out.println("Logging out..."); break;
                default: System.out.println("Invalid choice");
            }
        }
    }


    private void postNewDrive() {

        System.out.println("\n--- POST DRIVE ---");

        try {
            System.out.print("Job Role: ");
            String jobRole = scanner.nextLine();

            System.out.print("Job Type: ");
            String jobType = scanner.nextLine();

            System.out.print("CTC: ");
            double ctc = Double.parseDouble(scanner.nextLine());

            System.out.print("Min CGPA: ");
            double minCgpa = Double.parseDouble(scanner.nextLine());

            System.out.print("Max Backlogs: ");
            int maxBacklogs = Integer.parseInt(scanner.nextLine());

            System.out.print("Date: ");
            String date = scanner.nextLine();

            System.out.print("Location: ");
            String location = scanner.nextLine();

            System.out.print("Branches (comma or ALL): ");
            String branchInput = scanner.nextLine();

            System.out.print("Skills (comma or NONE): ");
            String skillInput = scanner.nextLine();

            String driveId = "DRV" + System.currentTimeMillis();

            Drive d = new Drive(
                    driveId,
                    loggedInCompany.getUserId(),
                    loggedInCompany.getCompanyName(),
                    jobRole,
                    ctc,
                    minCgpa,
                    maxBacklogs,
                    date,
                    location,
                    jobType
            );

            if (!branchInput.equalsIgnoreCase("ALL") && !branchInput.isEmpty()) {
                String[] arr = branchInput.split(",");
                for (int i = 0; i < arr.length; i++) {
                    d.addEligibleBranch(arr[i]);
                }
            }

            if (!skillInput.equalsIgnoreCase("NONE") && !skillInput.isEmpty()) {
                String[] arr = skillInput.split(",");
                for (int i = 0; i < arr.length; i++) {
                    d.addRequiredSkill(arr[i]);
                }
            }

            driveService.postDrive(d);
            System.out.println("Drive posted successfully");

        } catch (Exception e) {
            System.out.println("Error posting drive");
        }
    }

    // 2. VIEW MY DRIVES
    private void viewMyDrives() {

        System.out.println("\n--- MY DRIVES ---");

        ArrayList<Drive> list =
                driveService.getDrivesByCompany(loggedInCompany.getUserId());

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

        System.out.println("Total: " + list.size());
    }

    // 3. VIEW APPLICANTS
    private void viewApplicants() {

        System.out.println("\n--- VIEW APPLICANTS ---");

        System.out.print("Enter Drive ID: ");
        String id = scanner.nextLine();

        ArrayList<Application> apps =
                applicationService.getApplicationsByDrive(id);

        if (apps.size() == 0) {
            System.out.println("No applicants");
            return;
        }

        for (int i = 0; i < apps.size(); i++) {

            System.out.println("\nApplication " + (i + 1));
            apps.get(i).printDetails();

            Student s = userService.getStudentById(apps.get(i).getStudentId());

            if (s != null) {
                System.out.println("Branch: " + s.getBranch());
                System.out.println("CGPA: " + s.getCgpa());
                System.out.println("Skills: " + s.getSkills());
                System.out.println("Backlogs: " + s.getBacklogs());
            }
        }

        System.out.println("Total: " + apps.size());
    }

    // 4. SHORTLIST / REJECT
    private void shortlistOrReject() {

        System.out.println("\n--- UPDATE APPLICATION ---");

        System.out.print("Enter Drive ID: ");
        String driveId = scanner.nextLine();

        ArrayList<Application> apps =
                applicationService.getApplicationsByDrive(driveId);

        if (apps.size() == 0) {
            System.out.println("No applications");
            return;
        }

        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getStatus().equals("APPLIED")) {
                System.out.println(
                        apps.get(i).getApplicationId() + " | "
                        + apps.get(i).getStudentName()
                );
            }
        }

        System.out.print("Enter Application ID: ");
        String appId = scanner.nextLine();

        System.out.println("1. SHORTLIST");
        System.out.println("2. REJECT");
        System.out.print("Enter choice: ");

        int ch = Integer.parseInt(scanner.nextLine());

        String status = "";

        if (ch == 1) status = "SHORTLISTED";
        else if (ch == 2) status = "REJECTED";
        else {
            System.out.println("Invalid choice");
            return;
        }

        boolean ok = applicationService.updateApplicationStatus(appId, status);

        if (ok) {
            System.out.println("Updated to " + status);
        } else {
            System.out.println("Application not found");
        }
    }
}