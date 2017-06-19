package com.taipeitech.ooad.wheremybus;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.Reminder.BusArriveEvent;
import com.taipeitech.ooad.wheremybus.Reminder.Reminder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TestReminder {
    BusTable busTable;
    Context appContext;
    BusArriveEvent event;
    BusEstimateTime timeTable;

    @Before
    public void useAppContext() throws Exception {
        // Context of the app under test.
        busTable = BusTable.createBustable();
        appContext = InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(appContext, Reminder.class);
        appContext.startService(intent);
        event = new BusArriveEvent();
        event.setNotificationTime(1 * 60 * 1000)
                .setReferenceTime(System.currentTimeMillis())
                .setTargetBusRoute("299")
                .setGoDistance(1)
                .setTargetBusStation("正義郵局")
                .createTimeTable();
    }

    @Test
    public void addEvent() throws Exception {
        Reminder.getReminder().addEvent(event);
        assertNotNull(Reminder.getReminder().getAllEvents());
    }

    @Test
    public void deleteEvent() throws Exception {
        BusArriveEvent delEvent = Reminder.getReminder().getAllEvents().get(0);
        Reminder.getReminder().deleteEvent(delEvent);
        assertFalse(Reminder.getReminder().getAllEvents().equals(delEvent));
    }
}
