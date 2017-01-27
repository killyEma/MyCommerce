package com.ewikse.mycommerce.interfaces;

import android.content.Context;
import android.graphics.Bitmap;

import com.ewikse.mycommerce.model.Item;

import java.io.Serializable;
import java.util.List;

public interface NewProductView {
    void finish();
    List<Bitmap> retrieveProductImages();
    void loadImage();
    String [] retrieveDataInputs();
    Context getApplicationContext();
    void retrieveImageFromGallery();
    void callEmailService();
    void setResultAction(Serializable item);
}
