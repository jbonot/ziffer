package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class EventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle b = getIntent().getExtras();
        String event_data = b.getString("event_data");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.hostIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventDetails.this, ProfileActivity.class));
            }
        });
    }
}
