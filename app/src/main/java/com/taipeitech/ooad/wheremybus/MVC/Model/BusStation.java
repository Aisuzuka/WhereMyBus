package com.taipeitech.ooad.wheremybus.MVC.Model;

import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import com.taipeitech.ooad.wheremybus.BusInfo.BusInformationController;
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
public class BusStation {
    public String busStationName;
    public String address;
    public float lon;
    public float lat;
    public int locationId;
    public int sequence;
    public List<Integer> goStopId;
    public List<Integer> backStopId;
    public List<BusRoute> busRouteGoList;
    public List<BusRoute> busRouteBackList;

    private Map<Integer,BusEstimateTime> mirrorTable;

    private List<BusEstimateTime>busGoEstimateTime;
    private List<BusEstimateTime>busBackEstimateTime;

    public Pair<List<BusEstimateTime>,List<BusEstimateTime>> getEstimateTime()throws IOException{
        if(mirrorTable == null){
            mirrorTable =new HashMap<>();
            busGoEstimateTime =new ArrayList<>();
            busBackEstimateTime = new ArrayList<>();

            for (int i=0;i<busRouteGoList.size();i++){
                BusEstimateTime busEstimateTime =new BusEstimateTime();
                busEstimateTime.busStation =this;
                busEstimateTime.busRoute=busRouteGoList.get(i);
                busEstimateTime.goBack="0";
                busGoEstimateTime.add(busEstimateTime);
                mirrorTable.put(goStopId.get(i),busEstimateTime);
            }
            for (int i=0;i<busRouteBackList.size();i++){
                BusEstimateTime busEstimateTime =new BusEstimateTime();
                busEstimateTime.busStation =this;
                busEstimateTime.busRoute=busRouteBackList.get(i);
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
            Log.d("goEstimateTime" , busGoEstimateTime.get(i).busRoute.busRouteName+"       "+busGoEstimateTime.get(i).estimateTime);
        }
        for(int i=0 ; i<busBackEstimateTime.size() ;i++){
            Log.d("backEstimateTime" , busBackEstimateTime.get(i).busRoute.busRouteName+"       "+busBackEstimateTime.get(i).estimateTime);
        }

        return new Pair<>(busGoEstimateTime,busBackEstimateTime);

    }

    public List<BusRoute> getBusRouteGoList(){
        return busRouteGoList;
    }

    public List<BusRoute> getBusRouteBackList(){
        return busRouteBackList;
    }

}
