package com.example.andrewvu.rowdyhackapp;

import android.media.Image;

public class MyProduct {
    String product;
    String price;
    Image image; // Bitmap

    public MyProduct(){

    }

    public MyProduct(String product, String price) {
        this.product = product;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public String getPrice() {
        return price;
    }
}
