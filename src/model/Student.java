package model;

import java.util.ArrayList;

public class Student extends User {

    private String branch;
    private double cgpa;
    private ArrayList<String> skills;
    private int backlogs;

    // constructor
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

    // add skill
    public void addSkill(String skill) {
        skills.add(skill.trim().toLowerCase());
    }

    public int getBacklogs() {
        return backlogs;
    }

    public void setBacklogs(int bl) {
        backlogs = bl;
    }

    // simple resume score
    public int getResumeScore() {
        int score = 0;

        score += (int)(cgpa * 10);          // cgpa part
        score += skills.size() * 5;         // skills
        score -= backlogs * 2;              // penalty

        return score;
    }

    // display student info
    public String getDisplayInfo() {
        return "Student ID: " + getUserId() +
                " | Name: " + getName() +
                " | Branch: " + branch +
                " | CGPA: " + cgpa +
                " | Backlogs: " + backlogs +
                " | Skills: " + skills;
    }

    // convert skills list to string
    public String getSkillsAsString() {

        if (skills.size() == 0) {
            return "NONE";
        }

        String res = "";

        for (int i = 0; i < skills.size(); i++) {
            res += skills.get(i);

            if (i != skills.size() - 1) {
                res += ";";
            }
        }

        return res;
    }

    // convert to file string
    public String toFileString() {
        return getUserId() + "," + getName() + "," + getEmail() + ","
                + getPassword() + ",STUDENT," + branch + "," + cgpa + ","
                + backlogs + "," + getSkillsAsString();
    }
}