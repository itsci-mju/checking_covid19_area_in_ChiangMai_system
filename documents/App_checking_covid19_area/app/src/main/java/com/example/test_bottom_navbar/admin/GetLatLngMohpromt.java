package com.example.test_bottom_navbar.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.test_bottom_navbar.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GetLatLngMohpromt extends Fragment {
    private GoogleMap mMap,user_location,googleMap;
    double Mohpromt_Lat,Mohpromt_Lng;
    private ImageButton ButtonBack;
    private Button ButtonGetLatLng;
    String Mplace_def,Mstartdate_def,Menddate_def,Mdetail_def;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_lat_lng_map, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        Mplace_def = bundle.getString("Mplace_def");
        Mstartdate_def = bundle.getString("Mstartdate_def");
        Menddate_def = bundle.getString("Menddate_def");
        Mdetail_def = bundle.getString("Mdetail_def");

        getActivity().getIntent().getExtras().get("Mplace_def");
        getActivity().getIntent().getExtras().get("Mstartdate_def");
        getActivity().getIntent().getExtras().get("Menddate_def");
        getActivity().getIntent().getExtras().get("Mdetail_def");

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
                        Mohpromt_Lat = latLng.latitude;
                        Mohpromt_Lng = latLng.longitude;
                        System.out.println("////////////////////////////////////////////////////GET////"+Mohpromt_Lat+":"+Mohpromt_Lng);
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
                Intent intentLatLng = new Intent(getContext(), AddMohpromtActivity.class);
                intentLatLng.putExtra("Mohpromt_Lat", Mohpromt_Lat);
                intentLatLng.putExtra("Mohpromt_Lng", Mohpromt_Lng);
                intentLatLng.putExtra("Mplace_def",Mplace_def);
                intentLatLng.putExtra("Mstartdate_def",Mstartdate_def);
                intentLatLng.putExtra("Menddate_def",Menddate_def);
                intentLatLng.putExtra("Mdetail_def",Mdetail_def);
                startActivity(intentLatLng);
            }
        });

        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddMohpromtActivity.class);
                intent.putExtra("Mplace_def",Mplace_def);
                intent.putExtra("Mstartdate_def",Mstartdate_def);
                intent.putExtra("Menddate_def",Menddate_def);
                intent.putExtra("Mdetail_def",Mdetail_def);
                startActivity(intent);
            }
        });

        return view;
    }

}