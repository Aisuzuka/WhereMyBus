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
import com.taipeitech.ooad.wheremybus.MVC.Model.BusRoute;
import com.taipeitech.ooad.wheremybus.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class SearchByRouteFragment extends Fragment {
    ListView listView;
    ArrayList<BusRoute> busRoute = new ArrayList<>();
    ArrayList<String> filteredStringList = new ArrayList<>();
    private EditText busLineInput;
    private TextWatcher textWatcher;
    private ArrayAdapter<String> listAdapter;
    private AdapterView.OnItemClickListener ItemClickListener;
    private ArrayList<BusRoute> filteredObjectList = new ArrayList<>();
    private ResultByRouteFragment fragment;
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
        searchRoute("");


        return view;
    }

    private void initView() {
        listAdapter = new ArrayAdapter(MainActivity.getContext(), android.R.layout.simple_list_item_1, filteredStringList);

        busLineInput = (EditText) view.findViewById(R.id.Input);
        busLineInput.setHint("請輸入路線代碼");
        busLineInput.addTextChangedListener(textWatcher);

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(ItemClickListener);
    }

    private void initClickListener() {
        ItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusRoute busRoute = filteredObjectList.get(position);
                initFragment(busRoute);
                changeView(fragment);
            }
        };
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
                searchRoute(busLineInput.getText().toString());
            }
        };
    }

    private void clearFilteredList() {
        filteredStringList.clear();
        filteredObjectList.clear();
    }

    private void searchRoute(String inputText) {
        List<BusRoute> temp = BusTable.getBusTable().searchRouteByName(inputText);
        if (temp != null) {
            filteredObjectList.addAll(temp);
        }

        int size = filteredObjectList.size();
        for (int count = 0; count < size; count++) {
            BusRoute item = filteredObjectList.get(count);
            filteredStringList.add(item.busRouteName);
        }
        listAdapter.notifyDataSetChanged();
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
