package de.rwth_aachen.ziffer;

import android.os.AsyncTask;

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
public class BackgroundTaskEventDetails extends AsyncTask<String,Void,String> {
    public static final String RESULT_SUCCESS = "RESULT_SUCCESS";

    @Override
    protected String doInBackground(String... params) {
        if (params[0].equals("get_event")) {
            try {
                return this.fetch("get_event.php", "event_id=" + URLEncoder.encode(params[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (params[0].equals("get_location")) {
            try {
                return this.fetch("get_location.php", "location_id=" + URLEncoder.encode(params[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (params[0].equals("get_host_info")) {
            try {
                return this.fetch("get_host_info.php", "username=" + URLEncoder.encode(params[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (params[0].equals("get_event_guests")) {
            try {
                return this.fetch("get_event_guests.php", "event_id=" + URLEncoder.encode(params[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (params[0].equals("join_event")) {
            try
            {
                return this.simpleExecute("join_event.php",
                        "event_id=" + URLEncoder.encode(params[1], "UTF-8")
                                + "&username=" + URLEncoder.encode(params[2], "UTF-8"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else if (params[0].equals("delete_event_guest")) {

            try
            {
                return this.simpleExecute("delete_event_guest.php",
                        "event_id=" + URLEncoder.encode(params[1], "UTF-8")
                                + "&username=" + URLEncoder.encode(params[2], "UTF-8"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else if (params[0].equals("add_notification")) {
            try
            {
                return this.simpleExecute("add_notification.php",
                        "user_recipient=" + URLEncoder.encode(params[1], "UTF-8")
                                + "&user_sender=" + URLEncoder.encode(params[2], "UTF-8")
                                + "&event_id=" + URLEncoder.encode(params[3], "UTF-8")
                                + "&message_type=" + URLEncoder.encode(params[4], "UTF-8"));
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
            httpURLConnection.getInputStream().close();
            httpURLConnection.disconnect();

            return RESULT_SUCCESS;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String fetch(String file, String urlParams) {
        try
        {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(LocalSettings.Base_URL + file).openConnection();
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(urlParams.getBytes());
            os.flush();
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
