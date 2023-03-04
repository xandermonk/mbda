package com.example.mbda_assessment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;


public class DetailActivity extends AppCompatActivity {
    private Item item;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private ActivityResultLauncher<Intent> getContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Retrieve item data from Intent
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");

        // Load Detail Fragment
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .commit();

        // Initialize Location Manager and Listener
        initLocation();
        initContact();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // set up buttons once the view is loaded
        setupGetLocationButton();
        setupContactButton();
        setupShowLocationButton();
    }

    private void initLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if(item != null) {
                    String distance = MyUtils.calculateDistance(item.latitude, item.longitude, location.getLatitude(), location.getLongitude());

                    MyUtils.showDialog("Distance calculated!", "You are " + distance + " kilometers away from " + item.name + "!", DetailActivity.this);
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

    private void initContact() {
        getContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Uri contactUri = data.getData();
                        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);

                        String name = null;

                        if (cursor != null && cursor.moveToFirst()) {
                            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
                            if (nameIndex != -1) {
                                name = cursor.getString(nameIndex);
                                MyUtils.showDialog("Invitation sent!", "You just invited " + name + " to join you on your journey!", this);
                            }
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                });
    }

    private void setupGetLocationButton() {
        Button locationButton = findViewById(R.id.locationButton);

        locationButton.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted, request it
                ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                // Permission is granted, request a single location update
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
            }
        });
    }

    private void setupContactButton() {
        Button getContactButton = findViewById(R.id.getContactButton);
        getContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContacts();
            }
        });
    }

    private void setupShowLocationButton() {
        Button showLocationButton = findViewById(R.id.showLocationButton);
        showLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationOnMap(item.latitude, item.longitude);
            }
        });
    }

    private void openContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        getContent.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showLocationOnMap(double latitude, double longitude) {
        // Create a Uri from the latitude and longitude
        Uri locationUri = Uri.parse("geo:" + latitude + "," + longitude);

        // Create an Intent to open Google Maps with the location
        Intent intent = new Intent(Intent.ACTION_VIEW, locationUri);
        intent.setPackage("com.google.android.apps.maps");

        // Verify that the user has Google Maps installed
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;

        // If Google Maps is installed, open the location in the app
        if (isIntentSafe) {
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}