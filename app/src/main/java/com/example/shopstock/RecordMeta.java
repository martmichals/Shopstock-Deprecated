package com.example.shopstock;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class RecordMeta extends Fragment {

    private static final String[] ITEMS = {"Bread", "Eggs", "Toilet Paper", "Milk"};

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record_meta, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = getActivity().getIntent();
        String storeName = intent.getStringExtra("storeName");
        if (storeName != null) {
            TextView storeText = (TextView) view.findViewById(R.id.storeName);
            String title = "Store: " + storeName;
            storeText.setText(title);
        }

        // Create a listener on the button to go to the infinity fragment
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RecordMeta.this)
                        .navigate(R.id.action_RecordDataFragment_to_InfinityFragment);
            }
        });

        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        datePicker.setMinDate((long)946684800 * 1000);
        datePicker.setMaxDate(Calendar.getInstance().getTime().getTime());

        view.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout itemContainer = (LinearLayout) ((View) view.getParent().getParent()).findViewById(R.id.itemContainer);

                AutoCompleteTextView actv = new AutoCompleteTextView(getContext());
                actv.setTextColor(getResources().getColor(R.color.colorAccent));
                actv.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
                actv.setThreshold(0);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, ITEMS);
                actv.setAdapter(adapter);

                itemContainer.addView(actv);
            }
        });

    }
}
