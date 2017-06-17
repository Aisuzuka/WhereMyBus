package com.taipeitech.ooad.wheremybus.MVC.Model;

import android.util.Log;
import android.util.Pair;

import com.taipeitech.ooad.wheremybus.BusInfo.GetEstimateTime;
import com.taipeitech.ooad.wheremybus.Tool.DownloadObjectFromURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/5/5.
 */
public class BusRoute {
    public String departure;
    public String destination;
    public String busRouteName;
    public  int routeId;
    public List<Integer> goStopId;
    public List<Integer> backStopId;
    public List<BusStation> busStationGoList;
    public List<BusStation> busStationBackList;

    private Map<Integer,BusEstimateTime> mirrorTable;

    private List<BusEstimateTime>busGoEstimateTime;
    private List<BusEstimateTime>busBackEstimateTime;

    public Pair<List<BusEstimateTime>,List<BusEstimateTime>> getEstimateTime()throws IOException {
        if(mirrorTable == null){
            mirrorTable =new HashMap<>();
            busGoEstimateTime =new ArrayList<>();
            busBackEstimateTime = new ArrayList<>();

            for (int i=0;i<busStationGoList.size();i++){
                BusEstimateTime busEstimateTime =new BusEstimateTime();
                busEstimateTime.busStation =busStationGoList.get(i);
                busEstimateTime.busRoute=this;
                busEstimateTime.goBack="0";
                busGoEstimateTime.add(busEstimateTime);
                mirrorTable.put(goStopId.get(i),busEstimateTime);
            }
            for (int i=0;i<busStationBackList.size();i++){
                BusEstimateTime busEstimateTime =new BusEstimateTime();
                busEstimateTime.busStation =busStationBackList.get(i);
                busEstimateTime.busRoute=this;
                busEstimateTime.goBack="1";
                busBackEstimateTime.add(busEstimateTime);
                mirrorTable.put(backStopId.get(i),busEstimateTime);
            }
        }
        DownloadObjectFromURL downloadObjectFromURL =new DownloadObjectFromURL(GetEstimateTime.class,"http://data.taipei/bus/EstimateTime");
        GetEstimateTime getEstimateTime = (GetEstimateTime)downloadObjectFromURL.downloadObject();
        GetEstimateTime.BusInfo busInfo[] = getEstimateTime.BusInfo;
        for (int i =0 ;i<busInfo.length;i++){
            BusEstimateTime busEstimateTime = mirrorTable.get(busInfo[i].StopID);
            if (busEstimateTime != null){
                busEstimateTime.estimateTime=busInfo[i].EstimateTime;
            }

        }
        for(int i=0 ; i<busGoEstimateTime.size() ;i++){
            Log.d("goEstimateTime" , busGoEstimateTime.get(i).busStation.busStationName+"       "+busGoEstimateTime.get(i).estimateTime);
        }
        for(int i=0 ; i<busBackEstimateTime.size() ;i++){
            Log.d("backEstimateTime" , busBackEstimateTime.get(i).busStation.busStationName+"       "+busBackEstimateTime.get(i).estimateTime);
        }
        return new Pair<>(busGoEstimateTime,busBackEstimateTime);

    }

    public BusEstimateTime getGoEstimateTimeByStation(BusStation busStation)throws IOException{
        if(mirrorTable == null)getEstimateTime();
        BusEstimateTime result =null;
        for(int i=0;i<busGoEstimateTime.size();i++){
            if(busGoEstimateTime.get(i).busStation==busStation){
                result = busGoEstimateTime.get(i);
                return result;
            }

        }
        return result;
    }

    public BusEstimateTime getBackEstimateTimeByStation(BusStation busStation)throws IOException{
        if(mirrorTable == null)getEstimateTime();
        BusEstimateTime result =null;
        for(int i=0;i<busBackEstimateTime.size();i++){
            if(busBackEstimateTime.get(i).busStation==busStation){
                result = busBackEstimateTime.get(i);
                return result;
            }

        }
        return result;
    }

    public BusStation getGoStationByName(String busStationName){
        BusStation result =null;
        for(int i=0;i<busStationGoList.size();i++){
            if(busStationGoList.get(i).busStationName.equals(busStationName)){
                result = busStationGoList.get(i);
                return result;
            }

        }
        return result;
    }

    public BusStation getBackStationByName(String busStationName){
        BusStation result =null;
        for(int i=0;i<busStationBackList.size();i++){
            if(busStationBackList.get(i).busStationName.equals(busStationName)){
                result = busStationBackList.get(i);
                return result;
            }

        }
        return result;
    }

    public List<BusStation> getBusRouteGoList(){
        return busStationGoList;
    }

    public List<BusStation> getBusRouteBackList(){
        return busStationBackList;
    }


}
