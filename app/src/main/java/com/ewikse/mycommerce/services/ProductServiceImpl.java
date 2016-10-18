package com.ewikse.mycommerce.services;

import android.content.Context;

import com.ewikse.mycommerce.database.DataBase;
import com.ewikse.mycommerce.model.Product;

import java.util.List;

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

    public List<Product> getProducts() {
        return DataBase.productDAO.fetchAllProducts();
    }

    public Product getProductByCode(String code) {
        return DataBase.productDAO.getProductByCode(code);
    }
}
