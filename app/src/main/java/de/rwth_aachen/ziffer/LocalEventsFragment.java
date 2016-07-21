package de.rwth_aachen.ziffer;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Displays the list of events in the area.
 */
public class LocalEventsFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {
    private final String TAG = this.getClass().getSimpleName();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean zoomNeeded = true;

    private boolean mPermissionDenied = false;
    private ListAdapter listAdapter;
    private ListView listView;
    private GoogleMap mMap;
    private LocationManager _locationManager;
    private SupportMapFragment supportMapFragment;
    private LatLng gpsLocation;
    private double minLatitude = Double.NaN;
    private double maxLatitude = Double.NaN;
    private double minLongitude = Double.NaN;
    private double maxLongitude = Double.NaN;
    private String local_data="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.zoomNeeded = true;
        this.supportMapFragment = SupportMapFragment.newInstance();
        _locationManager = (LocationManager) getActivity().getSystemService( Context.LOCATION_SERVICE );

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, this.supportMapFragment);
        fragmentTransaction.commit();
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Add sample data to event list.
        supportMapFragment.getMapAsync(this);
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventDetails.class);
                intent.putExtra("event_id", Integer.parseInt(((TextView) view.findViewById(R.id.event_id)).getText().toString()));
                startActivity(intent);
            }
        });

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EventCreate.class));
            }
        });
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission((AppCompatActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (!_locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER )) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public void onResume() {
        this.zoomNeeded = true;
        super.onResume();
        Log.i(TAG, "onResume");
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getActivity().getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {
                gpsLocation = new LatLng(location.getLatitude(),
                        location.getLongitude());
                if (zoomNeeded) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gpsLocation, 12.0f));
                    zoomNeeded = false;
                }
            }
        });

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if (!zoomNeeded) {
                    LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
                    fetchData(bounds);
                }
            }
        });
    }

    private void fetchData(LatLngBounds bounds) {
        boolean newSearchRequired = false;
        double minLat = bounds.southwest.latitude;
        double maxLat = bounds.northeast.latitude;
        double minLng = bounds.southwest.longitude;
        double maxLng = bounds.northeast.longitude;

        if (Double.isNaN(this.minLatitude)) {
            this.minLatitude = bounds.southwest.latitude;
            this.maxLatitude = bounds.northeast.latitude;
            this.minLongitude = bounds.southwest.longitude;
            this.maxLongitude = bounds.northeast.longitude;
            newSearchRequired = true;
        } else {
            if (this.minLatitude > bounds.southwest.latitude) {
                minLat = bounds.southwest.latitude;
                maxLat = this.minLatitude;
                this.minLatitude = bounds.southwest.latitude;
                newSearchRequired = true;
            }
            if (this.maxLatitude < bounds.northeast.latitude) {
                minLat = this.maxLatitude;
                maxLat = bounds.northeast.latitude;
                this.maxLatitude = bounds.northeast.latitude;
                newSearchRequired = true;
            }
            if (this.minLongitude > bounds.southwest.longitude) {
                minLng = bounds.southwest.longitude;
                maxLng = this.minLongitude;
                this.minLongitude = bounds.southwest.longitude;
                newSearchRequired = true;
            }
            if (this.maxLongitude < bounds.northeast.longitude) {
                minLng = this.maxLongitude;
                maxLng = bounds.northeast.longitude;
                this.maxLongitude = bounds.northeast.longitude;
                newSearchRequired = true;
            }
        }

        // We only run the query if the user has moved to an undiscovered section of the map
        if (!newSearchRequired) {
            return;
        }

        BackgroundTask task = new BackgroundTask(getActivity());
        task.execute("get_local_events", Double.toString(minLat), Double.toString(maxLat),
                Double.toString(minLng), Double.toString(maxLng), "");

        try {
            String data = task.get();
            JSONArray arr = new JSONObject(data).getJSONArray("local_events");
            List<EventListItem> events = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject event = new JSONObject(arr.getString(i));
                EventListItem item = new EventListItem();
                item.setId(event.getInt("event_id"));
                item.setHeadline(event.getString("title"));
                item.setDescription(event.getString("location_name") + ", " + event.getString("address"));
                item.setLevel(event.getString("german_level"));
                item.setMaxAttendees(event.getInt("max_attendees"));
                item.setJoinedAttendees(event.getInt("attendees"));
                events.add(item);
                mMap.addMarker(new MarkerOptions().position(
                        new LatLng(event.getDouble("latitude"), event.getDouble("longitude"))));
            }

            listAdapter = new EventListAdapter(getActivity(), events.toArray(new EventListItem[0]));
            listView.setAdapter(listAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
