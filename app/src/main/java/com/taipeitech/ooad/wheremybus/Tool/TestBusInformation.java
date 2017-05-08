package com.taipeitech.ooad.wheremybus.Tool;

import android.util.Log;

import com.taipeitech.ooad.wheremybus.BusInfo.BusInformationController;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;

/**
 * Created by user on 2017/5/8.
 */

public class TestBusInformation {
    BusInformationController busInformationController;
    public TestBusInformation(BusInformationController busInformationController){
        this.busInformationController=busInformationController;
    }

    public void testSearchBusRouteByName(){
        String test;
        String result;

        test ="299";
        result = "299區";
        if(busInformationController.searchRouteByName(test).get(0).busRouteName.equals(result)){
            Log.d("testSearch"+test,"pass result = "+result);
        }else {
            Log.d("testSearch"+test,"error   result= "+busInformationController.searchRouteByName(test).get(0).busRouteName);
        }

        test ="307";
        result = "307";
        if(busInformationController.searchRouteByName(test).get(0).busRouteName.equals(result)){
            Log.d("testSearch"+test,"pass result = "+result);
        }else {
            Log.d("testSearch"+test,"error   result= "+busInformationController.searchRouteByName(test).get(0).busRouteName);
        }


        test ="25";
        result = "256";
        if(busInformationController.searchRouteByName(test).get(0).busRouteName.equals(result)){
            Log.d("testSearch"+test,"pass result = "+result);
        }else {
            Log.d("testSearch"+test,"error   result= "+busInformationController.searchRouteByName(test).get(0).busRouteName);
        }
        test ="617";
        result = "617";
        if(busInformationController.searchRouteByName(test).get(0).busRouteName.equals(result)){
            Log.d("testSearch"+test,"pass result = "+result);
        }else {
            Log.d("testSearch"+test,"error   result= "+busInformationController.searchRouteByName(test).get(0).busRouteName);
        }


        test ="61";
        result = "615";
        if(busInformationController.searchRouteByName(test).get(0).busRouteName.equals(result)){
            Log.d("testSearch"+test,"pass result = "+result);
        }else {
            Log.d("testSearch"+test,"error   result= "+busInformationController.searchRouteByName(test).get(0).busRouteName);
        }

        test ="4";
        result = "42";
        if(busInformationController.searchRouteByName(test).get(0).busRouteName.equals(result)){
            Log.d("testSearch"+test,"pass result = "+result);
        }else {
            Log.d("testSearch"+test,"error   result= "+busInformationController.searchRouteByName(test).get(0).busRouteName);
        }


        test ="55";
        result = "550";
        if(busInformationController.searchRouteByName(test).get(0).busRouteName.equals(result)){
            Log.d("testSearch"+test,"pass result = "+result);
        }else {
            Log.d("testSearch"+test,"error   result= "+busInformationController.searchRouteByName(test).get(0).busRouteName);
        }



    }

    public void testSearchBusStationByName(){
        String test;
        String result;

        test ="中華";
        result = "中華路北站";
        if(busInformationController.searchStationByName(test).get(0).busStationName.equals(result)){
            Log.d("testSearch"+test,"pass result = 0"+result);
        }else {
            Log.d("testSearch"+test,"error   result= "+busInformationController.searchStationByName(test).get(0).busStationName);
        }

        test ="北投";
        result = "北投市場(公館路)";
        if(busInformationController.searchStationByName(test).get(0).busStationName.equals(result)){
            Log.d("testSearch"+test,"pass result = 0"+result);
        }else {
            Log.d("testSearch"+test,"error   result= "+busInformationController.searchStationByName(test).get(0).busStationName);
        }

        test ="故宮";
        result = "故宮路口";
        if(busInformationController.searchStationByName(test).get(0).busStationName.equals(result)){
            Log.d("testSearch"+test,"pass result = 0"+result);
        }else {
            Log.d("testSearch"+test,"error   result= "+busInformationController.searchStationByName(test).get(0).busStationName);
        }

        test ="正義郵";
        result = "正義郵局";
        if(busInformationController.searchStationByName(test).get(0).busStationName.equals(result)){
            Log.d("testSearch"+test,"pass result = 0"+result);
        }else {
            Log.d("testSearch"+test,"error   result= "+busInformationController.searchStationByName(test).get(0).busStationName);
        }

        test ="大";
        result = "大坪偉一";
        if(busInformationController.searchStationByName(test).get(0).busStationName.equals(result)){
            Log.d("testSearch"+test,"pass result = 0"+result);
        }else {
            Log.d("testSearch"+test,"error   result= "+busInformationController.searchStationByName(test).get(0).busStationName);
        }
    }

}
