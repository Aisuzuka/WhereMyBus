package com.taipeitech.ooad.wheremybus.BusInfo;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/5/25.
 */

public class GetEstimateTime {
    public GetEstimateTime() {}

    public static class EssentialInfo{
        public  EssentialInfo(){}

        public static class Location{
            public Location(){}
            public  String name;
            public String CenterName;
        }

        public String UpdateTime;
        public String CoordinateSystem;
        public Location Location;
    }

    public static class BusInfo{
        public  BusInfo(){}
        public int RouteID;
        public int StopID;
        public String EstimateTime;
        public String GoBack;
    }

    public EssentialInfo EssentialInfo;
    public BusInfo[] BusInfo;

}
