package com.ewikse.mycommerce.services;

import android.content.Context;

import com.ewikse.mycommerce.database.DataBase;
import com.ewikse.mycommerce.model.Product;

public class ProductServiceImpl {
    private static ProductServiceImpl service;
    private static Context context;

    public static ProductServiceImpl getInstance(Context context) {
        ProductServiceImpl.context = context;
        return service == null ? new ProductServiceImpl() : service;
    }

    public boolean saveProduct(Product product) {
        //TODO: this call should be in a AsyncTask
        return DataBase.productDAO.addProduct(product);
    }
}
