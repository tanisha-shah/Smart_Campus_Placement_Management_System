package service;

import model.Application;
import model.Drive;
import model.Student;
import utils.FileHelper;
import exceptions.AlreadyAppliedException;

import java.util.ArrayList;

public class ApplicationService {

    // Apply for a drive
    public void applyForDrive(Student student, Drive drive) throws AlreadyAppliedException {

        if (hasAlreadyApplied(student.getUserId(), drive.getDriveId())) {
            throw new AlreadyAppliedException("Already applied");
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

        FileHelper.writeLineToFile(FileHelper.APPLICATIONS_FILE, app.toFileString());

        System.out.println("Applied successfully");
    }

    // check already applied
    public boolean hasAlreadyApplied(String studentId, String driveId) {

        ArrayList<Application> list = getApplicationsByStudent(studentId);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDriveId().equals(driveId)) {
                return true;
            }
        }

        return false;
    }

    // by student
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

    // by drive
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

    // all applications
    public ArrayList<Application> getAllApplications() {

        ArrayList<Application> list = new ArrayList<Application>();
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.APPLICATIONS_FILE);

        for (int i = 0; i < lines.size(); i++) {
            Application app = parseApplicationFromLine(lines.get(i));
            if (app != null) {
                list.add(app);
            }
        }

        return list;
    }

    // update status
    public boolean updateApplicationStatus(String applicationId, String newStatus) {

        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.APPLICATIONS_FILE);
        ArrayList<String> updated = new ArrayList<String>();

        boolean found = false;

        for (int i = 0; i < lines.size(); i++) {

            String[] parts = lines.get(i).split(",");

            if (parts.length >= 8 && parts[0].equals(applicationId)) {

                parts[6] = newStatus;

                String newLine = "";
                for (int j = 0; j < parts.length; j++) {
                    newLine += parts[j];
                    if (j < parts.length - 1) {
                        newLine += ",";
                    }
                }

                updated.add(newLine);
                found = true;

            } else {
                updated.add(lines.get(i));
            }
        }

        if (found) {
            FileHelper.writeAllLines(FileHelper.APPLICATIONS_FILE, updated);
        }

        return found;
    }

    // count
    public int getApplicationCount() {
        return getAllApplications().size();
    }

    // parse
    private Application parseApplicationFromLine(String line) {

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