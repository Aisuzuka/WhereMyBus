package com.taipeitech.ooad.wheremybus.MVC.Controller;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Intent;

import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.taipeitech.ooad.wheremybus.Reminder.Reminder;
import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusStation;
import com.taipeitech.ooad.wheremybus.MVC.View.Fragment.IndexFragment;
import com.taipeitech.ooad.wheremybus.R;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    public  class  MyThreadTask extends Thread
    {
        public void run()
        {
            try {
                BusTable busTable = BusTable.createBustable();
                List<BusRoute> busRouteList = busTable.searchRouteByName("299");
                busRouteList.get(1).getEstimateTime();
                List<BusStation> busStationList = busTable.searchStationByName("中華路口");
                busStationList.get(0).getEstimateTime();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_frame);

        FragmentManager fragmentManager = getFragmentManager();
        IndexFragment indexFragment = new IndexFragment();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment, indexFragment);
        ft.commit();

        MyThreadTask myThreadTask =new MyThreadTask();
        myThreadTask.start();



//        Handler handler =new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//               String message = (String)msg.obj;
//                if(message.equals("DataReady")){
//                    TestBusInformation testBusInformation =new TestBusInformation(SystemController.busInformationController);
//                    Log.d("test" , "testSearchBusRouteByName");
//                    testBusInformation.testSearchBusRouteByName();
//                    Log.d("test" , "testSearchBusStationByName");
//                    testBusInformation.testSearchBusStationByName();
//                }
//            }
//        };
//        SystemController.setSystemInitFinishListener(handler);

    }


    @Override
    protected void onStart() {
        super.onStart();
        context = this;
        if(!Reminder.isAlive()) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Reminder.class);
            this.startService(intent);
        }
    }
}
