package com.example.strig.baiterekapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class HandleFBMessageService extends FirebaseMessagingService {
    public final String TAG = "FIREBASE_TAG";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification());
        }

    }

    public void sendNotification(RemoteMessage.Notification rn) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;

        NotificationChannel channel;
        Notification.Builder nb;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("default",
                    "Firebase Activity", NotificationManager.IMPORTANCE_DEFAULT);

            channel.setLightColor(Color.GREEN);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            manager.createNotificationChannel(channel);

            nb = new Notification.Builder(this, "default");
        } else {
            nb = new Notification.Builder(this);
        }

        Notification n = nb.setContentTitle(rn.getTitle())
                .setContentText(rn.getBody())
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setAutoCancel(true)
                .build();

        manager.notify(1, n);
    }
}
