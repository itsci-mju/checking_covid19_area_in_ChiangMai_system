package com.example.test_bottom_navbar.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test_bottom_navbar.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ListDistrictClusterActivity extends AppCompatActivity {
    String clusterDistrict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_district_cluster);
        FirebaseApp.initializeApp(this);
        Intent intent1 = getIntent();
        clusterDistrict = intent1.getStringExtra("clusterDistrict");
        this.setListDistrictClusterByAdmin();
    }

    public void  setListDistrictClusterByAdmin(){
        LinearLayout list_cluster = findViewById(R.id.showlistcluster_district_admin);
        list_cluster.removeAllViews();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/cluster");
        Query query1 = myRef.orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Log.e("Data_cluster",ds.getValue().toString());
                    View cluster = getLayoutInflater().inflate(R.layout.layout_districtclusterby_admin, null);
                    String district2 = ds.child("name").getValue().toString();

                    TextView txtdistrict = cluster.findViewById(R.id.txt_district);
                    txtdistrict.setText(district2);


                    ImageView btn_Edit = (ImageView) cluster.findViewById(R.id.click_districttocluster);
                    btn_Edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ListDistrictClusterActivity.this, ListRiskAreaActivity.class);
                            intent.putExtra("อำเภอ2",district2);
                            startActivity(intent);
                        }
                    });
                    list_cluster.addView(cluster);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ClickBackDistrictByAdmin(View view){
        Intent intent = new Intent(ListDistrictClusterActivity.this, Mainpage_admin.class);
        startActivity(intent);
    }
}