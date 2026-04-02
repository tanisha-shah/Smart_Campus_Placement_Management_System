package service;

import java.util.ArrayList;
import model.Application;
import model.Student;

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


    public void generateSummaryReport() {

        System.out.println("========================================");
        System.out.println("     CAMPUS PLACEMENT - SUMMARY REPORT");
        System.out.println("========================================");

        System.out.println("Total Registered Students : " + userService.getStudentCount());
        System.out.println("Total Registered Companies: " + userService.getCompanyCount());
        System.out.println("Total Drives Posted       : " + driveService.getDriveCount());
        System.out.println("Total Applications        : " + applicationService.getApplicationCount());

        ArrayList<Application> allApps = applicationService.getAllApplications();

        int applied = 0;
        int shortlisted = 0;
        int rejected = 0;

        for (int i = 0; i < allApps.size(); i++) {
            String status = allApps.get(i).getStatus();

            if (status.equals("APPLIED")) {
                applied = applied + 1;
            } else if (status.equals("SHORTLISTED")) {
                shortlisted = shortlisted + 1;
            } else if (status.equals("REJECTED")) {
                rejected = rejected + 1;
            }
        }

        System.out.println("Applications - Applied     : " + applied);
        System.out.println("Applications - Shortlisted : " + shortlisted);
        System.out.println("Applications - Rejected    : " + rejected);

        System.out.println("========================================");
    }


    public void generateResumeReport(Student student) {

        int score = student.getResumeScore();

        System.out.println("----------------------------------------");
        System.out.println("RESUME SCORE REPORT");
        System.out.println("----------------------------------------");

        System.out.println("Name     : " + student.getName());
        System.out.println("Branch   : " + student.getBranch());
        System.out.println("CGPA     : " + student.getCgpa());
        System.out.println("Skills   : " + student.getSkills());
        System.out.println("Backlogs : " + student.getBacklogs());

        System.out.println("----------------------------------------");
        System.out.println("RESUME SCORE: " + score + " / 100");
        System.out.println("----------------------------------------");

        if (score >= 80) {
            System.out.println("Feedback: Excellent profile");
        } else if (score >= 60) {
            System.out.println("Feedback: Good profile");
        } else if (score >= 40) {
            System.out.println("Feedback: Average profile");
        } else {
            System.out.println("Feedback: Needs improvement");
        }

        System.out.println("----------------------------------------");
    }
}