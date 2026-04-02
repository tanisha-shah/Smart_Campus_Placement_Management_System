package defaults;

import model.Drive;

import java.util.ArrayList;

/**
 * Default lines for {@code data/drives.txt}.
 * Matches {@link Drive#toFileString()}: driveId, companyId, companyName, jobRole, ctc,
 * minCgpa, maxBacklogs, driveDate, location, jobType, status, branches, skills.
 */
public final class DrivesDefaultData {

    private DrivesDefaultData() {
    }

    public static ArrayList<String> getDefaultLines() {
        ArrayList<String> lines = new ArrayList<String>();

        Drive d1 = new Drive(
                "DRIVE001",
                "COMP001",
                "TechCorp Solutions Pvt Ltd",
                "Software Engineer",
                12.5,
                7.0,
                2,
                "2026-05-10",
                "Bangalore",
                "Full-Time");
        d1.addEligibleBranch("CSE");
        d1.addEligibleBranch("IT");
        d1.addRequiredSkill("java");
        d1.addRequiredSkill("python");
        d1.addRequiredSkill("sql");
        lines.add(d1.toFileString());

        Drive d2 = new Drive(
                "DRIVE002",
                "COMP001",
                "TechCorp Solutions Pvt Ltd",
                "Data Analyst Intern",
                6.0,
                6.5,
                0,
                "2026-05-20",
                "Bangalore",
                "Internship");
        d2.addRequiredSkill("python");
        d2.addRequiredSkill("sql");
        d2.addRequiredSkill("excel");
        lines.add(d2.toFileString());

        Drive d3 = new Drive(
                "DRIVE003",
                "COMP002",
                "Tata Motors Limited",
                "Graduate Engineer Trainee",
                8.0,
                6.0,
                6,
                "2026-06-01",
                "Pune",
                "Full-Time");
        d3.addEligibleBranch("ME");
        d3.addEligibleBranch("EEE");
        d3.addEligibleBranch("ECE");
        d3.addRequiredSkill("cad");
        d3.addRequiredSkill("matlab");
        lines.add(d3.toFileString());

        Drive d4 = new Drive(
                "DRIVE004",
                "COMP003",
                "Infosys Technologies",
                "System Engineer",
                9.5,
                7.5,
                0,
                "2026-05-28",
                "Mysuru",
                "Full-Time");
        d4.addEligibleBranch("CSE");
        d4.addEligibleBranch("IT");
        d4.addRequiredSkill("java");
        d4.addRequiredSkill("sql");
        lines.add(d4.toFileString());

        Drive d5 = new Drive(
                "DRIVE005",
                "COMP003",
                "Infosys Technologies",
                "Digital Specialist",
                11.0,
                8.0,
                0,
                "2026-06-15",
                "Hyderabad",
                "Full-Time");
        d5.addEligibleBranch("CSE");
        d5.addEligibleBranch("IT");
        d5.addEligibleBranch("ECE");
        d5.addRequiredSkill("java");
        d5.addRequiredSkill("angular");
        d5.addRequiredSkill("typescript");
        lines.add(d5.toFileString());

        return lines;
    }
}
