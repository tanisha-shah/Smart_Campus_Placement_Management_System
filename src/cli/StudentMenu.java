package cli;

import model.Student;
import model.Drive;
import model.Application;
import model.Notification;
import service.UserService;
import service.DriveService;
import service.ApplicationService;
import service.NotificationService;
import service.ReportService;
import utils.UIHelper;
import exceptions.AlreadyAppliedException;
import exceptions.DriveNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

// StudentMenu handles all student interactions via CLI
public class StudentMenu {

    private Scanner scanner;
    private UserService userService;
    private DriveService driveService;
    private ApplicationService applicationService;
    private NotificationService notificationService;
    private ReportService reportService;
    private Student loggedInStudent;

    public StudentMenu(Scanner scanner, UserService userService, DriveService driveService,
                       ApplicationService applicationService,
                       NotificationService notificationService,
                       ReportService reportService) {
        this.scanner = scanner;
        this.userService = userService;
        this.driveService = driveService;
        this.applicationService = applicationService;
        this.notificationService = notificationService;
        this.reportService = reportService;
    }

    // Main student menu loop
    public void showMenu(Student student) {
        this.loggedInStudent = student;
        int choice = 0;

        while (choice != 9) {
            UIHelper.printBlankLine();
            UIHelper.printHeading("STUDENT MENU - Welcome, " + loggedInStudent.getName());

            // Show unread notification count
            int unread = notificationService.getUnreadCount(loggedInStudent.getUserId());
            if (unread > 0) {
                System.out.println("  ** You have " + unread + " unread notification(s)! **");
                UIHelper.printLine();
            }

            UIHelper.printMenuItem(1, "View All Campus Drives");
            UIHelper.printMenuItem(2, "View Eligible Drives");
            UIHelper.printMenuItem(3, "Apply for a Drive");
            UIHelper.printMenuItem(4, "Track My Applications");
            UIHelper.printMenuItem(5, "Analyze Skill Gap");
            UIHelper.printMenuItem(6, "View Resume Score");
            UIHelper.printMenuItem(7, "View Notifications");
            UIHelper.printMenuItem(8, "Manage My Skills");
            UIHelper.printMenuItem(9, "Logout");
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
                    viewAllDrives();
                    break;
                case 2:
                    viewEligibleDrives();
                    break;
                case 3:
                    applyForDrive();
                    break;
                case 4:
                    trackApplications();
                    break;
                case 5:
                    analyzeSkillGap();
                    break;
                case 6:
                    viewResumeScore();
                    break;
                case 7:
                    viewNotifications();
                    break;
                case 8:
                    manageSkills();
                    break;
                case 9:
                    UIHelper.printInfo("Logging out. Goodbye, " + loggedInStudent.getName() + "!");
                    break;
                default:
                    UIHelper.printError("Invalid choice. Please enter a number between 1 and 9.");
            }
        }
    }

    // 1. View ALL active drives
    private void viewAllDrives() {
        UIHelper.printSubHeading("ALL ACTIVE CAMPUS DRIVES");
        ArrayList<Drive> drives = driveService.getActiveDrives();
        if (drives.isEmpty()) {
            UIHelper.printInfo("No active drives available at the moment.");
            return;
        }
        for (int i = 0; i < drives.size(); i++) {
            System.out.println("  [Drive " + (i + 1) + "]");
            drives.get(i).printDetails();
            UIHelper.printLine();
        }
        System.out.println("  Total drives found: " + drives.size());
    }

    // 2. View ELIGIBLE drives only
    private void viewEligibleDrives() {
        UIHelper.printSubHeading("DRIVES YOU ARE ELIGIBLE FOR");
        ArrayList<Drive> eligibleDrives = driveService.getEligibleDrives(loggedInStudent);
        if (eligibleDrives.isEmpty()) {
            UIHelper.printInfo("No eligible drives found based on your profile.");
            UIHelper.printInfo("Your Profile - CGPA: " + loggedInStudent.getCgpa()
                    + " | Branch: " + loggedInStudent.getBranch()
                    + " | Backlogs: " + loggedInStudent.getBacklogs());
            return;
        }
        for (int i = 0; i < eligibleDrives.size(); i++) {
            System.out.println("  [Drive " + (i + 1) + "]");
            eligibleDrives.get(i).printDetails();
            UIHelper.printLine();
        }
        System.out.println("  Total eligible drives: " + eligibleDrives.size());
    }

    // 3. Apply for a drive
    private void applyForDrive() {
        UIHelper.printSubHeading("APPLY FOR A DRIVE");

        ArrayList<Drive> eligibleDrives = driveService.getEligibleDrives(loggedInStudent);
        if (eligibleDrives.isEmpty()) {
            UIHelper.printInfo("No eligible drives to apply for.");
            return;
        }

        // Show eligible drives with numbers
        System.out.println("  Your eligible drives:");
        UIHelper.printLine();
        for (int i = 0; i < eligibleDrives.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + eligibleDrives.get(i).getCompanyName()
                    + " | " + eligibleDrives.get(i).getJobRole()
                    + " | " + eligibleDrives.get(i).getCtc() + " LPA"
                    + " | ID: " + eligibleDrives.get(i).getDriveId());
        }
        UIHelper.printLine();
        System.out.print("  Enter Drive ID to apply (or 0 to go back): ");
        String driveId = scanner.nextLine().trim();

        if (driveId.equals("0")) return;

        try {
            Drive selectedDrive = driveService.getDriveById(driveId);
            applicationService.applyForDrive(loggedInStudent, selectedDrive);

            // Send confirmation notification to student
            notificationService.sendNotification(
                    loggedInStudent.getUserId(),
                    "You have successfully applied to " + selectedDrive.getCompanyName()
                            + " for the role of " + selectedDrive.getJobRole(),
                    "APPLICATION"
            );

        } catch (DriveNotFoundException e) {
            UIHelper.printError(e.getMessage());
        } catch (AlreadyAppliedException e) {
            UIHelper.printError(e.getMessage());
        }
    }

    // 4. Track application status
    private void trackApplications() {
        UIHelper.printSubHeading("MY APPLICATION STATUS");
        ArrayList<Application> myApps = applicationService.getApplicationsByStudent(
                loggedInStudent.getUserId());

        if (myApps.isEmpty()) {
            UIHelper.printInfo("You haven't applied to any drives yet.");
            return;
        }

        for (int i = 0; i < myApps.size(); i++) {
            System.out.println("  [Application " + (i + 1) + "]");
            myApps.get(i).printDetails();
            UIHelper.printLine();
        }
        System.out.println("  Total applications: " + myApps.size());
    }

    // 5. Analyze skill gap for a specific drive
    private void analyzeSkillGap() {
        UIHelper.printSubHeading("SKILL GAP ANALYSIS");

        ArrayList<Drive> activeDrives = driveService.getActiveDrives();
        if (activeDrives.isEmpty()) {
            UIHelper.printInfo("No active drives to compare against.");
            return;
        }

        System.out.println("  Your current skills: " + loggedInStudent.getSkills().toString());
        UIHelper.printLine();
        System.out.println("  Active Drives:");
        for (int i = 0; i < activeDrives.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + activeDrives.get(i).getCompanyName()
                    + " - " + activeDrives.get(i).getJobRole()
                    + " | ID: " + activeDrives.get(i).getDriveId());
        }
        UIHelper.printLine();
        System.out.print("  Enter Drive ID for skill gap analysis (or 0 to go back): ");
        String driveId = scanner.nextLine().trim();

        if (driveId.equals("0")) return;

        try {
            Drive drive = driveService.getDriveById(driveId);
            ArrayList<String> missingSkills = driveService.analyzeSkillGap(loggedInStudent, drive);

            UIHelper.printLine();
            System.out.println("  SKILL GAP REPORT for: " + drive.getCompanyName()
                    + " - " + drive.getJobRole());
            UIHelper.printLine();
            System.out.println("  Required Skills : " + drive.getRequiredSkills().toString());
            System.out.println("  Your Skills     : " + loggedInStudent.getSkills().toString());
            UIHelper.printLine();

            if (missingSkills.isEmpty()) {
                UIHelper.printSuccess("Great! You have ALL the required skills for this drive!");
            } else {
                System.out.println("  Missing Skills  : " + missingSkills.toString());
                System.out.println("  Tip: Learn the above " + missingSkills.size()
                        + " skill(s) to improve your chances!");
            }

        } catch (DriveNotFoundException e) {
            UIHelper.printError(e.getMessage());
        }
    }

    // 6. View resume score
    private void viewResumeScore() {
        // Reload latest student data first
        Student freshData = userService.getStudentById(loggedInStudent.getUserId());
        if (freshData != null) {
            loggedInStudent = freshData;
        }
        reportService.generateResumeReport(loggedInStudent);
    }

    // 7. View notifications
    private void viewNotifications() {
        UIHelper.printSubHeading("MY NOTIFICATIONS");
        ArrayList<Notification> notifs = notificationService.getNotificationsForUser(
                loggedInStudent.getUserId());

        if (notifs.isEmpty()) {
            UIHelper.printInfo("No notifications yet.");
            return;
        }

        // Show most recent first (reverse order)
        for (int i = notifs.size() - 1; i >= 0; i--) {
            notifs.get(i).printDetails();
        }

        System.out.println("  Total notifications: " + notifs.size());
        UIHelper.printLine();

        // Mark all as read
        notificationService.markAllAsRead(loggedInStudent.getUserId());
        UIHelper.printInfo("All notifications marked as read.");
    }

    // 8. Manage skills (add skills to profile)
    private void manageSkills() {
        // Reload latest student data
        Student freshData = userService.getStudentById(loggedInStudent.getUserId());
        if (freshData != null) {
            loggedInStudent = freshData;
        }

        UIHelper.printSubHeading("MANAGE MY SKILLS");
        int choice = 0;

        while (choice != 3) {
            System.out.println("  Current Skills: " + loggedInStudent.getSkills().toString());
            UIHelper.printLine();
            UIHelper.printMenuItem(1, "Add a New Skill");
            UIHelper.printMenuItem(2, "Remove a Skill");
            UIHelper.printMenuItem(3, "Go Back");
            UIHelper.printChoicePrompt();

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                UIHelper.printError("Please enter a valid number.");
                continue;
            }

            if (choice == 1) {
                System.out.print("  Enter skill to add: ");
                String skill = scanner.nextLine().trim();
                if (!skill.isEmpty()) {
                    loggedInStudent.addSkill(skill);
                    userService.updateStudent(loggedInStudent);
                    UIHelper.printSuccess("Skill '" + skill + "' added successfully!");
                } else {
                    UIHelper.printError("Skill cannot be empty.");
                }
            } else if (choice == 2) {
                System.out.print("  Enter skill to remove: ");
                String skill = scanner.nextLine().trim().toLowerCase();
                ArrayList<String> skills = loggedInStudent.getSkills();
                boolean removed = false;
                for (int i = 0; i < skills.size(); i++) {
                    if (skills.get(i).equalsIgnoreCase(skill)) {
                        skills.remove(i);
                        removed = true;
                        break;
                    }
                }
                if (removed) {
                    userService.updateStudent(loggedInStudent);
                    UIHelper.printSuccess("Skill '" + skill + "' removed.");
                } else {
                    UIHelper.printError("Skill '" + skill + "' not found in your profile.");
                }
            } else if (choice != 3) {
                UIHelper.printError("Invalid choice. Enter 1, 2, or 3.");
            }
        }
    }
}
