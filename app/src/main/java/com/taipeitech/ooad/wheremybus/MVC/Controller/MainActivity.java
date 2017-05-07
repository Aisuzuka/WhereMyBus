package com.taipeitech.ooad.wheremybus.MVC.Controller;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.taipeitech.ooad.wheremybus.BusInfo.BusInformationController;
import com.taipeitech.ooad.wheremybus.MVC.View.Fragment.IndexFragment;
import com.taipeitech.ooad.wheremybus.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_frame);

        FragmentManager fragmentManager = getFragmentManager();
        IndexFragment indexFragment = new IndexFragment();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment, indexFragment);
        ft.commit();
        Handler handler=new Handler();
        BusInformationController busInformationController=new BusInformationController(handler);
        busInformationController.searchLineByName("299");


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
