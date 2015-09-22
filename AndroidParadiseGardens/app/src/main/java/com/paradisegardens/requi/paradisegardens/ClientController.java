package com.paradisegardens.requi.paradisegardens;

import android.os.AsyncTask;

/**
 * Created by Seth on 9/21/2015.
 */
public class ClientController{

    private Client client;

    public ClientController(){
        client = new Client();
    }

    public void sendMsg(){
        client.start();
        client.stopClient();
    }


}

