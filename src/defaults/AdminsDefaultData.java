package defaults;

import model.Admin;

import java.util.ArrayList;

/**
 * Default lines for {@code data/admins.txt}.
 * Matches {@link Admin#toFileString()}: userId, name, email, password, role, adminCode.
 */
public final class AdminsDefaultData {

    private AdminsDefaultData() {
    }

    public static ArrayList<String> getDefaultLines() {
        ArrayList<String> lines = new ArrayList<String>();

        Admin admin = new Admin(
                "ADMIN001",
                "Campus Admin",
                "admin@campus.edu",
                "admin123",
                "ADMIN2024");

        lines.add(admin.toFileString());
        return lines;
    }
}
