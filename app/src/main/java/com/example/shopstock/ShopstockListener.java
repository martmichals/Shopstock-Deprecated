package com.example.shopstock;

import com.android.volley.VolleyError;

public interface ShopstockListener {
    void onSuccess(String json);
    void onFailure(VolleyError error);
}
