package com.taipeitech.ooad.wheremybus.BusInfo;

import android.util.Log;
import android.util.Pair;

import com.taipeitech.ooad.wheremybus.MVC.Model.Bus;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusStation;
import com.taipeitech.ooad.wheremybus.Tool.DownloadObjectFromURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/5/25.
 */

public class BusTable {
    private List<BusRoute> busRouteList =new ArrayList<>();
    private List<BusStation> busStationList = new ArrayList<>();
    private static BusTable busTable = null;
    public static BusTable getBustable(){
        return busTable;
    }

    public static BusTable createBustable() throws IOException {
        if(busTable!= null)return busTable;
        long startTime = System.nanoTime();

        DownloadObjectFromURL downloadObjectFromURLRoute =new DownloadObjectFromURL(GetRoute.class,"http://data.taipei/bus/ROUTE");
        DownloadObjectFromURL downloadObjectFromURLStop =new DownloadObjectFromURL(GetStop.class,"http://data.taipei/bus/Stop");

        GetRoute getRoute = (GetRoute) downloadObjectFromURLRoute.downloadObject();
        GetStop getStop = (GetStop)downloadObjectFromURLStop.downloadObject();

        long endTime = System.nanoTime();
        Log.d("TimeStreamJackson",String.valueOf(-startTime+endTime));

        busTable =new BusTable();
        List<BusRoute> busRouteList = busTable.busRouteList;
        List<BusStation> busStationList=busTable.busStationList;

        GetRoute.BusInfo[] routeInfo = getRoute.BusInfo;
        GetStop.BusInfo[] stationInfo = getStop.BusInfo;

        Map<Integer,BusRoute> busRouteMap =new HashMap<>();
        for (int i =0 ;i<routeInfo.length;i++){

            BusRoute busRoute =busRouteMap.get(routeInfo[i].Id);
            if(busRoute==null){
                busRoute =new BusRoute();
                busRoute.busRouteName=routeInfo[i].nameZh;
                busRoute.routeId=routeInfo[i].Id;
                busRoute.departure=routeInfo[i].departureZh;
                busRoute.destination=routeInfo[i].destinationZh;
                busRoute.goStopId=new ArrayList<>();
                busRoute.backStopId=new ArrayList<>();
                busRoute.busStationGoList=new ArrayList<>();
                busRoute.busStationBackList=new ArrayList<>();
                busRouteList.add(busRoute);
                busRouteMap.put(busRoute.routeId,busRoute);
                Log.d("busRoute",busRoute.busRouteName+"    "+busRoute.routeId+"     "+busRoute.departure+"       "+busRoute.destination);
            }
        }

        Map<String,BusStation> busStationMap =new HashMap();
        for(int i=0 ;i<stationInfo.length;i++){
            BusStation busStation=busStationMap.get(stationInfo[i].nameZh);
            if(busStation==null){
                busStation =new BusStation();
                busStation.address=stationInfo[i].address;
                busStation.busStationName=stationInfo[i].nameZh;
                busStation.locationId=stationInfo[i].stopLocationId;
                busStation.sequence=stationInfo[i].seqNo;

                busStation.lat=Float.parseFloat(stationInfo[i].showLat);
                busStation.lon=Float.parseFloat(stationInfo[i].showLon);
                busStation.goStopId=new ArrayList<>();
                busStation.backStopId=new ArrayList<>();
                busStation.busRouteGoList =new ArrayList<>();
                busStation.busRouteBackList =new ArrayList<>();
                busStationList.add(busStation);
                busStationMap.put(busStation.busStationName,busStation);
                Log.d("busStation",busStation.busStationName+"    "+busStation.locationId+"     "+busStation.address+"       "+busStation.lat+"     "+busStation.lon);
            }
        }

        Map<Integer,List<Pair<BusStation,Pair<Integer,Integer>>>> throughStationGo =new HashMap<>();
        Map<Integer,List<Pair<BusStation,Pair<Integer,Integer>>>> throughStationBack = new HashMap<>();
        for(int i=0;i<stationInfo.length;i++){
            if(stationInfo[i].goBack.equals("0")){
                List<Pair<BusStation,Pair<Integer,Integer>>> stationSequenceList = throughStationGo.get(stationInfo[i].routeId);
                if(stationSequenceList ==null){
                    stationSequenceList =new ArrayList<>();
                    throughStationGo.put(stationInfo[i].routeId,stationSequenceList);
                }
                stationSequenceList.add(new Pair<BusStation, Pair<Integer,Integer>>(busStationMap.get(stationInfo[i].nameZh),new Pair<Integer,Integer>(stationInfo[i].seqNo,stationInfo[i].Id)));
            }else if(stationInfo[i].goBack.equals("1")){
                List<Pair<BusStation,Pair<Integer,Integer>>> stationSequenceList = throughStationBack.get(stationInfo[i].routeId);
                if(stationSequenceList ==null){
                    stationSequenceList =new ArrayList<>();
                    throughStationBack.put(stationInfo[i].routeId,stationSequenceList);
                }
                stationSequenceList.add(new Pair<BusStation, Pair<Integer,Integer>>(busStationMap.get(stationInfo[i].nameZh),new Pair<Integer,Integer>(stationInfo[i].seqNo,stationInfo[i].Id)));
            }
            else{
                Log.d("??????",stationInfo[i].goBack +"       "+stationInfo[i].nameZh+ "        "+stationInfo[i].seqNo+"      "+stationInfo[i].routeId);
            }
        }

        Comparator comparator =new Comparator<Pair<BusStation,Pair<Integer,Integer>>>() {
            @Override
            public int compare(Pair<BusStation,Pair<Integer,Integer>> o, Pair<BusStation,Pair<Integer,Integer>> t1) {
                if(o.second.first<t1.second.first){
                    return -1;
                }else if(o.second.first>t1.second.first){
                    return 1;
                }else {
                    return 0;
                }
            }
        };
        for (int i=0 ;i<busRouteList.size();i++){
            BusRoute busRoute = busRouteList.get(i);
            List<BusStation> goList = busRoute.busStationGoList;
            List<BusStation> backList = busRoute.busStationBackList;
            List<Integer> goStopId =busRoute.goStopId;
            List<Integer> backStopId =busRoute.backStopId;

            List<Pair<BusStation,Pair<Integer,Integer>>> stationSequenceGoList = throughStationGo.get(busRoute.routeId);
            if(stationSequenceGoList != null)
            java.util.Collections.sort(stationSequenceGoList,comparator);
            List<Pair<BusStation,Pair<Integer,Integer>>> stationSequenceBackList = throughStationBack.get(busRoute.routeId);
            if(stationSequenceBackList != null)
            java.util.Collections.sort(stationSequenceBackList,comparator);

            if(stationSequenceGoList != null)
           for(int j=0;j<stationSequenceGoList.size();j++){
               BusStation busStation = stationSequenceGoList.get(j).first;
               int stopId = stationSequenceGoList.get(j).second.second;
               goList.add(busStation);
               goStopId.add(stopId);
               busStation.busRouteGoList.add(busRoute);
               busStation.goStopId.add(stopId);
           }

            if(stationSequenceBackList != null)
            for(int j=0;j<stationSequenceBackList.size();j++){
                BusStation busStation = stationSequenceBackList.get(j).first;
                int stopId = stationSequenceBackList.get(j).second.second;
                backList.add(busStation);
                backStopId.add(stopId);
                busStation.busRouteBackList.add(busRoute);
                busStation.backStopId.add(stopId);
            }

//            Log.d("busRouteSort","----------------------------------------------------------------------"+busRoute.busRouteName+"       "+busRoute.routeId);
//            Log.d("go!!!!!","-----------------------go-----------------------go");
//            for(int j=0;j<goList.size();j++){
//                BusStation busStation= goList.get(j);
//                Log.d("throughStation",busStation.busStationName);
//            }
//            Log.d("back!!!!!","-----------------------back-----------------------back");
//            for(int j=0;j<backList.size();j++){
//                BusStation busStation= backList.get(j);
//                Log.d("throughStation",busStation.busStationName);
//            }
        }






        return busTable;
    }

