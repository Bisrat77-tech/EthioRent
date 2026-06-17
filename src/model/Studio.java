package model;

public class Studio extends Property {

    private boolean furnished;

    public Studio(String propertyId, String address,
                  double rent, int bedrooms,
                  int bathrooms, boolean furnished) {

        super(propertyId, address, rent, bedrooms, bathrooms);
        this.furnished = furnished;
    }

    public boolean isFurnished() {
        return furnished;
    }

    @Override
    public void displayInfo() {

        System.out.println("---- STUDIO ----");
        System.out.println("ID: " + propertyId);
        System.out.println("Address: " + address);
        System.out.println("Monthly Rent: " + rent + " ETB");
        System.out.println("Bedrooms: " + bedrooms);
        System.out.println("Bathrooms: " + bathrooms);
        System.out.println("Status: " + status);
        System.out.println("Furnished: " +
                (furnished ? "Yes" : "No"));
    }

    @Override
    public String getType() {
        return "Studio";
    }
}