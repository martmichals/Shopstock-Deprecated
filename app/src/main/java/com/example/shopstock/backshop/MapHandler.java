package com.example.shopstock.backshop;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class MapHandler {
    private static final String TAG = "MapHandler";
    private static final double EXPANSION_FACTOR = 0.2;

    private double[] bottomLeftCoordinateData;
    private double[] topRightCoordinateData;
    private double[] bottomLeftCoordinateScreen;
    private double[] topRightCoordinateScreen;
    private Store[] storesInArea;

    // Constructor
    public MapHandler(){
        bottomLeftCoordinateData = new double[2];
        topRightCoordinateData = new double[2];
        bottomLeftCoordinateScreen = new double[2];
        topRightCoordinateScreen = new double[2];
        for(int i = 0; i < 2; i++){
            bottomLeftCoordinateData[i] = 0;
            topRightCoordinateData[i] = 0;
            bottomLeftCoordinateScreen[i] = 0;
            topRightCoordinateScreen[i] = 0;
        }
    }

    // Updating the stores in the area based on a new area displayed in the screen
    // Brute Force w/Overwrites, improve in the future
   public void updateScreenArea(double[] newBottomLeft, double[] newTopRight,
                             Context context, final MapHandlerListener listener){

        Log.i(TAG, "Launching an update of the area");
        bottomLeftCoordinateScreen = newBottomLeft;
        topRightCoordinateScreen = newTopRight;

        if(newBottomLeft[0] < bottomLeftCoordinateData[0]
            || newBottomLeft[1] < bottomLeftCoordinateData[1]
            || newTopRight[0] > topRightCoordinateData[0]
            || newTopRight[1] > topRightCoordinateData[0]
            || bottomLeftCoordinateData[0] == topRightCoordinateData[0]){

            double deltaLat = newTopRight[0] - newBottomLeft[0];
            double deltaLong = newTopRight[0] - newBottomLeft[0];

            if(deltaLat <= 0 || deltaLong <= 0){
               Log.e(TAG, "The difference in latitude and longitude for the phone screen was not positive");
               listener.onFailure(false);
               return;
            }

            double latExpand = EXPANSION_FACTOR * deltaLat;
            double longExpand = EXPANSION_FACTOR * deltaLong;

            bottomLeftCoordinateData[0] = bottomLeftCoordinateScreen[0] - latExpand;
            bottomLeftCoordinateData[1] = bottomLeftCoordinateScreen[1] - longExpand;
            topRightCoordinateData[0] = topRightCoordinateScreen[0] + latExpand;
            topRightCoordinateData[1] = topRightCoordinateScreen[1] + longExpand;

            Log.d(TAG, "Launching the api method");
            ShopstockAPIHandler.updateStoresInArea(bottomLeftCoordinateData, topRightCoordinateData,
                    context, new ShopstockListener() {
                        @Override
                        public void onSuccess(String json) {
                            Log.e(TAG, "Success inside MapHandler");
                            storesInArea = ShopstockAPIHandler.parseIntoStores(json);
                            Log.d(TAG, "The number of stores from the request: " + storesInArea.length);
                            listener.onSuccess();
                        }

                        @Override
                        public void onFailure(boolean isConnectionError) {
                            listener.onFailure(isConnectionError);
                            Log.e(TAG, "Failure to pull store information");
                        }
                    });
        }else{
            listener.onSuccess();
        }
   }

   public ArrayList<Store> getStoresOnScreen(){
        ArrayList<Store> storesOnScreen = new ArrayList<>();

        if(storesInArea == null)
            return storesOnScreen;

        for(Store store: storesInArea){
            double[] storeCoordinates = store.getCoordinates();
            if(storeCoordinates[0] > bottomLeftCoordinateScreen[0] && storeCoordinates[1] > bottomLeftCoordinateScreen[1]
               && storeCoordinates[0] < topRightCoordinateScreen[0] && storeCoordinates[1] < topRightCoordinateScreen[1]){
                storesOnScreen.add(store);
            }
        }
        return storesOnScreen;
   }
}
