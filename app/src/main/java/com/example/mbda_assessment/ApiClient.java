package com.example.mbda_assessment;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiClient {

    private static final String BASE_URL = "https://restcountries.com/v3.1/";

    private static ApiClient instance;
    private RequestQueue requestQueue;

    private ApiClient(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized ApiClient getInstance(Context context) {
        if (instance == null) {
            instance = new ApiClient(context);
        }
        return instance;
    }

    public void getEuropeCountries(Response.Listener<List<Item>> successListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "region/europe";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Item> items = extractData(response);
                successListener.onResponse(items);
            }
        }, errorListener);
        requestQueue.add(request);
    }

    private List<Item> extractData(JSONArray response) {
        List<Item> data = new ArrayList<>();
        try {
            JSONArray countries = response;
            for (int i = 0; i < countries.length(); i++) {
                JSONObject country = countries.getJSONObject(i);
                String countryName = country.getJSONObject("name").getString("common");
                String countryDesc = country.getJSONObject("name").getString("official");
                String countryFlag = country.getJSONObject("flags").getString("png");
                double latitude = country.getJSONArray("latlng").getDouble(0);
                double longitude = country.getJSONArray("latlng").getDouble(1);
                data.add(new Item(i, countryName, countryDesc, countryFlag, latitude, longitude));
            }
        } catch (JSONException e) {
            Log.d("API ERROR", "JSON Error: " + e.getMessage());
        }
        return data;
    }
}
