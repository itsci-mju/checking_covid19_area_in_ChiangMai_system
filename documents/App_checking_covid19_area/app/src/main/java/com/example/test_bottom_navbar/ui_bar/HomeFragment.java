package com.example.test_bottom_navbar.ui_bar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.admin.ListRiskAreaActivity;
import com.example.test_bottom_navbar.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.GoogleMap;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Intent intent = new Intent(getContext(),MainActivity.class);
        startActivity(intent);


        return view;
    }

}