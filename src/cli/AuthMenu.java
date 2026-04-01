package cli;

import model.Student;
import model.Company;
import model.Admin;
import service.UserService;
import utils.UIHelper;
import exceptions.InvalidLoginException;
import exceptions.UserAlreadyExistsException;

import java.util.Scanner;

// AuthMenu handles registration and login for all user types
public class AuthMenu {

    private Scanner scanner;
    private UserService userService;

    public AuthMenu(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    // ===================== STUDENT REGISTRATION =====================

    public Student registerStudent() {
        UIHelper.printSubHeading("STUDENT REGISTRATION");

        try {
            System.out.print("  Full Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                UIHelper.printError("Name cannot be empty.");
                return null;
            }

            System.out.print("  Email: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty() || !email.contains("@")) {
                UIHelper.printError("Please enter a valid email address.");
                return null;
            }

            System.out.print("  Password (min 6 chars): ");
            String password = scanner.nextLine().trim();
            if (password.length() < 6) {
                UIHelper.printError("Password must be at least 6 characters long.");
                return null;
            }

            System.out.print("  Branch (e.g. CSE, IT, ECE, ME): ");
            String branch = scanner.nextLine().trim().toUpperCase();
            if (branch.isEmpty()) {
                UIHelper.printError("Branch cannot be empty.");
                return null;
            }

            System.out.print("  CGPA (0.0 to 10.0): ");
            double cgpa = Double.parseDouble(scanner.nextLine().trim());
            if (cgpa < 0 || cgpa > 10) {
                UIHelper.printError("CGPA must be between 0.0 and 10.0.");
                return null;
            }

            System.out.print("  Number of Active Backlogs: ");
            int backlogs = Integer.parseInt(scanner.nextLine().trim());
            if (backlogs < 0) {
                UIHelper.printError("Backlogs cannot be negative.");
                return null;
            }

            // Generate unique student ID
            String studentId = UIHelper.generateId("STU");
            Student student = new Student(studentId, name, email, password, branch, cgpa, backlogs);

            // Ask for initial skills (optional)
            System.out.print("  Add your skills (comma-separated, or press Enter to skip): ");
            String skillInput = scanner.nextLine().trim();
            if (!skillInput.isEmpty()) {
                String[] skills = skillInput.split(",");
                for (int i = 0; i < skills.length; i++) {
                    student.addSkill(skills[i].trim());
                }
            }

            userService.registerStudent(student);
            return student;

        } catch (NumberFormatException e) {
            UIHelper.printError("Invalid number format. Please enter correct values.");
            return null;
        } catch (UserAlreadyExistsException e) {
            UIHelper.printError(e.getMessage());
            return null;
        }
    }

    // ===================== STUDENT LOGIN =====================

    public Student loginStudent() {
        UIHelper.printSubHeading("STUDENT LOGIN");

        System.out.print("  Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("  Password: ");
        String password = scanner.nextLine().trim();

        try {
            Student student = userService.loginStudent(email, password);
            UIHelper.printSuccess("Login successful! Welcome back, " + student.getName() + "!");
            return student;
        } catch (InvalidLoginException e) {
            UIHelper.printError(e.getMessage());
            return null;
        }
    }

    // ===================== COMPANY REGISTRATION =====================

    public Company registerCompany() {
        UIHelper.printSubHeading("COMPANY REGISTRATION");

        try {
            System.out.print("  Contact Person Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                UIHelper.printError("Name cannot be empty.");
                return null;
            }

            System.out.print("  Email: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty() || !email.contains("@")) {
                UIHelper.printError("Please enter a valid email address.");
                return null;
            }

            System.out.print("  Password (min 6 chars): ");
            String password = scanner.nextLine().trim();
            if (password.length() < 6) {
                UIHelper.printError("Password must be at least 6 characters.");
                return null;
            }

            System.out.print("  Company Name: ");
            String companyName = scanner.nextLine().trim();
            if (companyName.isEmpty()) {
                UIHelper.printError("Company name cannot be empty.");
                return null;
            }

            System.out.print("  Industry (e.g. IT, Finance, Healthcare): ");
            String industry = scanner.nextLine().trim();
            if (industry.isEmpty()) industry = "Not Specified";

            System.out.print("  HR Contact Person: ");
            String contactPerson = scanner.nextLine().trim();
            if (contactPerson.isEmpty()) contactPerson = name;

            String companyId = UIHelper.generateId("CMP");
            Company company = new Company(companyId, name, email, password,
                    companyName, industry, contactPerson);

            userService.registerCompany(company);
            return company;

        } catch (UserAlreadyExistsException e) {
            UIHelper.printError(e.getMessage());
            return null;
        }
    }

    // ===================== COMPANY LOGIN =====================

    public Company loginCompany() {
        UIHelper.printSubHeading("COMPANY LOGIN");

        System.out.print("  Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("  Password: ");
        String password = scanner.nextLine().trim();

        try {
            Company company = userService.loginCompany(email, password);
            UIHelper.printSuccess("Login successful! Welcome, " + company.getCompanyName() + "!");
            return company;
        } catch (InvalidLoginException e) {
            UIHelper.printError(e.getMessage());
            return null;
        }
    }

    // ===================== ADMIN LOGIN =====================

    public Admin loginAdmin() {
        UIHelper.printSubHeading("ADMIN LOGIN");
        System.out.println("  [Default Admin: admin@campus.edu / admin123]");
        UIHelper.printLine();

        System.out.print("  Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("  Password: ");
        String password = scanner.nextLine().trim();

        try {
            Admin admin = userService.loginAdmin(email, password);
            UIHelper.printSuccess("Admin login successful! Welcome, " + admin.getName() + "!");
            return admin;
        } catch (InvalidLoginException e) {
            UIHelper.printError(e.getMessage());
            return null;
        }
    }
}
