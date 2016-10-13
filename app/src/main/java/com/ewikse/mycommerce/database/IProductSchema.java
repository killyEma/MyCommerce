package com.ewikse.mycommerce.database;

public interface IProductSchema {
    String TABLE_PRODUCTS = "products";

    String CL_PRODUCTS_CODE = "code";
    String CL_PRODUCTS_NAME = "name";
    String CL_PRODUCTS_DESCRIPTION = "description";
    String CL_PRODUCTS_PRICE = "price";
    String CL_PRODUCTS_STOCK = "stock";
    String CL_PRODUCTS_IMAGE = "image";

    String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS +
            "(" +
            CL_PRODUCTS_CODE + " TEXT PRIMARY KEY," +
            CL_PRODUCTS_NAME + " TEXT," +
            CL_PRODUCTS_DESCRIPTION + " TEXT," +
            CL_PRODUCTS_STOCK + " INTEGER," +
            CL_PRODUCTS_PRICE + " TEXT," +
            CL_PRODUCTS_IMAGE + " BLOB" +
            ")";

}
