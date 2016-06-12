package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MyJoinedEvents extends AppCompatActivity {

    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        // Add sample data to event list.
        ListView listView = (ListView) findViewById(R.id.listView);
        listAdapter = TestData.getEventListAdapter(this);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyJoinedEvents.this, EventDetails.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.selectedHome).setVisibility(View.GONE);
        findViewById(R.id.selectedEvents).setVisibility(View.VISIBLE);
        findViewById(R.id.selectedProfile).setVisibility(View.GONE);
        findViewById(R.id.selectedNotifications).setVisibility(View.GONE);

        findViewById(R.id.navHome).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyJoinedEvents.this, MainActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.navEvents).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyJoinedEvents.this, MyJoinedEvents.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.navProfile).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyJoinedEvents.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.navNotifications).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyJoinedEvents.this, Notifications.class));
                overridePendingTransition(0, 0);
            }
        });
    }
}
