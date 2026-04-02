package model;

public class Application {

    private String applicationId;
    private String studentId;
    private String studentName;
    private String driveId;
    private String companyName;
    private String jobRole;
    private String status;         
    private String appliedDate;


    public Application(String appId, String sId, String sName,
                   String dId, String compName, String jRole, String appDate) {

        applicationId = appId;
        studentId = sId;
        studentName = sName;
        driveId = dId;
        companyName = compName;
        jobRole = jRole;
        status = "APPLIED";
        appliedDate = appDate;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String appId) {
        applicationId = appId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String sName) {
        studentName = sName;
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

    public void setStatus(String stat) {
        status = stat;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    
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
