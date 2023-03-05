package com.example.mbda_assessment;

import java.io.Serializable;

public class Item implements Serializable {
    final String name;
    final String description;
    final String imagePath;
    final double latitude;
    final double longitude;

    Item (String name, String description, String imagePath, double lat, double lng) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.latitude = lat;
        this.longitude = lng;
    }
}
