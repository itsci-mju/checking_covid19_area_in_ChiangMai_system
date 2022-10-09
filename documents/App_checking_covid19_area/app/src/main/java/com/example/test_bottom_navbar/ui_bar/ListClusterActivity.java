package com.example.test_bottom_navbar.ui_bar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.admin.EditClusterActivity;
import com.example.test_bottom_navbar.admin.ListRiskAreaActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ListClusterActivity extends AppCompatActivity {
    private String[] District= {"เมืองเชียงใหม่","สารภี","เเม่ริม","สันกำเเพง","สันทราย"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cluster_user);

        this.setListCluster();
    }

    public void  setListCluster() {
        for (int i=0;i < District.length;i++) {
            System.out.println(District[i]);
            LinearLayout list_cluster = findViewById(R.id.showlistcluster_user);
            list_cluster.removeAllViews();
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/cluster/" + District[i]);
            Query query1 = myRef.orderByKey();
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Log.e("Data_cluster", ds.getValue().toString());
                        View cluster = getLayoutInflater().inflate(R.layout.layout_cluster, null);

                        String clusterDistrict = ds.child("clusterDistrict").getValue().toString();

                        TextView txtdistrict = cluster.findViewById(R.id.txtview_place);
                        txtdistrict.setText(clusterDistrict);

                        list_cluster.addView(cluster);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }
}
