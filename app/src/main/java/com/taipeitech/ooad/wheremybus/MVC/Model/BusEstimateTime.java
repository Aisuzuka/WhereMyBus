package com.taipeitech.ooad.wheremybus.MVC.Model;

import java.io.IOException;

/**
 * Created by user on 2017/5/4.
 */
public class BusEstimateTime {
    public BusStation busStation;
    public BusRoute busRoute;
    public String estimateTime;
    public String goBack;
    public void updateTime()throws IOException{
        busRoute.getEstimateTime();
    }
}
