package model;

import service.RentalSystem;

import java.util.*;

public class RentalAgreement {

    private String rentalId;
    private Property property;
    private Tenant tenant;
    private Date startDate;
    private Date endDate;
    private double deposit;
    private boolean isActive;
    private List<Double> paymentHistory;

    
    public RentalAgreement(String rentalId, Property property, Tenant tenant, double deposit,
                           int durationMonths) {
        this.rentalId = rentalId;
        this.property = property;
        this.tenant = tenant;
        this.deposit = deposit;
        this.startDate = new Date();
        this.isActive = true;
        this.paymentHistory = new ArrayList<>();


 
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, durationMonths);
        this.endDate = cal.getTime();


        tenant.addRentalHistory(this);
    }

    public String getRentalId() {
        return rentalId;
    }

    public Property getProperty() {
        return property;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getDeposit() {
        return deposit;
    }

    public boolean isActive() {
        return isActive;
    }

    public void recordPayment(double amount){
        if(!isActive){
            System.out.println("Cannot record payment for terminated rental!");
            return;
        }
        if (amount >= property.getRent()){
            paymentHistory.add(amount);
            System.out.printf("Payment of %.2f ETB recordedmfor %s%n", amount, rentalId);
            if (amount > property.getRent()){
                System.out.printf(" Overpayment: %.2f ETB (credit applied)%n", amount - property.getRent());
            }

        }else {
            System.out.println("Payment amount is less than monthly rent!");
        }
    }
    

    public void terminate(){
        this.isActive = false; 
    }
   

    public void displayInfo(){
        System.out.println("=== RENTAL AGREEMENT ===");
        System.out.println("Rental ID: " + rentalId);
        System.out.println("Property: " + property.getAddress());
        System.out.println("Tenant: " + tenant.getName());
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Deposit: " + deposit + " ETB");
        System.out.println("Status: " + (isActive ? "ACTIVE" : "TERMINATED"));
        System.out.println("Payments Made: " + paymentHistory.size());
    }
    public String toFileString(){
        return rentalId + "|" + property.getPropertyId() + "|" + deposit + "|" + isActive;
    }
}
