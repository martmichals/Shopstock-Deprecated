package com.example.shopstock.backshop;

public interface ShopstockListener {
    void onSuccess(String json);
    void onFailure(boolean isConnectionError);
}
