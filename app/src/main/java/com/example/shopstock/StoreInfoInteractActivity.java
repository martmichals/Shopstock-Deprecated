package com.example.shopstock;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shopstock.backshop.Store;

public class StoreInfoInteractActivity extends AppCompatActivity {
    private Store selectedStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info_interact);
    }

    // TODO: Put relevant API methods here

}
