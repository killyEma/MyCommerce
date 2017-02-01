package com.ewikse.mycommerce.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ewikse.mycommerce.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl extends DbContentProvider implements ProductDAO, IProductSchema {

    private static final String DATABASE = "DATABASE";

    private ContentValues contentValues;

    @Override
    protected Product cursorToEntity(Cursor cursor) {
        return new Product(cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6));
    }

    public ProductDAOImpl(SQLiteDatabase mDb) {
        super(mDb);
    }

    @Override
    public boolean addProduct(Product product) {
        setContentValues(product);
        try {
            return super.insert(TABLE_PRODUCTS, contentValues) > 0;
        } catch (SQLiteConstraintException e) {
            Log.w(DATABASE, e.getMessage());
            return false;
        }
    }

    @Override
    public Product getProductByCode(String code) {
        Cursor cursor = super.rawQuery(SINGLE_PRODUCT_QUERY, new String[]{code});
        cursor.moveToNext();
        return cursorToEntity(cursor);
    }

    @Override
    public String[] getPicturesProductByCode(String code) {
        Cursor cursor = super.rawQuery(PICTURES_NAME_PRODUCT_QUERY, new String[]{code});
        cursor.moveToNext();
        return new String[]{cursor.getString(0), cursor.getString(1)};
    }

    @Override
    public List<Product> fetchAllProducts() {
        List<Product> products = new ArrayList<>();
        Cursor cursor = super.query(TABLE_PRODUCTS, PRODUCT_COLUMNS_FOR_LIST, null, null, null, null);
        cursor.moveToNext();
        while (!cursor.isAfterLast()) {
            products.add(cursorToEntity(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    private void setContentValues(Product product) {
        contentValues = new ContentValues();
        contentValues.put(CL_PRODUCTS_CODE, product.getCode());
        contentValues.put(CL_PRODUCTS_DESCRIPTION, product.getDescription());
        contentValues.put(CL_PRODUCTS_IMAGE_ICON, product.getPictureIcon());
        contentValues.put(CL_PRODUCTS_IMAGE_DETAIL, product.getPictureDetail());
        contentValues.put(CL_PRODUCTS_NAME, product.getName());
        contentValues.put(CL_PRODUCTS_PRICE, product.getPrice());
        contentValues.put(CL_PRODUCTS_STOCK, product.getStock());

    }

    @Override
    public void deleteProduct(String code) {
        super.delete(TABLE_PRODUCTS, CL_PRODUCTS_CODE + "=?", new String[]{code});
    }

    @Override
    public boolean updateProduct(Product product) {
        return false;
    }
}
