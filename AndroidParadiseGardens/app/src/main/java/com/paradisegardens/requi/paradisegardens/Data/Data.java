package com.paradisegardens.requi.paradisegardens.Data;


import android.text.format.Time;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Data {

    public List getCardData(String url, ArrayList<String> params){

        ArrayList<String> cardsData = new ArrayList<>();
        ArrayList<String> imgUrls = new ArrayList<>();

        JSONObject json = null;
        JSONArray jsonArray = null;
        try{
            json = new JSONObject(getJson(url));
            jsonArray = json.getJSONArray("info");

            for(int i = 0; i<jsonArray.length(); i++){
                //add img url
                imgUrls.add(jsonArray.getJSONObject(i).getString("imgurl"));
                String line = ""; //one line of text in card
                String abierto = "";
                //add our text data
                for(String name : params){
                    String responce = jsonArray.getJSONObject(i).getString(name).toString();

                    if(name == "Horario" && !url.contains("shows")){
                        abierto = estaAbierto(responce);
                    }

                    if(responce != "null" && responce != ""){
                        if(name != "name")
                            line += name + ": " + responce;
                        else
                            line += responce;
                        line += "\n";
                    }
                }
                if(!url.contains("shows"))
                    line += abierto;

                Random r = new Random();
                if(url.contains("atractions"))
                    line += "\nTiempo Espera: " + (r.nextInt(4 - 0 + 1)) + "." + (r.nextInt(59 - 10 + 1) + 10)+"hrs";
                cardsData.add(String.format(line));
            }
        }catch(Exception e){
            Log.e("Json Parse", e.toString());
        }

        List data = new ArrayList();
        data.add(imgUrls);
        data.add(cardsData);
        return data;
    }

    /**
     *
     * @param horario string time from web service
     * @return string that says if it's open or not
     */
    private String estaAbierto(String horario){
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        String curTimeHour =  today.format("%k");

        String estado = "Cerrado";

        String[] msg = horario.split(",");
        //for all the times
        for(String time : msg){

            time = time.replaceAll("\\s", "");

            //if it's not a time range
            if(!time.contains("-")){
                int hour = convertToMilitary(time);
                if(hour == Integer.parseInt(curTimeHour))
                    estado = "Abierto";
            }
            else{//it is a time range to check if curTime is in range
                String []timeRange = time.split("-");
                int lower = convertToMilitary(timeRange[0]);
                int higher = convertToMilitary(timeRange[1]);
                int currentHour = Integer.parseInt(curTimeHour.replaceAll("\\s",""));

                //Log.e("Lower&Higher time", lower + " and " + currentHour +" and " + higher);
                if(lower <= currentHour &&
                    currentHour <= higher)
                    estado = "Abierto";
            }
        }

        return estado;
    }

    //convert am style hour to military
    private int convertToMilitary(String hour){
        int militaryHour = Integer.parseInt(hour.split(":")[0]);
        if(hour.split(":")[1].contains("pm"))
            militaryHour += 12;
        return  militaryHour;
    }

    //Get the json from the specified url
    protected String getJson(String url) {
        String result = null;

        DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        InputStream inputStream = null;
        try {
            HttpResponse response = httpclient.execute(get);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
            Log.i("Json Responce", result);
        } catch (IOException e) {
            Log.e("Json Exception", e.toString());
        }

        return result;
    }
}
