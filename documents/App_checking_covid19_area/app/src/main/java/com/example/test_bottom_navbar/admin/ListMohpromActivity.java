package com.example.test_bottom_navbar.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class ListMohpromActivity extends AppCompatActivity {
    String mohpromtPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mohprom);

        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        mohpromtPlace = intent.getStringExtra("mohpromtPlace");
        this.setListMohpromtByAdmin();

    }

    public void setListMohpromtByAdmin(){
        LinearLayout list_mohpromt = findViewById(R.id.showlistmohprom_admin);
        list_mohpromt.removeAllViews();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/mohpromt");
        Query query1 = myRef.orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    View mohpromt = getLayoutInflater().inflate(R.layout.layout_mohprom_admin, null);
                    String mohpromtPlace = ds.child("mohpromtPlace").getValue().toString();
                    String mohpromtType = ds.child("mohpromtType").getValue().toString();
                    String mohpromtStartDate = ds.child("mohpromtStartDate").getValue().toString();
                    String mohpromtEndDate = ds.child("mohpromtEndDate").getValue().toString();
                    String mohpromtStartTime = ds.child("mohpromtStartTime").getValue().toString();
                    String mohpromtEndTime = ds.child("mohpromtEndTime").getValue().toString();
                    String mohpromtDetail = ds.child("mohpromtDetail").getValue().toString();

                    TextView txtmohpromt_place = mohpromt.findViewById(R.id.txt_mohpromtplace);
                    txtmohpromt_place.setText(mohpromtPlace);
                    TextView txtmohpromt_type = mohpromt.findViewById(R.id.txt_mohpromttype);
                    txtmohpromt_type.setText(mohpromtType);
                    TextView txtmohpromt_startdate = mohpromt.findViewById(R.id.txt_mohpromt_startdate);
                    txtmohpromt_startdate.setText(mohpromtStartDate);
                    TextView txtmohpromt_enddate = mohpromt.findViewById(R.id.txt_mohpromt_enddate);
                    txtmohpromt_enddate.setText(mohpromtEndDate);
                    TextView txtmohpromt_starttime = mohpromt.findViewById(R.id.txt_mohpromt_starttime);
                    txtmohpromt_starttime.setText(mohpromtStartTime);
                    TextView txtmohpromt_endtime = mohpromt.findViewById(R.id.txt_mohpromtendtime);
                    txtmohpromt_endtime.setText(mohpromtEndTime);

                    ImageView btn_Call = (ImageView) mohpromt.findViewById(R.id.txtMdelete);
                    btn_Call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ListMohpromActivity.this);
                            builder.setTitle("เเจ้งเตือน");
                            builder.setMessage("คุณเเน่ใจหรอว่าต้องการลบ ?");
                            builder.setPositiveButton("ฉันเเน่ใจ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseReference myRef= database.getReference("admin001/mohpromt");
                                    DatabaseReference stu1 = myRef.child(mohpromtPlace);
                                    stu1.removeValue();
                                    Intent intent = new Intent(ListMohpromActivity.this, ListMohpromActivity.class);
                                    intent.putExtra("mohpromtPlace", mohpromtPlace);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    });
                    ImageView btn_Edit = (ImageView) mohpromt.findViewById(R.id.mohprom_clicktoedit);
                    btn_Edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ListMohpromActivity.this, EditMohpromActivity.class);
                            intent.putExtra("mohpromtPlace",mohpromtPlace);
                            startActivity(intent);
                        }
                    });
                    list_mohpromt.addView(mohpromt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void ClickToEditMohpromt(View view){
        Intent intent = new Intent(ListMohpromActivity.this, EditMohpromActivity.class);
        startActivity(intent);
    }

    public void ClickBTNBackByAdmin(View view){
        Intent intent = new Intent(ListMohpromActivity.this, Mainpage_admin.class);
        //intent.putExtra("Allpatient_District",Allpatient_District);
        startActivity(intent);
    }
}