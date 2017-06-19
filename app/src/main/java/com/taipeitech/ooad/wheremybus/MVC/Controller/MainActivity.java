package com.taipeitech.ooad.wheremybus.MVC.Controller;

import android.accessibilityservice.AccessibilityService;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.BusInfo.BusURL;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusStation;


import com.taipeitech.ooad.wheremybus.MVC.View.Fragment.IndexFragment;
import com.taipeitech.ooad.wheremybus.R;
import com.taipeitech.ooad.wheremybus.Reminder.Reminder;
import com.taipeitech.ooad.wheremybus.BusInfo.BusURL;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static Context context;



    public class MyThreadTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                BusTable.createBustable();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!Reminder.isAlive()) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Reminder.class);
                MainActivity.getContext().startService(intent);
            }
            super.onPostExecute(aVoid);
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

        BusURL.ESTIMATE_TIME_URL ="http://192.168.0.102:8080/GetEstimateTime.gz";
        BusURL.ROUTE_URL="http://192.168.0.102:8080/GetBUSDATA.gz";
        BusURL.STOP_URL="http://192.168.0.102:8080/GetSTOP.gz";
        MyThreadTask myThreadTask = new MyThreadTask();
        myThreadTask.execute();


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
    }
}
