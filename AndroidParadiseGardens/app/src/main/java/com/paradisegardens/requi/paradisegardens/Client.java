package com.paradisegardens.requi.paradisegardens;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Class to connect to our C# Server
 */
public class Client extends Thread{

    private String serverMessage;
    public static final String SERVERIP = "192.168.0.3";
    public static final int SERVERPORT = 4444;
    private boolean mRun = false;

    //PrintWriter out;
    OutputStream out;
    //BufferedReader in;


    public void stopClient(){
        mRun = false;
    }

    public void run() {

        mRun = true;

        try {

            InetAddress serverAddr = InetAddress.getByName(SERVERIP);

            Log.e("TCP Client", "C: Connecting...");

            Socket socket = new Socket(serverAddr, SERVERPORT);

            if(socket.isBound()){
                Log.i("SOCKET", "Socket: Connected");
            }
            else{
                Log.e("SOCKET", "Socket: Not Connected");
            }
            try {
                out = socket.getOutputStream();
                //out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                // Sending
                String toSend = "Hola Mundo";
                byte[] toSendBytes = toSend.getBytes();
                int toSendLen = toSendBytes.length;
                byte[] toSendLenBytes = new byte[4];
                toSendLenBytes[0] = (byte)(toSendLen & 0xff);
                toSendLenBytes[1] = (byte)((toSendLen >> 8) & 0xff);
                toSendLenBytes[2] = (byte)((toSendLen >> 16) & 0xff);
                toSendLenBytes[3] = (byte)((toSendLen >> 24) & 0xff);
                out.write(toSendLenBytes);
                out.write(toSendBytes);


                Log.e("TCP Client", "C: Sent.");

                Log.e("TCP Client", "C: Done.");


                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");


            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            } finally {

                socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }

    }

}