package com.taipeitech.ooad.wheremybus.Reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.taipeitech.ooad.wheremybus.MVC.Controller.MainActivity;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusArrivalEvent;
import com.taipeitech.ooad.wheremybus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class Reminder extends Service {
    private ArrayList<BusArrivalEvent> busArrivalEventList = new ArrayList<>();
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

    public void addEvent(BusArrivalEvent busArrivalEvent) {
        Log.e("Service", "add an Event");
        busArrivalEvent.setBusArriveListener(setOnBusArriveListener);
        busArrivalEvent.startWatch();
        busArrivalEventList.add(busArrivalEvent);
    }

    public List<BusArrivalEvent> getAllEvents() {
        return busArrivalEventList;
    }

    public void deleteEvent(BusArrivalEvent busArrivalEvent) {
        busArrivalEventList.remove(busArrivalEvent);
        busArrivalEvent.stopWatch();
    }

    @Override
    public void onCreate() {
        Log.e("Service", "Reminder been created");
        setOnBusArriveListener = new SetOnBusArriveListener();
        reminder = this;
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
                .setContentTitle("Service測試")
                .setContentText(String.valueOf(busArrivalEvent.getEventId()))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(busArrivalEvent.getEventId(),notification);
    }

    public class SetOnBusArriveListener implements BusArriveListener {
        @Override
        public void busArrived(final BusArrivalEvent busArrivalEvent) {
            showNotification(busArrivalEvent);
            busArrivalEvent.stopWatch();
        }
    }
}
