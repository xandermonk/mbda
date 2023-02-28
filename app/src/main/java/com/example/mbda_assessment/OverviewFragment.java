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

        List<Item> demoList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            demoList.add(new Item(i, "name "+i, "desc " +i));
        }


        adapter = new ItemAdapter(demoList);
        adapter.setOnItemClickListener(onItemClickListener);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
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
        this.itemList.clear();
        this.itemList = itemList;
        // TODO: remove log
        Log.d("myTag", String.valueOf(itemList.size()));
        adapter.notifyDataSetChanged();
    }
}