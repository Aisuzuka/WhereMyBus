package com.taipeitech.ooad.wheremybus.MVC.Model;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;

import java.io.IOException;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class Event {
    private BusEstimateTime busEstimateTime;
    private String busRuteName;
    private String busStationName;
    private int minite;
    private int timeStartHour;
    private int timeStartMinite;
    private int timeEndHour;
    private int timeEndMinite;
    private int goBack;
    private long id;

    public BusEstimateTime getBusEstimateTime() throws IOException{
        BusTable busTable = BusTable.getBustable();
        BusStation busStation=busTable.getStationByName(busStationName);
        BusRoute busRoute = busTable.getRouteByName(busRuteName);
        if(busStation ==null || busRoute ==null){
            return null;
        }
        BusEstimateTime busEstimateTime;
        if(goBack ==0)
            busEstimateTime= busRoute.getGoEstimateTimeByStation(busStation);
        else
            busEstimateTime= busRoute.getBackEstimateTimeByStation(busStation);
        if (busEstimateTime ==null){
            return null;
        }
        this.busEstimateTime = busEstimateTime;

        return busEstimateTime;
    }

    public void setBusEstimateTime(BusEstimateTime busEstimateTime) {
        this.busEstimateTime = busEstimateTime;
    }

    public int getMinite() {
        return minite;
    }

    public void setMinite(int minite) {
        this.minite = minite;
    }

    public int getTimeStartHour() {
        return timeStartHour;
    }

    public void setTimeStartHour(int timeStartHour) {
        this.timeStartHour = timeStartHour;
    }

    public int getTimeStartMinite() {
        return timeStartMinite;
    }

    public void setTimeStartMinite(int timeStartMinite) {
        this.timeStartMinite = timeStartMinite;
    }

    public int getTimeEndHour() {
        return timeEndHour;
    }

    public void setTimeEndHour(int timeEndHour) {
        this.timeEndHour = timeEndHour;
    }

    public int getTimeEndMinite() {
        return timeEndMinite;
    }

    public void setTimeEndMinite(int timeEndMinite) {
        this.timeEndMinite = timeEndMinite;
    }

    public String getBusRuteName() {
        return busRuteName;
    }

    public void setBusRuteName(String busRuteName) {
        this.busRuteName = busRuteName;
    }

    public String getBusStationName() {
        return busStationName;
    }

    public void setBusStationName(String busStationName) {
        this.busStationName = busStationName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGoBack() {
        return goBack;
    }

    public void setGoBack(int goBack) {
        this.goBack = goBack;
    }
}
