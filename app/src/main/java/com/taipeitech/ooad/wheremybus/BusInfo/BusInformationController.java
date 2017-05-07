package com.taipeitech.ooad.wheremybus.BusInfo;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Pair;

import com.taipeitech.ooad.wheremybus.Tool.DownloadJSONFromURL;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusStation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 2017/5/4.
 */
public class BusInformationController {
    private BusInformationController synchronizedLock  = this;
    public Timer timer;
    public Handler handler;
    private int lineId;
    private String lineName;
    private JSONObject busData =null;
    private JSONObject stationData =null;



    private Map<Integer,String> busIdName=new HashMap<Integer, String>();

    public  BusInformationController(Handler handler){
        this.handler=handler;
        timer = new Timer(true);
        timer.schedule(new MyTimerTask(), 0, 30000);

    }

    private Map<String,Integer> busRouteToIdMap;
    private Map<String,Integer> busStationToIdMap;
    private Map<Integer,Integer> stopIdToBusStationLocationIdMap;


    private List<BusRoute> busRouteList;
    private List<BusStation> busStationList;
    private Map<Integer,BusRoute> busRouteMap;
    private Map<String,BusStation> busStationMap;


    private List<Pair<Integer,Handler>> listenBusRoute =new ArrayList<>();
    private List<Pair<String,Handler>> listenBusStation = new ArrayList<>();


    JSONObject busStationData;
    private Map<Integer,List<BusEstimateTime>> busEstimateTimeByRouteMap =new HashMap<>();
    private Map<Integer,Map<Integer,BusEstimateTime>> stopIdToBusEstimateTimeByRouteMap =new HashMap<>();

    private Map<String,List<BusEstimateTime>> busEstimateTimeByStationMap =new HashMap<>();
    private Map<String,Map<Integer,BusEstimateTime>> stopIdToBusEstimateTimeByStationMap =new HashMap<>();

