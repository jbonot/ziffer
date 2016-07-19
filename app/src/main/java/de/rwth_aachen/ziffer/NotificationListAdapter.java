package de.rwth_aachen.ziffer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NotificationListAdapter extends ArrayAdapter<NotificationListItem> {

    private Context context;
    private NotificationListItem[] values;

    public NotificationListAdapter(Context context, NotificationListItem[] values) {
        super(context, R.layout.event_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.notification_list_item, parent, false);

        ((TextView)rowView.findViewById(R.id.message))
                .setText(this.values[position].getMessage());
        ((TextView)rowView.findViewById(R.id.date))
                .setText(this.values[position].getTimestamp());
        ((TextView)rowView.findViewById(R.id.event_id))
                .setText(String.valueOf(this.values[position].getEventId()));

        if (this.values[position].getImageFile() != null &&
                !this.values[position].getImageFile().isEmpty()) {
            Picasso.with(rowView.getContext()).load(
                    LocalSettings.Base_URL + "./image/" + this.values[position].getImageFile())
                    .into((ImageView) rowView.findViewById(R.id.icon));
        }

        if (!this.values[position].isRead()) {
            rowView.setBackgroundColor(0x50A8DADC);
        }

        return rowView;
    }
}
