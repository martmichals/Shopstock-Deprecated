package com.example.shopstock;

public class Item {
    // Item attributes
    private String itemName;
    private String itemID;
    private String categoryID;
    private double confidence;

    // TODO: Implement constructor and isInStock method

    // Constructor
    public Item(String itemName, String itemID, String categoryID, double confidence){
        this.itemName = itemName;
        this.itemID = itemID;
        this.categoryID = categoryID;
        this.confidence = confidence;
    }

    public String getItemName() {
        return itemName;
    }

    public double getConfidence() {
        return confidence;
    }

    // Method to return whether or not the given item is in stock or not
    public boolean isInStock(){
       return false;
    }
}
