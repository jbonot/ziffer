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
                new EventListItem("Deutsch A1 Tutor Session, Sunday 8PM, Bad Godesberg.", "Clara is organizing a meetup for 8 people at her residence in Bad Godesberg."),
                new EventListItem("Teaching German A1, Monday 8AM, Bonn Library.", "Hosted by Sven. Tutor sessions for A1 grammar and conversation."),
                new EventListItem("Halal BBQ, Sunday 8PM, Uni Bonn.", "Thomas is organizing a barbeque for 15 people. Please bring your own snacks."),
                new EventListItem("Deutsch B1 Tutor Session, Thursday 7:30PM, Römerlager 1 Study Room.", "Hosted by Julia. Only for residents at Römerlager Studentenwohnheim.")
        });
    }
}
