package com.example.test_bottom_navbar.ui_bar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.admin.EditNewsActivity;
import com.example.test_bottom_navbar.admin.ListNewsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {
    String newsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news_user);

        newsTitle = getIntent().getStringExtra("newsTitle");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/news");
        Query query1 = myRef.orderByKey().equalTo(newsTitle);;
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    String newsTitle = ds.child("newsTitle").getValue().toString();
                    String newsDate = ds.child("newsDate").getValue().toString();
                    String newsDetail = ds.child("detail").getValue().toString();
                    String newsImg = ds.child("newsImg").getValue().toString();

                    TextView txtnewstitle = findViewById(R.id.txt_showNewsTitle);
                    txtnewstitle.setText(newsTitle);

                    TextView txtnewsdate = findViewById(R.id.txt_showDateNews);
                    txtnewsdate.setText(newsDate);

                    TextView txtnewsDetail = findViewById(R.id.txt_showDetailNews);
                    txtnewsDetail.setText(newsDetail);

                    ImageView img = findViewById(R.id.image_showNews);
                    Picasso.with(NewsDetailActivity.this).load(newsImg).into(img);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ClickDetailNewsBack(View view){
        Intent intent = new Intent(NewsDetailActivity.this,NewsActivity.class);
        startActivity(intent);
    }
}
