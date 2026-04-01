package model;

import java.util.ArrayList;

// Drive class represents a campus placement drive posted by a company
public class Drive {

    private String driveId;
    private String companyId;
    private String companyName;
    private String jobRole;
    private double ctc;              // Cost to Company (package in LPA)
    private double minCgpa;
    private int maxBacklogs;
    private ArrayList<String> eligibleBranches;
    private ArrayList<String> requiredSkills;
    private String driveDate;
    private String location;
    private String jobType;          // Full-time, Internship, etc.
    private String status;           // ACTIVE, CLOSED

    // Constructor
    public Drive(String driveId, String companyId, String companyName,
                 String jobRole, double ctc, double minCgpa, int maxBacklogs,
                 String driveDate, String location, String jobType) {
        this.driveId = driveId;
        this.companyId = companyId;
        this.companyName = companyName;
        this.jobRole = jobRole;
        this.ctc = ctc;
        this.minCgpa = minCgpa;
        this.maxBacklogs = maxBacklogs;
        this.driveDate = driveDate;
        this.location = location;
        this.jobType = jobType;
        this.status = "ACTIVE";
        this.eligibleBranches = new ArrayList<String>();
        this.requiredSkills = new ArrayList<String>();
    }

    // Getters and Setters
    public String getDriveId() {
        return driveId;
    }

    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public double getCtc() {
        return ctc;
    }

    public void setCtc(double ctc) {
        this.ctc = ctc;
    }

    public double getMinCgpa() {
        return minCgpa;
    }

    public void setMinCgpa(double minCgpa) {
        this.minCgpa = minCgpa;
    }

    public int getMaxBacklogs() {
        return maxBacklogs;
    }

    public void setMaxBacklogs(int maxBacklogs) {
        this.maxBacklogs = maxBacklogs;
    }

    public ArrayList<String> getEligibleBranches() {
        return eligibleBranches;
    }

    public void addEligibleBranch(String branch) {
        this.eligibleBranches.add(branch.trim().toUpperCase());
    }

    public ArrayList<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void addRequiredSkill(String skill) {
        this.requiredSkills.add(skill.trim().toLowerCase());
    }

    public String getDriveDate() {
        return driveDate;
    }

    public void setDriveDate(String driveDate) {
        this.driveDate = driveDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Get branches as a semicolon-separated string
    public String getBranchesAsString() {
        if (eligibleBranches.isEmpty()) {
            return "ALL";
        }
        String result = "";
        for (int i = 0; i < eligibleBranches.size(); i++) {
            result += eligibleBranches.get(i);
            if (i < eligibleBranches.size() - 1) {
                result += ";";
            }
        }
        return result;
    }

    // Get skills as a semicolon-separated string
    public String getSkillsAsString() {
        if (requiredSkills.isEmpty()) {
            return "NONE";
        }
        String result = "";
        for (int i = 0; i < requiredSkills.size(); i++) {
            result += requiredSkills.get(i);
            if (i < requiredSkills.size() - 1) {
                result += ";";
            }
        }
        return result;
    }

    // Print drive details in a nice format
    public void printDetails() {
        System.out.println("  Drive ID     : " + driveId);
        System.out.println("  Company      : " + companyName);
        System.out.println("  Job Role     : " + jobRole);
        System.out.println("  Job Type     : " + jobType);
        System.out.println("  CTC          : " + ctc + " LPA");
        System.out.println("  Drive Date   : " + driveDate);
        System.out.println("  Location     : " + location);
        System.out.println("  Min CGPA     : " + minCgpa);
        System.out.println("  Max Backlogs : " + maxBacklogs);
        System.out.println("  Branches     : " + getBranchesAsString());
        System.out.println("  Skills Reqd  : " + getSkillsAsString());
        System.out.println("  Status       : " + status);
    }

    // Convert to file-safe string
    public String toFileString() {
        return driveId + "," + companyId + "," + companyName + "," + jobRole + ","
                + ctc + "," + minCgpa + "," + maxBacklogs + "," + driveDate + ","
                + location + "," + jobType + "," + status + ","
                + getBranchesAsString() + "," + getSkillsAsString();
    }
}
