package com.taipeitech.ooad.wheremybus.Connecter;

import android.os.Handler;

import com.taipeitech.ooad.wheremybus.Alarm.AlarmController;
import com.taipeitech.ooad.wheremybus.BusInfo.BusInfoController;
import com.taipeitech.ooad.wheremybus.RoutePlan.RoutePlanController;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class SystemController {

    AlarmController alarmController;
    BusInfoController busInfoController;
    RoutePlanController routePlanController;

    public SystemController(){
        alarmController = new AlarmController();
        busInfoController = new BusInfoController();
        routePlanController = new RoutePlanController();
    }

    public static void getBusEstimateTime(Handler busLineHandler, String busLine) {

    }
}
