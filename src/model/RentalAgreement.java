package model;

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

    public RentalAgreement(String rentalId,
                           Property property,
                           Tenant tenant,
                           Date startDate,
                           Date endDate,
                           double deposit) {

        this.rentalId = rentalId;
        this.property = property;
        this.tenant = tenant;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
        this.isActive = true;
        this.paymentHistory = new ArrayList<>();

        property.setStatus(PropertyStatus.RENTED);
    }

    public void recordPayment(double amount) {
        paymentHistory.add(amount);
    }

    public double getTotalPayments() {
        double total = 0;
        for (double p : paymentHistory) {
            total += p;
        }
        return total;
    }

    public void terminateAgreement() {
        isActive = false;
        property.setStatus(PropertyStatus.AVAILABLE);
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

    public void displayInfo() {

        System.out.println("---- RENTAL AGREEMENT ----");
        System.out.println("Rental ID: " + rentalId);
        System.out.println("Property: " + property.getPropertyId());
        System.out.println("Tenant: " + tenant.getFullName());
        System.out.println("Deposit: " + deposit);
        System.out.println("Start: " + startDate);
        System.out.println("End: " + endDate);
        System.out.println("Active: " + isActive);
        System.out.println("Total Payments: " + getTotalPayments());
    }
}