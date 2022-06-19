package com.example.trabalhoavaliativopdm2.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.trabalhoavaliativopdm2.R;
import com.example.trabalhoavaliativopdm2.SplashSreenActivity;

public class NotifyTryAgain extends MyNotification {

    private final static int NOTIFICACAO_ID = 5;

    public NotifyTryAgain(Context context) {
        this.context = context;
    }

    @Override
    void notifyNow() {
        Intent intent = new Intent(context, SplashSreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_pokeboll);

        Notification builder = new NotificationCompat.Builder(context, CANAL_ID).setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Pokemon")
                .setContentText("Vamos! tente novamente.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pi)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Vamos fazer melhor, clicke aqui para jogar novamente.")).build();
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(NOTIFICACAO_ID,builder);
    }
}
