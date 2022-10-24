package com.example.test_bottom_navbar.ui_bar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
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

import java.util.Calendar;
import java.util.Collections;

public class
MainMenuActivity extends AppCompatActivity {
    int Newpatient,GetWellpatient,Allpatient,AllHealingpatient,AllGetWellpatient,Allpatienttody;
    int num_newp,num_getwellp,num_allp,num_allhr,num_allgw,num_new;
    int intcluster_All_patient,intcluster_getwell_patient,intcluster_news_patient;
    String txtdate_defcluster_amount,date_def_news;
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
        setListClusterToday();
        setListAllCluster();

        HorizontalScrollView s_auto = (HorizontalScrollView)findViewById(R.id.Scrollid);
        s_auto.postDelayed(new Runnable() {
            public void run() {
                s_auto.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);
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
        TextView txtdate_def_news = findViewById(R.id.txt_defDate_news);
        txtdate_def.setText(day + "/" + months + "/" + mYear);
        txtdate_def_news.setText(day + "/" + months + "/" + mYear);
        txtdate_defcluster_amount = day+ "-" + months + "-" + mYear;
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

    public void  setListClusterToday() {
        for (int i=0;i < District.length;i++) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("admin001/cluster/" + District[i]);
                Query query1 = myRef.orderByKey();
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String clusterDate = ds.child("clusterDate").getValue().toString();
                            String cluster_news_patient = ds.child("cluster_news_patient").getValue().toString();
                            String cluster_getwell_patient = ds.child("cluster_getwell_patient").getValue().toString();

                            num_getwellp = Integer.parseInt(cluster_getwell_patient);
                            num_newp = Integer.parseInt(cluster_news_patient);

                            String nnp = String.valueOf(num_newp);
                            String ngw = String.valueOf(num_getwellp);

                            Log.e("================================new patient",nnp);
                            Log.e("================================getwell patient",ngw);

                            if (clusterDate.equals(txtdate_defcluster_amount)) {
                                Newpatient = Newpatient + num_newp;
                                GetWellpatient = GetWellpatient + num_getwellp;
                                Allpatienttody = Allpatienttody+num_new;
                            }

                            TextView txtNewpatient = findViewById(R.id.txt_new_patient);
                            txtNewpatient.setText(String.valueOf(Newpatient));

                            TextView txtGetWellpatient = findViewById(R.id.txt_getwell_patient);
                            txtGetWellpatient.setText(String.valueOf(GetWellpatient));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
    }

    public void  setListAllCluster() {
        for (int i=0;i < District.length;i++) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("admin001/cluster/" + District[i]);
                Query query1 = myRef.orderByKey();
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String cluster_Allpatient_district = ds.child("cluster_Allpatient_district").getValue().toString();
                            String cluster_Allgetwell_district = ds.child("cluster_Allgetwell_district").getValue().toString();
                            String cluster_Allhealing_district = ds.child("cluster_Allhealing_district").getValue().toString();

                            num_allgw = Integer.parseInt(cluster_Allgetwell_district);
                            num_allhr = Integer.parseInt(cluster_Allhealing_district);
                            num_allp = Integer.parseInt(cluster_Allpatient_district);

                            Allpatient = Allpatient + num_allp;
                            AllGetWellpatient = AllGetWellpatient + num_allgw;
                            AllHealingpatient = AllHealingpatient + num_allhr;

                            TextView txtNewpatient = findViewById(R.id.txt_All_patient);
                            txtNewpatient.setText(String.valueOf(Allpatient));

                            TextView txtGetWellpatient = findViewById(R.id.txt_Totalgetwell_patient);
                            txtGetWellpatient.setText(String.valueOf(AllGetWellpatient));

                            TextView txtAllhealingpatient = findViewById(R.id.txt_Totalhealing_patient);
                            txtAllhealingpatient.setText(String.valueOf(AllHealingpatient));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
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

}