package com.example.mbda_assessment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OverviewFragment extends Fragment {

    interface OnClickListener {
        void onItemSelected(View view);
    }
    OnClickListener listener;

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        List<Item> items = new ArrayList<>();


        for (int i = 0; i < 20; i++) {
            items.add(new Item(i, "item"+i, "description"+i));
        }

        ItemAdapter adapter = new ItemAdapter(items);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(onItemClickListener);

        return view;
    }

    private View.OnClickListener onItemClickListener = view -> {
        listener.onItemSelected(view);
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listener = (MainActivity) getActivity();
    }
}