    public void createEstimateTimeByStationList(String busStation){
        List<BusEstimateTime> busEstimateTimeList = new ArrayList<>();
        busEstimateTimeByStationMap.put(busStation, busEstimateTimeList);

        Map<Integer,BusEstimateTime> busEstimateTimeMap =new HashMap<>();
        stopIdToBusEstimateTimeByStationMap.put(busStation,busEstimateTimeMap);
        try {
            JSONArray busStationList = busStationData.getJSONArray("BusInfo");

            for(int i=0;i<busStationList.length();i++){
                if(busStationList.getJSONObject(i).getString("nameZh").equals(busStation) && busStationList.getJSONObject(i).getString("goBack").equals("0")){
                    BusEstimateTime busEstimateTime =new BusEstimateTime();
                    busEstimateTime.goBack =busStationList.getJSONObject(i).getString("goBack");
                    busEstimateTime.busRoute=busRouteMap.get(busStationList.getJSONObject(i).getInt("routeId"));
                    busEstimateTime.busStation=busStationMap.get(busStationList.getJSONObject(i).getString("nameZh"));
                    busEstimateTimeList.add(busEstimateTime);
                    busEstimateTimeMap.put(busStationList.getJSONObject(i).getInt("Id"),busEstimateTime);
                }
            }
            for(int i=0;i<busStationList.length();i++){
                if(busStationList.getJSONObject(i).getString("nameZh").equals(busStation) && busStationList.getJSONObject(i).getString("goBack").equals("1")){
                    BusEstimateTime busEstimateTime =new BusEstimateTime();
                    busEstimateTime.goBack =busStationList.getJSONObject(i).getString("goBack");
                    busEstimateTime.busRoute=busRouteMap.get(busStationList.getJSONObject(i).getInt("routeId"));
                    busEstimateTime.busStation=busStationMap.get(busStationList.getJSONObject(i).getString("nameZh"));
                    busEstimateTimeList.add(busEstimateTime);
                    busEstimateTimeMap.put(busStationList.getJSONObject(i).getInt("Id"),busEstimateTime);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void createEstimateTimeByRouteList(int busRoute){
        List<BusEstimateTime> busEstimateTimeList = new ArrayList<>();
        busEstimateTimeByRouteMap.put(busRoute, busEstimateTimeList);

        Map<Integer,BusEstimateTime> busEstimateTimeMap =new HashMap<>();
        stopIdToBusEstimateTimeByRouteMap.put(busRoute,busEstimateTimeMap);
        try {
            JSONArray busStationList = busStationData.getJSONArray("BusInfo");

            for(int i=0;i<busStationList.length();i++){
                if(busStationList.getJSONObject(i).getInt("routeId")==busRoute){
                    busEstimateTimeList.add(null);
                }
            }


            for(int i=0;i<busStationList.length();i++){
                if(busStationList.getJSONObject(i).getInt("routeId")==busRoute){
                    BusEstimateTime busEstimateTime =new BusEstimateTime();
                    busEstimateTime.goBack =busStationList.getJSONObject(i).getString("goBack");
                    busEstimateTime.busRoute=busRouteMap.get(busStationList.getJSONObject(i).getInt("routeId"));
                    busEstimateTime.busStation=busStationMap.get(busStationList.getJSONObject(i).getString("nameZh"));
                    busEstimateTimeList.set(busStationList.getJSONObject(i).getInt("seqNo"),busEstimateTime);
                    busEstimateTimeMap.put(busStationList.getJSONObject(i).getInt("Id"),busEstimateTime);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateBusInformation() {
        DownloadJSONFromURL downloadJSONFromURL =new DownloadJSONFromURL();
        JSONObject busRouteData = downloadJSONFromURL.downloadURL("http://data.taipei/bus/ROUTE");
        busRouteList =new ArrayList<>();
        busRouteMap =new HashMap<>();
        busRouteToIdMap =new HashMap<>();
        try {
            JSONArray busRouteList = busRouteData.getJSONArray("BusInfo");
            for(int i = 0; i< busRouteList.length(); i++){
                int id= busRouteList.getJSONObject(i).getInt("Id");
                if(busRouteMap.get(id)==null){
                    BusRoute busRoute=new BusRoute();
                    busRoute.busRouteName = busRouteList.getJSONObject(i).getString("nameZh");
                    busRoute.departure = busRouteList.getJSONObject(i).getString("departureZh");
                    busRoute.destination = busRouteList.getJSONObject(i).getString("destinationZh");
                    busRoute.routeId=id;
                    this.busRouteList.add(busRoute);
                    this.busRouteMap.put(id,busRoute);
                    this.busRouteToIdMap.put(busRoute.busRouteName,id);
                    Log.d("Route Information",busRoute.busRouteName +"   "+busRoute.departure+"  "+busRoute.destination);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        busStationData = downloadJSONFromURL.downloadURL("http://data.taipei/bus/Stop");
        busStationList =new ArrayList<>();
        busStationMap =new HashMap<>();
        stopIdToBusStationLocationIdMap =new HashMap<>();
        busStationToIdMap =new HashMap<>();
        try {
            JSONArray busStationList = busStationData.getJSONArray("BusInfo");
            for (int i =0 ;i<busStationList.length();i++){
                String stationName =busStationList.getJSONObject(i).getString("nameZh");
                if(busStationMap.get(stationName)==null){
                    BusStation busStation = new BusStation();
                    busStation.busStationName = busStationList.getJSONObject(i).getString("nameZh");
                    busStation.address=busStationList.getJSONObject(i).getString("address");
                    busStation.lat= Float.valueOf( busStationList.getJSONObject(i).getString("showLat"));
                    busStation.lon= Float.valueOf( busStationList.getJSONObject(i).getString("showLon"));
                    this.busStationList.add(busStation);
                    this.busStationMap.put(stationName,busStation);
                    Log.d("Station Information",busStation.busStationName+"   "+busStation.address+"   "+busStation.lon+"   "+busStation.lat);
                }



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getEstimateTime(){
        DownloadJSONFromURL downloadJSONFromURL =new DownloadJSONFromURL();
        JSONObject busEstimateTimeData = downloadJSONFromURL.downloadURL("http://data.taipei/bus/EstimateTime");
        try {
            JSONArray busEstimateTimeList =busEstimateTimeData.getJSONArray("BusInfo");
            for (int j =0;j<listenBusRoute.size();j++){
                List<BusEstimateTime> busEstimateTimeByRouteList = busEstimateTimeByRouteMap.get(listenBusRoute.get(j));
                Map<Integer,BusEstimateTime> busEstimateTimeMap = stopIdToBusEstimateTimeByRouteMap.get(listenBusRoute.get(j));
                if (busEstimateTimeMap ==null){
                    createEstimateTimeByRouteList(listenBusRoute.get(j).first);
                    busEstimateTimeMap = stopIdToBusEstimateTimeByRouteMap.get(listenBusRoute.get(j));
                    busEstimateTimeByRouteList = busEstimateTimeByRouteMap.get(listenBusRoute.get(j));
                }
                for(int i=0;i<busEstimateTimeList.length();i++){
                    if(busEstimateTimeList.getJSONObject(i).getInt("RouteID")==listenBusRoute.get(j).first){

                        BusEstimateTime busEstimateTime =busEstimateTimeMap.get(busEstimateTimeList.getJSONObject(i).getInt("StopID"));
                        busEstimateTime.estimateTime =busEstimateTimeList.getJSONObject(i).getString("EstimateTime");
                    }
                }

                for(int i=0;i<busEstimateTimeByRouteList.size();i++){
                    BusEstimateTime busEstimateTime= busEstimateTimeByRouteList.get(i);
                        Log.d("BusEstimateTimeByRoute",busEstimateTime.busRoute.busRouteName+"   "+busEstimateTime.busStation.busStationName+"   "+busEstimateTime.estimateTime+"   "+busEstimateTime.goBack);

                }
               Handler handler = listenBusRoute.get(j).second;
                Message message;
                message = handler.obtainMessage(1,(ArrayList<BusEstimateTime>)busEstimateTimeByRouteList);
                handler.sendMessage(message);

            }


            for(int j=0;j<listenBusStation.size();j++){
                List<BusEstimateTime> busEstimateTimeByRouteList = busEstimateTimeByStationMap.get(listenBusRoute.get(j));
                Map<Integer,BusEstimateTime> busEstimateTimeMap = stopIdToBusEstimateTimeByStationMap.get(listenBusRoute.get(j));
                if (busEstimateTimeMap ==null){
                    createEstimateTimeByStationList(listenBusStation.get(j).first);
                    busEstimateTimeMap = stopIdToBusEstimateTimeByStationMap.get(listenBusStation.get(j));
                    busEstimateTimeByRouteList = busEstimateTimeByStationMap.get(listenBusStation.get(j));
                }
                for(int i=0;i<busEstimateTimeList.length();i++){
                    if(busEstimateTimeMap.get(busEstimateTimeList.getJSONObject(i).getInt("StopID"))!=null){

                        BusEstimateTime busEstimateTime =busEstimateTimeMap.get(busEstimateTimeList.getJSONObject(i).getInt("StopID"));
                        busEstimateTime.estimateTime =busEstimateTimeList.getJSONObject(i).getString("EstimateTime");
                    }
                }
                for(int i=0;i<busEstimateTimeByRouteList.size();i++){
                    BusEstimateTime busEstimateTime= busEstimateTimeByRouteList.get(i);
                    Log.d("BusEstimateTimeByRoute",busEstimateTime.busRoute.busRouteName+"   "+busEstimateTime.busStation.busStationName+"   "+busEstimateTime.estimateTime+"   "+busEstimateTime.goBack);
                }
                Handler handler = listenBusStation.get(j).second;
                Message message;
                message = handler.obtainMessage(1,(ArrayList<BusEstimateTime>)busEstimateTimeByRouteList);
                handler.sendMessage(message);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class MyTimerTask extends TimerTask
    {
        public void run()
        {
            synchronized (synchronizedLock){
                updateBusInformation();
                searchRouteByName("29");
                searchStationByName("中華");
                getEstimateTime();
            }

        }
    };

    public  void searchLineByName(String name ,Handler handler){
        listenBusRoute.add(new Pair<Integer, Handler>(11411,handler));
    }

    public void listenEstimateTimeByRoute(BusRoute busRoute,Handler handler){
        listenBusRoute.add(new Pair<Integer, Handler>(busRoute.routeId,handler));
    }
    public void cancelListenByRoute(BusRoute busRoute,Handler handler){
        for (int i=0;i<listenBusRoute.size();i++){
            if(listenBusRoute.get(i).first==busRoute.routeId&&listenBusRoute.get(i).second==handler){
                listenBusRoute.remove(i);
                break;
            }
        }
    }
    public void listenEstimateTimeByStation(BusStation busStation,Handler handler){

        listenBusStation.add(new Pair<String, Handler>(busStation.busStationName,handler));
    }
    public void cancelListenByStation(BusStation busStation,Handler handler){
        for (int i=0;i<listenBusStation.size();i++){
            if(listenBusStation.get(i).first.equals(busStation.busStationName)&&listenBusStation.get(i).second==handler){
                listenBusStation.remove(i);
                break;
            }
        }
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

    public List<BusStation> searchStationByName(String busRouteName){
        List<BusStation> result =new ArrayList<>();
        int strLength =busRouteName.length();
        for(int i=0;i<busStationList.size();i++){
            if(busStationList.get(i).busStationName.length()>=strLength){
                if(busStationList.get(i).busStationName.substring(0,strLength).equals(busRouteName)){
                    result.add(busStationList.get(i));

                }
            }

        }
        for (int i=0;i<result.size();i++){
            Log.d("searchResult",result.get(i).busStationName);
        }

        return result;
    }







}
