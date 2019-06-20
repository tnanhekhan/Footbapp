package com.example.footbapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.footbapp.viewmodel.EventViewModel;

public class Footbapp extends Application {
    public static final String CHANNEL_1_ID = "Subscribed Events";
    private EventViewModel eventViewModel;

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
                    "Notifications for Subscribed Events",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This channel is for notifications for subscribed events");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
        }
    }
}
