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
    String clusterPlace, district_name, patientCheck,SetDefault_Date;
    int patient_number;
    int patientNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cluster);
        clusterPlace = getIntent().getStringExtra("clusterPlace");
        district_name = getIntent().getStringExtra("district_name");
        System.out.println("////////////////////////" + clusterPlace);


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
                    //String clusterDate = ds.child("clusterDate").getValue().toString();
                    String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                    String clusterplace = ds.child("clusterPlace").getValue().toString();
                    clusterPlace = clusterplace;
                    String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                    String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();
                    String clusterLat = ds.child("clusterLat").getValue().toString();
                    String clusterLng = ds.child("clusterLng").getValue().toString();

                    TextView txtplace = findViewById(R.id.txtedit_place);
                    txtplace.setText(clusterPlace);

                    TextView txtsubdistrict = findViewById(R.id.txtedit_subdistrict);
                    txtsubdistrict.setText(clusterSubdistrict);

                    TextView txtdistrict = findViewById(R.id.txtedit_district);
                    txtdistrict.setText(clusterDistrict);

                    /*EditText txtdate = findViewById(R.id.txtedit_date);
                    txtdate.setText(clusterDate);*/

                    EditText txtnewpatient = findViewById(R.id.txtedit_newpatient);
                    txtnewpatient.setText(cluster_news_patient);

                    TextView txtlat = findViewById(R.id.txtedit_lat);
                    txtlat.setText(clusterLat);

                    TextView txtlng = findViewById(R.id.txtedit_lng);
                    txtlng.setText(clusterLng);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

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
        EditText txtnewpatient = findViewById(R.id.txtedit_newpatient);
        clusterToedit.setCluster_news_patient(txtnewpatient.getText().toString());

        TextView lat = findViewById(R.id.txtedit_lat);
        clusterToedit.setClusterLat(lat.getText().toString());
        TextView lng = findViewById(R.id.txtedit_lng);
        clusterToedit.setClusterLng(lng.getText().toString());

        String clusterdate = txtdate.getText().toString();
        String clusterplace = txtplace.getText().toString();
        String clustersubdistrict = txtsubdistrict.getText().toString();
        String clusterdistrict = txtdistrict.getText().toString();
        String clusternewpatient = txtnewpatient.getText().toString();

        patientCheck = clusternewpatient;

        String clusterLat = lat.getText().toString();
        String clusterLng = lng.getText().toString();

        patientNum = Integer.parseInt(clusternewpatient);

        if (patientCheck.equals("0")) {
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
            Cluster cluster_edit = new Cluster(clusterdate, clusterplace, clustersubdistrict, clusterdistrict, clusternewpatient, clusterLat, clusterLng);
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
                        myRef_clusterEdit.child("cluster_news_patient").setValue(cluster_edit.getCluster_news_patient());
                        myRef_clusterEdit.child("clusterLat").setValue(cluster_edit.getClusterLat());
                        myRef_clusterEdit.child("clusterLng").setValue(cluster_edit.getClusterLng());
                        Intent intent = new Intent(EditClusterActivity.this, ListRiskAreaActivity.class);
                        intent.putExtra("patient_number", patientNum);
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