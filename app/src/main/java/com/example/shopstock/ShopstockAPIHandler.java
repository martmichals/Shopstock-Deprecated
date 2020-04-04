package com.example.shopstock;

// Used for JSON parsing and launching HTTPS requests
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

// Other required classes
import android.content.Context;
import android.util.Log;

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
        String methodRequest = BASE_URL + "get_stores_in_area?lat_1="
                + String.valueOf(bottom_left_coordinate[0]) + "&lat_2="
                + String.valueOf(top_right_coordinate[0]) + "&long_1="
                + String.valueOf(bottom_left_coordinate[1]) + "&long_2="
                + String.valueOf(top_right_coordinate[1]);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, methodRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Code that runs on request success
                        listener.onSuccess();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Code that runs on request failure
                        listener.onFailure(error);
                    }
                });
        queue.add(stringRequest);
    }
}
