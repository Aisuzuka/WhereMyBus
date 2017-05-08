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

public class ResultByStationAdapter extends ArrayAdapter<BusEstimateTime> {
    ArrayList<BusEstimateTime> station;
    LayoutInflater myInflater;
    ViewHolder viewHolder;

    public ResultByStationAdapter(Context context, int resource, ArrayList<BusEstimateTime> station) {
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
        viewHolder.route = (TextView) convertView.findViewById(R.id.station);
        viewHolder.estimeTime = (TextView) convertView.findViewById(R.id.estimeTime);
        setContext(position);
        return convertView;
    }

    private void setContext(int position) {
        viewHolder.route.setText(station.get(position).busRoute.busRouteName);
        int time = Integer.parseInt(station.get(position).estimateTime);
        if (time >= 60) {
            viewHolder.estimeTime.setText("預估" + Integer.toString(time / 60) + "分鐘後到站");
        } else if (time < 60) {
            viewHolder.estimeTime.setText("即將到站");
        } else if (time == -1){
            viewHolder.estimeTime.setText("尚未發車");
        } else if (time == -3){
            viewHolder.estimeTime.setText("末班車已過");
        }
    }

    public class ViewHolder {
        TextView route;
        TextView estimeTime;
    }
}
