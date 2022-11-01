package com.example.test_bottom_navbar.ui_bar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_bottom_navbar.R;
import com.example.test_bottom_navbar.admin.AddClusterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ListClusterHistory extends AppCompatActivity {
    List<String> date_historyArray = new ArrayList<String>();
    String[] District = {"เมืองเชียงใหม่","แม่ริม","สันทราย","สารภี"};
    int All_PatientDistrict,All_HealingDistrict,All_GetWellDistrict,today_newpatient,today_gwtwellpatient;
    int Allpatient,AllHealing,AllGetWell,TodayNew,TodayGetWell;
    int getAll_Allpatient_district,getAll_Allgetwell_district,getAll_Allhealing_district;
    ArrayAdapter<String> dataAdapter_district;
    List<String> myListReport = new ArrayList<String>();
    private Spinner spinner_district;
    private TextView SarchButton;
    String sarchbutton,Select_disteict,district_name;
    String d_CM,d_MR,d_SS,d_SP;
    int num_selectdisteict;

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cluster_history);
        //ListToAddArray();

        spinner_district = findViewById(R.id.txt_district);
        dataAdapter_district = new ArrayAdapter<String>(ListClusterHistory.this, android.R.layout.simple_spinner_dropdown_item,District);
        spinner_district.setAdapter(dataAdapter_district);

        //ListReport();
    }

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    public void ClickCheck(View view){
        num_selectdisteict = spinner_district.getSelectedItemPosition();
        Select_disteict = String.valueOf(num_selectdisteict);
        System.out.println("+++++++++++++++ : "+ Select_disteict);
    }


    public void ClickSearchReport(View view) {
        num_selectdisteict = spinner_district.getSelectedItemPosition();
        Select_disteict = String.valueOf(num_selectdisteict);

        if (Select_disteict.equals("0")) {
            d_CM = "เมืองเชียงใหม่";
            district_name = d_CM;
        } else if (Select_disteict.equals("1")) {
            d_MR = "แม่ริม";
            district_name = d_MR;
        } else if (Select_disteict.equals("2")) {
            d_SS = "สันทราย";
            district_name = d_SS;
        } else if (Select_disteict.equals("3")) {
            d_SP = "สารภี";
            district_name = d_SP;
        }

        ListReport();
    }

    public void ListReport() {
        TextView def_date = findViewById(R.id.txt_date_report);
        sarchbutton = def_date.getText().toString();

        if (sarchbutton.equals("")) {
            Toast.makeText(ListClusterHistory.this, "กรุณาเลือกวันที่", Toast.LENGTH_SHORT).show();
        } else {
            LinearLayout list_report_array = findViewById(R.id.showlistcluster_history);
            list_report_array.removeAllViews();
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/history_cluster/" + sarchbutton);
            Query query1 = myRef.orderByKey().equalTo(district_name);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        View report_array = getLayoutInflater().inflate(R.layout.layout_cluster, null);
                        String disteict = ds.getKey();
                        String cluster_Allpatient_district = ds.child("cluster_Allpatient_district").getValue().toString();
                        String cluster_Allgetwell_district = ds.child("cluster_Allgetwell_district").getValue().toString();
                        String cluster_Allhealing_district = ds.child("cluster_Allhealing_district").getValue().toString();
                        String cluster_getwellpatinet_today = ds.child("cluster_getwellpatinet_today").getValue().toString();
                        String cluster_newpatinet_today = ds.child("cluster_newpatinet_today").getValue().toString();

                        TextView txtdistrict = report_array.findViewById(R.id.txt_disteictName);
                        txtdistrict.setText(disteict);

                        TextView txtdate = report_array.findViewById(R.id.txt_defDate);
                        txtdate.setText(sarchbutton);

                        TextView txtTodayGetWell = report_array.findViewById(R.id.txt_getwell_patient);
                        txtTodayGetWell.setText(cluster_getwellpatinet_today);

                        TextView txtTodayNew = report_array.findViewById(R.id.txt_new_patient);
                        txtTodayNew.setText(cluster_newpatinet_today);

                        TextView txtAllpatient = report_array.findViewById(R.id.txt_All_patient);
                        txtAllpatient.setText(cluster_Allpatient_district);

                        TextView txtAllGetWell = report_array.findViewById(R.id.txt_Totalgetwell_patient);
                        txtAllGetWell.setText(cluster_Allgetwell_district);

                        TextView txtAllHealing = report_array.findViewById(R.id.txt_Totalhealing_patient);
                        txtAllHealing.setText(cluster_Allhealing_district);

                        list_report_array.addView(report_array);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }


    @SuppressLint("NewApi")
    public void ClickDateReport(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ListClusterHistory.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                TextView txtdate = findViewById(R.id.txt_date_report);
                String day = checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month + 1));
                txtdate.setText(day + "-" + months + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    /*public void ListToAddArray(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("admin001/history_cluster");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    date= ds.getKey();
                    date_historyArray.add(date);
                }
                ListToCal();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }*/

    /*public void ListToCal(){
        System.out.println("======================== "+date_historyArray);
        for (int i = 0; i < date_historyArray.size(); i++) {
                LinearLayout list_cluster = findViewById(R.id.showlistcluster_history);
                View listhistory = getLayoutInflater().inflate(R.layout.layout_cluster, null);
                list_cluster.removeAllViews();
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("admin001/history_cluster/" + date_historyArray.get(i));
                String date_historyArray_name = date_historyArray.get(i);
                //String district_name = District[u];
                Query query = myRef.orderByKey();
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String cluster_Allpatient_district = ds.child("cluster_Allpatient_district").getValue().toString();
                            String cluster_Allgetwell_district = ds.child("cluster_Allgetwell_district").getValue().toString();
                            String cluster_Allhealing_district = ds.child("cluster_Allhealing_district").getValue().toString();
                            String cluster_getwellpatinet_today = ds.child("cluster_getwellpatinet_today").getValue().toString();
                            String cluster_newpatinet_today = ds.child("cluster_newpatinet_today").getValue().toString();

                            today_gwtwellpatient = Integer.parseInt(cluster_getwellpatinet_today);
                            today_newpatient = Integer.parseInt(cluster_newpatinet_today);
                            All_PatientDistrict = Integer.parseInt(cluster_Allpatient_district);
                            All_HealingDistrict = Integer.parseInt(cluster_Allhealing_district);
                            All_GetWellDistrict = Integer.parseInt(cluster_Allgetwell_district);

                            if(!date_historyArray_name.equals(date)){
                                TodayNew = 0;
                                TodayGetWell = 0;
                                Allpatient = 0;
                                AllGetWell = 0;
                                AllHealing = 0;
                            }

                            TodayNew = TodayNew + today_newpatient;
                            TodayGetWell = TodayGetWell + today_gwtwellpatient;

                            Allpatient = Allpatient + All_PatientDistrict;
                            AllGetWell = AllGetWell + All_GetWellDistrict;
                            AllHealing = AllHealing + All_HealingDistrict;

                            TextView txtdate = listhistory.findViewById(R.id.txt_defDate);
                            txtdate.setText(date_historyArray_name);

                            TextView txtTodayGetWell = listhistory.findViewById(R.id.txt_getwell_patient);
                            txtTodayGetWell.setText(String.valueOf(TodayGetWell));

                            TextView txtTodayNew = listhistory.findViewById(R.id.txt_new_patient);
                            txtTodayNew.setText(String.valueOf(TodayNew));

                            TextView txtAllpatient = listhistory.findViewById(R.id.txt_All_patient);
                            txtAllpatient.setText(String.valueOf(Allpatient));

                            TextView txtAllGetWell = listhistory.findViewById(R.id.txt_Totalgetwell_patient);
                            txtAllGetWell.setText(String.valueOf(AllGetWell));

                            TextView txtAllHealing = listhistory.findViewById(R.id.txt_Totalhealing_patient);
                            txtAllHealing.setText(String.valueOf(AllHealing));

                        }
                        list_cluster.addView(listhistory);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
    }*/

}