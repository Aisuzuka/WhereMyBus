package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taipeitech.ooad.wheremybus.MVC.Controller.MainActivity;
import com.taipeitech.ooad.wheremybus.MVC.View.Adapter.EventListAdapter;
import com.taipeitech.ooad.wheremybus.R;
import com.taipeitech.ooad.wheremybus.Reminder.BusArriveEvent;
import com.taipeitech.ooad.wheremybus.Reminder.Reminder;

import java.util.ArrayList;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class EventListFragment extends Fragment {
    private ArrayList<BusArriveEvent> eventList;
    private ListView listView;
    private TextView title;
    private EventListAdapter eventListAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDataToList();
    }

    private void loadDataToList() {
        eventList = (ArrayList<BusArriveEvent>) Reminder.getReminder().getAllEvents();
    }

    @Override
    public void onStart() {
        super.onStart();
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
        eventListAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final BusArriveEvent event = eventList.get((int) id);
                new AlertDialog.Builder(MainActivity.getContext())
                        .setTitle("刪除提醒")
                        .setMessage("確定要刪除路線" + event.getTargetBusRoute() +"的提醒?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Reminder.getReminder().deleteEvent(event);
                                loadDataToList();
                                eventListAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.getContext(), "已刪除提醒", Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
        return view;
    }
}
