package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class EventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle b = getIntent().getExtras();
        String event_data = b.getString("event_data");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.hostIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventDetails.this, ProfileActivity.class));
            }
        });

        findViewById(R.id.button_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Remove hard-coded data.
                BackgroundTask task = new BackgroundTask();
                task.execute("add_notification",
                        "andrea.allen", "maxmusterman", Integer.toString(1), Integer.toString(0));
                try {
                    if (task.get().equals(BackgroundTask.RESULT_SUCCESS)) {
                        Toast.makeText(v.getContext(), "Notification sent", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class BackgroundTask extends AsyncTask<String,Void,String> {
        public static final String RESULT_SUCCESS = "RESULT_SUCCESS";

        @Override
        protected String doInBackground(String... params) {
            if (params[0].equals("add_notification")) {
                try
                {
                    String urlParams =
                            "user_recipient=" + URLEncoder.encode(params[1], "UTF-8")
                                    + "&user_sender=" + URLEncoder.encode(params[2], "UTF-8")
                                    + "&event_id=" + URLEncoder.encode(params[3], "UTF-8")
                                    + "&message_type=" + URLEncoder.encode(params[4], "UTF-8");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(LocalSettings.Base_URL + "add_notification.php").openConnection();
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
                catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.d("Catchexception1", e.toString());


                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Catchexception2",e.toString());

                }
            }
            return null;
        }
    }
}
