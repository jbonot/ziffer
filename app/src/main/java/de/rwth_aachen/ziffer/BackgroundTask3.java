package de.rwth_aachen.ziffer;

/**
 * Created by jasimwhd on 6/29/2016.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask3 extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    BackgroundTask3(Context context)
    {
        this.ctx =context;
    }
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("My Profile..");
    }
    @Override
    protected String doInBackground(String... params) {

        String myevent_url = LocalSettings.Base_URL + "myevent.php";
        String method=params[0];


        if(method.equals("myevent"))
        {
            int tmp;
            String user_name = params[1];

            Log.d("profiledata",user_name);

            String data="";
            try {
                URL url = new URL(myevent_url);
                String urlParams = "user_name="+user_name;
                Log.d("urlParams",urlParams);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }

                is.close();
                httpURLConnection.disconnect();
                Log.d("EventSelectData",data);
                if(data.contains("german_level_event"))
                {
                return data;
                }
                else
                    return "";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("CatchingLogging",e.getMessage());
            }
        }
       return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }
}
