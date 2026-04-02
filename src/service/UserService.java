package service;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import model.Admin;
import model.Company;
import model.Student;

public class UserService {

    // ---------- STUDENT ----------

    public void registerStudent(Student student) {

        if (emailExists(student.getEmail())) {
            System.out.println("Email already exists");
            return;
        }

        try {
            FileWriter fw = new FileWriter("data/students.txt", true);
            fw.write(student.toFileString());
            fw.write("\n");
            fw.close();

            System.out.println("Student registered. ID: " + student.getUserId());

        } catch (Exception e) {
            System.out.println("Error writing file");
        }
    }

    public Student loginStudent(String email, String password) {

        try {
            File file = new File("data/students.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] p = line.split(",");

                if (p.length >= 9) {
                    if (p[2].equals(email) && p[3].equals(password)) {
                        sc.close();
                        return parseStudent(p);
                    }
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error reading file");
        }

        System.out.println("Invalid login");
        return null;
    }

    public ArrayList<Student> getAllStudents() {

        ArrayList<Student> list = new ArrayList<>();

        try {
            File file = new File("data/students.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");

                if (p.length >= 9) {
                    list.add(parseStudent(p));
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error reading file");
        }

        return list;
    }

    public Student getStudentById(String id) {

        try {
            File file = new File("data/students.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");

                if (p.length >= 9 && p[0].equals(id)) {
                    sc.close();
                    return parseStudent(p);
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error reading file");
        }

        return null;
    }

    public void updateStudent(Student s) {

        ArrayList<String> updated = new ArrayList<>();

        try {
            File file = new File("data/students.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] p = line.split(",");

                if (p[0].equals(s.getUserId())) {
                    updated.add(s.toFileString());
                } else {
                    updated.add(line);
                }
            }

            sc.close();

            FileWriter fw = new FileWriter("data/students.txt");
            for (int i = 0; i < updated.size(); i++) {
                fw.write(updated.get(i) + "\n");
            }
            fw.close();

        } catch (Exception e) {
            System.out.println("Error updating file");
        }
    }

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

    // ---------- COMPANY ----------

    public void registerCompany(Company c) {

        if (emailExists(c.getEmail())) {
            System.out.println("Email already exists");
            return;
        }

        try {
            FileWriter fw = new FileWriter("data/companies.txt", true);
            fw.write(c.toFileString() + "\n");
            fw.close();

            System.out.println("Company registered. ID: " + c.getUserId());

        } catch (Exception e) {
            System.out.println("Error writing file");
        }
    }

    public Company loginCompany(String email, String password) {

        try {
            File file = new File("data/companies.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");

                if (p.length >= 8) {
                    if (p[2].equals(email) && p[3].equals(password)) {
                        sc.close();
                        return new Company(p[0], p[1], p[2], p[3], p[5], p[6], p[7]);
                    }
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error reading file");
        }

        System.out.println("Invalid login");
        return null;
    }

    public ArrayList<Company> getAllCompanies() {

        ArrayList<Company> list = new ArrayList<>();

        try {
            File file = new File("data/companies.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");

                if (p.length >= 8) {
                    list.add(new Company(p[0], p[1], p[2], p[3], p[5], p[6], p[7]));
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error reading file");
        }

        return list;
    }

    // ---------- ADMIN ----------

    public Admin loginAdmin(String email, String password) {

        try {
            File file = new File("data/admins.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");

                if (p.length >= 6) {
                    if (p[2].equals(email) && p[3].equals(password)) {
                        sc.close();
                        return new Admin(p[0], p[1], p[2], p[3], p[5]);
                    }
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error reading file");
        }

        System.out.println("Invalid admin login");
        return null;
    }

    // ---------- COMMON ----------

    private boolean emailExists(String email) {

        try {
            Scanner sc1 = new Scanner(new File("data/students.txt"));
            while (sc1.hasNextLine()) {
                String[] p = sc1.nextLine().split(",");
                if (p.length > 2 && p[2].equals(email)) {
                    sc1.close();
                    return true;
                }
            }
            sc1.close();

            Scanner sc2 = new Scanner(new File("data/companies.txt"));
            while (sc2.hasNextLine()) {
                String[] p = sc2.nextLine().split(",");
                if (p.length > 2 && p[2].equals(email)) {
                    sc2.close();
                    return true;
                }
            }
            sc2.close();

        } catch (Exception e) {
            System.out.println("Error checking email");
        }

        return false;
    }

    public int getStudentCount() {
        return getAllStudents().size();
    }

    public int getCompanyCount() {
        return getAllCompanies().size();
    }
}