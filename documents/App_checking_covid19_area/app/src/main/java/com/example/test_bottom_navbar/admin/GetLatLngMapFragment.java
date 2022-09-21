package com.example.test_bottom_navbar.admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_lat_lng_map, container, false);

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
                startActivity(intentLatLng);
            }
        });

        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddClusterActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}