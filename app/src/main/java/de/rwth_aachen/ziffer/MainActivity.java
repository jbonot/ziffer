package de.rwth_aachen.ziffer;

import android.app.SearchManager;
import android.content.Context;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SearchView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new LocalEventsFragment(), getResources().getString(R.string.nearby_events));
        viewPagerAdapter.addFragments(new JoinedEventsFragment(), getResources().getString(R.string.joined_events));
        viewPagerAdapter.addFragments(new ProfileFragment(), getResources().getString(R.string.profile));
        viewPagerAdapter.addFragments(new NotificationsFragment(), getResources().getString(R.string.notifications));
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setAdapter(viewPagerAdapter);
        ((IconTextTabLayout) findViewById(R.id.tabLayout)).setupWithViewPager(viewPager);
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void createNewEvent(View view) {
        Intent intent = new Intent(this, EventCreate.class);
        startActivity(intent);
    }
}
