package com.example.shopstock;

import com.example.shopstock.backshop.Store;

import java.util.ArrayList;

public class MapHandler {
    private double[] bottom_left_coordinate;
    private double[] top_right_coordinate;
    private ArrayList<Store> storesInArea;

    // Constructor
    public MapHandler(){
        // TODO: Implement
    }

    // Method to get stores, sorted by distance to a point
    public ArrayList<Store> sortStoresInAreaByDistance(double[] base_point) {
        // TODO: Code to sort stores by distance to the base_point, closest point first
        return null;
    }

    // Getter and setter methods
    public ArrayList<Store> getStoresInArea() {
        return storesInArea;
    }
}
