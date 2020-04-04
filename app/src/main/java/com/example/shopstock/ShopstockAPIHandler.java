package com.example.shopstock;

// Used for JSON parsing and launching HTTPS requests
import android.content.Context;
import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Other required classes

/* Class that facilitates interaction with the Shopstock API
 * Class methods are all static
 * All methods are asynchronous
 */
public class ShopstockAPIHandler {
    private static final String BASE_URL = "https://shopstock.live/api/";
    private static final String TAG = "ShopstockAPIHandler";

    // Method to grab all the information for stores in an area
    public static void updateStoresInArea(final double[] bottom_left_coordinate,
                                          final double[] top_right_coordinate,
                                          Context context, final ShopstockListener listener){
        RequestQueue queue = Volley.newRequestQueue(context);
        final String methodRequest = BASE_URL + "get_stores_in_area?lat_1="
                + String.valueOf(bottom_left_coordinate[0]) + "&lat_2="
                + String.valueOf(top_right_coordinate[0]) + "&long_1="
                + String.valueOf(bottom_left_coordinate[1]) + "&long_2="
                + String.valueOf(top_right_coordinate[1]);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, methodRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO : Save the data pulled locally

                        // Code that runs on request success
                        listener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Code that runs on request failure
                        if(error instanceof TimeoutError || error instanceof NoConnectionError){
                            listener.onFailure(true);
                        }else{
                            Log.e(TAG, "Error updating the stores in the area from the Shopstock API");
                            listener.onFailure(false);
                        }
                    }
                });
        queue.add(stringRequest);
    }

    // Method to grab the list of all items from the server
    // TODO: Fix API-side bug in url assembly
    public static void updateItemList(Context context, final ShopstockListener listener){
        RequestQueue queue = Volley.newRequestQueue(context);

        final String methodRequest = BASE_URL + "get_items";
        Log.d(TAG, "Sending the request: " + methodRequest);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, methodRequest,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listener.onSuccess(response);
                    Log.d(TAG, "This was the response string: " + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Code that runs on request failure
                    if(error instanceof TimeoutError || error instanceof NoConnectionError){
                        listener.onFailure(true);
                    }else{
                        Log.e(TAG, "Error updating the item list from the Shopstock API");
                        listener.onFailure(false);
                    }
                }
        });
    }


    /* All the methods used for parsing the json returns of http requests
     * All return workable class types
     */
    // Method to parse the updateStoresInArea response string into an array of Stores
    // TODO : NEHA
    public static Store[] parseIntoStores(String json) {
        return null;
    }

    // Method to parse the items into a list from JSON
    //returns null if list of stores is empty/information incomplete
    public static Item[] parseIntoItems(String json) {

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONObject("items").names();
            Item[] items = new Item[arr.length()]; //return array
            for (int i = 0; i < arr.length(); i++) {
                String item_id = arr.getString(i);
                JSONObject item = obj.getJSONObject("items").getJSONObject(item_id);

                int itemID = Integer.parseInt(item_id);
                String itemName = item.getString("name");
                int categoryID = item.getInt("item-category");

                items[i] = new Item(itemID, itemName, categoryID);
            }
            Log.i(TAG, "good");
            return items;
        } catch (JSONException e) {
            Log.e(TAG, "Could not find list of stores");
            return null;
        }
    }

    // Possibly implement this if we want to cache data
    public static Store[] getStoresFromLocal(String path){
        return null;
    }
}
