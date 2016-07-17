package de.rwth_aachen.ziffer;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    private ListAdapter listAdapter;
        private String notification_data="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Add sample data to event list.
        ListView listView = (ListView)view.findViewById(R.id.listView);
        BackgroundTask task = new BackgroundTask();
        task.execute("get_notifications", "andrea.allen");
        try {
            String data = task.get();
            List<NotificationListItem> list = new ArrayList<>();
            JSONArray arr = new JSONObject(data).getJSONArray("notifications");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject notification = new JSONObject(arr.getString(i));
                notification.getInt("notification_id");
                notification.getInt("event_id");
                notification.getInt("read_status");
                notification.getString("user_sender");
                String formatString = getResources().getStringArray(R.array.notifications_array)[notification.getInt("message_type")];
                String senderName = notification.getString("sender_firstname") + " " + notification.getString("sender_lastname");
                list.add(new NotificationListItem(String.format(formatString, senderName, notification.getString("event_name")), notification.getString("timestamp")));
            }

            listAdapter = new NotificationListAdapter(getActivity(),list.toArray(new NotificationListItem[0]));
            listView.setAdapter(listAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventDetails.class);
                startActivity(intent);
            }
        });
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
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
