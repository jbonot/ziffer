package de.rwth_aachen.ziffer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<EventListItem> {

    private Context context;
    private EventListItem[] values;

    public EventListAdapter(Context context, EventListItem[] values) {
        super(context, R.layout.event_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.event_list_item, parent, false);

        ((TextView)rowView.findViewById(R.id.textHeadline))
                .setText(this.values[position].getHeadline());
        ((TextView)rowView.findViewById(R.id.textDescription))
                .setText(this.values[position].getDescription());

        return rowView;
    }
}
