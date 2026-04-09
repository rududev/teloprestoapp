package com.telopresto.app.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.telopresto.app.R;

public class NotificacionReceiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "telopresto_channel";
    public static final String EXTRA_OBJETO = "objeto";
    public static final String EXTRA_PERSONA = "persona";

    @Override
    public void onReceive(Context context, Intent intent) {
        String objeto = intent.getStringExtra(EXTRA_OBJETO);
        String persona = intent.getStringExtra(EXTRA_PERSONA);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "TeLoPresto",
                NotificationManager.IMPORTANCE_DEFAULT);
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_back)
                .setContentTitle("Recordatorio de préstamo")
                .setContentText(objeto + " prestado a " + persona + " vence hoy")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}