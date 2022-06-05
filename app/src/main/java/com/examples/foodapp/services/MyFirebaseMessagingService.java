package com.examples.foodapp.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.examples.foodapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    final String notificationId = "Food_notifications";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        String title = message.getNotification().getTitle();
        String body = message.getNotification().getBody();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(notificationId, "Food Offers", NotificationManager.IMPORTANCE_HIGH);
            getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
        }
       // Intent intentnoti = new Intent(this, SplashActivity.class);
       // PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentnoti, 0);
        // Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), notificationId);
        builder.setSmallIcon(R.drawable.small_icon_notification);
        builder.setColor(getResources().getColor(R.color.colorPrimary));
        builder.setContentTitle(title);
        //  builder.setSound(defaultSoundUri);
      //  builder.setContentIntent(pendingIntent);
        builder.setContentText(body);

        NotificationManagerCompat nmc = NotificationManagerCompat.from(this);
        nmc.notify(new Random().nextInt(), builder.build());

        super.onMessageReceived(message);
    }
}
