package com.example.shopstock.ui.mapexplore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapExploreViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MapExploreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}