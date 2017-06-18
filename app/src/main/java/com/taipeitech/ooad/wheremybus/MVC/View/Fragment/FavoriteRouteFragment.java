package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
}
