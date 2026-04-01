package service;

import model.Student;
import model.Drive;
import model.Application;
import utils.UIHelper;

import java.util.ArrayList;

// ReportService generates reports - uses a Thread to simulate async report generation
// This demonstrates basic multithreading concepts
public class ReportService {

    private UserService userService;
    private DriveService driveService;
    private ApplicationService applicationService;

    public ReportService(UserService userService, DriveService driveService,
                         ApplicationService applicationService) {
        this.userService = userService;
        this.driveService = driveService;
        this.applicationService = applicationService;
    }

    // Generate summary report using a thread (simulates background processing)
    public void generateSummaryReport() {
        System.out.println("  Generating report, please wait...");

        // Create a thread to simulate report generation
        Thread reportThread = new Thread(new Runnable() {
            public void run() {
                try {
                    // Simulate some processing time
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        reportThread.start();
        try {
            reportThread.join(); // Wait for thread to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Now print the report
        UIHelper.printThickLine();
        System.out.println("        CAMPUS PLACEMENT - SUMMARY REPORT");
        UIHelper.printThickLine();
        System.out.println("  Total Registered Students : " + userService.getStudentCount());
        System.out.println("  Total Registered Companies: " + userService.getCompanyCount());
        System.out.println("  Total Drives Posted       : " + driveService.getDriveCount());
        System.out.println("  Total Applications        : " + applicationService.getApplicationCount());

        // Count by status
        ArrayList<Application> allApps = applicationService.getAllApplications();
        int applied = 0, shortlisted = 0, rejected = 0;
        for (int i = 0; i < allApps.size(); i++) {
            String status = allApps.get(i).getStatus();
            if (status.equals("APPLIED")) applied++;
            else if (status.equals("SHORTLISTED")) shortlisted++;
            else if (status.equals("REJECTED")) rejected++;
        }
        System.out.println("  Applications - Applied    : " + applied);
        System.out.println("  Applications - Shortlisted: " + shortlisted);
        System.out.println("  Applications - Rejected   : " + rejected);
        UIHelper.printThickLine();
    }

    // Generate student resume score report (also uses a thread)
    public void generateResumeReport(Student student) {
        System.out.println("  Calculating resume score...");

        Thread scoreThread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(500); // Simulate calculation time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        scoreThread.start();
        try {
            scoreThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int score = student.getResumeScore();
        UIHelper.printLine();
        System.out.println("  RESUME SCORE REPORT for " + student.getName());
        UIHelper.printLine();
        System.out.println("  Name    : " + student.getName());
        System.out.println("  Branch  : " + student.getBranch());
        System.out.println("  CGPA    : " + student.getCgpa() + " / 10.0");
        System.out.println("  Skills  : " + student.getSkills().toString());
        System.out.println("  Backlogs: " + student.getBacklogs());
        UIHelper.printLine();
        System.out.println("  RESUME SCORE: " + score + " / 100");
        UIHelper.printLine();

        // Give feedback based on score
        if (score >= 80) {
            System.out.println("  Feedback: Excellent! Your profile is very strong.");
        } else if (score >= 60) {
            System.out.println("  Feedback: Good profile. Try adding more skills to improve.");
        } else if (score >= 40) {
            System.out.println("  Feedback: Average profile. Focus on improving CGPA and skills.");
        } else {
            System.out.println("  Feedback: Needs improvement. Work on CGPA, skills, and backlogs.");
        }
        UIHelper.printLine();
    }
}
