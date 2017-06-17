package com.taipeitech.ooad.wheremybus.MVC.Model;

import android.os.AsyncTask;
import android.util.Pair;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.Reminder.BusArriveListener;
import com.taipeitech.ooad.wheremybus.Reminder.Reminder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public BusEstimateTime getBusEstimateTime() throws IOException{
        BusTable busTable = BusTable.getBustable();
        BusStation busStation=busTable.getStationByName(targetBusStation);
        BusRoute busRoute = busTable.getRouteByName(targetBusRoute);
        if(busStation ==null || busRoute ==null){
            return null;
        }
        BusEstimateTime busEstimateTime;
        if(isGoDistance ==1)
            busEstimateTime= busRoute.getGoEstimateTimeByStation(busStation);
        else
            busEstimateTime= busRoute.getBackEstimateTimeByStation(busStation);

        if (busEstimateTime ==null){
            return null;
        }
        this.timeTable = busEstimateTime;

        return busEstimateTime;
    }

    public long getReferenceTime() {
        return referenceTime;
    }

    public void setReferenceTime(long referenceTime) {
        this.referenceTime = referenceTime;
    }

    public int getEventId() {
        return eventId;
    }

    private int eventId;
    private GetBusEstimateTime getBusEstimateTime;

    public BusArrivalEvent() {
        eventId = new Random().nextInt();
        try {
            getBusEstimateTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int isGoDistance() {
        return isGoDistance;
    }

    public void setGoDistance(int goDistance) {
        isGoDistance = goDistance;
    }

    public int getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(int notificationTime) {
        this.notificationTime = notificationTime;
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

    public void setTargetBusRoute(String targetBusRoute) {
        this.targetBusRoute = targetBusRoute;
    }

    public void setTargetBusStation(String targetBusStation) {
        this.targetBusStation = targetBusStation;
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
            Pair<List<BusEstimateTime>, List<BusEstimateTime>> busEstimateTimeList = null;
//                busEstimateTimeList = params[0].update();
            return params[0];
        }

        @Override
        protected void onPostExecute(BusEstimateTime timeTable) {
            analysisEstimateTime(timeTable);
            super.onPostExecute(timeTable);
        }
    }

    private void analysisEstimateTime(BusEstimateTime timeTable) {
        if (System.currentTimeMillis() > referenceTime) {
            if (Integer.valueOf(timeTable.estimateTime) <= notificationTime) {
                busArriveListener.busArrived(this);
            }
        }
    }

}
