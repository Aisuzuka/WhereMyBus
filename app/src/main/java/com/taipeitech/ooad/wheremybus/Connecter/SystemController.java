package com.taipeitech.ooad.wheremybus.Connecter;

import android.os.Handler;

import com.taipeitech.ooad.wheremybus.Reminder.Reminder;
import com.taipeitech.ooad.wheremybus.BusInfo.BusInfoController;
import com.taipeitech.ooad.wheremybus.BusInfo.BusInformationController;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusStation;
import com.taipeitech.ooad.wheremybus.RoutePlan.RoutePlanController;

import java.util.List;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class SystemController {

    Reminder reminder;
    BusInfoController busInfoController;
    RoutePlanController routePlanController;
   public static BusInformationController busInformationController =new BusInformationController();

    public SystemController(){
        reminder = new Reminder();
        busInfoController = new BusInfoController();
        routePlanController = new RoutePlanController();
    }

    public static void setSystemInitFinishListener(Handler handler){
        busInformationController.setSystemInitFinishHandler(handler);
    }

    public static void setNetworkFailListener(Handler handler){
        busInformationController.setNetworkFailHandler(handler);
    }

    public static void getBusEstimateTimeByRoute(Handler handler, BusRoute busRoute) {
        busInformationController.listenEstimateTimeByRoute(busRoute,handler);
    }
    public static void getBusEstimateTimeByStation(Handler handler, BusStation busStation){
        busInformationController.listenEstimateTimeByStation(busStation,handler);
    }
    public  static void cancelListenEstimateTimeByRoute(Handler handler, BusRoute busRoute){
        busInformationController.cancelListenByRoute(busRoute,handler);
    }
    public static void cancelListenEstimateTimeByStation(Handler handler,BusStation busStation){
        busInformationController.cancelListenByStation(busStation, handler);
    }


    public static List<BusRoute> searchBusRouteByName(String name){
        return  busInformationController.searchRouteByName(name);
    }
    public static  List<BusStation> searchBusStstionByName(String name){
        return  busInformationController.searchStationByName(name);
    }

}
