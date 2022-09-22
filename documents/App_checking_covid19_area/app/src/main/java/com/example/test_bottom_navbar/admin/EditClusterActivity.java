package com.example.test_bottom_navbar.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

import java.util.Calendar;

public class EditClusterActivity extends AppCompatActivity {
    String clusterPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cluster);

        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        clusterPlace = intent.getStringExtra("clusterPlace");
        this.getClusterToEdit();
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


    public void getClusterToEdit(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001");
        Query query1 = myRef.orderByKey().equalTo(clusterPlace);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String clusterDate = ds.child("clusterDate").getValue().toString();
                    String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                    String clusterPlace = ds.child("clusterPlace").getValue().toString();
                    String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                    String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();

                    TextView txtplace = findViewById(R.id.txtedit_place);
                    txtplace.setText(clusterPlace);

                    TextView txtsubdistrict = findViewById(R.id.txtedit_subdistrict);
                    txtsubdistrict.setText(clusterSubdistrict);

                    TextView txtdistrict = findViewById(R.id.txtedit_district);
                    txtdistrict.setText(clusterDistrict);

                    EditText txtdate = findViewById(R.id.txtedit_date);
                    txtdate.setText(clusterDate);

                    EditText txtnewpatient = findViewById(R.id.txtedit_newpatient);
                    txtnewpatient.setText(cluster_news_patient);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ClickBTNEditCluster(View view){
        EditText txtdate = findViewById(R.id.txtedit_date);
        TextView txtplace = findViewById(R.id.txtedit_place);
        TextView txtsubdistrict = findViewById(R.id.txtedit_subdistrict);
        TextView txtdistrict = findViewById(R.id.txtedit_district);
        EditText txtnewpatient = findViewById(R.id.txtedit_newpatient);

        String clusterdate  = txtdate.getText().toString();
        String clusterplace = txtplace.getText().toString();
        String clustersubdistrict = txtsubdistrict.getText().toString();
        String clusterdistrict = txtdistrict.getText().toString();
        String clusternewpatient = txtnewpatient.getText().toString();

        Cluster cluster_edit = new Cluster(clusterdate,clusterplace,clustersubdistrict,clusterdistrict,clusternewpatient);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_clusterEdit = database.getReference("admin001/"+clusterPlace);
        Query query1 =  myRef_clusterEdit.orderByValue();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    myRef_clusterEdit.child("clusterDate").setValue(cluster_edit.getClusterDate());
                    myRef_clusterEdit.child("clusterPlace").setValue(cluster_edit.getClusterPlace());
                    myRef_clusterEdit.child("clusterDistrict").setValue(cluster_edit.getClusterDistrict());
                    myRef_clusterEdit.child("clusterSubdistrict").setValue(cluster_edit.getClusterSubdistrict());
                    myRef_clusterEdit.child("cluster_news_patient").setValue(cluster_edit.getCluster_news_patient());

                    Intent intent = new Intent(EditClusterActivity.this, ListRiskAreaActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void ClickBTNEditCancel (View view){
        Intent intent = new Intent(EditClusterActivity.this, ListRiskAreaActivity.class);
        startActivity(intent);
    }
}