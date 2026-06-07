package service;

import model.*;
import java.io.*;
import java.util.*;

public class RentalSystem {
    private Map<String, Property> properties;
    private Map<String, Tenant> tenants;
    private Map<String, RentalAgreement> rentals;

    private static final String DATA_DIR = "./data/";
    private static final String PROPERTIES_FILE = DATA_DIR + "properties.txt";
    private static final String TENANTS_FILE = DATA_DIR + "tenants.txt";
    private static final String RENTALS_FILE = DATA_DIR + "rentals.txt";

    public RentalSystem() {
        properties = new HashMap<>();
        tenants = new HashMap<>();
        rentals = new HashMap<>();
        createDataDirectory();
    }

    private void createDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // Property Management
    public void addProperty(Property property) {
        properties.put(property.getPropertyId(), property);
    }

    public Property findPropertyById(String id) {
        return properties.get(id);
    }

    public void removeProperty(String id) {
        if (properties.containsKey(id)) {
            properties.remove(id);
            System.out.println(" Property removed successfully!");
        } else {
            System.out.println(" Property not found!");
        }
    }

    public void displayAllProperties() {
        if (properties.isEmpty()) {
            System.out.println("No properties in the system.");
            return;
        }
        System.out.println("\n--- ALL PROPERTIES ---");
        for (Property p : properties.values()) {
            p.displayInfo();
            System.out.println("-".repeat(40));
        }
    }

