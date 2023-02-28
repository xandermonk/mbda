package com.example.mbda_assessment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {
    ImageView bannerImage;
    TextView itemName;
    TextView itemDesc;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        bannerImage = view.findViewById(R.id.countryFlag);
        itemName = view.findViewById(R.id.countryName);
        itemDesc = view.findViewById(R.id.countryDescription);

        return view;
    }

    public void setItem(Item item) {
        itemName.setText(item.name);
        itemDesc.setText(item.description);

        // TODO: remove peru
        bannerImage.setImageResource(R.drawable.flag_peru);
    }
}