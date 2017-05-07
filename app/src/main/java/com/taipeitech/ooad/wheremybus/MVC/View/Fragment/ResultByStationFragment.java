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
import android.widget.TextView;

import com.taipeitech.ooad.wheremybus.Connecter.SystemController;
import com.taipeitech.ooad.wheremybus.MVC.Controller.MainActivity;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusStation;
import com.taipeitech.ooad.wheremybus.MVC.View.Adapter.ResultByStationAdapter;
import com.taipeitech.ooad.wheremybus.R;

import java.util.ArrayList;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class ResultByStationFragment extends Fragment {
    Handler routeListener;
    Button goDistance, backDistance;
    View view;
    BusStation station;
    ResultByStationAdapter stationAdapter;
    ArrayList<BusEstimateTime> goDistanceList = new ArrayList<BusEstimateTime>();
    ArrayList<BusEstimateTime> backDistanceList = new ArrayList<BusEstimateTime>();
    ArrayList<BusEstimateTime> busLineStationList = new ArrayList<BusEstimateTime>();
    ListView listView;
    View.OnClickListener distanceClickListener;
    boolean isGoDistance = true;
    final String BackDistance = "1";
    final String GoDistanch = "0";
    private TextView busLineView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBusLineFromPastPage();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.route_or_station_detail, container, false);
        setClickListener();
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        relizationListener();
        openRouteListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        closeRouteListener();
    }

    private void relizationListener() {
        routeListener = new Handler() {
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
    }

    private void closeRouteListener() {
        SystemController.cancelListenEstimateTimeByStation(routeListener, station);
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
        station = new BusStation();
        station.busStationName = getArguments().getString("busStationName");
        station.address = getArguments().getString("address");
        station.lon = getArguments().getFloat("lon");
        station.lat = getArguments().getFloat("lat");
        station.locationId = getArguments().getInt("locationId");
    }

    private void openRouteListener() {
        SystemController.getBusEstimateTimeByStation(routeListener, station);
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

    private void initView() {
        listView = (ListView) view.findViewById(R.id.listView);
        stationAdapter = new ResultByStationAdapter(MainActivity.getContext(), R.layout.station_item, busLineStationList);
        listView.setAdapter(stationAdapter);

        goDistance = (Button) view.findViewById(R.id.GoDistance);
        backDistance = (Button) view.findViewById(R.id.BackDistance);
        goDistance.setOnClickListener(distanceClickListener);
        backDistance.setOnClickListener(distanceClickListener);

        busLineView = (TextView) view.findViewById(R.id.BusLine);
        busLineView.setText("站牌 " + station.busStationName);
    }
}
