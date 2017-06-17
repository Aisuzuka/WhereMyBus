package com.taipeitech.ooad.wheremybus.Reminder;

import com.taipeitech.ooad.wheremybus.MVC.Model.BusArrivalEvent;

/**
 * Created by Pyakuren-Chienhua on 2017/6/17.
 */

public interface BusArriveListener {
    void busArrived(BusArrivalEvent busArrivalEvent);
}
