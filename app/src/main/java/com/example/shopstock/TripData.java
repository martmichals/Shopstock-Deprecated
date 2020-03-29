package com.example.shopstock;

import java.util.ArrayList;
import java.util.Date;

/* Class to record the trip data for the user
 * Should be instantiated inside RecordTripActivity
 */
public class TripData {
    private Date date;
    private Store store;
    private ArrayList<Item> outOfStockItems;
    private ArrayList<Item> backInStockRecorded;

    public TripData(){
        this.date = null;
        this.store = null;
        this.outOfStockItems = null;
        this.backInStockRecorded = null;
    }

    // Getter and setter methods to call from the RecordTripActivity Fragment
    public void setDate(Date date) {
        this.date = date;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setBackInStockRecorded(ArrayList<Item> backInStockRecorded) {
        this.backInStockRecorded = backInStockRecorded;
    }

    public void setOutOfStockItems(ArrayList<Item> outOfStockItems) {
        this.outOfStockItems = outOfStockItems;
    }

    public Date getDate() {
        return date;
    }

    public Store getStore() {
        return store;
    }

    public ArrayList<Item> getBackInStockRecorded() {
        return backInStockRecorded;
    }

    public ArrayList<Item> getOutOfStockItems() {
        return outOfStockItems;
    }
}
