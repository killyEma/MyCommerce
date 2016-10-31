package com.ewikse.mycommerce.services;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.ewikse.mycommerce.database.DataBase;
import com.ewikse.mycommerce.model.Product;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl {
    private static final String EXTENSION_PICTURE = ".jpg";
    private static ProductServiceImpl service;
    private static Context context;
    private static final String FOLDER_IMAGE_PRODUCTS = "imageProducts";
    private ContextWrapper cw;
    private File directory;

    public static ProductServiceImpl getInstance(Context context) {
        ProductServiceImpl.context = context;
        return service == null ? new ProductServiceImpl() : service;
    }

    public boolean saveProduct(Product product, Bitmap imageBitmap) {
        saveToInternalStorage(product.getCode(), imageBitmap);
        product.setPicture(product.getCode());
        //TODO: this call should be in a AsyncTask
        return DataBase.productDAO.addProduct(product);
    }

    public List<Product> getProducts() {
        return DataBase.productDAO.fetchAllProducts();
    }

    public Product getProductByCode(String code) {
        return DataBase.productDAO.getProductByCode(code);
    }

    public boolean deleteProduct(String code) {
        deleteStoredPicture(retrievePictureNameByCode(code));
        DataBase.productDAO.deleteProduct(code);
        return true;
    }

    public Bitmap retrievePictureProduct(String picture) {
        Bitmap bitmap = null;
        try {
            File f = new File(loadDirectory(), picture + ProductServiceImpl.EXTENSION_PICTURE);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            Log.w(context.getPackageCodePath() , e.getMessage());
        }
        return bitmap;
    }

    public List<Bitmap> retrievePicturesForProducts(List<Product> products) {
        return new ArrayList<>(Collections2.transform(products, new Function<Product, Bitmap>() {
            @Override
            public Bitmap apply(Product product) {
                return retrievePictureProduct(product.getPicture());
            }
        }));
    }

    private String retrievePictureNameByCode(String code) {
        return DataBase.productDAO.getPictureProductByCode(code);
    }

    private boolean deleteStoredPicture(String namePicture) {
        return new File(loadDirectory(), namePicture + ProductServiceImpl.EXTENSION_PICTURE).delete();
    }

    private void saveToInternalStorage(String code, Bitmap bitmapImage) {
        File myPath = new File(loadDirectory(), code + ProductServiceImpl.EXTENSION_PICTURE);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            Log.w(context.getPackageCodePath(), e.getMessage());
        } finally {
            try {
                if (fos != null ) { fos.close(); }
            } catch (IOException e) {
                Log.w(context.getPackageCodePath() , e.getMessage());
            }
        }
    }

    private File loadDirectory() {
        if (cw == null && directory == null) {
            cw = new ContextWrapper(ProductServiceImpl.context);
            directory = cw.getDir(FOLDER_IMAGE_PRODUCTS, Context.MODE_PRIVATE);
        }
        return directory;
    }
}
