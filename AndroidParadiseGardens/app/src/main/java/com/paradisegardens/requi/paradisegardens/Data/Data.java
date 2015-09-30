package com.paradisegardens.requi.paradisegardens.Data;


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
                ArrayList<String> msg = new ArrayList<>();

                //add img url
                imgUrls.add(jsonArray.getJSONObject(i).getString("imgurl"));

                //add our text data
                for(String name : params) {
                    msg.add(jsonArray.getJSONObject(i).getString(name).toString());
                }

                String text = "";
                for(int j = 0; j< msg.size(); j++){
                    if(msg.get(j) != "null")
                        text += msg.get(j) + "\n";
                }
                cardsData.add(String.format(text));
            }
        }catch(Exception e){
            Log.e("Json Parse", e.toString());
        }

        List data = new ArrayList();
        data.add(imgUrls);
        data.add(cardsData);
        return data;
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
