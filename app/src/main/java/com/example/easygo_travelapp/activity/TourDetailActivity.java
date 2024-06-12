package com.example.easygo_travelapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.adapter.TourDetailAdapter;
import com.example.easygo_travelapp.customView.CTToolbar;
import com.example.easygo_travelapp.model.DetailScenic;
import com.example.easygo_travelapp.model.City;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TourDetailActivity extends BaseActivity implements OnMapReadyCallback {
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private CTToolbar toolbar;
    private GoogleMap mMap;
    private RecyclerView rvTourDetail;
    private ImageView imgTour;
    private TextView tvNameTour, location, timeTour;
    private ImageView star1, star2, star3, star4, star5;
    private List<City> cities;
    PlacesClient placesClient;
    FusedLocationProviderClient fusedLocationProviderClient;
    boolean locationPermissionGranted;
    Location lastKnownLocation;
    Location cameraPosition;
    DetailScenic scenic;

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        rvTourDetail = findViewById(R.id.rvTourDetail);
        imgTour = findViewById(R.id.imgTour);
        tvNameTour = findViewById(R.id.tvNameTour);
        location = findViewById(R.id.tvLocation);
        timeTour = findViewById(R.id.tvTimeTour);
        star1 = findViewById(R.id.imgStar1);
        star2 = findViewById(R.id.imgStar2);
        star3 = findViewById(R.id.imgStar3);
        star4 = findViewById(R.id.imgStar4);
        star5 = findViewById(R.id.imgStar5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);
        init();
        Bundle bundle = getIntent().getExtras();
        scenic = (DetailScenic) bundle.getSerializable(BaseActivity.GET_TOUR);
        cities = scenic.getCities();
        toolbar.setVisibleBack();
        toolbar.setTitleToolbar(scenic.getLocation());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fmMaps);
        mapFragment.getMapAsync(this);
        setItemCurrentTour(scenic);
        setListTourDetail(cities);

        // Construct a PlacesClient
        Places.initialize(getApplicationContext(), getString(R.string.google_api_key));
        placesClient = Places.createClient(this);

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void setListTourDetail(List<City> tourDetails) {
        TourDetailAdapter adapter = new TourDetailAdapter(this, tourDetails);
        rvTourDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvTourDetail.setAdapter(adapter);
    }

    private void setItemCurrentTour(DetailScenic tour) {
        Picasso.get().load(tour.getImageScenic()).into(imgTour);
        tvNameTour.setText(tour.getNameScenic());
        location.setText(tour.getLocation());
        timeTour.setText(tour.getTimeTour() + getString(R.string.days));
        BaseActivity.setRatingStar(this, tour.getRating(), star1, star2, star3, star4, star5);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), 13));
                            }
                        }
                    }
                });
            }
        } catch (SecurityException e) {
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Add a marker in Sydney and move the camera
        for (City item : cities) {
            LatLng sydney = new LatLng(item.getLatitude(), item.getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
        }
        updateLocationUI();
        getDeviceLocation();
    }
}