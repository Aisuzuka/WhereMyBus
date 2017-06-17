package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.taipeitech.ooad.wheremybus.MVC.Model.BusStation;
import com.taipeitech.ooad.wheremybus.MVC.View.Adapter.ResultByStationAdapter;
import com.taipeitech.ooad.wheremybus.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class ResultByStationFragment extends Fragment {
    Handler routeListener;
    Button goDistance, backDistance;
    View view;
    BusStation station;
    ResultByStationAdapter stationAdapter;
    ArrayList<BusEstimateTime> goDistanceList = new ArrayList<>();
    ArrayList<BusEstimateTime> backDistanceList = new ArrayList<>();
    ArrayList<BusEstimateTime> busLineStationList = new ArrayList<>();
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
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void relizationListener() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                clearAllListData();
                new getBusEstimateTimeByRoute().execute(station);
            }
        }, 30000);
    }

    private void loadDataToList(Pair<List<BusEstimateTime>,List<BusEstimateTime>> list) {
        goDistanceList.addAll(list.first);
        backDistanceList.addAll(list.second);
        loadDataToListView();
    }

    private void getBusLineFromPastPage() {
        station = new BusStation();
        station.busStationName = getArguments().getString("busStationName");
        station.address = getArguments().getString("address");
        station.lon = getArguments().getFloat("lon");
        station.lat = getArguments().getFloat("lat");
        station.locationId = getArguments().getInt("locationId");
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

    private class getBusEstimateTimeByRoute extends AsyncTask<BusStation, BusStation, Pair<List<BusEstimateTime>, List<BusEstimateTime>>> {
        @Override
        protected Pair<List<BusEstimateTime>, List<BusEstimateTime>> doInBackground(BusStation... params) {
            Pair<List<BusEstimateTime>, List<BusEstimateTime>> pair = null;
            try {
                BusTable busTable = BusTable.getBusTable();
                pair = busTable.getBusEstimateTimeByStation(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return pair;
        }

        @Override
        protected void onPostExecute(Pair<List<BusEstimateTime>,List<BusEstimateTime>> pair) {
            loadDataToList(pair);
        }
    }
}
