package com.example.hasantarek.firebasepushnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by HASAN TAREK on 1/24/2018.
 */
public class Fcmmessagingservice extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getData().get("title");
        String msg = remoteMessage.getData().get("body");

       // String click_action = remoteMessage.getNotification().getClickAction();
        String click_action = remoteMessage.getData().get("click_action");
        Intent intent = new Intent(click_action);


            String message = remoteMessage.getData().get("message");
            Bundle bundle = new Bundle();
            bundle.putString("message",message);
            intent.putExtras(bundle);


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setContentTitle(title);
        notification.setContentText(msg);



        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notification.setSound(soundUri);
        //Vibrator vibrator = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
        // vibrator.vibrate(1000);

        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setAutoCancel(true);
        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification.build());
    }
}