    public List<BusRoute> searchRouteByName(String busRouteName){
        List<BusRoute> result =new ArrayList<>();
        int strLength =busRouteName.length();
        for(int i=0;i<busRouteList.size();i++){
            if(busRouteList.get(i).busRouteName.length()>=strLength){
                if(busRouteList.get(i).busRouteName.substring(0,strLength).equals(busRouteName)){
                    result.add(busRouteList.get(i));

                }
            }

        }
        for (int i=0;i<result.size();i++){
            Log.d("searchResult",result.get(i).busRouteName);
        }
        return result;
    }

    public BusRoute getRouteByName(String busRouteName){
        BusRoute result =null;
        for(int i=0;i<busRouteList.size();i++){
            if(busRouteList.get(i).busRouteName.equals(busRouteName)){
                result = busRouteList.get(i);
                return result;
            }

        }

        return result;
    }

    public List<BusStation> searchStationByName(String busStationName){
        List<BusStation> result =new ArrayList<>();
        int strLength =busStationName.length();
        for(int i=0;i<busStationList.size();i++){
            if(busStationList.get(i).busStationName.length()>=strLength){
                if(busStationList.get(i).busStationName.substring(0,strLength).equals(busStationName)){
                    result.add(busStationList.get(i));
                }
            }

        }
        for (int i=0;i<result.size();i++){
            Log.d("searchResult",result.get(i).busStationName);
        }
        return result;
    }

    public BusStation getStationByName(String busStationName){
        BusStation result =null;
        for(int i=0;i<busStationList.size();i++){
            if(busStationList.get(i).busStationName.equals(busStationName)){
                result = busStationList.get(i);
                return result;
            }

        }
        return result;
    }

    public List<BusStation> getAllStation(){
        return busStationList;
    }
    public List<BusRoute> getAllRoute(){
        return busRouteList;
    }

    public Pair<List<BusEstimateTime>,List<BusEstimateTime>> getBusEstimateTimeByRoute(BusRoute busRoute)throws  IOException{
        return busRoute.getEstimateTime();
    }

    public Pair<List<BusEstimateTime>,List<BusEstimateTime>> getBusEstimateTimeByStation(BusStation busStation)throws  IOException{
        return busStation.getEstimateTime();
    }
}
