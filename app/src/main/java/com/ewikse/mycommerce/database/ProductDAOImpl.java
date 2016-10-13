package com.ewikse.mycommerce.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ewikse.mycommerce.model.Product;

import java.util.List;

public class ProductDAOImpl extends DbContentProvider implements ProductDAO, IProductSchema{
    private ContentValues contentValues;

    @Override
    protected Product cursorToEntity(Cursor cursor) {
        return new Product(cursor.getString(0),
                                        cursor.getString(1),
                                        cursor.getString(2),
                                        cursor.getInt(3),
                                        cursor.getString(4),
                                        cursor.getBlob(5));
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
            Log.w("DATABASE", e.getMessage());
            return false;
        }
    }

    private void setContentValues(Product product) {
        contentValues  = new ContentValues();
        contentValues.put(CL_PRODUCTS_CODE, product.getCode());
        contentValues.put(CL_PRODUCTS_DESCRIPTION, product.getDescription());
        contentValues.put(CL_PRODUCTS_IMAGE, product.getPicture());
        contentValues.put(CL_PRODUCTS_NAME, product.getName());
        contentValues.put(CL_PRODUCTS_PRICE, product.getPrice());
        contentValues.put(CL_PRODUCTS_STOCK, product.getStock());

    }

    @Override
    public List<Product> fetchAllProducts() {
        return null;
    }

    @Override
    public boolean deleteProduct(String code) {
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        return false;
    }
}
