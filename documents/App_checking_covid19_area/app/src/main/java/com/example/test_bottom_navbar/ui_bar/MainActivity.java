package com.example.test_bottom_navbar.ui_bar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test_bottom_navbar.Cluster;
import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.admin.AddClusterActivity;
import com.example.test_bottom_navbar.admin.ListRiskAreaActivity;
import com.example.test_bottom_navbar.admin.Mainpage_admin;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback{

    private ImageView imageView;
    private Button button;
    String location;
    private GoogleMap mMap,user_location;
    Cluster cluster = new Cluster();
    //private ActivityMapsBinding binding;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    String clusterdistrict;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/cluster");
        Query query1 = myRef.orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()){
                    Cluster cluster = s.getValue(Cluster.class);
                    cluster.getClusterDistrict();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageView = (ImageView) findViewById(R.id.Button_search);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        SupportMapFragment googleMap =(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_main);
        googleMap.getMapAsync(this);

        BottomNavigationView bottomNav = findViewById(R.id.nav_host_fragment_activity_bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }

    //instance firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("admin001/cluster");
    Query query1 = myRef.orderByKey();

    /*FirebaseDatabase database1 = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef1 = database1.getReference("admin001/cluster/เมืองเชียงใหม่");
    Query query2 = myRef1.orderByKey();*/

    public void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("ค้นหาคลัสเตอร์");
        builder.setView(R.layout.activity_list_cluster_user);

       /* LinearLayout list_cluster = findViewById(R.id.showlistcluster_user);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001");
        Query query1 = myRef.orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Log.e("Data_cluster", ds.getValue().toString());
                    View cluster = getLayoutInflater().inflate(R.layout.layout_cluster, null);

                    String clusterDate = ds.child("clusterDate").getValue().toString();
                    String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                    String clusterPlace = ds.child("clusterPlace").getValue().toString();
                    String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                    String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();

                    System.out.println(clusterDate + clusterDistrict);

                    TextView txtplace = cluster.findViewById(R.id.txtedit_place);
                    txtplace.setText(clusterPlace);

                    TextView txtsubdistrict = cluster.findViewById(R.id.txtedit_subdistrict);
                    txtsubdistrict.setText(clusterSubdistrict);

                    TextView txtdistrict = cluster.findViewById(R.id.txtedit_district);
                    txtdistrict.setText(clusterDistrict);

                    TextView txtdate = cluster.findViewById(R.id.txtedit_date);
                    txtdate.setText(clusterDate);

                    TextView txtnewpatient = cluster.findViewById(R.id.txtedit_newpatient);
                    txtnewpatient.setText(cluster_news_patient);

                    list_cluster.addView(cluster);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        user_location = googleMap;

        //user location
        LatLng user_location = new LatLng(18.7858623,98.9764537);
        mMap.addMarker(new MarkerOptions()
                .position(user_location)
                .title("I'm here !!!!")
                .snippet("Hello my what my name ?")
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_baseline_person_pin_circle_24))
        );

        mMap.moveCamera(CameraUpdateFactory.newLatLng(user_location));
        // Move the camera
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        //Zoom setUp
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );
        //Zoom button
        mMap.getUiSettings().setZoomControlsEnabled(true);

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()){
                    Cluster cluster = s.getValue(Cluster.class);
                    LatLng location = new LatLng(Double.parseDouble(cluster.getClusterLat()),Double.parseDouble(cluster.getClusterLng()));
                    mMap.addMarker(new MarkerOptions().position(location)
                            .title(cluster.getClusterPlace())
                            .snippet(cluster.getClusterDate() + " อ." +
                                    cluster.getClusterDistrict() + " ต." +
                                    cluster.getClusterSubdistrict() + " ยอด: "+
                                    cluster.getCluster_news_patient())
                            .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_baseline_warning_24))
                    );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

  /*      query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()){
                    Cluster cluster = s.getValue(Cluster.class);
                    LatLng location = new LatLng(Double.parseDouble(cluster.getClusterLat()),Double.parseDouble(cluster.getClusterLng()));
                    mMap.addMarker(new MarkerOptions().position(location)
                            .title(cluster.getClusterPlace())
                            .snippet(cluster.getClusterDate() + " อ." +
                                    cluster.getClusterDistrict() + " ต." +
                                    cluster.getClusterSubdistrict() + " ยอด: "+
                                    cluster.getCluster_news_patient())
                            .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_baseline_warning_24))
                    );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

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

    public void ClickbynBack(View view){
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
    }


}
