package com.example.test_bottom_navbar.admin;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.MapFragment;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test_bottom_navbar.R;

public class GetLatLngClusterActivity extends AppCompatActivity {
    String place_def,txtplace_def;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_lat_lng_cluster);

        Fragment fragment = new GetLatLngMapFragment();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }

}