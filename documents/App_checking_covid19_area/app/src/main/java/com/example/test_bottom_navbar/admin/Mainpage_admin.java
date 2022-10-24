package com.example.test_bottom_navbar.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.ui_bar.MainActivity;
import com.example.test_bottom_navbar.ui_bar.MainMenuActivity;

public class Mainpage_admin extends AppCompatActivity {
    int Allpatient_District,Totalpatient_CM,Totalpatient_Sarapee,Totalpatient_MaeRim,Totalpatient_SunSai;

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

    public void ClickListDistrict(View view){
        Intent intent = new Intent(Mainpage_admin.this, ListDistrictClusterActivity.class);
        startActivity(intent);
    }

    public void ClickAddMohprom(View view){
        Intent intent = new Intent(Mainpage_admin.this, AddMohpromtActivity.class);
        startActivity(intent);
    }


    public void ClickListMohprom(View view){
        Intent intent = new Intent(Mainpage_admin.this, ListMohpromActivity.class);
        startActivity(intent);
    }

    public void ClickAddNews(View view){
        Intent intent = new Intent(Mainpage_admin.this, AddNewsActivity.class);
        startActivity(intent);
    }

    public void ClickListNews(View view){
        Intent intent = new Intent(Mainpage_admin.this, ListNewsActivity.class);
        startActivity(intent);
    }

    public void Clicklogout(View view){
        Intent intent = new Intent(Mainpage_admin.this, MainMenuActivity.class);
        startActivity(intent);
    }
}