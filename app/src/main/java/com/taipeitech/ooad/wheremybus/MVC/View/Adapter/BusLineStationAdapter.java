package com.taipeitech.ooad.wheremybus.MVC.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.MVC.Model.Station;
import com.taipeitech.ooad.wheremybus.R;

import java.util.ArrayList;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class BusLineStationAdapter extends ArrayAdapter<BusEstimateTime> {
    ArrayList<BusEstimateTime> station;
    LayoutInflater myInflater;
    ViewHolder viewHolder;
    public BusLineStationAdapter(Context context, int resource, ArrayList<BusEstimateTime> station) {
        super(context, resource, station);
        this.station = station;
        myInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = myInflater.inflate(R.layout.station_item , null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.station = (TextView) convertView.findViewById(R.id.station);
        viewHolder.estimeTime = (TextView) convertView.findViewById(R.id.estimeTime);
        setContext(position);
        return convertView;
    }

    private void setContext(int position) {
        viewHolder.station.setText(station.get(position).busStation.busStationName + "         " + station.get(position).goBack);
        viewHolder.estimeTime.setText(station.get(position).estimateTime);
    }

    public class ViewHolder{
        TextView station;
        TextView estimeTime;
    }
}
