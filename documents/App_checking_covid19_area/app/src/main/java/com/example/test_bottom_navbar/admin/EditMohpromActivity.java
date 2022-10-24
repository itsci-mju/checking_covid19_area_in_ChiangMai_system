package com.example.test_bottom_navbar.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.test_bottom_navbar.Cluster;
import com.example.test_bottom_navbar.Mohpromt;
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
import java.util.List;

public class EditMohpromActivity extends AppCompatActivity {
    String Admin;
    String StringMohpormtStartDate,StringMohpormtEndDate;
    int mHour,mMinute;
    String mohpromtPlace;
    private Spinner spinner;
    ArrayAdapter<String> dataAdapter;
    String[] type = {"คลินิก","ร้านยา","ร.พ","จุดจ่ายยา"};
    String StartTime_Seclect = "";
    String StartTime_NotSeclect = "";
    TextView btnDisplay ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mohprom);

        Intent intent = getIntent();
        Admin = intent.getStringExtra("Admin");

        FirebaseApp.initializeApp(this);
        mohpromtPlace = intent.getStringExtra("mohpromtPlace");
        this.getmohpromtToEdit();

        findRadioButton();
    }

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    public void ClickStartTime_Edit(View view) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(EditMohpromActivity.this, android.R.style.Theme_Holo_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mHour = i;
                mMinute = i1;
                String Hour = checklength(String.valueOf(i));
                String Minute = checklength(String.valueOf(i1));
                EditText et = findViewById(R.id.txtedit_mohpromtstartTime);
                et.setText(Hour + " : " + Minute + " น.");
            }
        }, mHour, mMinute, true);
        tpd.show();
    }

    public void ClickEndTime_Edit(View view) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(EditMohpromActivity.this, android.R.style.Theme_Holo_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mHour = i;
                mMinute = i1;
                String Hour = checklength(String.valueOf(i));
                String Minute = checklength(String.valueOf(i1));
                EditText et = findViewById(R.id.txtedit_mohpromtendTime);
                et.setText(Hour + " : " + Minute+ " น.");
            }
        }, mHour, mMinute, true);
        tpd.show();
    }

    public void getmohpromtToEdit(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/mohpromt");
        Query query1 = myRef.orderByKey().equalTo(mohpromtPlace);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String mohpromtplace = ds.child("mohpromtPlace").getValue().toString();
                    mohpromtPlace = mohpromtplace;
                    String mohpromtType = ds.child("mohpromtType").getValue().toString();
                    String mohpromtStartDate = ds.child("mohpromtStartDate").getValue().toString();
                    String mohpromtEndDate = ds.child("mohpromtEndDate").getValue().toString();
                    String mohpromtStartTime = ds.child("mohpromtStartTime").getValue().toString();
                    String mohpromtEndtime = ds.child("mohpromtEndTime").getValue().toString();
                    String mohpromtLat = ds.child("mohpromtLat").getValue().toString();
                    String mohpromtLng = ds.child("mohpromtLng").getValue().toString();
                    String mohpromtAddress = ds.child("mohpromtAddress").getValue().toString();
                    String mohpromtDetail = ds.child("mohpromtDetail").getValue().toString();

                    TextView txtmohpromtplace = findViewById(R.id.txtedit_mohpromtplace);
                    txtmohpromtplace.setText(mohpromtPlace);

                    TextView txtmohpromtype = findViewById(R.id.txtedit_mohpromtlisttype);
                    txtmohpromtype.setText(mohpromtType);

                    StartTime_Seclect = mohpromtStartDate;

                    StartTime_NotSeclect = mohpromtEndDate;

                    CheckSelectStartDate();

                    EditText txtmohpromtstarttime = findViewById(R.id.txtedit_mohpromtstartTime);
                    txtmohpromtstarttime.setText(mohpromtStartTime);

                    EditText txtmohpromtendtime = findViewById(R.id.txtedit_mohpromtendTime);
                    txtmohpromtendtime.setText(mohpromtEndtime);

                    TextView txtmohpromtlat = findViewById(R.id.txtedit_mohpromtLat);
                    txtmohpromtlat.setText(mohpromtLat);

                    TextView txtmohpromtlng = findViewById(R.id.txtedit_mohpromtLng);
                    txtmohpromtlng.setText(mohpromtLng);

                    EditText txtmohpromtaddress = findViewById(R.id.txtedit_mohpromtaddress);
                    txtmohpromtaddress.setText(mohpromtAddress);

                    TextView txtmohpromtdetail = findViewById(R.id.txtedit_mohpromtdetail);
                    txtmohpromtdetail.setText(mohpromtDetail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void ClickToEditMohpromt(){
        Mohpromt mohpromtToedit = new Mohpromt();
        TextView txteditMohpromplace = findViewById(R.id.txtedit_mohpromtplace);
        mohpromtToedit.setMohpromtPlace(txteditMohpromplace.getText().toString());
        TextView txteditMohpromType = findViewById(R.id.txtedit_mohpromtlisttype);
        mohpromtToedit.setMohpromtType(txteditMohpromType.getText().toString());

        EditText txteditMohpromStartTime = findViewById(R.id.txtedit_mohpromtstartTime);
        mohpromtToedit.setMohpromtStartTime(txteditMohpromStartTime.getText().toString());
        EditText txteditMohpromEndTime = findViewById(R.id.txtedit_mohpromtendTime);
        mohpromtToedit.setMohpromtEndTime(txteditMohpromEndTime.getText().toString());
        TextView txteditMohpromLat = findViewById(R.id.txtedit_mohpromtLat);
        mohpromtToedit.setMohpromtLat(txteditMohpromLat.getText().toString());
        TextView txteditMohpromLng = findViewById(R.id.txtedit_mohpromtLng);
        mohpromtToedit.setMohpromtLat(txteditMohpromLng.getText().toString());
        TextView txteditMohpromAddress = findViewById(R.id.txtedit_mohpromtaddress);
        mohpromtToedit.setMohpromtLat(txteditMohpromLng.getText().toString());
        TextView txteditMohpromDetail = findViewById(R.id.txtedit_mohpromtdetail);
        mohpromtToedit.setMohpromtDetail(txteditMohpromDetail.getText().toString());

        String mohpromplace = txteditMohpromplace.getText().toString();
        String mohpromtype = txteditMohpromType.getText().toString();
        String mohpromt_startdate = StartTime_Seclect;
        StringMohpormtStartDate = mohpromt_startdate;
        String mohpromt_enddate = StartTime_NotSeclect;
        StringMohpormtEndDate = mohpromt_enddate.substring(1);
        String mohpromstarttime = txteditMohpromStartTime.getText().toString();
        String mohpromendtime = txteditMohpromEndTime.getText().toString();
        String mohpromlat = txteditMohpromLat.getText().toString();
        String mohpormlng = txteditMohpromLng.getText().toString();
        String mohpromaddress = txteditMohpromAddress.getText().toString();
        String mohpromdetail = txteditMohpromDetail.getText().toString();

        Mohpromt mohpromt_edit = new Mohpromt(mohpromplace,mohpromtype,StringMohpormtStartDate,StringMohpormtEndDate,mohpromstarttime,mohpromendtime,mohpromlat,mohpormlng,mohpromaddress,mohpromdetail);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_clusterEdit = database.getReference("admin001/mohpromt/"+mohpromtPlace);
        Query query1 =  myRef_clusterEdit.orderByValue();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    myRef_clusterEdit.child("mohpromtPlace").setValue(mohpromt_edit.getMohpromtPlace());
                    myRef_clusterEdit.child("mohpromtType").setValue(mohpromt_edit.getMohpromtType());
                    myRef_clusterEdit.child("mohpromtStartDate").setValue(mohpromt_edit.getMohpromtStartDate());
                    myRef_clusterEdit.child("mohpromtEndDate").setValue(mohpromt_edit.getMohpromtEndDate());
                    myRef_clusterEdit.child("mohpromtStartTime").setValue(mohpromt_edit.getMohpromtStartTime());
                    myRef_clusterEdit.child("mohpromtEndTime").setValue(mohpromt_edit.getMohpromtEndTime());
                    myRef_clusterEdit.child("mohpromtLat").setValue(mohpromt_edit.getMohpromtLat());
                    myRef_clusterEdit.child("mohpromtLng").setValue(mohpromt_edit.getMohpromtLng());
                    myRef_clusterEdit.child("mohpromtAddress").setValue(mohpromt_edit.getMohpromtAddress());
                    myRef_clusterEdit.child("mohpromtDetail").setValue(mohpromt_edit.getMohpromtDetail());

                    Intent intent = new Intent(EditMohpromActivity.this, ListMohpromActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void CheckSelectStartDate() {
        System.out.println("--------------------------------------------" + StartTime_Seclect);
        for (int i = 0; i < StartTime_Seclect.length(); i++) {
            String[] arr = StartTime_Seclect.split(",");
            for (int u = 0; u < arr.length; u++) {
                if (arr[u].equals("จ.")) {
                    CheckBox checkmon = findViewById(R.id.checkmon);
                    checkmon.setChecked(true);
                }if (arr[u].equals("อ.")) {
                    CheckBox checkmon = findViewById(R.id.checktue);
                    checkmon.setChecked(true);
                }if (arr[u].equals("พ.")) {
                    CheckBox checkmon = findViewById(R.id.checkwed);
                    checkmon.setChecked(true);
                }if (arr[u].equals("พฤ.")) {
                    CheckBox checkmon = findViewById(R.id.checkthu);
                    checkmon.setChecked(true);
                }if (arr[u].equals("ศ.")) {
                    CheckBox checkmon = findViewById(R.id.checkfri);
                    checkmon.setChecked(true);
                }if (arr[u].equals("ส.")) {
                    CheckBox checkmon = findViewById(R.id.checksat);
                    checkmon.setChecked(true);
                }if (arr[u].equals("อา.")) {
                    CheckBox checkmon = findViewById(R.id.checksun);
                    checkmon.setChecked(true);
                }
            }
        }
    }

    private void findRadioButton() {
        CheckBox Mon,tues,wednes,thurs,frid,satur,sun;
        Mon = (CheckBox) findViewById(R.id.checkmon);
        tues = (CheckBox) findViewById(R.id.checktue);
        wednes = (CheckBox) findViewById(R.id.checkwed);
        thurs = (CheckBox) findViewById(R.id.checkthu);
        frid = (CheckBox) findViewById(R.id.checkfri);
        satur = (CheckBox) findViewById(R.id.checksat);
        sun = (CheckBox) findViewById(R.id.checksun);
        btnDisplay = (TextView) findViewById(R.id.button_editMohpromt);
        String m = Mon.getText().toString();
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                StartTime_Seclect = "";
                StartTime_NotSeclect = "";

                if(Mon.isChecked()){
                    String mo = Mon.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+mo;
                }else{
                    String mo = Mon.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+mo;
                }
                if(tues.isChecked()){
                    String tu = tues.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+","+tu;
                }else{
                    String tu = tues.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+","+tu;
                }
                if(wednes.isChecked()){
                    String w = wednes.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+","+w;
                }else{
                    String w = wednes.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+","+w;
                }
                if(thurs.isChecked()){
                    String th = thurs.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+","+th;
                }else{
                    String th = thurs.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+","+th;
                }
                if(frid.isChecked()){
                    String f = frid.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+","+f;
                }else{
                    String f = frid.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+","+f;
                }
                if(satur.isChecked()){
                    String sa = satur.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+","+sa;
                }else{
                    String sa = satur.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+","+sa;
                }
                if(sun.isChecked()){
                    String s = sun.getText().toString();
                    StartTime_Seclect = StartTime_Seclect+","+s;
                }else{
                    String s = sun.getText().toString();
                    StartTime_NotSeclect = StartTime_NotSeclect+","+s;
                }
                Log.e("-------------------------------------STS",StartTime_Seclect);
                Log.e("-------------------------------------STNS",StartTime_NotSeclect);
                ClickToEditMohpromt();
            }
        });
    }

    public void ClickBTNEditMohpromtCancel (View view){
        Intent intent = new Intent(EditMohpromActivity.this, ListMohpromActivity.class);
        startActivity(intent);
    }



}