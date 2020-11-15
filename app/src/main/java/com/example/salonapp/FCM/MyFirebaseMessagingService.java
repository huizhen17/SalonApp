package com.example.salonapp.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import com.example.salonapp.MainActivity;
import com.example.salonapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");


        Intent intent = new Intent(this, MainActivity.class);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "SalonApp")
                        .setContentTitle(title)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(body));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        int id = (int) System.currentTimeMillis();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("SalonApp", "App Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(id, notificationBuilder.build());
    }


    private void sendRegistrationToServer(String token) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DocumentReference updateToken = db.collection("userDetail").document(mAuth.getCurrentUser().getUid());
        updateToken.update("token", token);
    }


}

