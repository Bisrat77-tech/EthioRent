package service;

import model.*;

import java.io.*;
import java.util.*;

public class RentalSystem {

    private Map<String, Property> properties;
    private Map<String, Tenant> tenants;
    private Map<String, RentalAgreement> rentals;

    private static final String DATA_DIR = "data/";
    private static final String PROPERTIES_FILE = DATA_DIR + "properties.txt";
    private static final String TENANTS_FILE = DATA_DIR + "tenants.txt";

    public RentalSystem() {

        properties = new HashMap<>();
        tenants = new HashMap<>();
        rentals = new HashMap<>();

        createDataDirectory();
        loadSampleData();
    }

    private void createDataDirectory() {

        File dir = new File(DATA_DIR);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void addProperty(Property property) {

        if (properties.containsKey(property.getPropertyId())) {

            System.out.println("Property ID already exists.");
            return;
        }

        properties.put(property.getPropertyId(), property);

        System.out.println("Property added successfully.");
    }

    public Property searchProperty(String propertyId) {

        return properties.get(propertyId);
    }

    public void removeProperty(String propertyId) {

        if (properties.remove(propertyId) != null) {

            System.out.println("Property removed successfully.");
        } else {

            System.out.println("Property not found.");
        }
    }

    public void displayAllProperties() {

        if (properties.isEmpty()) {

            System.out.println("No properties found.");
            return;
        }

        System.out.println("\n===== PROPERTIES =====");

        for (Property property : properties.values()) {

            property.displayInfo();
            System.out.println();
        }
    }

    public void addTenant(Tenant tenant) {

        if (tenants.containsKey(tenant.getTenantId())) {

            System.out.println("Tenant ID already exists.");
            return;
        }

        tenants.put(tenant.getTenantId(), tenant);

        System.out.println("Tenant added successfully.");
    }

    public Tenant searchTenant(String tenantId) {

        return tenants.get(tenantId);
    }

    public void removeTenant(String tenantId) {

        if (tenants.remove(tenantId) != null) {

            System.out.println("Tenant removed successfully.");
        } else {

            System.out.println("Tenant not found.");
        }
    }

    public void displayAllTenants() {

        if (tenants.isEmpty()) {

            System.out.println("No tenants found.");
            return;
        }

        System.out.println("\n===== TENANTS =====");

        for (Tenant tenant : tenants.values()) {

            tenant.displayInfo();
            System.out.println();
        }
    }

    public void createRentalAgreement(
            String rentalId,
            String propertyId,
            String tenantId,
            double deposit) {

        Property property = properties.get(propertyId);

        Tenant tenant = tenants.get(tenantId);

        if (property == null) {

            System.out.println("Property not found.");
            return;
        }

        if (tenant == null) {

            System.out.println("Tenant not found.");
            return;
        }

        if (property.getStatus() != PropertyStatus.AVAILABLE) {

            System.out.println("Property is not available.");
            return;
        }

        RentalAgreement agreement =
                new RentalAgreement(
                        rentalId,
                        property,
                        tenant,
                        new Date(),
                        new Date(System.currentTimeMillis()
                                + (365L * 24 * 60 * 60 * 1000)),
                        deposit
                );

        rentals.put(rentalId, agreement);

        System.out.println("Rental agreement created successfully.");
    }

    public void recordPayment(
            String rentalId,
            double amount) {

        RentalAgreement rental = rentals.get(rentalId);

        if (rental == null) {

            System.out.println("Rental agreement not found.");
            return;
        }

        rental.recordPayment(amount);

        System.out.println(
                "Payment of " + amount + " ETB recorded.");
    }

    public void terminateRental(String rentalId) {

        RentalAgreement rental = rentals.get(rentalId);

        if (rental == null) {

            System.out.println("Rental agreement not found.");
            return;
        }

        rental.terminateAgreement();

        System.out.println("Rental terminated successfully.");
    }

    public void displayAllRentals() {

        if (rentals.isEmpty()) {

            System.out.println("No rental agreements found.");
            return;
        }

        System.out.println("\n===== RENTALS =====");

        for (RentalAgreement rental : rentals.values()) {

            rental.displayInfo();
            System.out.println();
        }
    }

    public void showSystemSummary() {

        System.out.println("\n===== SYSTEM SUMMARY =====");

        System.out.println(
                "Total Properties: "
                        + properties.size());

        System.out.println(
                "Total Tenants: "
                        + tenants.size());

        System.out.println(
                "Total Rentals: "
                        + rentals.size());

        double revenue = 0;

        for (RentalAgreement rental : rentals.values()) {

            revenue += rental.getTotalPayments();
        }

        System.out.println(
                "Total Revenue: "
                        + revenue + " ETB");
    }

    public void saveProperties() {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(PROPERTIES_FILE))) {

            for (Property property : properties.values()) {

                writer.println(property.toFileString());
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving properties.");
        }
    }

    public void saveTenants() {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(TENANTS_FILE))) {

            for (Tenant tenant : tenants.values()) {

                writer.println(tenant.toFileString());
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving tenants.");
        }
    }

    private void loadSampleData() {

        Property house =
                new House(
                        "H001",
                        "Bole",
                        15000,
                        3,
                        2,
                        250,
                        true);

        Property apartment =
                new Apartment(
                        "A001",
                        "CMC",
                        10000,
                        2,
                        1,
                        4,
                        true);

        Property studio =
                new Studio(
                        "S001",
                        "Megenagna",
                        8000,
                        1,
                        1,
                        true);

        Tenant tenant1 =
                new Tenant(
                        "T001",
                        "Aya Anwar",
                        "0911111111",
                        "aya@gmail.com");

        Tenant tenant2 =
                new Tenant(
                        "T002",
                        "Bisrat Zenebe",
                        "0922222222",
                        "bisrat@gmail.com");

        properties.put(
                house.getPropertyId(),
                house);

        properties.put(
                apartment.getPropertyId(),
                apartment);

        properties.put(
                studio.getPropertyId(),
                studio);

        tenants.put(
                tenant1.getTenantId(),
                tenant1);

        tenants.put(
                tenant2.getTenantId(),
                tenant2);
    }
}