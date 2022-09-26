package com.example.test_bottom_navbar.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_bottom_navbar.Cluster;
import com.example.test_bottom_navbar.News;
import com.example.test_bottom_navbar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class EditNewsActivity extends AppCompatActivity {
    String newsTitle;
    private ImageView mButtonChooseImage;
    private TextView mButtonUpload;
    private ProgressBar mProgressBar;
    private StorageTask mUploadTask;
    private ImageView mImageView;
    private EditText editNewsDetail;
    private StorageReference mStorageRef;
    private Uri ImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);
        newsTitle = getIntent().getStringExtra("newsTitle");
        this.getNewsToEdit();

        mButtonChooseImage = findViewById(R.id.image_editnews);
        mButtonUpload = findViewById(R.id.button_edit_new);
        mImageView = findViewById(R.id.image_editnews);
        mProgressBar = findViewById(R.id.progress_bar_edit);
        editNewsDetail = findViewById(R.id.txtedit_newsdetail);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(EditNewsActivity.this, "อยู่ในระหว่างการอัปโหลด", Toast.LENGTH_SHORT).show();
                }else {
                    ClickEditNews();
                }
            }
        });
    }

    public void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            ImageUri = data.getData();
            Picasso.with(this).load(ImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    //Get Nwes
    public void getNewsToEdit(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/news");
        Query query1 = myRef.orderByKey().equalTo(newsTitle);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String newstitle = ds.child("newsTitle").getValue().toString();
                    newsTitle = newstitle;
                    String newsDate = ds.child("newsDate").getValue().toString();
                    String newsDetail = ds.child("detail").getValue().toString();
                    String newsImg = ds.child("newsImg").getValue().toString();

                    TextView txtnewstitle = findViewById(R.id.txtedit_newstitle);
                    txtnewstitle.setText(newsTitle);

                    TextView txtnewsDate = findViewById(R.id.txtedit_newsdate);
                    txtnewsDate.setText(newsDate);

                    TextView txtnewsDetail = findViewById(R.id.txtedit_newsdetail);
                    txtnewsDetail.setText(newsDetail);

                    ImageView img = findViewById(R.id.image_editnews);
                    Picasso.with(EditNewsActivity.this).load(newsImg).into(img);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Upload Image andd Update News
    public void ClickEditNews(){
        if(ImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(ImageUri));

            mUploadTask = fileReference.putFile(ImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 5000);

                            /*News newsToedit = new News();
                            TextView txtedittitle_news = findViewById(R.id.txtedit_newstitle);
                            newsToedit.setNewsTitle(txtedittitle_news.getText().toString());
                            TextView txteditdate_news = findViewById(R.id.txtedit_newsdate);
                            newsToedit.setNewsDate(txteditdate_news.getText().toString());
                            TextView txteditdetail_news = findViewById(R.id.txtedit_newsdetail);
                            newsToedit.setDetail(txteditdetail_news.getText().toString());

                            String newstitle  = txtedittitle_news.getText().toString();
                            String newsdate = txteditdate_news.getText().toString();
                            String newsdetail = txteditdetail_news.getText().toString();*/

                            String URL = fileReference.toString();
                            String url = URL.substring(34);

                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
                            DatabaseReference myRef_newsEdit = database.getReference("admin001/news/"+newsTitle);
                            Query query1 =  myRef_newsEdit.orderByValue();
                            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        myRef_newsEdit.child("newsImg").setValue("https://firebasestorage.googleapis.com/v0/b/ti411app.appspot.com/o/uploads%2F"+url+"?alt=media&token=");
                                        myRef_newsEdit.child("detail").setValue(editNewsDetail.getText().toString().trim());

                                        Intent intent = new Intent(EditNewsActivity.this, ListNewsActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditNewsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }else {
            Toast.makeText(this, "กรุณาเลือกไฟล์รูปภาพ",Toast.LENGTH_SHORT).show();
        }


    }

/*    public void ClickEditNews(){
        News newsToedit = new News();

        ImageView edit_image_news = findViewById(R.id.image_editnews);
        newsToedit.setNewsImg(edit_image_news.toString());
        TextView txtedittitle_news = findViewById(R.id.txtedit_newstitle);
        newsToedit.setNewsTitle(txtedittitle_news.getText().toString());
        TextView txteditdate_news = findViewById(R.id.txtedit_newsdate);
        newsToedit.setNewsDate(txteditdate_news.getText().toString());
        TextView txteditdetail_news = findViewById(R.id.txtedit_newsdetail);
        newsToedit.setDetail(txteditdetail_news.getText().toString());

        String newstitle  = txtedittitle_news.getText().toString();
        String newsdate = txteditdate_news.getText().toString();
        String newsdetail = txteditdetail_news.getText().toString();
        String newsimage = edit_image_news.toString();

        News news_edit = new News(newstitle,newsimage,newsdate,newsdetail);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_newsEdit = database.getReference("admin001/news/"+newsTitle);
        Query query1 =  myRef_newsEdit.orderByValue();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    myRef_newsEdit.child("newsImg").setValue(news_edit.getNewsImg());
                    myRef_newsEdit.child("newsTitle").setValue(news_edit.getNewsTitle());
                    myRef_newsEdit.child("newsDate").setValue(news_edit.getNewsDate());
                    myRef_newsEdit.child("detail").setValue(news_edit.getDetail());

                    Intent intent = new Intent(EditNewsActivity.this, ListNewsActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/


    public void ClickEditNewsCancel (View view){
        Intent intent = new Intent(EditNewsActivity.this, ListNewsActivity.class);
        startActivity(intent);
    }
}