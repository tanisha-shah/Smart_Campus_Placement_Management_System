package cli;

import model.Company;
import model.Drive;
import model.Application;
import model.Student;
import service.DriveService;
import service.ApplicationService;
import service.NotificationService;
import service.UserService;
import utils.UIHelper;
import exceptions.DriveNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

// CompanyMenu handles all company interactions via CLI
public class CompanyMenu {

    private Scanner scanner;
    private DriveService driveService;
    private ApplicationService applicationService;
    private NotificationService notificationService;
    private UserService userService;
    private Company loggedInCompany;

    public CompanyMenu(Scanner scanner, DriveService driveService,
                       ApplicationService applicationService,
                       NotificationService notificationService,
                       UserService userService) {
        this.scanner = scanner;
        this.driveService = driveService;
        this.applicationService = applicationService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    // Main company menu loop
    public void showMenu(Company company) {
        this.loggedInCompany = company;
        int choice = 0;

        while (choice != 5) {
            UIHelper.printBlankLine();
            UIHelper.printHeading("COMPANY MENU - Welcome, " + loggedInCompany.getCompanyName());
            UIHelper.printMenuItem(1, "Post a New Campus Drive");
            UIHelper.printMenuItem(2, "View My Posted Drives");
            UIHelper.printMenuItem(3, "View Applicants for a Drive");
            UIHelper.printMenuItem(4, "Shortlist / Reject Applicants");
            UIHelper.printMenuItem(5, "Logout");
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
                    postNewDrive();
                    break;
                case 2:
                    viewMyDrives();
                    break;
                case 3:
                    viewApplicants();
                    break;
                case 4:
                    shortlistOrRejectApplicants();
                    break;
                case 5:
                    UIHelper.printInfo("Logging out. Goodbye, " + loggedInCompany.getCompanyName() + "!");
                    break;
                default:
                    UIHelper.printError("Invalid choice. Enter a number between 1 and 5.");
            }
        }
    }

    // 1. Post a new campus drive
    private void postNewDrive() {
        UIHelper.printSubHeading("POST A NEW CAMPUS DRIVE");

        try {
            System.out.print("  Job Role (e.g. Software Engineer): ");
            String jobRole = scanner.nextLine().trim();
            if (jobRole.isEmpty()) {
                UIHelper.printError("Job role cannot be empty.");
                return;
            }

            System.out.print("  Job Type (Full-time / Internship / Contract): ");
            String jobType = scanner.nextLine().trim();
            if (jobType.isEmpty()) jobType = "Full-time";

            System.out.print("  CTC / Stipend (in LPA, e.g. 6.5): ");
            double ctc = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("  Minimum CGPA required (e.g. 7.0): ");
            double minCgpa = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("  Maximum Backlogs allowed (e.g. 0): ");
            int maxBacklogs = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("  Drive Date (e.g. 15-08-2025): ");
            String driveDate = scanner.nextLine().trim();
            if (driveDate.isEmpty()) driveDate = UIHelper.getTodayDate();

            System.out.print("  Location (e.g. Bangalore / Remote): ");
            String location = scanner.nextLine().trim();
            if (location.isEmpty()) location = "Not Specified";

            // Eligible branches
            System.out.print("  Eligible Branches (comma-separated, or ALL): ");
            String branchInput = scanner.nextLine().trim();

            // Required skills
            System.out.print("  Required Skills (comma-separated, or NONE): ");
            String skillInput = scanner.nextLine().trim();

            // Create the drive
            String driveId = UIHelper.generateId("DRV");
            Drive drive = new Drive(driveId, loggedInCompany.getUserId(),
                    loggedInCompany.getCompanyName(), jobRole, ctc, minCgpa,
                    maxBacklogs, driveDate, location, jobType);

            // Add branches
            if (!branchInput.equalsIgnoreCase("ALL") && !branchInput.isEmpty()) {
                String[] branches = branchInput.split(",");
                for (int i = 0; i < branches.length; i++) {
                    drive.addEligibleBranch(branches[i].trim());
                }
            }

            // Add skills
            if (!skillInput.equalsIgnoreCase("NONE") && !skillInput.isEmpty()) {
                String[] skills = skillInput.split(",");
                for (int i = 0; i < skills.length; i++) {
                    drive.addRequiredSkill(skills[i].trim());
                }
            }

            driveService.postDrive(drive);

            // Send notification to all eligible students
            ArrayList<Student> allStudents = userService.getAllStudents();
            ArrayList<String> eligibleStudentIds = new ArrayList<String>();
            for (int i = 0; i < allStudents.size(); i++) {
                ArrayList<Drive> eligible = driveService.getEligibleDrives(allStudents.get(i));
                for (int j = 0; j < eligible.size(); j++) {
                    if (eligible.get(j).getDriveId().equals(driveId)) {
                        eligibleStudentIds.add(allStudents.get(i).getUserId());
                        break;
                    }
                }
            }

            if (!eligibleStudentIds.isEmpty()) {
                notificationService.sendNotificationToAllStudents(
                        eligibleStudentIds,
                        "New drive: " + loggedInCompany.getCompanyName()
                                + " is hiring for " + jobRole + " (" + jobType + ") - " + ctc + " LPA",
                        "NEW_DRIVE"
                );
                UIHelper.printInfo("Notification sent to " + eligibleStudentIds.size()
                        + " eligible student(s).");
            }

        } catch (NumberFormatException e) {
            UIHelper.printError("Invalid number entered. Please try again.");
        }
    }

