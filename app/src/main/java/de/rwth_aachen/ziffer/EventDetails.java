package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EventDetails extends AppCompatActivity {
    private static final String NOTIFICATION_JOIN_EVENT = "0";
    private static final String NOTIFICATION_CANCEL_EVENT_ATTENDANCE = "1";
    private static final String NOTIFICATION_DELETE_EVENT = "3";

    private String hostUsername;
    private List<GuestListItem> guests;
    private int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        eventId = getIntent().getIntExtra("event_id", 0);
        this.fetchBasicData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (hostUsername.equals(SaveSharedPreference.getUserName(this))) {
            getMenuInflater().inflate(R.menu.event_options, menu);
            return true;
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {

            for (GuestListItem guest : this.guests) {
                BackgroundTaskEventDetails notify = new BackgroundTaskEventDetails();
                notify.execute("add_notification", guest.getUsername(),
                        SaveSharedPreference.getUserName(this),
                        String.valueOf(this.eventId),
                        String.valueOf(NOTIFICATION_DELETE_EVENT));
            }

            Toast.makeText(this, getResources().getString(R.string.notify_delete_success),
                    Toast.LENGTH_SHORT).show();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchBasicData(){
        BackgroundTaskEventDetails task = new BackgroundTaskEventDetails();
        task.execute("get_event", String.valueOf(eventId));
        try {
            String data = task.get();
            JSONArray arr = new JSONObject(data).getJSONArray("event");
            if (arr.length() == 0) {
                return;
            }

            JSONObject event = new JSONObject(arr.getString(0));
            ((TextView)findViewById(R.id.eventName)).setText(event.getString("title"));
            ((TextView)findViewById(R.id.germanLevel)).setText(
                    getResources().getString(R.string.german_level) + " "
                            + event.getString("german_level"));
            ((TextView)findViewById(R.id.time)).setText(
                    String.format(getResources().getString(R.string.date_from_to),
                            event.getString("date"),
                            event.getString("start_time"),
                            event.getString("end_time")));
            ((TextView)findViewById(R.id.description)).setText(event.getString("description"));

            BackgroundTaskEventDetails locationTask = new BackgroundTaskEventDetails();
            locationTask.execute("get_location", event.getString("location_id"));
            String locationData = locationTask.get();
            if (new JSONObject(locationData).getJSONArray("location").length() == 0) {
                return;
            }

            JSONObject location = new JSONObject(new JSONObject(locationData).getJSONArray("location").getString(0));
            if (location.getString("address").contains(location.getString("name"))) {
                ((TextView)findViewById(R.id.address)).setText(location.getString("address"));
            } else {
                ((TextView)findViewById(R.id.address)).setText(
                        location.getString("name") + ", " + location.getString("address"));
            }

            BackgroundTaskEventDetails hostTask = new BackgroundTaskEventDetails();
            hostTask.execute("get_host_info", event.getString("host_username"));
            String hostData = hostTask.get();
            if (new JSONObject(hostData).getJSONArray("host_info").length() == 0) {
                return;
            }

            JSONObject host = new JSONObject(new JSONObject(hostData).getJSONArray("host_info").getString(0));
            ((TextView)findViewById(R.id.hostName)).setText(
                    String.format(getResources().getString(R.string.hosted_by),
                            host.getString("first_name"), host.getString("last_name")));
            ((TextView)findViewById(R.id.hostGermanLevel)).setText(host.getString("german_level"));
            Picasso.with(this).load(
                    LocalSettings.Base_URL + "./image/" + host.getString("image"))
                    .into((ImageView) findViewById(R.id.hostIcon));

            hostUsername = event.getString("host_username");
            findViewById(R.id.hostInfo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EventDetails.this, ProfileActivity.class);
                    intent.putExtra("username", hostUsername);
                    startActivity(intent);
                }
            });

            BackgroundTaskEventDetails guestTask = new BackgroundTaskEventDetails();
            guestTask.execute("get_event_guests", String.valueOf(eventId));
            final String guestListJson = guestTask.get();

            guests = new ArrayList<>();
            JSONArray jsonGuestList = new JSONObject(guestListJson).getJSONArray("event_guests");
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

            if (hostUsername.equals(SaveSharedPreference.getUserName(this))) {
                findViewById(R.id.button_guests).setVisibility(View.VISIBLE);
                findViewById(R.id.button_guests).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), GuestListActivity.class);
                        intent.putExtra("guest_list", guestListJson);
                        startActivity(intent);
                    }
                });

            } else {
                if (guests.size() < event.getInt("max_attendees")) {
                    int button = R.id.button_join;
                    for (GuestListItem guest : guests) {
                        if (SaveSharedPreference.getUserName(this).equals(guest.getUsername())) {
                            button = R.id.button_cancel_attendance;
                            break;
                        }
                    }

                    this.setActionButton(button);
                } else {
                    this.setActionButton(-1);
                }
            }

            findViewById(R.id.button_join).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BackgroundTaskEventDetails joinTask = new BackgroundTaskEventDetails();
                    joinTask.execute("join_event", String.valueOf(eventId),
                            SaveSharedPreference.getUserName(v.getContext()));
                    BackgroundTaskEventDetails notificationTask = new BackgroundTaskEventDetails();
                    notificationTask.execute("add_notification",
                            hostUsername, SaveSharedPreference.getUserName(v.getContext()),
                            Integer.toString(eventId), NOTIFICATION_JOIN_EVENT);
                    try {
                        if (joinTask.get().equals(BackgroundTaskEventDetails.RESULT_SUCCESS)
                                && notificationTask.get().equals(BackgroundTaskEventDetails.RESULT_SUCCESS)) {
                            Toast.makeText(v.getContext(), getResources().getString(R.string.notify_join_success), Toast.LENGTH_SHORT).show();
                            setActionButton(R.id.button_cancel_attendance);

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });

            findViewById(R.id.button_cancel_attendance).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BackgroundTaskEventDetails cancelTask = new BackgroundTaskEventDetails();
                    cancelTask.execute("delete_event_guest", Integer.toString(eventId),
                            SaveSharedPreference.getUserName(v.getContext()));
                    BackgroundTaskEventDetails task = new BackgroundTaskEventDetails();
                    task.execute("add_notification",
                            hostUsername, SaveSharedPreference.getUserName(v.getContext()),
                            Integer.toString(eventId), NOTIFICATION_CANCEL_EVENT_ATTENDANCE);
                    try {
                        if (cancelTask.get().equals(BackgroundTaskEventDetails.RESULT_SUCCESS)
                                && task.get().equals(BackgroundTaskEventDetails.RESULT_SUCCESS)) {
                            Toast.makeText(v.getContext(),
                                    getResources().getString(R.string.notify_remove_guest_success),
                                    Toast.LENGTH_SHORT).show();
                            setActionButton(R.id.button_join);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setActionButton(int id) {
        switch (id) {
            case R.id.button_join:
                findViewById(R.id.button_join).setVisibility(View.VISIBLE);
                findViewById(R.id.button_cancel_attendance).setVisibility(View.GONE);
                break;
            case R.id.button_cancel_attendance:
                findViewById(R.id.button_join).setVisibility(View.GONE);
                findViewById(R.id.button_cancel_attendance).setVisibility(View.VISIBLE);
                break;
            default:
                findViewById(R.id.button_join).setVisibility(View.GONE);
                findViewById(R.id.button_cancel_attendance).setVisibility(View.GONE);
        }
    }
}
