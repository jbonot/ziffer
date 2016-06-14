package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MyEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_container_navbar);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new JoinedEventsFragment(), "Joined Events");
        viewPagerAdapter.addFragments(new ProfileEventsFragment(), "Created Events");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);
        ((TabLayout) findViewById(R.id.tabLayout)).setupWithViewPager(viewPager);

        findViewById(R.id.selectedHome).setVisibility(View.GONE);
        findViewById(R.id.selectedEvents).setVisibility(View.VISIBLE);
        findViewById(R.id.selectedProfile).setVisibility(View.GONE);
        findViewById(R.id.selectedNotifications).setVisibility(View.GONE);

        findViewById(R.id.navEvents).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyEventsActivity.this, MyEventsActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.navProfile).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyEventsActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.navNotifications).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyEventsActivity.this, Notifications.class));
                overridePendingTransition(0, 0);
            }
        });
    }
}
