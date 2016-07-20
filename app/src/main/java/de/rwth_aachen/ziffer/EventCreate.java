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

        findViewById(R.id.editText2).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openLocationPicker(v);
                }
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
        Spinner spinnerEventType = (Spinner) findViewById(R.id.spinnerEventType);
        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        TextView dateStart = (TextView) findViewById(R.id.dateStart);
        TextView timeStart = (TextView) findViewById(R.id.timeStart);
        TextView timeEnd = (TextView) findViewById(R.id.timeEnd);
        Spinner maxAttendees = (Spinner) findViewById(R.id.maxAttendees);
        EditText description = (EditText) findViewById(R.id.description);

        if (id == R.id.action_save) {
           Log.d("zifferspinner",String.valueOf(spinnerEventType.getSelectedItem()));
            String method = "event";
            String user_name_host,sEventType, eText,eText2, dStart, tStart, tEnd, miAttendees, maAttendees, descr;
            user_name_host= "jasimwhd";
            sEventType = String.valueOf(spinnerEventType.getSelectedItem());
            eText = editText.getText().toString();
            eText2 = editText2.getText().toString();
            dStart = dateStart.getText().toString();
            tStart = timeStart.getText().toString();
            tEnd = timeEnd.getText().toString();
            miAttendees = String.valueOf(0);
            maAttendees = String.valueOf(maxAttendees.getSelectedItem());
            descr = description.getText().toString();


            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method,user_name_host,sEventType,eText, eText2,dStart,tStart,tEnd,miAttendees,maAttendees,descr);
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
                ((TextView)findViewById(R.id.editText2)).setText(place.getName());
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
