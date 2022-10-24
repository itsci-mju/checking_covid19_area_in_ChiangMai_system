package com.example.test_bottom_navbar.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.test_bottom_navbar.Mohpromt;
import com.example.test_bottom_navbar.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMohpromtActivity extends AppCompatActivity {
    String Admin,StringMohpormtStartDate,StringMohpormtEndDate;
    String[] SetDefault_Date;
    Mohpromt mohpromt_startdate;
    Mohpromt mohpromt_enddate;
    int mHour;
    double Mohpromt_Lat,Mohpromt_Lng;
    int mMinute;
    String StartTime_Seclect = "",Get_StartDate_Seclect;
    String StartTime_NotSeclect = "",Get_StartTime_NotSeclect = "";
    private Spinner spinner;
    ArrayAdapter<String> dataAdapter;
    TextView  txttime1 ,txttime2 ,txttime3 ,txttime4 ,txttime5 ;
    RadioButton S_mon,S_tue,S_wed,S_thu,S_fri,S_sat,S_sun;
    RadioButton E_mon,E_tue,E_wed,E_thu,E_fri,E_sat,E_sun;
    String[] type = {"คลินิก","ร้านยา","ร.พ","จุดจ่ายยา"};
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    ImageView btnDisplay ;
    TextView btnDisplayadd;
    String Mplace_def,Mstarttime_def,Mendtime_def,Mdetail_def;
    String[] mohpromtDetail = {"จุดตรวจATK","จุดจ่ายยา","จุดฉีดวัคซีน","จุดฉีดวัคซีน/จุดตรวจATK"};
    ArrayAdapter<String> dataAdapter_mohpromtDetail;
    private Spinner spinner_mohpromtDetail;
    int tms,mdt;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mohprom);

        Intent intentLatLng = getIntent();
        Mohpromt_Lat = intentLatLng.getDoubleExtra("Mohpromt_Lat",Mohpromt_Lat);
        Mohpromt_Lng = intentLatLng.getDoubleExtra("Mohpromt_Lng",Mohpromt_Lng);

        Intent intentget = getIntent();
        Mplace_def = intentget.getStringExtra("Mplace_def");
        Mstarttime_def = intentget.getStringExtra("Mstarttime_def");
        Mendtime_def = intentget.getStringExtra("Mendtime_def");
        Mdetail_def = intentget.getStringExtra("Mdetail_def");

        Get_StartDate_Seclect = intentget.getStringExtra("Get_StartDate_Seclect");
        System.out.println("////////////////////////////////////////AMG:"+Get_StartDate_Seclect);

        tms = intentget.getIntExtra("tms",tms);
        Log.e("////////////////////////////////////////AM", String.valueOf(tms));

        String String_Mplace_def = Mplace_def;
        String String_Mstartdate_def = Mstarttime_def;
        String String_Menddate_def = Mendtime_def;
        String String_Mdetail_def = Mdetail_def;

        findRadioButton();
        CheckSelectStartDate();

        EditText mohpromtplace = findViewById(R.id.txtadd_mohpromtplace);
        mohpromtplace.setText(String_Mplace_def);

        EditText mohpromtstartdate = findViewById(R.id.mohpromtstartTime);
        mohpromtstartdate.setText(String_Mstartdate_def);

        EditText mohpromtenddate = findViewById(R.id.mohpromtendTime);
        mohpromtenddate.setText(String_Menddate_def);

        spinner_mohpromtDetail = findViewById(R.id.mohpromtdetail);
        spinner_mohpromtDetail.setSelection(mdt);

        spinner = findViewById(R.id.mohpromtlisttype);
        spinner.setSelection(tms);

        if (Mohpromt_Lat != 0.0 && Mohpromt_Lng != 0.0) {
            this.GetLatLngFromMap();
        }

        Intent intent = getIntent();
        Admin = intent.getStringExtra("Admin");


        spinner = findViewById(R.id.mohpromtlisttype);
        dataAdapter = new ArrayAdapter<String>(AddMohpromtActivity.this, android.R.layout.simple_spinner_dropdown_item,type);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(tms);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tms = spinner.getSelectedItemPosition();
                spinner.setSelection(tms);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_mohpromtDetail = findViewById(R.id.mohpromtdetail);
        dataAdapter_mohpromtDetail = new ArrayAdapter<String>(AddMohpromtActivity.this,android.R.layout.simple_spinner_dropdown_item,mohpromtDetail);
        spinner_mohpromtDetail.setAdapter(dataAdapter_mohpromtDetail);
        spinner_mohpromtDetail.setSelection(mdt);
        spinner_mohpromtDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mdt = spinner_mohpromtDetail.getSelectedItemPosition();
                spinner_mohpromtDetail.setSelection(mdt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void CheckSelectStartDate(){
        System.out.println("--------------------------------------------" + Get_StartDate_Seclect);
        if(Get_StartDate_Seclect != null) {
            for (int i = 0; i < Get_StartDate_Seclect.length(); i++) {
                String[] arr = Get_StartDate_Seclect.split(",");
                for (int u = 0; u < arr.length; u++) {
                    if (arr[u].equals("จ.")) {
                        CheckBox checkmon = findViewById(R.id.radioButton_starttime1);
                        checkmon.setChecked(true);
                    }
                    if (arr[u].equals("อ.")) {
                        CheckBox checkmon = findViewById(R.id.radioButton_starttime2);
                        checkmon.setChecked(true);
                    }
                    if (arr[u].equals("พ.")) {
                        CheckBox checkmon = findViewById(R.id.radioButton_starttime3);
                        checkmon.setChecked(true);
                    }
                    if (arr[u].equals("พฤ.")) {
                        CheckBox checkmon = findViewById(R.id.radioButton_starttime4);
                        checkmon.setChecked(true);
                    }
                    if (arr[u].equals("ศ.")) {
                        CheckBox checkmon = findViewById(R.id.radioButton_starttime5);
                        checkmon.setChecked(true);
                    }
                    if (arr[u].equals("ส.")) {
                        CheckBox checkmon = findViewById(R.id.radioButton_starttime6);
                        checkmon.setChecked(true);
                    }
                    if (arr[u].equals("อา.")) {
                        CheckBox checkmon = findViewById(R.id.radioButton_starttime7);
                        checkmon.setChecked(true);
                    }
                }
            }
        }
    }

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
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
                String Hour = checklength(String.valueOf(i));
                String Minute = checklength(String.valueOf(i1));
                EditText et = findViewById(R.id.mohpromtstartTime);
                et.setText(Hour + " : " + Minute + " น.");
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
                String Hour = checklength(String.valueOf(i));
                String Minute = checklength(String.valueOf(i1));
                EditText et = findViewById(R.id.mohpromtendTime);
                et.setText(Hour + " : " + Minute + " น.");

            }
        }, mHour, mMinute, true);
        tpd.show();
    }

    @SuppressLint("LongLogTag")
    public void ClickAddMohpromt() {
        mohpromt_startdate = new Mohpromt();
        mohpromt_enddate = new Mohpromt();
        final EditText mohpromtplace = (EditText) findViewById(R.id.txtadd_mohpromtplace);
        final Spinner listtype = (Spinner) findViewById(R.id.mohpromtlisttype);
        //final LinearLayout mohpromstartdate = (LinearLayout) findViewById(R.id.txt_mohpromtDate);
        //final LinearLayout mohpromenddate = (LinearLayout) findViewById(R.id.txtadd_mohpromtEndDate);
        final EditText starttime = (EditText) findViewById(R.id.mohpromtstartTime);
        final EditText endtime = (EditText) findViewById(R.id.mohpromtendTime);
        final EditText mohpromtlat = (EditText) findViewById(R.id.mohpromtLat);
        final EditText mohpromtlng = (EditText) findViewById(R.id.mohpromtLng);
        final EditText mohpromtaddress = (EditText) findViewById(R.id.mohpromtaddress);
        final Spinner mohpromtdetail = (Spinner) findViewById(R.id.mohpromtdetail);


        CheckBox Mon,tues,wednes,thurs,frid,satur,sun;
        Mon = (CheckBox) findViewById(R.id.radioButton_starttime1);
        tues = (CheckBox) findViewById(R.id.radioButton_starttime2);
        wednes = (CheckBox) findViewById(R.id.radioButton_starttime3);
        thurs = (CheckBox) findViewById(R.id.radioButton_starttime4);
        frid = (CheckBox) findViewById(R.id.radioButton_starttime5);
        satur = (CheckBox) findViewById(R.id.radioButton_starttime6);
        sun = (CheckBox) findViewById(R.id.radioButton_starttime7);

        String mohpromt_place = mohpromtplace.getText().toString();
        String mohpromt_listype = listtype.getSelectedItem().toString();
        String mohpromt_startdate = StartTime_Seclect;
        StringMohpormtStartDate = mohpromt_startdate;
        Log.e("-----------------------------------------------------",StringMohpormtStartDate);
        String mohpromt_enddate = StartTime_NotSeclect;
        StringMohpormtEndDate = mohpromt_enddate.substring(1);
        Log.e("-----------------------------------------------------",StringMohpormtEndDate);
        String mohpromt_starttime = starttime.getText().toString();
        String mohpromt_endtime = endtime.getText().toString();
        String mohpromt_lat = mohpromtlat.getText().toString();
        String mohpromt_lng = mohpromtlng.getText().toString();
        String mohpromt_address = mohpromtaddress.getText().toString();
        String mohpromt_detail = mohpromtdetail.getSelectedItem().toString();

        //Check Error
        if (mohpromt_place.equals("")) {
            Toast.makeText(AddMohpromtActivity.this, "กรุณากรอกชื่อ", Toast.LENGTH_SHORT).show();
        }else if(!Mon.isChecked()&&!tues.isChecked()&&!wednes.isChecked()&&!thurs.isChecked()&&!frid.isChecked()&&!satur.isChecked()&&!sun.isChecked()){
            Toast.makeText(AddMohpromtActivity.this, "กรุณาเลือกวันเปิดให้บริการ", Toast.LENGTH_SHORT).show();
        }else if(mohpromt_starttime.equals("")){
            Toast.makeText(AddMohpromtActivity.this, "กรุณาเลือกเวลาเปิดให้บริการ", Toast.LENGTH_SHORT).show();
        } else if(mohpromt_endtime.equals("")){
            Toast.makeText(AddMohpromtActivity.this, "กรุณาเลือกเวลาปิดให้บริการ", Toast.LENGTH_SHORT).show();
        } else if(mohpromt_lat.equals("")) {
            Toast.makeText(AddMohpromtActivity.this, "กรุณากรอก ละติจูดของสถานที่", Toast.LENGTH_SHORT).show();
        } else if(mohpromt_lng.equals("")) {
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
                        Mohpromt MT = new Mohpromt(mohpromt_place,mohpromt_listype,StringMohpormtStartDate,StringMohpormtEndDate,
                                mohpromt_starttime, mohpromt_endtime,mohpromt_lat,mohpromt_lng,mohpromt_detail,mohpromt_address);
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

    public void GetLatLngFromMap(){
        String M_lat,M_lng;
        M_lat = Double.toString(Mohpromt_Lat);
        M_lng = Double.toString(Mohpromt_Lng);
        TextView txtlat = findViewById(R.id.mohpromtLat);
        txtlat.setText(M_lat);
        TextView txtlng = findViewById(R.id.mohpromtLng);
        txtlng.setText(M_lng);
    }

    @SuppressLint("LongLogTag")
    private void findRadioButton() {
        CheckBox Mon,tues,wednes,thurs,frid,satur,sun;
        Mon = (CheckBox) findViewById(R.id.radioButton_starttime1);
        tues = (CheckBox) findViewById(R.id.radioButton_starttime2);
        wednes = (CheckBox) findViewById(R.id.radioButton_starttime3);
        thurs = (CheckBox) findViewById(R.id.radioButton_starttime4);
        frid = (CheckBox) findViewById(R.id.radioButton_starttime5);
        satur = (CheckBox) findViewById(R.id.radioButton_starttime6);
        sun = (CheckBox) findViewById(R.id.radioButton_starttime7);
        btnDisplayadd = (TextView) findViewById(R.id.button_addMohpromt);
        String m = Mon.getText().toString();
        Log.e("--------------------------------------------------------------ddddd",m);
        btnDisplayadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Mon.isChecked()){
                    String mo = Mon.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+mo;
                    Log.e("--------------------------------------------------------------1",StartTime_Seclect);
                }else{
                    String mo = Mon.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+mo;
                }
                if(tues.isChecked()){
                    String tu = tues.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+","+tu;
                    Log.e("--------------------------------------------------------------2",StartTime_Seclect);
                }else{
                    String tu = tues.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+","+tu;
                }
                if(wednes.isChecked()){
                    String w = wednes.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+","+w;
                    Log.e("--------------------------------------------------------------3",StartTime_Seclect);
                }else{
                    String w = wednes.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+","+w;
                }
                if(thurs.isChecked()){
                    String th = thurs.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+","+th;
                    Log.e("--------------------------------------------------------------4",StartTime_Seclect);
                }else{
                    String th = thurs.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+","+th;
                }
                if(frid.isChecked()){
                    String f = frid.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+","+f;
                    Log.e("--------------------------------------------------------------5",StartTime_Seclect);
                }else{
                    String f = frid.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+","+f;
                }
                if(satur.isChecked()){
                    String sa = satur.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+","+sa;
                    Log.e("--------------------------------------------------------------6",StartTime_Seclect);
                }else{
                    String sa = satur.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+","+sa;
                }
                if(sun.isChecked()){
                    String s = sun.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+","+s;
                    Log.e("--------------------------------------------------------------7",StartTime_Seclect);
                }else{
                    String s = sun.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+","+s;
                }
                Log.e("-------------------------------------STS",StartTime_Seclect);
                Log.e("-------------------------------------STNS",StartTime_NotSeclect);
                ClickAddMohpromt();
            }
        });
    }

    public void ClickCancelAddMohprom (View view){
        Intent intent = new Intent(AddMohpromtActivity.this, Mainpage_admin.class);
        startActivity(intent);
    }

    @SuppressLint("LongLogTag")
    public void ClickGetlatlng_mohpromt (View view){
        EditText mohpromtplace = findViewById(R.id.txtadd_mohpromtplace);
        EditText starttime = findViewById(R.id.mohpromtstartTime);
        EditText endtime = findViewById(R.id.mohpromtendTime);
        //EditText mohpromtdetail = findViewById(R.id.mohpromtdetail);

        GetSelectCheckBoxDate();

        String mohpromt_startdate = StartTime_Seclect;
        Get_StartDate_Seclect = mohpromt_startdate;

        Mplace_def =  mohpromtplace.getText().toString();
        Mstarttime_def = starttime.getText().toString();
        Mendtime_def = endtime.getText().toString();
        //Mdetail_def = mohpromtdetail.getText().toString();

        Intent intent = new Intent(AddMohpromtActivity.this, GetLatLngMohpromtActivity.class);
        intent.putExtra("Mplace_def",Mplace_def);
        intent.putExtra("Mstarttime_def",Mstarttime_def);
        intent.putExtra("Mendtime_def",Mendtime_def);
        intent.putExtra("Mdetail_def",Mdetail_def);
        intent.putExtra("tms",tms);
        intent.putExtra("Get_StartDate_Seclect",Get_StartDate_Seclect);

        startActivity(intent);
    }

    @SuppressLint("LongLogTag")
    public void GetSelectCheckBoxDate() {
        CheckBox Mon, tues, wednes, thurs, frid, satur, sun;
        Mon = (CheckBox) findViewById(R.id.radioButton_starttime1);
        tues = (CheckBox) findViewById(R.id.radioButton_starttime2);
        wednes = (CheckBox) findViewById(R.id.radioButton_starttime3);
        thurs = (CheckBox) findViewById(R.id.radioButton_starttime4);
        frid = (CheckBox) findViewById(R.id.radioButton_starttime5);
        satur = (CheckBox) findViewById(R.id.radioButton_starttime6);
        sun = (CheckBox) findViewById(R.id.radioButton_starttime7);

        StartTime_Seclect = "";
        StartTime_NotSeclect = "";

                if (Mon.isChecked()) {
                    String mo = Mon.getText().toString();
                    StartTime_Seclect = StartTime_Seclect + mo;
                } else {
                    String mo = Mon.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect + mo;
                }
                if (tues.isChecked()) {
                    String tu = tues.getText().toString();
                    StartTime_Seclect = StartTime_Seclect + "," + tu;
                } else {
                    String tu = tues.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect + "," + tu;
                }
                if (wednes.isChecked()) {
                    String w = wednes.getText().toString();
                    StartTime_Seclect = StartTime_Seclect + "," + w;
                } else {
                    String w = wednes.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect + "," + w;
                }
                if (thurs.isChecked()) {
                    String th = thurs.getText().toString();
                    StartTime_Seclect = StartTime_Seclect + "," + th;
                } else {
                    String th = thurs.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect + "," + th;
                }
                if (frid.isChecked()) {
                    String f = frid.getText().toString();
                    StartTime_Seclect = StartTime_Seclect + "," + f;
                } else {
                    String f = thurs.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect + "," + f;
                }
                if (satur.isChecked()) {
                    String sa = satur.getText().toString();
                    StartTime_Seclect = StartTime_Seclect + "," + sa;
                } else {
                    String sa = satur.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect + "," + sa;
                }
                if (sun.isChecked()) {
                    String s = sun.getText().toString();
                    StartTime_Seclect = StartTime_Seclect + "," + s;
                } else {
                    String s = sun.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect + "," + s;
                }
                Log.e("-------------------------------------STS", StartTime_Seclect);
                Log.e("-------------------------------------STNS", StartTime_NotSeclect);
            }

}


