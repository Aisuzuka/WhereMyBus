package com.taipeitech.ooad.wheremybus.Reminder;

import android.os.AsyncTask;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusStation;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class BusArrivalEvent {
    private BusEstimateTime timeTable;

    private int isGoDistance;
    private int notificationTime;
    private long referenceTime;
    private String targetBusRoute;
    private String targetBusStation;
    private long id;
    public long getReferenceTime() {
        return referenceTime;
    }

    public BusArrivalEvent setTimeTable(BusEstimateTime timeTable) {
        this.timeTable = timeTable;
        return this;
    }

    public BusArrivalEvent setReferenceTime(long referenceTime) {
        this.referenceTime = referenceTime;
        return this;
    }

    public int getEventId() {
        return eventId;
    }

    private int eventId;
    private GetBusEstimateTime getBusEstimateTime;

    public BusArrivalEvent() {
        eventId = new Random().nextInt();
    }

    public int isGoDistance() {
        return isGoDistance;
    }

    public BusArrivalEvent setGoDistance(int goDistance) {
        isGoDistance = goDistance;
        return this;
    }

    public int getNotificationTime() {
        return notificationTime;
    }

    public BusArrivalEvent setNotificationTime(int notificationTime) {
        this.notificationTime = notificationTime;
        return this;
    }

    private BusArriveListener busArriveListener;
    private Timer watcher;

    public void startWatch() {
        watcher = new Timer();
        watcher.schedule(new TimerTask() {
            @Override
            public void run() {
                new GetBusEstimateTime().execute(timeTable);
            }
        }, 0, 5000);
    }

    public void stopWatch() {
        watcher.cancel();
    }

    public void setBusArriveListener(Reminder.SetOnBusArriveListener busArriveListener) {
        this.busArriveListener = busArriveListener;
    }

    public String getTargetBusRoute() {
        return targetBusRoute;
    }

    public String getTargetBusStation() {
        return targetBusStation;
    }

    public BusArrivalEvent setTargetBusRoute(String targetBusRoute) {
        this.targetBusRoute = targetBusRoute;
        return this;
    }

    public BusArrivalEvent setTargetBusStation(String targetBusStation) {
        this.targetBusStation = targetBusStation;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private class GetBusEstimateTime extends AsyncTask<BusEstimateTime, BusEstimateTime, BusEstimateTime> {

        @Override
        protected BusEstimateTime doInBackground(BusEstimateTime... params) {
            try {
                params[0].updateTime();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(BusEstimateTime timeTable) {
            analysisEstimateTime(timeTable);
            super.onPostExecute(timeTable);
        }
    }

    private void analysisEstimateTime(BusEstimateTime timeTable) {
        if (System.currentTimeMillis() >= referenceTime) {
            if (Integer.valueOf(timeTable.estimateTime) >= 0 && Integer.valueOf(timeTable.estimateTime) <= notificationTime * 60) {
                busArriveListener.busArrived(this);
            }
        }
    }

}
