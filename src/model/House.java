package model;

public class House extends Property {
// House fields
    private double lotSize;
    private boolean hasGarden;
// Constructor
    public House(String propertyId, String address, double rent, int bedrooms, int bathrooms, double lotSize, boolean hasGarden){
        super(propertyId, address, rent, bedrooms, bathrooms);
        this.lotSize = lotSize;
        this.hasGarden = hasGarden;
    }
// Overriding abstract method from parent class
    @Override
    public void displayInfo(){
        System.out.println("=== HOUSE PROPERTY ===");
        System.out.println("ID: " + propertyId);
        System.out.println("Address: " + address);
        System.out.println("Monthly Rent: " + rent + "ETB");
        System.out.println("Bedrooms" + bedrooms + " | Bathrooms: " + bathrooms);
        System.out.println("Status: " + status);

    }

    @Override
    protected String getType() {
        return "House";
    }
// Getters

    public double getLotSize() {
        return lotSize;
    }

    public boolean isHasGarden() {
        return hasGarden;
    }
}
