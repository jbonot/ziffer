package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GuestListActivity extends AppCompatActivity {

    private ListView listView;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String guestsJSON = getIntent().getStringExtra("guest_list");

        List<GuestListItem> guests = new ArrayList<>();
        JSONArray jsonGuestList = null;
        try {
            jsonGuestList = new JSONObject(guestsJSON).getJSONArray("event_guests");
            for (int i = 0; i < jsonGuestList.length(); i++) {
                JSONObject jsonGuest = new JSONObject(jsonGuestList.getString(i));
                GuestListItem guest = new GuestListItem();
                guest.setUsername(jsonGuest.getString("username"));
                guest.setFirstName(jsonGuest.getString("first_name"));
                guest.setLastName(jsonGuest.getString("last_name"));
                guest.setGermanLevel(jsonGuest.getString("german_level"));
                guest.setImageFile(jsonGuest.getString("image"));
                guests.add(guest);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.listView = (ListView)findViewById(R.id.guest_list);
        this.listAdapter = new GuestListAdapter(this, guests.toArray(new GuestListItem[0]));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                intent.putExtra("username",
                        ((TextView) view.findViewById(R.id.username)).getText().toString());
                startActivity(intent);
            }
        });
    }
}
