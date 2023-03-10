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
    TextView nameTextView;
    TextView descriptionTextView;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Retrieve item data
        Bundle args = getArguments();
        if (args != null) {
            Item item = (Item) args.getSerializable("item");

            // Display data in Detail Fragment layout
            nameTextView = view.findViewById(R.id.countryName);
            descriptionTextView = view.findViewById(R.id.countryDescription);
            bannerImage = view.findViewById(R.id.countryFlag);


            nameTextView.setText(item.name);
            descriptionTextView.setText(item.description);
            MyUtils.loadImageFromUrl(item.imagePath, bannerImage);
        }


        return view;
    }


}