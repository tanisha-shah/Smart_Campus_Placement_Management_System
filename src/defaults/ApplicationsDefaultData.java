package defaults;

import model.Application;

import java.util.ArrayList;

/**
 * Default lines for {@code data/applications.txt}.
 * Matches {@link Application#toFileString()}: applicationId, studentId, studentName,
 * driveId, companyName, jobRole, status, appliedDate.
 */
public final class ApplicationsDefaultData {

    private ApplicationsDefaultData() {
    }

    public static ArrayList<String> getDefaultLines() {
        ArrayList<String> lines = new ArrayList<String>();

        Application app1 = new Application(
                "APP001",
                "STU001",
                "Alice Sharma",
                "DRIVE001",
                "TechCorp Solutions Pvt Ltd",
                "Software Engineer",
                "2026-03-15");
        app1.setStatus("SHORTLISTED");
        lines.add(app1.toFileString());

        Application app2 = new Application(
                "APP002",
                "STU001",
                "Alice Sharma",
                "DRIVE004",
                "Infosys Technologies",
                "System Engineer",
                "2026-03-20");
        lines.add(app2.toFileString());

        Application app3 = new Application(
                "APP003",
                "STU002",
                "Bob Verma",
                "DRIVE003",
                "Tata Motors Limited",
                "Graduate Engineer Trainee",
                "2026-03-18");
        lines.add(app3.toFileString());

        return lines;
    }
}
