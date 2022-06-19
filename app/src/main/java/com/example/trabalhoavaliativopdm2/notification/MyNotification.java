package com.example.trabalhoavaliativopdm2.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public abstract class MyNotification {
    protected final static String CANAL_ID = "4";
    Context context;

    public void createChanel(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            CharSequence nome = "canal1";
            String descricao = "descrição do canal 1";
            int importancia = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel canal = new NotificationChannel(CANAL_ID, nome,importancia);
            canal.setDescription(descricao);

            NotificationManager nm = context.getSystemService(NotificationManager.class);
            nm.createNotificationChannel(canal);
        }
    }
    abstract void notifyNow();
}
