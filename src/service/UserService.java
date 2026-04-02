package service;

import exceptions.InvalidLoginException;
import exceptions.UserAlreadyExistsException;
import java.util.ArrayList;
import model.Admin;
import model.Company;
import model.Student;
import utils.FileHelper;

public class UserService {

    // REGISTER STUDENT
    public void registerStudent(Student student) throws UserAlreadyExistsException {

        if (emailExists(student.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        FileHelper.writeLineToFile(FileHelper.STUDENTS_FILE, student.toFileString());
        System.out.println("Student registered. ID: " + student.getUserId());
    }

    // LOGIN STUDENT
    public Student loginStudent(String email, String password) throws InvalidLoginException {

        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.STUDENTS_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String[] p = lines.get(i).split(",");

            if (p.length >= 9) {
                if (p[2].equals(email) && p[3].equals(password)) {
                    return parseStudent(p);
                }
            }
        }

        throw new InvalidLoginException("Invalid login");
    }

    // GET ALL STUDENTS
    public ArrayList<Student> getAllStudents() {

        ArrayList<Student> list = new ArrayList<>();
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.STUDENTS_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String[] p = lines.get(i).split(",");
            if (p.length >= 9) {
                list.add(parseStudent(p));
            }
        }

        return list;
    }

    // GET STUDENT BY ID
    public Student getStudentById(String id) {

        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.STUDENTS_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String[] p = lines.get(i).split(",");
            if (p.length >= 9 && p[0].equals(id)) {
                return parseStudent(p);
            }
        }

        return null;
    }

    // UPDATE STUDENT
    public void updateStudent(Student s) {

        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.STUDENTS_FILE);
        ArrayList<String> updated = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String[] p = lines.get(i).split(",");

            if (p[0].equals(s.getUserId())) {
                updated.add(s.toFileString());
            } else {
                updated.add(lines.get(i));
            }
        }

        FileHelper.writeAllLines(FileHelper.STUDENTS_FILE, updated);
    }

    // PARSE STUDENT
    private Student parseStudent(String[] p) {

        double cgpa = 0;
        int backlogs = 0;

        try {
            cgpa = Double.parseDouble(p[6]);
            backlogs = Integer.parseInt(p[7]);
        } catch (Exception e) {}

        Student s = new Student(p[0], p[1], p[2], p[3], p[5], cgpa, backlogs);

        if (p.length > 8 && !p[8].equals("NONE")) {
            String[] skills = p[8].split(";");
            for (int i = 0; i < skills.length; i++) {
                s.addSkill(skills[i]);
            }
        }

        return s;
    }

    // ================= COMPANY =================

    public void registerCompany(Company c) throws UserAlreadyExistsException {

        if (emailExists(c.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        FileHelper.writeLineToFile(FileHelper.COMPANIES_FILE, c.toFileString());
        System.out.println("Company registered. ID: " + c.getUserId());
    }

    public Company loginCompany(String email, String password) throws InvalidLoginException {

        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.COMPANIES_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String[] p = lines.get(i).split(",");

            if (p.length >= 8) {
                if (p[2].equals(email) && p[3].equals(password)) {
                    return new Company(p[0], p[1], p[2], p[3], p[5], p[6], p[7]);
                }
            }
        }

        throw new InvalidLoginException("Invalid login");
    }

    public ArrayList<Company> getAllCompanies() {

        ArrayList<Company> list = new ArrayList<>();
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.COMPANIES_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String[] p = lines.get(i).split(",");

            if (p.length >= 8) {
                list.add(new Company(p[0], p[1], p[2], p[3], p[5], p[6], p[7]));
            }
        }

        return list;
    }

    // ================= ADMIN =================

    public Admin loginAdmin(String email, String password) throws InvalidLoginException {

        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.ADMINS_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String[] p = lines.get(i).split(",");

            if (p.length >= 6) {
                if (p[2].equals(email) && p[3].equals(password)) {
                    return new Admin(p[0], p[1], p[2], p[3], p[5]);
                }
            }
        }

        throw new InvalidLoginException("Invalid admin login");
    }

    // ================= COMMON =================

    private boolean emailExists(String email) {

        ArrayList<String> s = FileHelper.readAllLines(FileHelper.STUDENTS_FILE);
        for (int i = 0; i < s.size(); i++) {
            String[] p = s.get(i).split(",");
            if (p.length > 2 && p[2].equals(email)) return true;
        }

        ArrayList<String> c = FileHelper.readAllLines(FileHelper.COMPANIES_FILE);
        for (int i = 0; i < c.size(); i++) {
            String[] p = c.get(i).split(",");
            if (p.length > 2 && p[2].equals(email)) return true;
        }

        return false;
    }

    public int getStudentCount() {
        return FileHelper.readAllLines(FileHelper.STUDENTS_FILE).size();
    }

    public int getCompanyCount() {
        return FileHelper.readAllLines(FileHelper.COMPANIES_FILE).size();
    }
}