package com.taipeitech.ooad.wheremybus.MVC.Model;

import android.content.Context;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;

import java.io.IOException;
import java.util.List;

/**
 * Created by user on 2017/6/16.
 */

public class Reminder {
    private DataBase dataBase;

    public Reminder(Context context){
        dataBase =new DataBase(context);
    }
    public void addAlarm(Event event){
        dataBase.insert(event);
    }
    public void updateAlarm(Event event){
        dataBase.update(event);
    }

    public void deleteAlarm(Event event){
        dataBase.delete(event.getId());
    }
    public List<Event> getAllAlarm(){

        List<Event> eventList = dataBase.getAll();
        for (int i = 0; i< eventList.size(); i++){
            Event event = eventList.get(i);

        }
        return eventList;
    }

}
