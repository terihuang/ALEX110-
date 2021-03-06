package com.example.khor_000.testapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private View setLocat;
    private Button showList;
    private String locationName;
    private ArrayAdapter<LocItem> arrayAdapter;
    private ListView listView;
    private Marker addFavor;
    private List<LocItem> myLocations = new ArrayList<LocItem>();
    private List<Marker> locMakers = new ArrayList<Marker>();
    private String pastLocation = "";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        showList = (Button) findViewById(R.id.listButton);

        arrayAdapter = new MyLocAdapter();
        listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(arrayAdapter);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // call method to monitor click event on a location
        locationClickHandler();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.khor_000.testapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.khor_000.testapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class MyLocAdapter extends ArrayAdapter<LocItem> {
        public MyLocAdapter() {
            super(MapsActivity.this, R.layout.single_location, myLocations);
        }

        public void add(LocItem newLoc) {
            super.add(newLoc);
            super.notifyDataSetChanged();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // make sure hav view if given null
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.single_location, parent, false);
            }

            //find the location to work with
            LocItem currentLoc = myLocations.get(position);

            //fill the view
            // location name
            TextView makeText = (TextView) itemView.findViewById(R.id.locName);
            makeText.setText(currentLoc.getName());

            // latitude
            TextView laText = (TextView) itemView.findViewById(R.id.latTxt);
            Double latVal = currentLoc.getLatitude();
            latVal = Double.parseDouble(new DecimalFormat("##.###").format(latVal));
            laText.setText("LAT: " + latVal + "     ");

            // longtitude
            TextView lngText = (TextView) itemView.findViewById(R.id.longTxt);
            Double lngVal = currentLoc.getLongtitude();
            lngVal = Double.parseDouble(new DecimalFormat("##.###").format(lngVal));
            lngText.setText("  LON: " + lngVal);

            return itemView;
        }
    }

    private void locationClickHandler(){
        ListView list = (ListView) findViewById(R.id.lv);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                     int position, long id ) {

                if (!myLocations.isEmpty()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
                    builder1.setMessage("Confirm Delete " + myLocations.get(position).getName() + " ?");

                    final int index = position;

                    builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            String deleName = myLocations.get(index).getName();

                            myLocations.remove(index);

                            locMakers.get(index).remove();

                            locMakers.remove(index);

                            arrayAdapter.notifyDataSetChanged();

                            Toast.makeText(getApplicationContext(), "" + deleName + " Deleted", Toast.LENGTH_SHORT).show();

                        }                    // close the prompt after click cancel
                    }).setNegativeButton("No", null).setCancelable(true);  // cancelable even using back key

                    AlertDialog alert = builder1.create();
                    alert.show();

                }
            }

        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in San Diego and move the camera
        LatLng sanDiego = new LatLng(32.7157, -117.1611);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sanDiego));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sanDiego.latitude, sanDiego.longitude), 12.0f));
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listView.setVisibility(View.VISIBLE);
                showList.setVisibility(View.GONE);
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng point) {

                listView.setVisibility(View.GONE);
                showList.setVisibility(View.VISIBLE);
                //  show the alert box
                addFavor = mMap.addMarker(new MarkerOptions().position(point).title(" Selceted "));
                //addFavor.setPosition(point);
                setLocat = LayoutInflater.from(MapsActivity.this).inflate(R.layout.set_location, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setMessage("Assign A Name To This Location");
                final EditText favName = (EditText) setLocat.findViewById(R.id.nameOfLocation);

                builder.setView(setLocat).setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // set it to Data - base
                        locationName = favName.getText().toString();

                        if (!locationName.isEmpty()) {

                            //save location with given info
                            LocItem tempLoc = new LocItem(locationName, point.latitude, point.longitude);
                            arrayAdapter.add(tempLoc);

                            addFavor.remove();

                            // add marker on map
                            Marker temp = mMap.addMarker(new MarkerOptions().position(point).title(locationName));

                            locMakers.add(temp);

                            Toast.makeText(getApplicationContext(), "Added To Favorite", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Toast.makeText(getApplicationContext(), "No Input Detected", Toast.LENGTH_SHORT).show();
                            addFavor.remove();
                        }

                    }                    // close the prompt after click cancel
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                        addFavor.remove();

                    }                    // close the prompt after click cancel
                }).setCancelable(true);  // cancelable even using back key

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                //check if location is in the range of any of the favorite locations
                for (LocItem i : myLocations) {
                    if (getRange(location.getLatitude(), location.getLongitude(), i.getLatitude(), i.getLongtitude()) < 160.9) {

                        // only send msg if visit different fav location
                        if ( pastLocation != i.getName()) {
                            Toast.makeText(getApplicationContext(), "Vist: " + i.getName(), Toast.LENGTH_SHORT).show();
                             pastLocation = i.getName();
                        }
                    }
                }

                mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()) , 16.0f) );

            }

            public double getRange(double lat1, double lon1, double lat2, double lon2) {
                Double R = 6378.137; // Radius of earth in KM
                Double dLat = (lat2 - lat1) * Math.PI / 180;
                Double dLon = (lon2 - lon1) * Math.PI / 180;

                Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);

                Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                Double d = R * c;
                d = d * 1000; // meters
                return d;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);

            Log.d("test1", "ins");
            return;

        } else if (mMap != null) {
            Log.d("test2", "outs");
            mMap.setMyLocationEnabled(true);
        }

        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);

    }
}
