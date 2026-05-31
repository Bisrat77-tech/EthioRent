package service;

import model.*;
import java.io.*;
import java.util.*;

public class RentalSystem {

    private Map<String, Property> properties;
    private Map<String, Tenant> tenants;
    private Map<String, RentalSystem> rentals;

    private static final String DATA_DIR = "./data/";
    private static final String PROPERTIES_FILE = DATA_DIR + "properties.txt";
    private static final String TENANTS_FILE = DATA_DIR + "tenants.txt";
    private static final String RENTALS_FILE = DATA_DIR + "rentals.txt";

    // Default constructor
    // Methods


}
