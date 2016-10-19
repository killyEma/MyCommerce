package com.ewikse.mycommerce.initial;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.ewikse.mycommerce.backgroundservices.AlarmReceiverEmail;
import com.ewikse.mycommerce.database.DataBase;

import java.util.ArrayList;
import java.util.List;

public class InitApplicationCommerce extends Application {

    public static DataBase dataBase;
    private static List<String> codes;
    private final static long FIRST_MILLIS = System.currentTimeMillis();

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = new DataBase(this);
        dataBase.open();
        codes = new ArrayList<>();
        scheduleAlarm();
    }

    private void scheduleAlarm() {
        Log.w("enviando", "esperando los segundos");
        Intent intent = new Intent(getApplicationContext(), AlarmReceiverEmail.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiverEmail.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, FIRST_MILLIS,
                180000, pIntent);
    }

    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), AlarmReceiverEmail.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiverEmail.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        dataBase.closeDB();
        cancelAlarm();
    }

    public void addToList(String code) {
        codes.add(code);
    }

    public List<String> getListCode() {
        return codes;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
