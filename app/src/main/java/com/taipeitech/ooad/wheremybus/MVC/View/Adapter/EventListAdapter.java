package com.taipeitech.ooad.wheremybus.MVC.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.taipeitech.ooad.wheremybus.Reminder.BusArriveEvent;
import com.taipeitech.ooad.wheremybus.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class EventListAdapter extends ArrayAdapter<BusArriveEvent> {
    ArrayList<BusArriveEvent> busArriveEvent;
    LayoutInflater myInflater;
    ViewHolder viewHolder;
    public EventListAdapter(Context context, int resource, ArrayList<BusArriveEvent> busArriveEvent) {
        super(context, resource, busArriveEvent);
        this.busArriveEvent = busArriveEvent;
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
        setContext(busArriveEvent.get(position));
        return convertView;
    }

    private void setContext(BusArriveEvent busArriveEvent) {
        DateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(busArriveEvent.getReferenceTime());

        viewHolder.targetRoute.setText(busArriveEvent.getTargetBusRoute());
        viewHolder.targetStation.setText(busArriveEvent.getTargetBusStation());
        viewHolder.goDistance.setText("往 " + busArriveEvent.getDestination());
        viewHolder.notificationTime.setText(Integer.toString(busArriveEvent.getNotificationTime()));
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
