package service;

import model.*;
import utils.DatabaseConnection;
import utils.InputHelper;
import java.io.*;
import java.util.*;

public class RentalSystem {
    
    private Map<String, Property> properties;
    private Map<String, Tenant> tenants;
    private Map<String, RentalAgreement> rentals;

    
    private java.sql.Connection dbconnection;

    
    private static final String DATA_DIR = "./data/";
    private static final String PROPERTIES_FILE = DATA_DIR + "properties.txt";
    private static final String TENANTS_FILE = DATA_DIR + "tenants.txt";

    
    private boolean useDatabase = true; 

    public RentalSystem() {
        properties = new HashMap<>();
        tenants = new HashMap<>();
        rentals = new HashMap<>();
        if (useDatabase) {
            dbconnection = DatabaseConnection.getConnection();
            if (dbconnection == null) {
                System.out.println("Database not available, falling back to file storage!");
                useDatabase = false;
                createDataDirectory();
                loadDataFromFile();
            } else {
                System.out.println("Using DATABASE storage mode");
            }
        } else {
            createDataDirectory();
            loadDataFromFile();
            System.out.println("Using FILE storage mode");
        }

    }

    private void createDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    
    public void addProperty(Property property) {
        if (useDatabase) {
            addPropertyToDatabase(property);
        } else {
            addPropertToFile(property);
        }
    }

