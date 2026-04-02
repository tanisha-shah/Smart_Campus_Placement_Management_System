package service;

import model.Application;
import model.Drive;
import model.Student;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class ApplicationService {

    public void applyForDrive(Student student, Drive drive) {

        if (hasAlreadyApplied(student.getUserId(), drive.getDriveId())) {
            System.out.println("Already applied");
            return;
        }

        String appId = "APP" + System.currentTimeMillis();
        String today = java.time.LocalDate.now().toString();

        Application app = new Application(
                appId,
                student.getUserId(),
                student.getName(),
                drive.getDriveId(),
                drive.getCompanyName(),
                drive.getJobRole(),
                today
        );

        try {
            FileWriter fw = new FileWriter("data/applications.txt", true);
            fw.write(app.toFileString() + "\n");
            fw.close();

            System.out.println("Applied successfully");

        } catch (Exception e) {
            System.out.println("Error writing file");
        }
    }

    public boolean hasAlreadyApplied(String studentId, String driveId) {

        ArrayList<Application> list = getApplicationsByStudent(studentId);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDriveId().equals(driveId)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Application> getApplicationsByStudent(String studentId) {

        ArrayList<Application> result = new ArrayList<Application>();
        ArrayList<Application> all = getAllApplications();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getStudentId().equals(studentId)) {
                result.add(all.get(i));
            }
        }

        return result;
    }

    public ArrayList<Application> getApplicationsByDrive(String driveId) {

        ArrayList<Application> result = new ArrayList<Application>();
        ArrayList<Application> all = getAllApplications();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getDriveId().equals(driveId)) {
                result.add(all.get(i));
            }
        }

        return result;
    }

    public ArrayList<Application> getAllApplications() {

        ArrayList<Application> list = new ArrayList<Application>();

        try {
            File file = new File("data/applications.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                Application app = parseApplication(sc.nextLine());
                if (app != null) {
                    list.add(app);
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error reading file");
        }

        return list;
    }

    public boolean updateApplicationStatus(String applicationId, String newStatus) {

        ArrayList<String> updated = new ArrayList<String>();
        boolean found = false;

        try {
            File file = new File("data/applications.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 8 && parts[0].equals(applicationId)) {

                    parts[6] = newStatus;

                    String newLine = "";

                    for (int i = 0; i < parts.length; i++) {
                        newLine += parts[i];
                        if (i != parts.length - 1) {
                            newLine += ",";
                        }
                    }

                    updated.add(newLine);
                    found = true;

                } else {
                    updated.add(line);
                }
            }

            sc.close();

            if (found) {
                FileWriter fw = new FileWriter("data/applications.txt");
                for (int i = 0; i < updated.size(); i++) {
                    fw.write(updated.get(i) + "\n");
                }
                fw.close();
            }

        } catch (Exception e) {
            System.out.println("Error updating file");
        }

        return found;
    }

    public int getApplicationCount() {
        return getAllApplications().size();
    }

    private Application parseApplication(String line) {

        try {
            String[] p = line.split(",");

            if (p.length < 8) return null;

            Application a = new Application(
                    p[0], p[1], p[2],
                    p[3], p[4], p[5], p[7]
            );

            a.setStatus(p[6]);

            return a;

        } catch (Exception e) {
            return null;
        }
    }
}