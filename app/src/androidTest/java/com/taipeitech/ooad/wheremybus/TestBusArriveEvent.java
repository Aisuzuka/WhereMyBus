package com.taipeitech.ooad.wheremybus;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.Reminder.BusArriveEvent;
import com.taipeitech.ooad.wheremybus.Reminder.Reminder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestBusArriveEvent {
    BusTable busTable;
    Context appContext;
    BusArriveEvent event;

    @Before
    public void useAppContext() throws Exception {
        busTable = BusTable.createBustable();
        appContext = InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(appContext, Reminder.class);
        appContext.startService(intent);
        event = new BusArriveEvent();
        event.setNotificationTime(1 * 60 * 1000)
                .setReferenceTime(System.currentTimeMillis())
                .setTargetBusRoute("299")
                .setGoDistance(1)
                .setTargetBusStation("正義郵局");
    }

    @Test
    public void createTimeTable() throws Exception{
        event.createTimeTable();
        assertNotNull(event.getTimeTable());
    }
}
