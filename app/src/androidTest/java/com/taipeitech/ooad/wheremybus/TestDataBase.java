package com.taipeitech.ooad.wheremybus;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.BusInfo.BusURL;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;
import com.taipeitech.ooad.wheremybus.MVC.Model.DataBase;
import com.taipeitech.ooad.wheremybus.MVC.Model.FrequenceRoute;
import com.taipeitech.ooad.wheremybus.Reminder.BusArrivalEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 2017/6/19.
 */
@RunWith(AndroidJUnit4.class)
public class TestDataBase {

    DataBase dataBase;
    @Before
    public void initBusTable() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        dataBase =new DataBase(appContext);

       List<BusArrivalEvent> busArrivalEventList = dataBase.getAll();
        for(int i=0;i<busArrivalEventList.size();i++){
            dataBase.delete(busArrivalEventList.get(i).getId()) ;
        }

        List<FrequenceRoute> frequenceRouteList =dataBase.getAllFrequenceRoute();

        for (int i=0; i<frequenceRouteList.size();i++){
            dataBase.deleteBusRoute(frequenceRouteList.get(i));
        }
    }

    @Test
    public void testCRUDbusArriveEvent() throws Exception {
            BusArrivalEvent busArrivalEvent =new BusArrivalEvent();
        busArrivalEvent.setTargetBusStation("中華路口");
        busArrivalEvent.setTargetBusRoute("299");
        busArrivalEvent.setNotificationTime(20);
        busArrivalEvent.setReferenceTime(50000);
        busArrivalEvent.setGoDistance(0);
        busArrivalEvent = dataBase.insertBusArrivalEvent(busArrivalEvent);

        BusArrivalEvent busArrivalEventDB=null;
        List<BusArrivalEvent> busArrivalEventList = dataBase.getAll();
        for(int i=0;i<busArrivalEventList.size();i++){
          if( busArrivalEventList.get(i).getId() ==busArrivalEvent.getId()){
              busArrivalEventDB = busArrivalEventList.get(i);
          }
        }

        assertEquals(busArrivalEventDB.getTargetBusRoute(), busArrivalEvent.getTargetBusRoute());
        assertEquals(busArrivalEventDB.getTargetBusStation(), busArrivalEvent.getTargetBusStation());
        assertEquals(busArrivalEventDB.getNotificationTime(), busArrivalEvent.getNotificationTime());
        assertEquals(busArrivalEventDB.getReferenceTime(), busArrivalEvent.getReferenceTime());

        busArrivalEvent.setNotificationTime(30);
        dataBase.update(busArrivalEvent);
        busArrivalEventList = dataBase.getAll();
        for(int i=0;i<busArrivalEventList.size();i++){
            if( busArrivalEventList.get(i).getId() ==busArrivalEvent.getId()){
                busArrivalEventDB = busArrivalEventList.get(i);
            }
        }

        assertEquals(busArrivalEventDB.getTargetBusRoute(), busArrivalEvent.getTargetBusRoute());
        assertEquals(busArrivalEventDB.getTargetBusStation(), busArrivalEvent.getTargetBusStation());
        assertEquals(busArrivalEventDB.getNotificationTime(), busArrivalEvent.getNotificationTime());
        assertEquals(busArrivalEventDB.getReferenceTime(), busArrivalEvent.getReferenceTime());



        dataBase.delete(busArrivalEvent.getId());

        busArrivalEventList = dataBase.getAll();
        boolean find=false;
        for(int i=0;i<busArrivalEventList.size();i++){
            if( busArrivalEventList.get(i).getId() ==busArrivalEvent.getId()){
                find=true;
            }
        }
        assertEquals(find, false);



    }

    @Test
    public void testCRUDFrequenceRoute() throws Exception {
        FrequenceRoute frequenceRoute =new FrequenceRoute();
        frequenceRoute.setBusRouteName("299");
        frequenceRoute.setCount(1);

        frequenceRoute = dataBase.insterBusRoute(frequenceRoute);


        FrequenceRoute frequenceRouteDB=null;
        List<FrequenceRoute> frequenceRouteList = dataBase.getAllFrequenceRoute();
        for(int i=0;i< frequenceRouteList .size();i++){
            if(  frequenceRouteList.get(i).getId() ==frequenceRoute.getId()){
                frequenceRouteDB = frequenceRouteList.get(i);
            }
        }

        assertEquals(frequenceRouteDB.getBusRouteName(), frequenceRoute.getBusRouteName());
        assertEquals(frequenceRouteDB.getCount(), frequenceRoute.getCount());


        frequenceRoute.setCount(2);
        dataBase.updateBusRoute(frequenceRoute);
        frequenceRouteList = dataBase.getAllFrequenceRoute();
        for(int i=0;i<frequenceRouteList.size();i++){
            if( frequenceRouteList.get(i).getId() ==frequenceRoute.getId()){
                frequenceRouteDB = frequenceRouteList.get(i);
            }
        }

        assertEquals(frequenceRouteDB.getBusRouteName(), frequenceRoute.getBusRouteName());
        assertEquals(frequenceRouteDB.getCount(), frequenceRoute.getCount());



        dataBase.deleteBusRoute(frequenceRoute);

        frequenceRouteList = dataBase.getAllFrequenceRoute();
        boolean find=false;
        for(int i=0;i< frequenceRouteList.size();i++){
            if(  frequenceRouteList.get(i).getId() == frequenceRoute.getId()){
                find=true;
            }
        }
        assertEquals(find, false);



    }



}
