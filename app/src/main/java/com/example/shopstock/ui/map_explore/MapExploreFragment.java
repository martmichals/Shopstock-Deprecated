package com.example.shopstock.ui.map_explore;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.shopstock.R;
import com.example.shopstock.RecordTripActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapExploreFragment extends Fragment {

    private MapExploreViewModel mapExploreViewModel;
    private MapView map;
    private boolean locationPerms = false;

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
        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (!locationPerms) {
                   return;
                }

                googleMap.setMyLocationEnabled(true);

                LatLng[] pins = {new LatLng(41.894996, -87.629224), new LatLng(41.892316, -87.628676), new LatLng(41.893514, -87.626310)};
                final String[] storeNames = {"Whole Foods", "Jewel Osco", "Trader Joes"};
                String[] storeStatuses = {"Out of eggs and bread", "In Stock", "Out of toilet paper"};
                LatLng center = new LatLng(0, 0);
                for (int i = 0; i < pins.length; i++) {
                    final String name = storeNames[i];
                    googleMap.addMarker(new MarkerOptions().position(pins[i]).title(storeNames[i]).snippet(storeStatuses[i]));
                    googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Intent intent = new Intent(getActivity(), RecordTripActivity.class);
                            intent.putExtra("storeName",  marker.getTitle());
                            startActivity(intent);
                        }
                    });
                    center = new LatLng(center.latitude + (pins[i].latitude / pins.length), center.longitude + (pins[i].longitude / pins.length));
                }

                CameraPosition cameraPosition = new CameraPosition.Builder().target(center).zoom(16).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return root;
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
}
