package com.ewikse.mycommerce.services;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.ewikse.mycommerce.database.DataBase;
import com.ewikse.mycommerce.model.Item;
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

import static android.content.Context.MODE_PRIVATE;
import static com.ewikse.mycommerce.model.Product.DETAIL;
import static com.ewikse.mycommerce.model.Product.ICON;

public class ProductServiceImpl {
    private static final String EXTENSION_PICTURE = ".jpg";
    private static ProductServiceImpl service;
    private static Context context;
    private static final String FOLDER_IMAGE_PRODUCTS = "imageProducts";
    private ContextWrapper cw;
    private File directory;

    public static ProductServiceImpl getInstance(Context context) {
        ProductServiceImpl.context = context;
        if (service == null ) {
            service = new ProductServiceImpl();
        }
        return service;
    }

    public void saveProduct(Item item) {
        saveToInternalStorage(item.getCode(), item.getIcon(), item.getDetail());
        //TODO: this call should be in a AsyncTask
        DataBase.productDAO.addProduct(item.getProduct());
    }

    public List<Product> getProducts() {
        return DataBase.productDAO.fetchAllProducts();
    }

    public Product getProductByCode(String code) {
        return DataBase.productDAO.getProductByCode(code);
    }

    public void deleteProduct(String code) {
        deleteStoredPicture(retrievePictureNameByCode(code));
        DataBase.productDAO.deleteProduct(code);
    }

    public Bitmap retrievePictureProductByName(String picture) {
        Bitmap bitmap = null;
        try {
            File f = new File(loadDirectory(), picture + EXTENSION_PICTURE);
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
                return retrievePictureProductByName(product.getPictureIcon());
            }
        }));
    }

    private String retrievePictureNameByCode(String code) {
        return DataBase.productDAO.getPictureProductByCode(code);
    }

    private boolean deleteStoredPicture(String namePicture) {
        return new File(loadDirectory(), namePicture + EXTENSION_PICTURE).delete();
    }

    private void saveToInternalStorage(String code, Bitmap icon, Bitmap detail) {
        File myPathIcon = new File(loadDirectory(), code + ICON + EXTENSION_PICTURE);
        File myPathDetail = new File(loadDirectory(), code + DETAIL + EXTENSION_PICTURE);

        FileOutputStream fosIcon = null, fosDetail = null;
        try {
            fosIcon = new FileOutputStream(myPathIcon);
            icon.compress(Bitmap.CompressFormat.JPEG, 100, fosIcon);
            fosDetail = new FileOutputStream(myPathDetail);
            detail.compress(Bitmap.CompressFormat.JPEG, 100, fosDetail);
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

    private File loadDirectory() {
        if (cw == null && directory == null) {
            cw = new ContextWrapper(ProductServiceImpl.context);
            directory = cw.getDir(FOLDER_IMAGE_PRODUCTS, MODE_PRIVATE);
        }
        return directory;
    }
}