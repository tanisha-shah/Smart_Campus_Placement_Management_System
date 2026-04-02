package defaults;

import model.Student;

import java.util.ArrayList;

/**
 * Default lines for {@code data/students.txt}.
 * Matches {@link Student#toFileString()}: userId, name, email, password, STUDENT,
 * branch, cgpa, backlogs, skills (semicolon-separated or NONE).
 */
public final class StudentsDefaultData {

    private StudentsDefaultData() {
    }

    public static ArrayList<String> getDefaultLines() {
        ArrayList<String> lines = new ArrayList<String>();

        Student alice = new Student(
                "STU001",
                "Alice Sharma",
                "alice@student.edu",
                "student123",
                "CSE",
                8.5,
                0);
        alice.addSkill("java");
        alice.addSkill("python");
        alice.addSkill("sql");
        lines.add(alice.toFileString());

        Student bob = new Student(
                "STU002",
                "Bob Verma",
                "bob@student.edu",
                "student123",
                "ME",
                7.8,
                1);
        bob.addSkill("cad");
        bob.addSkill("matlab");
        lines.add(bob.toFileString());

        return lines;
    }
}
