package model;

public class Property {

    protected String propertyId;
    protected String address;
    protected double rent;
    protected int bedrooms;
    protected int bathrooms;
    protected PropertyStatus status;

    // Constructor, getters
    public Property (String propertyId, String address, double rent, int bedrooms, int bathrooms, PropertyStatus status) {
        this.propertyId = propertyId;
        this.address = address;
        this.rent = rent;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.status = status;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public String getAddress() {
        return address;
    }

    public double getRent() {
        return rent;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    // methods
    @Override
    public String toString() {
        return "Property ID: " + propertyId + ", Address: " + address + ", Rent: " + rent + ", Bedrooms: " + bedrooms + ", Bathrooms: " + bathrooms + ", Status: " + status;
    }
}
