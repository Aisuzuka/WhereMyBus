package com.taipeitech.ooad.wheremybus;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.BusInfo.BusURL;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusStation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by user on 2017/6/19.
 */
@RunWith(AndroidJUnit4.class)
public class TestBusRoute {
    BusTable busTable;
    BusRoute busRoute;
    List<BusEstimateTime>  goEstimateTime;
    List<BusEstimateTime>  backEstimateTime;
    @Before
    public void initBusRoute() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        BusURL.ESTIMATE_TIME_URL ="http://192.168.0.102:8080/GetEstimateTime.gz";
        BusURL.ROUTE_URL="http://192.168.0.102:8080/GetRoute.gz";
        BusURL.STOP_URL="http://192.168.0.102:8080/GetSTOP.gz";
        busTable =BusTable.createBustable();
        busRoute = busTable.getRouteByName("299");
        Pair<List<BusEstimateTime>,List<BusEstimateTime>> estimateTimeList = busRoute.getEstimateTime();

        goEstimateTime = estimateTimeList.first;

        backEstimateTime = estimateTimeList.second;
    }

    @Test
    public void testBusRouteGoList() throws Exception {

        List<BusStation> busStationList = busRoute.getBusRouteGoList();
        assertEquals(busStationList.get(0).busStationName, "新莊站");
        assertEquals(busStationList.get(1).busStationName, "建國後港路口");
        assertEquals(busStationList.get(2).busStationName, "建國一路");
        assertEquals(busStationList.get(3).busStationName, "捷運輔大站(建國一路)");
        assertEquals(busStationList.get(4).busStationName, "捷運輔大站");
        assertEquals(busStationList.get(5).busStationName, "盲人重建院");
        assertEquals(busStationList.get(6).busStationName, "海山里");

    }

    @Test
    public void testBusRouteBackList() throws Exception {

        List<BusStation> busStationList = busRoute.getBusRouteBackList();
        assertEquals(busStationList.get(0).busStationName, "永春高中");
        assertEquals(busStationList.get(1).busStationName, "松山商職(松山)");
        assertEquals(busStationList.get(2).busStationName, "永春公寓");
        assertEquals(busStationList.get(3).busStationName, "永春里");
        assertEquals(busStationList.get(4).busStationName, "永吉國小");
        assertEquals(busStationList.get(5).busStationName, "永春國小");
        assertEquals(busStationList.get(6).busStationName, "國民住宅");

    }
    @Test
    public void testGetEstimateTime11() throws Exception {

        BusEstimateTime busEstimateTime = goEstimateTime.get(0);
        assertEquals(busEstimateTime.busStation.busStationName, "新莊站");
        assertEquals(busEstimateTime.estimateTime, "285");
        assertEquals(busEstimateTime.goBack, "0");
    }
    @Test
    public void testGetEstimateTime12() throws Exception {

        BusEstimateTime busEstimateTime = goEstimateTime.get(1);
        assertEquals(busEstimateTime.busStation.busStationName, "建國後港路口");


        assertEquals(busEstimateTime.estimateTime, "370");
        assertEquals(busEstimateTime.goBack, "0");
    }
    @Test
    public void testGetEstimateTime13() throws Exception {

        BusEstimateTime  busEstimateTime = goEstimateTime.get(2);
        assertEquals(busEstimateTime.busStation.busStationName, "建國一路");
        assertEquals(busEstimateTime.estimateTime, "399");
        assertEquals(busEstimateTime.goBack, "0");
    }
    @Test
    public void testGetEstimateTime14() throws Exception {

        BusEstimateTime busEstimateTime = busEstimateTime = goEstimateTime.get(3);
        assertEquals(busEstimateTime.busStation.busStationName, "捷運輔大站(建國一路)");
        assertEquals(busEstimateTime.estimateTime, "424");
        assertEquals(busEstimateTime.goBack, "0");
    }
    @Test
    public void testGetEstimateTime15() throws Exception {

        BusEstimateTime busEstimateTime =  busEstimateTime = goEstimateTime.get(4);
        assertEquals(busEstimateTime.busStation.busStationName, "捷運輔大站");
        assertEquals(busEstimateTime.estimateTime, "570");
        assertEquals(busEstimateTime.goBack, "0");

    }
    @Test
    public void testGetEstimateTime21() throws Exception {

        BusEstimateTime  busEstimateTime = backEstimateTime.get(0);
        assertEquals(busEstimateTime.busStation.busStationName, "永春高中");
        assertEquals(busEstimateTime.estimateTime, "497");
        assertEquals(busEstimateTime.goBack, "1");
    }
    @Test
    public void testGetEstimateTime22() throws Exception {

        BusEstimateTime  busEstimateTime = backEstimateTime.get(1);
        assertEquals(busEstimateTime.busStation.busStationName, "松山商職(松山)");
        assertEquals(busEstimateTime.estimateTime, "517");
        assertEquals(busEstimateTime.goBack, "1");
    }
    @Test
    public void testGetEstimateTime23() throws Exception {

        BusEstimateTime  busEstimateTime = backEstimateTime.get(2);
        assertEquals(busEstimateTime.busStation.busStationName, "永春公寓");
        assertEquals(busEstimateTime.estimateTime, "603");
        assertEquals(busEstimateTime.goBack, "1");
    }
    @Test
    public void testGetEstimateTime24() throws Exception {

        BusEstimateTime  busEstimateTime = backEstimateTime.get(3);
        assertEquals(busEstimateTime.busStation.busStationName, "永春里");
        assertEquals(busEstimateTime.estimateTime, "185");
        assertEquals(busEstimateTime.goBack, "1");
    }
    @Test
    public void testGetEstimateTime25() throws Exception {

        BusEstimateTime  busEstimateTime = backEstimateTime.get(4);
        assertEquals(busEstimateTime.busStation.busStationName, "永吉國小");
        assertEquals(busEstimateTime.estimateTime, "202");
        assertEquals(busEstimateTime.goBack, "1");
    }
    @Test
    public void testGetEstimateTime26() throws Exception {

        BusEstimateTime  busEstimateTime = backEstimateTime.get(5);
        assertEquals(busEstimateTime.busStation.busStationName, "永春國小");
        assertEquals(busEstimateTime.estimateTime, "173");
        assertEquals(busEstimateTime.goBack, "1");
    }
    @Test
    public void testGetEstimateTime27() throws Exception {

        BusEstimateTime  busEstimateTime = backEstimateTime.get(6);
        assertEquals(busEstimateTime.busStation.busStationName, "國民住宅");
        assertEquals(busEstimateTime.estimateTime, "66");
        assertEquals(busEstimateTime.goBack, "1");
    }
    @Test
    public void testGetEstimateTime28() throws Exception {

        BusEstimateTime  busEstimateTime = backEstimateTime.get(7);
        assertEquals(busEstimateTime.busStation.busStationName, "松隆路口");
        assertEquals(busEstimateTime.estimateTime, "33");
        assertEquals(busEstimateTime.goBack, "1");
    }









}

