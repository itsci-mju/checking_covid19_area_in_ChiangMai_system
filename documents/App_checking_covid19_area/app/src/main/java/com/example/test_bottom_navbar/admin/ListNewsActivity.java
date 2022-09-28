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
import com.squareup.picasso.Picasso;
public class ListNewsActivity extends AppCompatActivity {
    String newsTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        newsTitle = intent.getStringExtra("newsTitle");
        this.setListNewsByAdmin();
    }

    public void  setListNewsByAdmin(){
        LinearLayout list_cluster = findViewById(R.id.showlistnews_admin);
        list_cluster.removeAllViews();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/news");
        Query query1 = myRef.orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    View news = getLayoutInflater().inflate(R.layout.layout_news_admin, null);
                    String newsTitle = ds.child("newsTitle").getValue().toString();
                    String newsDate = ds.child("newsDate").getValue().toString();
                    String newsImg = ds.child("newsImg").getValue().toString();

                    TextView txtnewstitle = news.findViewById(R.id.txt_newstitle);
                    txtnewstitle.setText(newsTitle);

                    TextView txtnewsdate = news.findViewById(R.id.txt_newsdate);
                    txtnewsdate.setText(newsDate);

                    ImageView img = news.findViewById(R.id.image_news);
                    Picasso.with(ListNewsActivity.this).load(newsImg).into(img);

                   ImageView btn_Call = (ImageView) news.findViewById(R.id.txtNewsdelete);
                    btn_Call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ListNewsActivity.this);
                            builder.setTitle("คำความเตือน");
                            builder.setMessage("คุณเเน่ใจหรอว่าต้องการลบข่าวนี้ ?");
                            builder.setPositiveButton("ฉันเเน่ใจ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseReference myRef= database.getReference("admin001/news");
                                    DatabaseReference stu1 = myRef.child(newsTitle);
                                    stu1.removeValue();
                                    Intent intent = new Intent(ListNewsActivity.this, ListNewsActivity.class);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    });
                  ImageView btn_Edit = (ImageView) news.findViewById(R.id.image_news);
                    btn_Edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ListNewsActivity.this, EditNewsActivity.class);
                            intent.putExtra("newsTitle",newsTitle);
                            startActivity(intent);
                        }
                    });
                    list_cluster.addView(news);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ClickBTNBackByAdmin(View view){
        Intent intent = new Intent(ListNewsActivity.this, Mainpage_admin.class);
        //intent.putExtra("Allpatient_District",Allpatient_District);
        startActivity(intent);
    }
}