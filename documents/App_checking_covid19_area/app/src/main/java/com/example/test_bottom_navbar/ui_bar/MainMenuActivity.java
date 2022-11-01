package com.example.test_bottom_navbar.ui_bar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.admin.EditClusterActivity;
import com.example.test_bottom_navbar.admin.EditNewsActivity;
import com.example.test_bottom_navbar.admin.ListNewsActivity;
import com.example.test_bottom_navbar.admin.ListRiskAreaActivity;
import com.example.test_bottom_navbar.admin.LoginActivity;
import com.example.test_bottom_navbar.admin.Mainpage_admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class
MainMenuActivity extends AppCompatActivity {
    int Allpatient,AllHealing,AllGetWell,TodayNew,TodayGetWell;
    int num_allp,num_allhr,num_allgw;
    int All_PatientDistrict,All_HealingDistrict,All_GetWellDistrict,today_newpatient,today_gwtwellpatient;
    int getAll_Allpatient_district,getAll_Allgetwell_district,getAll_Allhealing_district;
    String txtdate_defcluster_amount,date_def_news, date_def_cluster;
    String dateKey;
    private int STORAGE_PERMISSION_CODE = 1;
    List<String> date_historyArray = new ArrayList<String>();
    String[] District = {"เมืองเชียงใหม่","แม่ริม","สันทราย","สารภี"};
    String[] SubDistrict = {
            "ศรีภูมิ","พระสิงห์","หายยา","ช้างม่อย","ช้างคลาน","วัดเกต","ช้างเผือก","สุเทพ","แม่เหียะ","ป่าแดด","หนองหอย","ท่าศาลา","หนองป่าครั่ง","ฟ้าฮ่าม","ป่าตัน","สันผีเสื้อ",
            "ริมใต้","ริมเหนือ","สันโป่ง","ขี้เหล็ก","สะลวง","ห้วยทราย","แม่แรม","โป่งแยง","แม่สา","ดอนแก้ว","เหมืองแก้ว",
            "สันทรายหลวง","สันทรายน้อย","สันพระเนตร","สันนาเม็ง","สันป่าเปา","หนองแหย่ง","หนองจ๊อม","หนองหาร","แม่แฝก","แม่แฝกใหม่","เมืองเล็น","ป่าไผ่",
            "ยางเนิ้ง","สารภี","ชมภู","ไชยสถาน","ขัวมุง","หนองแฝก","หนองผึ้ง","ท่ากว้าง","ดอนแก้ว","ท่าวังตาล","สันทราย","ป่าบง",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.setListNewsByAdmin();
        DateDefault();
        listClusterHistory();
        //listAllClusterHistory();
        ListToAddArray();
        listDistrict();

        if(ContextCompat.checkSelfPermission(MainMenuActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainMenuActivity.this, "ระบบได้เข้าถึงตำเเหน่งบนอุปกรณ์ของคุณแล้ว",Toast.LENGTH_SHORT).show();
        }else{
            requestPermission();
        }

        HorizontalScrollView s_auto = (HorizontalScrollView)findViewById(R.id.Scrollid);
        s_auto.postDelayed(new Runnable() {
            public void run() {
                s_auto.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);
    }

    private void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("ระบบ")
                    .setMessage("อนุญาตให้ระบบเข้าถึงตำเเหน่งที่ตั้งในอุปกรณ์ของคุณหรือไม่")
                    .setPositiveButton("อนุญาต", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainMenuActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("ปฎิเสธ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .create().show();
        }else {
            System.out.println("........................................................else permission");
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    public void DateDefault(){
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        String day = checklength(String.valueOf(mDay));
        String months = checklength(String.valueOf(mMonth + 1));
        TextView txtdate_def = findViewById(R.id.txt_defDate);
        TextView txtdate_def_district = findViewById(R.id.txt_defDate_district);
        TextView txtdate_def_news = findViewById(R.id.txt_defDate_news);
        txtdate_def.setText(day + "/" + months + "/" + mYear);
        txtdate_def_news.setText(day + "/" + months + "/" + mYear);
        txtdate_def_district.setText(day + "/" + months + "/" + mYear);
        txtdate_defcluster_amount = day+ "-" + months + "-" + mYear;
        date_def_cluster = txtdate_defcluster_amount;
        date_def_news = day+ "-" + months + "-" + mYear;
    }

    public void  setListNewsByAdmin(){
        LinearLayout list_News = findViewById(R.id.showlistnews_user_menu);
        list_News.removeAllViews();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/news");
        Query query1 = myRef.orderByKey();

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    View news = getLayoutInflater().inflate(R.layout.layout_news_menu_page, null);
                    String newsTitle = ds.child("newsTitle").getValue().toString();
                    String newsDate = ds.child("newsDate").getValue().toString();
                    String newsImg = ds.child("newsImg").getValue().toString();
                    String newsdetail = ds.child("detail").getValue().toString();

                    if(newsDate.equals(date_def_news)){
                        TextView txtnewstitle = news.findViewById(R.id.txt_newstitle);
                        txtnewstitle.setText(newsTitle);
                        TextView txtnewsdate = news.findViewById(R.id.txt_newsdate);
                        txtnewsdate.setText(newsDate);
                        TextView txtnewsdetail = news.findViewById(R.id.txt_newsdetail);
                        txtnewsdetail.setText(newsdetail);

                        ImageView img = news.findViewById(R.id.image_news);
                        Picasso.with(MainMenuActivity.this).load(newsImg).into(img);
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainMenuActivity.this, NewsDetailActivity.class);
                                intent.putExtra("newsTitle", newsTitle);
                                startActivity(intent);
                            }
                        });
                        list_News.addView(news);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void ListToAddArray(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/history_cluster");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                     dateKey= ds.getKey();
                    date_historyArray.add(dateKey);
                }
                listAllClusterHistory();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void listAllClusterHistory() {
        for (int i=0;i < date_historyArray.size();i++) {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/history_cluster/" + date_historyArray.get(i));
            Query query = myRef.orderByValue();
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String cluster_Allpatient_district = ds.child("cluster_Allpatient_district").getValue().toString();
                        String cluster_Allgetwell_district = ds.child("cluster_Allgetwell_district").getValue().toString();
                        String cluster_Allhealing_district = ds.child("cluster_Allhealing_district").getValue().toString();

                        All_PatientDistrict = Integer.parseInt(cluster_Allpatient_district);
                        All_HealingDistrict = Integer.parseInt(cluster_Allhealing_district);
                        All_GetWellDistrict = Integer.parseInt(cluster_Allgetwell_district);

                        All_PatientDistrict = All_PatientDistrict + getAll_Allpatient_district;
                        All_GetWellDistrict = All_GetWellDistrict + getAll_Allgetwell_district;
                        All_HealingDistrict = All_HealingDistrict + getAll_Allhealing_district;

                        Allpatient = Allpatient + All_PatientDistrict;
                        AllGetWell = AllGetWell + All_GetWellDistrict;
                        AllHealing = AllHealing + All_HealingDistrict;

                        TextView txtAllpatient = findViewById(R.id.txt_All_patient);
                        txtAllpatient.setText(String.valueOf(Allpatient));

                        TextView txtAllGetWellpatient = findViewById(R.id.txt_Totalgetwell_patient);
                        txtAllGetWellpatient.setText(String.valueOf(AllGetWell));

                        TextView txtAllHealingpatient = findViewById(R.id.txt_Totalhealing_patient);
                        txtAllHealingpatient.setText(String.valueOf(AllHealing));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void listDistrict() {
        for (int i = 0; i < District.length; i++) {
            LinearLayout list_alldistrict = findViewById(R.id.Show_newpatine_district);
            list_alldistrict.removeAllViews();
            String district_name = District[i];
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/history_cluster/" + date_def_cluster);
            Query query = myRef.orderByKey().equalTo(district_name);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        View dialog_district = getLayoutInflater().inflate(R.layout.dialog_cluster_districtr, null);
                        String cluster_newpatinet_today = ds.child("cluster_newpatinet_today").getValue().toString();
                        today_newpatient = Integer.parseInt(cluster_newpatinet_today);

                        TextView txtDistrict_name = dialog_district.findViewById(R.id.district_name);
                        txtDistrict_name.setText(district_name);

                        TextView txtNew_patient = dialog_district.findViewById(R.id.new_patient);
                        txtNew_patient.setText(cluster_newpatinet_today);

                        list_alldistrict.addView(dialog_district);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


        public void listClusterHistory() {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/history_cluster/" + date_def_cluster);
            Query query = myRef.orderByValue();
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String cluster_Allpatient_district = ds.child("cluster_Allpatient_district").getValue().toString();
                        String cluster_Allgetwell_district = ds.child("cluster_Allgetwell_district").getValue().toString();
                        String cluster_Allhealing_district = ds.child("cluster_Allhealing_district").getValue().toString();
                        String cluster_getwellpatinet_today = ds.child("cluster_getwellpatinet_today").getValue().toString();
                        String cluster_newpatinet_today = ds.child("cluster_newpatinet_today").getValue().toString();

                        today_gwtwellpatient = Integer.parseInt(cluster_getwellpatinet_today);
                        today_newpatient = Integer.parseInt(cluster_newpatinet_today);

                        TodayNew = TodayNew + today_newpatient;
                        TodayGetWell = TodayGetWell + today_gwtwellpatient;

                        TextView txtNewpatient_today = findViewById(R.id.txt_new_patient);
                        txtNewpatient_today.setText(String.valueOf(TodayNew));

                        TextView txtGetWellpatient_today = findViewById(R.id.txt_getwell_patient);
                        txtGetWellpatient_today.setText(String.valueOf(TodayGetWell));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    public void ClickToClusterMap(View view){
        Intent intent = new Intent(MainMenuActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void ClickToNews(View view){
        Intent intent = new Intent(MainMenuActivity.this, NewsActivity.class);
        startActivity(intent);
    }

    public void ClickToMohpromt(View view){
        Intent intent = new Intent(MainMenuActivity.this, MohpromtActivity.class);
        startActivity(intent);
    }

    public void ClickMenuToLogin(View view){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void ClickListclusterhistory(View view){
        Intent intent = new Intent(getApplicationContext(), ListClusterHistory.class);
        startActivity(intent);
    }

}