package com.taipeitech.ooad.wheremybus.MVC.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.taipeitech.ooad.wheremybus.BusInfo.BusTable;
import com.taipeitech.ooad.wheremybus.MVC.View.Fragment.IndexFragment;
import com.taipeitech.ooad.wheremybus.R;
import com.taipeitech.ooad.wheremybus.Reminder.Reminder;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private static Context context;

    public class BusTableBuilder extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("請稍後")
                    .setMessage("正在準備公車路線圖")
                    .show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                BusTable.createBustable();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!Reminder.isAlive()) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Reminder.class);
                MainActivity.getContext().startService(intent);
            }

            dialog.cancel();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment, new IndexFragment())
                    .commit();
            super.onPostExecute(aVoid);
        }
    }

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_frame);
        checkNetwork();

    }

    private void checkNetwork() {
        if (couldSurfNet())
            new BusTableBuilder().execute();
        else{
            dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("網路異常")
                    .setMessage("請確認是否連接網路")
                    .setPositiveButton("重試", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkNetwork();
                        }
                    })
                    .setNegativeButton("離開", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.super.finish();
                        }
                    })
                    .show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        context = this;
    }

    private boolean couldSurfNet() {
        boolean result = false;
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            result = false;
        } else {
            if (!info.isAvailable()) {
                result = false;
            } else {
                result = true;
            }
        }

        return result;
    }
}
