package com.example.shopstock.backshop;

public class Item {
    // Item attributes
    private String itemName;
    private int itemID;
    private int categoryID;
    private double confidence;

    // TODO: Implement constructor and isInStock method

    // Constructors
    public Item(int item_ID, String item_Name, int category_ID, double conf) {
        itemID = item_ID;
        itemName = item_Name;
        categoryID = category_ID;
        confidence = conf;
    }

    // Specifically for populating lists of items, not connected to stores
    public Item(int item_ID, String item_Name, int category_ID) {
        itemID = item_ID;
        itemName = item_Name;
        categoryID = category_ID;
    }

    // Method to return whether or not the given item is in stock or not
    public boolean isInStock(){
       return false;
    }

    public String toString() {
        return itemName + ": ID " + itemID + ", Category " + categoryID + " \n";
    }

    // Setter and Getter Methods
    public void setConfidence(double level) {
        confidence = level;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getItemName(){
        return itemName;
    }
}
