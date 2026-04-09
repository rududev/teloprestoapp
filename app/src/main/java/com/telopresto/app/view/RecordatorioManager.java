package com.telopresto.app.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.telopresto.app.model.Prestamo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecordatorioManager {

    public static void programarRecordatorio(Context context, Prestamo prestamo, String nombrePersona) {
        if (prestamo.fechaDevolucion == null || prestamo.fechaDevolucion.isEmpty()) return;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date fecha = sdf.parse(prestamo.fechaDevolucion);
            if (fecha == null || fecha.before(new Date())) return;

            Intent intent = new Intent(context, NotificacionReceiver.class);
            intent.putExtra(NotificacionReceiver.EXTRA_OBJETO, prestamo.objeto);
            intent.putExtra(NotificacionReceiver.EXTRA_PERSONA, nombrePersona);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    prestamo.id,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        fecha.getTime(),
                        pendingIntent
                );
            } else {
                alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        fecha.getTime(),
                        pendingIntent
                );
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void cancelarRecordatorio(Context context, int prestamoId) {
        Intent intent = new Intent(context, NotificacionReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                prestamoId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}