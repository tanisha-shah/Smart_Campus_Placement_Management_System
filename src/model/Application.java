package model;

// Application class represents a student's application to a drive
public class Application {

    private String applicationId;
    private String studentId;
    private String studentName;
    private String driveId;
    private String companyName;
    private String jobRole;
    private String status;          // APPLIED, SHORTLISTED, REJECTED
    private String appliedDate;

    // Constructor
    public Application(String applicationId, String studentId, String studentName,
                       String driveId, String companyName, String jobRole, String appliedDate) {
        this.applicationId = applicationId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.driveId = driveId;
        this.companyName = companyName;
        this.jobRole = jobRole;
        this.status = "APPLIED"; // Default status when first applied
        this.appliedDate = appliedDate;
    }

    // Getters and Setters
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDriveId() {
        return driveId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobRole() {
        return jobRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    // Print application details
    public void printDetails() {
        System.out.println("  Application ID : " + applicationId);
        System.out.println("  Student        : " + studentName + " (" + studentId + ")");
        System.out.println("  Drive ID       : " + driveId);
        System.out.println("  Company        : " + companyName);
        System.out.println("  Job Role       : " + jobRole);
        System.out.println("  Applied Date   : " + appliedDate);
        System.out.println("  Status         : " + status);
    }

    // Convert to file-safe string
    public String toFileString() {
        return applicationId + "," + studentId + "," + studentName + ","
                + driveId + "," + companyName + "," + jobRole + ","
                + status + "," + appliedDate;
    }
}
