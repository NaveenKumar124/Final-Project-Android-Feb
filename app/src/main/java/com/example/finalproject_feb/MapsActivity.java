package com.example.finalproject_feb;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;

    NoteDatabase db;
    Note note;


    String cloc;


    public String finalLoc;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                fusedLocationProviderClient.requestLocationUpdates(locationRequest , locationCallback , Looper.myLooper());
            }else {
                ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 1);
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        Intent i = getIntent();
//        final Long id = i.getLongExtra("ID",0 );
//
//        db = new NoteDatabase(this);

        //note = db.getNote(id);

//        cloc  = note.getLoc();
//
//        String[] latlng = cloc.split(",");
//        double latitude = Double.parseDouble(latlng[0]);
//        double longitude = Double.parseDouble(latlng[1]);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = new LocationRequest();
        locationRequest.setSmallestDisplacement(20);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(3000);
        getLocation();

    }

    private void getLocation() {

       locationCallback = new LocationCallback(){
           @Override
           public void onLocationResult(LocationResult locationResult) {
               for (Location location: locationResult.getLocations()){

                   setHomeMarker(location);

               }


           }
       };


    }


    private void setHomeMarker(Location location){

        LatLng userLocation = new LatLng(location.getLatitude() , location.getLongitude());

        MarkerOptions options = new MarkerOptions()
                .position(userLocation)
                .title("Your Location");

        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation , 14));

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.requestLocationUpdates(locationRequest , locationCallback , Looper.myLooper());
        }else{
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 1);
        }




    }


}
