package com.example.mbda_assessment;

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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailFragment extends Fragment {
    ImageView bannerImage;
    TextView itemName;
    TextView itemDesc;
    Item item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("myTag", "Building view!");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        bannerImage = view.findViewById(R.id.countryFlag);

        itemName = view.findViewById(R.id.countryName);
        itemName.setText(item.name);

        itemDesc = view.findViewById(R.id.countryDescription);
        itemDesc.setText(item.description);

        return view;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}