package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventCreate extends AppCompatActivity {
    private final static int PLACE_PICKER_REQUEST = 1;
    private Place eventLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_create_form);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView timeStart = (TextView)findViewById(R.id.timeStart);
        TextView timeEnd = (TextView)findViewById(R.id.timeEnd);

        // Use the time format that corresponds with the phone settings.
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat.is24HourFormat(this) ? "H:mm" : "h:mm a");
        Calendar c = Calendar.getInstance();

        // Set start time to the next hour.
        c.set(Calendar.MINUTE, 0);
        c.add(Calendar.HOUR, 1);
        timeStart.setText(dateFormat.format(c.getTime()));

        // Set end time as one hour later.
        c.add(Calendar.HOUR, 1);
        timeEnd.setText(dateFormat.format(c.getTime()));

        timeStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", v.getId());
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        timeEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", v.getId());
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        TextView dateStart = (TextView)findViewById(R.id.dateStart);
        dateStart.setText(new SimpleDateFormat(
                getResources().getConfiguration().locale.getLanguage().equals("de")
                        ? "E, d. MMMM" : "EEE, MMMM d").format(c.getTime()));
        dateStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", v.getId());
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add(getResources().getString(R.string.german_level));
        categories.add(getResources().getString(R.string.a1_beginner));
        categories.add(getResources().getString(R.string.a2_elementary));
        categories.add(getResources().getString(R.string.b1_intermediate));
        categories.add(getResources().getString(R.string.b2_upper_intermediate));
        categories.add(getResources().getString(R.string.c1_advanced));

        this.setupSpinner((Spinner) findViewById(R.id.spinnerEventType), categories);

        categories = new ArrayList<String>();
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        categories.add("6");
        categories.add("7");
        categories.add("8");
        categories.add("9");
        categories.add("10");
        categories.add("20");
        categories.add("30");

        this.setupSpinner((Spinner) findViewById(R.id.maxAttendees), categories);

        findViewById(R.id.location_button).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  openLocationPicker(v);
              }
          });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            BackgroundTaskCreateEvent task = new BackgroundTaskCreateEvent();
            task.execute("create_event",
                    String.valueOf(eventLocation.getName()),
                    String.valueOf(eventLocation.getAddress()),
                    String.valueOf(eventLocation.getId()),
                    String.valueOf(eventLocation.getLatLng().latitude),
                    String.valueOf(eventLocation.getLatLng().longitude),
                    SaveSharedPreference.getUserName(this),
                    ((Spinner) findViewById(R.id.spinnerEventType)).getSelectedItem().toString(),
                    ((TextView) findViewById(R.id.title)).getText().toString(),
                    ((TextView) findViewById(R.id.dateStart)).getText().toString(),
                    ((TextView) findViewById(R.id.timeStart)).getText().toString(),
                    ((TextView) findViewById(R.id.timeEnd)).getText().toString(),
                    ((Spinner) findViewById(R.id.maxAttendees)).getSelectedItem().toString(),
                    ((TextView) findViewById(R.id.description)).getText().toString());

            try {
                Intent intent = new Intent(this, EventDetails.class);
                intent.putExtra("event_id", Integer.valueOf(task.get()));
                startActivity(intent);
                Toast.makeText(this, getResources().getString(R.string.notify_create_success),
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openLocationPicker(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e("ZIFFER", e.getMessage(), e);
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e("ZIFFER", e.getMessage(), e);
            e.printStackTrace();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {

            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                findViewById(R.id.location_info).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.location_name)).setText(place.getName());
                ((TextView)findViewById(R.id.location_address)).setText(place.getAddress());
                this.eventLocation = place;
            }
        }
    }

    private void setupSpinner(Spinner spinner, List<String> values) {

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Do nothing.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing.
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
}
