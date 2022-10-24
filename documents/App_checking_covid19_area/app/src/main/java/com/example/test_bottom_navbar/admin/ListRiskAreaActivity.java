package com.example.test_bottom_navbar.admin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.ui_bar.MainActivity;
import com.example.test_bottom_navbar.ui_bar.NewsActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListRiskAreaActivity extends AppCompatActivity {
    String clusterPlace;
    String cluster_nametxt;
    int cluster_patient;
    ArrayList<String> Sort_date = new ArrayList<String>();
    List<String> ListYellow = new ArrayList<String>();
    List<String> ListRed = new ArrayList<String>();
    List<String> ListOrange = new ArrayList<String>();

    int patient_number;
    int Allpatient_District,Totalpatient_CM,Totalpatient_Sarapee,Totalpatient_MaeRim,Totalpatient_SunSai;
    private String[] District= {"เมืองเชียงใหม่","สารภี","เเม่ริม","สันกำเเพง","สันทราย"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_risk_area);

        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        clusterPlace = intent.getStringExtra("clusterPlace");

        Intent getpatient = getIntent();
        patient_number = getpatient.getIntExtra("patient_number",patient_number);
        System.out.println("////////////////////////////////////////////"+Allpatient_District);
        this.setListClusterByAdmin();
        this.ListCheckCluster();
    }

    @SuppressLint("LongLogTag")
    public void  setListClusterByAdmin() {
        for (int i=0;i < District.length;i++) {
            System.out.println(District[i]);
            LinearLayout list_cluster = findViewById(R.id.showlistcluster_admin);
            list_cluster.removeAllViews();
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/cluster/" + District[i]);
            String district_name = District[i];
            Query query1 = myRef.orderByKey();
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Log.e("Data_cluster", ds.getValue().toString());
                        View cluster = getLayoutInflater().inflate(R.layout.layout_clusterby_admin, null);
                        String clusterDate = ds.child("clusterDate").getValue().toString();
                        String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                        String clusterPlace = ds.child("clusterPlace").getValue().toString();
                        String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                        String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();

                        TextView txtplace = cluster.findViewById(R.id.txtedit_place);
                        txtplace.setText(clusterPlace);

                        TextView txtsubdistrict = cluster.findViewById(R.id.txtedit_subdistrict);
                        txtsubdistrict.setText(clusterSubdistrict);

                        TextView txtdistrict = cluster.findViewById(R.id.txtedit_district);
                        txtdistrict.setText(clusterDistrict);

                        TextView txtdate = cluster.findViewById(R.id.txtedit_date);
                        txtdate.setText(clusterDate);

                        Sort_date.add(clusterDate);

                        TextView txtnewpatient = cluster.findViewById(R.id.txtedit_newpatient);
                        txtnewpatient.setText(cluster_news_patient);

                        patient_number = Integer.parseInt(txtnewpatient.getText().toString());

                        ImageView btn_Edit = (ImageView) cluster.findViewById(R.id.cluster_clicktoedit);
                        btn_Edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ListRiskAreaActivity.this, EditClusterActivity.class);
                                intent.putExtra("clusterPlace", clusterPlace);
                                intent.putExtra("district_name", district_name);
                                startActivity(intent);
                            }
                        });
                        list_cluster.addView(cluster);
                        Collections.sort(Sort_date);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    public void  setListClusterRedByAdmin() {
        for (int i=0;i < District.length;i++) {
            System.out.println(District[i]);
            LinearLayout list_cluster = findViewById(R.id.showlistcluster_admin);
            list_cluster.removeAllViews();
            for (int u = 0; u < ListRed.size(); u++) {
                System.out.println(ListRed.get(u));
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("admin001/cluster/"+District[i]);
                String district_name = District[i];
                Query query1 = myRef.orderByKey().equalTo(ListRed.get(u));
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Log.e("Data_cluster", ds.getValue().toString());
                            View cluster = getLayoutInflater().inflate(R.layout.layout_clusterby_admin, null);
                            String clusterDate = ds.child("clusterDate").getValue().toString();
                            String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                            String clusterPlace = ds.child("clusterPlace").getValue().toString();
                            String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                            String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();

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

                            patient_number = Integer.parseInt(txtnewpatient.getText().toString());

                            ImageView btn_Edit = (ImageView) cluster.findViewById(R.id.cluster_clicktoedit);
                            btn_Edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(ListRiskAreaActivity.this, EditClusterActivity.class);
                                    intent.putExtra("clusterPlace", clusterPlace);
                                    intent.putExtra("district_name", district_name);
                                    startActivity(intent);
                                }
                            });
                            list_cluster.addView(cluster);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
    }

    public void  setListClusterOrangeByAdmin() {
        for (int i=0;i < District.length;i++) {
            System.out.println(District[i]);
            LinearLayout list_cluster = findViewById(R.id.showlistcluster_admin);
            list_cluster.removeAllViews();
            for (int u = 0; u < ListOrange.size(); u++) {
                System.out.println(ListOrange.get(u));
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("admin001/cluster/"+District[i]);
                String district_name = District[i];
                Query query1 = myRef.orderByKey().equalTo(ListOrange.get(u));
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Log.e("Data_cluster", ds.getValue().toString());
                            View cluster = getLayoutInflater().inflate(R.layout.layout_clusterby_admin, null);
                            String clusterDate = ds.child("clusterDate").getValue().toString();
                            String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                            String clusterPlace = ds.child("clusterPlace").getValue().toString();
                            String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                            String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();

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

                            patient_number = Integer.parseInt(txtnewpatient.getText().toString());

                            ImageView btn_Edit = (ImageView) cluster.findViewById(R.id.cluster_clicktoedit);
                            btn_Edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(ListRiskAreaActivity.this, EditClusterActivity.class);
                                    intent.putExtra("clusterPlace", clusterPlace);
                                    intent.putExtra("district_name", district_name);
                                    startActivity(intent);
                                }
                            });
                            list_cluster.addView(cluster);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
    }

    public void  setListClusterYellowByAdmin() {
        for (int i=0;i < District.length;i++) {
            System.out.println(District[i]);
            LinearLayout list_cluster = findViewById(R.id.showlistcluster_admin);
            list_cluster.removeAllViews();
            for (int u = 0; u < ListYellow.size(); u++) {
                System.out.println(ListYellow.get(u));
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("admin001/cluster/"+District[i]);
                String district_name = District[i];
                Query query1 = myRef.orderByKey().equalTo(ListYellow.get(u));
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Log.e("Data_cluster", ds.getValue().toString());
                            View cluster = getLayoutInflater().inflate(R.layout.layout_clusterby_admin, null);
                            String clusterDate = ds.child("clusterDate").getValue().toString();
                            String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                            String clusterPlace = ds.child("clusterPlace").getValue().toString();
                            String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                            String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();

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

                            patient_number = Integer.parseInt(txtnewpatient.getText().toString());

                            ImageView btn_Edit = (ImageView) cluster.findViewById(R.id.cluster_clicktoedit);
                            btn_Edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(ListRiskAreaActivity.this, EditClusterActivity.class);
                                    intent.putExtra("clusterPlace", clusterPlace);
                                    intent.putExtra("district_name", district_name);
                                    startActivity(intent);
                                }
                            });
                            list_cluster.addView(cluster);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
    }

    public void ListCheckCluster() {
        for (int i=0;i < District.length;i++) {
            System.out.println(District[i]);
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/cluster/" + District[i]);
            String district_name = District[i];
            Query query1 = myRef.orderByKey();
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String clusterDate = ds.child("clusterDate").getValue().toString();
                        String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                        String clusterPlace = ds.child("clusterPlace").getValue().toString();
                        String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                        String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();
                        cluster_nametxt = clusterPlace;
                        cluster_patient = Integer.parseInt(cluster_news_patient);

                        if(cluster_patient <= 10){
                            ListYellow.add(cluster_nametxt);
                        }
                        if(cluster_patient <= 50){
                            ListOrange.add(cluster_nametxt);
                        }
                        if(cluster_patient > 50){
                            ListRed.add(cluster_nametxt);
                        }
                    }
                    System.out.println("---Y"+ListYellow);
                    System.out.println("---O"+ListOrange);
                    System.out.println("---R"+ListRed);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    public void ClickBTNBackByAdmin(View view){
        Intent intent = new Intent(ListRiskAreaActivity.this, Mainpage_admin.class);
        startActivity(intent);
    }

    public void ClickCluster_All(View view){
        TextView view_All = findViewById(R.id.view_All);
        TextView view_Red = findViewById(R.id.view_Red);
        TextView view_orange = findViewById(R.id.view_orange);
        TextView view_yellow = findViewById(R.id.view_yellow);

        view_yellow.setTextColor(Color.parseColor("#000000"));
        view_orange.setTextColor(Color.parseColor("#000000"));
        view_Red.setTextColor(Color.parseColor("#000000"));
        view_All.setTextColor(Color.parseColor("#FFFFFF"));

        view_Red.setBackgroundColor(Color.parseColor("#EF5350"));
        view_orange.setBackgroundColor(Color.parseColor("#FF7043"));
        view_yellow.setBackgroundColor(Color.parseColor("#FFEE58"));

        ///list cluster
        setListClusterByAdmin();
    }

    public void ClickCluster_Red(View view){
        TextView view_All = findViewById(R.id.view_All);
        TextView view_Red = findViewById(R.id.view_Red);
        TextView view_orange = findViewById(R.id.view_orange);
        TextView view_yellow = findViewById(R.id.view_yellow);

        view_yellow.setTextColor(Color.parseColor("#000000"));
        view_orange.setTextColor(Color.parseColor("#000000"));
        view_Red.setTextColor(Color.parseColor("#FFFFFF"));
        view_All.setTextColor(Color.parseColor("#000000"));

        view_Red.setBackgroundColor(Color.parseColor("#FF0500"));
        view_orange.setBackgroundColor(Color.parseColor("#FF7043"));
        view_yellow.setBackgroundColor(Color.parseColor("#FFEE58"));

        ///list cluster
        setListClusterRedByAdmin();
    }

    public void ClickCluster_Orange(View view){
        TextView view_All = findViewById(R.id.view_All);
        TextView view_Red = findViewById(R.id.view_Red);
        TextView view_orange = findViewById(R.id.view_orange);
        TextView view_yellow = findViewById(R.id.view_yellow);

        view_Red.setTextColor(Color.parseColor("#000000"));
        view_yellow.setTextColor(Color.parseColor("#000000"));
        view_orange.setTextColor(Color.parseColor("#FFFFFF"));
        view_All.setTextColor(Color.parseColor("#000000"));

        view_Red.setBackgroundColor(Color.parseColor("#EF5350"));
        view_orange.setBackgroundColor(Color.parseColor("#FD3D00"));
        view_yellow.setBackgroundColor(Color.parseColor("#FFEE58"));

        ///list cluster
        setListClusterOrangeByAdmin();
    }

    public void ClickCluster_Yellow(View view){
        TextView view_All = findViewById(R.id.view_All);
        TextView view_Red = findViewById(R.id.view_Red);
        TextView view_orange = findViewById(R.id.view_orange);
        TextView view_yellow = findViewById(R.id.view_yellow);

        view_Red.setTextColor(Color.parseColor("#000000"));
        view_orange.setTextColor(Color.parseColor("#000000"));
        view_yellow.setTextColor(Color.parseColor("#FFFFFF"));
        view_All.setTextColor(Color.parseColor("#000000"));

        view_yellow.setBackgroundColor(Color.parseColor("#FFE500"));
        view_orange.setBackgroundColor(Color.parseColor("#FF7043"));
        view_Red.setBackgroundColor(Color.parseColor("#EF5350"));

        ///list cluster
        setListClusterYellowByAdmin();
    }

}
