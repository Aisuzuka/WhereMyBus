package com.taipeitech.ooad.wheremybus.Reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
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
    private ArrayList<BusArrivalEvent> busArrivalEventList = new ArrayList<>();
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
        BusArrivalEvent busArrivalEvent;
        List<BusArrivalEvent> busArrivalEventList = dataBase.getAll();
        for (int i = 0; i < busArrivalEventList.size(); i++) {
            busArrivalEventList.get(i).setBusArriveListener(setOnBusArriveListener);
            busArrivalEventList.get(i).startWatch();
            busArrivalEvent = busArrivalEventList.get(i);
            this.busArrivalEventList.add(busArrivalEvent);
        }

    }

    public void addEvent(BusArrivalEvent busArrivalEvent) {
        Log.e("Service", "add an Event");
        dataBase.insertBusArrivalEvent(busArrivalEvent);
        busArrivalEvent.setBusArriveListener(setOnBusArriveListener);
        busArrivalEvent.startWatch();
        busArrivalEventList.add(busArrivalEvent);
    }

    public List<BusArrivalEvent> getAllEvents() {
        return busArrivalEventList;
    }

    public void deleteEvent(BusArrivalEvent busArrivalEvent) {
        dataBase.delete(busArrivalEvent.getId());
        busArrivalEventList.remove(busArrivalEvent);
        busArrivalEvent.stopWatch();
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

    private void showNotification(BusArrivalEvent busArrivalEvent) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                busArrivalEvent.getEventId(),
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("公車要到站囉")
                .setContentText("指定的公車路線 " + busArrivalEvent.getTargetBusRoute() + " 已經要到站牌 " + busArrivalEvent.getTargetBusStation())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLights(Color.GREEN, 1000, 1000)
                .setVibrate(new long[]{1000, 500, 1000, 400, 1000, 300, 1000, 200, 1000, 100})
                .build();

        notificationManager.notify(busArrivalEvent.getEventId(), notification);
    }

    public class SetOnBusArriveListener implements BusArriveListener {
        @Override
        public void busArrived(final BusArrivalEvent busArrivalEvent) {
            showNotification(busArrivalEvent);
            deleteEvent(busArrivalEvent);
        }
    }
}
