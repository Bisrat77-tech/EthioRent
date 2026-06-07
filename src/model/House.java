package model;

public class House extends Property {

    private double lotSize;
    private boolean hasGarden;

    // constructor , override
    public House(String propertyId, String address, double rent, int bedrooms, int bathrooms, double lotSize, boolean hasGarden) {

        super(propertyId, address, rent, bedrooms, bathrooms);
        this.lotSize = lotSize;
        this.hasGarden = hasGarden;
    }

    public double getLotSize() {
        return lotSize;
    }

    public boolean hasGarden() {
        return hasGarden;
    }

    @Override
    public void displayInfo() {
        System.out.println("---- HOUSE PROPERTY ----");
        System.out.println("ID: " + propertyId);
        System.out.println("Address: " + address);
        System.out.println("Monthly Rent: " + rent + "ETB");
        System.out.println("Bedrooms: " + bedrooms + " | Bathrooms: " + bathrooms);
        System.out.println("Status: " + status);
        System.out.println("Lot Size: " + lotSize);
        System.out.println("Has Garden: " + (hasGarden ? "Yes" : "No"));
    }

    @Override
    public String getType() {
        return "House";
    }
}
