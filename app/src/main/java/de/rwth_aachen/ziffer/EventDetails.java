package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class EventDetails extends AppCompatActivity {
    private static final String NOTIFICATION_JOIN_EVENT = "0";
    private static final String NOTIFICATION_CANCEL_EVENT_ATTENDANCE = "1";
    private static final String NOTIFICATION_DELETE_EVENT = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int eventId = getIntent().getIntExtra("event_id", 0);
        this.fetchBasicData(eventId);
    }

    private void fetchBasicData(final int eventId){
        BackgroundTask task = new BackgroundTask();
        task.execute("get_event", String.valueOf(eventId));
        try {
            String data = task.get();
            JSONArray arr = new JSONObject(data).getJSONArray("event");
            if (arr.length() == 0) {
                return;
            }

            JSONObject event = new JSONObject(arr.getString(0));
            ((TextView)findViewById(R.id.eventName)).setText(event.getString("title"));
            ((TextView)findViewById(R.id.germanLevel)).setText(event.getString("german_level"));
            ((TextView)findViewById(R.id.time)).setText(
                    String.format(getResources().getString(R.string.date_from_to),
                            event.getString("date"),
                            event.getString("start_time"),
                            event.getString("end_time")));
            ((TextView)findViewById(R.id.description)).setText(event.getString("description"));

            BackgroundTask locationTask = new BackgroundTask();
            locationTask.execute("get_location", event.getString("location_id"));
            String locationData = locationTask.get();
            if (new JSONObject(locationData).getJSONArray("location").length() == 0) {
                return;
            }

            JSONObject location = new JSONObject(new JSONObject(locationData).getJSONArray("location").getString(0));
            if (location.getString("address").contains(location.getString("name"))) {
                ((TextView)findViewById(R.id.address)).setText(location.getString("address"));
            } else {
                ((TextView)findViewById(R.id.address)).setText(
                        location.getString("name") + ", " + location.getString("address"));
            }

            BackgroundTask hostTask = new BackgroundTask();
            hostTask.execute("get_host_info", event.getString("host_username"));
            String hostData = hostTask.get();
            if (new JSONObject(hostData).getJSONArray("host_info").length() == 0) {
                return;
            }

            JSONObject host = new JSONObject(new JSONObject(hostData).getJSONArray("host_info").getString(0));
            ((TextView)findViewById(R.id.hostName)).setText(
                    String.format(getResources().getString(R.string.hosted_by),
                            host.getString("first_name"), host.getString("last_name")));
            ((TextView)findViewById(R.id.hostGermanLevel)).setText(host.getString("german_level"));
            Picasso.with(this).load(
                    LocalSettings.Base_URL + "./image/" + host.getString("image"))
                    .into((ImageView) findViewById(R.id.hostIcon));

            final String hostUsername = event.getString("host_username");
            findViewById(R.id.hostInfo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EventDetails.this, ProfileActivity.class);
                    intent.putExtra("username", hostUsername);
                    startActivity(intent);
                }
            });

            if (hostUsername.equals(SaveSharedPreference.getUserName(this))) {
                this.setActionButton(R.id.button_delete);
            } else {
                // TODO: Check if the event is full
                // TODO: Check if the user has already joined the event or not
                this.setActionButton(R.id.button_join);
            }

            findViewById(R.id.button_join).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BackgroundTask task = new BackgroundTask();
                    task.execute("add_notification",
                            hostUsername, SaveSharedPreference.getUserName(v.getContext()),
                            Integer.toString(eventId), NOTIFICATION_JOIN_EVENT);
                    try {
                        if (task.get().equals(BackgroundTask.RESULT_SUCCESS)) {
                            Toast.makeText(v.getContext(), "Notification sent", Toast.LENGTH_SHORT).show();
                            setActionButton(R.id.button_cancel_attendance);

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });

            findViewById(R.id.button_cancel_attendance).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BackgroundTask task = new BackgroundTask();
                    task.execute("add_notification",
                            hostUsername, SaveSharedPreference.getUserName(v.getContext()),
                            Integer.toString(eventId), NOTIFICATION_CANCEL_EVENT_ATTENDANCE);
                    try {
                        if (task.get().equals(BackgroundTask.RESULT_SUCCESS)) {
                            Toast.makeText(v.getContext(), "Notification sent", Toast.LENGTH_SHORT).show();
                            setActionButton(R.id.button_join);

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });

            findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Send notification to all guests
                    // TODO: Delete event
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setActionButton(int id) {
        switch (id) {
            case R.id.button_join:
                findViewById(R.id.button_join).setVisibility(View.VISIBLE);
                findViewById(R.id.button_cancel_attendance).setVisibility(View.GONE);
                findViewById(R.id.button_delete).setVisibility(View.GONE);
                break;
            case R.id.button_cancel_attendance:
                findViewById(R.id.button_join).setVisibility(View.GONE);
                findViewById(R.id.button_cancel_attendance).setVisibility(View.VISIBLE);
                findViewById(R.id.button_delete).setVisibility(View.GONE);
                break;
            case R.id.button_delete:
                findViewById(R.id.button_join).setVisibility(View.GONE);
                findViewById(R.id.button_cancel_attendance).setVisibility(View.GONE);
                findViewById(R.id.button_delete).setVisibility(View.VISIBLE);
                break;
            default:
                findViewById(R.id.button_join).setVisibility(View.GONE);
                findViewById(R.id.button_cancel_attendance).setVisibility(View.GONE);
                findViewById(R.id.button_delete).setVisibility(View.GONE);
        }
    }

    private class BackgroundTask extends AsyncTask<String,Void,String> {
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
            } else if (params[0].equals("add_notification")) {
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
}
