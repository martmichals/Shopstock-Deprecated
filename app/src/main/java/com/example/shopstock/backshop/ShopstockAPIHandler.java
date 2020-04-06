package com.example.shopstock.backshop;

// Imports
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

import java.util.Arrays;
import java.util.HashMap;

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

        Log.e(TAG, methodRequest);
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
                        chainListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error instanceof TimeoutError || error instanceof NoConnectionError){
                           chainListener.onFailure(true);
                        }else{
                           Log.e(TAG, "Error updating item categories from the API");
                           chainListener.onFailure(false);
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
                        categoryListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error instanceof TimeoutError || error instanceof NoConnectionError){
                            categoryListener.onFailure(true);
                        }else{
                            Log.e(TAG, "Error updating the store chains from the API");
                            categoryListener.onFailure(false);
                        }
                    }
                });
        queue.add(categoryRequest);
    }


    // All methods for parsing json into workable data types

    /* Method to parse the stores into a list from a JSON String
       Returns null if the list of stores is empty/information is incomplete
     */
    public static Store[] parseIntoStores(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray("stores"); // stores organized into array in JSON
            Store[] stores = new Store[arr.length()];
            for(int i = 0; i < arr.length(); i++) {
                JSONObject store = arr.getJSONObject(i);

                // Getting Store constructor arguments
                int storeID = store.getInt("id");
                String storeName = store.getString("name");
                String storeAddress = store.getString("address");
                double[] coordinates = new double[2];
                coordinates[0] = store.getDouble("lat");
                coordinates[1] = store.getDouble("long");

                Log.e(TAG, "This method is not yet complete. Some Store fields are arbitrary");
                //***The following will all need to be adjusted.
                int[] categoryIDs = new int[1]; //
                int chainID = store.getInt("store-chain");
                HashMap<String, String> map = new HashMap<String, String>();
                stores[i] = new Store(storeID, storeName, storeAddress, coordinates, categoryIDs, chainID, map);
            }
            return stores;
        } catch (JSONException e) {
            Log.e(TAG, "Could not parse json string into store list");
            return null;
        }
    }

    /* Method to parse the categories into a list from a JSON String
       Returns null if the list of categories is empty/information is incomplete
     */
    // AT PRESENT: void return. Will simply print category information until method use-case defined.
    public static void parseIntoCategories(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONObject("categories").names();
            // temp printing messages
            Log.i(TAG, "For now, parseIntoCategories only prints all the values it computes.");
            Log.i(TAG, "Store Categories \n");

            for (int i = 0; i < arr.length(); i++) {
                Log.i(TAG, Integer.toString(i));
                String category_id = arr.getString(i);
                JSONObject category = obj.getJSONObject("categories").getJSONObject(category_id);

                // Contains Category ID, Category name, List of Item categories
                int catID = Integer.parseInt(category_id); // Final value: Category ID
                String name = category.getString("name"); // Final value: Category Name
                String itemCats = category.getString("item-categories");

                // Parsing comma delimited list of categories
                String[] cat_as_string = itemCats.split("\\s*,\\s*");
                int[] categories = new int[cat_as_string.length]; // Final value: list of item categories
                for(int j = 0; i < categories.length; i++) { // converting to integer array
                    categories[i] = Integer.parseInt(cat_as_string[j]);
                }
                // Temporary printing. This is where all the information would need to be unified
                String category_print = catID + ": " + name + ", Item Categories " + Arrays.toString(categories) + "\n";
                Log.i(TAG, category_print);

            }
        } catch (JSONException e) {
            Log.e(TAG, "Could not parse json string of store categories");
        }
    }

    /* Method to parse the chains into a list from a JSON String
       Returns null if the list of chains is empty/information is incomplete
     */
    public static void parseIntoChains(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONObject("chains").names();

            // temp info printing
            Log.i(TAG, "For now, parseIntoChains only prints a few store chains it finds.");

            for (int i = 0; i < arr.length(); i++) {
                String chain_ID = arr.getString(i);
                JSONObject chain = obj.getJSONObject("chains").getJSONObject(chain_ID);

                // Contains Chain ID and Chain name
                int chainID = Integer.parseInt(chain_ID); // Final value: Chain ID
                String name = chain.getString("name"); // Final value: Category Name

                // Temporary printing. This is where all the information would need to be unified.
                if(i==0 || i==2) {
                    String category_print = chain_ID + ": " + name + "\n";
                    Log.i(TAG, category_print);
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, "Could not parse json string of store chains");
        }

    }

    /* Method to parse the items into a list from a JSON String
       Returns null if the list of items is empty/information is incomplete
     */
    public static Item[] parseIntoItems(String json) {

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONObject("items").names();
            Item[] items = new Item[arr.length()]; // Return array
            for (int i = 0; i < arr.length(); i++) {
                String item_id = arr.getString(i);
                JSONObject item = obj.getJSONObject("items").getJSONObject(item_id);

                int itemID = Integer.parseInt(item_id);
                String itemName = item.getString("name");
                int categoryID = item.getInt("item-category");
                // Confidence will be uninitialized in this context
                items[i] = new Item(itemID, itemName, categoryID);
            }
            return items;
        } catch (JSONException e) {
            Log.e(TAG, "Could not parse json string into item list");
            return null;
        }
    }

}
