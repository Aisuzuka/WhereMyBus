package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.MVC.Controller.MainActivity;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusStation;
import com.taipeitech.ooad.wheremybus.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/5/7.
 */

public class SearchByStationFragment extends Fragment {
    ListView listView;
    ArrayList<String> filteredStringList = new ArrayList<>();
    private ArrayList<BusStation> filteredObjectList = new ArrayList<>();
    private EditText stationInput;
    private TextWatcher textWatcher;
    private ArrayAdapter<String> listAdapter;
    private AdapterView.OnItemClickListener ItemClickListener;
    private ResultByStationFragment fragment;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_page, container, false);
        initTextWatcher();
        initClickListener();
        initView();
        searchStation("");

        return view;
    }

    private void initView() {
        listAdapter = new ArrayAdapter(MainActivity.getContext(), android.R.layout.simple_list_item_1, filteredStringList);

        stationInput = (EditText) view.findViewById(R.id.Input);
        stationInput.setHint("請輸入站牌名稱");
        stationInput.addTextChangedListener(textWatcher);

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(ItemClickListener);
    }

    private void initClickListener() {
        ItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusStation busStation = filteredObjectList.get(position);
                initFragment(busStation);
                changeView(fragment);
            }
        };
    }

    private void initFragment(BusStation busStation) {
        fragment = new ResultByStationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("busStationName", busStation.busStationName);
        bundle.putString("address", busStation.address);
        bundle.putFloat("lon", busStation.lon);
        bundle.putFloat("lat", busStation.lat);
        bundle.putInt("locationId", busStation.locationId);
        fragment.setArguments(bundle);
    }

    private void initTextWatcher() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                clearFilteredList();
                searchStation(stationInput.getText().toString());

            }
        };
    }

    private void searchStation(String inputText) {
        List<BusStation> temp = BusTable.getBusTable().searchStationByName(inputText);
        if (temp != null) {
            filteredObjectList.addAll(temp);
        }

        int size = filteredObjectList.size();
        for (int count = 0; count < size; count++) {
            BusStation item = filteredObjectList.get(count);
            filteredStringList.add(item.busStationName);
        }
        listAdapter.notifyDataSetChanged();
    }

    private void clearFilteredList() {
        filteredStringList.clear();
        filteredObjectList.clear();
    }

    public void changeView(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.fragment, fragment);
        ft.addToBackStack("");
        ft.commit();
    }
}
