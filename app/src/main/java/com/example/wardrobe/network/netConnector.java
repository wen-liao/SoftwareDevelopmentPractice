package com.example.wardrobe.network;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class netConnector  implements Callable<JSONObject> {
    private String TAG = "thread connecting..";
    private String serverPath = "http://39.106.57.177:8000/";
    private String url,method;
    private JSONObject body;
    private JSONObject response;

    public netConnector(String urlPath,String method,JSONObject body){
        this.url = this.serverPath+urlPath;
        this.method = method;
        this.body = body;
    }

    @Override
    public JSONObject call() {
        try {
            Log.e(TAG,"connecting..");
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod(this.method);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(this.body.toString().getBytes("UTF-8"));
            out.flush();
            out.close();


            InputStream in = conn.getInputStream();
            InputStreamReader input = new InputStreamReader(in);
            BufferedReader bf = new BufferedReader(input);

            if(conn.getResponseCode() == 200){

                String inputLine;
                StringBuffer sb = new StringBuffer();
                while((inputLine = bf.readLine()) != null){
                    sb.append(inputLine);
                }

                Log.e(TAG,sb.toString());
                this.response = new JSONObject(sb.toString());

                return response;
            }






        }catch(MalformedURLException e){
            Log.e(TAG,"url invalid");
        }
        catch(IOException e){
            Log.e(TAG,"tcp failed");
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }
}
