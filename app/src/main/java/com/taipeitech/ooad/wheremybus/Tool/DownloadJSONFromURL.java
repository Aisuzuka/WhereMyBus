package com.taipeitech.ooad.wheremybus.Tool;

import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by user on 2017/5/4.
 */
public class DownloadJSONFromURL {
    public JSONObject downloadURL(String httpURL)throws IOException{
        JSONObject jsonObject =null;
                try {
                    long startTime = System.nanoTime();
                    URL url = new URL(httpURL);

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
                    String theString = IOUtils.toString(gzis, "UTF-8");
                   // Log.d("JSONString",theString);

                   jsonObject=new JSONObject(theString);
                    long endTime = System.nanoTime();
                    Log.d("Time",String.valueOf(startTime-endTime));
                    if (jsonObject!=null){
                        Log.e("GGGGGG","GGGGGGG");
                    }
                    ObjectMapper mapper = new ObjectMapper();


                    return  jsonObject;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }  catch (JSONException e) {
                    e.printStackTrace();
                }finally {

                }
        return jsonObject;
    }

    public String downloadURLString(String httpURL)throws IOException{
        JSONObject jsonObject =null;
        String theString =null;
        try {
            URL url = new URL(httpURL);

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
            theString = IOUtils.toString(gzis, "UTF-8");



            return  theString;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return  theString;
    }
    public Class type;



}
