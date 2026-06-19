package model;

public class Studio extends Property{
    private boolean isFurnished;
   
    public Studio(String propertyId, String address, double rent, int bedrooms,
                  int bathrooms, boolean isFurnished){
        super(propertyId, address, rent, bedrooms, bathrooms);
        this.isFurnished = isFurnished;
    }
    @Override
    public void displayInfo() {
        System.out.println("\n STUDIO APARTMENT");
        System.out.println("ID: " + propertyId);
        System.out.println("Address: " + address);
        System.out.println("Monthly Rent: " + rent + " ETB");
        System.out.println("Bedrooms: " + bedrooms + " | Bathrooms: " + bathrooms);
        System.out.println("Furnished: " + (isFurnished ? "Yes" : "No"));
        System.out.println("Status: " + status);
    }

    @Override
    protected String getType() {
        return "STUDIO";
    }

    public boolean isFurnished(){return isFurnished;}

}



