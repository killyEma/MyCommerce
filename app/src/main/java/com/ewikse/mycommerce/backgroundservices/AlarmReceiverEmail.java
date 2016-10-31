package com.ewikse.mycommerce.backgroundservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiverEmail extends BroadcastReceiver{

    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("BRO_RE", "para el servicio");
//        Intent i = new Intent(context, AlarmEmailService.class);
//        context.startService(i);
    }

}
