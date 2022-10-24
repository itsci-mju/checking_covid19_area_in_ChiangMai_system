package com.example.test_bottom_navbar.admin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test_bottom_navbar.Cluster;
import com.example.test_bottom_navbar.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class EditClusterActivity extends AppCompatActivity {
    String clusterPlace, district_name,subdistrict_name,patientCheck,SetDefault_Date;
    int amount_NewPatient,amount_GetWellPatient,All_Patient,All_PatientDistrict,All_HealingDistrict,
            All_GetWellDistrict,today_newpatient,todat_gwtwellpatient;
    String clusterAlldistrict;
    String clusterAllhealingdistrict;
    String clusterAllgetwelldistrict;
    String cluster_GetWell_patient,cluster_News_patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cluster);
        clusterPlace = getIntent().getStringExtra("clusterPlace");
        district_name = getIntent().getStringExtra("district_name");
        subdistrict_name = getIntent().getStringExtra("subdistrict_name");

        this.getClusterToEdit();
        DateClusterDefault();
    }

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    @SuppressLint("NewApi")
    public void ClickDateCluster_Edit(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditClusterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                EditText txtdate = findViewById(R.id.txtedit_date);
                String day = checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month + 1));
                txtdate.setText(day + "-" + months + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void DateClusterDefault(){
        Calendar calendar = Calendar.getInstance();
        EditText txtdate = findViewById(R.id.txtedit_date);
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        String day = checklength(String.valueOf(mDay));
        String months = checklength(String.valueOf(mMonth + 1));
        txtdate.setText(day + "-" + months + "-" + mYear);
        SetDefault_Date = txtdate.getText().toString();
        txtdate.setText(SetDefault_Date);
    }

    public void getClusterToEdit() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/cluster/" + district_name);
        Query query1 = myRef.orderByKey().equalTo(clusterPlace);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                    String clusterplace = ds.child("clusterPlace").getValue().toString();
                    clusterPlace = clusterplace;
                    String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                    String cluster_All_patient = ds.child("cluster_All_patient").getValue().toString();
                    String cluster_getwell_patient = ds.child("cluster_getwell_patient").getValue().toString();
                    String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();
                    cluster_GetWell_patient = cluster_getwell_patient;
                    cluster_News_patient = cluster_news_patient;

                    String cluster_Allgetwell_district = ds.child("cluster_Allgetwell_district").getValue().toString();
                    String cluster_Allhealing_district = ds.child("cluster_Allhealing_district").getValue().toString();
                    String cluster_Allpatient_district = ds.child("cluster_Allpatient_district").getValue().toString();

                    clusterAlldistrict = cluster_Allpatient_district;
                    clusterAllhealingdistrict = cluster_Allhealing_district;
                    clusterAllgetwelldistrict = cluster_Allgetwell_district;

                    String clusterLat = ds.child("clusterLat").getValue().toString();
                    String clusterLng = ds.child("clusterLng").getValue().toString();

                    TextView txtplace = findViewById(R.id.txtedit_place);
                    txtplace.setText(clusterPlace);

                    TextView txtsubdistrict = findViewById(R.id.txtedit_subdistrict);
                    txtsubdistrict.setText(clusterSubdistrict);

                    TextView txtdistrict = findViewById(R.id.txtedit_district);
                    txtdistrict.setText(clusterDistrict);

                    TextView txtnewpatient = findViewById(R.id.txtAll_patient);
                    txtnewpatient.setText(cluster_All_patient);

                    TextView txtlat = findViewById(R.id.txtedit_lat);
                    txtlat.setText(clusterLat);

                    TextView txtlng = findViewById(R.id.txtedit_lng);
                    txtlng.setText(clusterLng);

                    EditText txtcluster_getwell_patient = findViewById(R.id.txt_getwellpatient_amount);
                    txtcluster_getwell_patient.setText("0");

                    EditText txtcluster_news_patient = findViewById(R.id.txt_newpatient_amount);
                    txtcluster_news_patient.setText("0");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("LongLogTag")
    public void ClickEditCluster(View view) {
        Cluster clusterToedit = new Cluster();

        EditText txtdate = findViewById(R.id.txtedit_date);
        clusterToedit.setClusterDate(txtdate.getText().toString());
        TextView txtplace = findViewById(R.id.txtedit_place);
        clusterToedit.setClusterPlace(txtplace.getText().toString());
        TextView txtsubdistrict = findViewById(R.id.txtedit_subdistrict);
        clusterToedit.setClusterSubdistrict(txtsubdistrict.getText().toString());
        TextView txtdistrict = findViewById(R.id.txtedit_district);
        clusterToedit.setClusterDistrict(txtdistrict.getText().toString());

        TextView txtAllpatient = findViewById(R.id.txtAll_patient);
        clusterToedit.setCluster_All_patient(txtAllpatient.getText().toString());

        EditText txtnewpatient = findViewById(R.id.txt_newpatient_amount);
        clusterToedit.setCluster_news_patient(txtnewpatient.getText().toString());

        EditText txtgetwellpatient = findViewById(R.id.txt_getwellpatient_amount);
        clusterToedit.setCluster_getwell_patient(txtgetwellpatient.getText().toString());

        TextView lat = findViewById(R.id.txtedit_lat);
        clusterToedit.setClusterLat(lat.getText().toString());
        TextView lng = findViewById(R.id.txtedit_lng);
        clusterToedit.setClusterLng(lng.getText().toString());

        String clusterDate = txtdate.getText().toString();
        String clusterPlace = txtplace.getText().toString();
        String clusterSubdistrict = txtsubdistrict.getText().toString();
        String clusterDistrict = txtdistrict.getText().toString();
        String clusterLat = lat.getText().toString();
        String clusterLng = lng.getText().toString();

        String clusterAllpatient = txtAllpatient.getText().toString();
        String clusternewpatient = txtnewpatient.getText().toString();
        String clustergetwellpatient = txtgetwellpatient.getText().toString();

        String Allpatientdistrict ;
        String Allgetwelldistrict ;
        String Allhealingdistrict ;

        /*if(clusternewpatient.equals("0")){
            clusternewpatient = cluster_News_patient;
        }else if(clustergetwellpatient.equals("0")){
            clustergetwellpatient = cluster_GetWell_patient;
        }*/

        amount_GetWellPatient = Integer.parseInt(clustergetwellpatient);
        amount_NewPatient = Integer.parseInt(clusternewpatient);
        All_Patient = Integer.parseInt(clusterAllpatient);

        All_PatientDistrict = Integer.parseInt(clusterAlldistrict);
        All_GetWellDistrict = Integer.parseInt(clusterAllgetwelldistrict);
        All_HealingDistrict = Integer.parseInt(clusterAllhealingdistrict);

        All_Patient = All_Patient + amount_NewPatient;
        All_Patient = All_Patient - amount_GetWellPatient;

        All_PatientDistrict = All_PatientDistrict + amount_NewPatient;

        All_GetWellDistrict = All_GetWellDistrict + amount_GetWellPatient;

        All_HealingDistrict = All_HealingDistrict + amount_NewPatient;
        All_HealingDistrict = All_HealingDistrict - amount_GetWellPatient;

        clusterAllpatient = String.valueOf(All_Patient);
        clustergetwellpatient = String.valueOf(amount_GetWellPatient);
        clusternewpatient = String.valueOf(amount_NewPatient);

        Allpatientdistrict = String.valueOf(All_PatientDistrict);
        Allhealingdistrict = String.valueOf(All_HealingDistrict);
        Allgetwelldistrict = String.valueOf(All_GetWellDistrict);

        if (All_Patient <= 0) {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            AlertDialog.Builder builder = new AlertDialog.Builder(EditClusterActivity.this);
            builder.setTitle("คำความเตือน");
            builder.setMessage("หากจำนวนยอดผู้ติดเชื้อเป็น 0 ระบบจะทำการลบจุดคลัสเตอร์นั้นออกจากแผนที่");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseReference myRef = database.getReference("admin001/cluster/" + district_name);
                    DatabaseReference stu1 = myRef.child(clusterPlace);
                    stu1.removeValue();
                    Intent intent = new Intent(EditClusterActivity.this, ListRiskAreaActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            Cluster cluster_edit = new Cluster( clusterDate,  clusterPlace,  clusterSubdistrict,  clusterDistrict,  clustergetwellpatient,  clusternewpatient,  clusterAllpatient,  Allpatientdistrict,  Allhealingdistrict,  Allgetwelldistrict,  clusterLat,  clusterLng);
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef_clusterEdit = database.getReference("admin001/cluster/" + district_name + "/" + clusterPlace);
            Query query1 = myRef_clusterEdit.orderByValue();
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        myRef_clusterEdit.child("clusterDate").setValue(cluster_edit.getClusterDate());
                        myRef_clusterEdit.child("clusterPlace").setValue(cluster_edit.getClusterPlace());
                        myRef_clusterEdit.child("clusterDistrict").setValue(cluster_edit.getClusterDistrict());
                        myRef_clusterEdit.child("clusterSubdistrict").setValue(cluster_edit.getClusterSubdistrict());
                        myRef_clusterEdit.child("cluster_All_patient").setValue(cluster_edit.getCluster_All_patient());
                        myRef_clusterEdit.child("cluster_getwell_patient").setValue(cluster_edit.getCluster_getwell_patient());
                        myRef_clusterEdit.child("cluster_news_patient").setValue(cluster_edit.getCluster_news_patient());
                        myRef_clusterEdit.child("cluster_Allgetwell_district").setValue(cluster_edit.getCluster_Allhealing_district());
                        myRef_clusterEdit.child("cluster_Allhealing_district").setValue(cluster_edit.getCluster_Allgetwell_district());
                        myRef_clusterEdit.child("cluster_Allpatient_district").setValue(cluster_edit.getCluster_Allpatient_district());
                        myRef_clusterEdit.child("clusterLat").setValue(cluster_edit.getClusterLat());
                        myRef_clusterEdit.child("clusterLng").setValue(cluster_edit.getClusterLng());

                        Intent intent = new Intent(EditClusterActivity.this, ListRiskAreaActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    public void ClickBTNEditCancel(View view) {
        Intent intent = new Intent(EditClusterActivity.this, ListRiskAreaActivity.class);
        startActivity(intent);
    }

    public void ClickGetlatlng(View view) {
        Intent intent = new Intent(EditClusterActivity.this, GetLatLngClusterActivity.class);
        startActivity(intent);
    }
}