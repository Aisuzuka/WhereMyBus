package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.R;

import java.util.List;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class IndexFragment extends Fragment {
    View view;
    Button busSearch, stationSearch, alarmList;
    View.OnClickListener indexClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.index, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        setIndexClickListener();
        setViewListener();

    }

    private void setIndexClickListener() {
        indexClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                switch (v.getId()) {
                    case R.id.busSearchButton:
                        fragment = new BusLinePageFragment();
                        fragment = new BusLineStationFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("busLine", "299");
                        fragment.setArguments(bundle);
                        break;
                    case R.id.stationSearchButton:
                        fragment = new StationListFragment();
                        break;
                    case R.id.alarmListButton:
                        fragment = new AlarmListFragment();
                        break;
                }
                changeView(fragment);
            }
        };
    }


    public void changeView(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.fragment, fragment);
        ft.addToBackStack("");
        ft.commit();
    }

    private void setViewListener() {
        busSearch.setOnClickListener(indexClickListener);
        stationSearch.setOnClickListener(indexClickListener);
        alarmList.setOnClickListener(indexClickListener);
    }

    private void initView() {
        busSearch = (Button) view.findViewById(R.id.busSearchButton);
        stationSearch = (Button) view.findViewById(R.id.stationSearchButton);
        alarmList = (Button) view.findViewById(R.id.alarmListButton);
    }
}
