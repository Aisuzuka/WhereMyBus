package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    Button goDistance, backDistance;
    View view;
    String busLine;
    BusLineStationAdapter stationAdapter;
    ArrayList<BusEstimateTime> goDistanceList = new ArrayList<BusEstimateTime>();
    ArrayList<BusEstimateTime> backDistanceList = new ArrayList<BusEstimateTime>();
    ArrayList<BusEstimateTime> busLineStationList = new ArrayList<BusEstimateTime>();
    ListView listView;
    View.OnClickListener distanceClickListener;
    boolean isGoDistance = true;
    final String BackDistance = "1";
    final String GoDistanch = "0";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBusLineFromPastPage();
        busEstimateTimeHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        clearAllListData();
                        ArrayList<BusEstimateTime> list = (ArrayList<BusEstimateTime>) msg.obj;
                        loadDataToList(list);
                        break;
                }
            }
        };
        getBusEstimoteTime();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.station_page, container, false);
        setClickListener();
        initButton();
        initListView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void loadDataToList(ArrayList<BusEstimateTime> list) {
        int size = list.size();
        for (int count = 0; count < size; count++) {
            loadDataToGoAndBackList(list.get(count));
        }
        loadDataToListView();
    }

    private void loadDataToGoAndBackList(BusEstimateTime item) {
        switch (item.goBack) {
            case GoDistanch:
                goDistanceList.add(item);
                break;
            case BackDistance:
                backDistanceList.add(item);
                break;
        }
    }

    private void getBusLineFromPastPage() {
        busLine = getArguments().getString("busLine");
    }

    private void getBusEstimoteTime() {
        SystemController.getBusEstimateTime(busEstimateTimeHandler, busLine);
    }

    private void clearAllListData() {
        goDistanceList.clear();
        backDistanceList.clear();
        busLineStationList.clear();
    }

    private void loadDataToListView() {
        if (isGoDistance) {
            loadGoToListView();
        } else {
            loadBackToListView();
        }
        refreashAdapter();
    }

    private void refreashAdapter() {
        stationAdapter.notifyDataSetChanged();
    }

    private void loadBackToListView() {
        busLineStationList.addAll(backDistanceList);
    }

    private void loadGoToListView() {
        busLineStationList.addAll(goDistanceList);
    }

    private void setClickListener() {
        distanceClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.GoDistance:
                        isGoDistance = true;
                        clearListViewData();
                        loadDataToListView();
                        break;
                    case R.id.BackDistance:
                        isGoDistance = false;
                        clearListViewData();
                        loadDataToListView();
                        break;
                }
            }
        };
    }

    private void clearListViewData() {
        busLineStationList.clear();
    }

    private void initButton() {
        goDistance = (Button) view.findViewById(R.id.GoDistance);
        backDistance = (Button) view.findViewById(R.id.BackDistance);
        goDistance.setOnClickListener(distanceClickListener);
        backDistance.setOnClickListener(distanceClickListener);
    }

    private void initListView() {
        listView = (ListView) view.findViewById(R.id.listView);
        stationAdapter = new BusLineStationAdapter(MainActivity.getContext(), R.layout.station_item, busLineStationList);
        listView.setAdapter(stationAdapter);
    }
}
