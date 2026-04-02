package cli;

import java.util.Scanner;
import model.Admin;
import model.Company;
import model.Student;
import service.UserService;

public class AuthMenu {

    private Scanner scanner;
    private UserService userService;

    public AuthMenu(Scanner sc, UserService us) {
        scanner = sc;
        userService = us;
    }

    public Student registerStudent() {

        System.out.println("\n--- STUDENT REGISTER ---");

        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            System.out.print("Branch: ");
            String branch = scanner.nextLine();

            System.out.print("CGPA: ");
            double cgpa = Double.parseDouble(scanner.nextLine());

            System.out.print("Backlogs: ");
            int backlogs = Integer.parseInt(scanner.nextLine());

            String id = "STU" + System.currentTimeMillis();

            Student s = new Student(id, name, email, password, branch, cgpa, backlogs);

            System.out.print("Skills (comma or blank): ");
            String input = scanner.nextLine();

            if (!input.equals("")) {
                String[] arr = input.split(",");
                for (int i = 0; i < arr.length; i++) {
                    s.addSkill(arr[i].trim());
                }
            }

            userService.registerStudent(s);
            return s;

        } catch (Exception e) {
            System.out.println("Error in registration");
            return null;
        }
    }

    public Student loginStudent() {

        System.out.println("\n--- STUDENT LOGIN ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Student s = userService.loginStudent(email, password);

        if (s != null) {
            System.out.println("Login success");
        }

        return s;
    }

    public Company registerCompany() {

        System.out.println("\n--- COMPANY REGISTER ---");

        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            System.out.print("Company Name: ");
            String cname = scanner.nextLine();

            System.out.print("Industry: ");
            String industry = scanner.nextLine();

            System.out.print("Contact Person: ");
            String contact = scanner.nextLine();

            String id = "CMP" + System.currentTimeMillis();

            Company c = new Company(id, name, email, password, cname, industry, contact);

            userService.registerCompany(c);
            return c;

        } catch (Exception e) {
            System.out.println("Error in registration");
            return null;
        }
    }

    public Company loginCompany() {

        System.out.println("\n--- COMPANY LOGIN ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Company c = userService.loginCompany(email, password);

        if (c != null) {
            System.out.println("Login success");
        }

        return c;
    }

    public Admin loginAdmin() {

        System.out.println("\n--- ADMIN LOGIN ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Admin a = userService.loginAdmin(email, password);

        if (a != null) {
            System.out.println("Admin login success");
        }

        return a;
    }
}