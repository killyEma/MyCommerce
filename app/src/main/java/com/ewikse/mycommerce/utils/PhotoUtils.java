package com.ewikse.mycommerce.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;

import static android.provider.MediaStore.MediaColumns.DATA;
import static com.ewikse.mycommerce.activities.NewProductActivity.SAVE_ACTION;

public class PhotoUtils {
    private final static int PHOTO_SIZE_ICON = 48;

    private ContentResolver contentResolver;
    private Context context;
    private WindowManager windowManager;

    public PhotoUtils(ContentResolver contentResolver, Context context, WindowManager windowManager) {
        this.contentResolver = contentResolver;
        this.context = context;
        this.windowManager = windowManager;
    }

    public Bitmap reloadImageViewProduct(Uri selectedImage, int action) {
        String[] filePathColumn = {DATA};

        Cursor cursor = contentResolver.query(selectedImage,
                filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.close();
        }

        Bitmap bitmap = null;
        try {
            bitmap = getBitmapFromUri(selectedImage, action);
        } catch (IOException e) {
            Log.w(context.getPackageName(), e.getMessage());
        }
        return bitmap;
    }

    public Bitmap getBitmapFromUri(Uri uri, int action) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        int reqWidth, reqHeight;
        if (action == SAVE_ACTION) {
            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(metrics);
            int height = metrics.heightPixels;
            int width = metrics.widthPixels;
            reqHeight = height / 2;
            reqWidth = width / 2;
        } else {
            reqHeight = PHOTO_SIZE_ICON;
            reqWidth = PHOTO_SIZE_ICON;
        }
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        if (inputStream != null) {
            inputStream.close();
        }
        inputStream = context.getContentResolver().openInputStream(uri);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
