package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new ProfileMainFragment(), "About Me");
        viewPagerAdapter.addFragments(new ProfileEventsFragment(), "Jasim's Events");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.selectedHome).setVisibility(View.GONE);
        findViewById(R.id.selectedEvents).setVisibility(View.GONE);
        findViewById(R.id.selectedProfile).setVisibility(View.VISIBLE);
        findViewById(R.id.selectedNotifications).setVisibility(View.GONE);

        findViewById(R.id.navHome).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.navEvents).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MyEventsActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.navProfile).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.navNotifications).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, Notifications.class));
                overridePendingTransition(0, 0);
            }
        });
    }
}
