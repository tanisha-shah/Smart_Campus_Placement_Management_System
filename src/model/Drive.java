package model;

import java.util.ArrayList;

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
    private String jobType;        
    private String status;          

public Drive(String dId, String cId, String cName,
             String jRole, double ctcVal, double minCgpaVal, int maxBacklogsVal,
             String dDate, String loc, String jType) {

        driveId = dId;
        companyId = cId;
        companyName = cName;
        jobRole = jRole;
        ctc = ctcVal;
        minCgpa = minCgpaVal;
        maxBacklogs = maxBacklogsVal;
        driveDate = dDate;
        location = loc;
        jobType = jType;

        status = "ACTIVE";
        eligibleBranches = new ArrayList<String>();
        requiredSkills = new ArrayList<String>();
}


        public String getDriveId() {
            return driveId;
        }

        public void setDriveId(String dId) {
            driveId = dId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String cName) {
            companyName = cName;
        }

        public String getJobRole() {
            return jobRole;
        }

        public void setJobRole(String jRole) {
            jobRole = jRole;
        }

        public double getCtc() {
            return ctc;
        }

        public void setCtc(double ctcVal) {
            ctc = ctcVal;
        }

        public double getMinCgpa() {
            return minCgpa;
        }

        public void setMinCgpa(double minCgpaVal) {
            minCgpa = minCgpaVal;
        }

        public int getMaxBacklogs() {
            return maxBacklogs;
        }

        public void setMaxBacklogs(int maxBacklogsVal) {
            maxBacklogs = maxBacklogsVal;
        }

        public ArrayList<String> getEligibleBranches() {
            return eligibleBranches;
        }

        public void addEligibleBranch(String branch) {
            eligibleBranches.add(branch.trim().toUpperCase());
        }

        public ArrayList<String> getRequiredSkills() {
            return requiredSkills;
        }

        public void addRequiredSkill(String skill) {
            requiredSkills.add(skill.trim().toLowerCase());
        }

        public String getDriveDate() {
            return driveDate;
        }

        public void setDriveDate(String dDate) {
            driveDate = dDate;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String loc) {
            location = loc;
        }

        public String getJobType() {
            return jobType;
        }

        public void setJobType(String jType) {
            jobType = jType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String stat) {
            status = stat;
        }




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
