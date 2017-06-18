package com.taipeitech.ooad.wheremybus.MVC.Model;

import android.content.Context;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class FrequenceCounter {

    private static DataBase dataBase =null;
    private static List<FrequenceRoute> frequenceRouteList =null;

    public  FrequenceCounter(Context context){
        if(dataBase==null){
            dataBase = new DataBase(context);
            frequenceRouteList = dataBase.getAllFrequenceRoute();
        }
    }

    public  BusRoute addBusRoute(BusRoute busRoute){
        FrequenceRoute frequenceRoute =null;
        for(int i=0;i<frequenceRouteList.size();i++){

            if(frequenceRouteList.get(i).getBusRouteName().equals( busRoute.busRouteName)){
                frequenceRoute =frequenceRouteList.get(i);
                frequenceRoute.addCount();
                dataBase.updateFrequenceRoute( frequenceRoute);
            }
        }
        if( frequenceRoute ==null){
            frequenceRoute =new FrequenceRoute();
            frequenceRoute.addCount();
            frequenceRoute.setBusRouteName(busRoute.busRouteName);
            dataBase.insertFrequenceRoute(frequenceRoute);
            frequenceRouteList.add(frequenceRoute);
        }

        return busRoute;

    }

    Comparator comparator =new Comparator<FrequenceRoute>() {
        @Override
        public int compare(FrequenceRoute o, FrequenceRoute t1) {
            if(o.getCount()>t1.getCount()){
                return -1;
            }else if(o.getCount()<t1.getCount()){
                return 1;
            }else {
                return 0;
            }
        }
    };


    public  List<BusRoute> getHeightFrequenceRoute(){
        BusTable busTable = BusTable.getBusTable();
        List<BusRoute> busRouteList =new ArrayList<>();
        BusRoute busRoute =null;
        java.util.Collections.sort(frequenceRouteList,comparator);
        for(int i=0;i<frequenceRouteList.size();i++){
            busRoute = busTable.getRouteByName(frequenceRouteList.get(i).getBusRouteName());
            if(busRoute !=null)
                busRouteList.add(busRoute);
        }




        return busRouteList;

    }

}
