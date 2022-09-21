package com.example.test_bottom_navbar.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.test_bottom_navbar.R;

public class GetLatLngMohpromtActivity extends AppCompatActivity {
    String Cluster_latlng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_lat_lng_mohprmt);

        Fragment fragment = new GetLatLngMohpromt();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_m,fragment)
                .commit();
    }

}