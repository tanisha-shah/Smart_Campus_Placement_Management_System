package service;

import model.Application;
import model.Drive;
import model.Student;
import utils.FileHelper;
import utils.UIHelper;
import exceptions.AlreadyAppliedException;

import java.util.ArrayList;

// ApplicationService handles applying for drives and updating status
public class ApplicationService {

    // Apply for a drive
    public void applyForDrive(Student student, Drive drive) throws AlreadyAppliedException {
        // Check if already applied
        if (hasAlreadyApplied(student.getUserId(), drive.getDriveId())) {
            throw new AlreadyAppliedException("You have already applied for this drive: "
                    + drive.getCompanyName() + " - " + drive.getJobRole());
        }

        // Create new application
        String appId = UIHelper.generateId("APP");
        String today = UIHelper.getTodayDate();
        Application app = new Application(appId, student.getUserId(), student.getName(),
                drive.getDriveId(), drive.getCompanyName(), drive.getJobRole(), today);

        // Save to file
        FileHelper.writeLineToFile(FileHelper.APPLICATIONS_FILE, app.toFileString());
        UIHelper.printSuccess("Applied successfully to " + drive.getCompanyName()
                + " for the role of " + drive.getJobRole());
    }

    // Check if student already applied to a drive
    public boolean hasAlreadyApplied(String studentId, String driveId) {
        ArrayList<Application> apps = getApplicationsByStudent(studentId);
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getDriveId().equals(driveId)) {
                return true;
            }
        }
        return false;
    }

    // Get all applications by a student
    public ArrayList<Application> getApplicationsByStudent(String studentId) {
        ArrayList<Application> result = new ArrayList<Application>();
        ArrayList<Application> allApps = getAllApplications();
        for (int i = 0; i < allApps.size(); i++) {
            if (allApps.get(i).getStudentId().equals(studentId)) {
                result.add(allApps.get(i));
            }
        }
        return result;
    }

    // Get all applications for a specific drive
    public ArrayList<Application> getApplicationsByDrive(String driveId) {
        ArrayList<Application> result = new ArrayList<Application>();
        ArrayList<Application> allApps = getAllApplications();
        for (int i = 0; i < allApps.size(); i++) {
            if (allApps.get(i).getDriveId().equals(driveId)) {
                result.add(allApps.get(i));
            }
        }
        return result;
    }

    // Get ALL applications
    public ArrayList<Application> getAllApplications() {
        ArrayList<Application> apps = new ArrayList<Application>();
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.APPLICATIONS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Application app = parseApplicationFromLine(lines.get(i));
            if (app != null) {
                apps.add(app);
            }
        }
        return apps;
    }

    // Update application status (Shortlist or Reject)
    public boolean updateApplicationStatus(String applicationId, String newStatus) {
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.APPLICATIONS_FILE);
        ArrayList<String> updatedLines = new ArrayList<String>();
        boolean found = false;

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            // Format: appId,studentId,studentName,driveId,companyName,jobRole,status,date
            if (parts.length >= 8 && parts[0].equals(applicationId)) {
                // Replace status (index 6)
                parts[6] = newStatus;
                // Rebuild the line
                String updatedLine = "";
                for (int j = 0; j < parts.length; j++) {
                    updatedLine += parts[j];
                    if (j < parts.length - 1) updatedLine += ",";
                }
                updatedLines.add(updatedLine);
                found = true;
            } else {
                updatedLines.add(lines.get(i));
            }
        }

        if (found) {
            FileHelper.writeAllLines(FileHelper.APPLICATIONS_FILE, updatedLines);
        }
        return found;
    }

    // Get total application count
    public int getApplicationCount() {
        return getAllApplications().size();
    }

    // Parse Application from CSV line
    private Application parseApplicationFromLine(String line) {
        try {
            String[] parts = line.split(",");
            // Format: appId,studentId,studentName,driveId,companyName,jobRole,status,date
            if (parts.length < 8) return null;

            Application app = new Application(parts[0], parts[1], parts[2],
                    parts[3], parts[4], parts[5], parts[7]);
            app.setStatus(parts[6]);
            return app;
        } catch (Exception e) {
            return null;
        }
    }
}
