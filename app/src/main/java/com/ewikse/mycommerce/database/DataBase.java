package com.ewikse.mycommerce.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase {
    private static final String DATABASE_NAME = "Products.db";
    private static final int DATABASE_VERSION = 2;
    private final Context context;
    private DataBaseHelper dbHelper;
    private SQLiteDatabase mDb;

    public static ProductDAO productDAO;

    public DataBase(Context context){
        this.context = context;
    }

    public DataBase open() {
        if (dbHelper == null) {
            dbHelper = new DataBaseHelper(context);
        }
        mDb = dbHelper.getWritableDatabase();
        productDAO = new ProductDAOImpl(mDb);
        return this;
    }

    public void closeDB(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db != null && db.isOpen()){
            db.close();
        }
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(IProductSchema.CREATE_PRODUCTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion != newVersion) {
                db.execSQL("DROP TABLE " + IProductSchema.TABLE_PRODUCTS);
                onCreate(db);
            }
        }
    }
}
