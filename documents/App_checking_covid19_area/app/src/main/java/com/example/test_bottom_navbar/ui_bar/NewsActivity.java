package com.example.test_bottom_navbar.ui_bar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.admin.AddClusterActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    int Allpatient_District;
    private EditText SarchButton;
    ViewFlipper flipper;
    String news_titleTxt_error = "ไม่พบข้อมูล";
    int imgArray[]={R.drawable.img_s1,R.drawable.img_s2,R.drawable.img_s5};
    List<String> myListNews = new ArrayList<String>();
    String sarchbutton,news_titleTxt,news_dateCalendar,news_dateTxt,NewsTitle,myListNews_String;
    BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        this.setListNewsByUser();
        Intent intent = getIntent();
        Allpatient_District = intent.getIntExtra("Allpatient_District",Allpatient_District);
        System.out.println("////////////////////////////////////////////"+Allpatient_District);

        flipper=(ViewFlipper)findViewById(R.id.flipper_img);
        for(int i=0;i< imgArray.length;i++){
            showImge(imgArray[i]);
        }

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.nav_news);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_back:
                        startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public void showImge(int img){
        ImageView imageview = new ImageView(this);
        imageview.setBackgroundResource(img);

        flipper.addView(imageview);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    @SuppressLint("NewApi")
    public void ClickDateSarchNews(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(NewsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                EditText txtdate = findViewById(R.id.txt_sarchNews);
                news_dateCalendar = txtdate.getText().toString();
                String day = checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month + 1));
                txtdate.setText(day + "-" + months + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void setListNewsByUser(){
        LinearLayout list_News = findViewById(R.id.showlistnews_user);
        list_News.removeAllViews();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/news");
        Query query1 = myRef.orderByKey();

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    View news = getLayoutInflater().inflate(R.layout.layout_news_user, null);
                    String newsTitle = ds.child("newsTitle").getValue().toString();
                    String newsDate = ds.child("newsDate").getValue().toString();
                    String newsImg = ds.child("newsImg").getValue().toString();
                    String newsdetail = ds.child("detail").getValue().toString();


                    TextView txtnewstitle = news.findViewById(R.id.txt_newstitle);
                    txtnewstitle.setText(newsTitle);
                    TextView txtnewsdate = news.findViewById(R.id.txt_newsdate);
                    txtnewsdate.setText(newsDate);
                    TextView txtnewsdetail = news.findViewById(R.id.txt_newsdetail);
                    txtnewsdetail.setText(newsdetail);


                    ImageView img = news.findViewById(R.id.image_news);
                    Picasso.with(NewsActivity.this).load(newsImg).into(img);
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(NewsActivity.this,NewsDetailActivity.class);
                            intent.putExtra("newsTitle", newsTitle);
                            startActivity(intent);
                        }

                    });
                    list_News.addView(news);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ClickSearchNews(View view) {
        SarchButton = findViewById(R.id.txt_sarchNews);
        sarchbutton = SarchButton.getText().toString();
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/news");
            Query query1 = myRef.orderByKey();
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String newsTitle = ds.child("newsTitle").getValue().toString();
                        String newsDate = ds.child("newsDate").getValue().toString();
                        news_titleTxt = newsTitle;
                        news_dateTxt = newsDate;

                        if(news_dateTxt.equals(sarchbutton)){
                            myListNews.add(news_titleTxt);
                        }
                    }
                    System.out.println(myListNews);
                    myListNews_String = myListNews.toString();
                    System.out.println(myListNews_String);

                    if(sarchbutton.equals("")){
                        setListNewsByUser();
                        Toast.makeText(NewsActivity.this, "กรุณากรอกวันที่", Toast.LENGTH_SHORT).show();
                    }else if(myListNews_String.equals("[]")){
                        setListNewsByUser();
                        Toast.makeText(NewsActivity.this, "ไม่พบข่าวประจำวันที่ "+sarchbutton, Toast.LENGTH_SHORT).show();
                    }
                    if(myListNews != null){
                            ListSearchNews();
                        }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }

    public void ListSearchNews() {
        LinearLayout list_News_Array = findViewById(R.id.showlistnews_user);
        list_News_Array.removeAllViews();
        for (int i=0;i<myListNews.size();i++) {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/news");
            Query query1 = myRef.orderByKey().equalTo(myListNews.get(i));
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        View news_array = getLayoutInflater().inflate(R.layout.layout_news_user, null);
                        String newsTitle = ds.child("newsTitle").getValue().toString();
                        String newsDate = ds.child("newsDate").getValue().toString();
                        String newsImg = ds.child("newsImg").getValue().toString();

                        TextView txtnewstitle = news_array.findViewById(R.id.txt_newstitle);
                        txtnewstitle.setText(newsTitle);

                        TextView txtnewsdate = news_array.findViewById(R.id.txt_newsdate);
                        txtnewsdate.setText(newsDate);

                        ImageView img = news_array.findViewById(R.id.image_news);
                        Picasso.with(NewsActivity.this).load(newsImg).into(img);
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                                intent.putExtra("newsTitle", newsTitle);
                                startActivity(intent);
                            }
                        });
                        list_News_Array.addView(news_array);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        myListNews.clear();
    }
}