package com.example.shopstock;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class InfinityScreen extends Fragment {

    private static int index = 0;
    private static final String[] questions = {"Are eggs in stock?", "Is bread in stock?", "Is there any milk in stock?"};

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_infinity_screen, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (index >= questions.length) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }

        ((TextView) view.findViewById(R.id.prompt)).setText(questions[index++]);

        // Setting the listeners for the buttons in the infinity fragment
        view.findViewById(R.id.yes_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: Implement method to grab a new question

                NavHostFragment.findNavController(InfinityScreen.this)
                        .navigate(R.id.action_loop_to_new_q);
            }
        });

        view.findViewById(R.id.no_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement method to grab a new question

                NavHostFragment.findNavController(InfinityScreen.this)
                        .navigate(R.id.action_loop_to_new_q);
            }
        });

        view.findViewById(R.id.stop_questions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
