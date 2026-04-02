package model;

public class Company extends User {

    private String companyName;
    private String industry;
    private String contactPerson;

    // constructor
    public Company(String uId, String nm, String em, String pass,
                   String compName, String ind, String contact) {

        super(uId, nm, em, pass, "COMPANY");

        companyName = compName;
        industry = ind;
        contactPerson = contact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String compName) {
        companyName = compName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String ind) {
        industry = ind;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contact) {
        contactPerson = contact;
    }

    // display company info
    public String getDisplayInfo() {
        return "Company ID: " + getUserId() +
                " | Company: " + companyName +
                " | Industry: " + industry +
                " | Contact: " + contactPerson +
                " | Email: " + getEmail();
    }

    // convert to file string
    public String toFileString() {
        return getUserId() + "," + getName() + "," + getEmail() + ","
                + getPassword() + ",COMPANY," + companyName + ","
                + industry + "," + contactPerson;
    }
}