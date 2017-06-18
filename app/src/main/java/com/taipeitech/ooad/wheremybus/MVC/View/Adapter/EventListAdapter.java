package com.taipeitech.ooad.wheremybus.MVC.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.taipeitech.ooad.wheremybus.Reminder.BusArrivalEvent;
import com.taipeitech.ooad.wheremybus.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class EventListAdapter extends ArrayAdapter<BusArrivalEvent> {
    ArrayList<BusArrivalEvent> busArrivalEvent;
    LayoutInflater myInflater;
    ViewHolder viewHolder;
    public EventListAdapter(Context context, int resource, ArrayList<BusArrivalEvent> busArrivalEvent) {
        super(context, resource, busArrivalEvent);
        this.busArrivalEvent = busArrivalEvent;
        myInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = myInflater.inflate(R.layout.event_item, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.targetStation = (TextView) convertView.findViewById(R.id.BusStation);
        viewHolder.targetRoute = (TextView) convertView.findViewById(R.id.BusRoute);
        viewHolder.goDistance = (TextView) convertView.findViewById(R.id.GoDistance);
        viewHolder.notificationTime = (TextView) convertView.findViewById(R.id.NotificationTime);
        viewHolder.referenceTime = (TextView) convertView.findViewById(R.id.ReferenceTime);
        setContext(busArrivalEvent.get(position));
        return convertView;
    }

    private void setContext(BusArrivalEvent busArrivalEvent) {
        DateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(busArrivalEvent.getReferenceTime());

        viewHolder.targetRoute.setText(busArrivalEvent.getTargetBusRoute());
        viewHolder.targetStation.setText(busArrivalEvent.getTargetBusStation());
        viewHolder.goDistance.setText("往 " + busArrivalEvent.getDestination());
        viewHolder.notificationTime.setText(Integer.toString(busArrivalEvent.getNotificationTime()));
        viewHolder.referenceTime.setText(dateFormat.format(calendar.getTime()));
    }

    public class ViewHolder{
        TextView targetRoute;
        TextView targetStation;
        TextView goDistance;
        TextView notificationTime;
        TextView referenceTime;
    }
}
