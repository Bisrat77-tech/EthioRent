package model;

public class Apartment extends Property{
    private int floor;
    private boolean hasElevator;

    // constructor

    public Apartment (String propertyId, String address, double rent, int bedroom,
                      int bathrooms, int floor, boolean hasElevator){
        super(propertyId, address, rent,bedroom, bathrooms);
        this.floor = floor;
        this.hasElevator = hasElevator;
    }
    // Override
    public void displayInfo(){
        System.out.println("=== APARTMENT ===");
        System.out.println("ID: " + propertyId);
        System.out.println("Address: " + address);
        System.out.println("Monthly Rent: " + rent + "ETB");
        System.out.println("Bedrooms: " + bedrooms + "Bathrooms: " + bathrooms);
        System.out.println("Floor: " + floor);
        System.out.println("Has Elevator: " + (hasElevator ? "Yes" : "No"));
        System.out.println("Status: " + status);
    }

    @Override
    protected String getType() {
        return "APARTMENT";
    }

// Getters
    public int getFloor() {
        return floor;
    }

    public boolean hasElevator() {
        return hasElevator;
    }
}
