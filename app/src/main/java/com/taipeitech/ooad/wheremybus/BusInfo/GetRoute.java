package com.taipeitech.ooad.wheremybus.BusInfo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by user on 2017/5/25.
 */

public class GetRoute {
    public GetRoute(){}

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
        public String nameZh;
        public String departureZh;
        public String destinationZh;
    }

    public EssentialInfo EssentialInfo;
    public BusInfo[] BusInfo;
}