    // 2. View drives posted by this company
    private void viewMyDrives() {
        UIHelper.printSubHeading("MY POSTED DRIVES");
        ArrayList<Drive> myDrives = driveService.getDrivesByCompany(loggedInCompany.getUserId());

        if (myDrives.isEmpty()) {
            UIHelper.printInfo("You have not posted any drives yet.");
            return;
        }

        for (int i = 0; i < myDrives.size(); i++) {
            System.out.println("  [Drive " + (i + 1) + "]");
            myDrives.get(i).printDetails();

            // Show applicant count for each drive
            int appCount = applicationService.getApplicationsByDrive(
                    myDrives.get(i).getDriveId()).size();
            System.out.println("  Applicants   : " + appCount);
            UIHelper.printLine();
        }
        System.out.println("  Total drives posted: " + myDrives.size());
    }

    // 3. View applicants for a specific drive
    private void viewApplicants() {
        UIHelper.printSubHeading("VIEW APPLICANTS");

        ArrayList<Drive> myDrives = driveService.getDrivesByCompany(loggedInCompany.getUserId());
        if (myDrives.isEmpty()) {
            UIHelper.printInfo("You have not posted any drives.");
            return;
        }

        // List drives to choose from
        System.out.println("  Select a drive:");
        for (int i = 0; i < myDrives.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + myDrives.get(i).getJobRole()
                    + " | ID: " + myDrives.get(i).getDriveId());
        }
        UIHelper.printLine();
        System.out.print("  Enter Drive ID (or 0 to go back): ");
        String driveId = scanner.nextLine().trim();

        if (driveId.equals("0")) return;

        ArrayList<Application> apps = applicationService.getApplicationsByDrive(driveId);
        if (apps.isEmpty()) {
            UIHelper.printInfo("No applicants for this drive yet.");
            return;
        }

        UIHelper.printLine();
        System.out.println("  APPLICANTS LIST:");
        UIHelper.printLine();
        for (int i = 0; i < apps.size(); i++) {
            System.out.println("  [" + (i + 1) + "]");
            apps.get(i).printDetails();

            // Show student profile details
            Student student = userService.getStudentById(apps.get(i).getStudentId());
            if (student != null) {
                System.out.println("  Branch   : " + student.getBranch());
                System.out.println("  CGPA     : " + student.getCgpa());
                System.out.println("  Skills   : " + student.getSkills().toString());
                System.out.println("  Backlogs : " + student.getBacklogs());
            }
            UIHelper.printLine();
        }
        System.out.println("  Total applicants: " + apps.size());
    }

    // 4. Shortlist or Reject applicants
    private void shortlistOrRejectApplicants() {
        UIHelper.printSubHeading("SHORTLIST / REJECT APPLICANTS");

        ArrayList<Drive> myDrives = driveService.getDrivesByCompany(loggedInCompany.getUserId());
        if (myDrives.isEmpty()) {
            UIHelper.printInfo("You have not posted any drives.");
            return;
        }

        // List drives
        System.out.println("  Select a drive:");
        for (int i = 0; i < myDrives.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + myDrives.get(i).getJobRole()
                    + " | ID: " + myDrives.get(i).getDriveId());
        }
        UIHelper.printLine();
        System.out.print("  Enter Drive ID (or 0 to go back): ");
        String driveId = scanner.nextLine().trim();

        if (driveId.equals("0")) return;

        ArrayList<Application> apps = applicationService.getApplicationsByDrive(driveId);
        if (apps.isEmpty()) {
            UIHelper.printInfo("No applicants for this drive.");
            return;
        }

        // Show applicants with APPLIED status
        System.out.println("  Applicants with APPLIED status:");
        UIHelper.printLine();
        boolean anyPending = false;
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getStatus().equals("APPLIED")) {
                System.out.println("  App ID: " + apps.get(i).getApplicationId()
                        + " | Student: " + apps.get(i).getStudentName()
                        + " | Status: " + apps.get(i).getStatus());
                anyPending = true;
            }
        }

        if (!anyPending) {
            UIHelper.printInfo("All applicants have already been processed.");
            return;
        }

        UIHelper.printLine();
        System.out.print("  Enter Application ID to update (or 0 to go back): ");
        String appId = scanner.nextLine().trim();

        if (appId.equals("0")) return;

        System.out.println("  Update status to:");
        UIHelper.printMenuItem(1, "SHORTLISTED");
        UIHelper.printMenuItem(2, "REJECTED");
        UIHelper.printChoicePrompt();

        int statusChoice = 0;
        try {
            statusChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            UIHelper.printError("Invalid choice.");
            return;
        }

        String newStatus = "";
        String notifType = "";
        String notifMessage = "";

        if (statusChoice == 1) {
            newStatus = "SHORTLISTED";
            notifType = "SHORTLISTED";
        } else if (statusChoice == 2) {
            newStatus = "REJECTED";
            notifType = "REJECTED";
        } else {
            UIHelper.printError("Invalid choice. Enter 1 or 2.");
            return;
        }

        boolean updated = applicationService.updateApplicationStatus(appId, newStatus);
        if (updated) {
            UIHelper.printSuccess("Application status updated to: " + newStatus);

            // Find student ID for this application and send notification
            for (int i = 0; i < apps.size(); i++) {
                if (apps.get(i).getApplicationId().equals(appId)) {
                    String studentId = apps.get(i).getStudentId();
                    notifMessage = "Your application to " + loggedInCompany.getCompanyName()
                            + " has been updated to: " + newStatus;
                    notificationService.sendNotification(studentId, notifMessage, notifType);
                    UIHelper.printInfo("Student has been notified.");
                    break;
                }
            }
        } else {
            UIHelper.printError("Application ID not found: " + appId);
        }
    }
}
