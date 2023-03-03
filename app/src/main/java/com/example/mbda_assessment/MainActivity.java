package com.example.mbda_assessment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Item> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup toolbar
        setupToolbar();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create list of items
        fetchData();

        List<Item> demoList = new ArrayList<>();
        demoList.add(new Item(1, "Name", "Desc", "imagePath"));
        demoList.add(new Item(2, "Name", "Desc", "imagePath"));

        itemAdapter = new ItemAdapter(demoList, this);
        recyclerView.setAdapter(itemAdapter);

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    void fetchData() {
        ApiClient apiClient = ApiClient.getInstance(this);

        apiClient.getEuropeCountries(items -> {
            itemList = items;
        }, error -> Log.d("API ERROR", error.toString()));
    }
}