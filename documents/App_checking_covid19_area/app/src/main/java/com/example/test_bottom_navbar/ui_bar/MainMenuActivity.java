package com.example.test_bottom_navbar.ui_bar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.admin.EditNewsActivity;
import com.example.test_bottom_navbar.admin.ListNewsActivity;
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

public class
MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.setListNewsByAdmin();
        DateDefault();

        HorizontalScrollView s_auto = (HorizontalScrollView)findViewById(R.id.Scrollid);
        s_auto.postDelayed(new Runnable() {
            public void run() {
                s_auto.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);
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
                            Intent intent = new Intent(MainMenuActivity.this,NewsDetailActivity.class);
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

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    public void DateDefault(){
        Calendar calendar = Calendar.getInstance();
        TextView txtdate_def = findViewById(R.id.txt_defDate);
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        String day = checklength(String.valueOf(mDay));
        String months = checklength(String.valueOf(mMonth + 1));
        txtdate_def.setText(day + "-" + months + "-" + mYear);
    }
}