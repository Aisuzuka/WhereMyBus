package com.taipeitech.ooad.wheremybus.Tool;

import android.util.Log;

import org.apache.commons.io.IOUtils;
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
                    if (jsonObject!=null){
                        Log.e("GGGGGG","GGGGGGG");
                    }
                    return  jsonObject;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }  catch (JSONException e) {
                    e.printStackTrace();
                }finally {

                }
        return jsonObject;
    }
}
