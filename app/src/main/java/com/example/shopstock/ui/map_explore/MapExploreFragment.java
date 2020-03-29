package com.example.shopstock.ui.map_explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.shopstock.R;

public class MapExploreFragment extends Fragment {

    private MapExploreViewModel mapExploreViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mapExploreViewModel = ViewModelProviders.of(this).get(MapExploreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map_explore, container, false);


        return root;
    }
}
