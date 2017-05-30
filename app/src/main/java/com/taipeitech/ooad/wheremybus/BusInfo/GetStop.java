package com.taipeitech.ooad.wheremybus.BusInfo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by user on 2017/5/25.
 */

public class GetStop {
    public GetStop(){};

    public static class EssentialInfo{
        public  EssentialInfo(){}

        public static class Location{
            public Location(){}

            public String name;
            public String CenterName;
        }

        public String UpdateTime;
        public String CoordinateSystem;
        public Location Location;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class BusInfo{
        public BusInfo(){}
        public int Id;
        public  int routeId;
        public String nameZh;
        public  int seqNo;
        public String goBack;
        public String address;
        public  int stopLocationId;
        public String showLon;
        public String showLat;
    }

    public EssentialInfo EssentialInfo;
    public BusInfo[] BusInfo;



//    public static GetStop createFromInputStream(InputStream inputStream){
//
//        String[] keyString = {"routeId","nameZh","seqNo","goBack","address","stopLocationId","showLon","showLat"};
//        int[] type ={0,1,0,1,1,0,1,1};
//        int stateNum =keyString.length;
//
//        char[][] keyword =new char[keyString.length][];
//        for (int i=0;i<keyString.length;i++){
//            keyword[i] =keyString[i].toCharArray();
//        }
//
//
//        char[] buffer =new char[1024];
//        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
//
//        int stateNow=0;
//        int typeNow=type[0];
//
//        int analysisIndex=0;
//        char[] charNow = keyword[0];
//        int successNum=0;
//        boolean analysisKey=true;
//
//        int actualBuffered;
//        try {
//            while ((actualBuffered = bufferReader.read(buffer))!=-1){
//                for (int i=0;i<actualBuffered;i++){
//                    if(analysisKey){
//
//                    }else{
//
//                    }
//                    if(typeNow ==0){
//
//                    }else if(typeNow==0){
//
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
