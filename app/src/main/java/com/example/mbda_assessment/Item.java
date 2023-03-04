package com.example.mbda_assessment;

import java.io.Serializable;

public class Item implements Serializable {

    int id;
    String name;
    String description;
    String imagePath;
    double latitude;
    double longitude;

    Item (int id, String name, String description, String imagePath, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.latitude = lat;
        this.longitude = lng;
    }
}
