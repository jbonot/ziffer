package de.rwth_aachen.ziffer;

import android.Manifest;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity  {
    private static final int[] ICONS = new int[] { R.drawable.ic_room_white_36dp,
            R.drawable.ic_event_white_36dp,
            R.drawable.ic_account_circle_white_36dp,
            R.drawable.ic_feedback_white_36dp };

    private static final int[] TITLES = new int[] {
            R.string.nearby_events,
            R.string.joined_events,
            R.string.profile,
            R.string.notifications
    };

    String user_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_name = extras.getString("user_name");
            //The key argument here must match that used in the other activity
        }
        Log.d("user_mainactivity",user_name);

        ProfileFragment f = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("userkey",user_name);
         f.setArguments(args);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new LocalEventsFragment(), "");
        viewPagerAdapter.addFragments(new JoinedEventsFragment(), "");
        viewPagerAdapter.addFragments(new ProfileFragment(), "");
        viewPagerAdapter.addFragments(new NotificationsFragment(), "");
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = ((TabLayout) findViewById(R.id.tabLayout));
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        tabLayout.getTabAt(3).setIcon(ICONS[3]);

        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN);

        getSupportActionBar().setTitle(getResources().getString(TITLES[0]));

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                getSupportActionBar().setTitle(getResources().getString(TITLES[tab.getPosition()]));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                tab.getIcon().setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });

        this.checkForNewNotifications();
    }

    public void onResume() {
        super.onResume();
        this.checkForNewNotifications();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            Intent intent = new Intent(this, FilterActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_set_en) {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            recreate();
            return true;
        } else if (id == R.id.action_set_de) {
            Locale locale = new Locale("de");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            recreate();
            return true;
        } else if (id == R.id.action_logout) {
            SaveSharedPreference.setUserName(this, "");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public String getMyData() {
        return user_name;
    }
    private void checkForNewNotifications() {
        BackgroundTask task = new BackgroundTask(this);
        task.execute("check_new_notifications", SaveSharedPreference.getUserName(this));
        try {
            int notifications = Integer.parseInt(task.get());
            if (notifications > 0) {
                Toast.makeText(this,
                        getResources().getQuantityString(R.plurals.new_notifications,
                                notifications, notifications), Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void createNewEvent(View view) {
        Intent intent = new Intent(this, EventCreate.class);
        startActivity(intent);
    }
}
