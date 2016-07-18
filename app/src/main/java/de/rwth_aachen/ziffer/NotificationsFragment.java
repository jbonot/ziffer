package de.rwth_aachen.ziffer;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    private ListAdapter listAdapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Add sample data to event list.
        this.listView = (ListView)view.findViewById(R.id.listView);
        this.fetchNotifications();
        
       this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventDetails.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible && isAdded()) {
            this.fetchNotifications();
        }
    }

    private void fetchNotifications() {
        Log.d("ZIFFER_notifications", "fetchNotifications");
        BackgroundTask task = new BackgroundTask();
        task.execute("get_notifications", SaveSharedPreference.getUserName(getActivity()));
        try {
            String data = task.get();
            List<String> unread = new ArrayList<>();
            List<NotificationListItem> list = new ArrayList<>();
            JSONArray arr = new JSONObject(data).getJSONArray("notifications");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject notification = new JSONObject(arr.getString(i));
                notification.getInt("notification_id");
                notification.getInt("event_id");
                notification.getInt("read_status");
                notification.getString("user_sender");

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = formatter.parse(notification.getString("timestamp"));
                Calendar c = Calendar.getInstance();
                long timeDifference = c.getTimeInMillis() - date.getTime();
                String timeDifferenceStr = "";
                int days = (int)TimeUnit.MILLISECONDS.toDays(timeDifference);
                int hours = (int)(TimeUnit.MILLISECONDS.toHours(timeDifference) -
                        TimeUnit.MINUTES.toHours(TimeUnit.MILLISECONDS.toDays(timeDifference)));
                int minutes = (int)(TimeUnit.MILLISECONDS.toMinutes(timeDifference) -
                        TimeUnit.MILLISECONDS.toMinutes(TimeUnit.MINUTES.toHours(TimeUnit.MILLISECONDS.toDays(timeDifference))));
                if (days > 0) {
                    timeDifferenceStr = getResources().getQuantityString(R.plurals.days_ago, days + 1, days + 1);
                } else if (hours > 0) {
                    timeDifferenceStr = getResources().getQuantityString(R.plurals.hours_ago, hours + 1, hours + 1);
                } else if (minutes > 0) {
                    timeDifferenceStr = getResources().getQuantityString(R.plurals.minutes_ago, minutes + 1, minutes + 1);
                } else {
                    timeDifferenceStr = getResources().getString(R.string.moments_ago);
                }

                String formatString = getResources().getStringArray(R.array.notifications_array)[notification.getInt("message_type")];
                String senderName = notification.getString("sender_firstname") + " " + notification.getString("sender_lastname");
                NotificationListItem item = new NotificationListItem(String.format(formatString, senderName, notification.getString("event_name")), timeDifferenceStr);

                if (notification.getInt("read_status") == 1) {
                    item.setIsRead(true);
                } else {
                    unread.add(notification.getString("notification_id"));
                }

                if (!notification.getString("sender_image").equals("")) {
                    item.setImageFile(notification.getString("sender_image"));
                }

                list.add(item);
            }

            listAdapter = new NotificationListAdapter(getActivity(),list.toArray(new NotificationListItem[0]));
            listView.setAdapter(listAdapter);

            if (unread.size() > 0) {
                String toUpdate = "";
                for (String id : unread) {
                    toUpdate += id + " ";
                }

                Log.d("ZIFFER_notifications", toUpdate);
                new BackgroundTask().execute("update_notification_status", toUpdate.substring(0, toUpdate.length() - 1));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private class BackgroundTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            if (params[0].equals("get_notifications")) {
                try
                {
                    String urlParams = "user_recipient=" + URLEncoder.encode(params[1], "UTF-8");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(LocalSettings.Base_URL + "get_notifications.php").openConnection();
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
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (params[0].equals("update_notification_status")) {
                try
                {
                    String urlParams = "notification_ids=" + URLEncoder.encode(params[1], "UTF-8");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(LocalSettings.Base_URL + "update_notification_status.php").openConnection();
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

                    Log.d("ZIFFER_notifications", data);
                    httpURLConnection.disconnect();
                    return "SUCCESS";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }
}
