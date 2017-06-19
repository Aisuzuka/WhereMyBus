package com.taipeitech.ooad.wheremybus.Reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.taipeitech.ooad.wheremybus.MVC.Controller.MainActivity;
import com.taipeitech.ooad.wheremybus.MVC.Model.DataBase;
import com.taipeitech.ooad.wheremybus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class Reminder extends Service {
    private ArrayList<BusArriveEvent> busArriveEventList = new ArrayList<>();
    private DataBase dataBase;
    SetOnBusArriveListener setOnBusArriveListener;

    static Reminder reminder;

    static public boolean isAlive() {
        if (reminder == null)
            return false;
        else
            return true;
    }

    public static Reminder getReminder() {
        return reminder;
    }

    public void addEventFromDataBase() {
        BusArriveEvent busArriveEvent;
        List<BusArriveEvent> busArriveEventList = dataBase.getAllArriveEvent();
        for (int i = 0; i < busArriveEventList.size(); i++) {
            busArriveEventList.get(i).setBusArriveListener(setOnBusArriveListener);
            busArriveEventList.get(i).startWatch();
            busArriveEvent = busArriveEventList.get(i);
            this.busArriveEventList.add(busArriveEvent);
        }

    }

    public void addEvent(BusArriveEvent busArriveEvent) {
        Log.e("Service", "add an Event");
        dataBase.insertBusArriveEvent(busArriveEvent);
        busArriveEvent.setBusArriveListener(setOnBusArriveListener);
        busArriveEvent.startWatch();
        busArriveEventList.add(busArriveEvent);
    }

    public List<BusArriveEvent> getAllEvents() {
        return busArriveEventList;
    }

    public void deleteEvent(BusArriveEvent busArriveEvent) {
        dataBase.deleteBusArrivaEvent(busArriveEvent.getId());
        busArriveEventList.remove(busArriveEvent);
        busArriveEvent.stopWatch();
    }

    @Override
    public void onCreate() {
        Log.e("Service", "Reminder been created");
        setOnBusArriveListener = new SetOnBusArriveListener();
        reminder = this;
        dataBase = new DataBase(this);
        addEventFromDataBase();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification(BusArriveEvent busArriveEvent) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                busArriveEvent.getEventId(),
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("公車要到站囉")
                .setContentText("指定的公車路線 " + busArriveEvent.getTargetBusRoute() + " 已經要到站牌 " + busArriveEvent.getTargetBusStation())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLights(Color.GREEN, 1000, 1000)
                .setVibrate(new long[]{1000, 500, 1000, 400, 1000, 300, 1000, 200, 1000, 100})
                .build();

        notificationManager.notify(busArriveEvent.getEventId(), notification);
    }

    public class SetOnBusArriveListener implements BusArriveListener {
        @Override
        public void busArrived(final BusArriveEvent busArriveEvent) {
            showNotification(busArriveEvent);
            deleteEvent(busArriveEvent);
        }
    }
}
