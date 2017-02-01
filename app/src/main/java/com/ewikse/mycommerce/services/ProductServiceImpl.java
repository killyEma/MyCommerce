package com.ewikse.mycommerce.services;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.util.Log;

import com.ewikse.mycommerce.model.Item;
import com.ewikse.mycommerce.model.Product;
import com.google.common.base.Function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.graphics.Bitmap.CompressFormat.JPEG;
import static android.graphics.BitmapFactory.decodeStream;
import static com.ewikse.mycommerce.database.DataBase.productDAO;
import static com.ewikse.mycommerce.model.Product.ICON;
import static com.google.common.collect.Collections2.transform;

public class ProductServiceImpl {
    private static final String EXTENSION_PICTURE = ".jpg";
    private static final String FOLDER_IMAGE_PRODUCTS = "imageProducts";

    private static HashMap<String, Bitmap> cacheIcon;
    private static ProductServiceImpl service;
    private static Context context;
    private ContextWrapper cw;
    private File directory;

    public static ProductServiceImpl getInstance(Context context) {
        ProductServiceImpl.context = context;
        if (service == null ) {
            service = new ProductServiceImpl();
            cacheIcon = new HashMap<>(1);
        }
        return service;
    }

    public void saveProduct(Item item) {
        //TODO: this call should be in a AsyncTask
        productDAO.addProduct(item.getProduct());
        cacheIcon.put(item.getCode() + ICON, item.getIcon()) ;
        saveImagesToInternalStorage(item);
    }

    public List<Product> getProducts() {
        return productDAO.fetchAllProducts();
    }

    public Product getProductByCode(String code) {
        return productDAO.getProductByCode(code);
    }

    public void deleteProduct(String code) {
        String[] picturesName = retrievePictureNameByCode(code);
        deleteStoredPicturesByName(picturesName);
        productDAO.deleteProduct(code);
    }

    public Bitmap retrievePictureProductByName(String picture) {
        Bitmap bitmap = cacheIcon.get(picture);
        if (bitmap != null) {
            cacheIcon.clear();
            return bitmap;
        }
        try {
            File f = new File(getDirPictures(), picture + EXTENSION_PICTURE);
            bitmap = decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            Log.w(context.getPackageCodePath() , e.getMessage());
        }
        return bitmap;
    }

    public List<Bitmap> retrievePicturesForProducts(List<Product> products) {
        return new ArrayList<>(transform(products, new Function<Product, Bitmap>() {
            @Override
            public Bitmap apply(Product product) {
                return retrievePictureProductByName(product.getPictureIcon());
            }
        }));
    }

    private String[] retrievePictureNameByCode(String code) {
        return productDAO.getPicturesProductByCode(code);
    }

    private void deleteStoredPicturesByName(String[] namePicture) {
        new File(getDirPictures(), namePicture[0] + EXTENSION_PICTURE).delete();
        new File(getDirPictures(), namePicture[1] + EXTENSION_PICTURE).delete();
    }

    private void saveImagesToInternalStorage(Item item) {
        File newIcon = new File(getDirPictures(), item.getProduct().getPictureIcon() + EXTENSION_PICTURE);
        File newDetail = new File(getDirPictures(), item.getProduct().getPictureDetail() + EXTENSION_PICTURE);

        FileOutputStream fosIcon = null, fosDetail = null;
        try {
            fosIcon = new FileOutputStream(newIcon);
            item.getIcon().compress(JPEG, 100, fosIcon);
            fosDetail = new FileOutputStream(newDetail);
            item.getDetail().compress(JPEG, 100, fosDetail);
        } catch (Exception e) {
            Log.w(context.getPackageCodePath(), e.getMessage());
        } finally {
            try {
                if (fosIcon != null ) { fosIcon.close(); }
                if (fosDetail != null ) { fosDetail.close(); }
            } catch (IOException e) {
                Log.e(context.getPackageCodePath() , e.getMessage());
            }
        }
    }

    private File getDirPictures() {
        if (cw == null && directory == null) {
            cw = new ContextWrapper(context);
            directory = cw.getDir(FOLDER_IMAGE_PRODUCTS, MODE_PRIVATE);
        }
        return directory;
    }
}