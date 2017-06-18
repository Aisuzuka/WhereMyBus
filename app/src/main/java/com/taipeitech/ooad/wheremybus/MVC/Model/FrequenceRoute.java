package com.taipeitech.ooad.wheremybus.MVC.Model;

/**
 * Created by user on 2017/6/18.
 */
public class FrequenceRoute{
    private String busRouteName;
    private  int count =0;
    private long id;

    public String getBusRouteName() {
        return busRouteName;
    }

    public void setBusRouteName(String busRouteName) {
        this.busRouteName = busRouteName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addCount(){
        count++;
    }
}
