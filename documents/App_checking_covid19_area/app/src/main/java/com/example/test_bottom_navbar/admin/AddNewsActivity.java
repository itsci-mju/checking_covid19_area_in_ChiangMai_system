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
import android.widget.Button;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class AddNewsActivity extends AppCompatActivity {
    String Admin;

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView mButtonChooseImage;
    private TextView mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri ImageUri;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        mButtonChooseImage = findViewById(R.id.image_view);
        mButtonUpload = findViewById(R.id.button_addnew);
        //mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        Intent intent = getIntent();
        Admin = intent.getStringExtra("Admin");

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
                    Toast.makeText(AddNewsActivity.this, "อยู่ในระหว่างการอัปโหลด", Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
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

    private void uploadFile(){
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

                            final EditText newstitle = (EditText) findViewById(R.id.txtadd_newstitle);
                            final EditText newsdate = (EditText) findViewById(R.id.txtadd_newsdate);
                            final EditText newsdetail = (EditText) findViewById(R.id.txtadd_newsdetail);

                            String newsTitle = newstitle.getText().toString();
                            String URL = fileReference.toString();
                            String url = URL.substring(34);
                            //System.out.println("************************************************"+url);
                            String newsDate = newsdate.getText().toString();
                            String newsDetail = newsdetail.getText().toString();

                                FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                DatabaseReference myRef = database.getReference("admin001/news");
                                Query query2 = myRef.orderByKey().equalTo(newsTitle);
                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String Error = "F";
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            Error = "T";
                                        }
                                        if (Error.equals("F")) {
                                            News Nw = new News(newsTitle
                                                    , "https://firebasestorage.googleapis.com/v0/b/ti411app.appspot.com/o/uploads%2F"+url+"?alt=media&token="
                                                    , newsDate, newsDetail);
                                            DatabaseReference stu1 = myRef.child(newsTitle);
                                            stu1.setValue(Nw);
                                            Intent intent = new Intent(AddNewsActivity.this, ListNewsActivity.class);
                                            intent.putExtra("newsTitle", newsTitle);
                                            Toast.makeText(AddNewsActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_LONG).show();
                                            intent.putExtra("Admin", Admin);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(AddNewsActivity.this, "ชื่อสถานที่เกิดคลัสเตอร์ ซ้ำ", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(AddNewsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    public void ClickDateNews(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                EditText txtdate = findViewById(R.id.txtadd_newsdate);
                String day = checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month + 1));
                txtdate.setText(day + "-" + months + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void ClickCancelAddNews (View view){
        Intent intent = new Intent(AddNewsActivity.this, Mainpage_admin.class);
        startActivity(intent);
    }
}