    private void addPropertyToDatabase(Property property) {
        String sql = "INSERT INTO properties (id, address, rent, bedrooms, bathrooms, status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            java.sql.PreparedStatement pstmt = dbconnection.prepareStatement(sql);
            pstmt.setString(1, property.getPropertyId());
            pstmt.setString(2, property.getAddress());
            pstmt.setDouble(3, property.getRent());
            pstmt.setInt(4, property.getBedrooms());
            pstmt.setInt(5, property.getBathrooms());
            pstmt.setString(6, property.getStatus().toString());
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Property saved to Database!");
        } catch (java.sql.SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private void addPropertToFile(Property property) {
        properties.put(property.getPropertyId(), property);
        System.out.println("Property saved to FILE!");
    }

    public void displayAllProperties() {
        if (useDatabase) {
            displayPropertiesFromDatabase();
        } else {
            displayPropertiesFromFile();
        }
    }

    private void displayPropertiesFromDatabase() {
        String sql = "SELECT * FROM properties";
        try {
            java.sql.Statement stmt = dbconnection.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n --- ALL PROPERTIES (FROM DATABASE) ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id"));
                System.out.println("   Address: " + rs.getString("address"));
                System.out.println("   Rent: " + rs.getDouble("rent") + " ETB");
                System.out.println("   Status: " + rs.getString("status"));
                System.out.println("-".repeat(40));
            }
            rs.close();
            stmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private void displayPropertiesFromFile() {
        if (properties.isEmpty()) {
            System.out.println("No properties in the system. ");
            return;
        }
        System.out.println("\n --- ALL PROPERTIES (FROM FILE) ---");
        for (Property p : properties.values()) {
            p.displayInfo();
            System.out.println("-".repeat(40));
        }
    }


    public Property findPropertyById(String id) {
        if (useDatabase) {
            return findPropertyInDatabase(id);
        } else {
            return properties.get(id);
        }
    }

    private Property findPropertyInDatabase(String id) {
        String sql = " SELECT * FROM properties WHERE id = ?";
        try {
            java.sql.PreparedStatement pstmt = dbconnection.prepareStatement(sql);
            pstmt.setString(1, id);
            java.sql.ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Found in Database: " + rs.getString("address"));
            } else {
                System.out.println("Property not Found!");
            }
            rs.close();
            pstmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    public void updatePropertyStatus(String id, PropertyStatus newStatus) {
        if (useDatabase) {
            String sql = "UPDATE properties SET status = ? WHERE id = ?";
            try {
                java.sql.PreparedStatement pstmt = dbconnection.prepareStatement(sql);
                pstmt.setString(1, newStatus.toString());
                pstmt.setString(2, id);
                int updated = pstmt.executeUpdate();
                if (updated > 0) {
                    System.out.println("Status updated in DATABASE!");
                } else {
                    System.out.println("Property not found!");
                }
                pstmt.close();
            } catch (java.sql.SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        } else {
            Property p = properties.get(id);
            if (p != null) {
                p.setStatus(newStatus);
                System.out.println("Status updated in FILE!");
            } else {
                System.out.println("Property not found!");
            }
        }
    }

    public void removeProperty(String id) {
        if (useDatabase) {
            String sql = "DELETE FROM properties WHERE id = ?";
            try {
                java.sql.PreparedStatement pstmt = dbconnection.prepareStatement(sql);
                pstmt.setString(1, id);
                int deleted = pstmt.executeUpdate();
                if (deleted > 0) {
                    System.out.println("Property removed from DATABASE!");
                } else {
                    System.out.println("Property not found!");
                }
                pstmt.close();
            } catch (java.sql.SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        } else {
            if (properties.containsKey(id)) {
                properties.remove(id);
                System.out.println("Property removed from FILE!");
            } else {
                System.out.println("Property not found!");
            }
        }
    }

    
    public void addTenant(Tenant tenant) {
        if (useDatabase) {
            addTenantToDatabase(tenant);
        } else {
            addTenantToFile(tenant);
        }
    }

    private void addTenantToDatabase(Tenant tenant) {
        String sql = "INSERT INTO tenants (id, name, phone, email) VALUES (?, ?, ?, ?)";
        try {
            java.sql.PreparedStatement pstmt = dbconnection.prepareStatement(sql);
            pstmt.setString(1, tenant.getTenantId());
            pstmt.setString(2, tenant.getName());
            pstmt.setString(3, tenant.getPhone());
            pstmt.setString(4, tenant.getEmail());
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println(" Tenant saved to DATABASE!");
        } catch (java.sql.SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private void addTenantToFile(Tenant tenant) {
        tenants.put(tenant.getTenantId(), tenant);
        System.out.println("Tenant saved to FILE!");
    }

    public void displayAllTenants() {
        if (useDatabase) {
            displayTenantsFromDatabase();
        } else {
            displayTenantFromFile();
        }
    }

    private void displayTenantsFromDatabase() {
        String sql = "SELECT * From tenants";
        try {
            java.sql.Statement stmt = dbconnection.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n--- ALL TENANTS (FROM DATABASE) ---");
            while (rs.next()) {
                System.out.println(" ID: " + rs.getString("id"));
                System.out.println(" Name: " + rs.getString("name"));
                System.out.println(" Phone: " + rs.getString("phone"));
                System.out.println("-".repeat(40));
            }
            rs.close();
            stmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(" Database error: " + e.getMessage());
        }
    }

    private void displayTenantFromFile() {
        if (tenants.isEmpty()) {
            System.out.println("No tenants in the system.");
            return;
        }
        System.out.println("\n--- ALL TENANTS (FROM FILE) ---");
        for (Tenant t : tenants.values()) {
            t.displayInfo();
            System.out.println("-".repeat(40));
        }
    }

    public Tenant findTenantById(String id) {
        if (useDatabase) {
            return findTenantInDatabase(id);
        } else {
            return tenants.get(id);
        }
    }

    private Tenant findTenantInDatabase(String id) {
        String sql = "SELECT * FROM tenants WHERE id = ?";
        try {
            java.sql.PreparedStatement pstmt = dbconnection.prepareStatement(sql);
            pstmt.setString(1, id);
            java.sql.ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println(" Found in database: " + rs.getString("name"));
                return new Tenant(rs.getString("id"), rs.getString("name"),
                        rs.getString("phone"), rs.getString("email"));
            } else {
                System.out.println(" Tenant not found!");
            }
            rs.close();
            pstmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(" Database error: " + e.getMessage());
        }
        return null;
    }

    public void saveDataToFile() {
        if (!useDatabase) {
            saveProperties();
            saveTenants();
            saveRentals();
            System.out.println(" All data saved to files!");
        }
    }


    private void saveProperties() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PROPERTIES_FILE))) {
            for (Property p : properties.values()) {
                writer.println(p.toFileString());
            }
        } catch (IOException e) {
            System.out.println(" Error saving properties: " + e.getMessage());
        }
    }

    private void saveTenants() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TENANTS_FILE))) {
            for (Tenant t : tenants.values()) {
                writer.println(t.toFileString());
            }
        } catch (IOException e) {
            System.out.println(" Error saving tenants: " + e.getMessage());
        }
    }

    private void saveRentals() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RENTALS_FILE))) {
            for (RentalAgreement r : rentals.values()) {
                writer.println(r.toFileString());
            }
        } catch (IOException e) {
            System.out.println(" Error saving rentals: " + e.getMessage());
        }
    }

    public void loadDataFromFile() {
        if (!useDatabase) {
            loadProperties();
            loadTenants();
            loadRentals();
        }
    }

    private void loadProperties() {
        File file = new File(PROPERTIES_FILE);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 7) continue;
                
            }
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
                
            }
        } catch (IOException e) {
            System.out.println(" Error loading rentals: " + e.getMessage());
        }
    }

    public List<RentalAgreement> getRentals() {
        return new ArrayList<>(rentals.values());
    }

    public void displaySystemSummary() {
        if (useDatabase) {
            System.out.println("\n--- SYSTEM SUMMARY (FROM DATABASE) ---");
            try {
                java.sql.Statement stmt = dbconnection.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM properties");
                if (rs.next()) {
                    System.out.println(" Total Properties: " + rs.getInt(1));
                }
                rs = stmt.executeQuery("SELECT COUNT(*) FROM tenants");
                if (rs.next()) {
                    System.out.println("👤 Total Tenants: " + rs.getInt(1));
                }
                rs.close();
                stmt.close();
            } catch (java.sql.SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        } else {
            System.out.println("\n--- SYSTEM SUMMARY (FROM FILE) ---");
            System.out.println("Total Properties: " + properties.size());
            System.out.println("Total Tenants: " + tenants.size());
        }
    }

    public void displayAvailableProperties() {
        if (useDatabase) {
            String sql = "SELECT * FROM properties WHERE status = 'AVAILABLE'";
            try {
                java.sql.Statement stmt = dbconnection.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql);
                System.out.println("\n--- AVAILABLE PROPERTIES (FROM DATABASE) ---");
                while (rs.next()) {
                    System.out.println(" ID: " + rs.getString("id"));
                    System.out.println(" Address: " + rs.getString("address"));
                    System.out.println(" Rent: " + rs.getDouble("rent") + " ETB");
                    System.out.println("-".repeat(40));
                }
                rs.close();
                stmt.close();
            } catch (java.sql.SQLException e) {
                System.out.println(" Database error: " + e.getMessage());
            }
        } else {
            System.out.println("\n--- AVAILABLE PROPERTIES (FROM FILE) ---");
            boolean found = false;
            for (Property p : properties.values()) {
                if (p.getStatus() == PropertyStatus.AVAILABLE) {
                    p.displayInfo();
                    System.out.println("-".repeat(40));
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No available properties.");
            }
        }
    }

    public void removeTenant(String id) {
        if (useDatabase) {
            String sql = "DELETE FROM tenants WHERE id = ?";
            try {
                java.sql.PreparedStatement pstmt = dbconnection.prepareStatement(sql);
                pstmt.setString(1, id);
                int deleted = pstmt.executeUpdate();
                if (deleted > 0) {
                    System.out.println("Tenant removed from DATABASE!");
                } else {
                    System.out.println("Tenant not found!");
                }
                pstmt.close();
            } catch (java.sql.SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        } else {
            if (tenants.containsKey(id)) {
                tenants.remove(id);
                System.out.println("Tenant removed from FILE!");
            } else {
                System.out.println("Tenant not found!");
            }
        }
    }
    public void addRental(RentalAgreement rental) {
        if (useDatabase) {
            String sql = "INSERT INTO rentals (rental_id, property_id, tenant_id, start_date, end_date, deposit, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try {
                java.sql.PreparedStatement pstmt = dbconnection.prepareStatement(sql);
                pstmt.setString(1, rental.getRentalId());
                pstmt.setString(2, rental.getProperty().getPropertyId());
                pstmt.setString(3, rental.getTenant().getTenantId());
                pstmt.setDate(4, new java.sql.Date(rental.getStartDate().getTime()));
                pstmt.setDate(5, new java.sql.Date(rental.getEndDate().getTime()));
                pstmt.setDouble(6, rental.getDeposit());
                pstmt.setBoolean(7, rental.isActive());
                pstmt.executeUpdate();
                pstmt.close();
                System.out.println("Rental saved to DATABASE!");
            } catch (java.sql.SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        } else {
            rentals.put(rental.getRentalId(), rental);
            System.out.println("Rental saved to FILE!");
        }
    }

    
    public RentalAgreement findRentalById(String id) {
        if (useDatabase) {
            String sql = "SELECT * FROM rentals WHERE rental_id = ?";
            try {
                java.sql.PreparedStatement pstmt = dbconnection.prepareStatement(sql);
                pstmt.setString(1, id);
                java.sql.ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Rental found in DATABASE!");
                    
                } else {
                    System.out.println("Rental not found!");
                }
                rs.close();
                pstmt.close();
            } catch (java.sql.SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
            return null;
        } else {
            return rentals.get(id);
        }
    }

    
    public void displayAllRentals() {
        if (useDatabase) {
            String sql = "SELECT * FROM rentals WHERE is_active = true";
            try {
                java.sql.Statement stmt = dbconnection.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql);
                System.out.println("\n--- ALL ACTIVE RENTALS (FROM DATABASE) ---");
                while (rs.next()) {
                    System.out.println("   Rental ID: " + rs.getString("rental_id"));
                    System.out.println("   Property ID: " + rs.getString("property_id"));
                    System.out.println("   Tenant ID: " + rs.getString("tenant_id"));
                    System.out.println("   Deposit: " + rs.getDouble("deposit") + " ETB");
                    System.out.println("-".repeat(40));
                }
                rs.close();
                stmt.close();
            } catch (java.sql.SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        } else {
            if (rentals.isEmpty()) {
                System.out.println("No active rentals in the system.");
                return;
            }
            System.out.println("\n--- ALL ACTIVE RENTALS (FROM FILE) ---");
            for (RentalAgreement r : rentals.values()) {
                if (r.isActive()) {
                    r.displayInfo();
                    System.out.println("-".repeat(40));
                }
            }
        }
    }

        

}



