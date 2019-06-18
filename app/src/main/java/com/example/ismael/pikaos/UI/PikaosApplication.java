package com.example.ismael.pikaos.UI;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.ismael.pikaos.data.dao.local.PikaosOpenHelper;

public class PikaosApplication extends Application {
    //http://157.230.114.223:8090/
    //http://89.39.159.65/
    public final static String URL = "https://ismael.edufdezsoy.es:8090/";

    private static Context context;
    public static String CHANNEL_ID = "canal";

    //nombre y token del usuario actualmente logueado
    public static String token;

    public static String userName;
    public static String userAvatar;
    public static String userStatus;


    public void onCreate() {
        super.onCreate();
        context = this;

        createChannel();
    }

    public static Context getAppContext() {
        return PikaosApplication.context;
    }

    public void createChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "canal", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
