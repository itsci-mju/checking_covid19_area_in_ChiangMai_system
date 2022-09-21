package com.example.test_bottom_navbar.ui_bar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test_bottom_navbar.Cluster;
import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.admin.EditClusterActivity;
import com.example.test_bottom_navbar.admin.ListRiskAreaActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CheckLocatUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_locat_user);
        FirebaseApp.initializeApp(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef= database.getReference("admin001/cluster");
        Query query1 = myRef.orderByValue();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        this.openDialogCheckUser();
    }

    public void  openDialogCheckUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckLocatUserActivity.this);
        builder.setTitle("ระบบ");
        builder.setMessage("อนุญาตให้ระบบเข้าถึงตำเเหน่งบนอุปกรณ์ของคุณหรือไม่ ?");
        builder.setPositiveButton("อนุญาต", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //System.out.println("/////////////////////////////////////////////////////"+clusterDistrict);
                //Intent intent = new Intent(CheckLocatUserActivity.this, MainActivity.class);
                //intent.putExtra("clusterDistrict", clusterDistrict);
                //startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

 /*   public void openDialogCheckUser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/cluster");
        Query query1 = myRef.orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //String clusterDate = ds.child("clusterDate").getValue().toString();
                    String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                    //String clusterPlace = ds.child("clusterPlace").getValue().toString();
                    //String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                    //String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();

                    AlertDialog.Builder builder = new AlertDialog.Builder(CheckLocatUserActivity.this);
                    builder.setTitle("ระบบ");
                    builder.setMessage("อนุญาตให้ระบบเข้าถึงตำเเหน่งบนอุปกรณ์ของคุณหรือไม่ ?");
                    builder.setPositiveButton("อนุญาต", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference myRef = database.getReference("admin001/cluster");
                            DatabaseReference stu1 = myRef.child(clusterDistrict);
                            stu1.removeValue();
                            Intent intent = new Intent(CheckLocatUserActivity.this, MainActivity.class);
                            intent.putExtra("clusterDistrict", clusterDistrict);
                            startActivity(intent);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
