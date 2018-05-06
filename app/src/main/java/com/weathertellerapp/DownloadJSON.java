package com.weathertellerapp;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ANISH on 07-05-2018.
 */

public class DownloadJSON extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... urls) {

        String result="";
        try {
            URL url=new URL(urls[0]);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            InputStream in = conn.getInputStream();
            InputStreamReader rd=new InputStreamReader(in);
            int data=rd.read();
            while(data!=-1)
            {
                char curr=(char) data;
                result+=curr;
                data=rd.read();
            }
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
