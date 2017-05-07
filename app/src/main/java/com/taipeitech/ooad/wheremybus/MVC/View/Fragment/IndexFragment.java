package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.taipeitech.ooad.wheremybus.R;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class IndexFragment extends Fragment {
    View view;
    Button searchByBusLine, NearStation, alarmList;
    View.OnClickListener indexClickListener;
    private Button searchByStation;

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
                    case R.id.SearchByBusLine:
                        fragment = new SearchByRouteFragment();
                        break;
                    case R.id.SearchByStation:
                        fragment = new SearchByStationFragment();
                        break;
                    case R.id.NearStationButton:
                        fragment = new NearStationFragment();
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
        searchByBusLine.setOnClickListener(indexClickListener);
        searchByStation.setOnClickListener(indexClickListener);
        NearStation.setOnClickListener(indexClickListener);
        alarmList.setOnClickListener(indexClickListener);
    }

    private void initView() {
        searchByBusLine = (Button) view.findViewById(R.id.SearchByBusLine);
        searchByStation = (Button) view.findViewById(R.id.SearchByStation);
        NearStation = (Button) view.findViewById(R.id.NearStationButton);
        alarmList = (Button) view.findViewById(R.id.alarmListButton);
    }
}
