package de.rwth_aachen.ziffer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class JoinedEventsFragment extends Fragment {

    private ListAdapter listAdapter;
    private String user_key;
    private String joined_data="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.listview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Add sample data to event list.

        BackgroundTask4 task = new BackgroundTask4(getActivity());
        task.execute("get_joined_events",SaveSharedPreference.getUserName(getActivity()));
        ListView listView = (ListView)view.findViewById(R.id.listView);
        try {
            String data = task.get();
            JSONArray arr = new JSONObject(data).getJSONArray("joined_events");
            List<EventListItem> events = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject event = new JSONObject(arr.getString(i));
                EventListItem item = new EventListItem();
                item.setId(event.getInt("event_id"));
                item.setHeadline(event.getString("title"));
                item.setDescription(event.getString("location_name") + ", " + event.getString("location_address"));
                item.setLevel(event.getString("german_level"));
                item.setMaxAttendees(event.getInt("max_attendees"));
                item.setJoinedAttendees(event.getInt("count_attending"));
                events.add(item);
            }

            listAdapter = new EventListAdapter(getActivity(), events.toArray(new EventListItem[0]));
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
                intent.putExtra("event_id", Integer.valueOf(((TextView) view.findViewById(R.id.event_id)).getText().toString()));
                startActivity(intent);
            }
        });
    }

}
