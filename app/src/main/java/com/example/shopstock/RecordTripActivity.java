package com.example.shopstock;

import android.os.Bundle;

import com.example.shopstock.backshop.Store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RecordTripActivity extends AppCompatActivity {
    private Store selectedStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_trip);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // TODO: Put relevant API methods here

}
