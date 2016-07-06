package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get the user data from user name (RegisterActivity)



        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add(getResources().getString(R.string.german_level));
        categories.add(getResources().getString(R.string.a1_beginner));
        categories.add(getResources().getString(R.string.a2_elementary));
        categories.add(getResources().getString(R.string.b1_intermediate));
        categories.add(getResources().getString(R.string.b2_upper_intermediate));
        categories.add(getResources().getString(R.string.c1_advanced));
        // Spinner click listener
        Spinner germanLevel = (Spinner) findViewById(R.id.german_level);
        germanLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Do nothing.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing.
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        germanLevel.setAdapter(dataAdapter);

        findViewById(R.id.date_of_birth).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new BirthDatePickerFragment().show(getSupportFragmentManager(), "datePicker");
                }
            }
        });

        findViewById(R.id.date_of_birth).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new BirthDatePickerFragment().show(getSupportFragmentManager(), "datePicker");
            }
        });
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
            attemptSave();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void attemptSave() {

        // Reset errors.
        EditText firstNameView = (EditText) findViewById(R.id.first_name);
        EditText lastNameView = (EditText) findViewById(R.id.last_name);
        EditText dobView = (EditText) findViewById(R.id.date_of_birth);
        Spinner germanLevelSpinner = (Spinner) findViewById(R.id.german_level);
        EditText description = (EditText) findViewById(R.id.description);

        firstNameView.setError(null);
        lastNameView.setError(null);
        dobView.setError(null);

        // Store values at the time of the login attempt.
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        String dob = dobView.getText().toString();
        String descr =description.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (germanLevelSpinner.getSelectedItemPosition() == 0) {
            TextView germanLevel = (TextView) germanLevelSpinner.getSelectedView();
            germanLevel.setError(getString(R.string.error_unselected_german_level));
            focusView = germanLevel;
            cancel = true;
        }

        if (TextUtils.isEmpty(dob)) {
            dobView.setError(getString(R.string.error_field_required));
            focusView = dobView;
            cancel = true;
        } else if (!isDateOfBirthValid(dob)) {
            dobView.setError(getString(R.string.error_invalid_date_of_birth));
            focusView = dobView;
            cancel = true;
        }

        if (TextUtils.isEmpty(lastName)) {
            lastNameView.setError(getString(R.string.error_field_required));
            focusView = lastNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(firstName)) {
            firstNameView.setError(getString(R.string.error_field_required));
            focusView = firstNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Store user information and forward to the home page.
            Intent intent = getIntent();

            String user_name = intent.getStringExtra("userkey");
            Log.d("uservaluenew",user_name);

            String gLevelSpinner = String.valueOf(germanLevelSpinner.getSelectedItem());
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute("profile_data",user_name,firstName,lastName,dob,gLevelSpinner,descr);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private boolean isDateOfBirthValid(String dob){
        SimpleDateFormat dobFormat = new SimpleDateFormat(getResources().getConfiguration().locale.getLanguage().equals("de")
                ? "d. MMMM, yyyy" : "MMMM d, yyyy");
        try {
            dobFormat.parse(dob);
            return true;
        } catch (ParseException e) {
            Log.e("ZIFFER", e.getMessage(), e);
        }

        return false;
    }
}
