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
    int mHour,mMinute;
    String mohpromtPlace;
    private Spinner spinner;
    ArrayAdapter<String> dataAdapter;
    String[] type = {"คลินิก","ร้านยา","ร.พ","จุดจ่ายยา"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mohprom);

        Intent intent = getIntent();
        Admin = intent.getStringExtra("Admin");

        FirebaseApp.initializeApp(this);
        mohpromtPlace = intent.getStringExtra("mohpromtPlace");
        this.getmohpromtToEdit();

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
                EditText et = findViewById(R.id.mohpromtstartTime);
                et.setText(i + ":" + i1 + "น.");
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
                EditText et = findViewById(R.id.mohpromtendTime);
                et.setText(i + ":" + i1+ "น.");

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
                    String mohpromtDetail = ds.child("mohpromtDetail").getValue().toString();
                     
                    TextView txtmohpromtplace = findViewById(R.id.txtedit_mohpromtplace);
                    txtmohpromtplace.setText(mohpromtPlace);

                    TextView txtmohpromtype = findViewById(R.id.txtedit_mohpromtlisttype);
                    txtmohpromtype.setText(mohpromtType);

                    /*Spinner txtmohpromtstartdate = findViewById(R.id.txtedit_mohpromtlistStartDate);
                    txtmohpromtstartdate.getSelectedItem().toString();

                    Spinner txtmohpromtenddate = findViewById(R.id.txtedit_mohpromtlistEndDate);
                    txtmohpromtenddate.getSelectedItem().toString();*/

                    EditText txtmohpromtstarttime = findViewById(R.id.txtedit_mohpromtstartTime);
                    txtmohpromtstarttime.setText(mohpromtStartTime);

                    EditText txtmohpromtendtime = findViewById(R.id.txtedit_mohpromtendTime);
                    txtmohpromtendtime.setText(mohpromtEndtime);

                    TextView txtmohpromtlat = findViewById(R.id.txtedit_mohpromtLat);
                    txtmohpromtlat.setText(mohpromtLat);

                    TextView txtmohpromtlng = findViewById(R.id.txtedit_mohpromtLng);
                    txtmohpromtlng.setText(mohpromtLng);

                    TextView txtmohpromtdetail = findViewById(R.id.txtedit_mohpromtdetail);
                    txtmohpromtdetail.setText(mohpromtDetail);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void ClickToEditMohpromt(View view){
        Mohpromt mohpromtToedit = new Mohpromt();
        TextView txteditMohpromplace = findViewById(R.id.txtedit_mohpromtplace);
        mohpromtToedit.setMohpromtPlace(txteditMohpromplace.getText().toString());
        TextView txteditMohpromType = findViewById(R.id.txtedit_mohpromtlisttype);
        mohpromtToedit.setMohpromtType(txteditMohpromType.getText().toString());
        //StartDate
        //EndDate
        EditText txteditMohpromStartTime = findViewById(R.id.txtedit_mohpromtstartTime);
        mohpromtToedit.setMohpromtStartTime(txteditMohpromStartTime.getText().toString());
        EditText txteditMohpromEndTime = findViewById(R.id.txtedit_mohpromtendTime);
        mohpromtToedit.setMohpromtEndTime(txteditMohpromEndTime.getText().toString());
        TextView txteditMohpromLat = findViewById(R.id.txtedit_mohpromtLat);
        mohpromtToedit.setMohpromtLat(txteditMohpromLat.getText().toString());
        TextView txteditMohpromLng = findViewById(R.id.txtedit_mohpromtLng);
        mohpromtToedit.setMohpromtLat(txteditMohpromLng.getText().toString());
        EditText txteditMohpromDetail = findViewById(R.id.txtedit_mohpromtdetail);
        mohpromtToedit.setMohpromtDetail(txteditMohpromDetail.getText().toString());

        String mohpromplace = txteditMohpromplace.getText().toString();
        String mohpromtype = txteditMohpromType.getText().toString();
        String mogpromStartdate = txteditMohpromType.getText().toString();
        String mohpromEndTime = txteditMohpromType.getText().toString();
        String mohpromstarttime = txteditMohpromStartTime.getText().toString();
        String mohpromendtime = txteditMohpromEndTime.getText().toString();
        String mohpromlat = txteditMohpromLat.getText().toString();
        String mohpormlng = txteditMohpromLng.getText().toString();
        String mohpromdetail = txteditMohpromDetail.getText().toString();

        Mohpromt mohpromt_edit = new Mohpromt(mohpromplace,mohpromtype,mogpromStartdate,mohpromEndTime,mohpromstarttime,mohpromendtime,mohpromlat,mohpormlng,mohpromdetail);
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

    public void ClickBTNEditMohpromtCancel (View view){
        Intent intent = new Intent(EditMohpromActivity.this, ListMohpromActivity.class);
        startActivity(intent);
    }

}