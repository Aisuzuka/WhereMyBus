package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.taipeitech.ooad.wheremybus.MVC.Controller.MainActivity;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;
import com.taipeitech.ooad.wheremybus.MVC.Model.FrequenceCounter;
import com.taipeitech.ooad.wheremybus.R;

import java.util.ArrayList;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class FavoriteRouteFragment extends Fragment {
    ListView listView;
    TextView title;
    ArrayAdapter<String> listAdapter;
    ArrayList<BusRoute> favoriteRoute;
    ArrayList<String> stringList;
    private ResultByRouteFragment fragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        title = (TextView) view.findViewById(R.id.title);

        title.setText("查詢紀錄");
        listAdapter = new ArrayAdapter(MainActivity.getContext(), android.R.layout.simple_list_item_1, stringList);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusRoute busRoute = favoriteRoute.get(position);
                initFragment(busRoute);
                changeView();
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        favoriteRoute = (ArrayList<BusRoute>) new FrequenceCounter(MainActivity.getContext()).getHeightFrequenceRoute();
        stringList = new ArrayList<>();
        for(int i = 0; i < favoriteRoute.size(); i++){
            stringList.add(favoriteRoute.get(i).busRouteName);
        }
        super.onCreate(savedInstanceState);
    }

    private void initFragment(BusRoute busRoute) {
        fragment = new ResultByRouteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("busRouteName", busRoute.busRouteName);
        bundle.putString("departure", busRoute.departure);
        bundle.putString("destination", busRoute.destination);
        bundle.putInt("routeId", busRoute.routeId);
        fragment.setArguments(bundle);
    }

    public void changeView() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.fragment, fragment);
        ft.addToBackStack("");
        ft.commit();
    }
}
