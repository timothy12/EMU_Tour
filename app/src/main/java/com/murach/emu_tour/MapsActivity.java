package com.murach.emu_tour;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
//import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnClickListener {

    private GoogleMap map;
    public static final int UPDATE_INTERVAL = 5000;         // 5 seconds
    public static final int FASTEST_UPDATE_INTERVAL = 2000; // 2 seconds

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    private Button infoButton;
    private Button go;
    private Intent infoIntent;
    private boolean GPSNeeded = false;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        infoButton = (Button) findViewById(R.id.button2);
        go = (Button) findViewById(R.id.button3);
        infoButton.setOnClickListener(this);
        infoIntent = new Intent(getApplicationContext(),
                information.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);



    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.button2:
                startActivity(infoIntent);
            case R.id.button3:
                timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        MapsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {updateMap();
                            }
                        });
                    }
                };
                timer.schedule(task, UPDATE_INTERVAL, UPDATE_INTERVAL );
        }
    }

    private void updateMap()
    {
        if (googleApiClient.isConnected())
        {
            setCurrentLocationMarker();
        }
    }


    private void setCurrentLocationMarker(){
        if (map != null && GPSNeeded) {
            // get current location
            try {
                Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);


                if (location != null) {
                    // zoom in on current location
                    map.moveCamera(
                            CameraUpdateFactory.newCameraPosition(
                                    new CameraPosition.Builder()
                                            .target(new LatLng(location.getLatitude(),
                                                    location.getLongitude()))
                                            .zoom(18.0f)
                                            .bearing(0)
                                            .tilt(0)
                                            .build()));

                    // add a marker for the current location
                    map.clear();      // clear old marker(s)
                    map.addMarker(    // add new marker
                            new MarkerOptions()
                                    .position(new LatLng(location.getLatitude(),
                                            location.getLongitude()))
                                    .title("You are here"));
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    double minDist = .0001;
                    if(lat - commonslat <= minDist && lng - commonslong <= minDist)
                    {
                        Toast.makeText(this, "You have arrived at The Commons", Toast.LENGTH_SHORT).show();
                    }
                    if(lat - hilllat <= minDist && lng - hilllong <= minDist)
                    {
                        Toast.makeText(this, "You have arrived at The Hill", Toast.LENGTH_SHORT).show();
                    }
                    if(lat - northlawnlat <= minDist && lng - northlawnlong <= minDist)
                    {
                        Toast.makeText(this, "You have arrived at Northlawn", Toast.LENGTH_SHORT).show();
                    }
                    if(lat - librarylat <= minDist && lng - librarylong <= minDist)
                    {
                        Toast.makeText(this, "You have arrived at The Library", Toast.LENGTH_SHORT).show();
                    }
                    if(lat - campuscenterlat <= minDist && lng - campuscenterlong <= minDist)
                    {
                        Toast.makeText(this, "You have arrived at The Campus Center", Toast.LENGTH_SHORT).show();
                    }
                    if(lat - sciencecenterlat <= minDist && lng - sciencecenterlong <= minDist)
                    {
                        Toast.makeText(this, "You have arrived at The Science Center", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch(SecurityException e)
            {
                Toast.makeText(this, "Please check your permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }
/**
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_resources, menu);
        return true;
    }
 **/

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_sitting:
                GPSNeeded = false;
                return true;
            case R.id.menu_walking:
                GPSNeeded = true;
                return true;
            default:
                return true;
        }
    }

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        double commonslat = 38.4719;
        double commonslong = -78.8793;
        double hilllat = 38.471430;
        double hilllong = -78.882421;
        double northlawnlat = 38.471699;
        double northlawnlong = -78.879716;
        double librarylat = 38.470401;
        double librarylong = -78.878976;
        double campuscenterlat = 38.471082;
        double campuscenterlong = -78.880052;
        double sciencecenterlat = 38.470038;
        double sciencecenterlong = -78.878207;
        LatLng commons = new LatLng(commonslat, commonslong);
        LatLng hill = new LatLng(hilllat, hilllong);
        LatLng northlawn = new LatLng(northlawnlat, northlawnlong);
        LatLng library = new LatLng(librarylat, librarylong);
        LatLng campusCenter = new LatLng(campuscenterlat, campuscenterlong);
        LatLng scienceCenter = new LatLng(sciencecenterlat, sciencecenterlong);
        @Override
        public void onMapReady (GoogleMap googleMap){
            map = googleMap;
            //If GPS is not connected, will ask the user to connect to GPS
            //borrowed from Murach
            LocationManager locationManager =
                    (LocationManager) getSystemService(LOCATION_SERVICE);
            if (GPSNeeded == true && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this, "Please enable GPS!",
                        Toast.LENGTH_SHORT).show();
                Intent intent =
                        new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }

            // Add a marker at EMU and move the camera

            map.addMarker(new MarkerOptions().position(commons).title("Commons"));
            map.moveCamera(CameraUpdateFactory.newLatLng(commons));

            map.addMarker(new MarkerOptions().position(hill).title("The Hill"));
            map.moveCamera(CameraUpdateFactory.newLatLng(hill));

            map.addMarker(new MarkerOptions().position(northlawn).title("Northlawn"));
            map.moveCamera(CameraUpdateFactory.newLatLng(northlawn));

            map.addMarker(new MarkerOptions().position(library).title("Library"));
            map.moveCamera(CameraUpdateFactory.newLatLng(library));

            map.addMarker(new MarkerOptions().position(campusCenter).title("Campus Center"));
            map.moveCamera(CameraUpdateFactory.newLatLng(campusCenter));

            map.addMarker(new MarkerOptions().position(scienceCenter).title("Science Center"));
            map.moveCamera(CameraUpdateFactory.newLatLng(scienceCenter));
        }

    //borrowed from Murach
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        Toast.makeText(this, "Connection failed! " +
                        "Please check your settings and try again.",
                Toast.LENGTH_SHORT).show();
    }
}
