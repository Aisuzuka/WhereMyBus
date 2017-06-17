package com.taipeitech.ooad.wheremybus.MVC.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    public static final String TIMESTARTHUR_COLUMN = "timeStartHour";
    public static final String TIMESTARTMINITE_COLUMN = "timeStartMinite";
    public static final String TIMEENDHOUR_COLUMN = "timeEndHour";
    public static final String TIMEENDMINITE_COLUMN = "timeEndMinite";
    public static final String GOBACK_COLUMN  = "goBackk";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GOBACK_COLUMN + "  INTEGER NOT NULL," +
                    MINITE_COLUMN + " INTEGER NOT NULL, " +
                    TIMESTARTHUR_COLUMN + " INTEGER NOT NULL, " +
                    TIMESTARTMINITE_COLUMN + " INTEGER NOT NULL, " +
                    TIMEENDHOUR_COLUMN + " INTEGER NOT NULL, " +
                    TIMEENDMINITE_COLUMN + " INTEGER NOT NULL, " +
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

    // 新增參數指定的物件
    public Event insert(Event event) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料

        cv.put(GOBACK_COLUMN, event.getGoBack());
        cv.put(MINITE_COLUMN, event.getMinite());
        cv.put(TIMESTARTHUR_COLUMN, event.getTimeStartHour());
        cv.put(TIMESTARTMINITE_COLUMN, event.getTimeStartMinite());
        cv.put(TIMEENDHOUR_COLUMN, event.getTimeEndHour());
        cv.put(TIMEENDMINITE_COLUMN, event.getTimeEndMinite());
        cv.put(BUSROUTENAME_COLUMN, event.getBusRuteName());
        cv.put(BUSSTATIONNAME_COLUMN, event.getBusStationName());

        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
      event.setId(id);
        // 回傳結果
        return event;
    }

    // 修改參數指定的物件
    public boolean update(Event event) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(GOBACK_COLUMN, event.getGoBack());
        cv.put(MINITE_COLUMN, event.getMinite());
        cv.put(TIMESTARTHUR_COLUMN, event.getTimeStartHour());
        cv.put(TIMESTARTMINITE_COLUMN, event.getTimeStartMinite());
        cv.put(TIMEENDHOUR_COLUMN, event.getTimeEndHour());
        cv.put(TIMEENDMINITE_COLUMN, event.getTimeEndMinite());
        cv.put(BUSROUTENAME_COLUMN, event.getBusRuteName());
        cv.put(BUSSTATIONNAME_COLUMN, event.getBusStationName());
        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + event.getId();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // 讀取所有記事資料
    public List<Event> getAll() {
        List<Event> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public Event get(long id) {
        // 準備回傳結果用的物件
        Event event = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            event = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return event;
    }

    // 把Cursor目前的資料包裝為物件
    public Event getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        Event result = new Event();

        result.setId(cursor.getLong(0));

        result.setGoBack((cursor.getInt(1)));
        result.setMinite(cursor.getInt(2));
        result.setTimeStartHour(cursor.getInt(3));
        result.setTimeStartMinite(cursor.getInt(4));
        result.setTimeEndHour(cursor.getInt(5));
        result.setTimeEndMinite(cursor.getInt(6));
        result.setBusRuteName(cursor.getString(7));
        result.setBusStationName(cursor.getString(8));


        // 回傳結果
        return result;
    }

    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }


}
