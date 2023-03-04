package com.example.mbda_assessment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class DetailActivity extends AppCompatActivity {
    private Item item;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private DetailFragment detailFragment;
    private ActivityResultLauncher<Intent> getContent;
    private static final int REQUEST_CODE_PICK_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Retrieve item data from Intent
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

        // Initialize Location Manager and Listener
        initLocation();
        initContact();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // set up buttons once the view is loaded
        setupLocationButton();
        setupContactButton();
    }

    private void initLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if(item != null) {
                    String distance = MyUtils.calculateDistance(item.latitude, item.longitude, location.getLatitude(), location.getLongitude());

                    String distanceFromCountryString = getString(R.string.distance_from_country, distance, item.name);
                    detailFragment.distanceFromCountry.setText(distanceFromCountryString);
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
                                showContactInvitation(name);
                            }
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                });
    }

    public void setupLocationButton() {
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

    public void setupContactButton() {
        Button getContactButton = findViewById(R.id.getContactButton);
        getContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContacts();
            }
        });
    }

    private void openContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        getContent.launch(intent);
    }

    private void showContactInvitation(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invitation sent");
        builder.setMessage("You just invited " + name + " to join you on your journey!");
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Do nothing
        });
        builder.show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}