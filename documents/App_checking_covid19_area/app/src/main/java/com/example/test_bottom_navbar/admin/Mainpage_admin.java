package com.example.test_bottom_navbar.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.ui_bar.MainActivity;

public class Mainpage_admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage_admin);
    }

    public void ClickAddCluster(View view){
        Intent intent = new Intent(Mainpage_admin.this, AddClusterActivity.class);
        startActivity(intent);
    }

    public void ClickListRiskArea(View view){
        Intent intent = new Intent(Mainpage_admin.this, ListRiskAreaActivity.class);
        startActivity(intent);
    }

    public void ClickAddMohprom(View view){
        Intent intent = new Intent(Mainpage_admin.this, AddMohpromtActivity.class);
        startActivity(intent);
    }

    public void ClickEditMohprom(View view){
        Intent intent = new Intent(Mainpage_admin.this, EditMohpromActivity.class);
        startActivity(intent);
    }

    public void ClickListMohprom(View view){
        Intent intent = new Intent(Mainpage_admin.this, ListMohpromActivity.class);
        startActivity(intent);
    }

    /*public void ClickAddNews(View view){
        Intent intent = new Intent(Mainpage_admin.this, AddNewsActivity.class);
        startActivity(intent);
    }*/

    public void Clicklogout(View view){
        Intent intent = new Intent(Mainpage_admin.this, MainActivity.class);
        startActivity(intent);
    }
}