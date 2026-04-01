package model;

import java.util.ArrayList;

public class Student extends User {

    private String branch;
    private double cgpa;
    private ArrayList<String> skills;
    private int backlogs;

    public Student(String userId, String name, String email, String password,
                   String branch, double cgpa, int backlogs) {
        super(userId, name, email, password, "STUDENT");
        this.branch = branch;
        this.cgpa = cgpa;
        this.backlogs = backlogs;
        this.skills = new ArrayList<String>();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public void addSkill(String skill) {
        this.skills.add(skill.trim().toLowerCase());
    }

    public int getBacklogs() {
        return backlogs;
    }

    public void setBacklogs(int backlogs) {
        this.backlogs = backlogs;
    }

    public int getResumeScore() {
        int score = 0;

        score += (int)(cgpa / 10.0 * 50);

        int skillPoints = skills.size() * 5;
        if (skillPoints > 40) {
            skillPoints = 40;
        }
        score += skillPoints;

        // No backlogs gives 10 bonus points
        if (backlogs == 0) {
            score += 10;
        }

        return score;
    }

    // Override parent method - Dynamic Method Dispatch
    public String getDisplayInfo() {
        return "Student ID: " + getUserId() + " | Name: " + getName()
                + " | Branch: " + branch + " | CGPA: " + cgpa
                + " | Backlogs: " + backlogs + " | Skills: " + skills.toString();
    }

    // Convert skills list to comma-separated string for file storage
    public String getSkillsAsString() {
        if (skills.isEmpty()) {
            return "NONE";
        }
        String result = "";
        for (int i = 0; i < skills.size(); i++) {
            result += skills.get(i);
            if (i < skills.size() - 1) {
                result += ";";
            }
        }
        return result;
    }

    // Convert to file-safe string
    public String toFileString() {
        return getUserId() + "," + getName() + "," + getEmail() + ","
                + getPassword() + ",STUDENT," + branch + "," + cgpa + ","
                + backlogs + "," + getSkillsAsString();
    }
}
