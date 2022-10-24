package com.example.test_bottom_navbar.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.ui_bar.MohpromtActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GetLatLngMapFragment extends Fragment {
    private GoogleMap mMap,user_location,googleMap;
    double Cluster_Lat,Cluster_Lng;
    private ImageButton ButtonBack;
    private Button ButtonGetLatLng;
    String place_def,news_patient_def,Clat_def,Clng_def;
    String subdistrict_def,district_def;
    int dt,sdt;

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_lat_lng_map, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        place_def = bundle.getString("place_def");

        dt = bundle.getInt("dt");
        Log.e("////////////////////////////////////////S", String.valueOf(dt));
        sdt = bundle.getInt("sdt");
        Log.e("////////////////////////////////////////S", String.valueOf(sdt));

        news_patient_def = bundle.getString("news_patient_def");
        Clat_def = bundle.getString("Clat_def");
        Clng_def = bundle.getString("Clng_def");

        getActivity().getIntent().getExtras().get("place_def");
        getActivity().getIntent().getExtras().get("subdistrict_def");
        getActivity().getIntent().getExtras().get("district_def");
        getActivity().getIntent().getExtras().get("news_patient_def");
        getActivity().getIntent().getExtras().get("Clat_def");
        getActivity().getIntent().getExtras().get("Clng_def");

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        ButtonBack = view.findViewById(R.id.imageButtonBack);
        ButtonGetLatLng = view.findViewById(R.id.buttonGetlatlng);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        markerOptions.position(latLng).toString();
                        System.out.println("////////////////////////////////////////////////////" + latLng.latitude + "/" + latLng.longitude);
                        Cluster_Lat = latLng.latitude;
                        Cluster_Lng = latLng.longitude;
                        System.out.println("////////////////////////////////////////////////////GET////"+Cluster_Lat+":"+Cluster_Lng);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 16
                        ));
                        googleMap.addMarker(markerOptions);
                    }
                });
                mMap = googleMap;
                LatLng user_location = new LatLng(18.7858623, 98.9764537);
                mMap.addMarker(new MarkerOptions()
                        .position(user_location)
                );
                mMap.moveCamera(CameraUpdateFactory.newLatLng(user_location));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
                mMap.getUiSettings().setZoomControlsEnabled(true);
            }
        });

        ButtonGetLatLng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLatLng = new Intent(getContext(), AddClusterActivity.class);
                intentLatLng.putExtra("Cluster_Lat", Cluster_Lat);
                intentLatLng.putExtra("Cluster_Lng", Cluster_Lng);
                intentLatLng.putExtra("place_def", place_def);
                intentLatLng.putExtra("sdt", sdt);
                intentLatLng.putExtra("dt", dt);
                intentLatLng.putExtra("news_patient_def", news_patient_def);
                startActivity(intentLatLng);
            }
        });

        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddClusterActivity.class);
                intent.putExtra("Cluster_Lat", Cluster_Lat);
                intent.putExtra("Cluster_Lng", Cluster_Lng);
                intent.putExtra("place_def", place_def);
                intent.putExtra("subdistrict_def", subdistrict_def);
                intent.putExtra("district_def", district_def);
                intent.putExtra("news_patient_def", news_patient_def);
                intent.putExtra("Clat_def", Clat_def);
                intent.putExtra("Clng_def", Clng_def);
                startActivity(intent);
            }
        });

        return view;
    }

}