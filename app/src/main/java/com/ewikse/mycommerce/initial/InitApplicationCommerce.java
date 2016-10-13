package com.ewikse.mycommerce.initial;

import android.app.Application;

import com.ewikse.mycommerce.database.DataBase;

public class InitApplicationCommerce extends Application{

    public static DataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = new DataBase(this);
        dataBase.open();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        dataBase.closeDB();
    }
}
