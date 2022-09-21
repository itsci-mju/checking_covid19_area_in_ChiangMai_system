package com.example.test_bottom_navbar.ui_bar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_bottom_navbar.Cluster;
import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.admin.AddClusterActivity;
import com.example.test_bottom_navbar.admin.ListRiskAreaActivity;
import com.example.test_bottom_navbar.admin.Mainpage_admin;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback{

    private ImageView imageView;
    private EditText SarchButton;
    private Button button;
    Intent intent = getIntent();
    private Dialog dialog;
    String location,clusterPlace,sarchbutton;
    String LatLng,string_clusterlat,string_clusterlng;
    Double sarchLat,sarchLng;
    private GoogleMap mMap,user_location,sarch_location;
    Cluster cluster = new Cluster();
    private Context context;
    private String[] District= {"เมืองเชียงใหม่","สารภี","เเม่ริม","สันกำเเพง","สันทราย"};
    Circle myCircle1;
    Circle myCircle2;
    Circle myCircle3;
    Circle myCircle4;
    Circle myCircle5;
    Circle myCircle6;

    int Allpatient_District,Totalpatient_CM = 5,Totalpatient_Sarapee = 30,Totalpatient_MaeRim = 10,Totalpatient_SunSai = 60;
    private int STORAGE_PERMISSION_CODE = 1;
    String statesobj[] = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Totalpatient_CM = intent.getIntExtra("Totalpatient_CM",Totalpatient_CM);
        Totalpatient_Sarapee = intent.getIntExtra("Totalpatient_Sarapee",Totalpatient_Sarapee);
        Totalpatient_MaeRim = intent.getIntExtra("Totalpatient_MaeRim",Totalpatient_MaeRim);
        Totalpatient_SunSai = intent.getIntExtra("Totalpatient_SunSai",Totalpatient_SunSai);

        System.out.println("////////////////////////////////////////////CM"+Totalpatient_CM);
        System.out.println("////////////////////////////////////////////SP"+Totalpatient_Sarapee);
        System.out.println("////////////////////////////////////////////MR"+Totalpatient_MaeRim);
        System.out.println("////////////////////////////////////////////SS"+Totalpatient_SunSai);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "ระบบได้เข้าถึงตำเเหน่งบนอุปกรณ์ของคุณแล้ว",
                    Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        FirebaseApp.initializeApp(this);

        ///Search cluster
       /* FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/cluster/");
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
        });*/

        SupportMapFragment googleMap =(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_main);
        googleMap.getMapAsync(this);
        BottomNavigationView bottomNav = findViewById(R.id.nav_host_fragment_activity_bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("ระบบ")
                    .setMessage("อนุญาตให้ระบบเข้าถึงตำเเหน่งที่ตั้งในอุปกรณ์ของคุณหรือไม่")
                    .setPositiveButton("อนุญาต", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("ปฎิเสธ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }

    }

    public void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("ค้นหาคลัสเตอร์");
        builder.setView(R.layout.activity_list_cluster_user);

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
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                //.icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.user_64)

        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(user_location));
        // Move the camera
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        //Zoom setUp
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );
        //Zoom button
        mMap.getUiSettings().setZoomControlsEnabled(true);
        addingCircleView_user(user_location);

        //instance firebase
        for (int i=0;i < District.length;i++) {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/cluster/"+District[i]);
            Query query1 = myRef.orderByValue();
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        Cluster cluster = s.getValue(Cluster.class);
                        LatLng location = new LatLng(Double.parseDouble(cluster.getClusterLat()), Double.parseDouble(cluster.getClusterLng()));
                        mMap.addMarker(new MarkerOptions().position(location)
                                        .title(cluster.getClusterPlace())
                                        .snippet(cluster.getClusterDate() + " อ." +
                                                cluster.getClusterDistrict() + " ต." +
                                                cluster.getClusterSubdistrict() + " ยอด: " +
                                                cluster.getCluster_news_patient())
                        );
                        addingCircleView(location);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        /////////////////////////////////////////Polygon/////////////////////////////////////////

        ////////////////////เมืองเชียงใหม่////////////////////
        if(Totalpatient_CM <= 10){
            PolygonOptions MaungCM = new PolygonOptions()
                    .add(m1).add(m2).add(m3).add(m4).add(m5).add(m6).add(m7).add(m8).add(m9).add(m10)
                    .add(m11).add(m12).add(m13).add(m14).add(m15).add(m16).add(m17).add(m18).add(m19).add(m20)
                    .add(m21).add(m22).add(m23).add(m24).add(m25).add(m26).add(m27).add(m28).add(m29).add(m30)
                    .add(m31).add(m32).add(m33).add(m34).add(m35)
                    .strokeColor(Color.argb(250, 0, 255, 0))
                    .fillColor(Color.argb(30, 0, 255, 0));
            Polygon polygonMaungCM = mMap.addPolygon(MaungCM);
            polygonMaungCM.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    //createDialog1();
                }
            });
        }else if(Totalpatient_CM <= 30) {
            PolygonOptions MaungCM = new PolygonOptions()
                    .add(m1).add(m2).add(m3).add(m4).add(m5).add(m6).add(m7).add(m8).add(m9).add(m10)
                    .add(m11).add(m12).add(m13).add(m14).add(m15).add(m16).add(m17).add(m18).add(m19).add(m20)
                    .add(m21).add(m22).add(m23).add(m24).add(m25).add(m26).add(m27).add(m28).add(m29).add(m30)
                    .add(m31).add(m32).add(m33).add(m34).add(m35)
                    .strokeColor(Color.argb(250, 255, 255, 0))
                    .fillColor(Color.argb(30, 255, 255, 0));
            Polygon polygonMaungCM = mMap.addPolygon(MaungCM);
            polygonMaungCM.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    //createDialog1();
                }
            });
        }else if(Totalpatient_CM >= 50) {
            PolygonOptions MaungCM = new PolygonOptions()
                    .add(m1).add(m2).add(m3).add(m4).add(m5).add(m6).add(m7).add(m8).add(m9).add(m10)
                    .add(m11).add(m12).add(m13).add(m14).add(m15).add(m16).add(m17).add(m18).add(m19).add(m20)
                    .add(m21).add(m22).add(m23).add(m24).add(m25).add(m26).add(m27).add(m28).add(m29).add(m30)
                    .add(m31).add(m32).add(m33).add(m34).add(m35)
                    .strokeColor(Color.argb(250, 255, 0, 0))
                    .fillColor(Color.argb(30, 255, 0, 0));
            Polygon polygonMaungCM = mMap.addPolygon(MaungCM);
            polygonMaungCM.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    //createDialog1();
                }
            });
        }
        ////////////////////End เมืองเชียงใหม่////////////////////

        ////////////////////เเม่ริม////////////////////
        if(Totalpatient_MaeRim <= 10) {
            PolygonOptions MaeRim = new PolygonOptions()
                    .add(ma1).add(ma2).add(ma3).add(ma4).add(ma5).add(ma6)
                    .add(ma7).add(ma8).add(ma9).add(ma10).add(ma11).add(ma12)
                    .add(ma13).add(ma14).add(ma15).add(ma16).add(ma17).add(ma18)
                    .add(ma19).add(ma20).add(ma21).add(ma22).add(ma23).add(ma24)
                    .add(ma25).add(ma26).add(ma27).add(ma28).add(ma29).add(ma30)
                    .add(ma31).add(ma32)
                    .strokeColor(Color.argb(250, 0, 255, 0))
                    .fillColor(Color.argb(30, 0, 255, 0));
            Polygon polygonMaeRim = mMap.addPolygon(MaeRim);
            polygonMaeRim.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    createDialog2();
                }
            });
        }else if(Totalpatient_MaeRim <= 30) {
            PolygonOptions MaeRim = new PolygonOptions()
                    .add(ma1).add(ma2).add(ma3).add(ma4).add(ma5).add(ma6)
                    .add(ma7).add(ma8).add(ma9).add(ma10).add(ma11).add(ma12)
                    .add(ma13).add(ma14).add(ma15).add(ma16).add(ma17).add(ma18)
                    .add(ma19).add(ma20).add(ma21).add(ma22).add(ma23).add(ma24)
                    .add(ma25).add(ma26).add(ma27).add(ma28).add(ma29).add(ma30)
                    .add(ma31).add(ma32)
                    .strokeColor(Color.argb(250, 255, 255, 0))
                    .fillColor(Color.argb(30, 255, 255, 0));
            Polygon polygonMaeRim = mMap.addPolygon(MaeRim);
            polygonMaeRim.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    createDialog2();
                }
            });
        }else if(Totalpatient_MaeRim >= 50) {
            PolygonOptions MaeRim = new PolygonOptions()
                    .add(ma1).add(ma2).add(ma3).add(ma4).add(ma5).add(ma6)
                    .add(ma7).add(ma8).add(ma9).add(ma10).add(ma11).add(ma12)
                    .add(ma13).add(ma14).add(ma15).add(ma16).add(ma17).add(ma18)
                    .add(ma19).add(ma20).add(ma21).add(ma22).add(ma23).add(ma24)
                    .add(ma25).add(ma26).add(ma27).add(ma28).add(ma29).add(ma30)
                    .add(ma31).add(ma32)
                    .strokeColor(Color.argb(250, 255, 0, 0))
                    .fillColor(Color.argb(30, 255, 0, 0));
            Polygon polygonMaeRim = mMap.addPolygon(MaeRim);
            polygonMaeRim.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    createDialog2();
                }
            });
        }
        ////////////////////End เเม่ริม////////////////////

        ////////////////////สารภี////////////////////
        if(Totalpatient_Sarapee <= 10) {
            PolygonOptions Sarapee = new PolygonOptions()
                    .add(sarapee1).add(sarapee2).add(sarapee3).add(sarapee4).add(sarapee5).add(sarapee6)
                    .add(sarapee7).add(sarapee8).add(sarapee9).add(sarapee10).add(sarapee11).add(sarapee12)
                    .add(sarapee13).add(sarapee14).add(sarapee15).add(sarapee16).add(sarapee17).add(sarapee18)
                    .add(sarapee19).add(sarapee20).add(sarapee21).add(sarapee22).add(sarapee23).add(sarapee24)
                    .add(sarapee25).add(sarapee26).add(sarapee27).add(sarapee28).add(sarapee29).add(sarapee30)
                    .add(sarapee31).add(sarapee32)
                    .strokeColor(Color.argb(250, 0, 255, 0))
                    .fillColor(Color.argb(30, 0, 255, 0));
            Polygon polygonSarapee = mMap.addPolygon(Sarapee);
            polygonSarapee.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    createDialog2();
                }
            });
        }else if(Totalpatient_Sarapee <= 30){
            PolygonOptions Sarapee = new PolygonOptions()
                    .add(sarapee1).add(sarapee2).add(sarapee3).add(sarapee4).add(sarapee5).add(sarapee6)
                    .add(sarapee7).add(sarapee8).add(sarapee9).add(sarapee10).add(sarapee11).add(sarapee12)
                    .add(sarapee13).add(sarapee14).add(sarapee15).add(sarapee16).add(sarapee17).add(sarapee18)
                    .add(sarapee19).add(sarapee20).add(sarapee21).add(sarapee22).add(sarapee23).add(sarapee24)
                    .add(sarapee25).add(sarapee26).add(sarapee27).add(sarapee28).add(sarapee29).add(sarapee30)
                    .add(sarapee31).add(sarapee32)
                    .strokeColor(Color.argb(250, 255, 255, 0))
                    .fillColor(Color.argb(30, 255, 255, 0));
            Polygon polygonSarapee = mMap.addPolygon(Sarapee);
            polygonSarapee.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    createDialog2();
                }
            });
        }else if(Totalpatient_Sarapee >= 50){
            PolygonOptions Sarapee = new PolygonOptions()
                    .add(sarapee1).add(sarapee2).add(sarapee3).add(sarapee4).add(sarapee5).add(sarapee6)
                    .add(sarapee7).add(sarapee8).add(sarapee9).add(sarapee10).add(sarapee11).add(sarapee12)
                    .add(sarapee13).add(sarapee14).add(sarapee15).add(sarapee16).add(sarapee17).add(sarapee18)
                    .add(sarapee19).add(sarapee20).add(sarapee21).add(sarapee22).add(sarapee23).add(sarapee24)
                    .add(sarapee25).add(sarapee26).add(sarapee27).add(sarapee28).add(sarapee29).add(sarapee30)
                    .add(sarapee31).add(sarapee32)
                    .strokeColor(Color.argb(250, 255, 0, 0))
                    .fillColor(Color.argb(30, 255, 0, 0));
            Polygon polygonSarapee = mMap.addPolygon(Sarapee);
            polygonSarapee.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    createDialog2();
                }
            });
        }
        ////////////////////End สารภี////////////////////

        ////////////////////สันทราย////////////////////
        if(Totalpatient_SunSai <= 10){
            PolygonOptions Sunsai = new PolygonOptions()
                    .add(sunsai1).add(sunsai2).add(sunsai3).add(sunsai4).add(sunsai5).add(sunsai6)
                    .add(sunsai7).add(sunsai8).add(sunsai9).add(sunsai10).add(sunsai11).add(sunsai12)
                    .add(sunsai19).add(sunsai20).add(sunsai21).add(sunsai22).add(sunsai23).add(sunsai24)
                    .add(sunsai25).add(sunsai26).add(sunsai27).add(sunsai28).add(sunsai29).add(sunsai30)
                    .add(sunsai31).add(sunsai32).add(sunsai33).add(sunsai34).add(sunsai35).add(sunsai36)

                    .strokeColor(Color.argb(250, 0, 255, 0))
                    .fillColor(Color.argb(30, 0, 255, 0));
            Polygon polygonSunSai = mMap.addPolygon(Sunsai);
            polygonSunSai.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    createDialog2();
                }
            });
        }else if(Totalpatient_SunSai <= 30) {
            PolygonOptions Sunsai = new PolygonOptions()
                    .add(sunsai1).add(sunsai2).add(sunsai3).add(sunsai4).add(sunsai5).add(sunsai6)
                    .add(sunsai7).add(sunsai8).add(sunsai9).add(sunsai10).add(sunsai11).add(sunsai12)
                    .add(sunsai19).add(sunsai20).add(sunsai21).add(sunsai22).add(sunsai23).add(sunsai24)
                    .add(sunsai25).add(sunsai26).add(sunsai27).add(sunsai28).add(sunsai29).add(sunsai30)
                    .add(sunsai31).add(sunsai32).add(sunsai33).add(sunsai34).add(sunsai35).add(sunsai36)

                    .strokeColor(Color.argb(250, 255, 255, 0))
                    .fillColor(Color.argb(30, 255, 255, 0));
            Polygon polygonSunSai = mMap.addPolygon(Sunsai);
            polygonSunSai.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    createDialog2();
                }
            });
        }else if(Totalpatient_SunSai <= 30) {
            PolygonOptions Sunsai = new PolygonOptions()
                    .add(sunsai1).add(sunsai2).add(sunsai3).add(sunsai4).add(sunsai5).add(sunsai6)
                    .add(sunsai7).add(sunsai8).add(sunsai9).add(sunsai10).add(sunsai11).add(sunsai12)
                    .add(sunsai19).add(sunsai20).add(sunsai21).add(sunsai22).add(sunsai23).add(sunsai24)
                    .add(sunsai25).add(sunsai26).add(sunsai27).add(sunsai28).add(sunsai29).add(sunsai30)
                    .add(sunsai31).add(sunsai32).add(sunsai33).add(sunsai34).add(sunsai35).add(sunsai36)

                    .strokeColor(Color.argb(250, 255, 255, 0))
                    .fillColor(Color.argb(30, 255, 255, 0));
            Polygon polygonSunSai = mMap.addPolygon(Sunsai);
            polygonSunSai.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    createDialog2();
                }
            });
        }else if(Totalpatient_SunSai >= 50) {
            PolygonOptions Sunsai = new PolygonOptions()
                    .add(sunsai1).add(sunsai2).add(sunsai3).add(sunsai4).add(sunsai5).add(sunsai6)
                    .add(sunsai7).add(sunsai8).add(sunsai9).add(sunsai10).add(sunsai11).add(sunsai12)
                    .add(sunsai19).add(sunsai20).add(sunsai21).add(sunsai22).add(sunsai23).add(sunsai24)
                    .add(sunsai25).add(sunsai26).add(sunsai27).add(sunsai28).add(sunsai29).add(sunsai30)
                    .add(sunsai31).add(sunsai32).add(sunsai33).add(sunsai34).add(sunsai35).add(sunsai36)

                    .strokeColor(Color.argb(250, 255, 0, 0))
                    .fillColor(Color.argb(30, 255, 0, 0));
            Polygon polygonSunSai = mMap.addPolygon(Sunsai);
            polygonSunSai.setClickable(true);
            mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(@NonNull Polygon polygon) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(user_location, 12);
                    mMap.animateCamera(cameraUpdate);
                    createDialog2();
                }
            });
        }
        ////////////////////End สันทราย////////////////////

    }

    private void createDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("อำเภอเมืองเชียงใหม่");
        builder.setMessage("????????????????");
        /*LinearLayout list_cluster = findViewById(R.id.showlistdistrick);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/cluster/เมืองเชียงใหม่");
        Query query1 = myRef.orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    View cluster = getLayoutInflater().inflate(R.layout.layout_cluster, null);
                    String clusterDate = ds.child("clusterDate").getValue().toString();
                    String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                    String clusterPlace = ds.child("clusterPlace").getValue().toString();
                    String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                    String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();

                    TextView txtplace = cluster.findViewById(R.id.txtview_place);
                    txtplace.setText(clusterPlace);

                    TextView txtsubdistrict = cluster.findViewById(R.id.txtview_subdistrict);
                    txtsubdistrict.setText(clusterSubdistrict);

                    TextView txtdistrict = cluster.findViewById(R.id.txtview_district);
                    txtdistrict.setText(clusterDistrict);

                    TextView txtdate = cluster.findViewById(R.id.txtview_date);
                    txtdate.setText(clusterDate);

                    TextView txtnewpatient = cluster.findViewById(R.id.txtview_newpatient);
                    txtnewpatient.setText(cluster_news_patient);

                    list_cluster.addView(cluster);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });*/
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void addingCircleView(LatLng mLatLng) {
        CircleOptions circleOptions = new CircleOptions()
                .center(mLatLng)   //set center
                .radius(100)   //set radius in meters
                .fillColor(Color.parseColor("#1AFB2323"))  //default
                .strokeColor(Color.parseColor("#1AFB2323"))
                .strokeWidth(1);
        myCircle1 = mMap.addCircle(circleOptions);
        circleOptions = new CircleOptions()
                .center(mLatLng)   //set center
                .radius(80)   //set radius in meters
                .fillColor(Color.parseColor("#40FB2323"))  //default
                .strokeColor(Color.parseColor("#40FB2323"))
                .strokeWidth(1);
        myCircle2 = mMap.addCircle(circleOptions);
        circleOptions = new CircleOptions()
                .center(mLatLng)   //set center
                .radius(60)   //set radius in meters
                .fillColor(Color.parseColor("#66FB2323"))  //default
                .strokeColor(Color.parseColor("#66FB2323"))
                .strokeWidth(1);
        myCircle3 = mMap.addCircle(circleOptions);
        circleOptions = new CircleOptions()
                .center(mLatLng)   //set center
                .radius(40)   //set radius in meters
                .fillColor(Color.parseColor("#8CFB2323"))  //default
                .strokeColor(Color.parseColor("#8CFB2323"))
                .strokeWidth(1);
        myCircle4 = mMap.addCircle(circleOptions);
        circleOptions = new CircleOptions()
                .center(mLatLng)   //set center
                .radius(20)   //set radius in meters
                .fillColor(Color.parseColor("#A6FB2323"))  //default
                .strokeColor(Color.parseColor("#A6FB2323"))
                .strokeWidth(1);
        myCircle5 = mMap.addCircle(circleOptions);

        circleOptions = new CircleOptions()
                .center(mLatLng)   //set center
                .radius(10)   //set radius in meters
                .fillColor(Color.parseColor("#CCFB2323"))  //default
                .strokeColor(Color.parseColor("#CCFB2323"))
                .strokeWidth(1);
        myCircle6 = mMap.addCircle(circleOptions);
    }

    private void addingCircleView_user(LatLng mLatLng) {
        CircleOptions circleOptions = new CircleOptions()
                .center(mLatLng)   //set center
                .radius(500)   //set radius in meters
                .fillColor(Color.argb(30, 0, 0, 255))  //default
                .strokeColor(Color.argb(40, 0, 0, 255))
                .strokeWidth(2);
        myCircle1 = mMap.addCircle(circleOptions);
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

    public void ClickSearch(View view) {
        SarchButton = findViewById(R.id.txt_sarch);
        sarchbutton = SarchButton.getText().toString();

        for (int i = 0; i < District.length; i++) {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/cluster/"+District[i]);
            Query query1 = myRef.orderByKey();
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        //String clusterDate = ds.child("clusterDate").getValue().toString();
                        //String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                        String clusterplace = ds.child("clusterPlace").getValue().toString();
                        clusterPlace = clusterplace;
                        //String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                        //String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();

                        String clusterLat = ds.child("clusterLat").getValue().toString();
                        String clusterLng = ds.child("clusterLng").getValue().toString();

                        if(sarchbutton.equals(clusterPlace)){
                                sarchLat = Double.parseDouble(clusterLat);
                                sarchLng = Double.parseDouble(clusterLng);

                                Toast.makeText(MainActivity.this, "พบข้อมูล", Toast.LENGTH_SHORT).show();
                                LatLng sarch_location = new LatLng(sarchLat,sarchLng);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sarch_location));
                                mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );
                        }else if(sarchbutton.equals("")){
                            Toast.makeText(MainActivity.this, "กรุณากรอกชื่อสถานที่", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

    }

    //เมืองเชียงใหม่
    LatLng m1 = new LatLng(18.835322,98.866662);
    LatLng m2 = new LatLng(18.831993,98.883823);
    LatLng m3 = new LatLng(18.841613,98.906748);
    LatLng m4 = new LatLng(18.841818,98.941786);
    LatLng m5 = new LatLng(18.840590,98.977904);
    LatLng m6 = new LatLng(18.844709,98.985048);
    LatLng m7 = new LatLng(18.850947,98.986051);
    LatLng m8 = new LatLng(18.851191,98.981550);
    LatLng m9 = new LatLng(18.852043,98.980457);
    LatLng m10 = new LatLng(18.857641,98.981550);
    LatLng m11 = new LatLng(18.859588,98.979942);
    LatLng m12 = new LatLng(18.858614,98.975441);
    LatLng m13 = new LatLng(18.861352,98.975248);
    LatLng m14 = new LatLng(18.864771,98.978688);
    LatLng m15 = new LatLng(18.869890,98.974571);
    LatLng m16 = new LatLng(18.877667,98.999443);
    LatLng m17 = new LatLng(18.825678,99.009176);
    LatLng m18 = new LatLng(18.793331,99.030371);
    LatLng m19 = new LatLng(18.798040,99.050269);
    LatLng m20 = new LatLng(18.795583,99.049404);
    LatLng m21 = new LatLng(18.789646,99.042267);
    LatLng m22 = new LatLng(18.763640,99.051134);
    LatLng m23 = new LatLng(18.746642,99.030155);
    LatLng m24 = new LatLng(18.752991,98.992090);
    LatLng m25 = new LatLng(18.737630,98.982357);
    LatLng m26 = new LatLng(18.695842,98.985602);
    LatLng m27 = new LatLng(18.703217,98.977383);
    LatLng m28 = new LatLng(18.730257,98.980195);
    LatLng m29 = new LatLng(18.736606,98.977383);
    LatLng m30 = new LatLng(18.731485,98.958567);
    LatLng m31 = new LatLng(18.749919,98.912066);
    LatLng m32 = new LatLng(18.745003,98.902550);
    LatLng m33 = new LatLng(18.745003,98.899306);
    LatLng m34 = new LatLng(18.775927,98.890655);
    LatLng m35 = new LatLng(18.800907,98.863187);

    //เเม่ริม
    LatLng ma1 = new LatLng(18.831752,98.884903);
    LatLng ma2 = new LatLng(18.834877,98.866978);
    LatLng ma3 = new LatLng(18.851395,98.861318);
    LatLng ma4 = new LatLng(18.856752,98.844336);
    LatLng ma5 = new LatLng(18.846485,98.834902);
    LatLng ma6 = new LatLng(18.845592,98.831600);
    LatLng ma7 = new LatLng(18.852734,98.818392);
    LatLng ma8 = new LatLng(18.844253,98.801883);
    LatLng ma9 = new LatLng(18.877731,98.782542);
    LatLng ma10 = new LatLng(18.920128,98.799996);
    LatLng ma11= new LatLng(18.939314,98.799052);
    LatLng ma12= new LatLng(18.951807,98.783958);
    LatLng ma13= new LatLng(18.970097,98.781127);
    LatLng ma14= new LatLng(19.024512,98.747164);
    LatLng ma15= new LatLng(19.075787,98.770750);
    LatLng ma16= new LatLng(19.035214,98.810373);
    LatLng ma17= new LatLng(19.025849,98.875469);
    LatLng ma18= new LatLng(19.030755,98.888205);
    LatLng ma19= new LatLng(19.034768,98.891046);
    LatLng ma20= new LatLng(19.040565,98.914160);
    LatLng ma21= new LatLng(19.036106,98.918877);
    LatLng ma22= new LatLng(19.044132,98.958501);
    LatLng ma23= new LatLng(18.997307,98.967463);
    LatLng ma24= new LatLng(18.974558,98.965576);
    LatLng ma25= new LatLng(18.879070,98.997181);
    LatLng ma26= new LatLng(18.869697,98.974067);
    LatLng ma27= new LatLng(18.848978,98.986903);
    LatLng ma28= new LatLng(18.840496,98.978412);
    LatLng ma29= new LatLng(18.836478,98.955298);
    LatLng ma30= new LatLng(18.841389,98.941619);
    LatLng ma31= new LatLng(18.841836,98.906241);
    LatLng ma32= new LatLng(18.831752,98.884903);

    //สารภี
    LatLng sarapee1= new LatLng(18.637389,99.003428);
    LatLng sarapee2= new LatLng(18.635842,98.986280);
    LatLng sarapee3= new LatLng(18.614175,98.965457);
    LatLng sarapee4= new LatLng(18.621140,98.953821);
    LatLng sarapee5= new LatLng(18.644159,98.954230);
    LatLng sarapee6= new LatLng(18.665048,98.969132);
    LatLng sarapee7= new LatLng(18.676652,98.974644);
    LatLng sarapee8= new LatLng(18.678199,98.981584);
    LatLng sarapee9= new LatLng(18.687978,98.986295);
    LatLng sarapee10= new LatLng(18.697981,98.986416);
    LatLng sarapee11= new LatLng(18.726540,98.987096);
    LatLng sarapee12= new LatLng(18.739299,98.980768);
    LatLng sarapee13= new LatLng(18.753604,98.990771);
    LatLng sarapee14= new LatLng(18.749352,99.009552);
    LatLng sarapee15= new LatLng(18.746452,99.030374);
    LatLng sarapee16= new LatLng(18.749970,99.035784);
    LatLng sarapee17= new LatLng(18.760505,99.039560);
    LatLng sarapee18= new LatLng(18.763211,99.050788);
    LatLng sarapee19= new LatLng(18.753449,99.056708);
    LatLng sarapee20= new LatLng(18.732185,99.071509);
    LatLng sarapee21= new LatLng(18.725418,99.067834);
    LatLng sarapee22= new LatLng(18.696222,99.079368);
    LatLng sarapee23= new LatLng(18.681429,99.080695);
    LatLng sarapee24= new LatLng(18.670269,99.078910);
    LatLng sarapee25= new LatLng(18.660724,99.071263);
    LatLng sarapee26= new LatLng(18.665324,99.057668);
    LatLng sarapee27= new LatLng(18.671304,99.015427);
    LatLng sarapee28= new LatLng(18.665784,99.013849);
    LatLng sarapee29= new LatLng(18.644163,99.011907);
    LatLng sarapee30= new LatLng(18.643128,99.007415);
    LatLng sarapee31= new LatLng(18.641173,99.007294);
    LatLng sarapee32= new LatLng(18.637389,99.003428);

    //////สันทราย
    LatLng sunsai1= new LatLng(18.793556,99.030590);
    LatLng sunsai2= new LatLng(18.806674,99.025393);
    LatLng sunsai3= new LatLng(18.820884,99.015577);
    LatLng sunsai4= new LatLng(18.826101,99.016484);
    LatLng sunsai5= new LatLng(18.826101,99.008715);
    LatLng sunsai6= new LatLng(18.835292,99.011628);
    LatLng sunsai7= new LatLng(18.894748,98.991931);
    LatLng sunsai8= new LatLng(19.018298,98.954020);
    LatLng sunsai9= new LatLng(19.092185,98.939454);
    LatLng sunsai10= new LatLng(19.104114,98.973927);
    LatLng sunsai11= new LatLng(19.056852,99.038503);
    LatLng sunsai12= new LatLng(19.057770,99.058895);
    LatLng sunsai19= new LatLng(19.036199,99.081715);
    LatLng sunsai20= new LatLng(19.003149,99.086085);
    LatLng sunsai21= new LatLng(18.995804,99.075889);
    LatLng sunsai22= new LatLng(18.991213,99.064236);
    LatLng sunsai23= new LatLng(18.977899,99.069577);
    LatLng sunsai24= new LatLng(18.971471,99.059866);
    LatLng sunsai25= new LatLng(18.964124,99.078316);
    LatLng sunsai26= new LatLng(18.948511,99.098223);
    LatLng sunsai27= new LatLng(18.937030,99.119101);
    LatLng sunsai28= new LatLng(18.914985,99.127841);
    LatLng sunsai29= new LatLng(18.891558,99.115702);
    LatLng sunsai30= new LatLng(18.887883,99.123471);
    LatLng sunsai31= new LatLng(18.857101,99.102107);
    LatLng sunsai32= new LatLng(18.830450,99.067635);
    LatLng sunsai33= new LatLng(18.818501,99.051127);
    LatLng sunsai34= new LatLng(18.798278,99.057924);
    LatLng sunsai35= new LatLng(18.799198,99.041416);
    LatLng sunsai36= new LatLng(18.793556,99.030590);

    //สำกำเเพง
    LatLng sungom1= new LatLng(18.792837,99.043005);
    LatLng sungom2= new LatLng(18.787607,99.042946);
    LatLng sungom3= new LatLng(18.732372,99.071500);
    LatLng sungom4= new LatLng(18.726194,99.066179);
    LatLng sungom5= new LatLng(18.717480,99.073088);
    LatLng sungom6= new LatLng(18.681380,99.080800);
    LatLng sungom7= new LatLng(18.681533,99.075773);
    LatLng sungom8= new LatLng(18.679464,99.076380);
    LatLng sungom9= new LatLng(18.677164,99.075895);
    LatLng sungom10= new LatLng(18.677164,99.075895);
    LatLng sungom11= new LatLng(18.675209,99.077109);
    LatLng sungom12= new LatLng(18.673024,99.076259);
    LatLng sungom13= new LatLng(18.670494,99.079051);
    LatLng sungom14= new LatLng(18.671989,99.080022);
    LatLng sungom15= new LatLng(18.669344,99.098715);
    LatLng sungom16= new LatLng(18.679349,99.106240);
    LatLng sungom17= new LatLng(18.679119,99.113281);
    LatLng sungom18= new LatLng(18.683833,99.126633);
}