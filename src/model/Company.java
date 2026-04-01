package model;

// Company class extends User - Inheritance
public class Company extends User {

    private String companyName;
    private String industry;
    private String contactPerson;

    // Constructor
    public Company(String userId, String name, String email, String password,
                   String companyName, String industry, String contactPerson) {
        super(userId, name, email, password, "COMPANY"); // Call parent constructor
        this.companyName = companyName;
        this.industry = industry;
        this.contactPerson = contactPerson;
    }

    // Getters and Setters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    // Override parent method - Dynamic Method Dispatch
    public String getDisplayInfo() {
        return "Company ID: " + getUserId() + " | Company: " + companyName
                + " | Industry: " + industry + " | Contact: " + contactPerson
                + " | Email: " + getEmail();
    }

    // Convert to file-safe string
    public String toFileString() {
        return getUserId() + "," + getName() + "," + getEmail() + ","
                + getPassword() + ",COMPANY," + companyName + "," + industry + "," + contactPerson;
    }
}
