package com.example.mbda_assessment;

import android.os.Bundle;
import android.util.Log;
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
    List<Item> itemList = new ArrayList<>();
    ItemAdapter adapter = new ItemAdapter(itemList);
    RecyclerView recyclerView;

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

        adapter = new ItemAdapter(itemList);
        adapter.setOnItemClickListener(onItemClickListener);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

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

    // TODO: improve code
    public void setItems(List<Item> itemList) {
        adapter = new ItemAdapter(itemList);
        adapter.setOnItemClickListener(onItemClickListener);

        recyclerView.setAdapter(adapter);
    }
}