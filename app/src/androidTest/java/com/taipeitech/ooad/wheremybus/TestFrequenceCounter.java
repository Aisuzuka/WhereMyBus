package com.taipeitech.ooad.wheremybus;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.MVC.Model.FrequenceCounter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pyakuren-Chienhua on 2017/6/19.
 */
@RunWith(AndroidJUnit4.class)
public class TestFrequenceCounter {
    Context appContext;
    FrequenceCounter frequenceCounter;
    BusTable busTable;

    @Before
    public void initTest() throws Exception{
        appContext = InstrumentationRegistry.getTargetContext();
        frequenceCounter = new FrequenceCounter(appContext);
        busTable = BusTable.createBustable();
    }

    @Test
    public void checkBusRoutesAdded() throws Exception{
        frequenceCounter.addBusRoute(busTable.getRouteByName("299"));
        assertNotNull(frequenceCounter.getHeightFrequenceRoute());
        Log.e("Test","");
    }
}
