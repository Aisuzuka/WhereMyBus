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

public class BusArriveEvent {
    private BusEstimateTime timeTable = null;

    private int isGoDistance;
    private int notificationTime;
    private long referenceTime;
    private String targetBusRoute;
    private String targetBusStation;
    private long id;

    public BusEstimateTime getTimeTable(){
        return timeTable;
    }

    public void createTimeTable() throws IOException {
        if (timeTable != null) return;
        BusTable busTable = BusTable.getBusTable();
        BusStation busStation = busTable.getStationByName(targetBusStation);
        BusRoute busRoute = busTable.getRouteByName(targetBusRoute);
        if (busStation == null || busRoute == null) {

        } else {
            BusEstimateTime busEstimateTime;
            if (isGoDistance == 1)
                busEstimateTime = busRoute.getGoEstimateTimeByStation(busStation);
            else
                busEstimateTime = busRoute.getBackEstimateTimeByStation(busStation);

            if (busEstimateTime == null) {
            } else {
                this.timeTable = busEstimateTime;
            }
        }
    }

    public long getReferenceTime() {
        return referenceTime;
    }

    public BusArriveEvent setTimeTable(BusEstimateTime timeTable) {
        this.timeTable = timeTable;
        return this;
    }

    public BusArriveEvent setReferenceTime(long referenceTime) {
        this.referenceTime = referenceTime;
        return this;
    }

    public int getEventId() {
        return eventId;
    }

    private int eventId;

    public BusArriveEvent() {
        eventId = new Random().nextInt();
    }

    public int isGoDistance() {
        return isGoDistance;
    }

    public BusArriveEvent setGoDistance(int goDistance) {
        isGoDistance = goDistance;
        return this;
    }

    public int getNotificationTime() {
        return notificationTime;
    }

    public String getDestination() {
        return timeTable.busRoute.destination;
    }

    public BusArriveEvent setNotificationTime(int notificationTime) {
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
                new GetBusEstimateTime().execute();
            }
        }, 0, 30000);
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

    public BusArriveEvent setTargetBusRoute(String targetBusRoute) {
        this.targetBusRoute = targetBusRoute;
        return this;
    }

    public BusArriveEvent setTargetBusStation(String targetBusStation) {
        this.targetBusStation = targetBusStation;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private class GetBusEstimateTime extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (timeTable == null)
                    try {
                        createTimeTable();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                timeTable.updateTime();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            analysisEstimateTime(timeTable);
            super.onPostExecute(aVoid);
        }
    }

    private void analysisEstimateTime(BusEstimateTime timeTable) {
        if (isReferenceTimeNow()) {
            if (isBusApproach(timeTable)) {
                busArriveListener.busArrived(this);
            }
        }
    }

    private boolean isReferenceTimeNow() {
        return System.currentTimeMillis() >= referenceTime;
    }

    private boolean isBusApproach(BusEstimateTime timeTable) {
        int estimateTime = Integer.valueOf(timeTable.estimateTime);
        int notificationTime_second = notificationTime * 60;
        return estimateTime >= notificationTime_second && estimateTime <= notificationTime_second + 60 ? true : false;
    }

}
