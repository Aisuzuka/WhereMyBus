package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.taipeitech.ooad.wheremybus.Connecter.SystemController;
import com.taipeitech.ooad.wheremybus.MVC.Controller.MainActivity;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.MVC.View.Adapter.BusLineStationAdapter;
import com.taipeitech.ooad.wheremybus.R;

import java.util.ArrayList;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class BusLineStationFragment extends Fragment {
    Handler busEstimateTimeHandler;
    View view;
    String busLine;
    BusLineStationAdapter stationAdapter;
    ArrayList<BusEstimateTime> stationList;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        busLine = savedInstanceState.getString("busLine");
        listView = (ListView) view.findViewById(R.id.listView);
        busEstimateTimeHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch(msg.arg1){
                    case 1:
                        stationList = (ArrayList<BusEstimateTime>) msg.obj;
                        stationAdapter.notifyDataSetChanged();
                }
            }
        };
        //SystemController.getBusEstimateTime(busEstimateTimeHandler, busLine);
    }

    @Override
    public void onStart() {
        super.onStart();
        stationAdapter = new BusLineStationAdapter(MainActivity.getContext(), R.layout.station_item, stationList);
        listView.setAdapter(stationAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.station_page, container, false);
        return view;
    }
}
