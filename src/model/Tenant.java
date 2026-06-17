package model;

public class Tenant {

    private String tenantId;
    private String fullName;
    private String phoneNumber;
    private String email;

    public Tenant(String tenantId, String fullName,
                  String phoneNumber, String email) {

        this.tenantId = tenantId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void displayInfo() {
        System.out.println("---- TENANT ----");
        System.out.println("ID: " + tenantId);
        System.out.println("Name: " + fullName);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Email: " + email);
    }

    public String toFileString() {
        return tenantId + "|" + fullName + "|" +
                phoneNumber + "|" + email;
    }

    @Override
    public String toString() {
        return toFileString();
    }
}