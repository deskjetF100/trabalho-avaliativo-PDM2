package com.example.trabalhoavaliativopdm2.notification;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.trabalhoavaliativopdm2.R;
import com.example.trabalhoavaliativopdm2.pokemon.PokemonsData;

public class NotifyNewRecord extends MyNotification {
    private final static int NOTIFICACAO_ID = 5;

    public NotifyNewRecord(Context context) {
        this.context = context;

    }

    @Override
    void notifyNow() {
        String userNikeName = PokemonsData.getInstace().getUserNikeName();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_pokeboll);

        Notification builder = new NotificationCompat.Builder(context, CANAL_ID).setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Parabéns!!")
                .setContentText("Você fez uma bela jogada.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Parabéns "+userNikeName+", você está entre os melhores!")).build();
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(NOTIFICACAO_ID,builder);
    }
}
