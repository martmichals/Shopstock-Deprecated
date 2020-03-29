package com.example.shopstock;

import java.util.ArrayList;

public class MapHandler {
    private double[] bottom_left_coordinate;
    private double[] top_right_coordinate;
    private ArrayList<Store> storesInArea;

    // Constructor
    public MapHandler(){
        // TODO: Implement
    }

    // Method to update all attributes with data from the API
    // @return: true if data acquisition was successful, false otherwise
    public boolean updateStoresInArea(double[] bottom_left_coordinate, double[] top_right_coordinate){
       this.bottom_left_coordinate = bottom_left_coordinate;
       this.top_right_coordinate = top_right_coordinate;

       // TODO : Code to call the API with a query for the data in the area
        return false;
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
