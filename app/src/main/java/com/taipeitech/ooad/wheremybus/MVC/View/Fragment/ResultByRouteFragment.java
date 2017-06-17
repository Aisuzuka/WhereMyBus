package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.MVC.Controller.MainActivity;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;
import com.taipeitech.ooad.wheremybus.MVC.View.Adapter.ResultByBusLineAdapter;
import com.taipeitech.ooad.wheremybus.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class ResultByRouteFragment extends Fragment {
    Handler routeListener;
    Button goDistance, backDistance;
    View view;
    BusRoute route;
    ResultByBusLineAdapter stationAdapter;
    ArrayList<BusEstimateTime> goDistanceList = new ArrayList<>();
    ArrayList<BusEstimateTime> backDistanceList = new ArrayList<>();
    ArrayList<BusEstimateTime> busLineStationList = new ArrayList<>();
    ListView listView;
    View.OnClickListener distanceClickListener;
    boolean isGoDistance = true;
    final String BackDistance = "1";
    final String GoDistanch = "0";
    TextView busLineView;


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
    }

    private void loadDataToList(Pair<List<BusEstimateTime>, List<BusEstimateTime>> list) {
        goDistanceList.addAll(list.first);
        backDistanceList.addAll(list.second);
        loadDataToListView();
    }

    private void getBusLineFromPastPage() {
        route = new BusRoute();
        route.busRouteName = getArguments().getString("busRouteName");
        route.departure = getArguments().getString("departure");
        route.destination = getArguments().getString("destination");
        route.routeId = getArguments().getInt("routeId");
    }

    private void relizationListener() {
        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                clearAllListData();
                new getBusEstimateTimeByRoute().execute(route);
            }
        }, 30000);
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
        stationAdapter = new ResultByBusLineAdapter(MainActivity.getContext(), R.layout.station_item, busLineStationList);
        listView.setAdapter(stationAdapter);

        goDistance = (Button) view.findViewById(R.id.GoDistance);
        backDistance = (Button) view.findViewById(R.id.BackDistance);
        goDistance.setOnClickListener(distanceClickListener);
        backDistance.setOnClickListener(distanceClickListener);

        busLineView = (TextView) view.findViewById(R.id.BusLine);
        busLineView.setText("路線 " + route.busRouteName);
    }

    class getBusEstimateTimeByRoute extends AsyncTask<BusRoute, BusRoute, Pair<List<BusEstimateTime>,List<BusEstimateTime>> >{
        @Override
        protected Pair<List<BusEstimateTime>,List<BusEstimateTime>> doInBackground(BusRoute... params) {
            Pair<List<BusEstimateTime>,List<BusEstimateTime>> pair = null;
            try {
                BusTable busTable = BusTable.getBusTable();
                pair = busTable.getBusEstimateTimeByRoute(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return pair;
        }

        @Override
        protected void onPostExecute(Pair<List<BusEstimateTime>,List<BusEstimateTime>> pair) {
            loadDataToList(pair);
            super.onPostExecute(pair);
        }
    }
}
