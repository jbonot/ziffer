package de.rwth_aachen.ziffer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class FilterActivity extends AppCompatActivity {

    private static int MIN_RADIUS = 1;
    private static int MAX_RADIUS = 50;
    private static int DEFAULT_RADIUS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // Assign OnClick listeners
        findViewById(R.id.titleGermanLevel).setOnClickListener(
                new FilterOnClickListener(R.id.contentGermanLevel));
        findViewById(R.id.titleMaxRadius).setOnClickListener(
                new FilterOnClickListener(R.id.contentMaxRadius));
        findViewById(R.id.titleGuests).setOnClickListener(
                new FilterOnClickListener(R.id.contentGuests));

        // Set slider min and max values for "max radius"
        ((TextView)findViewById(R.id.textMinRadius)).setText(String.valueOf(MIN_RADIUS));
        ((TextView)findViewById(R.id.textMaxRadius)).setText(String.valueOf(MAX_RADIUS));
        ((SeekBar)findViewById(R.id.maxRadius)).setMax(MAX_RADIUS - MIN_RADIUS);

        ((SeekBar)findViewById(R.id.maxRadius)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView) findViewById(R.id.textRadiusValue)).setText(
                        Html.fromHtml(String.format("Within <b>%s km</b>", progress + MIN_RADIUS)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing.
            }
        });

        ((TextView)findViewById(R.id.restore)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterActivity.this.restoreDefaultValues();
            }
        });

        this.restoreDefaultValues();

        findViewById(R.id.selectedHome).setVisibility(View.VISIBLE);
        findViewById(R.id.selectedEvents).setVisibility(View.GONE);
        findViewById(R.id.selectedProfile).setVisibility(View.GONE);
        findViewById(R.id.selectedNotifications).setVisibility(View.GONE);

        findViewById(R.id.navHome).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.navEvents).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(FilterActivity.this, MyEventsActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.navProfile).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(FilterActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.navNotifications).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(FilterActivity.this, Notifications.class));
                overridePendingTransition(0, 0);
            }
        });
    }

    public void restoreDefaultValues() {
        ((CheckBox)findViewById(R.id.checkBoxA1)).setChecked(true);
        ((CheckBox)findViewById(R.id.checkBoxA2)).setChecked(true);
        ((CheckBox)findViewById(R.id.checkBoxB1)).setChecked(true);
        ((CheckBox)findViewById(R.id.checkBoxB2)).setChecked(true);
        ((CheckBox)findViewById(R.id.checkBoxC1)).setChecked(true);

        ((SeekBar)findViewById(R.id.maxRadius)).setProgress(DEFAULT_RADIUS - MIN_RADIUS);

        ((CheckBox)findViewById(R.id.checkBoxGuests1)).setChecked(true);
        ((CheckBox)findViewById(R.id.checkBoxGuests2)).setChecked(true);
        ((CheckBox)findViewById(R.id.checkBoxGuests3)).setChecked(true);
        ((CheckBox)findViewById(R.id.checkBoxGuests4)).setChecked(true);
    }


    private class FilterOnClickListener implements View.OnClickListener {
        private int viewId;

        public FilterOnClickListener(int viewId) {
            this.viewId = viewId;
        }

        @Override
        public void onClick(View v) {
            View content = findViewById(this.viewId);
            if (content.getVisibility() == View.GONE) {
                content.setVisibility(View.VISIBLE);
            } else {
                content.setVisibility(View.GONE);
            }
        }
    }
}
