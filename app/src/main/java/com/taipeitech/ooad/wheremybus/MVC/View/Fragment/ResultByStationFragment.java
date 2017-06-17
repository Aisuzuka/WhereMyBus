package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.MVC.Controller.MainActivity;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusStation;
import com.taipeitech.ooad.wheremybus.MVC.View.Adapter.ResultByStationAdapter;
import com.taipeitech.ooad.wheremybus.R;
import com.taipeitech.ooad.wheremybus.Reminder.BusArrivalEvent;
import com.taipeitech.ooad.wheremybus.Reminder.Reminder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class ResultByStationFragment extends Fragment {
    Handler routeListener;
    Button goDistance, backDistance;
    View view;
    BusStation station;
    ResultByStationAdapter stationAdapter;
    ArrayList<BusEstimateTime> goDistanceList = new ArrayList<>();
    ArrayList<BusEstimateTime> backDistanceList = new ArrayList<>();
    ArrayList<BusEstimateTime> busLineStationList = new ArrayList<>();
    ListView listView;
    View.OnClickListener distanceClickListener;
    boolean isGoDistance = true;
    final String BackDistance = "1";
    final String GoDistanch = "0";
    private TextView busLineView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBusLineFromPastPage();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.route_or_station_detail, container, false);
        setClickListener();
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        relizationListener();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void relizationListener() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                clearAllListData();
                new getBusEstimateTimeByRoute().execute(station);
            }
        }, 0, 30000);
    }

    private void loadDataToList(Pair<List<BusEstimateTime>,List<BusEstimateTime>> list) {
        goDistanceList.addAll(list.first);
        backDistanceList.addAll(list.second);
        loadDataToListView();
    }

    private void getBusLineFromPastPage() {
        station = BusTable.getBusTable().getStationByName(getArguments().getString("busStationName"));
    }

    private void clearAllListData() {
        goDistanceList.clear();
        backDistanceList.clear();
        busLineStationList.clear();
    }

    private void loadDataToListView() {
        if (isGoDistance) {
            loadGoToListView();
        } else {
            loadBackToListView();
        }
        refreashAdapter();
    }

    private void refreashAdapter() {
        stationAdapter.notifyDataSetChanged();
    }

    private void loadBackToListView() {
        busLineStationList.addAll(backDistanceList);
    }

    private void loadGoToListView() {
        busLineStationList.addAll(goDistanceList);
    }

    private void setClickListener() {
        distanceClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.GoDistance:
                        isGoDistance = true;
                        clearListViewData();
                        loadDataToListView();
                        break;
                    case R.id.BackDistance:
                        isGoDistance = false;
                        clearListViewData();
                        loadDataToListView();
                        break;
                }
            }
        };
    }

    private void clearListViewData() {
        busLineStationList.clear();
    }

    private void initView() {
        listView = (ListView) view.findViewById(R.id.listView);
        stationAdapter = new ResultByStationAdapter(MainActivity.getContext(), R.layout.station_item, busLineStationList);
        listView.setAdapter(stationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final BusEstimateTime selectBusEstimateTime = busLineStationList.get((int) id);
                final Dialog dialog = new Dialog(MainActivity.getContext());
                dialog.setContentView(R.layout.event_check_dialog);
                final EditText refrenceTime = (EditText) dialog.findViewById(R.id.ReferenceTime);
                final EditText notificationTime = (EditText) dialog.findViewById(R.id.NotificationTime);
                TextView busRoute = (TextView) dialog.findViewById(R.id.BusRoute);
                TextView busStation = (TextView) dialog.findViewById(R.id.BusStation);
                TextView goDistance = (TextView) dialog.findViewById(R.id.GoDistance);
                Button btn_Ok = (Button) dialog.findViewById(R.id.Ok);
                Button btn_Cancel = (Button) dialog.findViewById(R.id.Cancel);

                busRoute.setText(selectBusEstimateTime.busRoute.busRouteName);
                busStation.setText(selectBusEstimateTime.busStation.busStationName);
                goDistance.setText("往" + selectBusEstimateTime.busRoute.destination);

                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm");
                dialog.show();
                refrenceTime.setOnClickListener(new View.OnClickListener() {
                    GregorianCalendar calendar = new GregorianCalendar();

                    @Override
                    public void onClick(View v) {

                        new DatePickerDialog(MainActivity.getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                                GregorianCalendar calendar1 = new GregorianCalendar();

                                new TimePickerDialog(MainActivity.getContext(), new TimePickerDialog.OnTimeSetListener() {


                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        Date date;
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTimeInMillis(0);
                                        calendar.set(year, month, dayOfMonth, hourOfDay, minute);
                                        date = calendar.getTime();
                                        refrenceTime.setText(simpleDateFormat.format(date));
                                    }
                                }, calendar1.get(Calendar.HOUR_OF_DAY), calendar1.get(Calendar.MINUTE), false).show();
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                btn_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                btn_Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isEmpty(refrenceTime) && !isEmpty(notificationTime)) {
                            Calendar referenceTime = null;
                            try {
                                DateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm");
                                dateFormat.format(simpleDateFormat.parse(refrenceTime.getText().toString()));
                                referenceTime = dateFormat.getCalendar();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Reminder.getReminder().addEvent(new BusArrivalEvent()
                                    .setGoDistance(isGoDistance ? 1 : 0)
                                    .setReferenceTime(referenceTime.getTimeInMillis())
                                    .setNotificationTime(Integer.valueOf(notificationTime.getText().toString()))
                                    .setTargetBusRoute(selectBusEstimateTime.busRoute.busRouteName)
                                    .setTargetBusStation(selectBusEstimateTime.busStation.busStationName)
                                    .setTimeTable(selectBusEstimateTime));
                            Toast.makeText(MainActivity.getContext(), "已加入到站提醒", Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        } else {
                            Toast.makeText(MainActivity.getContext(), "時間及到站提醒不得為空", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        goDistance = (Button) view.findViewById(R.id.GoDistance);
        backDistance = (Button) view.findViewById(R.id.BackDistance);
        goDistance.setOnClickListener(distanceClickListener);
        backDistance.setOnClickListener(distanceClickListener);

        busLineView = (TextView) view.findViewById(R.id.BusLine);
        busLineView.setText("站牌 " + station.busStationName);
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().equals("");
    }

    private class getBusEstimateTimeByRoute extends AsyncTask<BusStation, BusStation, Pair<List<BusEstimateTime>, List<BusEstimateTime>>> {
        @Override
        protected Pair<List<BusEstimateTime>, List<BusEstimateTime>> doInBackground(BusStation... params) {
            Pair<List<BusEstimateTime>, List<BusEstimateTime>> pair = null;
            try {
                BusTable busTable = BusTable.getBusTable();
                pair = busTable.getBusEstimateTimeByStation(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return pair;
        }

        @Override
        protected void onPostExecute(Pair<List<BusEstimateTime>,List<BusEstimateTime>> pair) {
            loadDataToList(pair);
        }
    }
}
