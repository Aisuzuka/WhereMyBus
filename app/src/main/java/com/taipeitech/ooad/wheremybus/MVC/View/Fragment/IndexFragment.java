package com.taipeitech.ooad.wheremybus.MVC.View.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.taipeitech.ooad.wheremybus.MVC.Model.BusEstimateTime;
import com.taipeitech.ooad.wheremybus.BusInfo.BusInformationController;
import com.taipeitech.ooad.wheremybus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pyakuren-Chienhua on 2017/5/4.
 */

public class IndexFragment extends Fragment {
    private ListView list;

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater myInflater;
        private List<BusEstimateTime> mList;

        public MyAdapter(Context c, List<BusEstimateTime> mList) {
            this.mList=mList;
            myInflater = LayoutInflater.from(c);
        }

        public void setList(List<BusEstimateTime> mList){
            this.mList=mList;
        }

        @Override
        public int getCount() {
// TODO Auto-generated method stub
            return mList.size();
            // return names.length;
        }

        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
            convertView = myInflater.inflate(R.layout.estimate_bus_time, null);
            final TextView name = (TextView) convertView.findViewById(R.id.textView);
            final TextView time = (TextView) convertView.findViewById(R.id.textView2);

            name.setText(mList.get(position).busStation.busStationName + "         " + mList.get(position).goBack);
            time.setText(mList.get(position).estimateTime);
            return convertView;
        }
    }
    View view;
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_system_controller, container, false);
        list = (ListView)  view.findViewById(R.id.listViewEstimateTimeByRoute);
        List<BusEstimateTime> busEstimateTimeList =new ArrayList<>();
        final MyAdapter myAdapter =new MyAdapter(getActivity(),busEstimateTimeList);
        list.setAdapter(myAdapter);

        Handler DOFindAttributehandler =  new Handler(){
            private MyAdapter adapter =myAdapter;
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                List<BusEstimateTime> MsgString = (List<BusEstimateTime>)msg.obj;
                adapter.setList(MsgString);
                adapter.notifyDataSetChanged();
            }
        };



        BusInformationController busInformationController =new BusInformationController(DOFindAttributehandler);
        busInformationController.searchLineByName("299");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
