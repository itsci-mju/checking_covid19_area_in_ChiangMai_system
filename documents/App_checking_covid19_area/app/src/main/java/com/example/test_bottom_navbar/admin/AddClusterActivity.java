package com.example.test_bottom_navbar.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test_bottom_navbar.Cluster;
import com.example.test_bottom_navbar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddClusterActivity extends AppCompatActivity {
    String Admin;
    int patientNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cluster);

        Intent intent = getIntent();
        Admin = intent.getStringExtra("Admin");
    }

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    public void ClickDateCluster(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddClusterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                EditText txtdate = findViewById(R.id.txtadd_datecluster);
                String day = checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month + 1));
                txtdate.setText(day + "-" + months + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void ClickBTNAddCluster(View view) {
        final EditText date = (EditText) findViewById(R.id.txtadd_datecluster);
        final EditText place = (EditText) findViewById(R.id.txtadd_place);
        final EditText subdistrict = (EditText) findViewById(R.id.txtadd_subdistrict);
        final EditText district = (EditText) findViewById(R.id.txtadd_district);
        final EditText newspatient = (EditText) findViewById(R.id.txtadd_newpatient);

        final EditText lat = (EditText) findViewById(R.id.txtLat);
        final EditText lng = (EditText) findViewById(R.id.txtLng);

        String clusterDate = date.getText().toString();
        String clusterPlace = place.getText().toString();
        String clusterSubdistrict = subdistrict.getText().toString();
        String clusterDistrict = district.getText().toString();
        String cluster_news_patient = newspatient.getText().toString();

        patientNum = Integer.parseInt(cluster_news_patient);


        String clusterLat = lat.getText().toString();
        String clusterLng = lng.getText().toString();


        if (clusterDate.equals("")) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก วันที่", Toast.LENGTH_SHORT).show();

        } else if (clusterPlace.equals("")) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก สถานที่", Toast.LENGTH_SHORT).show();

        } else if (clusterSubdistrict.equals("")) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ตำบล", Toast.LENGTH_SHORT).show();

        } else if (clusterDistrict.equals("")) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก อำเภอ", Toast.LENGTH_SHORT).show();

        } else if(cluster_news_patient.equals("")){
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ยอดผู้ติดเชื้อใหม่", Toast.LENGTH_SHORT).show();

        } else if(clusterLat.equals("")){
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ละติจูดของสถานที่", Toast.LENGTH_SHORT).show();

        } else if(clusterLng.equals("")){
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ลองจิจูดของสถานที่", Toast.LENGTH_SHORT).show();

        } else {

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/cluster");
            Query query1 = myRef.orderByKey().equalTo(clusterPlace);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String Error = "F";
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Error = "T";
                    }
                    if (Error.equals("F")) {
                        Cluster CT = new Cluster(clusterDate,clusterPlace,clusterSubdistrict,clusterDistrict,cluster_news_patient,clusterLat,clusterLng);
                        DatabaseReference stu1 = myRef.child(clusterPlace);
                        stu1.setValue(CT);
                        Intent intent = new Intent(AddClusterActivity.this, ListRiskAreaActivity.class);
                        intent.putExtra("clusterPlace", clusterPlace);
                        intent.putExtra("patient_number",patientNum);
                        Toast.makeText(AddClusterActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_LONG).show();
                        intent.putExtra("Admin", Admin);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddClusterActivity.this, "ชื่อสถานที่เกิดคลัสเตอร์ ซ้ำ", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    public void ClickBTNCancel (View view){
        Intent intent = new Intent(AddClusterActivity.this, Mainpage_admin.class);
        startActivity(intent);
    }

}