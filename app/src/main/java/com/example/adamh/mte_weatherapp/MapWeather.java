package com.example.adamh.mte_weatherapp;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MapWeather {
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String APPID = "6e1239278a276a67c8f27841b004b90a";

    public String getWeatherData(String location) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            con = (HttpURLConnection) ( new URL(BASE_URL + location + "&APPID=" + APPID)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ( (line = br.readLine()) != null )
                buffer.append(line + "rn");

            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;
    }

}
