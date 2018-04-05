package e.manal.truckstationthenwev;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by manal on 3/5/2018.
 */

public class VsitorAllTrucks  extends AppCompatActivity implements OnMapReadyCallback  , NavigationView.OnNavigationItemSelectedListener  {
        private FirebaseDatabase db = FirebaseDatabase.getInstance();
        private DatabaseReference dbRef = db.getReference();
        double x = 0;
        double y = 0;
        FirebaseAuth firebaseAuth ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.all_trucks_map_visitor_drawer);
                android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(this);
                firebaseAuth = FirebaseAuth.getInstance();
                getLocationPermission();
                dbRef.child("PublicFoodTruckOwner").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                        PublicFoodTruckOwner pOwner;
                                        pOwner = postSnapshot.getValue(PublicFoodTruckOwner.class);
                                        if (pOwner == null)
                                                Toast.makeText(VsitorAllTrucks.this, "فاضي !!!!!!!!", Toast.LENGTH_SHORT).show();
                                        try {
                                                if (pOwner != null) {
                                                        x = pOwner.getXFLication();
                                                        y = pOwner.getYFLocation();
                                                        //   Toast.makeText(VisitorHomePage.this, "يتم تحديد المواقع الان....", Toast.LENGTH_SHORT).show();
                                                        // mMap.clear();
                                                        LatLng location = new LatLng(x, y);
                                                        mMap.addMarker(new MarkerOptions()
                                                                .position(location)
                                                                .title(pOwner.getFUsername()));
                                                        //.icon(BitmapDescriptorFactory.fromBitmap(bmp))
                                                }
                                        } catch (Exception e) {
                                                Toast.makeText(VsitorAllTrucks.this, "حدث خطاء ما !!", Toast.LENGTH_SHORT).show();
                                        }

                                }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                });
            }
        @Override
        public void onMapReady(GoogleMap googleMap) {
                Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onMapReady: map is ready");
                mMap = googleMap;

                if (mLocationPermissionsGranted) {
                        getDeviceLocation();

                        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                        }
                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);

                }
        }

        private static final String TAG = "MapActivity";

        private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
        private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
        private static final float DEFAULT_ZOOM = 15f;

        //vars
        private Boolean mLocationPermissionsGranted = false;
        private GoogleMap mMap;
        private FusedLocationProviderClient mFusedLocationProviderClient;


        private void getDeviceLocation(){
                Log.d(TAG, "getDeviceLocation: getting the devices current location");

                mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

                try{
                        if(mLocationPermissionsGranted){

                                mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                                // Got last known location. In some rare situations this can be null.
                                                if (location != null) {
                                                        // Logic to handle location object
                                                        moveCamera(new LatLng(location.getLatitude(), location.getLongitude()),
                                                                DEFAULT_ZOOM);
                                                }
                                                else {  moveCamera(new LatLng(24.723129, 46.636892),
                                                        DEFAULT_ZOOM);}
                                        }
                                });
                        }
                }catch (SecurityException e){
                        Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
                }
        }

        private void moveCamera(LatLng latLng, float zoom){
                Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }

        private void initMap(){
                Log.d(TAG, "initMap: initializing map");
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

                mapFragment.getMapAsync(VsitorAllTrucks.this);
        }

        private void getLocationPermission(){
                Log.d(TAG, "getLocationPermission: getting location permissions");
                String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION};

                if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                                COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                                mLocationPermissionsGranted = true;
                                initMap();
                        }else{
                                ActivityCompat.requestPermissions(this,
                                        permissions,
                                        LOCATION_PERMISSION_REQUEST_CODE);
                        }
                }else{
                        ActivityCompat.requestPermissions(this,
                                permissions,
                                LOCATION_PERMISSION_REQUEST_CODE);
                }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                Log.d(TAG, "onRequestPermissionsResult: called.");
                mLocationPermissionsGranted = false;

                switch(requestCode){
                        case LOCATION_PERMISSION_REQUEST_CODE:{
                                if(grantResults.length > 0){
                                        for(int i = 0; i < grantResults.length; i++){
                                                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                                                        mLocationPermissionsGranted = false;
                                                        Log.d(TAG, "onRequestPermissionsResult: permission failed");
                                                        return;
                                                }
                                        }
                                        Log.d(TAG, "onRequestPermissionsResult: permission granted");
                                        mLocationPermissionsGranted = true;
                                        //initialize our map
                                        initMap();
                                }
                        }
                }
        }
        @Override
        public void onBackPressed() {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                } else {
                        super.onBackPressed();
                }
        }

        @Override
        public boolean onCreateOptionsMenu(android.view.Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.main, menu);
                return true;
        }


        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_publiclist1) {
                        Intent intent = new Intent(this, publicListVistor.class);
                        Bundle b=new Bundle();
                        // b.putString("id",user);
                        // intent.putExtras(b);
                        startActivity(intent);
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                }
                else if (id == R.id.nav_privatelist1) {
                        Intent intent = new Intent(this, PravitListVistor.class);
                        Bundle b=new Bundle();
                        // b.putString("id",user);
                        // intent.putExtras(b);
                        startActivity(intent);
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                }
                else if (id == R.id.nav_map1) {
                        Intent intent = new Intent(this, VsitorAllTrucks.class);
                        Bundle b=new Bundle();
                        //  b.putString("id",user);
                        // intent.putExtras(b);
                        startActivity(intent);
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                }

                else if (id == R.id.nav_nearmap1) {
                        Intent intent = new Intent(this, NearByTrucksVisitors.class);
                        Bundle b=new Bundle();
                        //  b.putString("id",user);
                        // intent.putExtras(b);
                        startActivity(intent);
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                }


                else if (id == R.id.nav_app1) {

                        Intent intent = new Intent(this, ChartVisitor.class);
                        Bundle b=new Bundle();
                        //  b.putString("id",user);
                        //  intent.putExtras(b);
                        startActivity(intent);
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                }
                else if (id == R.id.home) {

                        Intent intent = new Intent(this, MainActivity.class);
                        Bundle b=new Bundle();
                        //  b.putString("id",user);
                        //  intent.putExtras(b);
                        startActivity(intent);
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;

                }


                return false;
        }

}