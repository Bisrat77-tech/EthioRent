package model;

public abstract class Property {

    protected String propertyId;
    protected String address;
    protected double rent;
    protected int bedrooms;
    protected int bathrooms;
    protected PropertyStatus status;

    // Constructor, getters
    public Property (String propertyId, String address, double rent, int bedrooms, int bathrooms) {
        this.propertyId = propertyId;
        this.address = address;
        this.rent = rent;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.status = PropertyStatus.AVAILABLE;
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

    public abstract void displayInfo();
    protected abstract String getType();

    public String toFileString() {
        return propertyId + "|" + address + "|" + rent + "|" + bedrooms + "|" + bathrooms + "|" + status + "|" + getType();
    }

    @Override
    public String toString() {
        return toFileString();
    }
}
