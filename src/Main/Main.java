package Main;

import model.*;
import service.RentalSystem;
import utils.InputHelper;

public class Main {
    private static RentalSystem rentalSystem = new RentalSystem();

    public static void main(String[] args) {
        // Load existing data
        rentalSystem.loadDataFromFile();

        System.out.println("\n WELCOME TO ETHIORENT - HOUSE RENTAL SYSTEM ");

        while (true) {
            displayMainMenu();
            int choice = InputHelper.getIntInput("Enter your choice: ");

            switch (choice) {
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
                    System.out.println("\n Saving data...");
                    rentalSystem.saveDataToFile();
                    System.out.println(" Thank you for using EthioRent!");
                    System.exit(0);
                    break;
                default:
                    System.out.println(" Invalid choice! Please enter 1-5.");
            }
        }
    }

    // ==================== DISPLAY MAIN MENU ====================

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("      ETHIORENT - MAIN MENU ");
        System.out.println("=".repeat(55));
        System.out.println("1. Manage Properties");
        System.out.println("2.  Manage Tenants");
        System.out.println("3. Manage Rentals");
        System.out.println("4.  View Reports");
        System.out.println("5.  Exit");
        System.out.println("=".repeat(55));
    }

    // ==================== PROPERTY MANAGEMENT ====================

    private static void manageProperties() {
        while (true) {
            System.out.println("\n---  PROPERTY MANAGEMENT ---");
            System.out.println("1. Add New Property");
            System.out.println("2. View All Properties");
            System.out.println("3. Search Property");
            System.out.println("4. Update Property Status");
            System.out.println("5. Remove Property");
            System.out.println("6. Back to Main Menu");

            int choice = InputHelper.getIntInput("Choice: ");

            switch (choice) {
                case 1:
                    addNewProperty();
                    break;
                case 2:
                    rentalSystem.displayAllProperties();
                    break;
                case 3:
                    searchProperty();
                    break;
                case 4:
                    updatePropertyStatus();
                    break;
                case 5:
                    removeProperty();
                    break;
                case 6:
                    return;
                default:
                    System.out.println(" Invalid choice!");
            }
        }
    }

    private static void addNewProperty() {
        System.out.println("\n--- ADD NEW PROPERTY ---");
        System.out.println("Property Types:");
        System.out.println("1.  Apartment");
        System.out.println("2.  House");
        System.out.println("3. Studio");

        int type = InputHelper.getIntInput("Select property type: ");

        String id = InputHelper.getStringInput("Enter Property ID: ");
        String address = InputHelper.getStringInput("Enter Address: ");
        double rent = InputHelper.getDoubleInput("Enter Monthly Rent (ETB): ");
        int bedrooms = InputHelper.getIntInput("Enter Bedrooms: ");
        int bathrooms = InputHelper.getIntInput("Enter Bathrooms: ");

        Property property = null;

        switch (type) {
            case 1: // Apartment
                int floor = InputHelper.getIntInput("Enter Floor Number: ");
                String elevatorInput = InputHelper.getStringInput("Has Elevator? (yes/no): ");
                boolean hasElevator = elevatorInput.equalsIgnoreCase("yes");
                property = new Apartment(id, address, rent, bedrooms, bathrooms, floor, hasElevator);
                break;
            case 2: // House
                double lotSize = InputHelper.getDoubleInput("Enter Lot Size (sqm): ");
                String gardenInput = InputHelper.getStringInput("Has Garden? (yes/no): ");
                boolean hasGarden = gardenInput.equalsIgnoreCase("yes");
                property = new House(id, address, rent, bedrooms, bathrooms, lotSize, hasGarden);
                break;
            case 3: // Studio
                String furnishedInput = InputHelper.getStringInput("Is Furnished? (yes/no): ");
                boolean isFurnished = furnishedInput.equalsIgnoreCase("yes");
                property = new Studio(id, address, rent, bedrooms, bathrooms, isFurnished);
                break;
            default:
                System.out.println(" Invalid property type!");
                return;
        }

        rentalSystem.addProperty(property);
        System.out.println(" Property added successfully!");
    }

    private static void searchProperty() {
        String id = InputHelper.getStringInput("\nEnter Property ID to search: ");
        Property property = rentalSystem.findPropertyById(id);
        if (property != null) {
            property.displayInfo();
        } else {
            System.out.println(" Property not found!");
        }
    }

    private static void updatePropertyStatus() {
        String id = InputHelper.getStringInput("\nEnter Property ID: ");
        Property property = rentalSystem.findPropertyById(id);
        if (property != null) {
            System.out.println("Current Status: " + property.getStatus());
            System.out.println("Available Status: AVAILABLE, RENTED, MAINTENANCE");
            String status = InputHelper.getStringInput("New Status: ").toUpperCase();

            try {
                PropertyStatus newStatus = PropertyStatus.valueOf(status);
                rentalSystem.updatePropertyStatus(id, newStatus);
                System.out.println("Status updated to: " + newStatus);
            } catch (IllegalArgumentException e) {
                System.out.println(" Invalid status! Use: AVAILABLE, RENTED, or MAINTENANCE");
            }
        } else {
            System.out.println(" Property not found!");
        }
    }

    private static void removeProperty() {
        String id = InputHelper.getStringInput("\nEnter Property ID to remove: ");
        rentalSystem.removeProperty(id);
    }

    // ==================== TENANT MANAGEMENT ====================

    private static void manageTenants() {
        while (true) {
            System.out.println("\n---  TENANT MANAGEMENT ---");
            System.out.println("1. Register New Tenant");
            System.out.println("2. View All Tenants");
            System.out.println("3. Search Tenant");
            System.out.println("4. Update Tenant Contact");
            System.out.println("5. Remove Tenant");
            System.out.println("6. Back to Main Menu");

            int choice = InputHelper.getIntInput("Choice: ");

            switch (choice) {
                case 1:
                    registerNewTenant();
                    break;
                case 2:
                    rentalSystem.displayAllTenants();
                    break;
                case 3:
                    searchTenant();
                    break;
                case 4:
                    updateTenantContact();
                    break;
                case 5:
                    removeTenant();
                    break;
                case 6:
                    return;
                default:
                    System.out.println(" Invalid choice!");
            }
        }
    }

    private static void registerNewTenant() {
        System.out.println("\n--- REGISTER NEW TENANT ---");
        String id = InputHelper.getStringInput("Enter Tenant ID: ");
        String name = InputHelper.getStringInput("Enter Full Name: ");
        String phone = InputHelper.getStringInput("Enter Phone Number: ");
        String email = InputHelper.getStringInput("Enter Email: ");

        Tenant tenant = new Tenant(id, name, phone, email);
        rentalSystem.addTenant(tenant);
        System.out.println(" Tenant registered successfully!");
    }

    private static void searchTenant() {
        String id = InputHelper.getStringInput("\nEnter Tenant ID to search: ");
        Tenant tenant = rentalSystem.findTenantById(id);
        if (tenant != null) {
            tenant.displayInfo();
        } else {
            System.out.println(" Tenant not found!");
        }
    }

    private static void updateTenantContact() {
        String id = InputHelper.getStringInput("\nEnter Tenant ID: ");
        Tenant tenant = rentalSystem.findTenantById(id);
        if (tenant != null) {
            String newPhone = InputHelper.getStringInput("New Phone Number: ");
            String newEmail = InputHelper.getStringInput("New Email: ");
            tenant.setPhone(newPhone);
            tenant.setEmail(newEmail);
            System.out.println(" Contact information updated!");
        } else {
            System.out.println(" Tenant not found!");
        }
    }

    private static void removeTenant() {
        String id = InputHelper.getStringInput("\nEnter Tenant ID to remove: ");
        rentalSystem.removeTenant(id);
    }

    // ==================== RENTAL MANAGEMENT ====================

    private static void manageRentals() {
        while (true) {
            System.out.println("\n---  RENTAL MANAGEMENT ---");
            System.out.println("1. Create New Rental Agreement");
            System.out.println("2. View All Active Rentals");
            System.out.println("3. Record Rent Payment");
            System.out.println("4. Terminate Rental");
            System.out.println("5. Back to Main Menu");

            int choice = InputHelper.getIntInput("Choice: ");

            switch (choice) {
                case 1:
                    createRentalAgreement();
                    break;
                case 2:
                    rentalSystem.displayAllRentals();
                    break;
                case 3:
                    recordPayment();
                    break;
                case 4:
                    terminateRental();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void createRentalAgreement() {
        System.out.println("\n--- CREATE RENTAL AGREEMENT ---");

        String propertyId = InputHelper.getStringInput("Enter Property ID: ");
        String tenantId = InputHelper.getStringInput("Enter Tenant ID: ");

        Property property = rentalSystem.findPropertyById(propertyId);
        Tenant tenant = rentalSystem.findTenantById(tenantId);

        if (property == null) {
            System.out.println(" Property not found!");
            return;
        }
        if (tenant == null) {
            System.out.println(" Tenant not found!");
            return;
        }
        if (property.getStatus() != PropertyStatus.AVAILABLE) {
            System.out.println(" Property is not available for rent! Current status: " + property.getStatus());
            return;
        }

        String rentalId = InputHelper.getStringInput("Enter Rental ID: ");
        double deposit = InputHelper.getDoubleInput("Enter Deposit Amount (ETB): ");
        int duration = InputHelper.getIntInput("Enter Lease Duration (months): ");

        RentalAgreement rental = new RentalAgreement(rentalId, property, tenant, deposit, duration);
        rentalSystem.addRental(rental);
        property.setStatus(PropertyStatus.RENTED);

        System.out.println(" Rental agreement created successfully!");
        System.out.println("   Rental ID: " + rentalId);
        System.out.println("   Property: " + property.getAddress());
        System.out.println("   Tenant: " + tenant.getName());
        System.out.println("   Duration: " + duration + " months");
        System.out.println("   Deposit: " + deposit + " ETB");
    }

    private static void recordPayment() {
        String rentalId = InputHelper.getStringInput("\nEnter Rental ID: ");
        RentalAgreement rental = rentalSystem.findRentalById(rentalId);

        if (rental != null) {
            if (!rental.isActive()) {
                System.out.println(" Cannot record payment for terminated rental!");
                return;
            }
            double amount = InputHelper.getDoubleInput("Enter Payment Amount (ETB): ");
            rental.recordPayment(amount);
        } else {
            System.out.println(" Rental agreement not found!");
        }
    }

    private static void terminateRental() {
        String rentalId = InputHelper.getStringInput("\nEnter Rental ID to terminate: ");
        RentalAgreement rental = rentalSystem.findRentalById(rentalId);

        if (rental != null) {
            if (!rental.isActive()) {
                System.out.println(" Rental is already terminated!");
                return;
            }
            rental.terminate();
            Property property = rental.getProperty();
            if (property != null) {
                property.setStatus(PropertyStatus.AVAILABLE);
            }
            System.out.println(" Rental terminated successfully!");
            System.out.println("   Property is now available for new tenants.");
        } else {
            System.out.println(" Rental agreement not found!");
        }
    }

    // ==================== REPORTS ====================

    private static void displayReports() {
        while (true) {
            System.out.println("\n--- REPORTS & ANALYTICS ---");
            System.out.println("1. System Summary");
            System.out.println("2. Available Properties");
            System.out.println("3. Monthly Revenue Report");
            System.out.println("4. Back to Main Menu");

            int choice = InputHelper.getIntInput("Choice: ");

            switch (choice) {
                case 1:
                    rentalSystem.displaySystemSummary();
                    break;
                case 2:
                    rentalSystem.displayAvailableProperties();
                    break;
                case 3:
                    displayRevenueReport();
                    break;
                case 4:
                    return;
                default:
                    System.out.println(" Invalid choice!");
            }
        }
    }

    private static void displayRevenueReport() {
        System.out.println("\n--- MONTHLY REVENUE REPORT ---");
        double totalMonthlyRevenue = 0;
        int activeRentalCount = 0;

        for (RentalAgreement rental : rentalSystem.getRentals()) {
            if (rental.isActive()) {
                double rent = rental.getProperty().getRent();
                totalMonthlyRevenue += rent;
                activeRentalCount++;
                System.out.printf("    %s - %s: %.2f ETB/month%n",
                        rental.getRentalId(),
                        rental.getProperty().getAddress(),
                        rent);
            }
        }

        System.out.println("-".repeat(45));
        System.out.println("   Active Rentals: " + activeRentalCount);
        System.out.printf("    TOTAL MONTHLY REVENUE: %.2f ETB%n", totalMonthlyRevenue);
        System.out.println("=".repeat(45));
    }
}
