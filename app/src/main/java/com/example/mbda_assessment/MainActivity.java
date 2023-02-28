package com.example.mbda_assessment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnClickListener {

    List<Item> itemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            URL url = new URL("https://restcountries.com/v3.1/region/europe");
            useVolley(url);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

    public void onItemSelected(View view) {

        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();

        // TODO: set item
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        detailFragment.setItem(null);
    }

    void useVolley(URL url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url.toString(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // TODO: extract data
                itemList = extractData(response);

                // TODO: populate recyclerview
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    itemList.forEach(item -> {

                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("API ERROR", error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }

    private List<Item> extractData(JSONArray response) {
        List<Item> data = new ArrayList<>();
        try {
            JSONArray countries = response;
            for (int i = 0; i < countries.length(); i++) {
                JSONObject country = countries.getJSONObject(i);
                String countryName = country.getJSONObject("name").getString("common");
                String countryDesc = country.getJSONObject("name").getString("official");
                data.add(new Item(i, countryName, countryDesc));
            }
        } catch (JSONException e) {
            Log.d("API ERROR", "JSON Error: " + e.getMessage());
        }
        return data;
    }
}