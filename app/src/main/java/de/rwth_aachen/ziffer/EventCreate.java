package de.rwth_aachen.ziffer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class EventCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_create_form);

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

        this.setupSpinner((Spinner) findViewById(R.id.spinnerMaxAttendees), categories);

        categories = new ArrayList<String>();
        categories.add("1 hour before");
        categories.add("6 hours before");
        categories.add("1 day before");
        categories.add("3 days before");

        this.setupSpinner((Spinner) findViewById(R.id.spinnerRsvpDeadline), categories);
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
