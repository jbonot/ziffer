package de.rwth_aachen.ziffer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GuestListAdapter extends ArrayAdapter<GuestListItem> {

    private Context context;
    private GuestListItem[] values;

    public GuestListAdapter(Context context, GuestListItem[] values) {
        super(context, R.layout.guest_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.guest_list_item, parent, false);

        ((TextView)rowView.findViewById(R.id.name))
                .setText(this.values[position].getFirstName() + " "
                        + this.values[position].getLastName());
        ((TextView)rowView.findViewById(R.id.username))
                .setText(String.valueOf(this.values[position].getUsername()));

        if (this.values[position].getImageFile() != null &&
                !this.values[position].getImageFile().isEmpty()) {
            Picasso.with(rowView.getContext()).load(
                    LocalSettings.Base_URL + "./image/" + this.values[position].getImageFile())
                    .into((ImageView) rowView.findViewById(R.id.icon));
        }

        String level = this.values[position].getGermanLevel();
        switch (level) {
            case "A1":
                ((TextView)rowView.findViewById(R.id.german_level))
                        .setText(rowView.getResources().getString(R.string.a1_beginner));
                break;
            case "A2":
                ((TextView)rowView.findViewById(R.id.german_level))
                        .setText(rowView.getResources().getString(R.string.a1_beginner));
                break;
            case "B1":
                ((TextView)rowView.findViewById(R.id.german_level))
                        .setText(rowView.getResources().getString(R.string.b1_intermediate));
                break;
            case "B2":
                ((TextView)rowView.findViewById(R.id.german_level))
                        .setText(rowView.getResources().getString(R.string.b2_upper_intermediate));
                break;
            case "C1":
                ((TextView)rowView.findViewById(R.id.german_level))
                        .setText(rowView.getResources().getString(R.string.c1_advanced));
                break;
        }

        return rowView;
    }
}
