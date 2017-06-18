package com.taipeitech.ooad.wheremybus.MVC.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.R;

import java.util.ArrayList;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class ResultByBusLineAdapter extends ArrayAdapter<BusEstimateTime> {
    ArrayList<BusEstimateTime> station;
    LayoutInflater myInflater;
    ViewHolder viewHolder;

    public ResultByBusLineAdapter(Context context, int resource, ArrayList<BusEstimateTime> station) {
        super(context, resource, station);
        this.station = station;
        myInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = myInflater.inflate(R.layout.station_item, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.station = (TextView) convertView.findViewById(R.id.busRoute);
        viewHolder.estimeTime = (TextView) convertView.findViewById(R.id.estimeTime);
        setContent(position);
        return convertView;
    }

    private void setContent(int position) {
        viewHolder.station.setText(station.get(position).busStation.busStationName);
        int time = Integer.parseInt(station.get(position).estimateTime);
        String string;
        if (time < 0) {
            if (time == -3)
                string = "末班車已過";
            else
                string = "尚未發車";
        } else {
            if (time < 60)
                string = "即將到站";
            else
                string = "預估" + Integer.toString((time / 60) + 1) + "分鐘後到站";
        }
        viewHolder.estimeTime.setText(string);
    }

    public class ViewHolder {
        TextView station;
        TextView estimeTime;
    }
}
