package com.ewikse.mycommerce.interfaces;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

public interface NewProductView {
    void finish();
    List<Bitmap> retrieveProductImages();
    void loadImage();
    String [] retrieveTextInputs();
    Context getApplicationContext();
    void retrieveImageFromGallery();
    void callEmailService();
}
