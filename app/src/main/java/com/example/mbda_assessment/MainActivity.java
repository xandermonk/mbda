package com.example.mbda_assessment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnClickListener {

    List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchData();
    }

    public void onItemSelected(View view) {

        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();

        DetailFragment detailFragment = new DetailFragment();
        Log.d("myTag", String.valueOf(itemList.get(position)));
        detailFragment.setItem(itemList.get(position));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.overviewFragment, detailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    void fetchData() {
        ApiClient apiClient = ApiClient.getInstance(this);

        apiClient.getEuropeCountries(items -> {
            itemList = items;

            OverviewFragment overviewFragment = (OverviewFragment) getSupportFragmentManager().findFragmentById(R.id.overviewFragment);
            overviewFragment.setItems(itemList);
        }, error -> Log.d("API ERROR", error.toString()));
    }
}