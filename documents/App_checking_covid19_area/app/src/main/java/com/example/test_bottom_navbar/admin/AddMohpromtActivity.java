package com.example.test_bottom_navbar.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    Mohpromt mohpromt_startdate;
    Mohpromt mohpromt_enddate;
    int mHour;
    int mMinute;
    private Spinner spinner,spinner2;
    ArrayAdapter<String> dataAdapter;
    RadioButton S_mon,S_tue,S_wed,S_thu,S_fri,S_sat,S_sun;
    RadioButton E_mon,E_tue,E_wed,E_thu,E_fri,E_sat,E_sun;
    String[] type = {"คลินิก","ร้านยา","ร.พ","จุดจ่ายยา"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mohprom);
        Intent intent = getIntent();
        Admin = intent.getStringExtra("Admin");

        spinner = findViewById(R.id.mohpromtlisttype);
        dataAdapter = new ArrayAdapter<String>(AddMohpromtActivity.this, android.R.layout.simple_spinner_dropdown_item,type);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddMohpromtActivity.this, "เลือก "+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
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

        mohpromt_startdate = new Mohpromt();
        mohpromt_enddate = new Mohpromt();

        final EditText mohpromtplace = (EditText) findViewById(R.id.txtadd_mohpromtplace);
        final Spinner listtype = (Spinner) findViewById(R.id.mohpromtlisttype);
        final LinearLayout mohpromstartdate = (LinearLayout) findViewById(R.id.txtadd_mohpromtStartDate);
        final  LinearLayout mohpromenddate = (LinearLayout) findViewById(R.id.txtadd_mohpromtEndDate);

        /*//RadioButton StartDate
        S_mon = findViewById(R.id.start_mon);
        S_tue = findViewById(R.id.start_tue);
        S_wed = findViewById(R.id.start_wed);
        S_thu = findViewById(R.id.start_thu);
        S_fri = findViewById(R.id.start_fri);
        S_sat = findViewById(R.id.start_sat);
        S_sun = findViewById(R.id.start_sun);
        //RadioButton EndDate
        E_mon = findViewById(R.id.end_mon);
        E_tue = findViewById(R.id.end_tue);
        E_wed = findViewById(R.id.end_wed);
        E_thu = findViewById(R.id.end_thu);
        E_fri = findViewById(R.id.end_fri);
        E_sat = findViewById(R.id.end_sat);
        E_sun = findViewById(R.id.end_sun);*/

        final EditText starttime = (EditText) findViewById(R.id.mohpromtstartTime);
        final EditText endtime = (EditText) findViewById(R.id.mohpromtendTime);
        final EditText mohpromtlat = (EditText) findViewById(R.id.mohpromtLat);
        final EditText mohpromtlng = (EditText) findViewById(R.id.mohpromtLng);
        final EditText mohpromtdetail = (EditText) findViewById(R.id.mohpromtdetail);

        String mohpromt_place = mohpromtplace.getText().toString();
        String mohpromt_listype = listtype.getSelectedItem().toString();
        String mohpromt_startdate = mohpromstartdate.toString();
        String mohpromt_enddate = mohpromenddate.toString();

        /*//GetText StartDate
        String smon = S_mon.getText().toString();
        String stue = S_tue.getText().toString();
        String swed = S_wed.getText().toString();
        String sthu = S_thu.getText().toString();
        String sfri = S_fri.getText().toString();
        String ssat = S_sat.getText().toString();
        String ssun = S_sun.getText().toString();
        //GetText EndDate
        String emon = E_mon.getText().toString();
        String etue = E_tue.getText().toString();
        String ewed = E_wed.getText().toString();
        String ethu = E_thu.getText().toString();
        String efri = E_fri.getText().toString();
        String esat = E_sat.getText().toString();
        String esun = E_sun.getText().toString();*/

        String mohpromt_starttime = starttime.getText().toString();
        String mohpromt_endtime = endtime.getText().toString();
        String mohpromt_lat = mohpromtlat.getText().toString();
        String mohpromt_lng = mohpromtlng.getText().toString();
        String mohpromt_detail = mohpromtdetail.getText().toString();

        /*//set StartDate
        if(S_mon.isChecked()){
            mohpromt_startdate.setMohpromtStartDate(smon);
        }else if(S_tue.isChecked()){
            mohpromt_startdate.setMohpromtStartDate(stue);
        }else if(S_wed.isChecked()){
            mohpromt_startdate.setMohpromtStartDate(swed);
        }else if(S_thu.isChecked()){
            mohpromt_startdate.setMohpromtStartDate(sthu);
        }else if(S_fri.isChecked()){
            mohpromt_startdate.setMohpromtStartDate(sfri);
        }else if(S_sat.isChecked()){
            mohpromt_startdate.setMohpromtStartDate(ssat);
        }else if(S_sun.isChecked()) {
            mohpromt_startdate.setMohpromtStartDate(ssun);
        }

        //set EndDate
        if(E_mon.isChecked()){
            mohpromt_enddate.setMohpromtStartDate(emon);
        }else if(E_tue.isChecked()){
            mohpromt_enddate.setMohpromtStartDate(etue);
        }else if(E_wed.isChecked()){
            mohpromt_enddate.setMohpromtStartDate(ewed);
        }else if(E_thu.isChecked()){
            mohpromt_enddate.setMohpromtStartDate(ethu);
        }else if(E_fri.isChecked()){
            mohpromt_enddate.setMohpromtStartDate(efri);
        }else if(E_sat.isChecked()){
            mohpromt_enddate.setMohpromtStartDate(esat);
        }else if(E_sun.isChecked()) {
            mohpromt_enddate.setMohpromtStartDate(esun);
        }*/

        //Check Error
        if (mohpromt_place.equals("")) {
            Toast.makeText(AddMohpromtActivity.this, "กรุณากรอกชื่อ", Toast.LENGTH_SHORT).show();
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