package model;

public class Apartment extends Property {
    private int floor;
    private boolean hasElevator;

    public Apartment(String propertyId, String address, double rent, int bedrooms, int bathrooms,int floor, boolean hasElevator) {

        super(propertyId, address, rent, bedrooms, bathrooms);
        this.floor = floor;
        this.hasElevator = hasElevator;
    }

    @Override
    public void displayInfo() {
        System.out.println("----APARTMENTS----");
        System.out.println("Property ID: " + propertyId);
        System.out.println("Address: " + address);
        System.out.println("Rent: " + rent + " ETB");
        System.out.println("Bedrooms: " + bedrooms + " | Bathrooms: " + bathrooms);
        System.out.println("Floor Level: " + floor);
        System.out.println("Status: " + status);

        System.out.println("Elevator: ");
        if (hasElevator) {
            System.out.println("Available");
        } else {
            System.out.println("Not Available");
        }
    }

    @Override
    public String getType() {
        return "Apartment";
    }

    public int getFloor() {
        return floor;
    }

    public boolean isHasElevator() {
        return hasElevator;
    }
}
