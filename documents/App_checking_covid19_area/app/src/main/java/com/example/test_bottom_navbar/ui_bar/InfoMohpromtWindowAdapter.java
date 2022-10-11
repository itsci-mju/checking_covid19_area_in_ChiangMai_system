package com.example.test_bottom_navbar.ui_bar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test_bottom_navbar.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoMohpromtWindowAdapter implements GoogleMap.InfoWindowAdapter {
    Context context;
    public InfoMohpromtWindowAdapter(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return null;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        View infoView = LayoutInflater.from(context).inflate(R.layout.custom_mohpromt_info,null);
        TextView title = infoView.findViewById(R.id.title);
        TextView snipp = infoView.findViewById(R.id.snipp);
        title.setText(marker.getTitle());
        snipp.setText(marker.getSnippet());

        return infoView;
    }



}
