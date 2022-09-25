package com.example.test_bottom_navbar.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.test_bottom_navbar.Cluster;
import com.example.test_bottom_navbar.Mohpromt;
import com.example.test_bottom_navbar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddMohpromtActivity extends AppCompatActivity {
    String Admin;
    int mHour;
    int mMinute;
    private Spinner spinner,spinner2;
    ArrayAdapter<String> dataAdapter;
    String[] type = {"คลินิก","ร้านยา","ร.พ","จุดจ่ายยา"};
    String[] enddate = {"จ","อ","พ","พฤ","ศ","ส","อา"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mohprom);

        Intent intent = getIntent();
        Admin = intent.getStringExtra("Admin");

        spinner = findViewById(R.id.mohpromtlisttype);
        dataAdapter = new ArrayAdapter<String>(AddMohpromtActivity.this, android.R.layout.simple_spinner_item,type);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddMohpromtActivity.this, "เลือก "+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2 = findViewById(R.id.mohpromtlistEndDate);
        dataAdapter = new ArrayAdapter<String>(AddMohpromtActivity.this, android.R.layout.simple_spinner_item,enddate);
        spinner2.setAdapter(dataAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddMohpromtActivity.this, "เลือก "+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void ClickStartTime(View view) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(AddMohpromtActivity.this, android.R.style.Theme_Holo_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mHour = i;
                mMinute = i1;
                EditText et = findViewById(R.id.mohpromtstartTime);
                et.setText(i + ":" + i1 + "น.");

            }
        }, mHour, mMinute, true);
        tpd.show();
    }

    public void ClickEndTime(View view) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(AddMohpromtActivity.this, android.R.style.Theme_Holo_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mHour = i;
                mMinute = i1;
                EditText et = findViewById(R.id.mohpromtendTime);
                et.setText(i + ":" + i1+ "น.");

            }
        }, mHour, mMinute, true);
        tpd.show();
    }


    public void ClickAddMohpromt(View view) {
        final EditText mohpromtplace = (EditText) findViewById(R.id.txtadd_mohpromtplace);
        final Spinner listtype = (Spinner) findViewById(R.id.mohpromtlisttype);
        final EditText startdate = (EditText) findViewById(R.id.mohpromtStartDate);
        final Spinner enddate = (Spinner) findViewById(R.id.mohpromtlistEndDate);
        final EditText starttime = (EditText) findViewById(R.id.mohpromtstartTime);
        final EditText endtime = (EditText) findViewById(R.id.mohpromtendTime);
        final EditText mohpromtlat = (EditText) findViewById(R.id.mohpromtLat);
        final EditText mohpromtlng = (EditText) findViewById(R.id.mohpromtLng);
        final EditText mohpromtdetail = (EditText) findViewById(R.id.mohpromtdetail);

        String mohpromt_place = mohpromtplace.getText().toString();
        String mohpromt_listype = listtype.getSelectedItem().toString();
        String mohpromt_startdate = startdate.getText().toString();
        String mohpromt_enddate = enddate.getSelectedItem().toString();
        String mohpromt_starttime = starttime.getText().toString();
        String mohpromt_endtime = endtime.getText().toString();
        String mohpromt_lat = mohpromtlat.getText().toString();
        String mohpromt_lng = mohpromtlng.getText().toString();
        String mohpromt_detail = mohpromtdetail.getText().toString();

        if (mohpromt_place.equals("")) {
            Toast.makeText(AddMohpromtActivity.this, "กรุณากรอกชื่อ", Toast.LENGTH_SHORT).show();

        } else if (mohpromt_startdate.equals("")) {
            Toast.makeText(AddMohpromtActivity.this, "กรุณากรอก เวลาเปิด", Toast.LENGTH_SHORT).show();

        } else if (mohpromt_enddate.equals("")) {
            Toast.makeText(AddMohpromtActivity.this, "กรุณากรอก เวลาปิด", Toast.LENGTH_SHORT).show();

        } else if(mohpromt_starttime.equals("")){
            Toast.makeText(AddMohpromtActivity.this, "กรุณากรอก ยอดผู้ติดเชื้อใหม่", Toast.LENGTH_SHORT).show();

        } else if(mohpromt_endtime.equals("")){
            Toast.makeText(AddMohpromtActivity.this, "กรุณากรอก ละติจูดของสถานที่", Toast.LENGTH_SHORT).show();

        } else if(mohpromt_lat.equals("")) {
            Toast.makeText(AddMohpromtActivity.this, "กรุณากรอก ลองจิจูดของสถานที่", Toast.LENGTH_SHORT).show();

        } else if(mohpromt_lng.equals("")) {
            Toast.makeText(AddMohpromtActivity.this, "กรุณากรอก ลองจิจูดของสถานที่", Toast.LENGTH_SHORT).show();

        } else if(mohpromt_detail.equals("")){
            Toast.makeText(AddMohpromtActivity.this, "กรุณากรอก ลองจิจูดของสถานที่", Toast.LENGTH_SHORT).show();
        } else {

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/mohpromt");
            Query query1 = myRef.orderByKey().equalTo(mohpromt_place);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String Error = "F";
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Error = "T";
                    }
                    if (Error.equals("F")) {
                        Mohpromt MT = new Mohpromt(mohpromt_place,mohpromt_listype,mohpromt_startdate,mohpromt_enddate,
                                mohpromt_starttime, mohpromt_endtime,mohpromt_lat,mohpromt_lng,mohpromt_detail);
                        DatabaseReference stu1 = myRef.child(mohpromt_place);
                        stu1.setValue(MT);
                        Intent intent = new Intent(AddMohpromtActivity.this, ListMohpromActivity.class);
                        intent.putExtra("mohpromtPlace", mohpromt_place);
                        Toast.makeText(AddMohpromtActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_LONG).show();
                        intent.putExtra("Admin", Admin);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddMohpromtActivity.this, "ชื่อสถานที่ ซ้ำ กรุณากรอกใหม่", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    public void ClickCancelAddMohprom (View view){
        Intent intent = new Intent(AddMohpromtActivity.this, Mainpage_admin.class);
        startActivity(intent);
    }


}