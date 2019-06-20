package com.example.footbapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Footbapp extends Application {
    public static final String CHANNEL_1_ID = "Subscribed Events";
    private static final String NOTIFICATION_CHANNEL_DESCRIPTION = "This channel is for notifications for subscribed events";
    private static final String NOTIFICATION_CHANNEL_NAME = "Notifications for Subscribed Events";

    @Override
    public void onCreate() {
        super.onCreate();

//        eventViewModel = ViewModelProviders.of().get(EventViewModel.class);
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
        }
    }
}
