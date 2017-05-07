package com.taipeitech.ooad.wheremybus.MVC.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.taipeitech.ooad.wheremybus.MVC.Model.Station;
import com.taipeitech.ooad.wheremybus.R;

import java.util.ArrayList;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class StationListAdapter extends ArrayAdapter<Station> {
    ArrayList<Station> station;
    LayoutInflater myInflater;
    ViewHolder viewHolder;
    public StationListAdapter(Context context, int resource, ArrayList<Station> station) {
        super(context, resource, station);
        this.station = station;
        myInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = myInflater.inflate(R.layout.bus_item , null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return super.getView(position, convertView, parent);
    }

    public class ViewHolder{
        TextView station;
        TextView estimeTime;
    }
}
