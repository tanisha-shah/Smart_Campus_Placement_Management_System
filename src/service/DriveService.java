package service;

import exceptions.DriveNotFoundException;
import java.util.ArrayList;
import model.Drive;
import model.Student;
import utils.FileHelper;

public class DriveService {

    // POST DRIVE
    public void postDrive(Drive drive) {
        FileHelper.writeLineToFile(FileHelper.DRIVES_FILE, drive.toFileString());
        System.out.println("Drive posted successfully! ID: " + drive.getDriveId());
    }

    // GET ALL DRIVES
    public ArrayList<Drive> getAllDrives() {

        ArrayList<Drive> drives = new ArrayList<>();
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.DRIVES_FILE);

        for (int i = 0; i < lines.size(); i++) {
            Drive d = parseDrive(lines.get(i));
            if (d != null) {
                drives.add(d);
            }
        }
        return drives;
    }

    // GET ACTIVE DRIVES
    public ArrayList<Drive> getActiveDrives() {

        ArrayList<Drive> all = getAllDrives();
        ArrayList<Drive> active = new ArrayList<>();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getStatus().equals("ACTIVE")) {
                active.add(all.get(i));
            }
        }
        return active;
    }

    // GET DRIVES BY COMPANY
    public ArrayList<Drive> getDrivesByCompany(String companyId) {

        ArrayList<Drive> all = getAllDrives();
        ArrayList<Drive> list = new ArrayList<>();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getCompanyId().equals(companyId)) {
                list.add(all.get(i));
            }
        }
        return list;
    }

    // ELIGIBLE DRIVES
    public ArrayList<Drive> getEligibleDrives(Student s) {

        ArrayList<Drive> active = getActiveDrives();
        ArrayList<Drive> result = new ArrayList<>();

        for (int i = 0; i < active.size(); i++) {

            Drive d = active.get(i);

            if (s.getCgpa() < d.getMinCgpa()) continue;
            if (s.getBacklogs() > d.getMaxBacklogs()) continue;

            String branchStr = d.getBranchesAsString();

            if (!branchStr.equals("ALL")) {

                ArrayList<String> branches = d.getEligibleBranches();
                boolean ok = false;

                for (int j = 0; j < branches.size(); j++) {
                    if (branches.get(j).equalsIgnoreCase(s.getBranch())) {
                        ok = true;
                        break;
                    }
                }

                if (!ok) continue;
            }

            result.add(d);
        }

        return result;
    }

    // GET DRIVE BY ID
    public Drive getDriveById(String id) throws DriveNotFoundException {

        ArrayList<Drive> list = getAllDrives();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDriveId().equals(id)) {
                return list.get(i);
            }
        }

        throw new DriveNotFoundException("Drive not found");
    }

    // SKILL GAP
    public ArrayList<String> analyzeSkillGap(Student s, Drive d) {

        ArrayList<String> missing = new ArrayList<>();

        ArrayList<String> req = d.getRequiredSkills();
        ArrayList<String> stu = s.getSkills();

        for (int i = 0; i < req.size(); i++) {

            String r = req.get(i).toLowerCase();
            boolean found = false;

            for (int j = 0; j < stu.size(); j++) {
                if (stu.get(j).toLowerCase().equals(r)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                missing.add(req.get(i));
            }
        }

        return missing;
    }

    // COUNT
    public int getDriveCount() {
        return getAllDrives().size();
    }

    // PARSE CSV
    private Drive parseDrive(String line) {

        try {
            String[] p = line.split(",");

            if (p.length < 13) return null;

            Drive d = new Drive(
                    p[0], p[1], p[2], p[3],
                    Double.parseDouble(p[4]),
                    Double.parseDouble(p[5]),
                    Integer.parseInt(p[6]),
                    p[7], p[8], p[9]
            );

            d.setStatus(p[10]);

            // branches
            if (!p[11].equals("ALL")) {
                String[] b = p[11].split(";");
                for (int i = 0; i < b.length; i++) {
                    d.addEligibleBranch(b[i]);
                }
            }

            // skills
            if (!p[12].equals("NONE")) {
                String[] s = p[12].split(";");
                for (int i = 0; i < s.length; i++) {
                    d.addRequiredSkill(s[i]);
                }
            }

            return d;

        } catch (Exception e) {
            return null;
        }
    }
}