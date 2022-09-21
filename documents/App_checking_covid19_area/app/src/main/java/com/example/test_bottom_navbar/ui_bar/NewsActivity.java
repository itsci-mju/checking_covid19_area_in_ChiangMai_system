package com.example.test_bottom_navbar.ui_bar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.admin.AddClusterActivity;
import com.example.test_bottom_navbar.admin.ListNewsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;

public class NewsActivity extends AppCompatActivity {
    int Allpatient_District;
    private EditText SarchButton;
    String sarchbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        this.setListNewsByUser();

        Intent intent = getIntent();
        Allpatient_District = intent.getIntExtra("Allpatient_District",Allpatient_District);
        System.out.println("////////////////////////////////////////////"+Allpatient_District);



        BottomNavigationView bottomNav = findViewById(R.id.nav_host_fragment_activity_bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    public void ClickDateSarchNews(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(NewsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                EditText txtdate = findViewById(R.id.txt_sarchNews);
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

                    TextView txtnewstitle = news.findViewById(R.id.txt_newstitle);
                    txtnewstitle.setText(newsTitle);


                    TextView txtnewsdate = news.findViewById(R.id.txt_newsdate);
                    txtnewsdate.setText(newsDate);

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
        LinearLayout list_News = findViewById(R.id.showlistnews_user);
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

                        TextView txtnewstitle = news.findViewById(R.id.txt_newstitle);
                        txtnewstitle.setText(newsTitle);

                        TextView txtnewsdate = news.findViewById(R.id.txt_newsdate);
                        txtnewsdate.setText(newsDate);

                        ImageView img = news.findViewById(R.id.image_news);
                        Picasso.with(NewsActivity.this).load(newsImg).into(img);
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                                intent.putExtra("newsTitle", newsTitle);
                                startActivity(intent);
                            }
                        });
                        list_News.addView(news);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new NotificationsFragment();
                            break;
                        case R.id.nav_setting:
                            selectedFragment = new SettingFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}