package service;

import model.Drive;
import model.Student;
import utils.FileHelper;
import utils.UIHelper;
import exceptions.DriveNotFoundException;

import java.util.ArrayList;

// DriveService handles all drive-related operations
public class DriveService {

    // Post a new drive (company posts it)
    public void postDrive(Drive drive) {
        FileHelper.writeLineToFile(FileHelper.DRIVES_FILE, drive.toFileString());
        UIHelper.printSuccess("Drive posted successfully! Drive ID: " + drive.getDriveId());
    }

    // Get ALL drives from file
    public ArrayList<Drive> getAllDrives() {
        ArrayList<Drive> drives = new ArrayList<Drive>();
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.DRIVES_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Drive drive = parseDriveFromLine(lines.get(i));
            if (drive != null) {
                drives.add(drive);
            }
        }
        return drives;
    }

    // Get only ACTIVE drives
    public ArrayList<Drive> getActiveDrives() {
        ArrayList<Drive> allDrives = getAllDrives();
        ArrayList<Drive> activeDrives = new ArrayList<Drive>();
        for (int i = 0; i < allDrives.size(); i++) {
            if (allDrives.get(i).getStatus().equals("ACTIVE")) {
                activeDrives.add(allDrives.get(i));
            }
        }
        return activeDrives;
    }

    // Get drives posted by a specific company
    public ArrayList<Drive> getDrivesByCompany(String companyId) {
        ArrayList<Drive> allDrives = getAllDrives();
        ArrayList<Drive> companyDrives = new ArrayList<Drive>();
        for (int i = 0; i < allDrives.size(); i++) {
            if (allDrives.get(i).getCompanyId().equals(companyId)) {
                companyDrives.add(allDrives.get(i));
            }
        }
        return companyDrives;
    }

    // Get drives that a student is ELIGIBLE for based on their profile
    public ArrayList<Drive> getEligibleDrives(Student student) {
        ArrayList<Drive> activeDrives = getActiveDrives();
        ArrayList<Drive> eligibleDrives = new ArrayList<Drive>();

        for (int i = 0; i < activeDrives.size(); i++) {
            Drive drive = activeDrives.get(i);

            // Check CGPA requirement
            if (student.getCgpa() < drive.getMinCgpa()) {
                continue; // Not eligible - skip this drive
            }

            // Check backlogs requirement
            if (student.getBacklogs() > drive.getMaxBacklogs()) {
                continue; // Not eligible - skip this drive
            }

            // Check branch eligibility (if drive is not open to ALL)
            String branchStr = drive.getBranchesAsString();
            if (!branchStr.equals("ALL")) {
                ArrayList<String> branches = drive.getEligibleBranches();
                boolean branchEligible = false;
                for (int j = 0; j < branches.size(); j++) {
                    if (branches.get(j).equalsIgnoreCase(student.getBranch())) {
                        branchEligible = true;
                        break;
                    }
                }
                if (!branchEligible) {
                    continue; // Branch not eligible
                }
            }

            // Student is eligible for this drive
            eligibleDrives.add(drive);
        }
        return eligibleDrives;
    }

    // Find drive by ID
    public Drive getDriveById(String driveId) throws DriveNotFoundException {
        ArrayList<Drive> drives = getAllDrives();
        for (int i = 0; i < drives.size(); i++) {
            if (drives.get(i).getDriveId().equals(driveId)) {
                return drives.get(i);
            }
        }
        throw new DriveNotFoundException("Drive not found with ID: " + driveId);
    }

    // Analyze skill gap - return list of missing skills for a drive
    public ArrayList<String> analyzeSkillGap(Student student, Drive drive) {
        ArrayList<String> missingSkills = new ArrayList<String>();
        ArrayList<String> requiredSkills = drive.getRequiredSkills();
        ArrayList<String> studentSkills = student.getSkills();

        for (int i = 0; i < requiredSkills.size(); i++) {
            String required = requiredSkills.get(i).toLowerCase();
            boolean hasSkill = false;
            for (int j = 0; j < studentSkills.size(); j++) {
                if (studentSkills.get(j).toLowerCase().equals(required)) {
                    hasSkill = true;
                    break;
                }
            }
            if (!hasSkill) {
                missingSkills.add(requiredSkills.get(i));
            }
        }
        return missingSkills;
    }

    // Get total drive count
    public int getDriveCount() {
        return getAllDrives().size();
    }

    // Parse a Drive object from a CSV line
    private Drive parseDriveFromLine(String line) {
        try {
            String[] parts = line.split(",");
            // Format: driveId,companyId,companyName,jobRole,ctc,minCgpa,maxBacklogs,
            //         driveDate,location,jobType,status,branches,skills
            if (parts.length < 13) return null;

            String driveId = parts[0];
            String companyId = parts[1];
            String companyName = parts[2];
            String jobRole = parts[3];
            double ctc = Double.parseDouble(parts[4]);
            double minCgpa = Double.parseDouble(parts[5]);
            int maxBacklogs = Integer.parseInt(parts[6]);
            String driveDate = parts[7];
            String location = parts[8];
            String jobType = parts[9];
            String status = parts[10];
            String branchStr = parts[11];
            String skillStr = parts[12];

            Drive drive = new Drive(driveId, companyId, companyName, jobRole,
                    ctc, minCgpa, maxBacklogs, driveDate, location, jobType);
            drive.setStatus(status);

            // Parse branches
            if (!branchStr.equals("ALL")) {
                String[] branchArr = branchStr.split(";");
                for (int i = 0; i < branchArr.length; i++) {
                    drive.addEligibleBranch(branchArr[i].trim());
                }
            }

            // Parse skills
            if (!skillStr.equals("NONE")) {
                String[] skillArr = skillStr.split(";");
                for (int i = 0; i < skillArr.length; i++) {
                    drive.addRequiredSkill(skillArr[i].trim());
                }
            }

            return drive;

        } catch (Exception e) {
            return null; // Skip malformed lines
        }
    }
}
