package com.taipeitech.ooad.wheremybus.MVC.Controller;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Handler;

import android.content.Context;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.taipeitech.ooad.wheremybus.BusInfo.BusInformationController;
import com.taipeitech.ooad.wheremybus.Connecter.SystemController;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.MVC.View.Fragment.IndexFragment;
import com.taipeitech.ooad.wheremybus.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static Context context;

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
//        Handler DOFindAttributehandler =  new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                List<BusEstimateTime> MsgString = (List<BusEstimateTime>)msg.obj;
//                for(int i =0;i<MsgString.size();i++){
//                    Log.d("Mass",MsgString.get(i).busStation.busStationName);
//                }
//        }
//
//        };
//        SystemController.getBusEstimateTime(DOFindAttributehandler,"299");
//        Handler handler=new Handler();
//        BusInformationController busInformationController=new BusInformationController(handler);
//        busInformationController.searchLineByName("299");


    }

    @Override
    protected void onStart() {
        super.onStart();
        context = this;
    }
}
