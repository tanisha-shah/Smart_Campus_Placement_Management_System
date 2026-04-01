package service;

import model.Student;
import model.Company;
import model.Admin;
import model.User;
import utils.FileHelper;
import utils.UIHelper;
import exceptions.InvalidLoginException;
import exceptions.UserAlreadyExistsException;

import java.util.ArrayList;

// UserService handles all user-related operations
// Registration, Login, fetching users
public class UserService {

    // ===================== STUDENT OPERATIONS =====================

    // Register a new student
    public void registerStudent(Student student) throws UserAlreadyExistsException {
        // Check if email already exists
        if (emailExists(student.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered: " + student.getEmail());
        }
        // Save to file
        FileHelper.writeLineToFile(FileHelper.STUDENTS_FILE, student.toFileString());
        UIHelper.printSuccess("Student registered successfully! Your ID: " + student.getUserId());
    }

    // Login a student - returns Student object if successful
    public Student loginStudent(String email, String password) throws InvalidLoginException {
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.STUDENTS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(",");
            // Format: userId,name,email,password,STUDENT,branch,cgpa,backlogs,skills
            if (parts.length >= 9) {
                if (parts[2].equals(email) && parts[3].equals(password)) {
                    return parseStudentFromParts(parts);
                }
            }
        }
        throw new InvalidLoginException("Invalid email or password. Please try again.");
    }

    // Get all students from file
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.STUDENTS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length >= 9) {
                students.add(parseStudentFromParts(parts));
            }
        }
        return students;
    }

    // Find a student by ID
    public Student getStudentById(String studentId) {
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.STUDENTS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length >= 9 && parts[0].equals(studentId)) {
                return parseStudentFromParts(parts);
            }
        }
        return null;
    }

    // Update student data (saves all students back after modifying one)
    public void updateStudent(Student updatedStudent) {
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.STUDENTS_FILE);
        ArrayList<String> updatedLines = new ArrayList<String>();
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length >= 1 && parts[0].equals(updatedStudent.getUserId())) {
                updatedLines.add(updatedStudent.toFileString());
            } else {
                updatedLines.add(lines.get(i));
            }
        }
        FileHelper.writeAllLines(FileHelper.STUDENTS_FILE, updatedLines);
    }

    // Parse a Student from CSV parts
    private Student parseStudentFromParts(String[] parts) {
        String userId = parts[0];
        String name = parts[1];
        String email = parts[2];
        String password = parts[3];
        // parts[4] = "STUDENT"
        String branch = parts[5];
        double cgpa = 0.0;
        int backlogs = 0;
        try {
            cgpa = Double.parseDouble(parts[6]);
            backlogs = Integer.parseInt(parts[7]);
        } catch (NumberFormatException e) {
            // use defaults
        }
        Student student = new Student(userId, name, email, password, branch, cgpa, backlogs);

        // Parse skills - stored as semicolon-separated in parts[8]
        if (parts.length > 8 && !parts[8].equals("NONE")) {
            String[] skillArr = parts[8].split(";");
            for (int j = 0; j < skillArr.length; j++) {
                if (!skillArr[j].trim().isEmpty()) {
                    student.addSkill(skillArr[j].trim());
                }
            }
        }
        return student;
    }

    // ===================== COMPANY OPERATIONS =====================

    // Register a new company
    public void registerCompany(Company company) throws UserAlreadyExistsException {
        if (emailExists(company.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered: " + company.getEmail());
        }
        FileHelper.writeLineToFile(FileHelper.COMPANIES_FILE, company.toFileString());
        UIHelper.printSuccess("Company registered successfully! Your ID: " + company.getUserId());
    }

    // Login a company
    public Company loginCompany(String email, String password) throws InvalidLoginException {
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.COMPANIES_FILE);
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            // Format: userId,name,email,password,COMPANY,companyName,industry,contactPerson
            if (parts.length >= 8) {
                if (parts[2].equals(email) && parts[3].equals(password)) {
                    return new Company(parts[0], parts[1], parts[2], parts[3],
                            parts[5], parts[6], parts[7]);
                }
            }
        }
        throw new InvalidLoginException("Invalid email or password. Please try again.");
    }

    // Get all companies from file
    public ArrayList<Company> getAllCompanies() {
        ArrayList<Company> companies = new ArrayList<Company>();
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.COMPANIES_FILE);
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length >= 8) {
                companies.add(new Company(parts[0], parts[1], parts[2], parts[3],
                        parts[5], parts[6], parts[7]));
            }
        }
        return companies;
    }

    // ===================== ADMIN OPERATIONS =====================

    // Login an admin
    public Admin loginAdmin(String email, String password) throws InvalidLoginException {
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.ADMINS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            // Format: userId,name,email,password,ADMIN,adminCode
            if (parts.length >= 6) {
                if (parts[2].equals(email) && parts[3].equals(password)) {
                    return new Admin(parts[0], parts[1], parts[2], parts[3], parts[5]);
                }
            }
        }
        throw new InvalidLoginException("Invalid admin email or password.");
    }

    // ===================== HELPER METHODS =====================

    // Check if an email is already registered (checks all user files)
    private boolean emailExists(String email) {
        // Check students
        ArrayList<String> studentLines = FileHelper.readAllLines(FileHelper.STUDENTS_FILE);
        for (int i = 0; i < studentLines.size(); i++) {
            String[] parts = studentLines.get(i).split(",");
            if (parts.length > 2 && parts[2].equals(email)) {
                return true;
            }
        }
        // Check companies
        ArrayList<String> companyLines = FileHelper.readAllLines(FileHelper.COMPANIES_FILE);
        for (int i = 0; i < companyLines.size(); i++) {
            String[] parts = companyLines.get(i).split(",");
            if (parts.length > 2 && parts[2].equals(email)) {
                return true;
            }
        }
        return false;
    }

    // Get count of registered students
    public int getStudentCount() {
        return FileHelper.readAllLines(FileHelper.STUDENTS_FILE).size();
    }

    // Get count of registered companies
    public int getCompanyCount() {
        return FileHelper.readAllLines(FileHelper.COMPANIES_FILE).size();
    }
}
