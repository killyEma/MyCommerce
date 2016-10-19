package com.ewikse.mycommerce.backgroundservices;

import android.app.IntentService;
import android.content.Intent;

import com.ewikse.mycommerce.initial.InitApplicationCommerce;


public class EmailService extends IntentService{
    public static final String CODE_PRODUCT = "code";
    private InitApplicationCommerce applicationCommerce;

    public EmailService() {
        super(EmailService.class.getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationCommerce = (InitApplicationCommerce) getApplication();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        applicationCommerce.addToList(intent.getStringExtra(EmailService.CODE_PRODUCT));
    }
}
