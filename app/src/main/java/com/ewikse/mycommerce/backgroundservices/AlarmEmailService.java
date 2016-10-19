package com.ewikse.mycommerce.backgroundservices;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.ewikse.mycommerce.initial.InitApplicationCommerce;

import java.util.List;

public class AlarmEmailService extends IntentService{

    private InitApplicationCommerce applicationCommerce;
    private static List<String> codes;
    private static String codesString;
    public AlarmEmailService() {
        super(AlarmEmailService.class.getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationCommerce = (InitApplicationCommerce) getApplication();
        codes = applicationCommerce.getListCode();
        codesString = "";
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w("llego msjes", "enviando...");
        for (String code : codes) {
            codesString = codesString + "-" + code;
        }
        BackgroundMail.newBuilder(this)
                .withUsername("ekilly.23@gmail.com")
                .withPassword("123456")
                .withMailto("ekilly.23@gmail.com")
                .withSubject("this is the subject")
                .withBody(codesString + "codigos que fueron cambiados")
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                    }
                })
                .send();
    }
}
