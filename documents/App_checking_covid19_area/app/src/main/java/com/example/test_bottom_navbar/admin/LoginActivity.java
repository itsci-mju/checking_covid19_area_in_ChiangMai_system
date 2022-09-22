package com.example.test_bottom_navbar.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_bottom_navbar.R;

public class LoginActivity extends AppCompatActivity {

    String ID;
    String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginbyadmin);
    }

    public void ClickLoginFreeWay(View view){
        Intent intent = new Intent(LoginActivity.this, Mainpage_admin.class);
        startActivity(intent);
    }

    /*public void ClickLogin(View view){
        EditText id = findViewById(R.id.adminid);
        ID = id.getText().toString();

        EditText pw = findViewById(R.id.password);
        Password = pw.getText().toString();

        if(!ID.equals("")&&!Password.equals("")) {
            Toast.makeText(LoginActivity.this, "เข้าสู่ระบบ", Toast.LENGTH_LONG).show();
            this.queryFirebase(ID,Password);
        }else {
            Toast.makeText(LoginActivity.this, "กรุณากรอก ID เเละ Password ให้ครบ", Toast.LENGTH_LONG).show();
        }
    }*/

    /*public void queryFirebase(String ID , String Password){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef= database.getReference("Admin");
        Query query1 = myRef.orderByKey().equalTo("password");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.getValue().toString().equals(Password)){
                        Intent intent = new Intent(LoginActivity.this, Mainpage_admin.class);
                        intent.putExtra("ID", ID);
                        startActivity(intent);

                    }else{
                        Toast.makeText(LoginActivity.this, "กรุณากรอกใหม่", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }*/




}