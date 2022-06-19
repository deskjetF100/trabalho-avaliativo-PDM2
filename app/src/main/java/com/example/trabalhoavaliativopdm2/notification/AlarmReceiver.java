package com.example.trabalhoavaliativopdm2.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int option = intent.getIntExtra("typeNotification", 2);
        MyNotification notify = null;
        switch(option){
            case 1:
                notify = new NotifyNewRecord(context.getApplicationContext());
                break;
            case 2:
                notify = new NotifyTryAgain(context.getApplicationContext());
                break;
        }
        notify.createChanel();
        notify.notifyNow();
        Log.i("AlarmReciver", "Terminou de executar");
    }
}