    public void displayAvailableProperties() {
        System.out.println("\n--- AVAILABLE PROPERTIES ---");
        boolean found = false;
        for (Property p : properties.values()) {
            if (p.getStatus() == PropertyStatus.AVAILABLE) {
                p.displayInfo();
                System.out.println("-".repeat(40));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available properties at the moment.");
        }
    }

    // Tenant Management
    public void addTenant(Tenant tenant) {
        tenants.put(tenant.getTenantId(), tenant);
    }

    public Tenant findTenantById(String id) {
        return tenants.get(id);
    }

    public void removeTenant(String id) {
        if (tenants.containsKey(id)) {
            tenants.remove(id);
            System.out.println(" Tenant removed successfully!");
        } else {
            System.out.println(" Tenant not found!");
        }
    }

    public void displayAllTenants() {
        if (tenants.isEmpty()) {
            System.out.println("No tenants in the system.");
            return;
        }
        System.out.println("\n--- ALL TENANTS ---");
        for (Tenant t : tenants.values()) {
            t.displayInfo();
            System.out.println("-".repeat(40));
        }
    }

    // Rental Management
    public void addRental(RentalAgreement rental) {
        rentals.put(rental.getRentalId(), rental);
    }

    public RentalAgreement findRentalById(String id) {
        return rentals.get(id);
    }

    public void displayAllRentals() {
        if (rentals.isEmpty()) {
            System.out.println("No active rentals in the system.");
            return;
        }
        System.out.println("\n--- ALL RENTALS ---");
        for (RentalAgreement r : rentals.values()) {
            if (r.isActive()) {
                r.displayInfo();
                System.out.println("-".repeat(40));
            }
        }
    }

    public List<RentalAgreement> getRentals() {
        return new ArrayList<>(rentals.values());
    }

    // Report Methods
    public void displaySystemSummary() {
        System.out.println("\n--- SYSTEM SUMMARY ---");
        System.out.println(" Total Properties: " + properties.size());
        System.out.println(" Total Tenants: " + tenants.size());

        long activeRentals = rentals.values().stream().filter(RentalAgreement::isActive).count();
        System.out.println(" Active Rentals: " + activeRentals);

        double totalRevenue = rentals.values().stream()
                .filter(RentalAgreement::isActive)
                .mapToDouble(r -> r.getProperty().getRent())
                .sum();
        System.out.printf(" Monthly Revenue: %.2f ETB%n", totalRevenue);
    }

    // ========== FILE HANDLING - SAVE DATA ==========
    public void saveDataToFile() {
        saveProperties();
        saveTenants();
        saveRentals();
        System.out.println(" All data saved successfully!");
    }

    private void saveProperties() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PROPERTIES_FILE))) {
            for (Property p : properties.values()) {
                writer.println(p.toFileString());
            }
            System.out.println("✓ Properties saved: " + properties.size());
        } catch (IOException e) {
            System.out.println(" Error saving properties: " + e.getMessage());
        }
    }

    private void saveTenants() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TENANTS_FILE))) {
            for (Tenant t : tenants.values()) {
                writer.println(t.toFileString());
            }
            System.out.println("✓ Tenants saved: " + tenants.size());
        } catch (IOException e) {
            System.out.println(" Error saving tenants: " + e.getMessage());
        }
    }

    private void saveRentals() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RENTALS_FILE))) {
            for (RentalAgreement r : rentals.values()) {
                writer.println(r.toFileString());
            }
            System.out.println("✓ Rentals saved: " + rentals.size());
        } catch (IOException e) {
            System.out.println(" Error saving rentals: " + e.getMessage());
        }
    }

    // ========== FILE HANDLING - LOAD DATA ==========
    public void loadDataFromFile() {
        loadProperties();
        loadTenants();
        loadRentals();
        System.out.println(" Data loaded from files!");
    }

    private void loadProperties() {
        File file = new File(PROPERTIES_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 7) continue;

                String type = parts[6];
                Property property = null;

                try {
                    switch (type) {
                        case "HOUSE":
                            property = new House(parts[0], parts[1], Double.parseDouble(parts[2]),
                                    Integer.parseInt(parts[3]), Integer.parseInt(parts[4]),
                                    parts.length > 7 ? Double.parseDouble(parts[7]) : 0,
                                    parts.length > 8 && Boolean.parseBoolean(parts[8]));
                            break;
                        case "APARTMENT":
                            property = new Apartment(parts[0], parts[1], Double.parseDouble(parts[2]),
                                    Integer.parseInt(parts[3]), Integer.parseInt(parts[4]),
                                    parts.length > 7 ? Integer.parseInt(parts[7]) : 1,
                                    parts.length > 8 && Boolean.parseBoolean(parts[8]));
                            break;
                        case "STUDIO":
                            property = new Studio(parts[0], parts[1], Double.parseDouble(parts[2]),
                                    Integer.parseInt(parts[3]), Integer.parseInt(parts[4]),
                                    parts.length > 7 && Boolean.parseBoolean(parts[7]));
                            break;
                    }

                    if (property != null) {
                        property.setStatus(PropertyStatus.valueOf(parts[5]));
                        properties.put(property.getPropertyId(), property);
                    }
                } catch (Exception e) {
                    System.out.println("Error loading property: " + e.getMessage());
                }
            }
            System.out.println("✓ Properties loaded: " + properties.size());
        } catch (IOException e) {
            System.out.println(" Error loading properties: " + e.getMessage());
        }
    }

    private void loadTenants() {
        File file = new File(TENANTS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    Tenant tenant = new Tenant(parts[0], parts[1], parts[2], parts[3]);
                    tenants.put(tenant.getTenantId(), tenant);
                }
            }
            System.out.println("✓ Tenants loaded: " + tenants.size());
        } catch (IOException e) {
            System.out.println(" Error loading tenants: " + e.getMessage());
        }
    }

    private void loadRentals() {
        File file = new File(RENTALS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    Property p = findPropertyById(parts[1]);
                    Tenant t = findTenantById(parts[2]);

                    if (p != null && t != null) {
                        RentalAgreement rental = new RentalAgreement(parts[0], p, t,
                                Double.parseDouble(parts[5]), 12);
                        if (parts.length > 6 && !Boolean.parseBoolean(parts[6])) {
                            rental.terminate();
                        }
                        rentals.put(rental.getRentalId(), rental);
                    }
                }
            }
            System.out.println("✓ Rentals loaded: " + rentals.size());
        } catch (IOException e) {
            System.out.println(" Error loading rentals: " + e.getMessage());
        }
    }
}