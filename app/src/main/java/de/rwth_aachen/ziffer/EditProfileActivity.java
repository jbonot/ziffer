package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMG = 1;
    private boolean hasNewPhoto = false;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.hasNewPhoto = false;
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
        getMenuInflater().inflate(R.menu.save, menu);
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

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imageView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

                this.hasNewPhoto = true;

            } else {
                // User cancelled.  Do nothing.
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

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

            String image_str = "";
            if (this.hasNewPhoto) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                ((BitmapDrawable)(((ImageView)findViewById(R.id.imageView)).getDrawable()))
                        .getBitmap().compress(Bitmap.CompressFormat.PNG, 90, stream);
                image_str = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
            }

            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute("profile_data",user_name,firstName,lastName,dob,gLevelSpinner,descr,image_str);

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            SaveSharedPreference.setUserName(this, user_name);
            startActivity(i);
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
