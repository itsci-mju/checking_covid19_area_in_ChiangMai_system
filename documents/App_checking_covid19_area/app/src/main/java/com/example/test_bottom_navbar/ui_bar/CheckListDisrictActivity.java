package com.example.test_bottom_navbar.ui_bar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test_bottom_navbar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CheckListDisrictActivity extends AppCompatActivity {
    private String[] District= {"เมืองเชียงใหม่","สารภี","เเม่ริม","สันกำเเพง","สันทราย"};
    private String C_District,C_new_patient;
    private int news_patient,sum_CM,sum_SP,sum_MR,sum_SK,sum_SS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_disrict);
        //this.setListDistrict();

    }

    /*public void  setListDistrict() {
        setListCluster();
        LinearLayout list_cluster = findViewById(R.id.showlistcluster_user);
        list_cluster.removeAllViews();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/cluster");
        Query query1 = myRef.orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    View cluster = getLayoutInflater().inflate(R.layout.layout_cluster, null);

                    String id = ds.getKey();

                    if(id.equals("เมืองเชียงใหม่")){
                        TextView txtdistrict = cluster.findViewById(R.id.txtview_place);
                        txtdistrict.setText(id);
                        TextView txtdistrict_totalpatient = cluster.findViewById(R.id.txtview_newpatient);
                        txtdistrict_totalpatient.setText(Integer.toString(sum_CM));
                    }else if(id.equals("สารภี")){
                        TextView txtdistrict = cluster.findViewById(R.id.txtview_place);
                        txtdistrict.setText(id);
                        TextView txtdistrict_totalpatient = cluster.findViewById(R.id.txtview_newpatient);
                        txtdistrict_totalpatient.setText(Integer.toString(sum_SP));
                    }else if(id.equals("เเม่ริม")) {
                        TextView txtdistrict = cluster.findViewById(R.id.txtview_place);
                        txtdistrict.setText(id);
                        TextView txtdistrict_totalpatient = cluster.findViewById(R.id.txtview_newpatient);
                        txtdistrict_totalpatient.setText(Integer.toString(sum_MR));
                    }else if(id.equals("สันกำเเพง")) {
                        TextView txtdistrict = cluster.findViewById(R.id.txtview_place);
                        txtdistrict.setText(id);
                        TextView txtdistrict_totalpatient = cluster.findViewById(R.id.txtview_newpatient);
                        txtdistrict_totalpatient.setText(Integer.toString(sum_SK));
                    }else if(id.equals("สันทราย")) {
                        TextView txtdistrict = cluster.findViewById(R.id.txtview_place);
                        txtdistrict.setText(id);
                        TextView txtdistrict_totalpatient = cluster.findViewById(R.id.txtview_newpatient);
                        txtdistrict_totalpatient.setText(Integer.toString(sum_SS));
                    }

                    list_cluster.addView(cluster);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    public void  setListCluster() {
        for (int i=0;i < District.length;i++) {
            System.out.println(District[i]);
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/cluster/" + District[i]);
            C_District = District[i];
            Query query1 = myRef.orderByKey();
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Log.e("Data_cluster", ds.getValue().toString());
                        String clusterPlace = ds.child("clusterPlace").getValue().toString();
                        String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                        String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();
                        news_patient = Integer.parseInt(cluster_news_patient);
                        if(clusterDistrict.equals("เมืองเชียงใหม่")){
                            sum_CM = sum_CM+news_patient;
                        }else if(clusterDistrict.equals("สารภี")){
                            sum_SP = sum_SP+news_patient;
                        }else if(clusterDistrict.equals("เเม่ริม")){
                            sum_MR = sum_MR+news_patient;
                        }else if(clusterDistrict.equals("สันกำเเพง")){
                            sum_SK = sum_SK+news_patient;
                        }else if(clusterDistrict.equals("สันทราย")){
                            sum_SS = sum_SS+news_patient;
                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    public void ClickBack(View view){
        Intent intent = new Intent(CheckListDisrictActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void ClickDistrict_All(View view){
        TextView view_All = findViewById(R.id.check_All);
        TextView view_Red = findViewById(R.id.check_Red);
        TextView view_orange = findViewById(R.id.check_orange);
        TextView view_yellow = findViewById(R.id.check_yellow);

        view_yellow.setTextColor(Color.parseColor("#000000"));
        view_orange.setTextColor(Color.parseColor("#000000"));
        view_Red.setTextColor(Color.parseColor("#000000"));
        view_All.setTextColor(Color.parseColor("#FFFFFF"));

        view_Red.setBackgroundColor(Color.parseColor("#EF5350"));
        view_orange.setBackgroundColor(Color.parseColor("#FF7043"));
        view_yellow.setBackgroundColor(Color.parseColor("#FFEE58"));

        ///list cluster
        //setListClusterByAdmin();
    }

    public void ClickDistrict_Red(View view){
        TextView view_All = findViewById(R.id.check_All);
        TextView view_Red = findViewById(R.id.check_Red);
        TextView view_orange = findViewById(R.id.check_orange);
        TextView view_yellow = findViewById(R.id.check_yellow);

        view_yellow.setTextColor(Color.parseColor("#000000"));
        view_orange.setTextColor(Color.parseColor("#000000"));
        view_Red.setTextColor(Color.parseColor("#FFFFFF"));
        view_All.setTextColor(Color.parseColor("#000000"));

        view_Red.setBackgroundColor(Color.parseColor("#FF0500"));
        view_orange.setBackgroundColor(Color.parseColor("#FF7043"));
        view_yellow.setBackgroundColor(Color.parseColor("#FFEE58"));

        ///list cluster
        //setListClusterRedByAdmin();
    }

    public void ClickDistrict_Orange(View view){
        TextView view_All = findViewById(R.id.check_All);
        TextView view_Red = findViewById(R.id.check_Red);
        TextView view_orange = findViewById(R.id.check_orange);
        TextView view_yellow = findViewById(R.id.check_yellow);

        view_Red.setTextColor(Color.parseColor("#000000"));
        view_yellow.setTextColor(Color.parseColor("#000000"));
        view_orange.setTextColor(Color.parseColor("#FFFFFF"));
        view_All.setTextColor(Color.parseColor("#000000"));

        view_Red.setBackgroundColor(Color.parseColor("#EF5350"));
        view_orange.setBackgroundColor(Color.parseColor("#FD3D00"));
        view_yellow.setBackgroundColor(Color.parseColor("#FFEE58"));

        ///list cluster
        //setListClusterOrangeByAdmin();
    }

    public void ClickDistrict_Yellow(View view){
        TextView view_All = findViewById(R.id.check_All);
        TextView view_Red = findViewById(R.id.check_Red);
        TextView view_orange = findViewById(R.id.check_orange);
        TextView view_yellow = findViewById(R.id.check_yellow);

        view_Red.setTextColor(Color.parseColor("#000000"));
        view_orange.setTextColor(Color.parseColor("#000000"));
        view_yellow.setTextColor(Color.parseColor("#FFFFFF"));
        view_All.setTextColor(Color.parseColor("#000000"));

        view_yellow.setBackgroundColor(Color.parseColor("#FFE500"));
        view_orange.setBackgroundColor(Color.parseColor("#FF7043"));
        view_Red.setBackgroundColor(Color.parseColor("#EF5350"));

        ///list cluster
        //setListClusterYellowByAdmin();
    }
}