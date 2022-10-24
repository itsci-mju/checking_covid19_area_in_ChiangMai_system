package com.example.test_bottom_navbar.ui_bar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test_bottom_navbar.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    Context context;
    String def_date;
    private String[] District= {"เมืองเชียงใหม่","สารภี","เเม่ริม","สันกำเเพง","สันทราย"};
    public InfoWindowAdapter(Context context) {
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
        DateClusterDefault();
        setListCluster();

        View infoView = LayoutInflater.from(context).inflate(R.layout.custom_info,null);
        TextView title = infoView.findViewById(R.id.title);
        TextView snipp = infoView.findViewById(R.id.snipp);
        title.setText(marker.getTitle());
        snipp.setText(marker.getSnippet());

        /*DateClusterDefault();
        setListCluster();*/

        return infoView;
    }

    public void setListCluster(){
        for (int i=0;i < District.length;i++) {
            System.out.println(District[i]);
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/cluster/" + District[i]);
            Query query1 = myRef.orderByKey();
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String clusterDate = ds.child("clusterDate").getValue().toString();
                        if(clusterDate.equals(def_date)){
                            View infoView = LayoutInflater.from(context).inflate(R.layout.custom_info,null);
                            TextView status = infoView.findViewById(R.id.status);
                            status.setText("ใหม่");
                            return;
                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    public void DateClusterDefault(){
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        String day = checklength(String.valueOf(mDay));
        String months = checklength(String.valueOf(mMonth + 1));
        def_date = day + "-" + months + "-" + mYear;
    }

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

}
