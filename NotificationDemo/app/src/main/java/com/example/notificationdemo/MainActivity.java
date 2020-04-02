package com.example.notificationdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);








        /*creating notification channel*/
        createNotificationChannel();
        /*calling functions*/
        generateNotification();


    }

    public void  generateNotification()
    {
        /*create notification*/
        NotificationCompat.Builder myNotification = new NotificationCompat.Builder(this,"MyNotification");
        myNotification.setSmallIcon(R.drawable.ic_notify);
        myNotification.setContentTitle("Demo notification");
        myNotification.setContentText("This is demo notification for demo app");
        myNotification.setStyle(new NotificationCompat.BigTextStyle().bigText("asdfg fsdf sdfsdf sfsd sdsf dfdsf   fsdaff dfdsf sdfsf dsfdsf dsf dsfdf dfdf df df df dfd "));
        myNotification.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        myNotification.setAutoCancel(true);

        /*create tap action*/
        Intent intent = new Intent(this, NotificationResult.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        /*set tap action*/
        myNotification.setContentIntent(pendingIntent);


        /*Notification action button*/


        /*show notification */
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1001, myNotification.build());

    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyNotificationChannel";
            String description = "This is a notification channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MyNotification", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
