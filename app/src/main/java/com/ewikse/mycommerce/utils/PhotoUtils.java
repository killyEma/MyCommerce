package com.ewikse.mycommerce.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PhotoUtils {

    private ByteArrayInputStream imageStream;
    private BitmapFactory.Options options;
    private Bitmap bitmap;

    public PhotoUtils() {
        options = new BitmapFactory.Options();
        options.inSampleSize = 4;
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public Bitmap getPhoto(byte[] image) {
        imageStream = new ByteArrayInputStream(image);
        bitmap = BitmapFactory.decodeStream(imageStream, null, options);
        return Bitmap.createScaledBitmap(bitmap, 100 , 100 , true);
    }
}
