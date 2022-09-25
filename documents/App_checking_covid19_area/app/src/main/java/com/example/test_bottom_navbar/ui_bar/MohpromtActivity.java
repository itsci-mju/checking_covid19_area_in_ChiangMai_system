package com.example.test_bottom_navbar.ui_bar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.test_bottom_navbar.Cluster;
import com.example.test_bottom_navbar.Mohpromt;
import com.example.test_bottom_navbar.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MohpromtActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap,user_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mohpromt);

        SupportMapFragment googleMap =(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_main_not);
        googleMap.getMapAsync(MohpromtActivity.this);

        BottomNavigationView bottomNav = findViewById(R.id.nav_host_fragment_activity_bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }

    //instance firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("admin001/mohpromt");
    Query query1 = myRef.orderByKey();


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        user_location = googleMap;

        //user location
        LatLng user_location = new LatLng(18.7858623, 98.9764537);
        mMap.addMarker(new MarkerOptions()
                .position(user_location)
                .title("Here !!!!")
                .snippet("Mohpromtstation!!!")
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_baseline_person_pin_circle_24))

        );

        mMap.moveCamera(CameraUpdateFactory.newLatLng(user_location));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()){
                    Mohpromt mohpromt = s.getValue(Mohpromt.class);
                    LatLng location = new LatLng(Double.parseDouble(mohpromt.getMohpromtLat()),Double.parseDouble(mohpromt.getMohpromtLng()));
                    mMap.addMarker(new MarkerOptions().position(location)
                            .title(mohpromt.getMohpromtPlace())
                            .snippet(mohpromt.getMohpromtStartTime() + "ถึง " +
                                    mohpromt.getMohpromtEndTime()
                                    )
                            .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_baseline_hospital_location_24))
                    );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new NotificationsFragment();
                            break;
                        case R.id.nav_setting:
                            selectedFragment = new SettingFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}