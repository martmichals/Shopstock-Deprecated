package com.example.shopstock;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.shopstock.backshop.Item;

import java.util.ArrayList;

public class ViewStoreInfoFragment extends Fragment implements SearchView.OnQueryTextListener {
    private View view;

    private ViewStoreInformationViewModel mViewModel;
    private final Item[] items = {
        new Item(1, "bread", 1, -1),
        new Item(1, "bread", 1, -0.9),
        new Item(1, "bread", 1, -0.8),
        new Item(1, "bread", 1, -0.7),
        new Item(1, "bread", 1, -0.6),
        new Item(1, "bread", 1, -0.5),
        new Item(1, "bread", 1, -0.4),
        new Item(1, "bread", 1, -0.3),
        new Item(1, "bread", 1, -0.2),
        new Item(1, "bread", 1, -0.1),
        new Item(1, "bread", 1, 0),
        new Item(1, "bread", 1, 0.1),
        new Item(1, "bread", 1, 0.2),
        new Item(1, "bread", 1, 0.3),
        new Item(1, "bread", 1, 0.4),
        new Item(1, "bread", 1, 0.5),
        new Item(1, "bread", 1, 0.6),
        new Item(1, "bread", 1, 0.7),
        new Item(1, "bread", 1, 0.8),
        new Item(1, "bread", 1, 0.9),
        new Item(1, "bread", 1, 1)
    };

    public static ViewStoreInfoFragment newInstance() {
        return new ViewStoreInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_store_info_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.go_to_record_meta_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ViewStoreInfoFragment.this)
                        .navigate((R.id.action_ViewStoreInfoFragment_to_RecordMetaFragment));
            }
        });

        this.view = view;

        SearchView searchView = (SearchView) view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);

        updateItemList();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ViewStoreInformationViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        updateItemList();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        updateItemList();
        return false;
    }

    private void updateItemList() {
        SearchView searchView = (SearchView) view.findViewById(R.id.search_bar);
        ArrayList<Item> itemList = new ArrayList<Item>();
        for (Item item : items) {
            if (item.getItemName().toLowerCase().contains(searchView.getQuery().toString().toLowerCase())) {
                itemList.add(item);
            }
        }

        Log.d("DEBUG", searchView.getQuery().toString());

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.scroll_layout);
        layout.removeAllViews();
        for (Item item : itemList) {
            LinearLayout subLayout = new LinearLayout(getContext());
            subLayout.setPadding(0,16, 0,16);
            subLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            layout.addView(subLayout);

            TextView name = new TextView(getContext());
            name.setText(item.getItemName());
            name.setTextSize(24);
            name.setTextColor(getResources().getColor(R.color.colorAccent));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            name.setLayoutParams(params);
            subLayout.addView(name);

            CardView cardView = new CardView(getContext());
            cardView.setRadius(100);
            subLayout.addView(cardView);

            TextView progress = new TextView(getContext());
            double intrepConst = (item.getConfidence() + 1) / 2;
            float[] hsv = {
                    (float) item.getConfidence() * 60f + 60f,
                    0.4f,
                    0.8f,
            };
            progress.setBackgroundColor(Color.HSVToColor(hsv));
            progress.setTextColor(getResources().getColor(R.color.colorBackground));
            String[] prob = {
                    "Out of Stock",
                    "Likely Out of Stock",
                    "Unknown",
                    "Likely In Stock",
                    "In Stock"
            };
            progress.setText(prob[Math.min((int)(((item.getConfidence() + 1) / 2) * prob.length), prob.length - 1)]);
            progress.setTextSize(24);
            progress.setPadding(24, 0, 24,0);
            cardView.addView(progress);
        }
    }
}
