package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.taipeitech.ooad.wheremybus.MVC.Controller.MainActivity;
import com.taipeitech.ooad.wheremybus.MVC.View.Adapter.EventListAdapter;
import com.taipeitech.ooad.wheremybus.R;
import com.taipeitech.ooad.wheremybus.Reminder.BusArrivalEvent;
import com.taipeitech.ooad.wheremybus.Reminder.Reminder;

import java.util.ArrayList;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class EventListFragment extends Fragment {
    private ArrayList<BusArrivalEvent> eventList;
    private ListView listView;
    private TextView title;
    private EventListAdapter eventListAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventList = (ArrayList<BusArrivalEvent>) Reminder.getReminder().getAllEvents();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        title = (TextView) view.findViewById(R.id.title);

        title.setText("提醒列表");
        eventListAdapter = new EventListAdapter(MainActivity.getContext(), R.layout.event_item, eventList);
        listView.setAdapter(eventListAdapter);
        return view;
    }
}
