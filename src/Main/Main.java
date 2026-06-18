package Main;

import model.*;
import service.RentalSystem;
import utils.InputHelper;

public class Main {

    private static RentalSystem rentalSystem = new RentalSystem();

    public static void main(String[] args) {

        while (true) {

            System.out.println("=================================");
            System.out.println("   ETHIORENT MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Add Property");
            System.out.println("2. View Properties");
            System.out.println("3. Add Tenant");
            System.out.println("4. View Tenants");
            System.out.println("5. Create Rental Agreement");
            System.out.println("6. View Rentals");
            System.out.println("7. Record Payment");
            System.out.println("8. System Report");
            System.out.println("9. Exit");
            System.out.println("=================================");

            int choice = InputHelper.getIntInput("Choose option: ");

            switch (choice) {

                case 1:
                    addProperty();
                    break;

                case 2:
                    rentalSystem.displayAllProperties();
                    break;

                case 3:
                    addTenant();
                    break;

                case 4:
                    rentalSystem.displayAllTenants();
                    break;

                case 5:
                    createRental();
                    break;

                case 6:
                    rentalSystem.displayAllRentals();
                    break;

                case 7:
                    recordPayment();
                    break;

                case 8:
                    rentalSystem.showSystemSummary();
                    break;

                case 9:
                    System.out.println("Thank you for using EthioRent!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addProperty() {

        System.out.println("\nProperty Types");
        System.out.println("1. House");
        System.out.println("2. Apartment");
        System.out.println("3. Studio");

        int type = InputHelper.getIntInput("Choose type: ");

        String id = InputHelper.getStringInput("Property ID: ");
        String address = InputHelper.getStringInput("Address: ");
        double rent = InputHelper.getDoubleInput("Monthly Rent: ");
        int bedrooms = InputHelper.getIntInput("Bedrooms: ");
        int bathrooms = InputHelper.getIntInput("Bathrooms: ");

        Property property;

        switch (type) {

            case 1:
                double lotSize = InputHelper.getDoubleInput("Lot Size: ");
                boolean hasGarden = Boolean.parseBoolean(
                        InputHelper.getStringInput("Has Garden (true/false): "));

                property = new House(id, address, rent, bedrooms, bathrooms, lotSize, hasGarden);
                break;

            case 2:
                int floor = InputHelper.getIntInput("Floor Number: ");
                boolean elevator = Boolean.parseBoolean(
                        InputHelper.getStringInput("Has Elevator (true/false): "));

                property = new Apartment(id, address, rent, bedrooms, bathrooms, floor, elevator);
                break;

            case 3:
                boolean furnished = Boolean.parseBoolean(
                        InputHelper.getStringInput("Furnished (true/false): "));

                property = new Studio(id, address, rent, bedrooms, bathrooms, furnished);
                break;

            default:
                System.out.println("Invalid type.");
                return;
        }

        rentalSystem.addProperty(property);
    }

    private static void addTenant() {

        String tenantId = InputHelper.getStringInput("Tenant ID: ");
        String name = InputHelper.getStringInput("Full Name: ");
        String phone = InputHelper.getStringInput("Phone Number: ");
        String email = InputHelper.getStringInput("Email: ");

        Tenant tenant = new Tenant(tenantId, name, phone, email);

        rentalSystem.addTenant(tenant);
    }

    private static void createRental() {

        String rentalId = InputHelper.getStringInput("Rental ID: ");
        String propertyId = InputHelper.getStringInput("Property ID: ");
        String tenantId = InputHelper.getStringInput("Tenant ID: ");
        double deposit = InputHelper.getDoubleInput("Deposit Amount: ");

        rentalSystem.createRentalAgreement(rentalId, propertyId, tenantId, deposit);
    }

    private static void recordPayment() {

        String rentalId = InputHelper.getStringInput("Rental ID: ");
        double amount = InputHelper.getDoubleInput("Payment Amount: ");

        rentalSystem.recordPayment(rentalId, amount);
    }
}