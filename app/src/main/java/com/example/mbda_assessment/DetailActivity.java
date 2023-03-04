package com.example.mbda_assessment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    Item item;
    LocationManager locationManager;
    LocationListener locationListener;

    DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // retrieve Item data
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");

        // Load Detail Fragment
        detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("item", item);
        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .commit();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // TODO: show location
                //calculateDistance();
                if(item != null) {
                    String distance = calculateDistance(item.latitude, item.longitude, location.getLatitude(), location.getLongitude());
                    detailFragment.distanceFromCountry.setText("You are " + distance + " kilometers away from " + item.name + "!");
                }
            }

            @Override
            public void onProviderDisabled(String provider) {
                // Placeholder implementation
            }

            @Override
            public void onProviderEnabled(String provider) {
                // Placeholder implementation
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Placeholder implementation
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupLocationButton();
    }

    public void setupLocationButton() {
        Button locationButton = findViewById(R.id.locationButton);
        Log.d("myTag", String.valueOf(locationButton));

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    // Permission is granted, request a single location update
                    locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public String calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in kilometers

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to km

        // Format the distance as a string with two decimal places
        DecimalFormat df = new DecimalFormat("#.##");

        return df.format(distance);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}