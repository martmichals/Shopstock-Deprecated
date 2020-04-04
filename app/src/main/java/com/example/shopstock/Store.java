package com.example.shopstock;

import java.util.HashMap;

public class Store {
    // Attributes of this class
    private String storeID;
    private String storeName;
    private String storeAddress;
    private double[] coordinates;
    private String[] categoryIDs;
    private String chainID;

    // Item Name, Confidence
    private HashMap<String, String> items;

    // TODO: Implement the below methods

    // Constructor
    public Store(){
    }

    // Get the distance from the store to a set of coordinates
    public double getDistanceTo(double[] gps_coordinates){
       return 0;
    }

    // Get out of stock items for a store
    public Item[] getOutOfStock(){
       return null;
    }

    // END TO DO

    // Getter methods
    public String getStoreAddress(){return storeAddress;}
    public String getStoreName(){return storeName;}
}
