package com.taipeitech.ooad.wheremybus.MVC.Model;

import android.os.AsyncTask;
import android.util.Pair;

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
    private boolean isGoDistance;
    private int notificationTime;
    private long referenceTime;

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
