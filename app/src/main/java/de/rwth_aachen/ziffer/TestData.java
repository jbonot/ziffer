package de.rwth_aachen.ziffer;

import android.content.Context;
import android.util.Log;
import android.widget.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for entering test data only.
 */
public class TestData {
    String data_event;
    public TestData(String data_event) {
        this.data_event = data_event;
    }




    /**
     * Creates an adapter for the event list with sample data.
     *
     * @param context
     * @return
     */
    public ListAdapter getEventListAdapter(Context context){
        JSONArray jsonArray;
        ArrayList<EventListItem> e = new ArrayList<EventListItem>();
        try
        {

            JSONObject root = new JSONObject(data_event);
            jsonArray= root.getJSONArray("event_data");
            int count=0;
            String german_level_event,title,location;
           while(count<jsonArray.length())
           {
               JSONObject JO= jsonArray.getJSONObject(count);
               german_level_event= JO.getString("german_level_event");
               title=JO.getString("title");
               location=JO.getString("location");
               e.add(new EventListItem(german_level_event, title, location));

           }

        }
        catch (JSONException e1)
        {
            Log.d("JSONexception",e1.toString());
            e1.printStackTrace();
        }

       /* return new EventListAdapter(context, new EventListItem[]{
                new EventListItem("A1", "Deutsch A1 Tutor Session", "SuperC, Aachen, Germany"),
                new EventListItem("A1", "Teaching German A1", "RWTH Bibliothek, Aachen, Germany"),
                new EventListItem("B2", "Conversation Practice", "Elisenpark, Aachen, Germany"),
                new EventListItem("B1", "Deutsch B1 Tutor Session", "Pontstraße 8, Aachen, Germany")
        }); */

        EventListItem[] events = new EventListItem[e.size()];
        for(int i=0; i < events.length; i++) {
            events[i] = e.get(i);
        }

        return new EventListAdapter(context, events);
    }
    /**
     * Creates an adapter for the notifications with sample data.
     *
     * @param context
     * @return
     */
    public ListAdapter getNotificationListAdapter(Context context){
        return new NotificationListAdapter(context, new NotificationListItem[]{
                new NotificationListItem("Joram has joined your event \"Deutsch A1 Tutor Session.\"", "1 min ago"),
                new NotificationListItem("Uri has joined your event \"Deutsch A1 Tutor Session.\"", "2 hrs ago"),
                new NotificationListItem("Areej has joined your event \"Deutsch A1 Tutor Session.\"", "1 day ago"),
                new NotificationListItem("Marwan has joined your event \"Deutsch A1 Tutor Session.\"", "1 day ago")
        });
    }
}
