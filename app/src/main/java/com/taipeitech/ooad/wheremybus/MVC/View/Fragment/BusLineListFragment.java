package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taipeitech.ooad.wheremybus.R;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class BusLineListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bus_page, container, false);
        return view;
    }

//    BusLineStationFragment fragment = new BusLineStationFragment();
//    Bundle bundle = new Bundle();
//    bundle.putString("busLine", busLine);
//    fragment.setArguments(bundle);
}
