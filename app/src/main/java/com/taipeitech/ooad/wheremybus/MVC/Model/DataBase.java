package com.taipeitech.ooad.wheremybus.MVC.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.taipeitech.ooad.wheremybus.Reminder.BusArrivalEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/6/16.
 */

// 資料功能類別
public class DataBase {
    // 表格名稱
    public static final String TABLE_NAME = "busitem";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String BUSROUTENAME_COLUMN = "busRuteName";
    public static final String BUSSTATIONNAME_COLUMN = "busStationName";
    public static final String MINITE_COLUMN = "minite";
    public static final String REFERENCETIME_COLUMN  = "referencetime";
    public static final String GOBACK_COLUMN  = "goBackk";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GOBACK_COLUMN + "  INTEGER NOT NULL," +
                    MINITE_COLUMN + " INTEGER NOT NULL, " +
                    REFERENCETIME_COLUMN + " INTEGER NOT NULL, " +
                    BUSROUTENAME_COLUMN + " TEXT NOT NULL, " +
                    BUSSTATIONNAME_COLUMN + " TEXT NOT NULL)";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public DataBase(Context context) {
        db = DBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    public BusArrivalEvent insertBusArriveEvent(BusArrivalEvent event) {
        ContentValues cv = new ContentValues();

        cv.put(GOBACK_COLUMN, event.isGoDistance());
        cv.put(MINITE_COLUMN, event.getNotificationTime());
        cv.put(REFERENCETIME_COLUMN , event.getReferenceTime());
        cv.put(BUSROUTENAME_COLUMN,event.getTargetBusRoute() );
        cv.put(BUSSTATIONNAME_COLUMN, event.getTargetBusStation());

        long id = db.insert(TABLE_NAME, null, cv);

        event.setId(id);
        return event;
    }

    // 修改參數指定的物件
    public boolean updateBusArriveEvent(BusArrivalEvent event) {
        ContentValues cv = new ContentValues();

        cv.put(GOBACK_COLUMN, event.isGoDistance());
        cv.put(MINITE_COLUMN, event.getNotificationTime());
        cv.put(REFERENCETIME_COLUMN , event.getReferenceTime());
        cv.put(BUSROUTENAME_COLUMN,event.getTargetBusRoute() );
        cv.put(BUSSTATIONNAME_COLUMN, event.getTargetBusStation());
        String where = KEY_ID + "=" + event.getId();
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean deleteBusArrivaEvent(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // 讀取所有記事資料
    public List<BusArrivalEvent> getAllArriveEvent() {
        List<BusArrivalEvent> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getBusArriveEvent(cursor));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public BusArrivalEvent get(long id) {
        // 準備回傳結果用的物件
        BusArrivalEvent event = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            event = getBusArriveEvent(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return event;
    }

    // 把Cursor目前的資料包裝為物件
    public BusArrivalEvent getBusArriveEvent(Cursor cursor) {
        // 準備回傳結果用的物件
        BusArrivalEvent result = new BusArrivalEvent();

        result.setId(cursor.getLong(0));

        result.setGoDistance((cursor.getInt(1)));
        result.setNotificationTime(cursor.getInt(2));
        result.setReferenceTime(cursor.getLong(3));
        result.setTargetBusRoute(cursor.getString(4));
        result.setTargetBusStation(cursor.getString(5));

        return result;
    }

    public static final String TABLE_NAME_FREQUENCE = "busitemfreuence";

    public static final String KEY_ID_FREUENCE = "_id";

    // 其它表格欄位名稱
    public static final String BUSROUTENAME_FREUENCE_COLUMN = "busRuteName";
    public static final String COUNT_COLUMN  = "clicknum";

    public static final String CREATE_TABLE_FREQUENCE =
            "CREATE TABLE " + TABLE_NAME_FREQUENCE + " (" +
                    KEY_ID_FREUENCE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COUNT_COLUMN + "  INTEGER NOT NULL," +
                    BUSROUTENAME_FREUENCE_COLUMN + " TEXT NOT NULL)";



    public FrequenceRoute insertFrequenceRoute(FrequenceRoute frequenceRoute){
        ContentValues cv = new ContentValues();

        cv.put(COUNT_COLUMN, frequenceRoute.getCount());
        cv.put(BUSROUTENAME_FREUENCE_COLUMN, frequenceRoute.getBusRouteName());

        long id = db.insert(TABLE_NAME_FREQUENCE, null, cv);

        frequenceRoute.setId(id);
        return  frequenceRoute;
    }
    public boolean updateFrequenceRoute(FrequenceRoute frequenceRoute){
        ContentValues cv = new ContentValues();

        cv.put(COUNT_COLUMN, frequenceRoute.getCount());
        cv.put(BUSROUTENAME_FREUENCE_COLUMN, frequenceRoute.getBusRouteName());

        String where = KEY_ID_FREUENCE + "=" +  frequenceRoute.getId();
        return db.update(TABLE_NAME_FREQUENCE, cv, where, null) > 0;
    }

    public List<FrequenceRoute> getAllFrequenceRoute() {
        List<FrequenceRoute> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME_FREQUENCE, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getFrequenceRoute(cursor));
        }

        cursor.close();
        return result;
    }

    public FrequenceRoute getFrequenceRoute(Cursor cursor) {
        // 準備回傳結果用的物件
        FrequenceRoute result = new FrequenceRoute();

        result.setId(cursor.getLong(0));
        result.setCount((cursor.getInt(1)));
        result.setBusRouteName(cursor.getString(2));


        return result;
    }

}
