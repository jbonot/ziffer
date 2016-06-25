package de.rwth_aachen.ziffer;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventCreate extends AppCompatActivity {

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

        ((TextView)findViewById(R.id.duration)).setText("1 hour");

        TextView dateStart = (TextView)findViewById(R.id.dateStart);
        dateStart.setText(DatePickerFragment.MONTHS[c.get(Calendar.MONTH)] + " " + c.get(Calendar.DAY_OF_MONTH));
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
        categories.add("German Level");
        categories.add("A1 - Beginner");
        categories.add("A2 - Elementary");
        categories.add("B1 - Intermediate");
        categories.add("B2 - Upper Intermediate");
        categories.add("C1 - Advanced");

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
        this.setupSpinner((Spinner) findViewById(R.id.minAttendees), categories);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.savecancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            return true;
        } else if (id == R.id.action_cancel) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
