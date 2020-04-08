package com.example.shopstock.ui.map_explore;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.shopstock.R;
import com.example.shopstock.backshop.MapHandler;
import com.example.shopstock.backshop.MapHandlerListener;
import com.example.shopstock.backshop.Store;
import com.example.shopstock.StoreInfoInteractActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapExploreFragment extends Fragment implements  OnMapReadyCallback, GoogleMap.OnCameraIdleListener {
    private static final String TAG = "MapExploreFragment";

    private MapExploreViewModel mapExploreViewModel;
    private MapView map;
    private GoogleMap googleMap;
    private boolean locationPerms = false;
    private MapHandler mapHandler;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mapExploreViewModel = ViewModelProviders.of(this).get(MapExploreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map_explore, container, false);

        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        requestPermissions(permissions, 0);

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            locationPerms = true;
        }
        map = (MapView) root.findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);

        map.onResume();
        map.getMapAsync(this);

        mapHandler = new MapHandler();

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (!locationPerms) {
            return;
        }

        this.googleMap = googleMap;
        googleMap.setOnCameraIdleListener(this);
        googleMap.setMyLocationEnabled(true);

        LatLng[] pins = {new LatLng(41.894996, -87.629224), new LatLng(41.892316, -87.628676), new LatLng(41.893514, -87.626310)};
        final String[] storeNames = {"Whole Foods", "Jewel Osco", "Trader Joes"};
        String[] storeStatuses = {"Out of eggs and bread", "In Stock", "Out of toilet paper"};
        LatLng center = new LatLng(0, 0);
        for (int i = 0; i < pins.length; i++) {
            final String name = storeNames[i];
            //googleMap.addMarker(new MarkerOptions().position(pins[i]).title(storeNames[i]).snippet(storeStatuses[i]));
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(getActivity(), StoreInfoInteractActivity.class);
                    intent.putExtra("storeName",  marker.getTitle());
                    startActivity(intent);
                }
            });
            center = new LatLng(center.latitude + (pins[i].latitude / pins.length), center.longitude + (pins[i].longitude / pins.length));
        }

        CameraPosition cameraPosition = new CameraPosition.Builder().target(center).zoom(16).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }

    @Override
    public void onCameraIdle() {
        Projection projection = googleMap.getProjection();

        LatLng[] corners = {
                projection.fromScreenLocation(new Point(0, 0)),
                projection.fromScreenLocation(new Point(map.getWidth(), 0)),
                projection.fromScreenLocation(new Point(map.getWidth(), map.getHeight())),
                projection.fromScreenLocation(new Point(0, map.getHeight()))
        };

        LatLng sw = corners[0];
        LatLng ne = corners[0];
        for (int i = 1; i < corners.length; i++) {
            LatLng point = corners[i];
            sw = new LatLng(Math.min(sw.latitude, point.latitude), Math.min(sw.longitude, point.longitude));
            ne = new LatLng(Math.max(ne.latitude, point.latitude), Math.max(ne.longitude, point.longitude));
        }
        Log.d(TAG, sw.toString());
        Log.d(TAG, ne.toString());

        double[] bottom_left = {sw.latitude, sw.longitude};
        double[] top_right = {ne.latitude, ne.longitude};
        mapHandler.updateScreenArea(bottom_left, top_right, getContext(), new MapHandlerListener() {
            @Override
            public void onSuccess() {
               ArrayList<Store> storesOnScreen = mapHandler.getStoresOnScreen();
               for(int i = 0; i < storesOnScreen.size(); i++){
                   Store store = storesOnScreen.get(i);
                   LatLng location = new LatLng(store.getCoordinates()[0], store.getCoordinates()[1]);
                   googleMap.addMarker(new MarkerOptions().position(location).title(store.getStoreName()).snippet(""));
                   Log.d(TAG, storesOnScreen.get(i).toString());

               }
            }

            @Override
            public void onFailure(boolean isConnectionError) {
                if(isConnectionError)
                    Log.e(TAG, "Failure updating the stores. No connection.");
                else
                    Log.e(TAG, "App error. Failure updating stores on map.");
            }
        });
    }
}