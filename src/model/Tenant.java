package model;

import java.util.*;

public class Tenant {
    private String tenantId;
    private String name;
    private String phone;
    private String email;
    private List<RentalAgreement> rentalHistory;

    
    
    public Tenant(String tenantId, String name, String phone, String email){
        this.tenantId = tenantId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.rentalHistory = new ArrayList<>();
    }
   
    

    public String getTenantId() {
        return tenantId;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
    
    

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public void addRentalHistory(RentalAgreement rental){
        rentalHistory.add(rental);
    }
    
    
    public void displayInfo(){
        System.out.println("=== TENANT INFORMATION ===");
        System.out.println("ID: " + tenantId);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Past Rentals: " + rentalHistory.size());
    }
   
    
    public String toFileString(){
        return tenantId + "|" + name + "|" + phone + "|" + email;
    }

}
