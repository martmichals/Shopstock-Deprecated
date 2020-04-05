package com.example.shopstock.backshop;

// Imports
import android.app.VoiceInteractor;
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
import com.example.shopstock.Item;
import com.example.shopstock.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* Class that facilitates interaction with the Shopstock API
 * Class methods are all static
 * Provides functionality for: parsing JSON, launching requests via Volley
 * Methods with calls to the API are asynchronous
 */
public class ShopstockAPIHandler {
    private static final String BASE_URL = "https://shopstock.live/api/";
    private static final String TAG = "ShopstockAPIHandler";


    /* Method to grab the information for stores in an area
       Does not return anything
     */
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

    /* Method that grabs a list of all items from the server
       Saves the items list locally, as well as shoots the string async through the listener
     */
    public static void updateItemList(final Context context, final ShopstockListener listener){
        RequestQueue queue = Volley.newRequestQueue(context);

        final String methodRequest = BASE_URL + "get_items";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, methodRequest,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    LocalDataHandler.saveItemsToLocal(context, response);
                    listener.onSuccess(response);
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
        queue.add(stringRequest);
    }


    /* Method that pulls the list of store categories
     * Saves the list of categories locally
     */
    public static void updateCategoriesChains(Context context, final ShopstockListener categoryListener,
                                              final ShopstockListener chainListener){
        RequestQueue queue = Volley.newRequestQueue(context);

        String methodRequest = BASE_URL + "get_chains";
        StringRequest chainRequest = new StringRequest(Request.Method.GET, methodRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO : Save the data locally
                        categoryListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error instanceof TimeoutError || error instanceof NoConnectionError){
                           categoryListener.onFailure(true);
                        }else{
                           Log.e(TAG, "Error updating item categories from the API");
                           categoryListener.onFailure(false);
                        }
                    }
                });
        queue.add(chainRequest);

        methodRequest = BASE_URL + "get_store_categories";
        StringRequest categoryRequest = new StringRequest(Request.Method.GET, methodRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO : Invoke the method to save the data locally
                        chainListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error instanceof TimeoutError || error instanceof NoConnectionError){
                            chainListener.onFailure(true);
                        }else{
                            Log.e(TAG, "Error updating the store chains from the API");
                            chainListener.onFailure(false);
                        }
                    }
                });
        queue.add(categoryRequest);
    }

    /* All the methods used for parsing the json returns of http requests
     * All return workable class types
     */
    public static Store[] parseIntoStores(String json) {
        return null;
    }

    /* Method to parse the items into a list from a JSON String
       Returns null if the list of stores is empty/information is incomplete
     */
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
            return items;
        } catch (JSONException e) {
            Log.e(TAG, "Could not find list of stores");
            return null;
        }
    }


}