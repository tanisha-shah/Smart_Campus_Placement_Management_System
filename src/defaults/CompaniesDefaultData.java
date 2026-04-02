package defaults;

import model.Company;

import java.util.ArrayList;

/**
 * Default lines for {@code data/companies.txt}.
 * Matches {@link Company#toFileString()}: userId, name, email, password, COMPANY,
 * companyName, industry, contactPerson.
 */
public final class CompaniesDefaultData {

    private CompaniesDefaultData() {
    }

    public static ArrayList<String> getDefaultLines() {
        ArrayList<String> lines = new ArrayList<String>();

        Company techCorp = new Company(
                "COMP001",
                "TechCorp Solutions Pvt Ltd HR",
                "techcorp@campus.edu",
                "company123",
                "TechCorp Solutions Pvt Ltd",
                "Information Technology",
                "Ms. Priya Sharma");
        lines.add(techCorp.toFileString());

        Company tata = new Company(
                "COMP002",
                "Tata Motors Recruiter",
                "tata@campus.edu",
                "company123",
                "Tata Motors Limited",
                "Automotive",
                "Mr. Rajesh Kumar");
        lines.add(tata.toFileString());

        Company infosys = new Company(
                "COMP003",
                "Infosys HR Partner",
                "infosys@campus.edu",
                "company123",
                "Infosys Technologies",
                "IT Services",
                "Ms. Ananya Reddy");
        lines.add(infosys.toFileString());

        return lines;
    }
}
