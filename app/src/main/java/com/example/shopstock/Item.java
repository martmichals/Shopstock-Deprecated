package com.example.shopstock;

public class Item {
    // Item attributes
    private String itemName;
    private int itemID;
    private int categoryID;
    private double confidence;

    // TODO: Implement constructor and isInStock method

    // Constructors
    public Item(int iID, String name, int catID, double level) {
        itemID = iID;
        itemName = name;
        categoryID = catID;
        confidence = level;
    }
    //specifically for populating lists of items, not connected to stores
    public Item(int iID, String name, int catID) {
        itemID = iID;
        itemName = name;
        categoryID = catID;
    }

    // Method to return whether or not the given item is in stock or not
    public boolean isInStock(){
       return false;
    }

    //setter
    public void setConfidence(double level) {
        confidence = level;
    }

    public String toString() {
        return itemID + " \n" + itemName + " \n" + categoryID + " \n";
    }

}
