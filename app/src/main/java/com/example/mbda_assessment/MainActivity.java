package com.example.mbda_assessment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

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

        // TODO: set item
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        detailFragment.setItem(null);
    }


    void fetchData() {
        ApiClient apiClient = ApiClient.getInstance(this);

        apiClient.getEuropeCountries(new Response.Listener<List<Item>>() {
            @Override
            public void onResponse(List<Item> items) {
                itemList = items;

                OverviewFragment overviewFragment = (OverviewFragment) getSupportFragmentManager().findFragmentById(R.id.overviewFragment);
                overviewFragment.setItems(itemList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("API ERROR", error.toString());
            }
        });
    }
}