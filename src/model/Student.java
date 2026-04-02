package model;

import java.util.ArrayList;

public class Student extends User {

    private String branch;
    private double cgpa;
    private ArrayList<String> skills;
    private int backlogs;

    public Student(String uId, String n, String em, String p,
                   String b, double cg, int bl) {
        super(uId, n, em, p, "STUDENT");
        branch = b;
        cgpa = cg;
        backlogs = bl;
        skills = new ArrayList<String>();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String b) {
        branch = b;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cg) {
        cgpa = cg;
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

    public void setBacklogs(int bl) {
        backlogs = bl;
    }

    public int getResumeScore() {
        int score = 0;
        //cgpa
        // total cg ka 50 marks to score me add karenge
        score += (int)(cgpa / 10.0 * 50);

        //skills
        int skillPoints = skills.size() * 5;
        if (skillPoints > 40) {
            skillPoints = 40;
        }
        score += skillPoints;

        //check backlogs
        if (backlogs == 0) {
            score += 10;
        }

        return score;
    }

    //Dynamic Method Dispatch
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
                result = result + ";";
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
