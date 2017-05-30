package com.taipeitech.ooad.wheremybus.Tool;

import android.util.Log;

import com.taipeitech.ooad.wheremybus.BusInfo.GetEstimateTime;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by user on 2017/5/25.
 */

public class DownloadObjectFromURL {
    private String url;
    private ObjectReader objectReader;
    public DownloadObjectFromURL(Class type,String url){
        this.url=url;
        ObjectMapper objectMapper =new ObjectMapper();
        this.objectReader= objectMapper.reader(type);

    }
    public Object downloadObject()throws IOException {
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn =null;
            int responseCode;
            do{
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                responseCode =conn.getResponseCode();
                if(responseCode<200||responseCode>299){
                    String newUrl =  conn.getHeaderField("Location");
                    Log.d("responseCode", String.valueOf(responseCode) );
                    Log.d("newLocation",newUrl );
                    url = new URL(newUrl);
                }
            }while (responseCode<200||responseCode>299);
            InputStream instream =url.openStream();
            GZIPInputStream gzis = new GZIPInputStream(instream);
            return objectReader.readValue(gzis);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
