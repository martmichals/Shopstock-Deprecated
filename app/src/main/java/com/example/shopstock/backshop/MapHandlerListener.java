package com.example.shopstock.backshop;

import java.util.ArrayList;

public interface MapHandlerListener {
    void onSuccess();
    void onFailure(boolean isConnectionError);
}
