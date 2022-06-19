package com.example.trabalhoavaliativopdm2.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MyNotification notify = new NotifyNewRecord(context.getApplicationContext());
        notify.createChanel();
        notify.notifyNow();
        Log.i("AlarmReciver", "Terminou de executar");
    }
}
