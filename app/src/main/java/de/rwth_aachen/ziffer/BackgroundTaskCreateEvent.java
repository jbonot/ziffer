package de.rwth_aachen.ziffer;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Jeanine on 20.07.2016.
 */
public class BackgroundTaskCreateEvent extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... params) {
        if (params[0].equals("create_event")) {
            try
            {
                return this.simpleExecute("create_event.php",
                            "name=" + URLEncoder.encode(params[1], "UTF-8")
                            + "&address=" + URLEncoder.encode(params[2], "UTF-8")
                            + "&google_id=" + URLEncoder.encode(params[3], "UTF-8")
                            + "&latitude=" + URLEncoder.encode(params[4], "UTF-8")
                            + "&longitude=" + URLEncoder.encode(params[5], "UTF-8")
                            + "&username=" + URLEncoder.encode(params[6], "UTF-8")
                            + "&level=" + URLEncoder.encode(params[7], "UTF-8")
                            + "&title=" + URLEncoder.encode(params[8], "UTF-8")
                            + "&date=" + URLEncoder.encode(params[9], "UTF-8")
                            + "&start_time=" + URLEncoder.encode(params[10], "UTF-8")
                            + "&end_time=" + URLEncoder.encode(params[11], "UTF-8")
                            + "&max_attendees=" + URLEncoder.encode(params[12], "UTF-8")
                            + "&description=" + URLEncoder.encode(params[13], "UTF-8"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String simpleExecute(String file, String urlParams) {
        try
        {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(LocalSettings.Base_URL + file).openConnection();
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bufferedWriter.write(urlParams);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            int tmp;
            String data = "";
            InputStream is = httpURLConnection.getInputStream();
            while((tmp = is.read())!= -1){
                data += (char)tmp;
            }

            is.close();

            httpURLConnection.disconnect();
            return data;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
