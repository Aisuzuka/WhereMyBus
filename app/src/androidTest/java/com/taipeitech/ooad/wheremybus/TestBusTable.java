package com.taipeitech.ooad.wheremybus;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.BusInfo.BusURL;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusStation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestBusTable {
    BusTable busTable;
    @Before
    public void initBusTable() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        BusURL.ESTIMATE_TIME_URL ="http://192.168.0.102:8080/GetEstimateTime.gz";
        BusURL.ROUTE_URL="http://192.168.0.102:8080/GetRoute.gz";
        BusURL.STOP_URL="http://192.168.0.102:8080/GetSTOP.gz";
        busTable =BusTable.createBustable();

        assertEquals("com.taipeitech.ooad.wheremybus", appContext.getPackageName());
    }

    @Test
    public void testSearchRouteByName1() throws Exception {

        List<BusRoute> busRouteList = busTable.searchRouteByName("299");
        assertEquals(busRouteList.get(0).busRouteName, "299區");
        assertEquals(busRouteList.get(1).busRouteName, "299");
    }

    @Test
    public void testSearchRouteByName2() throws Exception {

        List<BusRoute> busRouteList = busTable.searchRouteByName("652");
        assertEquals(busRouteList.get(0).busRouteName, "652");
    }

    @Test
    public void testSearchStationByName() throws Exception {

        List<BusStation> busStationList = busTable.searchStationByName("中華路口");
        assertEquals( busStationList.get(0).busStationName, "中華路口");
    }


    @Test
    public void testGetRouteByName() throws Exception {

        BusRoute busRoute = busTable.getRouteByName("232");
        assertEquals( busRoute.busRouteName, "232");
    }

    @Test
    public void testGetStationByName() throws Exception {

        BusStation busStation = busTable.getStationByName("中華路口");
        assertEquals( busStation.busStationName, "中華路口");
    }



}
