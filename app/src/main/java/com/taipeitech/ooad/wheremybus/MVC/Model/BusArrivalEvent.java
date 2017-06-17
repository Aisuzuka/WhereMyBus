package com.taipeitech.ooad.wheremybus.MVC.Model;

import android.os.AsyncTask;
import android.util.Pair;

import com.taipeitech.ooad.wheremybus.Alarm.BusArriveListener;
import com.taipeitech.ooad.wheremybus.Alarm.Reminder;

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
    private BusRoute targetBusRoute;
    private BusStation targetBusStation;
    private boolean isGoDistance;
    private int notificationTime;

    public int getEventId() {
        return eventId;
    }

    private int eventId;
    private GetBusEstimateTime getBusEstimateTime;

    public BusArrivalEvent(){
        eventId = new Random().nextInt();
    }

    public BusRoute getTargetBusRoute() {
        return targetBusRoute;
    }

    public void setTargetBusRoute(BusRoute targetBusRoute) {
        this.targetBusRoute = targetBusRoute;
    }

    public BusStation getTargetBusStation() {
        return targetBusStation;
    }

    public void setTargetBusStation(BusStation targetBusStation) {
        this.targetBusStation = targetBusStation;
    }

    public boolean isGoDistance() {
        return isGoDistance;
    }

    public void setGoDistance(boolean goDistance) {
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
                new GetBusEstimateTime().execute(targetBusStation);
            }
        }, 0, 30000);
    }

    public void stopWatch(){
        watcher.cancel();
    }

    public void setBusArriveListener(Reminder.SetOnBusArriveListener busArriveListener) {
        this.busArriveListener = busArriveListener;
    }

    private class GetBusEstimateTime extends AsyncTask<BusStation, BusStation, Pair<List<BusEstimateTime>, List<BusEstimateTime>>> {

        @Override
        protected Pair<List<BusEstimateTime>, List<BusEstimateTime>> doInBackground(BusStation... params) {
            Pair<List<BusEstimateTime>, List<BusEstimateTime>> busEstimateTimeList = null;
            try {
                busEstimateTimeList = params[0].getEstimateTime();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return busEstimateTimeList;
        }

        @Override
        protected void onPostExecute(Pair<List<BusEstimateTime>, List<BusEstimateTime>> listListPair) {
            analysisEstimateTime(listListPair);
            super.onPostExecute(listListPair);
        }
    }

    private void analysisEstimateTime(Pair<List<BusEstimateTime>, List<BusEstimateTime>> listListPair) {
        ArrayList<BusEstimateTime> list = null;
        if (isGoDistance)
            list = (ArrayList<BusEstimateTime>) listListPair.first;
        else
            list = (ArrayList<BusEstimateTime>) listListPair.second;
        for (BusEstimateTime item : list) {
            if (item.busRoute.busRouteName.equals(targetBusRoute)) {
                if (Integer.valueOf(item.estimateTime) <= notificationTime) {
                    busArriveListener.busArrived(this);
                }
            }
        }
    }

}
