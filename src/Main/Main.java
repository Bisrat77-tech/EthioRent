package Main;
import model.*;
import service.RentalSystem;
import utils.InputHelper;
import java.util.*;

public class Main {
    private static Scanner scanner = InputHelper.getScanner();
    private static RentalSystem rentalSystem = new RentalSystem();

    void main(){
        rentalSystem.loadDataFromFile();
        while (true){
            displayMainMenu();
            int choice = InputHelper.getIntInput("Enter your Choice: ");

            switch (choice){
                case 1:
                    manageProperties();
                    break;
                case 2:
                    manageTenants();
                    break;
                case 3:
                    manageRentals();
                    break;
                case 4:
                    displayReports();
                    break;
                case 5:
                    System.out.println("Saving data and exiting EthioRent...");
                    rentalSystem.saveDataToFile(); // save all data before exit
                    System.out.println("Thank you for using EthioRent!");
                    scanner.close(); // close scanner to prevent resource leak
                    System.exit(0); // Exit program with success code
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    // Display Main Menu
    private static void displayMainMenu(){
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ETHIORENT - HOUSE RENTAL MANAGEMENT SYSTEM ");
        System.out.println("=".repeat(60));
        System.out.println("1.  Manage Properties");
        System.out.println("2.  Manage Tenants");
        System.out.println("3.  Manage Rentals");
        System.out.println("4.  View Reports");
        System.out.println("5.  Exit");
        System.out.println("=".repeat(60));
    }
    // Property Management Menu
    private static void manageProperties(){
        while (true){
            System.out.println("\n --- PROPERTY MANAGEMENT ---");
            System.out.println("1. Add New Property");
            System.out.println("2. View All Properties");
            System.out.println("3. Search Property");
            System.out.println("4. Update Property Status");
            System.out.println("5. Remove Property");
            System.out.println("6. Back to Main Menu");

            int choice = InputHelper.getIntInput("Choice: ");

            switch (choice){
                case 1: addNewProperty(); break;
                case 2: rentalSystem.displayAllProperties(); break;
                case 3: searchProperty(); break;
                case 4: updatePropertyStatus(); break;
                case 6: return; // Exit back to main menu
                default: System.out.println("Invalid Choice!");

            }

        }
    }
    // ADD NEW PROPERTY - DEMNSTRATES POLYMORPHISM
    private static void addNewProperty(){
        System.out.println("\n --- ADD NEW PROPERTY ---");
        System.out.println("Property Types:");
        System.out.println("1. Apartment");
        System.out.println("2. House");
        System.out.println("3. Studio");

        int type = InputHelper.getIntInput("Select property type: ");

        // Common fields for all property types
        String id = InputHelper.getStringInput("Enter Property ID: ");
        String address = InputHelper.getStringInput("Enter Address: ");
        double rent = InputHelper.getDoubleInput("Enter Monthly Rent (ETB): ");
        int bedrooms = InputHelper.getIntInput("Enter Bedrooms: ");
        int bathrooms = InputHelper.getIntInput("Enter Bathrooms: ");

        Property property = null; // Polymorphic refernce

        // Create specific property type based on user choice
        switch (type){
            case 1: // Apartment
                int floor = InputHelper.getIntInput("Enter Floor Number: ");
                boolean hasElevator = Boolean.parseBoolean(InputHelper.getStringInput("Has Elevator? (true/ false):" ));
                property = new Apartment(id, address, rent, bedrooms, bathrooms, floor, hasElevator);
                break;
            case 2: // House
                double lotsize = InputHelper.getDoubleInput("Enter Lot Size (sqm): ");
                boolean hasGarden = Boolean.parseBoolean(InputHelper.getStringInput("Has Garden? (true/false): "));
                property = new House(id, address, rent, bedrooms, bathrooms, lotsize, hasGarden);
                break;
            case 3: // Studio
                boolean isFurnished = Boolean.parseBoolean(InputHelper.getStringInput("Is Furnished? (true/false): "));
                property = new Studio(id, address, rent, bedrooms, bathrooms, isFurnished);
                break;
            default:
                System.out.println("Invalid property type!");
                return;
        }
        rentalSystem.addProperty(property);
        System.out.println("Property added successfully!");
    }
    // Search property
    private static void searchProperty(){
        String id = InputHelper.getStringInput("\n Enter Property ID to search: ");
        Property p = rentalSystem.findPropertyById(id);
        if(p != null){
            p.displayInfo(); // Polymorphism - calls correct display method
        }else {
            System.out.println("Property not found");
        }

    }
    // Update Property Status
    private static void updatePropertyStatus(){
        String id = InputHelper.getStringInput("\n Enter Property ID: ");
        Property p = rentalSystem.findPropertyById(id);
        if (p != null){
            System.out.println("Current Status: " + p.getStatus());
            String status = InputHelper.getStringInput("New Status (AVAILABLE/RENTED/MAINTENANCE): ").toUpperCase();
            try {
                p.setStatus(PropertyStatus.valueOf(status)); // Convert String to enum
                System.out.println("Status updated!");
            }catch (IllegalArgumentException e){
                System.out.println("Invalid status!");
            }
        }else {
            System.out.println("Property not found!");
        }
    }
    private static void manageTenants(){
        while (true){
            System.out.println("\n--- TENANT MANAGEMENT ---");
            System.out.println("1. Register New Tenant");
            System.out.println("2. View All Tenants");
            System.out.println("3. Search Tenant");
            System.out.println("4. Update Tenant Contact");
            System.out.println("5. Remove Tenant");
            System.out.println("6. Back to Main Menu");

            int choice = InputHelper.getIntInput("Choice: ");

            switch (choice){
                case 1: registerNewTenant(); break;
                case 2: rentalSystem.displayAllTenants(); break;
                case 3: searchTenant(); break;
                case 4: updateTenantContact(); break;
                case 5: removeTenant(); break;
                case 6: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }
    private static void registerNewTenant(){
        System.out.println("\n--- REGISTER NEW TENANT ---");
        String id = InputHelper.getStringInput("Enter Tenant ID: ");
        String name = InputHelper.getStringInput("Enter Full Name: ");
        String phone = InputHelper.getStringInput("Enter Phone Number: ");
        String email = InputHelper.getStringInput("Enter Email: ");

        Tenant tenant = new Tenant(id, name, phone, email);
        rentalSystem.addTenant(tenant);
        System.out.println("Tenant registered successfully!");
    }

    private static void searchTenant(){
        String id = InputHelper.getStringInput("\n Enter Tenant ID to search: ");
        Tenant t = rentalSystem.findTenantById(id);
        if (t != null) {
            t.displayInfo();
        } else {
            System.out.println(" Tenant not found!");
        }
    }
    private static void updateTenantContact(){
        String id = InputHelper.getStringInput("\n Enter Tenant ID: ");
        Tenant t = rentalSystem.findTenantById(id);
        if (t != null) {
            String phone = InputHelper.getStringInput("New Phone Number: ");
            String email = InputHelper.getStringInput("New Email: ");
            t.setPhone(phone);
            t.setEmail(email);
            System.out.println(" Contact updated!");
        } else {
            System.out.println(" Tenant not found!");
        }
    }
    private static void removeTenant(){
        String id = InputHelper.getStringInput("\n Enter Tenant ID to remove: ");
        rentalSystem.removeTenant(id);
    }

    // RENTAL MANAGEMENT MENU
    private static void manageRentals(){
        while (true){
            System.out.println("\n--- RENTAL MANAGEMENT ---");
            System.out.println("1. Create New Rental Agreement");
            System.out.println("2. View All Active Rentals");
            System.out.println("3. Record Rent Payment");
            System.out.println("4. Terminate Rental");
            System.out.println("5. Back to Main Menu");

            int choice = InputHelper.getIntInput("Choice: ");

            switch (choice) {
                case 1: createRentalAgreement(); break;
                case 2: rentalSystem.displayAllRentals(); break;
                case 3: recordPayment(); break;
                case 4: terminateRental(); break;
                case 5: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }
    private static void createRentalAgreement(){
        System.out.println("\n --- CREATE RENTAL AGREEMENT ---");

        String propertyId = InputHelper.getStringInput("Enter Property ID: ");
        String tenantId = InputHelper.getStringInput("Enter Tenant ID: ");

        Property property = rentalSystem.findPropertyById(propertyId);
        Tenant tenant = rentalSystem.findTenantById(tenantId);

        // Validation checks
        if (property == null) {
            System.out.println(" Property not found!");
            return;
        }
        if (tenant == null) {
            System.out.println(" Tenant not found!");
            return;
        }
        if (property.getStatus() != PropertyStatus.AVAILABLE) {
            System.out.println(" Property is not available for rent!");
            return;
        }

        String rentalId = InputHelper.getStringInput("Enter Rental ID: ");
        double deposit = InputHelper.getDoubleInput("Enter Deposit Amount (ETB): ");
        int duration = InputHelper.getIntInput("Enter Lease Duration (months): ");
        RentalAgreement rental = new RentalAgreement(rentalId, property, tenant, deposit, duration);
        rentalSystem.addRental(rental);
        property.setStatus(PropertyStatus.RENTED);  // Update property status

        System.out.println("Rental agreement created successfully!");
    }
    private static void recordPayment(){
        String rentalId = InputHelper.getStringInput("\nEnter Rental ID: ");
        RentalAgreement rental = rentalSystem.findRentalById(rentalId);

        if (rental != null) {
            double amount = InputHelper.getDoubleInput("Enter Payment Amount (ETB): ");
            rental.recordPayment(amount);
        } else {
            System.out.println(" Rental not found!");
        }
    }
    private  static void terminateRental(){
        String rentalId = InputHelper.getStringInput("\nEnter Rental ID to terminate: ");
        RentalAgreement rental = rentalSystem.findRentalById(rentalId);

        if (rental != null) {
            rental.terminate();
            Property p = rental.getProperty();
            if (p != null) {
                p.setStatus(PropertyStatus.AVAILABLE);  // Make property available again
            }
            System.out.println(" Rental terminated successfully!");
        } else {
            System.out.println(" Rental not found!");
        }
    }
    // Reports Menu
    private static void displayReports(){
        while (true) {
            System.out.println("\n--- REPORTS & ANALYTICS ---");
            System.out.println("1. System Summary");
            System.out.println("2. Available Properties");
            System.out.println("3. Monthly Revenue Report");
            System.out.println("4. Back to Main Menu");

            int choice = InputHelper.getIntInput("Choice: ");

            switch (choice) {
                case 1: rentalSystem.displaySystemSummary(); break;
                case 2: rentalSystem.displayAvailableProperties(); break;
                case 3: displayRevenueReport(); break;
                case 4: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }
    private static void displayRevenueReport(){
        System.out.println("\n--- MONTHLY REVENUE REPORT ---");
        double total = 0;
        for (RentalAgreement r : rentalSystem.getRentals()) {
            if (r.isActive()) {
                total += r.getProperty().getRent();
                System.out.printf("  %s - %s: %.2f ETB/month%n",
                        r.getRentalId(), r.getProperty().getAddress(), r.getProperty().getRent());
            }
        }
        System.out.println("=".repeat(40));
        System.out.printf(" TOTAL MONTHLY REVENUE: %.2f ETB%n", total);
        System.out.println("=".repeat(40));
    }
    }




