package com.example.shopstock;

import com.android.volley.VolleyError;

public interface ShopstockListener {
    void onSuccess();
    void onFailure(VolleyError error);
}
