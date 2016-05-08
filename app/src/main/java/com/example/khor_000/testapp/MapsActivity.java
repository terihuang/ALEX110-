package com.example.khor_000.testapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<String> arrayList;
    private ListView listView;
    private List<LocItem> myLocations = new ArrayList<LocItem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        showList = (Button) findViewById(R.id.listButton);

        //arrayList = new ArrayList<String>();
        //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        //arrayAdapter = new ArrayAdapter<LocItem>( this, R.layout.single_location, myLocations);

        arrayAdapter = new MyLocAdapter();
        listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(arrayAdapter);

    }

    private class MyLocAdapter extends ArrayAdapter<LocItem>{
        public MyLocAdapter(){
            super(MapsActivity.this, R.layout.single_location, myLocations);
        }

        public void add(LocItem newLoc){
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
            latVal = Double.parseDouble(new DecimalFormat("##.##").format(latVal));
            laText.setText("LAT: " + latVal + "     ");

            // longtitude
            TextView lngText = (TextView) itemView.findViewById(R.id.longTxt);
            Double lngVal = currentLoc.getLongtitude();
            lngVal = Double.parseDouble(new DecimalFormat("##.##").format(lngVal));
            lngText.setText("  LON: " + lngVal);

            return itemView;
        }
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
        // Add a marker in Sydney and move the camera
        LatLng sanDiego = new LatLng( 32.7157, -117.1611);
        final Marker addFavor = mMap.addMarker(new MarkerOptions().position(sanDiego).title("where am I  "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sanDiego));
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
                addFavor.setPosition(point);
                setLocat = LayoutInflater.from(MapsActivity.this).inflate(R.layout.set_location,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setMessage("Assign A Name To This Location");
                final EditText favName =(EditText) setLocat.findViewById(R.id.nameOfLocation);

                builder.setView(setLocat).setPositiveButton("Add",new DialogInterface.OnClickListener(){

                   public void onClick(DialogInterface dialog, int which){
                       // set it to Data - base
                       locationName = favName.getText().toString();

                       if( !locationName.isEmpty() ) {

                           //save location with given info
                           LocItem tempLoc = new LocItem( locationName, point.latitude, point.longitude );

                           arrayAdapter.add(tempLoc);

                           Toast.makeText(getApplicationContext(), "Added To Favorite", Toast.LENGTH_SHORT).show();
                       }

                       else {
                           Toast.makeText(getApplicationContext(), "No Input Detected", Toast.LENGTH_SHORT).show();

                       }

                   }                    // close the prompt after click cancel
                }).setNegativeButton("Cancel",null).setCancelable(true);  // cancelable even using back key

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        LocationListener locationListener  = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

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

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED  ){

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, 100);

            Log.d("test1","ins");
            return;

        }else if (mMap != null){
            Log.d("test2", "outs");
            mMap.setMyLocationEnabled(true);
        }

        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);

    }
}
