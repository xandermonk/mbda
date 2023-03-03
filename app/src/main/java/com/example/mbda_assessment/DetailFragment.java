package com.example.mbda_assessment;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailFragment extends Fragment {
    ImageView bannerImage;
    TextView nameTextView;
    TextView descriptionTextView;
    Item item;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Retrieve data for clicked item from bundle
        Bundle bundle = getArguments();
        String name = bundle.getString("name");
        String description = bundle.getString("description");
        // TODO: properly get image
        int coverImage = bundle.getInt("coverImage");

        // Display data in Detail Fragment layout
        nameTextView = view.findViewById(R.id.countryName);
        descriptionTextView = view.findViewById(R.id.countryDescription);
        bannerImage = view.findViewById(R.id.countryFlag);


        nameTextView.setText(name);
        descriptionTextView.setText(description);
        bannerImage.setImageResource(coverImage);

        return view;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}