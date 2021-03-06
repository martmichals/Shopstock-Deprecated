package com.example.shopstock;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_map_explore, R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        /*
         *  All code below is part of the dev playground
         */
        // TODO : Add conditional based on auth status

        // goToAuthScreen();

        // goRecordTripActivity();


    }

    private void goToAuthScreen(){
        // Pop the current activity off of the stack
        this.finish();

        Intent intent = new Intent(this, AuthStartActivity.class);
        startActivity(intent);
    }

    private void goRecordTripActivity(){
        Intent intent = new Intent(this,  StoreInfoInteractActivity.class);
        startActivity(intent);
    }

}
