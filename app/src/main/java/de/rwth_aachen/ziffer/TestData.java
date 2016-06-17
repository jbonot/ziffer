package de.rwth_aachen.ziffer;

import android.content.Context;
import android.widget.ListAdapter;

/**
 * Responsible for entering test data only.
 */
public class TestData {

    /**
     * Creates an adapter for the event list with sample data.
     *
     * @param context
     * @return
     */
    public static ListAdapter getEventListAdapter(Context context){
        return new EventListAdapter(context, new EventListItem[]{
                new EventListItem("A1", "Deutsch A1 Tutor Session", "SuperC, Aachen, Germany"),
                new EventListItem("A1", "Teaching German A1", "RWTH Bibliothek, Aachen, Germany"),
                new EventListItem("B2", "Conversation Practice", "Elisenpark, Aachen, Germany"),
                new EventListItem("B1", "Deutsch B1 Tutor Session", "Pontstraße 8, Aachen, Germany")
        });
    }
    /**
     * Creates an adapter for the notifications with sample data.
     *
     * @param context
     * @return
     */
    public static ListAdapter getNotificationListAdapter(Context context){
        return new NotificationListAdapter(context, new NotificationListItem[]{
                new NotificationListItem("Joram has joined your event \"Deutsch A1 Tutor Session.\"", "1 min ago"),
                new NotificationListItem("Uri has joined your event \"Deutsch A1 Tutor Session.\"", "2 hrs ago"),
                new NotificationListItem("Areej has joined your event \"Deutsch A1 Tutor Session.\"", "1 day ago"),
                new NotificationListItem("Marwan has joined your event \"Deutsch A1 Tutor Session.\"", "1 day ago")
        });
    }
}
