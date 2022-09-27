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
    private Button button;
    Intent intent = getIntent();
    private Dialog dialog;
    String location;
    String LatLng;
    private GoogleMap mMap,user_location;
    Cluster cluster = new Cluster();
    //private ActivityMapsBinding binding;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    String clusterdistrict[] = {"เมืองเชียงใหม่","แม่ริม","สารภี","สันทราย","สันกำแพง","หางดง","ดอยสะเก็ด",
            "สันป่าตอง","แม่ออน","แม่วาง","แม่แตง","ดอยหล่อ","สะเมิง","จอมทอง",
            "เชียงดาว","ฮอด","พร้าว","ดอยเต่า","ไชยปราการ","เวียงแหง","ฝาง","แม่แจ่ม",
            "แม่อาย","อมก๋อย"};
    private Context context;

    double SaMeng[] =  {98.7868,18.844,98.7819,18.8286,98.7831,18.8177,98.7869,18.8144,98.7831,18.8107,98.7831,18.8024,98.7921,18.7847,98.8004,18.7785,98.8047,18.7695,98.808,18.7707,98.8106,18.7684,98.8106,18.7652,98.8152,18.7643,98.8173,18.7623,98.819,18.7578,98.8289,18.7545,98.8276,18.7465,98.8287,18.7428,98.8273,18.7397,98.8309,18.7293,98.8349,18.7267,98.8316,18.718,98.8247,18.7169,98.8167,18.7308,98.812,18.728,98.8106,18.7255,98.8087,18.7254,98.8063,18.7287,98.7988,18.7317,98.7967,18.7355,98.7908,18.7385,98.7883,18.7378,98.7879,18.7411,98.7847,18.744,98.7807,18.7431,98.779,18.7467,98.777,18.746,98.7726,18.7535,98.7668,18.7545,98.7645,18.7602,98.7608,18.7598,98.7577,18.7615,98.7567,18.7627,98.7576,18.7666,98.7429,18.7666,98.735,18.7646,98.7276,18.7686,98.7266,18.7717,98.7098,18.7754,98.7061,18.7802,98.7013,18.7798,98.6977,18.7818,98.6949,18.7809,98.6935,18.7777,98.6816,18.7785,98.6796,18.7741,98.6738,18.7727,98.6651,18.7719,98.6654,18.7735,98.662,18.7736,98.6592,18.7691,98.6508,18.773,98.6429,18.7679,98.6393,18.7627,98.6426,18.7606,98.6471,18.7484,98.6509,18.7492,98.6534,18.7481,98.6618,18.7409,98.6628,18.7389,98.6618,18.7378,98.6502,18.7353,98.6385,18.7283,98.631,18.7259,98.6181,18.7265,98.614,18.7299,98.6026,18.7269,98.595,18.7354,98.5952,18.7431,98.5924,18.7467,98.5882,18.7478,98.5866,18.7581,98.584,18.7564,98.5784,18.758,98.5741,18.769,98.5658,18.7715,98.5662,18.776,98.5626,18.787,98.5659,18.7911,98.5656,18.7937,98.5615,18.7968,98.5596,18.8022,98.5473,18.8133,98.5476,18.8188,98.5415,18.8252,98.5411,18.8301,98.5318,18.836,98.532,18.8398,98.5297,18.8441,98.5304,18.8467,98.525,18.856,98.5179,18.858,98.519,18.8614,98.5251,18.8662,98.527,18.8729,98.518,18.8794,98.5123,18.8813,98.5082,18.8798,98.5053,18.8817,98.5048,18.8857,98.4985,18.8859,98.4993,18.8945,98.5049,18.9037,98.5016,18.9104,98.4921,18.9148,98.4849,18.913,98.4777,18.9207,98.4809,18.9268,98.4802,18.9293,98.4774,18.9312,98.4699,18.931,98.4603,18.9355,98.4617,18.9379,98.4606,18.9415,98.4732,18.9527,98.4745,18.9598,98.4687,18.9598,98.465,18.9572,98.4564,18.9595,98.4491,18.9587,98.4381,18.9502,98.4317,18.9494,98.4283,18.9594,98.4191,18.9613,98.4111,18.9685,98.4134,18.9765,98.4101,18.9819,98.4029,18.9851,98.4003,18.9982,98.4019,18.9988,98.4031,19.0031,98.406,19.0036,98.4088,19.0011,98.4159,19.0063,98.4146,19.0093,98.4206,19.0113,98.422,19.0189,98.4206,19.0231,98.4231,19.0279,98.4254,19.0291,98.4264,19.0342,98.4304,19.0398,98.4409,19.0458,98.4587,19.0445,98.4653,19.0413,98.4681,19.0416,98.47,19.0397,98.4797,19.0408,98.4848,19.0385,98.4921,19.0386,98.4953,19.0361,98.5171,19.0302,98.5294,19.023,98.5408,19.0233,98.5472,19.0243,98.5539,19.0299,98.5562,19.0354,98.5644,19.0352,98.5694,19.0436,98.5849,19.0456,98.5944,19.0414,98.598,19.0424,98.6027,19.0491,98.6064,19.0512,98.6153,19.055,98.6212,19.0556,98.6294,19.06,98.634,19.0604,98.6388,19.0656,98.6471,19.0677,98.6512,19.0706,98.6622,19.0704,98.6668,19.0731,98.6881,19.0692,98.6911,19.0651,98.6941,19.065,98.7098,19.0553,98.7126,19.0552,98.7124,19.0523,98.7141,19.0517,98.7179,19.0526,98.7193,19.0549,98.7271,19.0549,98.7272,19.0608,98.7257,19.0631,98.7277,19.068,98.7316,19.0695,98.7374,19.0695,98.7392,19.0722,98.7407,19.0708,98.7508,19.0711,98.7536,19.0696,98.7576,19.0718,98.7613,19.071,98.7684,19.0741,98.7716,19.0734,98.7702,19.0629,98.7678,19.0601,98.763,19.0598,98.7592,19.0522,98.7586,19.047,98.7522,19.0438,98.7505,19.0378,98.7522,19.0345,98.7517,19.0252,98.7492,19.0224,98.7528,19.0147,98.7532,19.0091,98.7566,19.0052,98.7571,19.0007,98.7606,18.9996,98.7651,18.9852,98.7728,18.9774,98.7785,18.974,98.7828,18.969,98.7831,18.9632,98.7873,18.9606,98.7876,18.9563,98.7853,18.9504,98.7888,18.9454,98.7973,18.9403,98.8006,18.9347,98.8013,18.9219,98.798,18.9112,98.7906,18.9021,98.7891,18.8917,98.7865,18.8887,98.7847,18.8783,98.7847,18.8731,98.7866,18.8715,98.7917,18.8565,98.79,18.8528,98.7924,18.8492,98.792,18.8473,98.7868,18.844};

    Circle myCircle1;
    Circle myCircle2;
    Circle myCircle3;
    Circle myCircle4;
    Circle myCircle5;
    Circle myCircle6;

    int Allpatient_District;
    private int STORAGE_PERMISSION_CODE = 1;
    String statesobj[] = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Allpatient_District = intent.getIntExtra("Allpatient_District",Allpatient_District);
        System.out.println("////////////////////////////////////////////"+Allpatient_District);

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

    //instance firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("admin001/cluster");
    Query query1 = myRef.orderByValue();

   /* FirebaseDatabase database1 = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef1 = database1.getReference("admin001/cluster/สันทราย");
    Query query2 = myRef1.orderByKey();*/

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
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_baseline_person_pin_circle_24)
                )
        );

        mMap.moveCamera(CameraUpdateFactory.newLatLng(user_location));
        // Move the camera
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        //Zoom setUp
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );
        //Zoom button
        mMap.getUiSettings().setZoomControlsEnabled(true);

        addingCircleView_user(user_location);

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
                           // .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_baseline_warning_24))
                    );
                    addingCircleView(location);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Polygon
        //เมืองเชียงใหม่
        if(Allpatient_District <= 10){
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
                    createDialog1();
                }
            });
        }else if(Allpatient_District >= 20){
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
                    createDialog1();
                }
            });
        }


        //เเม่ริม
        PolygonOptions SaMeng = new PolygonOptions()
                .add(ma1).add(ma2).add(ma3).add(ma4).add(ma5).add(ma6)
                .add(ma7).add(ma8).add(ma9).add(ma10).add(ma11).add(ma12)
                .add(ma13).add(ma14).add(ma15).add(ma16).add(ma17).add(ma18)
                .add(ma19).add(ma20).add(ma21).add(ma22).add(ma23).add(ma24)
                .add(ma25).add(ma26).add(ma27).add(ma28).add(ma29).add(ma30)
                .add(ma31).add(ma32)
                .strokeColor(Color.argb(250, 255, 0, 0))
                .fillColor(Color.argb(30, 255, 0, 0));
        Polygon polygonMaeRim = mMap.addPolygon(SaMeng);
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

    private void createDialog1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("อำเภอเมืองเชียงใหม่");
        builder.setView(R.layout.layout_dialog);
        AlertDialog alert = builder.create();
        alert.show();

        /*dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.layout_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.diaog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay);
        Button Cancel = dialog.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();*/
    }

    private void createDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("อำเภอเเม่ริม");
        builder.setView(R.layout.layout_dialog);
        AlertDialog alert = builder.create();
        alert.show();
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




}
