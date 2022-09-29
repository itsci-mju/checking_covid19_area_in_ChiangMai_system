package com.example.test_bottom_navbar.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.ui_bar.MainActivity;
import com.example.test_bottom_navbar.ui_bar.NewsActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ListRiskAreaActivity extends AppCompatActivity {
    String clusterPlace;
    int patient_number;
    int Allpatient_District,Totalpatient_CM,Totalpatient_Sarapee,Totalpatient_MaeRim,Totalpatient_SunSai;
    private String[] District= {"เมืองเชียงใหม่","สารภี","เเม่ริม","สันกำเเพง","สันทราย"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_risk_area);

        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        clusterPlace = intent.getStringExtra("clusterPlace");

        Intent getpatient = getIntent();
        patient_number = getpatient.getIntExtra("patient_number",patient_number);
        System.out.println("////////////////////////////////////////////"+Allpatient_District);

        this.setListClusterByAdmin();
    }

    public void  setListClusterByAdmin() {
        for (int i=0;i < District.length;i++) {
            System.out.println(District[i]);
            LinearLayout list_cluster = findViewById(R.id.showlistcluster_admin);
            list_cluster.removeAllViews();
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/cluster/" + District[i]);
            String district_name = District[i];
            Query query1 = myRef.orderByKey();
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Log.e("Data_cluster", ds.getValue().toString());
                        View cluster = getLayoutInflater().inflate(R.layout.layout_clusterby_admin, null);
                        String clusterDate = ds.child("clusterDate").getValue().toString();
                        String clusterDistrict = ds.child("clusterDistrict").getValue().toString();
                        String clusterPlace = ds.child("clusterPlace").getValue().toString();
                        String clusterSubdistrict = ds.child("clusterSubdistrict").getValue().toString();
                        String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();

                        TextView txtplace = cluster.findViewById(R.id.txtedit_place);
                        txtplace.setText(clusterPlace);

                        TextView txtsubdistrict = cluster.findViewById(R.id.txtedit_subdistrict);
                        txtsubdistrict.setText(clusterSubdistrict);

                        TextView txtdistrict = cluster.findViewById(R.id.txtedit_district);
                        txtdistrict.setText(clusterDistrict);

                        TextView txtdate = cluster.findViewById(R.id.txtedit_date);
                        txtdate.setText(clusterDate);

                        TextView txtnewpatient = cluster.findViewById(R.id.txtedit_newpatient);
                        txtnewpatient.setText(cluster_news_patient);

                        patient_number = Integer.parseInt(txtnewpatient.getText().toString());

                        if(district_name == "เมืองเชียงใหม่"){
                            Totalpatient_CM = Totalpatient_CM + patient_number;
                            System.out.println("////////////////////////////////////////////" + district_name);
                            System.out.println("////////////////////////////////////////////" + Totalpatient_CM);
                        }else if(district_name == "สารภี"){
                            Totalpatient_Sarapee = Totalpatient_Sarapee + patient_number;
                            System.out.println("////////////////////////////////////////////" + district_name);
                            System.out.println("////////////////////////////////////////////" + Totalpatient_Sarapee);
                        }else if(district_name == "เเม่ริม") {
                            Totalpatient_MaeRim = Totalpatient_MaeRim + patient_number;
                            System.out.println("////////////////////////////////////////////" + district_name);
                            System.out.println("////////////////////////////////////////////" + Totalpatient_MaeRim);
                        }else if(district_name == "สันทราย") {
                            Totalpatient_SunSai = Totalpatient_SunSai + patient_number;
                            System.out.println("////////////////////////////////////////////" + district_name);
                            System.out.println("////////////////////////////////////////////" + Totalpatient_SunSai);
                        }

                        /*Allpatient_District = Allpatient_District + patient_number;
                        System.out.println("////////////////////////////////////////////" + Allpatient_District);*/

                        ImageView btn_Call = (ImageView) cluster.findViewById(R.id.txtdelete);
                        btn_Call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ListRiskAreaActivity.this);
                                builder.setTitle("คำความเตือน");
                                builder.setMessage("คุณเเน่ใจหรอว่าต้องการลบคลัสเตอร์นี้ ?");
                                builder.setPositiveButton("ฉันเเน่ใจ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DatabaseReference myRef = database.getReference("admin001/cluster/"+district_name);
                                        DatabaseReference stu1 = myRef.child(clusterPlace);
                                        stu1.removeValue();
                                        Intent intent = new Intent(ListRiskAreaActivity.this, ListRiskAreaActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });
                        ImageView btn_Edit = (ImageView) cluster.findViewById(R.id.cluster_clicktoedit);
                        btn_Edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ListRiskAreaActivity.this, EditClusterActivity.class);
                                intent.putExtra("clusterPlace", clusterPlace);
                                intent.putExtra("district_name", district_name);
                                startActivity(intent);
                            }
                        });
                        list_cluster.addView(cluster);

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
        Log.e("------------------------------Totalpatient_CM", String.valueOf(Totalpatient_CM));
    }

    public void OnClickClusterDetail(View view) {}

    public void ClickToEditCluster(View view){
        Intent intent = new Intent(ListRiskAreaActivity.this, EditClusterActivity.class);
        startActivity(intent);
    }

    public void ClickBTNBackByAdmin(View view){
        Intent intent = new Intent(ListRiskAreaActivity.this, Mainpage_admin.class);
        intent.putExtra("Totalpatient_CM",Totalpatient_CM);
        Log.e("------------------------------Totalpatient_CM", String.valueOf(Totalpatient_CM));
        intent.putExtra("Totalpatient_Sarapee",Totalpatient_Sarapee);
        intent.putExtra("Totalpatient_MaeRim",Totalpatient_MaeRim);
        intent.putExtra("Totalpatient_SunSai",Totalpatient_SunSai);

        startActivity(intent);
    }

    public void ClickbynBack(View view){
        Intent intent = new Intent(ListRiskAreaActivity.this,MainActivity.class);
        startActivity(intent);
    }

}
